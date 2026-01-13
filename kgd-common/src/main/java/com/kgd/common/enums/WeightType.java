package com.kgd.common.enums;

/**
 * 权重类型枚举
 */
public enum WeightType {
    /**
     * 速度指令控制权重
     */
    SPEED_COMMAND_CONTROL("速度指令控制"),
    
    /**
     * 滚转角指令控制权重
     */
    ROLL_ANGLE_COMMAND_CONTROL("滚转角指令控制"),
    
    /**
     * 航向角指令控制权重
     */
    HEADING_ANGLE_COMMAND_CONTROL("航向角指令控制"),
    
    /**
     * 6000米油耗权重
     */
    FUEL_CONSUMPTION_6000("6000米油耗"),
    
    /**
     * 8000米油耗权重
     */
    FUEL_CONSUMPTION_8000("8000米油耗"),
    
    /**
     * 10000米油耗权重
     */
    FUEL_CONSUMPTION_10000("10000米油耗"),
    
    /**
     * 12000米油耗权重
     */
    FUEL_CONSUMPTION_12000("12000米油耗"),
    
    /**
     * 14000米油耗权重
     */
    FUEL_CONSUMPTION_14000("14000米油耗"),
    
    /**
     * 最大速度权重
     */
    MAX_SPEED("最大速度"),
    
    /**
     * 最大加速度权重
     */
    MAX_ACCELERATION("最大加速度"),
    
    /**
     * 最大过载权重
     */
    MAX_OVERLOAD("最大过载"),
    
    /**
     * 最大转弯速率权重
     */
    MAX_TURNING_RATE("最大转弯速率"),
    
    /**
     * 最大方位扫描范围准确度权重
     */
    MAX_AZIMUTH_SCAN_RANGE_ACCURACY("最大方位扫描范围准确度"),
    
    /**
     * 最大俯仰扫描范围准确度权重
     */
    MAX_ELEVATION_SCAN_RANGE_ACCURACY("最大俯仰扫描范围准确度"),
    
    /**
     * 最大探测距离准确度权重
     */
    MAX_DETECTION_RANGE_ACCURACY("最大探测距离准确度"),
    
    /**
     * 最大跟踪目标数量权重
     */
    MAX_TRACK_TARGET_COUNT("最大跟踪目标数量"),
    
    /**
     * 最大跟踪外推时间权重
     */
    MAX_TRACK_EXTRAPOLATION_TIME("最大跟踪外推时间"),
    
    /**
     * 弹道准确度权重
     */
    TRAJECTORY_ACCURACY("弹道准确度"),
    
    /**
     * 武器杀伤半径准确度权重
     */
    WEAPON_KILL_RADIUS_ACCURACY("武器杀伤半径准确度"),
    
    /**
     * 导引头截获距离准确度权重
     */
    SEARCHER_ACQUISITION_RANGE_ACCURACY("导引头截获距离准确度"),
    
    /**
     * 发动机工作时间准确度权重
     */
    ENGINE_OPERATING_TIME_ACCURACY("发动机工作时间准确度"),
    
    /**
     * 导弹射程准确度权重
     */
    MISSILE_RANGE_ACCURACY("导弹射程准确度"),
    
    /**
     * 飞行控制系统模型准确度权重
     */
    FLIGHT_CONTROL_SYSTEM_ACCURACY("飞行控制系统模型准确度"),
    
    /**
     * 发动机推力模型准确度权重
     */
    ENGINE_THRUST_MODEL_ACCURACY("发动机推力模型准确度"),
    
    /**
     * 飞行加速性能准确度权重
     */
    FLIGHT_ACCELERATION_PERFORMANCE_ACCURACY("飞行加速性能准确度"),
    
    /**
     * 飞行转弯性能准确度权重
     */
    FLIGHT_TURNING_PERFORMANCE_ACCURACY("飞行转弯性能准确度"),
    
    /**
     * 雷达扫描范围准确度权重
     */
    RADAR_SCAN_RANGE_ACCURACY("雷达扫描范围准确度"),
    
    /**
     * 雷达跟踪效能准确度权重
     */
    RADAR_TRACKING_EFFICIENCY_ACCURACY("雷达跟踪效能准确度"),
    
    /**
     * 导弹弹道准确度权重
     */
    MISSILE_TRAJECTORY_ACCURACY("导弹弹道准确度"),
    
    /**
     * 导弹性能参数准确度权重
     */
    MISSILE_PERFORMANCE_PARAMETER_ACCURACY("导弹性能参数准确度"),
    
    /**
     * 导弹发动机模型准确度权重
     */
    MISSILE_ENGINE_MODEL_ACCURACY("导弹发动机模型准确度"),
    
    /**
     * 飞行仿真性能准确度权重
     */
    FLIGHT_SIMULATION_PERFORMANCE_ACCURACY("飞行仿真性能准确度"),
    
    /**
     * 雷达性能准确度权重
     */
    RADAR_PERFORMANCE_ACCURACY("雷达性能准确度"),
    
    /**
     * 导弹仿真系统性能准确度权重
     */
    MISSILE_SIMULATION_SYSTEM_PERFORMANCE_ACCURACY("导弹仿真系统性能准确度"),
    
    /**
     * 性能准确度权重
     */
    PERFORMANCE_ACCURACY("性能准确度"),

    /**
     * 体系功能权重
     */
    SYSTEM_FUNCTION("体系功能"),

    /**
     * 想定合理性权重
     */
    SCENARIO_RATIONALITY("想定合理性"),

    /**
     * 软件质量权重
     */
    SOFTWARE_QUALITY("软件质量"),

    /**
     * 支持智能决策模型生成与评估权重
     */
    INTELLIGENT_DECISION_SUPPORT("支持智能决策模型生成与评估"),

    /**
     * 总体评估权重
     */
    COMPREHENSIVE_EVALUATION("总体评估")
    ;
    
    private final String value;
    
    WeightType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}