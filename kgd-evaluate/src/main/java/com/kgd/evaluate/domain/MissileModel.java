package com.kgd.evaluate.domain;

import com.kgd.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kgd.common.annotation.Excel;

/**
 * 导弹模型对象 missile_model
 *
 * @author kgd
 * @date 2025-11-18
 */
public class MissileModel extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 射程（11km高度，km）
     */
    @Excel(name = "射程")
    private String missileRange;

    /**
     * 导引头截获距离（km）
     */
    @Excel(name = "导引头截获距离")
    private String interceptDistance;

    /**
     * 杀伤范围
     */
    @Excel(name = "杀伤范围")
    private String killingRange;
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



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setMissileRange(String missileRange) {
        this.missileRange = missileRange;
    }

    public String getMissileRange() {
        return missileRange;
    }


    public void setInterceptDistance(String interceptDistance) {
        this.interceptDistance = interceptDistance;
    }

    public String getInterceptDistance() {
        return interceptDistance;
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

    public void setKillingRange(String killingRange) {
        this.killingRange = killingRange;
    }

    public String getKillingRange() {
        return killingRange;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("missileRange", getMissileRange())
                .append("interceptDistance", getInterceptDistance())
                .append("killingRange", getKillingRange())
                .append("dataType", getDataType())
                .append("isStandard", getIsStandard())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())

                .toString();
    }
}
