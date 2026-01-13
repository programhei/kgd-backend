package com.kgd.datasource.domain;

import com.kgd.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kgd.common.annotation.Excel;

/**
 * 实验记录对象 experimental_record
 *
 * @author kgd
 * @date 2025-11-06
 */
public class ExperimentalRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 实验ID，ID为三位数，百位表示第一维度，十位和个位表示第二维度，如1120X代表体系功能维度下的雷达仿真颗粒度的实验，0X代表的是实验次数
     */
    @Excel(name = "实验ID")
    private String experimentalId;

    /**
     * 实验次数
     */
    @Excel(name = "实验次数")
    private String experimentalCount;

    /**
     * 变量
     */
    @Excel(name = "变量")
    private String variable;

    /**
     * 变量值
     */
    @Excel(name = "变量值")
    private String variableValue;

    /**
     * 得分
     */
    @Excel(name = "仿真得分")
    private String score;

    /**
     * 维度，体系功能、性能准确度、想定合理性、软件质量、支持智能决策
     */
    private String dimension;

    /**
     * 击中数量
     */
    @Excel(name = "击中数量")
    private String hitCount;

    /**
     * 剩余弹数
     */
    @Excel(name = "剩余弹数")
    private String remainingAmmo;

    /**
     * 剩余油量
     */
    @Excel(name = "剩余油量")
    private String remainingFuel;

    /**
     * 平台ID
     */
    @Excel(name = "平台ID")
    private String platformId;

    /**
     * 总弹数
     */
    @Excel(name = "总弹数")
    private String totalAmmo;

    /**
     * 总油量
     */
    @Excel(name = "总油量")
    private String totalFuel;

    /**
     * 打偏数量
     */
    @Excel(name = "打偏数量")
    private String missCount;

    /**
     * 摧毁状态 0-未摧毁 1-已摧毁
     */
    @Excel(name = "摧毁状态", readConverterExp = "0=未摧毁,1=已摧毁")
    private String destroyStatus;

    /**
     * 规避数量
     */
    @Excel(name = "规避数量")
    private String evadeCount;

    /**
     * 角色
     */
    @Excel(name = "角色")
    private String role;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setExperimentalId(String experimentalId) {
        this.experimentalId = experimentalId;
    }

    public String getExperimentalId() {
        return experimentalId;
    }

    public void setExperimentalCount(String experimentalCount) {
        this.experimentalCount = experimentalCount;
    }

    public String getExperimentalCount() {
        return experimentalCount;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariableValue(String variableValue) {
        this.variableValue = variableValue;
    }

    public String getVariableValue() {
        return variableValue;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScore() {
        return score;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getDimension() {
        return dimension;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("experimentalId", getExperimentalId())
                .append("experimentalCount", getExperimentalCount())
                .append("variable", getVariable())
                .append("variableValue", getVariableValue())
                .append("score", getScore())
                .append("dimension", getDimension())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("dimension", getDimension())
                .toString();
    }

    public Double getScoreAsDouble() {
        // 空值/非法值统一当 0 处理
        try {
            return score == null || score.isEmpty() ? 0.0 : Double.parseDouble(score);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
