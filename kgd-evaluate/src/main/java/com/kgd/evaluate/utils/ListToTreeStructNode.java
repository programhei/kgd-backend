package com.kgd.evaluate.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:
 * @description:
 * @date: 2025/12/23
 * @version: 1.0
 */
@Data
public class ListToTreeStructNode {
    private String scoreType;
    private String reason;
    /** 节点名称 */
    private String name;
    /** 节点权重（仅展示） */
    private Double weight;
    /** 节点得分（最终得分，无需乘权重） */
    private Double score;
    /** 是否参与总分计算（前端差异化展示） */
    private Boolean isCalculate;
    /** 子节点列表（递归嵌套） */
    private List<ListToTreeStructNode> children = new ArrayList<>();

    // 快捷构造器
    public ListToTreeStructNode(String name, Double weight, Double score, Boolean isCalculate) {
        this.name = name;
        this.weight = weight;
        this.score = score;
        this.isCalculate = isCalculate;
    }
    public ListToTreeStructNode(String scoreType, String reason, Double weight, Double score, Boolean isCalculate) {
        this.scoreType = scoreType;
        this.reason = reason;
        this.weight = weight;
        this.score = score;
        this.isCalculate = isCalculate;
    }
}
