package com.kgd.datasource.domain.vo;

import com.kgd.common.annotation.Excel;
import com.kgd.common.core.domain.BaseEntity;


// 最大过载

public class PerformanceAccuracyExcelVo extends BaseEntity {
    // id
    @Excel(name = "id")
    private int id;

    // 维度
    @Excel(name = "维度")
    private String dimension;

    // 类型
    @Excel(name = "类型")
    private String type;

    // 变量
    @Excel(name = "变量")
    private String variable;

    // 变量值
    @Excel(name = "变量值")
    private String variableValue;

    // 备注
    @Excel(name = "备注")
    private String remarks;

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getVariableValue() {
        return variableValue;
    }

    public void setVariableValue(String variableValue) {
        this.variableValue = variableValue;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "PerformanceAccuracyExcelVo{" +
                "id='" + id + '\'' +
                ", dimension='" + dimension + '\'' +
                ", type='" + type + '\'' +
                ", variable='" + variable + '\'' +
                ", variableValue='" + variableValue + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}