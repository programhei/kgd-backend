package com.kgd.evaluate.domain;

import com.kgd.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 仿真系统性能准确度算法对象 simulation_algorithm
 */
public class SimulationAlgorithm extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 算法标题 */
    private String algorithmTitle;

    /** 算法代码 */
    private String algorithmCode;

    /** 算法类型 */
    private String algorithmType;

    /** 算法详情 */
    private String description;

    /** 测试用例描述 */
    private String testCaseDesc;

    /** 指标参数计算方法 */
    private String indicatorMethod;

    /** 计算公式 */
    private String formula;

    /** 目标值说明 */
    private String targetValueDesc;

    /** 数据要求 */
    private String dataRequirements;

    /** 状态（0正常 1停用） */
    private String status;

    /** 排序 */
    private Integer sortOrder;

    /** 备注 */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlgorithmTitle() {
        return algorithmTitle;
    }

    public void setAlgorithmTitle(String algorithmTitle) {
        this.algorithmTitle = algorithmTitle;
    }

    public String getAlgorithmCode() {
        return algorithmCode;
    }

    public void setAlgorithmCode(String algorithmCode) {
        this.algorithmCode = algorithmCode;
    }

    public String getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTestCaseDesc() {
        return testCaseDesc;
    }

    public void setTestCaseDesc(String testCaseDesc) {
        this.testCaseDesc = testCaseDesc;
    }

    public String getIndicatorMethod() {
        return indicatorMethod;
    }

    public void setIndicatorMethod(String indicatorMethod) {
        this.indicatorMethod = indicatorMethod;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getTargetValueDesc() {
        return targetValueDesc;
    }

    public void setTargetValueDesc(String targetValueDesc) {
        this.targetValueDesc = targetValueDesc;
    }

    public String getDataRequirements() {
        return dataRequirements;
    }

    public void setDataRequirements(String dataRequirements) {
        this.dataRequirements = dataRequirements;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("algorithmTitle", getAlgorithmTitle())
            .append("algorithmCode", getAlgorithmCode())
            .append("algorithmType", getAlgorithmType())
            .append("description", getDescription())
            .append("testCaseDesc", getTestCaseDesc())
            .append("indicatorMethod", getIndicatorMethod())
            .append("formula", getFormula())
            .append("targetValueDesc", getTargetValueDesc())
            .append("dataRequirements", getDataRequirements())
            .append("status", getStatus())
            .append("sortOrder", getSortOrder())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

