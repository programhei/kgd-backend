package com.kgd.evaluate.domain;

import com.kgd.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kgd.common.annotation.Excel;

/**
 * 飞行模型性能配置对象 flight_model
 *
 * @author kgd
 * @date 2025-11-18
 */
public class FlightModel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 加速度 */
    @Excel(name = "加速度")
    private String acceleration;

    /** 最大速度 */
    @Excel(name = "最大速度")
    private String maximumSpeed;

    /** 最大过载 */
    @Excel(name = "最大过载")
    private String maximumOverload;

    /** 耗油率 */
    @Excel(name = "耗油率")
    private String fuelRate;

    /** 数据类型 */
    @Excel(name = "数据类型")
    private String dataType;

    /** 是否是标准数据 */
    @Excel(name = "是否是标准数据")
    private String isStandard;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setAcceleration(String acceleration)
    {
        this.acceleration = acceleration;
    }

    public String getAcceleration()
    {
        return acceleration;
    }

    public void setMaximumSpeed(String maximumSpeed)
    {
        this.maximumSpeed = maximumSpeed;
    }

    public String getMaximumSpeed()
    {
        return maximumSpeed;
    }

    public void setMaximumOverload(String maximumOverload)
    {
        this.maximumOverload = maximumOverload;
    }

    public String getMaximumOverload()
    {
        return maximumOverload;
    }

    public void setFuelRate(String fuelRate)
    {
        this.fuelRate = fuelRate;
    }

    public String getFuelRate()
    {
        return fuelRate;
    }

    public void setDataType(String dataType)
    {
        this.dataType = dataType;
    }

    public String getDataType()
    {
        return dataType;
    }

    public void setIsStandard(String isStandard)
    {
        this.isStandard = isStandard;
    }

    public String getIsStandard()
    {
        return isStandard;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("acceleration", getAcceleration())
                .append("maximumSpeed", getMaximumSpeed())
                .append("maximumOverload", getMaximumOverload())
                .append("fuelRate", getFuelRate())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .append("createBy", getCreateBy())
                .append("dataType", getDataType())
                .append("isStandard", getIsStandard())
                .toString();
    }
}
