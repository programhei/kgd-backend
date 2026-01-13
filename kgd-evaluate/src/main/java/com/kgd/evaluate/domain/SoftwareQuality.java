package com.kgd.evaluate.domain;

import com.kgd.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kgd.common.annotation.Excel;

/**
 * 软件质量对象 software_quality
 *
 * @date 2025-11-18
 */
public class SoftwareQuality extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 时间响应特性 */
    @Excel(name = "时间响应特性")
    private String timeResponseCharact;

    /** 资源利用率 */
    @Excel(name = "资源利用率")
    private String resourceUtilization;

    /** 代码注释率（%） */
    @Excel(name = "代码注释率（%）")
    private String codeCommentRate;

    /** 代码规范率（%） */
    @Excel(name = "代码规范率（%）")
    private String codeStandardRate;

    /** 连续运行时间 */
    @Excel(name = "连续运行时间")
    private String continuousRunTime;

    /** 接口容错率 */
    @Excel(name = "接口容错率")
    private String interfaceFaultTolerance;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setTimeResponseCharact(String timeResponseCharact)
    {
        this.timeResponseCharact = timeResponseCharact;
    }

    public String getTimeResponseCharact()
    {
        return timeResponseCharact;
    }

    public void setResourceUtilization(String resourceUtilization)
    {
        this.resourceUtilization = resourceUtilization;
    }

    public String getResourceUtilization()
    {
        return resourceUtilization;
    }

    public void setCodeCommentRate(String codeCommentRate)
    {
        this.codeCommentRate = codeCommentRate;
    }

    public String getCodeCommentRate()
    {
        return codeCommentRate;
    }

    public void setCodeStandardRate(String codeStandardRate)
    {
        this.codeStandardRate = codeStandardRate;
    }

    public String getCodeStandardRate()
    {
        return codeStandardRate;
    }

    public void setContinuousRunTime(String continuousRunTime)
    {
        this.continuousRunTime = continuousRunTime;
    }

    public String getContinuousRunTime()
    {
        return continuousRunTime;
    }

    public void setInterfaceFaultTolerance(String interfaceFaultTolerance)
    {
        this.interfaceFaultTolerance = interfaceFaultTolerance;
    }

    public String getInterfaceFaultTolerance()
    {
        return interfaceFaultTolerance;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("timeResponseCharact", getTimeResponseCharact())
                .append("resourceUtilization", getResourceUtilization())
                .append("codeCommentRate", getCodeCommentRate())
                .append("codeStandardRate", getCodeStandardRate())
                .append("continuousRunTime", getContinuousRunTime())
                .append("interfaceFaultTolerance", getInterfaceFaultTolerance())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
