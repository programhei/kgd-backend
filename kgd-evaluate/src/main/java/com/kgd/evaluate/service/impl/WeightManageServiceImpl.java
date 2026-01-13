package com.kgd.evaluate.service.impl;


import com.kgd.common.utils.DateUtils;
import com.kgd.common.utils.SecurityUtils;
import com.kgd.evaluate.domain.WeightMangeData;
import com.kgd.evaluate.mapper.WeightMangeMapper;
import com.kgd.evaluate.service.IWeightManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WeightManageServiceImpl implements IWeightManageService {

    @Autowired
    private WeightMangeMapper weightMangeMapper;

    @Override
    public Map<String, Object> selectWeightTree(WeightMangeData weightMangeData) {
        List<WeightMangeData> tree = weightMangeMapper.tree(0L,Long.valueOf(weightMangeData.getGroupId()),  Long.valueOf(weightMangeData.getType()));
//        List<WeightMangeData> list = weightMangeMapper.selectWeightTree(weightMangeData);
//        List<WeightMangeData> reList = new ArrayList<>();
//        for (WeightMangeData data: list) {
//            data.setChildren(new ArrayList<>());
//            if(data.getParentId() == 0) {
//                reList.add(data);
//            }else{
//                Long parentId = data.getParentId();
//                // 使用递归方法查找并添加到对应的父元素下
//                findAndAddChild(reList, parentId, data);
//            }
//        }
//        // 递归处理所有节点，将空的children列表设置为null
//        for (WeightMangeData item : reList) {
//            processEmptyChildren(item);
//        }
        Map<String, Object>  map = new HashMap<>();
        map.put("list", tree);

        countAvgWeightWithType();
        return map;
    }

    /**
     * 查询权值
     *
     * @param weightMangeData 权值数据
     * @return 权值数据结果
     */
    @Override
    public Map<String, Object> selectWeightTree(Integer type, Integer groupId) {
        Long groupIdLong = groupId != null ? Long.valueOf(groupId) : null;
        List<WeightMangeData> tree = weightMangeMapper.tree(0L, groupIdLong, Long.valueOf(type));
//        List<WeightMangeData> list = weightMangeMapper.selectWeightTree(weightMangeData);
//        List<WeightMangeData> reList = new ArrayList<>();
//        for (WeightMangeData data: list) {
//            data.setChildren(new ArrayList<>());
//            if(data.getParentId() == 0) {
//                reList.add(data);
//            }else{
//                Long parentId = data.getParentId();
//                // 使用递归方法查找并添加到对应的父元素下
//                findAndAddChild(reList, parentId, data);
//            }
//        }
//        // 递归处理所有节点，将空的children列表设置为null
//        for (WeightMangeData item : reList) {
//            processEmptyChildren(item);
//        }
        Map<String, Object>  map = new HashMap<>();
        map.put("list", tree);

        countAvgWeightWithType();
        return map;
    }
    private void processEmptyChildren(WeightMangeData item) {
        if (item.getChildren() != null && item.getChildren().isEmpty()) {
            item.setChildren(null);
        } else if (item.getChildren() != null && !item.getChildren().isEmpty()) {
            // 递归处理所有子节点
            for (WeightMangeData child : item.getChildren()) {
                processEmptyChildren(child);
            }
        }

    }
    /**
     * 递归查找并添加子元素
     * @param list 要查找的元素列表
     * @param parentId 父元素ID
     * @param child 要添加的子元素
     * @return 是否找到并添加成功
     */
    private boolean findAndAddChild(List<WeightMangeData> list, Long parentId, WeightMangeData child) {
        for (WeightMangeData item : list) {
            if (item.getId().equals(parentId)) {
                item.getChildren().add(child);
                return true;
            }
            // 递归查找子元素的子元素
            if (!item.getChildren().isEmpty()) {
                if (findAndAddChild(item.getChildren(), parentId, child)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     *  保存权值
     *
     * @param weightMangeData 请求参数
     */
    @Override
    public void saveWeight(WeightMangeData weightMangeData) throws Exception {

        weightMangeData.setUpdateTime(DateUtils.getNowDate());
        weightMangeData.setUpdateBy(SecurityUtils.getUsername());
        weightMangeMapper.updateWeightData(weightMangeData);

    }
    /**
     * 查询权值
     *
     * @param weightMangeData 权值数据
     * @return 权值数据结果
     */
    @Override
    public Map<String, Object> selectTotalScore(WeightMangeData weightMangeData) {
        Map map = new HashMap();
        List<WeightMangeData> list = weightMangeMapper.selectWeightTree(weightMangeData);
        List<WeightMangeData> reList = new ArrayList<>();
        for (WeightMangeData data: list) {
            data.setChildren(new ArrayList<>());
            if(data.getParentId() == 0) {
                reList.add(data);
            }else{
                Long parentId = data.getParentId();
                // 使用递归方法查找并添加到对应的父元素下
                findAndAddChild(reList, parentId, data);
            }
        }
        
        
        // 计算所有节点的分数，从最里面的节点开始计算到最上层节点
        for (WeightMangeData rootNode : reList) {
            calculateNodeScore(rootNode);
        }
        double total = 0;
        for (WeightMangeData rootNode : reList) {
            rootNode.setChildren(null);
            rootNode.setScore(rootNode.getWeight()*rootNode.getScore());
            total += rootNode.getScore();
        }
        WeightMangeData totalData = new WeightMangeData();
        totalData.setName("总和");
        totalData.setScore(total);

        reList.add(totalData);
        map.put("list", reList);

        return map;
    }

    @Override
    public List<WeightMangeData> countAvgWeightWithType() {
        List<WeightMangeData> kgTree = weightMangeMapper.tree(0L, null, 1L);
        List<WeightMangeData> zgTree = weightMangeMapper.tree(0L, null, 0L);

        // 按层级计算平均值
        Map<String, Map<Integer, Double>> kgAvgByLevel = calculateAverageByLevelAndName(kgTree);
        Map<String, Map<Integer, Double>> zgAvgByLevel = calculateAverageByLevelAndName(zgTree);

        List<WeightMangeData> rulist = weightMangeMapper.tree(0L, null, null);

        if (!CollectionUtils.isEmpty(rulist)) {
            rulist = rulist.stream()
                    .collect(Collectors.toMap(
                            WeightMangeData::getName,  // 使用名称作为key
                            Function.identity(),       // value为对象本身
                            (existing, replacement) -> existing  // 如果重复，保留已存在的
                    ))
                    .values()
                    .stream()
                    .collect(Collectors.toList());
            // 按层级设置平均值
            setAveragesByLevelRecursive(rulist, kgAvgByLevel, zgAvgByLevel, 1);

            sortTreeByOrderRecursive(rulist);
        }

        return rulist;
    }

    /**
     * 递归对整棵树的每层节点按sortOrder排序（处理null值）
     */
    private void sortTreeByOrderRecursive(List<WeightMangeData> nodes) {
        if (CollectionUtils.isEmpty(nodes)) {
            return;
        }

        // 对当前层级节点排序，处理sortOrder为null的情况
        nodes.sort(Comparator.comparing(
                WeightMangeData::getSortOrder,
                Comparator.nullsLast(Comparator.naturalOrder())
        ));

        // 递归对子节点排序
        for (WeightMangeData node : nodes) {
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                sortTreeByOrderRecursive(node.getChildren());
            }
        }
    }

    private Map<String, Map<Integer, Double>> calculateAverageByLevelAndName(List<WeightMangeData> tree) {
        if (CollectionUtils.isEmpty(tree)) {
            return new HashMap<>();
        }

        // 层级 -> 名称 -> 权重列表
        Map<Integer, Map<String, List<Double>>> levelCollector = new HashMap<>();

        // 递归收集，传入起始层级
        traverseWithLevel(tree, levelCollector, 1);

        // 计算每层级的平均值
        Map<String, Map<Integer, Double>> result = new HashMap<>();

        for (Map.Entry<Integer, Map<String, List<Double>>> levelEntry : levelCollector.entrySet()) {
            Integer level = levelEntry.getKey();
            Map<String, List<Double>> nameWeights = levelEntry.getValue();

            for (Map.Entry<String, List<Double>> nameEntry : nameWeights.entrySet()) {
                String name = nameEntry.getKey();
                List<Double> weights = nameEntry.getValue();

                if (!weights.isEmpty()) {
                    double avg = weights.stream()
                            .mapToDouble(Double::doubleValue)
                            .average()
                            .orElse(0.0);

                    // 初始化内层Map
                    result.putIfAbsent(name, new HashMap<>());
                    result.get(name).put(level, avg);
                }
            }
        }

        return result;
    }

    /**
     * 带层级的递归遍历
     */
    private void traverseWithLevel(List<WeightMangeData> nodes,
                                   Map<Integer, Map<String, List<Double>>> levelCollector,
                                   int currentLevel) {
        if (CollectionUtils.isEmpty(nodes)) {
            return;
        }

        // 初始化当前层级的Map
        levelCollector.putIfAbsent(currentLevel, new HashMap<>());
        Map<String, List<Double>> currentLevelMap = levelCollector.get(currentLevel);

        for (WeightMangeData node : nodes) {
            String name = node.getName();
            Double weight = node.getWeight();

            // 初始化名称的权重列表
            currentLevelMap.putIfAbsent(name, new ArrayList<>());

            // 添加权重
            if (weight != null) {
                currentLevelMap.get(name).add(weight);
            }

            // 递归处理子节点，层级+1
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                traverseWithLevel(node.getChildren(), levelCollector, currentLevel + 1);
            }
        }
    }

    /**
     * 按层级递归设置平均值
     */
    private void setAveragesByLevelRecursive(List<WeightMangeData> nodes,
                                             Map<String, Map<Integer, Double>> kgAvgByLevel,
                                             Map<String, Map<Integer, Double>> zgAvgByLevel,
                                             int currentLevel) {
        if (CollectionUtils.isEmpty(nodes)) {
            return;
        }

        for (WeightMangeData node : nodes) {
            String name = node.getName();
            double weightKg = 0.0;
            double weightZg = 0.0;
            // 设置当前层级的kg平均值
            if (kgAvgByLevel.containsKey(name) && kgAvgByLevel.get(name).containsKey(currentLevel)) {
                weightKg = kgAvgByLevel.get(name).get(currentLevel);
                node.setWeightKg(weightKg);
            }

            // 设置当前层级的zg平均值
            if (zgAvgByLevel.containsKey(name) && zgAvgByLevel.get(name).containsKey(currentLevel)) {
                weightZg = zgAvgByLevel.get(name).get(currentLevel);
                node.setWeightZg(weightZg);
            }
            calculateAndSetTotalWeight(node,weightKg, weightZg);
            // 递归处理子节点，层级+1
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                setAveragesByLevelRecursive(node.getChildren(), kgAvgByLevel, zgAvgByLevel, currentLevel + 1);
            }
        }
    }


    /**
     * 计算并设置综合权重：0.5 * WeightZg + 0.5 * WeightKg
     */
    private void calculateAndSetTotalWeight(WeightMangeData node, double weightKg, double weightZg) {
        double totalWeight = 0.5 * weightZg + 0.5 * weightKg;
        node.setWeightZh(totalWeight);

    }
    @Override
    public List<Long> selectUserIds() {
        return weightMangeMapper.selectUserIds();
    }

    /**
     * 递归计算节点的分数
     * 当前节点score = 所有子节点的score*weight的总和
     * @param node 要计算分数的节点
     * @return 计算后的节点分数
     */
    private Double calculateNodeScore(WeightMangeData node) {
        // 如果是叶子节点，直接返回其自身的分数
        if (node.getChildren() == null || node.getChildren().isEmpty()) {
            return node.getScore();
        }
        
        // 非叶子节点，计算所有子节点的score*weight的总和
        double totalScore = 0.0;
        for (WeightMangeData child : node.getChildren()) {
            Double childScore = calculateNodeScore(child);
            Double childWeight = child.getWeight();
            totalScore += childScore * childWeight;
        }
        
        // 设置当前节点的分数
        node.setScore(totalScore);
        return totalScore;
    }

}