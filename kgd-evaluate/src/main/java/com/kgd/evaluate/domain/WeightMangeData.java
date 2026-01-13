package com.kgd.evaluate.domain;


import com.kgd.common.annotation.Excel;
import com.kgd.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 权值数据对象 weight_data
 *
 * @author ruoyi
 * @date 2025-12-01
 */

public class WeightMangeData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Excel(name = "主键ID")
    private Long id;

    /**
     * 组ID
     */
    @Excel(name = "组ID")
    private Long groupId;

    /**
     * 权值名称
     */
    @Excel(name = "维度名称")
    private String name;

    /**
     * 父节点ID
     */
    @Excel(name = "父节点ID")
    private Long parentId;

    /**
     * 层级：1-一级维度，2-二级维度，3-三级维度
     */
    @Excel(name = "层级")
    private Long level;

    /**
     * 权重值，如0.25、0.5等
     */
    @Excel(name = "权值")
    private double weight;

    /**
     * 树路径
     */
    private String treeView;


    /**
     * 完整路径
     */
    @Excel(name = "完整路径")
    private String fullPath;

    /**
     * 权重值，如0.25、0.5等
     */
    @Excel(name = "分数")
    private double score;

    /**
     * 同级排序顺序
     */
    @Excel(name = "同级排序顺序")
    private int sortOrder;


    /**
     * 类型 0为主观 1为客观
     */
    private int type;


    private double weightKg;

    private double weightZg;

    private double weightZh;

    public double getWeightZh() {
        return weightZh;
    }

    public void setWeightZh(double weightZh) {
        this.weightZh = weightZh;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(double weightKg) {
        this.weightKg = weightKg;
    }

    public double getWeightZg() {
        return weightZg;
    }

    public void setWeightZg(double weightZg) {
        this.weightZg = weightZg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    List<WeightMangeData> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getTreeView() {
        return treeView;
    }

    public void setTreeView(String treeView) {
        this.treeView = treeView;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<WeightMangeData> getChildren() {
        return children;
    }

    public void setChildren(List<WeightMangeData> children) {
        this.children = children;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "WeightMangeData{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", level=" + level +
                ", weight=" + weight +
                ", treeView='" + treeView + '\'' +
                ", fullPath='" + fullPath + '\'' +
                ", sortOrder=" + sortOrder +
                ", score=" + score +
                ", WeightMangeData=" + children +
                '}';
    }
}
