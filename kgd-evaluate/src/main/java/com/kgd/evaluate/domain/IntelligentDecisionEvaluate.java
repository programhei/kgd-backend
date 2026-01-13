package com.kgd.evaluate.domain;

import com.kgd.common.annotation.Excel;
import com.kgd.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 支持智能决策模型生成与评估-评估 intelligent_decision_evaluate
 *
 * @author kgd
 * @date 2025-11-10
 */
public class IntelligentDecisionEvaluate extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /** 地形创建 **/
    @Excel(name = "地形创建")
    private String createTerrain;

    /** 视角设计 **/
    @Excel(name = "视角设计")
    private String perspectiveDesign;

    /** 飞行轨迹视景显示 **/
    @Excel(name = "飞行轨迹视景显示")
    private String flightTrajectoryVisualization;

    /** 飞行姿态显示 **/
    @Excel(name = "飞行姿态显示")
    private String flightAttitude;

    /** 仿真对抗胜负评估 **/
    @Excel(name = "仿真对抗胜负评估")
    private String winLossEvaluation;

    /** 仿真对抗效能评估 **/
    @Excel(name = "仿真对抗效能评估")
    private String effectivenessEvaluation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateTerrain() {
        return createTerrain;
    }

    public void setCreateTerrain(String createTerrain) {
        this.createTerrain = createTerrain;
    }

    public String getPerspectiveDesign() {
        return perspectiveDesign;
    }

    public void setPerspectiveDesign(String perspectiveDesign) {
        this.perspectiveDesign = perspectiveDesign;
    }

    public String getFlightTrajectoryVisualization() {
        return flightTrajectoryVisualization;
    }

    public void setFlightTrajectoryVisualization(String flightTrajectoryVisualization) {
        this.flightTrajectoryVisualization = flightTrajectoryVisualization;
    }

    public String getFlightAttitude() {
        return flightAttitude;
    }

    public void setFlightAttitude(String flightAttitude) {
        this.flightAttitude = flightAttitude;
    }

    public String getWinLossEvaluation() {
        return winLossEvaluation;
    }

    public void setWinLossEvaluation(String winLossEvaluation) {
        this.winLossEvaluation = winLossEvaluation;
    }

    public String getEffectivenessEvaluation() {
        return effectivenessEvaluation;
    }

    public void setEffectivenessEvaluation(String effectivenessEvaluation) {
        this.effectivenessEvaluation = effectivenessEvaluation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("createTerrain", getCreateTerrain())
                .append("perspectiveDesign", getPerspectiveDesign())
                .append("flightTrajectoryVisualization", getFlightTrajectoryVisualization())
                .append("flightAttitude", getFlightAttitude())
                .append("winLossEvaluation", getWinLossEvaluation())
                .append("effectivenessEvaluation", getEffectivenessEvaluation())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
