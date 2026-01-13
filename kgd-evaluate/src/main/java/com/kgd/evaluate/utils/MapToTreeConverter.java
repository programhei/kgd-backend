package com.kgd.evaluate.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 核心转换工具：Map转树状结构（score为最终得分，无需乘权重）
 */
public class MapToTreeConverter {

    // 定义参与计算的7个核心维度
    private static final Set<String> CALCULATE_DIMENSIONS = Arrays.stream(new String[]{
            "近距空战想定",
            "中远距空战想定",
            "态势分类合理性",
            "基本编队",
            "视距内空战编队",
            "中距空战编队",
            "远距空战编队"
    }).collect(Collectors.toSet());

    /**
     * 核心转换方法
     * @param originalMap 原始Map数据（key: score/children/weight）
     * @return 完整树状结构根节点
     */
    @SuppressWarnings("unchecked")
    public TreeStructNode convert(Map<String, Object> originalMap) {
        // 1. 解析原始Map核心字段
        Double rootShowScore = Double.parseDouble(originalMap.get("score").toString()); // 原始展示用得分
        List<Map<String, Object>> flatChildren = (List<Map<String, Object>>) originalMap.get("children");
        Double originalRootWeight = Double.parseDouble(originalMap.get("weight").toString());

        // 2. 构建根节点（想定合理性）
        TreeStructNode root = new TreeStructNode("想定合理性", 0.2, 0.0, true);

        // 3. 构建一级子节点
        TreeStructNode situationNode = new TreeStructNode("态势想定合理性", 0.6, 0.0, true); // 态势想定合理性（权重0.6参与根节点计算）
        TreeStructNode formationNode = new TreeStructNode("编队合理性", 0.4, 0.0, true);     // 编队合理性（权重0.4参与根节点计算）
        TreeStructNode baseParamNode = new TreeStructNode("基础参数合理性", 0.0, 0.0, false); // 基础参数（仅展示）

        // 4. 遍历所有扁平节点，分类封装
        for (Map<String, Object> flatNodeMap : flatChildren) {
            // 解析叶子节点基础信息
            String dimension = flatNodeMap.get("dimension").toString();
            Double nodeScore = Double.parseDouble(flatNodeMap.get("score").toString());
            Double nodeWeight = Double.parseDouble(flatNodeMap.get("weight").toString());
            Boolean isCalculate = CALCULATE_DIMENSIONS.contains(dimension);

            // 封装叶子节点（score为最终得分，weight仅展示）
            TreeStructNode leafNode = new TreeStructNode(dimension, nodeWeight, nodeScore, isCalculate);

            // 按规则归类到对应父节点
            if (dimension.contains("飞机数量") || dimension.contains("敌我标识")
                    || dimension.contains("初始化") || dimension.contains("航炮")
                    || dimension.contains("导弹数量")) {
                baseParamNode.getChildren().add(leafNode); // 归到基础参数
            } else if (dimension.contains("空战想定") || dimension.contains("态势分类")) {
                situationNode.getChildren().add(leafNode); // 归到态势想定
            } else if (dimension.contains("编队")) {
                formationNode.getChildren().add(leafNode); // 归到编队合理性
            }
        }

        // 5. 基础参数节点加入态势想定的子节点（展示层级）
        situationNode.getChildren().add(0, baseParamNode);

        // 6. 计算得分（核心修正：仅求和，不乘叶子节点权重）
        calculateNodeScore(situationNode);
        calculateNodeScore(formationNode);

        // 7. 计算根节点总分（仅一级节点权重参与计算）
        double rootTotalScore = (situationNode.getScore() * situationNode.getWeight())
                + (formationNode.getScore() * formationNode.getWeight());
        root.setScore(rootTotalScore);

        // 8. 组装完整树结构
        root.getChildren().add(situationNode);
        root.getChildren().add(formationNode);

        return root;
    }

    /**
     * 递归计算节点得分：
     * - 叶子节点：直接返回score（无需乘权重）
     * - 非叶子节点：累加子节点中isCalculate=true的score之和
     */
    private Double calculateNodeScore(TreeStructNode node) {
        // 叶子节点：参与计算则返回score，否则返回0
        if (node.getChildren().isEmpty()) {
            return node.getIsCalculate() ? node.getScore() : 0.0;
        }

        // 非叶子节点：仅累加子节点中isCalculate=true的score（不乘权重）
        Double totalScore = 0.0;
        for (TreeStructNode child : node.getChildren()) {
            Double childScore = calculateNodeScore(child);
            if (child.getIsCalculate()) { // 仅参与计算的子节点才累加
                totalScore += childScore;
            }
        }

        // 设置当前节点得分并返回
        node.setScore(totalScore);
        return totalScore;
    }
}
