package com.kgd.datasource.domain;

import com.kgd.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kgd.common.annotation.Excel;

/**
 * 实验ID对应对象 experimental_id_correspondence
 * 
 * @author kgd
 * @date 2025-11-06
 */
public class ExperimentalIdCorrespondence extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 数字ID */
    @Excel(name = "数字ID")
    private String digitalId;

    /** 维度等级 */
    @Excel(name = "维度等级")
    private String dimensionLevel;

    /** 维度名称 */
    @Excel(name = "维度名称")
    private String dimension;

    /** 变量值 */
    @Excel(name = "变量值")
    private String variableValue;

    /** 父级Id */
    @Excel(name = "父级Id")
    private String parentId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setDigitalId(String digitalId) 
    {
        this.digitalId = digitalId;
    }

    public String getDigitalId() 
    {
        return digitalId;
    }

    public void setDimensionLevel(String dimensionLevel) 
    {
        this.dimensionLevel = dimensionLevel;
    }

    public String getDimensionLevel() 
    {
        return dimensionLevel;
    }

    public void setDimension(String dimension) 
    {
        this.dimension = dimension;
    }

    public String getDimension() 
    {
        return dimension;
    }

    public void setVariableValue(String variableValue) 
    {
        this.variableValue = variableValue;
    }

    public String getVariableValue() 
    {
        return variableValue;
    }

    public void setParentId(String parentId) 
    {
        this.parentId = parentId;
    }

    public String getParentId() 
    {
        return parentId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("digitalId", getDigitalId())
            .append("dimensionLevel", getDimensionLevel())
            .append("dimension", getDimension())
            .append("variableValue", getVariableValue())
            .append("remark", getRemark())
            .append("parentId", getParentId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
