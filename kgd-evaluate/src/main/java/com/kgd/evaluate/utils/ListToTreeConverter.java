package com.kgd.evaluate.utils;

import com.kgd.evaluate.domain.EvaluateScore;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author:
 * @description:
 * @date: 2025/12/23
 * @version: 1.0
 */
public class ListToTreeConverter {
    // 定义参与计算的7个核心维度
    private static final Set<String> CALCULATE_DIMENSIONS = Arrays.stream(new String[]{
            "软件质量-时间响应特性",
            "软件质量-资源利用率",
            "软件质量-代码注释率",
            "软件质量-代码规范率",
            "软件质量-连续运行时间",
            "软件质量-接口容错率"
    }).collect(Collectors.toSet());
    /**
     * 核心转换方法
     * @param originalList 原始Map数据（key: score/children/weight）
     * @return 完整树状结构根节点
     */
    @SuppressWarnings("unchecked")
    public ListToTreeStructNode convert(List<EvaluateScore> originalList) {
        List<Map<String, EvaluateScore>> flatChildren = new ArrayList<>();
        originalList.forEach(
                item ->{
                    Map<String, EvaluateScore> tempHashMap = new HashMap<>();
                    tempHashMap.put("children", item);
                    flatChildren.add(tempHashMap);
                }
        );
        // 1. 解析原始Map核心字段
        //Double rootShowScore = Double.parseDouble(originalMap.get("score").toString()); // 原始展示用得分
        //List<Map<String, Object>> flatChildren = (List<Map<String, Object>>) flatChildren.get("children");
        //Double originalRootWeight = Double.parseDouble(originalMap.get("weight").toString());

        // 2. 构建根节点（想定合理性）
        ListToTreeStructNode root = new ListToTreeStructNode("软件质量", 0.1, 0.0, true);

        // 3. 构建一级子节点
        ListToTreeStructNode situationNode = new ListToTreeStructNode("软件性能效率", 0.3, 0.0, true); // 态势想定合理性（权重0.6参与根节点计算）
        ListToTreeStructNode formationNode = new ListToTreeStructNode("可维护性", 0.3, 0.0, true);     // 编队合理性（权重0.4参与根节点计算）
        ListToTreeStructNode baseParamNode = new ListToTreeStructNode("可靠性", 0.4, 0.0, true); // 基础参数（仅展示）

        // 4. 遍历所有扁平节点，分类封装
        for (Map<String, EvaluateScore> flatNodeMap : flatChildren) {
            EvaluateScore children = flatNodeMap.get("children");
            // 解析叶子节点基础信息
            String dimension = children.getScoreType();
            Double nodeScore = children.getScore().multiply(BigDecimal.valueOf(children.getWeight())).doubleValue();
            Double nodeWeight = children.getWeight();
            Boolean isCalculate = CALCULATE_DIMENSIONS.contains(dimension);
            String reason = children.getReason();
            // 封装叶子节点（score为最终得分，weight仅展示）
            ListToTreeStructNode leafNode = new ListToTreeStructNode(dimension, reason, nodeWeight, nodeScore, isCalculate);

            // 按规则归类到对应父节点
            if (dimension.contains("软件质量-连续运行时间") || dimension.contains("软件质量-接口容错率")) {
                baseParamNode.getChildren().add(leafNode); // 归到可靠性
            } else if (dimension.contains("软件质量-时间响应特性") || dimension.contains("软件质量-资源利用率")) {
                situationNode.getChildren().add(leafNode); // 归到软件性能效率
            } else if (dimension.contains("软件质量-代码注释率") || dimension.contains("软件质量-代码规范率")) {
                formationNode.getChildren().add(leafNode); // 归到可维护性
            }
        }

        // 5. 基础参数节点加入态势想定的子节点（展示层级）
        //situationNode.getChildren().add(0, baseParamNode);

        // 6. 计算得分（核心修正：仅求和，不乘叶子节点权重）
        calculateNodeScore(situationNode);
        calculateNodeScore(formationNode);
        calculateNodeScore(baseParamNode);

        // 7. 计算根节点总分（仅一级节点权重参与计算）
        double rootTotalScore = (situationNode.getScore() * situationNode.getWeight())
                + (formationNode.getScore() * formationNode.getWeight() + baseParamNode.getScore() * baseParamNode.getWeight());
        root.setScore(rootTotalScore);

        // 8. 组装完整树结构
        root.getChildren().add(situationNode);
        root.getChildren().add(formationNode);
        root.getChildren().add(baseParamNode);

        return root;
    }

    /**
     * 递归计算节点得分：
     * - 叶子节点：直接返回score（无需乘权重）
     * - 非叶子节点：累加子节点中isCalculate=true的score之和
     */
    private Double calculateNodeScore(ListToTreeStructNode node) {
        // 叶子节点：参与计算则返回score，否则返回0
        if (node.getChildren().isEmpty()) {
            return node.getIsCalculate() ? node.getScore() : 0.0;
        }

        // 非叶子节点：仅累加子节点中isCalculate=true的score（不乘权重）
        Double totalScore = 0.0;
        for (ListToTreeStructNode child : node.getChildren()) {
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
