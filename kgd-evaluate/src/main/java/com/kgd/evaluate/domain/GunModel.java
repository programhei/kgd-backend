package com.kgd.evaluate.domain;

import com.kgd.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kgd.common.annotation.Excel;

/**
 * 航炮对象 gun_model
 *
 * @author kgd
 * @date 2025-11-05
 */
public class GunModel extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 射程（标准为N）
     */
    @Excel(name = "射程（标准为N）")
    private String shootRange;

    /**
     * 范围（标准为N）
     */
    @Excel(name = "范围（标准为N）")
    private String shootScope;

    /**
     * 数据类型
     */
    private String dataType;


    /**
     * 是否是标准数据
     */
    private String isStandard;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setShootRange(String shootRange) {
        this.shootRange = shootRange;
    }

    public String getShootRange() {
        return shootRange;
    }

    public void setShootScope(String shootScope) {
        this.shootScope = shootScope;
    }

    public String getShootScope() {
        return shootScope;
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
                .append("shootRange", getShootRange())
                .append("shootScope", getShootScope())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("dataType", getDataType())
                .append("isStandard", getIsStandard())
                .toString();
    }
}
