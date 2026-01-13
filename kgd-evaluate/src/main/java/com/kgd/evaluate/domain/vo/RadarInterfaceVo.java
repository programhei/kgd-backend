package com.kgd.evaluate.domain.vo;

import com.kgd.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class RadarInterfaceVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "雷达开关机状态")
    private String radarSwitchStatus;       // 雷达开机状态

    @Excel(name = "雷达扫描方式")
    private String radarScanningMethod;       // 雷达扫描方式

    @Excel(name = "俯仰扫描范围")
    private String elevationScanningRange;       // 俯仰扫描范围

    @Excel(name = "方位扫描范围")
    private String directionalScanningRange;       // 方位扫描范围

    /**
     * 跟踪目标数量（当前帧目标总数）
     */
    @Excel(name = "跟踪目标数量")
    private String targetCount;

    /**
     * 目标批号（唯一标识）
     */
    @Excel(name = "目标批号")
    private String targetBatchId;

    /**
     * 雷达对该目标状态（0-丢失 1-跟踪 2-捕获）
     */
    @Excel(name = "雷达对该目标状态")
    private String radarStatus;

    /**
     * 目标距离 [m]
     */
    @Excel(name = "目标距离")
    private String targetDistance;

    /**
     * 目标方位角 [deg]
     */
    @Excel(name = "目标方位角")
    private String targetAzimuth;

    /**
     * 目标俯仰角 [deg]
     */
    @Excel(name = "目标俯仰角")
    private String targetElevation;

    /**
     * 目标径向速度 [m/s]
     */
    @Excel(name = "目标径向速度")
    private String targetRadialVel;

    /**
     * 目标速度X轴 [m/s]
     */
    @Excel(name = "目标速度X轴")
    private String targetVelX;

    /**
     * 目标速度Y轴 [m/s]
     */
    @Excel(name = "目标速度Y轴")
    private String targetVelY;

    /**
     * 目标速度Z轴 [m/s]
     */
    @Excel(name = "目标速度Z轴")
    private String targetVelZ;

    /**
     * 目标 RCS [m²]
     */
    @Excel(name = "目标RCS")
    private String targetRCS;

    /**
     * 飞行数据
     */
    /**
     * 武器系统
     */
    public Object getRadarColumnValue(String columnName) {
        switch (columnName) {
            /* ---------- 原雷达工作状态 ---------- */
            case "雷达开关机状态":
                return radarSwitchStatus;
            case "雷达扫描方式":
                return radarScanningMethod;
            case "俯仰扫描范围":
                return elevationScanningRange;
            case "方位扫描范围":
                return directionalScanningRange;

            /* ---------- 雷达目标信息（新增）---------- */
            case "跟踪目标数量":
                return targetCount;
            case "目标批号":
                return targetBatchId;
            case "雷达对该目标状态":
                return radarStatus;
            case "目标距离":
                return targetDistance;
            case "目标方位角":
                return targetAzimuth;
            case "目标俯仰角":
                return targetElevation;
            case "目标径向速度":
                return targetRadialVel;
            case "目标速度X轴":
                return targetVelX;
            case "目标速度Y轴":
                return targetVelY;
            case "目标速度Z轴":
                return targetVelZ;
            case "目标RCS":
                return targetRCS;

            /* ---------- 后续继续往下追加即可 ---------- */
            default:
                throw new IllegalArgumentException("未知列名: " + columnName);
        }
    }
}
