package com.kgd.evaluate.domain.vo;

import com.kgd.common.annotation.Excel;
import lombok.Data;


import java.io.Serializable;

/**
 * 本机运动状态实体
 * 列序与 CSV 完全一致
 */
@Data
public class InterFaceVo implements Serializable {


    /**
     * 导弹经度 [deg]
     */
    @Excel(name = "导弹经度")
    private String missileLongitude;
    /**
     * 导弹纬度 [deg]
     */
    @Excel(name = "导弹纬度")
    private String missileLatitude;
    /**
     * 导弹高度 [m]
     */
    @Excel(name = "导弹高度")
    private String missileAltitude;
    /**
     * 导弹航向角 [deg]
     */
    @Excel(name = "导弹航向角")
    private String missileHeading;
    /**
     * 导弹俯仰角 [deg]
     */
    @Excel(name = "导弹俯仰角")
    private String missilePitch;
    /**
     * 导弹滚转角 [deg]
     */
    @Excel(name = "导弹滚转角")
    private String missileRoll;
    /**
     * 导弹速度X轴 [m/s]
     */
    @Excel(name = "导弹速度X轴")
    private String missileVelX;
    /**
     * 导弹速度Y轴 [m/s]
     */
    @Excel(name = "导弹速度Y轴")
    private String missileVelY;
    /**
     * 导弹速度Z轴 [m/s]
     */
    @Excel(name = "导弹速度Z轴")
    private String missileVelZ;
    /**
     * 加力状态 0-关 1-开
     */
    @Excel(name = "加力状态")
    private String afterburnerStatus;
    /**
     * 导弹导引头截获状态 0-未截获 1-截获
     */
    @Excel(name = "导弹导引头截获状态")
    private String seekerCaptureStatus;

    /**
     * 本机剩余弹量
     */
//    @Excel(name = "本机剩余弹量")
    private String remainingAmmunition;


    /**
     * 武器系统
     */
    public Object getMissileColumnValue(String columnName) {
        switch (columnName) {
            case "本机剩余弹量":
                return remainingAmmunition;
            case "导弹经度":
                return missileLongitude;
            case "导弹纬度":
                return missileLatitude;
            case "导弹高度":
                return missileAltitude;
            case "导弹航向角":
                return missileHeading;
            case "导弹俯仰角":
                return missilePitch;
            case "导弹滚转角":
                return missileRoll;
            case "导弹速度X轴":
                return missileVelX;
            case "导弹速度Y轴":
                return missileVelY;
            case "导弹速度Z轴":
                return missileVelZ;
            case "加力状态":
                return afterburnerStatus;
            case "导弹导引头截获状态":
                return seekerCaptureStatus;

            /* ---------- 后续继续往下追加即可 ---------- */
            default:
                throw new IllegalArgumentException("未知列名: " + columnName);
        }
    }
}
