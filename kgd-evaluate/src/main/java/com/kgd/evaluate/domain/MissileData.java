package com.kgd.evaluate.domain;


import com.kgd.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kgd.common.annotation.Excel;

/**
 * 导弹数据对象 missile_data
 *
 * @author kgd
 * @date 2025-10-31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MissileData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;

    /**
     * 时间
     */
    @Excel(name = "时间")
    private String flightTime;

    /**
     * X（北向位置）
     */
    @Excel(name = "X（北向位置）")
    private String x;

    /**
     * Z（东向位置）
     */
    @Excel(name = "Z（东向位置）")
    private String z;

    /**
     * Y（天向位置）
     */
    @Excel(name = "Y（天向位置）")
    private String y;

    /**
     * 数据ID
     */
    @Excel(name = "数据ID")
    private String dataTitle;

    /**
     * 加力状态
     */
    @Excel(name = "加力状态")
    private String enhancedState;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 是否是标准数据
     */
    private String isStandard;

    /**
     * 数据类型
     */
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

    public void setX(String x) {
        this.x = x;
    }

    public String getX() {
        return x;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public String getZ() {
        return z;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getY() {
        return y;
    }

    public void setDataTitle(String dataTitle) {
        this.dataTitle = dataTitle;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public void setEnhancedState(String enhancedState) {
        this.enhancedState = enhancedState;
    }

    public String getEnhancedState() {
        return enhancedState;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("flightTime", getFlightTime())
                .append("x", getX())
                .append("z", getZ())
                .append("y", getY())
                .append("dataTitle", getDataTitle())
                .append("enhancedState", getEnhancedState())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .append("dataType", getDataType())
                .append("isStandard", getIsStandard())
                .toString();
    }
}
