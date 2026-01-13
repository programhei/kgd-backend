package com.kgd.evaluate.domain;

import com.kgd.common.core.domain.BaseEntity;

import java.util.List;

public class EvaluateTreeScore extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    private Long parentId;

    /**
     * 分数
     */
//    @Excel(name = "分数")
    private double score;

    /**
     * 分数类型
     */
//    @Excel(name = "分数类型")
    private String scoreType;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 得分原因
     */
//    @Excel(name = "得分原因")
    private String reason;

//    @Excel(name = "权值")
    private double weight;

    private double allSore;

    public double getAllSore() {
        return allSore;
    }

    public void setAllSore(double allSore) {
        this.allSore = allSore;
    }

    private List<EvaluateTreeScore> evaluateTreeScoresList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<EvaluateTreeScore> getEvaluateTreeScoresList() {
        return evaluateTreeScoresList;
    }

    public void setEvaluateTreeScoresList(List<EvaluateTreeScore> evaluateTreeScoresList) {
        this.evaluateTreeScoresList = evaluateTreeScoresList;
    }
}
