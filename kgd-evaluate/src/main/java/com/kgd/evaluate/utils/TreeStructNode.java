package com.kgd.evaluate.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 树状节点实体（含展示+计算标识）
 */
@Data
public class TreeStructNode {
    /** 节点名称 */
    private String name;
    /** 节点权重（仅展示） */
    private Double weight;
    /** 节点得分（最终得分，无需乘权重） */
    private Double score;
    /** 是否参与总分计算（前端差异化展示） */
    private Boolean isCalculate;
    /** 子节点列表（递归嵌套） */
    private List<TreeStructNode> children = new ArrayList<>();

    // 快捷构造器
    public TreeStructNode(String name, Double weight, Double score, Boolean isCalculate) {
        this.name = name;
        this.weight = weight;
        this.score = score;
        this.isCalculate = isCalculate;
    }
}
