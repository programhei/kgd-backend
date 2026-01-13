package com.kgd.datasource.domain;

import java.math.BigDecimal;

import com.kgd.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kgd.common.annotation.Excel;

/**
 * 权值对象 weight
 *
 * @author kgd
 * @date 2025-11-13
 */
public class Weight extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 主观权重
     */
    @Excel(name = "主观权重")
    private BigDecimal subjectiveWeight;

    /**
     * 客观权重
     */
    @Excel(name = "客观权重")
    private BigDecimal objectiveWeight;

    /**
     * 综合权重
     */
    @Excel(name = "客观权重")
    private BigDecimal comprehensiveWeight;

    /**
     * 维度
     */
    @Excel(name = "维度")
    private String dimension;

    /**
     * 变量
     */
    @Excel(name = "变量")
    private String variable;

    /**
     * 变量值（指标）
     */
    @Excel(name = "变量值", readConverterExp = "指=标")
    private String variableValue;

    /**
     * 偏好系数k1
     */
    @Excel(name = "偏好系数k1")
    private BigDecimal coefficientK1;

    /**
     * 偏好系数k2
     */
    @Excel(name = "偏好系数k2")
    private BigDecimal coefficientK2;


    /**
     * 专家id
     */
    private String userId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setSubjectiveWeight(BigDecimal subjectiveWeight) {
        this.subjectiveWeight = subjectiveWeight;
    }

    public BigDecimal getSubjectiveWeight() {
        return subjectiveWeight;
    }

    public void setObjectiveWeight(BigDecimal objectiveWeight) {
        this.objectiveWeight = objectiveWeight;
    }

    public BigDecimal getObjectiveWeight() {
        return objectiveWeight;
    }

    public void setComprehensiveWeight(BigDecimal comprehensiveWeight) {
        this.comprehensiveWeight = comprehensiveWeight;
    }

    public BigDecimal getComprehensiveWeight() {
        return comprehensiveWeight;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getDimension() {
        return dimension;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariableValue(String variableValue) {
        this.variableValue = variableValue;
    }

    public String getVariableValue() {
        return variableValue;
    }

    public void setCoefficientK1(BigDecimal coefficientK1) {
        this.coefficientK1 = coefficientK1;
    }

    public BigDecimal getCoefficientK1() {
        return coefficientK1;
    }

    public void setCoefficientK2(BigDecimal coefficientK2) {
        this.coefficientK2 = coefficientK2;
    }

    public BigDecimal getCoefficientK2() {
        return coefficientK2;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("subjectiveWeight", getSubjectiveWeight())
                .append("objectiveWeight", getObjectiveWeight())
                .append("comprehensiveWeight", getComprehensiveWeight())
                .append("dimension", getDimension())
                .append("variable", getVariable())
                .append("variableValue", getVariableValue())
                .append("coefficientK1", getCoefficientK1())
                .append("coefficientK2", getCoefficientK2())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
