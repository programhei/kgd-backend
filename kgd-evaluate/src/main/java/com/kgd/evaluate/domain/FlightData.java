package com.kgd.evaluate.domain;


import com.kgd.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kgd.common.annotation.Excel;

/**
 * 飞行数据对象 flight_data
 *
 * @author ruoyi
 * @date 2025-10-31
 */

public class FlightData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 日志唯一标识
     */
    private Long id;

    /**
     * 时间
     */
    @Excel(name = "时间")
    private String flightTime;

    /**
     * 速度
     */
    @Excel(name = "速度")
    private String flightSpeed;

    /**
     * 经度
     */
    @Excel(name = "经度")
    private String longitude;

    /**
     * 纬度
     */
    @Excel(name = "纬度")
    private String latitude;

    /**
     * 高度
     */
    @Excel(name = "高度")
    private String height;

    /**
     * 数据ID
     */
    @Excel(name = "数据ID")
    private String dataTitle;

    /**
     * 航向角
     */
    @Excel(name = "航向角")
    private String headingAngle;

    /**
     * 过载
     */
    @Excel(name = "过载")
    private String overload;

    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 是否是标准数据
     */
    private String isStandard;
    /**
     * 油耗
     */
    @Excel(name = "油耗")
    private String fuelConsumption;

    /**
     * 发动机耗油率
     */
    @Excel(name = "发动机耗油率")
    private String specificFuelConsumption;

    private String dimension;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setFlightTime(String flightTime) {
        this.flightTime = flightTime;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public void setFlightSpeed(String flightSpeed) {
        this.flightSpeed = flightSpeed;
    }

    public String getFlightSpeed() {
        return flightSpeed;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeight() {
        return height;
    }

    public void setDataTitle(String dataTitle) {
        this.dataTitle = dataTitle;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public void setHeadingAngle(String headingAngle) {
        this.headingAngle = headingAngle;
    }

    public String getHeadingAngle() {
        return headingAngle;
    }

    public void setOverload(String overload) {
        this.overload = overload;
    }

    public String getOverload() {
        return overload;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setIsStandard(String isStandard) {
        this.isStandard = isStandard;
    }

    public String getIsStandard() {
        return isStandard;
    }

    public void setSpecificFuelConsumption(String specificFuelConsumption) {
        this.specificFuelConsumption = specificFuelConsumption;
    }

    public String getSpecificFuelConsumption() {
        return specificFuelConsumption;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getDimension() {
        return dimension;
    }

    public void setFuelConsumption(String fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public String getFuelConsumption() {
        return fuelConsumption;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("flightTime", getFlightTime())
                .append("flightSpeed", getFlightSpeed())
                .append("longitude", getLongitude())
                .append("latitude", getLatitude())
                .append("height", getHeight())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("createTime", getCreateTime())
                .append("dataTitle", getDataTitle())
                .append("headingAngle", getHeadingAngle())
                .append("overload", getOverload())
                .append("dataType", getDataType())
                .append("isStandard", getIsStandard())
                .toString();
    }
}
