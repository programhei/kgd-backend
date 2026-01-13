package com.kgd.evaluate.domain;


import com.kgd.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kgd.common.annotation.Excel;

/**
 * 导弹数据对象 radar_data
 *
 * @author ruoyi
 * @date 2025-10-31
 */
@EqualsAndHashCode(callSuper = true)
@Setter
@Data
public class RadarData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 数据ID
     */
    @Excel(name = "数据ID")
    private String dataTitle;

    /**
     * 测试次数
     */
    @Excel(name = "测试次数")
    private Long testCount;

    /**
     * 发现距离
     */
    @Excel(name = "发现距离")
    private String discoveryDistance;

    /**
     * 时间
     */
    @Excel(name = "时间")
    private String trackTime;

    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 数据类型
     */
    private String dimension;

    /**
     * 是否是标准数据
     */
    private String isStandard;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("dataTitle", getDataTitle())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .append("testCount", getTestCount())
                .append("discoveryDistance", getDiscoveryDistance())
                .append("trackTime", getTrackTime())
                .append("dataType", getDataType())
                .append("isStandard", getIsStandard())
                .toString();
    }
}
