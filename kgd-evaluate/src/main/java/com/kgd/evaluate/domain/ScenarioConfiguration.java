package com.kgd.evaluate.domain;

import com.kgd.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kgd.common.annotation.Excel;

/**
 * 想定配置对象 scenario_configuration
 *
 * @author kgd
 * @date 2025-11-18
 */
public class ScenarioConfiguration extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 我方飞机数量 */
    @Excel(name = "我方飞机数量")
    private String myAircraftQuantity;

    /** 敌方飞机数量 */
    @Excel(name = "敌方飞机数量")
    private String enemyAircraftQuantity;

    /** 敌我标识 */
    @Excel(name = "敌我标识")
    private String identification;

    /** 我方飞机初始化 */
    @Excel(name = "我方飞机初始化")
    private String initialMyAircraft;

    /** 敌方飞机初始化 */
    @Excel(name = "敌方飞机初始化")
    private String initialEnemyAircraft;

    /** 航炮数量 */
    @Excel(name = "航炮数量")
    private String aircraftGunsQuantity;

    /** 近距导弹数量 */
    @Excel(name = "近距导弹数量")
    private String shortRangeMissilesQuantity;

    /** 中远距导弹数量 */
    @Excel(name = "中远距导弹数量")
    private String mediumLongRangeMissilesQuantity;

    /** 近距空战想定 */
    @Excel(name = "近距空战想定")
    private String shortRangeCombat;

    /** 中远距空战想定 */
    @Excel(name = "中远距空战想定")
    private String mediumLongRangeCombat;

    /** 态势分类合理性 */
    @Excel(name = "态势分类合理性")
    private String classificationRationality;

    /** 基本编队 */
    @Excel(name = "基本编队")
    private String basicFormation;

    /** 视距内空战编队 */
    @Excel(name = "视距内空战编队")
    private String shortRangeFormation;

    /** 中距空战编队 */
    @Excel(name = "中距空战编队")
    private String mediumRangeFormation;

    /** 远距空战编队 */
    @Excel(name = "远距空战编队")
    private String longRangeFormation;

    /** 是否是标准数据 */
    @Excel(name = "是否是标准数据")
    private String isStandard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMyAircraftQuantity() {
        return myAircraftQuantity;
    }

    public void setMyAircraftQuantity(String myAircraftQuantity) {
        this.myAircraftQuantity = myAircraftQuantity;
    }

    public String getEnemyAircraftQuantity() {
        return enemyAircraftQuantity;
    }

    public void setEnemyAircraftQuantity(String enemyAircraftQuantity) {
        this.enemyAircraftQuantity = enemyAircraftQuantity;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getInitialMyAircraft() {
        return initialMyAircraft;
    }

    public void setInitialMyAircraft(String initialMyAircraft) {
        this.initialMyAircraft = initialMyAircraft;
    }

    public String getInitialEnemyAircraft() {
        return initialEnemyAircraft;
    }

    public void setInitialEnemyAircraft(String initialEnemyAircraft) {
        this.initialEnemyAircraft = initialEnemyAircraft;
    }

    public String getAircraftGunsQuantity() {
        return aircraftGunsQuantity;
    }

    public void setAircraftGunsQuantity(String aircraftGunsQuantity) {
        this.aircraftGunsQuantity = aircraftGunsQuantity;
    }

    public String getShortRangeMissilesQuantity() {
        return shortRangeMissilesQuantity;
    }

    public void setShortRangeMissilesQuantity(String shortRangeMissilesQuantity) {
        this.shortRangeMissilesQuantity = shortRangeMissilesQuantity;
    }

    public String getMediumLongRangeMissilesQuantity() {
        return mediumLongRangeMissilesQuantity;
    }

    public void setMediumLongRangeMissilesQuantity(String mediumLongRangeMissilesQuantity) {
        this.mediumLongRangeMissilesQuantity = mediumLongRangeMissilesQuantity;
    }

    public String getShortRangeCombat() {
        return shortRangeCombat;
    }

    public void setShortRangeCombat(String shortRangeCombat) {
        this.shortRangeCombat = shortRangeCombat;
    }

    public String getMediumLongRangeCombat() {
        return mediumLongRangeCombat;
    }

    public void setMediumLongRangeCombat(String mediumLongRangeCombat) {
        this.mediumLongRangeCombat = mediumLongRangeCombat;
    }

    public String getClassificationRationality() {
        return classificationRationality;
    }

    public void setClassificationRationality(String classificationRationality) {
        this.classificationRationality = classificationRationality;
    }

    public String getBasicFormation() {
        return basicFormation;
    }

    public void setBasicFormation(String basicFormation) {
        this.basicFormation = basicFormation;
    }

    public String getShortRangeFormation() {
        return shortRangeFormation;
    }

    public void setShortRangeFormation(String shortRangeFormation) {
        this.shortRangeFormation = shortRangeFormation;
    }

    public String getMediumRangeFormation() {
        return mediumRangeFormation;
    }

    public void setMediumRangeFormation(String mediumRangeFormation) {
        this.mediumRangeFormation = mediumRangeFormation;
    }

    public String getLongRangeFormation() {
        return longRangeFormation;
    }

    public void setLongRangeFormation(String longRangeFormation) {
        this.longRangeFormation = longRangeFormation;
    }

    public String getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(String isStandard) {
        this.isStandard = isStandard;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("myAircraftQuantity", getMyAircraftQuantity())
                .append("enemyAircraftQuantity", getEnemyAircraftQuantity())
                .append("identification", getIdentification())
                .append("initialMyAircraft", getInitialMyAircraft())
                .append("initialEnemyAircraft", getInitialEnemyAircraft())
                .append("aircraftGunsQuantity", getAircraftGunsQuantity())
                .append("shortRangeMissilesQuantity", getShortRangeMissilesQuantity())
                .append("mediumLongRangeMissilesQuantity", getMediumLongRangeMissilesQuantity())
                .append("shortRangeCombat", getShortRangeCombat())
                .append("mediumLongRangeCombat", getMediumLongRangeCombat())
                .append("classificationRationality", getClassificationRationality())
                .append("basicFormation", getBasicFormation())
                .append("shortRangeFormation", getShortRangeFormation())
                .append("mediumRangeFormation", getMediumRangeFormation())
                .append("longRangeFormation", getLongRangeFormation())
                .append("isStandard", getIsStandard())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }

}
