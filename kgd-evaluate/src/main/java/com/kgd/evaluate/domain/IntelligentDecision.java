package com.kgd.evaluate.domain;

import com.kgd.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kgd.common.annotation.Excel;

/**
 * 支持智能决策模型生成与评估对象 intelligent_decision
 *
 * @author kgd
 * @date 2025-11-10
 */
public class IntelligentDecision extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 数据类型
     */
    @Excel(name = "数据类型")
    private String dataType;

    /**
     * 数据
     */
    @Excel(name = "数据")
    private String dataContent;

    /**
     * 值类型
     */
    @Excel(name = "值类型")
    private String valueType;

    /**
     * 接口含义
     */
    @Excel(name = "接口含义")
    private String interfaceMeaning;

    /**
     * 重要等级
     */
    @Excel(name = "重要等级")
    private String importanceLevel;

    /**
     * 数据种类
     */
    private String dataKind;
    /**
     * 数据种类
     */
    @Excel(name = "备注")
    private String remark;
    /**
     * 序号
     */
    @Excel(name = "序号")
    private String serialNumber;

    /**
     * 接口中是否缺失
     */
    @Excel(name = "是否缺失")
    private String isMissing;

    /**
     * 格式是否正确
     */
    @Excel(name = "格式是否正确")
    private String isFormatCorrect;

    /**
     * 是否是输入接口
     */
    @Excel(name = "是否是输入接口")
    private String isInput;

    /**
     * 接口维度
     */
    @Excel(name = "接口维度")
    private String dimension;

    /**
     * 接口类型
     */
    @Excel(name = "接口类型")
    private String interfaceType;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getValueType() {
        return valueType;
    }

    public void setInterfaceMeaning(String interfaceMeaning) {
        this.interfaceMeaning = interfaceMeaning;
    }

    public String getInterfaceMeaning() {
        return interfaceMeaning;
    }

    public void setImportanceLevel(String importanceLevel) {
        this.importanceLevel = importanceLevel;
    }

    public String getImportanceLevel() {
        return importanceLevel;
    }

    public void setDataKind(String dataKind) {
        this.dataKind = dataKind;
    }

    public String getDataKind() {
        return dataKind;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setIsMissing(String isMissing) {
        this.isMissing = isMissing;
    }

    public String getIsMissing() {
        return isMissing;
    }

    public void setIsFormatCorrect(String isFormatCorrect) {
        this.isFormatCorrect = isFormatCorrect;
    }

    public String getIsFormatCorrect() {
        return isFormatCorrect;
    }

    public void setIsInput(String isInput) {
        this.isInput = isInput;
    }

    public String getIsInput() {
        return isInput;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getDimension() {
        return dimension;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("dataType", getDataType())
                .append("dataContent", getDataContent())
                .append("valueType", getValueType())
                .append("interfaceMeaning", getInterfaceMeaning())
                .append("importanceLevel", getImportanceLevel())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("dataKind", getDataKind())
                .append("remark", getRemark())
                .append("serialNumber", getSerialNumber())
                .append("isMissing", getIsMissing())
                .append("isFormatCorrect", getIsFormatCorrect())
                .append("isInput", getIsInput())
                .append("interfaceType", getInterfaceType())
                .append("dimension", getDimension())
                .toString();
    }
}
