package com.kgd.evaluate.domain.vo;

import com.kgd.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class FlightInterfaceVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /* ---------- 时间 & 位置 ---------- */
    // 时间戳
    @Excel(name = "时间戳")
    private String timestamp;
    // 本机经度
    @Excel(name = "本机经度")
    private String longitude;
    // 本机纬度
    @Excel(name = "本机纬度")
    private String latitude;
    @Excel(name = "本机高度")
    private String altitude;        // 本机高度

    /* ---------- 姿态角 ---------- */
    @Excel(name = "本机航向角")
    private String heading;         // 本机航向角
    @Excel(name = "本机俯仰角")
    private String pitch;           // 本机俯仰角
    @Excel(name = "本机滚转角")
    private String roll;            // 本机滚转角

    /* ---------- 速度分量 ---------- */
    @Excel(name = "本机速度X轴")
    private String velX;            // 本机速度X轴
    @Excel(name = "本机速度Y轴")
    private String velY;            // 本机速度Y轴
    @Excel(name = "本机速度Z轴")
    private String velZ;            // 本机速度Z轴

    /* ---------- 角速度 ---------- */
    @Excel(name = "体轴滚转角速度")
    private String rollRate;        // 体轴滚转角速度
    @Excel(name = "体轴俯仰角速度")
    private String pitchRate;       // 体轴俯仰角速度
    @Excel(name = "体轴偏航角速度")
    private String yawRate;         // 体轴偏航角速度

    /* ---------- 气动 & 燃油 ---------- */
    @Excel(name = "本机马赫数")
    private String mach;            // 本机马赫数
    @Excel(name = "本机剩余弹量")
    private String remainingAmmo;      // 本机剩余弹量

    @Excel(name = "本机耗油率")
    private String specificFuelConsumption; // 本机耗油率

    /* ---------- 舵面位置 ---------- */
    @Excel(name = "左升降舵位置")
    private String leftElevator;    // 左升降舵位置
    @Excel(name = "右升降舵位置")
    private String rightElevator;   // 右升降舵位置
    @Excel(name = "左副翼位置")
    private String leftAileron;     // 左副翼位置
    @Excel(name = "右副翼位置")
    private String rightAileron;    // 右副翼位置

    @Excel(name = "方向舵位置")
    private String rudder;          // 方向舵位置

    @Excel(name = "迎角")
    private String attackAngle;          // 迎角

    @Excel(name = "侧滑角")
    private String sideslipAngle;          // 侧滑角


    @Excel(name = "法向过载")
    private String normalOverload;          // 法向过载

    @Excel(name = "燃油消耗")
    private String fuelConsumption;          // 燃油消耗

    @Excel(name = "油门位置")
    private String throttlePosition;          // 油门位置

    /**
     * 飞行数据
     */
    public Object getFlightColumnValue(String columnName) {
        switch (columnName) {
            case "本机经度":
                return longitude;
            case "本机纬度":
                return latitude;
            case "本机高度":
                return altitude;
            case "本机航向角":
                return heading;
            case "本机俯仰角":
                return pitch;
            case "本机滚转角":
                return roll;
            case "本机速度X轴":
                return velX;
            case "本机速度Y轴":
                return velY;
            case "本机速度Z轴":
                return velZ;
            case "体轴滚转角速度":
                return rollRate;
            case "体轴俯仰角速度":
                return pitchRate;
            case "体轴偏航角速度":
                return yawRate;
            case "本机马赫数":
                return mach;
            case "本机剩余弹量":
                return remainingAmmo;
            case "本机耗油率":
                return specificFuelConsumption;
            case "左升降舵位置":
                return leftElevator;
            case "右升降舵位置":
                return rightElevator;
            case "左副翼位置":
                return leftAileron;
            case "右副翼位置":
                return rightAileron;
            case "方向舵位置":
                return rudder;
            case "迎角":
                return attackAngle;
            case "侧滑角":
                return sideslipAngle;
            case "法向过载":
                return normalOverload;
            case "燃油消耗":
                return fuelConsumption;
            case "油门位置":
                return throttlePosition;
            default:
                throw new IllegalArgumentException("未知列名: " + columnName);
        }
    }
}
