package com.kgd.web.controller.datasource;

import com.kgd.common.core.domain.AjaxResult;
import com.kgd.common.enums.WeightType;
import com.kgd.datasource.domain.vo.HeadingAngleExcelVo;
import com.kgd.datasource.domain.vo.RollAngleExcelVo;
import com.kgd.datasource.domain.vo.SpeedCommandExcelVo;
import com.kgd.datasource.mapper.WeightMapper;
import com.kgd.datasource.service.FlightService;
import com.kgd.evaluate.domain.WeightMangeData;
import com.kgd.evaluate.mapper.WeightMangeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/datasource/flight")
public class FlightController {
    @Autowired
    private FlightService flightService;
    
    //飞行控制系统模型准确度
    /**
     * 计算飞行仿真性能准确度
     * 速度指令控制权重 * 得分 + 滚转角指令控制权重 * 得分 + 航向角指令控制权重 * 得分
     *
     */
    @PostMapping("/listFlightPerformanceAccuracy")
    public AjaxResult listFlightPerformanceAccuracy() {
        Map<String, Object> maps = flightService.listFlightPerformanceAccuracy();
        return AjaxResult.success(maps);
    }
    //发动机推力模型准确度
    /**
     * 发动机推力模型准确度
     * 6000米油耗权重 * 得分 + 8000米油耗权重 * 得分 + 10000米油耗权重 * 得分 + 12000米油耗权重 * 得分+ 14000米油耗权重 * 得分
     */
    @PostMapping("/listEnginePowerModelAccuracy")
    public AjaxResult listEnginePowerModelAccuracy() {
        Map<String, Object> maps = flightService.listEnginePowerModelAccuracy();
        return AjaxResult.success(maps);
    }
    //飞行加速性能准确度
    /**
     * 飞行加速性能准确度
     * 最大速度权重 * 得分 + 最大加速度权重 * 得分
     */
    @PostMapping("/listFlightAccelerationAccuracy")
    public AjaxResult listFlightAccelerationAccuracy() {
        Map<String, Object> maps = flightService.listFlightAccelerationAccuracy();
        return AjaxResult.success(maps);
    }
    //飞行转弯性能准确度
    /**
     * 飞行转弯性能准确度
     * 最大过载权重 * 得分 + 最大转弯速率权重 * 得分
     */
    @PostMapping("/listFlightTurningAccuracy")
    public AjaxResult listFlightTurningAccuracy() {
        Map<String, Object> maps = flightService.listFlightTurningAccuracy();
        return AjaxResult.success(maps);
    }
    //雷达扫描范围准确度
    /**
     * 雷达扫描范围准确度
     * 最大方位扫描范围准确度权重 * 得分 + 最大俯仰扫描范围准确度权重 * 得分 + 最大探测距离准确度权重 * 得分
     */
    @PostMapping("/listRadarScanRangeAccuracy")
    public AjaxResult listRadarScanRangeAccuracy() {
        Map<String, Object> maps = flightService.listRadarScanRangeAccuracy();
        return AjaxResult.success(maps);
    }
    //雷达跟踪效能准确度
    /**
     * 雷达跟踪效能准确度
     * 最大跟踪目标数量权重 * 得分 + 最大跟踪外推时间权重 * 得分
     */
    @PostMapping("/listRadarTrackingEfficiencyAccuracy")
    public AjaxResult listRadarTrackingEfficiencyAccuracy() {
        Map<String, Object> maps = flightService.listRadarTrackingEfficiencyAccuracy();
        return AjaxResult.success(maps);
    }
    //导弹弹道准确度
    /**
     * 导弹弹道准确度
     * 弹道准确度权重 * 得分
     */
    @PostMapping("/listMissileTrajectoryAccuracy")
    public AjaxResult listMissileTrajectoryAccuracy() {
        Map<String, Object> maps = flightService.listMissileTrajectoryAccuracy();
        return AjaxResult.success(maps);
    }
    //导弹性能参数准确度
    /**
     * 导弹性能参数准确度
     *武器杀伤半径准确度权重 * 得分+ 导引头截获距离准确度权重 * 得分
     */
    @PostMapping("/listMissilePerformanceParameterAccuracy")
    public AjaxResult listMissilePerformanceParameterAccuracy() {
        Map<String, Object> maps = flightService.listMissilePerformanceParameterAccuracy();
        return AjaxResult.success(maps);
    }
    //导弹发动机模型准确度
    /**
     * 导弹发动机模型准确度
     *发动机工作时间准确度权重 * 得分+ 导弹射程准确度权重 * 得分
     */
    @PostMapping("/listMissileEngineModelAccuracy")
    public AjaxResult listMissileEngineModelAccuracy() {
        Map<String, Object> maps = flightService.listMissileEngineModelAccuracy();
        return AjaxResult.success(maps);
    }
    //飞行仿真性能准确度
    /**
     * 飞行仿真性能准确度
     * 飞行控制系统模型准确度权重 * 得分+  发动机推力模型准确度权重 * 得分+  飞行加速性能准确度权重 * 得分+  飞行转弯性能准确度权重 * 得分
     */
    @PostMapping("/listFlightSimulationPerformanceAccuracy")
    public AjaxResult listFlightSimulationPerformanceAccuracy() {
        Map<String, Object> maps = flightService.listFlightSimulationPerformanceAccuracy();
        return AjaxResult.success(maps);
    }
    //雷达性能准确度
    /**
     * 雷达性能准确度
     * 雷达扫描范围准确度权重 * 得分+  雷达跟踪效能准确度权重 * 得分
     * 雷达扫描范围准确度
     * 雷达跟踪效能准确度
     */
    @PostMapping("/listRadarPerformanceAccuracy")
    public AjaxResult listRadarPerformanceAccuracy() {
        Map<String, Object> maps = flightService.listRadarPerformanceAccuracy();
        return AjaxResult.success(maps);
    }
    //导弹仿真系统性能准确度
    /**
     * 导弹仿真系统性能准确度
     * 导弹弹道准确度权重 * 得分+ 导弹性能参数准确度权重 * 得分+ 导弹发动机模型准确度权重 * 得分
     */
    @PostMapping("/listMissileSimulationSystemPerformanceAccuracy")
    public AjaxResult listMissileSimulationSystemPerformanceAccuracy() {
        Map<String, Object> maps = flightService.listMissileSimulationSystemPerformanceAccuracy();
        return AjaxResult.success(maps);
    }
    //性能准确度
    /**
     * 性能准确度
     * 飞行仿真性能准确度 权重 * 得分+  雷达性能准确度权重 * 得分+  导弹仿真系统性能准确度权重 * 得分
     */
    @PostMapping("/listPerformanceAccuracy")
    public AjaxResult listPerformanceAccuracy() {
        Map<String, Object> maps = flightService.listPerformanceAccuracy();
        return AjaxResult.success(maps);
    }
}
