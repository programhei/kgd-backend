package com.kgd.evaluate.domain;

import java.math.BigDecimal;

import com.kgd.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kgd.common.annotation.Excel;

/**
 * 评估分数记录对象 evaluate_score
 *
 * @author kgd
 * @date 2025-11-20
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class EvaluateScore extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 分数
     */
    @Excel(name = "分数")
    private BigDecimal score;

    /**
     * 分数类型
     */
    @Excel(name = "分数类型")
    private String scoreType;

    /**
     * 得分原因
     */
    @Excel(name = "得分原因")
    private String reason;

    /**
     * 总分数
     */
    @Excel(name = "总分数")
    private BigDecimal totalScore;

    @Excel(name = "总分数")
    private double weight;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    public String getScoreType() {
        return scoreType;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("score", getScore())
                .append("scoreType", getScoreType())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("reason", getReason())
                .append("totalScore", getTotalScore())
                .toString();
    }
}
