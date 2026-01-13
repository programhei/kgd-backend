package com.kgd.evaluate.domain;

import com.kgd.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kgd.common.annotation.Excel;

/**
 * 雷达模型对象 radar_model
 *
 * @author ruoyi
 * @date 2025-11-18
 */
public class RadarModel extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 探测距离（5㎡目标）
     */
    @Excel(name = "探测距离")
    private String detectionDistance;

    /**
     * 探测范围（方位，度）
     */
    @Excel(name = "探测范围")
    private String detectionRange;

    /**
     * 数据类型
     */
    @Excel(name = "数据类型")
    private String dataType;

    /**
     * 是否是标准数据
     */
    @Excel(name = "是否是标准数据")
    private String isStandard;

    @Excel(name = "最大俯仰扫描范围准确度")
    private String maxScanningRange;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDetectionDistance(String detectionDistance) {
        this.detectionDistance = detectionDistance;
    }

    public String getDetectionDistance() {
        return detectionDistance;
    }

    public void setDetectionRange(String detectionRange) {
        this.detectionRange = detectionRange;
    }

    public String getDetectionRange() {
        return detectionRange;
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

    public void setMaxScanningRange(String maxScanningRange) {
        this.maxScanningRange = maxScanningRange;
    }

    public String getMaxScanningRange() {
        return maxScanningRange;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("detectionDistance", getDetectionDistance())
                .append("detectionRange", getDetectionRange())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("dataType", getDataType())
                .append("isStandard", getIsStandard())
                .toString();
    }
}
