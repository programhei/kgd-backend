package com.kgd.datasource.service;


import com.kgd.common.core.domain.AjaxResult;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;


public interface FlightService {
    //飞行控制系统模型准确度
    /**
     * 计算飞行仿真性能准确度
     * 速度指令控制权重 * 得分 + 滚转角指令控制权重 * 得分 + 航向角指令控制权重 * 得分
     *
     */
    Map<String,Object> listFlightPerformanceAccuracy();
    //发动机推力模型准确度
    /**
     * 发动机推力模型准确度
     * 6000米油耗权重 * 得分 + 8000米油耗权重 * 得分 + 10000米油耗权重 * 得分 + 12000米油耗权重 * 得分+ 14000米油耗权重 * 得分
     */
    Map<String, Object> listEnginePowerModelAccuracy();
    //飞行加速性能准确度
    /**
     * 飞行加速性能准确度
     * 最大速度权重 * 得分 + 最大加速度权重 * 得分
     */
    Map<String, Object> listFlightAccelerationAccuracy();
    //飞行转弯性能准确度
    /**
     * 飞行转弯性能准确度
     * 最大过载权重 * 得分 + 最大转弯速率权重 * 得分
     */
    Map<String, Object> listFlightTurningAccuracy();
    //雷达扫描范围准确度
    /**
     * 雷达扫描范围准确度
     * 最大方位扫描范围准确度权重 * 得分 + 最大俯仰扫描范围准确度权重 * 得分 + 最大探测距离准确度权重 * 得分
     */
    Map<String, Object> listRadarScanRangeAccuracy();
    //雷达跟踪效能准确度
    /**
     * 雷达跟踪效能准确度
     * 最大跟踪目标数量权重 * 得分 + 最大跟踪外推时间权重 * 得分
     */
    Map<String, Object> listRadarTrackingEfficiencyAccuracy();
    //导弹弹道准确度
    /**
     * 导弹弹道准确度
     * 弹道准确度权重 * 得分
     */
    Map<String, Object> listMissileTrajectoryAccuracy();
    //导弹性能参数准确度
    /**
     * 导弹性能参数准确度
     *武器杀伤半径准确度权重 * 得分+ 导引头截获距离准确度权重 * 得分
     */
    Map<String, Object> listMissilePerformanceParameterAccuracy();
    //导弹发动机模型准确度
    /**
     * 导弹发动机模型准确度
     *发动机工作时间准确度权重 * 得分+ 导弹射程准确度权重 * 得分
     */
    Map<String, Object> listMissileEngineModelAccuracy();
    //飞行仿真性能准确度
    /**
     * 飞行仿真性能准确度
     * 飞行控制系统模型准确度权重 * 得分+  发动机推力模型准确度权重 * 得分+  飞行加速性能准确度权重 * 得分+  飞行转弯性能准确度权重 * 得分
     */
    Map<String, Object> listFlightSimulationPerformanceAccuracy() ;
    //雷达性能准确度
    /**
     * 雷达性能准确度
     * 雷达扫描范围准确度权重 * 得分+  雷达跟踪效能准确度权重 * 得分
     * 雷达扫描范围准确度
     * 雷达跟踪效能准确度
     */
    Map<String, Object> listRadarPerformanceAccuracy();
    //导弹仿真系统性能准确度
    /**
     * 导弹仿真系统性能准确度
     * 导弹弹道准确度权重 * 得分+ 导弹性能参数准确度权重 * 得分+ 导弹发动机模型准确度权重 * 得分
     */
    Map<String, Object> listMissileSimulationSystemPerformanceAccuracy();
    //性能准确度
    /**
     * 性能准确度
     * 飞行仿真性能准确度 权重 * 得分+  雷达性能准确度权重 * 得分+  导弹仿真系统性能准确度权重 * 得分
     */
    Map<String, Object> listPerformanceAccuracy();
}
