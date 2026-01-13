package com.kgd.datasource.service.impl;

import com.kgd.common.core.domain.AjaxResult;
import com.kgd.common.enums.WeightType;
import com.kgd.common.constant.PerformanceConstants;
import com.kgd.datasource.domain.vo.*;
import com.kgd.datasource.mapper.MaxAccelerationStandardMapper;
import com.kgd.datasource.mapper.WeightMapper;
import com.kgd.datasource.service.FlightService;
import com.kgd.datasource.service.IWeightService;
import com.kgd.evaluate.domain.WeightMangeData;
import com.kgd.evaluate.mapper.WeightMangeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private WeightMapper weightMapper;
    @Autowired
    private WeightMangeMapper weightMangeMapper;
    @Resource
    private MaxAccelerationStandardMapper maxAccelerationStandardMapper;
    @Resource
    private IWeightService weightService;
    
    // 线程池，用于并行执行性能计算任务
    private final ExecutorService executorService = new ThreadPoolExecutor(
            3, // 核心线程数，与需要并行执行的任务数匹配
            5, // 最大线程数
            60, // 空闲线程存活时间
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(), // 任务队列
            Executors.defaultThreadFactory(), // 线程工厂
            new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略
    );
    
    // 通用的相对误差平方计算方法
    private double calculateRelativeErrorSquare(String standardStr, String testStr) {
        // 处理空值情况，如果为空则设为"0"
        standardStr = (standardStr == null || standardStr.isEmpty()) ? "0" : standardStr;
        testStr = (testStr == null || testStr.isEmpty()) ? "0" : testStr;
        
        try {
            double standardValue = Double.parseDouble(standardStr);
            double testValue = Double.parseDouble(testStr);
            
            // 避免除以零
            if (standardValue == 0) {
                return 0;
            }
            
            // 计算相对误差的平方
            double relativeError = (testValue - standardValue) / standardValue;
            return Math.pow(relativeError, 2);
        } catch (NumberFormatException e) {
            // 处理无法解析为数字的情况，返回0表示不参与计算
            return 0;
        }
    }
    
    // 计算均方根误差(RMSE)并转换为准确度百分比
    private double calculateAccuracyPercentage(double sumOfSquares, int count) {
        if (count == 0) {
            return 0;
        }
        
        // 计算均方误差(MSE)
        double mse = sumOfSquares / count;
        
        // 计算均方根误差(RMSE)
        double rmse = Math.sqrt(mse);
        
        // 将误差转换为准确度百分比 (假设误差在0-1之间)
        // 准确度 = (1 - rmse) * 100%，但不小于0%
        double accuracy = Math.max(0, (1 - rmse) * 100);
        
        return accuracy;
    }
    
    //飞行控制系统模型准确度
    /**
     * 计算飞行仿真性能准确度
     * 速度指令控制权重 * 得分 + 滚转角指令控制权重 * 得分 + 航向角指令控制权重 * 得分
     *
     */
    @Override
    public Map<String,Object> listFlightPerformanceAccuracy() {
        Map<String, Object> maps = new HashMap<>();

        // ==================================================================
        // 1. 获取权值数据
        // ==================================================================
        HashMap<String, Double> weightMap = getWeightMap();
        
        // ==================================================================
        // 2. 速度指令控制性能计算
        // ==================================================================
        // 2.1 获取速度指令数据
        List<SpeedCommandExcelVo> speedStandardList = weightMapper.selectSpeedCommandExcelVo_standard(null);
        List<SpeedCommandExcelVo> speedTestList = weightMapper.selectSpeedCommandExcelVo_test(null);
        
        double speedSumOfSquares = 0;
        int speedValidCount = 0;

        // 2.2 计算速度指令的相对误差平方和
        int speedMinSize = Math.min(speedStandardList.size(), speedTestList.size());
        for (int i = 0; i < speedMinSize; i++) {
            String standardSpeed = speedStandardList.get(i).getSpeed();
            String testSpeed = speedTestList.get(i).getSpeed();
            
            double errorSquare = calculateRelativeErrorSquare(standardSpeed, testSpeed);
            if (errorSquare != 0) {
                speedSumOfSquares += errorSquare;
                speedValidCount++;
            }
        }
        
        // 2.3 计算速度指令准确度
        double speedAccuracy = calculateAccuracyPercentage(speedSumOfSquares, speedValidCount);
        maps.put("speed_accuracy", speedAccuracy);
        maps.put("speed_valid_count", speedValidCount);

        // ==================================================================
        // 3. 滚转角指令控制性能计算
        // ==================================================================
        // 3.1 获取滚转角指令数据
        List<RollAngleExcelVo> rollStandardList = weightMapper.selectRollAngleExcelVo_standard(null);
        List<RollAngleExcelVo> rollTestList = weightMapper.selectRollAngleExcelVo_test(null);
        
        double rollSumOfSquares = 0;
        int rollValidCount = 0;
        
        // 3.2 计算滚转角指令的相对误差平方和 (同时使用30°和60°数据)
        int rollMinSize = Math.min(rollStandardList.size(), rollTestList.size());
        for (int i = 0; i < rollMinSize; i++) {
            // 30°滚转角数据
            String standardRoll30 = rollStandardList.get(i).getPitchAngle30();
            String testRoll30 = rollTestList.get(i).getPitchAngle30();
            double error30 = calculateRelativeErrorSquare(standardRoll30, testRoll30);
            
            // 60°滚转角数据
            String standardRoll60 = rollStandardList.get(i).getPitchAngle60();
            String testRoll60 = rollTestList.get(i).getPitchAngle60();
            double error60 = calculateRelativeErrorSquare(standardRoll60, testRoll60);
            
            // 累加误差并统计有效数据点
            if (error30 != 0) {
                rollSumOfSquares += error30;
                rollValidCount++;
            }
            if (error60 != 0) {
                rollSumOfSquares += error60;
                rollValidCount++;
            }
        }
        
        // 3.3 计算滚转角指令准确度
        double rollAccuracy = calculateAccuracyPercentage(rollSumOfSquares, rollValidCount);
        maps.put("roll_angle_accuracy", rollAccuracy);
        maps.put("roll_valid_count", rollValidCount);

        // ==================================================================
        // 4. 航向角指令控制性能计算
        // ==================================================================
        // 4.1 获取航向角指令数据
        List<HeadingAngleExcelVo> headingStandardList = weightMapper.selectHeadingAngleExcelVo_standard(null);
        List<HeadingAngleExcelVo> headingTestList = weightMapper.selectHeadingAngleExcelVo_test(null);
        
        double headingSumOfSquares = 0;
        int headingValidCount = 0;
        
        // 4.2 计算航向角指令的相对误差平方和 (同时使用60°和120°数据)
        int headingMinSize = Math.min(headingStandardList.size(), headingTestList.size());
        for (int i = 0; i < headingMinSize; i++) {
            // 60°航向角数据
            String standardHeading60 = headingStandardList.get(i).getCommandHeading60();
            String testHeading60 = headingTestList.get(i).getCommandHeading60();
            double error60 = calculateRelativeErrorSquare(standardHeading60, testHeading60);
            
            // 120°航向角数据
            String standardHeading120 = headingStandardList.get(i).getCommandHeading120();
            String testHeading120 = headingTestList.get(i).getCommandHeading120();
            double error120 = calculateRelativeErrorSquare(standardHeading120, testHeading120);
            
            // 累加误差并统计有效数据点
            if (error60 != 0) {
                headingSumOfSquares += error60;
                headingValidCount++;
            }
            if (error120 != 0) {
                headingSumOfSquares += error120;
                headingValidCount++;
            }
        }
        
        // 4.3 计算航向角指令准确度
        double headingAccuracy = calculateAccuracyPercentage(headingSumOfSquares, headingValidCount);
        maps.put("heading_angle_accuracy", headingAccuracy);
        maps.put("heading_valid_count", headingValidCount);
        
        // ==================================================================
        // 5. 综合性能计算
        // ==================================================================
        // 计算总体平均准确度
        double totalSumOfSquares = speedSumOfSquares + rollSumOfSquares + headingSumOfSquares;
        int totalValidCount = speedValidCount + rollValidCount + headingValidCount;
        double totalAccuracy = calculateAccuracyPercentage(totalSumOfSquares, totalValidCount);
        maps.put("total_accuracy", totalAccuracy);
        maps.put("total_valid_count", totalValidCount);
        
        // ==================================================================
        // 6. 加权计算（如果有权值数据）
        // ==================================================================
        if (weightMap != null) {
            //速度指令控制加权得分
            Double speedWeight = weightMap.get(WeightType.SPEED_COMMAND_CONTROL.getValue());
            if (speedWeight != null) {
                double weightedSpeedScore = speedAccuracy * speedWeight;
                maps.put("weighted_speed_score", weightedSpeedScore);
            }
            
            //滚转角指令控制加权得分
            Double rollWeight = weightMap.get(WeightType.ROLL_ANGLE_COMMAND_CONTROL.getValue());
            if (rollWeight != null) {
                double weightedRollScore = rollAccuracy * rollWeight;
                maps.put("weighted_roll_score", weightedRollScore);
            }
            
            //航向角指令控制加权得分
            Double headingWeight = weightMap.get(WeightType.HEADING_ANGLE_COMMAND_CONTROL.getValue());
            if (headingWeight != null) {
                double weightedHeadingScore = headingAccuracy * headingWeight;
                maps.put("weighted_heading_score", weightedHeadingScore);
            }
            
            //计算加权综合得分
            double weightedTotalScore = 0;
            
            if (speedWeight != null) {
                weightedTotalScore += speedAccuracy * speedWeight;
            }
            if (rollWeight != null) {
                weightedTotalScore += rollAccuracy * rollWeight;
            }
            if (headingWeight != null) {
                weightedTotalScore += headingAccuracy * headingWeight;
            }
            

            maps.put("weighted_total_score", weightedTotalScore);

        }
        
        return maps;
    }
    /**
     * 发动机推力模型准确度
     * 6000米油耗权重 * 得分 + 8000米油耗权重 * 得分 + 10000米油耗权重 * 得分 + 12000米油耗权重 * 得分+ 14000米油耗权重 * 得分
     */
    @Override
    public Map<String, Object> listEnginePowerModelAccuracy() {
        Map<String, Object> maps = new HashMap<>();
        
        // ==================================================================
        // 1. 获取油耗数据
        // ==================================================================
        List<FuelConsumptionExcelVo> standardList = weightMapper.selectFuelConsumptionExcelVo_standard(null);
        List<FuelConsumptionExcelVo> testList = weightMapper.selectFuelConsumptionExcelVo_test(null);
        
        // ==================================================================
        // 2. 计算各高度油耗的相对误差平方和
        // ==================================================================
        // 初始化各高度的误差平方和和有效数据点数量
        double sum6000 = 0;
        double sum8000 = 0;
        double sum10000 = 0;
        double sum12000 = 0;
        double sum14000 = 0;
        int count6000 = 0;
        int count8000 = 0;
        int count10000 = 0;
        int count12000 = 0;
        int count14000 = 0;
        
        // 计算每个高度的相对误差平方和
        int minSize = Math.min(standardList.size(), testList.size());
        for (int i = 0; i < minSize; i++) {
            FuelConsumptionExcelVo standard = standardList.get(i);
            FuelConsumptionExcelVo test = testList.get(i);
            
            // 6000米油耗
            double error6000 = calculateRelativeErrorSquare(standard.getFuelConsumption6000(), test.getFuelConsumption6000());
            if (error6000 != 0) {
                sum6000 += error6000;
                count6000++;
            }
            
            // 8000米油耗
            double error8000 = calculateRelativeErrorSquare(standard.getFuelConsumption8000(), test.getFuelConsumption8000());
            if (error8000 != 0) {
                sum8000 += error8000;
                count8000++;
            }
            
            // 10000米油耗
            double error10000 = calculateRelativeErrorSquare(standard.getFuelConsumption10000(), test.getFuelConsumption10000());
            if (error10000 != 0) {
                sum10000 += error10000;
                count10000++;
            }
            
            // 12000米油耗
            double error12000 = calculateRelativeErrorSquare(standard.getFuelConsumption12000(), test.getFuelConsumption12000());
            if (error12000 != 0) {
                sum12000 += error12000;
                count12000++;
            }
            
            // 14000米油耗
            double error14000 = calculateRelativeErrorSquare(standard.getFuelConsumption14000(), test.getFuelConsumption14000());
            if (error14000 != 0) {
                sum14000 += error14000;
                count14000++;
            }
        }
        
        // ==================================================================
        // 3. 计算各高度油耗的准确度
        // ==================================================================
        double accuracy6000 = calculateAccuracyPercentage(sum6000, count6000);
        double accuracy8000 = calculateAccuracyPercentage(sum8000, count8000);
        double accuracy10000 = calculateAccuracyPercentage(sum10000, count10000);
        double accuracy12000 = calculateAccuracyPercentage(sum12000, count12000);
        double accuracy14000 = calculateAccuracyPercentage(sum14000, count14000);
        
        // 将各高度的准确度和有效数据点数量添加到结果中
        maps.put("accuracy_6000", accuracy6000);
        maps.put("count_6000", count6000);
        maps.put("accuracy_8000", accuracy8000);
        maps.put("count_8000", count8000);
        maps.put("accuracy_10000", accuracy10000);
        maps.put("count_10000", count10000);
        maps.put("accuracy_12000", accuracy12000);
        maps.put("count_12000", count12000);
        maps.put("accuracy_14000", accuracy14000);
        maps.put("count_14000", count14000);
        
        // ==================================================================
        // 4. 计算综合性能
        // ==================================================================
        // 计算总体平均准确度
        double totalSum = sum6000 + sum8000 + sum10000 + sum12000 + sum14000;
//        int totalCount = count6000 + count8000 + count10000 + count12000 + count14000;
        double totalAccuracy = calculateAccuracyPercentage(totalSum, 5);
        maps.put("total_accuracy", totalAccuracy);
//        maps.put("total_valid_count", totalCount);
        
        // ==================================================================
        // 5. 加权计算（如果有权值数据）
        // ==================================================================
        HashMap<String, Double> weightMap = getWeightMap();
        if (weightMap != null) {
            // 6000米油耗加权得分
            Double weight6000 = weightMap.get(WeightType.FUEL_CONSUMPTION_6000.getValue());
            if (weight6000 != null) {
                double weightedScore6000 = accuracy6000 * weight6000;
                maps.put("weighted_score_6000", weightedScore6000);
            }
            
            // 8000米油耗加权得分
            Double weight8000 = weightMap.get(WeightType.FUEL_CONSUMPTION_8000.getValue());
            if (weight8000 != null) {
                double weightedScore8000 = accuracy8000 * weight8000;
                maps.put("weighted_score_8000", weightedScore8000);
            }
            
            // 10000米油耗加权得分
            Double weight10000 = weightMap.get(WeightType.FUEL_CONSUMPTION_10000.getValue());
            if (weight10000 != null) {
                double weightedScore10000 = accuracy10000 * weight10000;
                maps.put("weighted_score_10000", weightedScore10000);
            }
            
            // 12000米油耗加权得分
            Double weight12000 = weightMap.get(WeightType.FUEL_CONSUMPTION_12000.getValue());
            if (weight12000 != null) {
                double weightedScore12000 = accuracy12000 * weight12000;
                maps.put("weighted_score_12000", weightedScore12000);
            }
            
            // 14000米油耗加权得分
            Double weight14000 = weightMap.get(WeightType.FUEL_CONSUMPTION_14000.getValue());
            if (weight14000 != null) {
                double weightedScore14000 = accuracy14000 * weight14000;
                maps.put("weighted_score_14000", weightedScore14000);
            }
            
            // 计算加权综合得分
            double weightedTotalScore = 0;
            
            if (weight6000 != null) {
                weightedTotalScore += accuracy6000 * weight6000;
            }
            if (weight8000 != null) {
                weightedTotalScore += accuracy8000 * weight8000;
            }
            if (weight10000 != null) {
                weightedTotalScore += accuracy10000 * weight10000;
            }
            if (weight12000 != null) {
                weightedTotalScore += accuracy12000 * weight12000;
            }
            if (weight14000 != null) {
                weightedTotalScore += accuracy14000 * weight14000;
            }
            
    
            maps.put("weighted_total_score", weightedTotalScore);
        }
        
        return maps;
    }
    //飞行加速性能准确度
    /**
     * 飞行加速性能准确度
     * 最大速度权重 * 得分 + 最大加速度权重 * 得分
     */
    @Override
    public Map<String, Object> listFlightAccelerationAccuracy() {
        Map<String, Object> maps = new HashMap<>();
        //6.2最大速度、加速度
        List<MaxAccelerationStandardVo> allList = maxAccelerationStandardMapper.selectMaxAccelerationStandardAll();
        //最大速度标准测试
        List<MaxAccelerationStandardVo> maxSpeedStandard = allList.stream().filter(e -> e.getEvaluateType() == 0 && e.getSpeedOrTest() == 0).collect(Collectors.toList());
        List<MaxAccelerationStandardVo> maxSpeedTest = allList.stream().filter(e -> e.getEvaluateType() == 0 && e.getSpeedOrTest() == 1).collect(Collectors.toList());
        //计算分数
        MaxAccelerationStandardVo maxSpeedScore = weightService.maxSpeed(maxSpeedStandard, maxSpeedTest);
        maps.put("maxSpeedStandard",maxSpeedStandard);
        maps.put("maxSpeedTest",maxSpeedTest);
        maps.put("maxSpeedScore",maxSpeedScore);
        //最大加速度标准测试
        List<MaxAccelerationStandardVo> maxAccelerationStandard = allList.stream().filter(e -> e.getEvaluateType() == 1 && e.getSpeedOrTest() == 0).collect(Collectors.toList());
        List<MaxAccelerationStandardVo> maxAccelerationTest = allList.stream().filter(e -> e.getEvaluateType() == 1 && e.getSpeedOrTest() == 1).collect(Collectors.toList());
        MaxAccelerationStandardVo maxAccelerationScore = weightService.maxSpeed(maxAccelerationStandard, maxAccelerationTest);
        maps.put("maxAccelerationStandard",maxAccelerationStandard);
        maps.put("maxAccelerationTest",maxAccelerationTest);
        maps.put("maxAccelerationScore",maxAccelerationScore);
        //加上权重计算
        HashMap<String, Double> weightMap = getWeightMap();
        if (weightMap != null) {
            // 获取最大速度和最大加速度的权重，默认为0.5（平均分配）
            Double maxSpeedWeight = weightMap.getOrDefault(WeightType.MAX_SPEED.getValue(), 0.5);
            Double maxAccelerationWeight = weightMap.getOrDefault(WeightType.MAX_ACCELERATION.getValue(), 0.5);
            
            // 计算综合加权得分
            double totalWeightedScore = 0;
            
            // 计算6000米加权得分
            if (maxSpeedScore.getHeight6000() != null && maxAccelerationScore.getHeight6000() != null) {
                double weightedScore6000 = (maxSpeedScore.getHeight6000() * maxSpeedWeight) + (maxAccelerationScore.getHeight6000() * maxAccelerationWeight);
                maps.put("weighted_score_6000", weightedScore6000);
                totalWeightedScore += weightedScore6000;
            }
            
            // 计算8000米加权得分
            if (maxSpeedScore.getHeight8000() != null && maxAccelerationScore.getHeight8000() != null) {
                double weightedScore8000 = (maxSpeedScore.getHeight8000() * maxSpeedWeight) + (maxAccelerationScore.getHeight8000() * maxAccelerationWeight);
                maps.put("weighted_score_8000", weightedScore8000);
                totalWeightedScore += weightedScore8000;
            }
            
            // 计算10000米加权得分
            if (maxSpeedScore.getHeight10000() != null && maxAccelerationScore.getHeight10000() != null) {
                double weightedScore10000 = (maxSpeedScore.getHeight10000() * maxSpeedWeight) + (maxAccelerationScore.getHeight10000() * maxAccelerationWeight);
                maps.put("weighted_score_10000", weightedScore10000);
                totalWeightedScore += weightedScore10000;
            }
            
            // 计算12000米加权得分
            if (maxSpeedScore.getHeight12000() != null && maxAccelerationScore.getHeight12000() != null) {
                double weightedScore12000 = (maxSpeedScore.getHeight12000() * maxSpeedWeight) + (maxAccelerationScore.getHeight12000() * maxAccelerationWeight);
                maps.put("weighted_score_12000", weightedScore12000);
                totalWeightedScore += weightedScore12000;
            }
            
            // 计算14000米加权得分
            if (maxSpeedScore.getHeight14000() != null && maxAccelerationScore.getHeight14000() != null) {
                double weightedScore14000 = (maxSpeedScore.getHeight14000() * maxSpeedWeight) + (maxAccelerationScore.getHeight14000() * maxAccelerationWeight);
                maps.put("weighted_score_14000", weightedScore14000);
                totalWeightedScore += weightedScore14000;
            }
            
            // 保存综合加权得分
            maps.put("weighted_total_score", totalWeightedScore);
        }

        return maps;
    }
    //飞行转弯性能准确度
    /**
     * 飞行转弯性能准确度
     * 最大过载权重 * 得分 + 最大转弯速率权重 * 得分
     * 飞行转弯性能准确度 = 最大过载权值*最大过载得分+最大转弯速率权值*最大过载得分
     */
    @Override
    public Map<String, Object> listFlightTurningAccuracy() {
        Map<String, Object> maps = new HashMap<>();
        
        // 获取权重映射
        HashMap<String, Double> weightMap = getWeightMap();
        // 设置最大过载权重，默认值为0.5
        double maxOverloadWeight = weightMap != null ? weightMap.getOrDefault(WeightType.MAX_OVERLOAD.getValue(), 0.5) : 0.5;
        // 设置转弯速率权重，默认值为0.5
        double turningRateWeight = weightMap != null ? weightMap.getOrDefault(WeightType.MAX_TURNING_RATE.getValue(), 0.5) : 0.5;
        
        // 5.1 最大过载计算
        List<MaxOverloadExcelVo> overload0List = weightMapper.selectMaxOverloadExcelVo_standard(null);
        List<MaxOverloadExcelVo> overload1List = weightMapper.selectMaxOverloadExcelVo_test(null);
        
        double overloadSumOfSquares = 0;
        int overloadValidCount = 0;
        
        // 遍历最大过载数据列表，计算相对误差平方和
        for (int i = 0; i < Math.min(overload0List.size(), overload1List.size()); i++) {
            MaxOverloadExcelVo standard = overload0List.get(i);
            MaxOverloadExcelVo test = overload1List.get(i);
            
            if (standard != null && test != null) {
                String standardOverload = String.valueOf(standard.getOverload());
                String testOverload = String.valueOf(test.getOverload());
                
                // 计算相对误差平方
                double errorSquare = calculateRelativeErrorSquare(standardOverload, testOverload);
                overloadSumOfSquares += errorSquare;
                overloadValidCount++;
            }
        }
        
        // 计算最大过载准确度百分比
        double maxOverloadAccuracy = calculateAccuracyPercentage(overloadSumOfSquares, overloadValidCount);

        // 计算加权总准确度：飞行转弯性能准确度 = 最大过载权值*最大过载得分 + 最大转弯速率权值*最大过载得分
        double totalWeightedAccuracy = maxOverloadAccuracy * (maxOverloadWeight + turningRateWeight);
        
        // 将计算结果存入映射
        maps.put("max_overload_accuracy", maxOverloadAccuracy);
        maps.put("max_overload_weight", maxOverloadWeight);
        maps.put("turning_rate_weight", turningRateWeight);
        maps.put("total_weighted_accuracy", totalWeightedAccuracy);
        
        return maps;
    }
    //雷达扫描范围准确度
    /**
     * 雷达扫描范围准确度
     * 最大方位扫描范围准确度权重 * 得分 + 最大俯仰扫描范围准确度权重 * 得分 + 最大探测距离准确度权重 * 得分
     */
    @Override
    public Map<String, Object> listRadarScanRangeAccuracy() {
        Map<String, Object> maps = new HashMap<>();
        
        // 获取权重映射
        HashMap<String, Double> weightMap = getWeightMap();
        double azimuthWeight = weightMap != null ? weightMap.getOrDefault(WeightType.MAX_AZIMUTH_SCAN_RANGE_ACCURACY.getValue(), 1.0/3) : 1.0/3;
        double elevationWeight = weightMap != null ? weightMap.getOrDefault(WeightType.MAX_ELEVATION_SCAN_RANGE_ACCURACY.getValue(), 1.0/3) : 1.0/3;
        double detectionWeight = weightMap != null ? weightMap.getOrDefault(WeightType.MAX_DETECTION_RANGE_ACCURACY.getValue(), 1.0/3) : 1.0/3;
        
        // 查询雷达扫描范围准确度数据
        PerformanceAccuracyExcelVo queryVo = new PerformanceAccuracyExcelVo();
        queryVo.setDimension(PerformanceConstants.DIMENSION_RADAR_SCAN_RANGE);
        
        // 标准数据
        List<PerformanceAccuracyExcelVo> standardList = weightMapper.selectPerformanceAccuracyExcelVo_standard(queryVo);
        // 测试数据
        List<PerformanceAccuracyExcelVo> testList = weightMapper.selectPerformanceAccuracyExcelVo_test(queryVo);
        
        // 按类型分组处理数据
        Map<String, PerformanceAccuracyExcelVo> standardMap = standardList.stream()
                .collect(Collectors.toMap(PerformanceAccuracyExcelVo::getVariable, vo -> vo));
        Map<String, PerformanceAccuracyExcelVo> testMap = testList.stream()
                .collect(Collectors.toMap(PerformanceAccuracyExcelVo::getVariable, vo -> vo));
        
        // 计算最大方位扫描范围准确度
        PerformanceAccuracyExcelVo azimuthStandard = standardMap.getOrDefault(WeightType.MAX_AZIMUTH_SCAN_RANGE_ACCURACY.getValue(), null);
        PerformanceAccuracyExcelVo azimuthTest = testMap.getOrDefault(WeightType.MAX_AZIMUTH_SCAN_RANGE_ACCURACY.getValue(), null);
        double azimuthAccuracy = 0;
        
        if (azimuthStandard != null && azimuthTest != null) {
            String standardValue = azimuthStandard.getVariableValue();
            String testValue = azimuthTest.getVariableValue();
            double errorSquare = calculateRelativeErrorSquare(standardValue, testValue);
            azimuthAccuracy = calculateAccuracyPercentage(errorSquare, 1);
        }
        
        // 计算最大俯仰扫描范围准确度
        PerformanceAccuracyExcelVo elevationStandard = standardMap.getOrDefault(WeightType.MAX_ELEVATION_SCAN_RANGE_ACCURACY.getValue(), null);
        PerformanceAccuracyExcelVo elevationTest = testMap.getOrDefault(WeightType.MAX_ELEVATION_SCAN_RANGE_ACCURACY.getValue(), null);
        double elevationAccuracy = 0;
        
        if (elevationStandard != null && elevationTest != null) {
            String standardValue = elevationStandard.getVariableValue();
            String testValue = elevationTest.getVariableValue();
            double errorSquare = calculateRelativeErrorSquare(standardValue, testValue);
            elevationAccuracy = calculateAccuracyPercentage(errorSquare, 1);
        }
        
        // 计算最大探测距离准确度
        PerformanceAccuracyExcelVo detectionStandard = standardMap.getOrDefault(WeightType.MAX_DETECTION_RANGE_ACCURACY.getValue(), null);
        PerformanceAccuracyExcelVo detectionTest = testMap.getOrDefault(WeightType.MAX_DETECTION_RANGE_ACCURACY.getValue(), null);
        double detectionAccuracy = 0;
        
        if (detectionStandard != null && detectionTest != null) {
            String standardValue = detectionStandard.getVariableValue();
            String testValue = detectionTest.getVariableValue();
            double errorSquare = calculateRelativeErrorSquare(standardValue, testValue);
            detectionAccuracy = calculateAccuracyPercentage(errorSquare, 1);
        }
        
        // 计算加权总准确度：最大方位扫描范围准确度权重 * 得分 + 最大俯仰扫描范围准确度权重 * 得分 + 最大探测距离准确度权重 * 得分
        double totalAccuracy = (azimuthAccuracy * azimuthWeight) + (elevationAccuracy * elevationWeight) + (detectionAccuracy * detectionWeight);
        
        // 将结果存入映射
        maps.put("azimuth_accuracy", azimuthAccuracy);
        maps.put("elevation_accuracy", elevationAccuracy);
        maps.put("detection_accuracy", detectionAccuracy);
        maps.put("azimuth_weight", azimuthWeight);
        maps.put("elevation_weight", elevationWeight);
        maps.put("detection_weight", detectionWeight);
        maps.put("total_weighted_accuracy", totalAccuracy);
        
        return maps;
    }
    //雷达跟踪效能准确度
    /**
     * 雷达跟踪效能准确度
     * 最大跟踪目标数量权重 * 得分 + 最大跟踪外推时间权重 * 得分
     */
    @Override
    public Map<String, Object> listRadarTrackingEfficiencyAccuracy() {
        Map<String, Object> maps = new HashMap<>();
        
        // 获取权重映射
        HashMap<String, Double> weightMap = getWeightMap();
        double trackCountWeight = weightMap != null ? weightMap.getOrDefault(WeightType.MAX_TRACK_TARGET_COUNT.getValue(), 0.5) : 0.5;
        double extrapolationTimeWeight = weightMap != null ? weightMap.getOrDefault(WeightType.MAX_TRACK_EXTRAPOLATION_TIME.getValue(), 0.5) : 0.5;
        
        // 查询雷达跟踪效能准确度数据
        PerformanceAccuracyExcelVo queryVo = new PerformanceAccuracyExcelVo();
        queryVo.setDimension(PerformanceConstants.DIMENSION_RADAR_TRACKING_EFFICIENCY);
        
        // 标准数据
        List<PerformanceAccuracyExcelVo> standardList = weightMapper.selectPerformanceAccuracyExcelVo_standard(queryVo);
        // 测试数据
        List<PerformanceAccuracyExcelVo> testList = weightMapper.selectPerformanceAccuracyExcelVo_test(queryVo);
        
        // 按类型分组处理数据
        Map<String, PerformanceAccuracyExcelVo> standardMap = standardList.stream()
                .collect(Collectors.toMap(PerformanceAccuracyExcelVo::getVariable, vo -> vo));
        Map<String, PerformanceAccuracyExcelVo> testMap = testList.stream()
                .collect(Collectors.toMap(PerformanceAccuracyExcelVo::getVariable, vo -> vo));
        
        // 计算最大跟踪目标数量准确度
        PerformanceAccuracyExcelVo trackCountStandard = standardMap.getOrDefault(WeightType.MAX_TRACK_TARGET_COUNT.getValue(), null);
        PerformanceAccuracyExcelVo trackCountTest = testMap.getOrDefault(WeightType.MAX_TRACK_TARGET_COUNT.getValue(), null);
        double trackCountAccuracy = 0;
        
        if (trackCountStandard != null && trackCountTest != null) {
            String standardValue = trackCountStandard.getVariableValue();
            String testValue = trackCountTest.getVariableValue();
            double errorSquare = calculateRelativeErrorSquare(standardValue, testValue);
            trackCountAccuracy = calculateAccuracyPercentage(errorSquare, 1);
        }
        
        // 计算最大跟踪外推时间准确度
        PerformanceAccuracyExcelVo extrapolationStandard = standardMap.getOrDefault(WeightType.MAX_TRACK_EXTRAPOLATION_TIME.getValue(), null);
        PerformanceAccuracyExcelVo extrapolationTest = testMap.getOrDefault(WeightType.MAX_TRACK_EXTRAPOLATION_TIME.getValue(), null);
        double extrapolationAccuracy = 0;
        
        if (extrapolationStandard != null && extrapolationTest != null) {
            String standardValue = extrapolationStandard.getVariableValue();
            String testValue = extrapolationTest.getVariableValue();
            double errorSquare = calculateRelativeErrorSquare(standardValue, testValue);
            extrapolationAccuracy = calculateAccuracyPercentage(errorSquare, 1);
        }
        
        // 计算加权总准确度：最大跟踪目标数量权重 * 得分 + 最大跟踪外推时间权重 * 得分
        double totalAccuracy = (trackCountAccuracy * trackCountWeight) + (extrapolationAccuracy * extrapolationTimeWeight);
        
        // 将结果存入映射
        maps.put("track_count_accuracy", trackCountAccuracy);
        maps.put("extrapolation_time_accuracy", extrapolationAccuracy);
        maps.put("track_count_weight", trackCountWeight);
        maps.put("extrapolation_time_weight", extrapolationTimeWeight);
        maps.put("total_weighted_accuracy", totalAccuracy);
        
        return maps;
    }
    //导弹弹道准确度
    /**
     * 导弹弹道准确度
     * 弹道准确度权重 * 得分
     */
    @Override
    public Map<String, Object> listMissileTrajectoryAccuracy() {
        Map<String, Object> maps = new HashMap<>();
        
        // 获取权重映射
        HashMap<String, Double> weightMap = getWeightMap();
        double trajectoryWeight = weightMap != null ? weightMap.getOrDefault(WeightType.MISSILE_RANGE_ACCURACY.getValue(), 1.0) : 1.0;
        
        // 查询导弹弹道准确度数据
        PerformanceAccuracyExcelVo queryVo = new PerformanceAccuracyExcelVo();
        queryVo.setVariable(PerformanceConstants.DIMENSION_MISSILE_TRAJECTORY);
        
        // 标准数据
        List<PerformanceAccuracyExcelVo> standardList = weightMapper.selectPerformanceAccuracyExcelVo_standard(queryVo);
        // 测试数据
        List<PerformanceAccuracyExcelVo> testList = weightMapper.selectPerformanceAccuracyExcelVo_test(queryVo);
        
        // 计算弹道准确度
        double trajectorySumOfSquares = 0;
        int trajectoryValidCount = 0;
        
        for (int i = 0; i < Math.min(standardList.size(), testList.size()); i++) {
            PerformanceAccuracyExcelVo standard = standardList.get(i);
            PerformanceAccuracyExcelVo test = testList.get(i);
            
            if (standard != null && test != null) {
                String standardValue = standard.getVariableValue();
                String testValue = test.getVariableValue();
                
                // 计算相对误差平方
                double errorSquare = calculateRelativeErrorSquare(standardValue, testValue);
                trajectorySumOfSquares += errorSquare;
                trajectoryValidCount++;
            }
        }
        
        // 计算弹道准确度百分比
        double trajectoryAccuracy = calculateAccuracyPercentage(trajectorySumOfSquares, trajectoryValidCount);
        
        // 计算加权总准确度：弹道准确度权重 * 得分
        double totalAccuracy = trajectoryAccuracy * trajectoryWeight;
        
        // 将结果存入映射
        maps.put("trajectory_accuracy", trajectoryAccuracy);
        maps.put("trajectory_weight", trajectoryWeight);
        maps.put("total_weighted_accuracy", totalAccuracy);
        
        return maps;
    }
    //导弹性能参数准确度
    /**
     * 导弹性能参数准确度
     *武器杀伤半径准确度权重 * 得分+ 导引头截获距离准确度权重 * 得分
     */
    @Override
    public Map<String, Object> listMissilePerformanceParameterAccuracy() {
        Map<String, Object> maps = new HashMap<>();
        
        // 获取权重映射
        HashMap<String, Double> weightMap = getWeightMap();
        double killRadiusWeight = weightMap != null ? weightMap.getOrDefault(WeightType.WEAPON_KILL_RADIUS_ACCURACY.getValue(), 0.5) : 0.5;
        double acquisitionRangeWeight = weightMap != null ? weightMap.getOrDefault(WeightType.SEARCHER_ACQUISITION_RANGE_ACCURACY.getValue(), 0.5) : 0.5;
        
        // 查询导弹性能参数准确度数据
        PerformanceAccuracyExcelVo queryVo = new PerformanceAccuracyExcelVo();
        queryVo.setDimension(PerformanceConstants.DIMENSION_MISSILE_PERFORMANCE);
        
        // 标准数据
        List<PerformanceAccuracyExcelVo> standardList = weightMapper.selectPerformanceAccuracyExcelVo_standard(queryVo);
        // 测试数据
        List<PerformanceAccuracyExcelVo> testList = weightMapper.selectPerformanceAccuracyExcelVo_test(queryVo);
        
        // 按类型分组处理数据
        Map<String, PerformanceAccuracyExcelVo> standardMap = standardList.stream()
                .collect(Collectors.toMap(PerformanceAccuracyExcelVo::getVariable, vo -> vo));
        Map<String, PerformanceAccuracyExcelVo> testMap = testList.stream()
                .collect(Collectors.toMap(PerformanceAccuracyExcelVo::getVariable, vo -> vo));
        
        // 计算武器杀伤半径准确度
        PerformanceAccuracyExcelVo killRadiusStandard = standardMap.getOrDefault(WeightType.WEAPON_KILL_RADIUS_ACCURACY.getValue(), null);
        PerformanceAccuracyExcelVo killRadiusTest = testMap.getOrDefault(WeightType.WEAPON_KILL_RADIUS_ACCURACY.getValue(), null);
        double killRadiusAccuracy = 0;
        
        if (killRadiusStandard != null && killRadiusTest != null) {
            String standardValue = killRadiusStandard.getVariableValue();
            String testValue = killRadiusTest.getVariableValue();
            double errorSquare = calculateRelativeErrorSquare(standardValue, testValue);
            killRadiusAccuracy = calculateAccuracyPercentage(errorSquare, 1);
        }
        
        // 计算导引头截获距离准确度
        PerformanceAccuracyExcelVo acquisitionRangeStandard = standardMap.getOrDefault(WeightType.SEARCHER_ACQUISITION_RANGE_ACCURACY.getValue(), null);
        PerformanceAccuracyExcelVo acquisitionRangeTest = testMap.getOrDefault(WeightType.SEARCHER_ACQUISITION_RANGE_ACCURACY.getValue(), null);
        double acquisitionRangeAccuracy = 0;
        
        if (acquisitionRangeStandard != null && acquisitionRangeTest != null) {
            String standardValue = acquisitionRangeStandard.getVariableValue();
            String testValue = acquisitionRangeTest.getVariableValue();
            double errorSquare = calculateRelativeErrorSquare(standardValue, testValue);
            acquisitionRangeAccuracy = calculateAccuracyPercentage(errorSquare, 1);
        }
        
        // 计算加权总准确度：武器杀伤半径准确度权重 * 得分+ 导引头截获距离准确度权重 * 得分
        double totalAccuracy = (killRadiusAccuracy * killRadiusWeight) + (acquisitionRangeAccuracy * acquisitionRangeWeight);
        
        // 将结果存入映射
        maps.put("kill_radius_accuracy", killRadiusAccuracy);
        maps.put("acquisition_range_accuracy", acquisitionRangeAccuracy);
        maps.put("kill_radius_weight", killRadiusWeight);
        maps.put("acquisition_range_weight", acquisitionRangeWeight);
        maps.put("total_weighted_accuracy", totalAccuracy);
        
        return maps;
    }
    //导弹发动机模型准确度
    /**
     * 导弹发动机模型准确度
     *发动机工作时间准确度权重 * 得分+ 导弹射程准确度权重 * 得分
     */
    @Override
    public Map<String, Object> listMissileEngineModelAccuracy() {
        Map<String, Object> maps = new HashMap<>();
        
        // 获取权重映射
        HashMap<String, Double> weightMap = getWeightMap();
        double operatingTimeWeight = weightMap != null ? weightMap.getOrDefault(WeightType.ENGINE_OPERATING_TIME_ACCURACY.getValue(), 0.5) : 0.5;
        double missileRangeWeight = weightMap != null ? weightMap.getOrDefault(WeightType.MISSILE_RANGE_ACCURACY.getValue(), 0.5) : 0.5;
        
        // 查询导弹发动机模型准确度数据
        PerformanceAccuracyExcelVo queryVo = new PerformanceAccuracyExcelVo();
        queryVo.setDimension(PerformanceConstants.DIMENSION_MISSILE_ENGINE);
        
        // 标准数据
        List<PerformanceAccuracyExcelVo> standardList = weightMapper.selectPerformanceAccuracyExcelVo_standard(queryVo);
        // 测试数据
        List<PerformanceAccuracyExcelVo> testList = weightMapper.selectPerformanceAccuracyExcelVo_test(queryVo);
        
        // 按类型分组处理数据
        Map<String, PerformanceAccuracyExcelVo> standardMap = standardList.stream()
                .collect(Collectors.toMap(PerformanceAccuracyExcelVo::getVariable, vo -> vo));
        Map<String, PerformanceAccuracyExcelVo> testMap = testList.stream()
                .collect(Collectors.toMap(PerformanceAccuracyExcelVo::getVariable, vo -> vo));
        
        // 计算发动机工作时间准确度
        PerformanceAccuracyExcelVo operatingTimeStandard = standardMap.getOrDefault(WeightType.ENGINE_OPERATING_TIME_ACCURACY.getValue(), null);
        PerformanceAccuracyExcelVo operatingTimeTest = testMap.getOrDefault(WeightType.ENGINE_OPERATING_TIME_ACCURACY.getValue(), null);
        double operatingTimeAccuracy = 0;
        
        if (operatingTimeStandard != null && operatingTimeTest != null) {
            String standardValue = operatingTimeStandard.getVariableValue();
            String testValue = operatingTimeTest.getVariableValue();
            double errorSquare = calculateRelativeErrorSquare(standardValue, testValue);
            operatingTimeAccuracy = calculateAccuracyPercentage(errorSquare, 1);
        }
        
        // 计算导弹射程准确度
        PerformanceAccuracyExcelVo missileRangeStandard = standardMap.getOrDefault(WeightType.MISSILE_RANGE_ACCURACY.getValue(), null);
        PerformanceAccuracyExcelVo missileRangeTest = testMap.getOrDefault(WeightType.MISSILE_RANGE_ACCURACY.getValue(), null);
        double missileRangeAccuracy = 0;
        
        if (missileRangeStandard != null && missileRangeTest != null) {
            String standardValue = missileRangeStandard.getVariableValue();
            String testValue = missileRangeTest.getVariableValue();
            double errorSquare = calculateRelativeErrorSquare(standardValue, testValue);
            missileRangeAccuracy = calculateAccuracyPercentage(errorSquare, 1);
        }
        
        // 计算加权总准确度：发动机工作时间准确度权重 * 得分+ 导弹射程准确度权重 * 得分
        double totalAccuracy = (operatingTimeAccuracy * operatingTimeWeight) + (missileRangeAccuracy * missileRangeWeight);
        
        // 将结果存入映射
        maps.put("operating_time_accuracy", operatingTimeAccuracy);
        maps.put("missile_range_accuracy", missileRangeAccuracy);
        maps.put("operating_time_weight", operatingTimeWeight);
        maps.put("missile_range_weight", missileRangeWeight);
        maps.put("total_weighted_accuracy", totalAccuracy);
        
        return maps;
    }
    //飞行仿真性能准确度
    /**
     * 飞行仿真性能准确度
     * 飞行控制系统模型准确度权重 * 得分+  发动机推力模型准确度权重 * 得分+  飞行加速性能准确度权重 * 得分+  飞行转弯性能准确度权重 * 得分
     */
    @Override
    public Map<String, Object> listFlightSimulationPerformanceAccuracy() {
        Map<String, Object> maps = new HashMap<>();
        
        // 获取权重映射
        HashMap<String, Double> weightMap = getWeightMap();
        
        // 获取各子项权重，默认值为0.25（四个子项平均）
        double flightControlWeight = weightMap != null ? weightMap.getOrDefault(WeightType.FLIGHT_CONTROL_SYSTEM_ACCURACY.getValue(), 0.25) : 0.25;
        double engineThrustWeight = weightMap != null ? weightMap.getOrDefault(WeightType.ENGINE_THRUST_MODEL_ACCURACY.getValue(), 0.25) : 0.25;
        double flightAccelerationWeight = weightMap != null ? weightMap.getOrDefault(WeightType.FLIGHT_ACCELERATION_PERFORMANCE_ACCURACY.getValue(), 0.25) : 0.25;
        double flightTurningWeight = weightMap != null ? weightMap.getOrDefault(WeightType.FLIGHT_TURNING_PERFORMANCE_ACCURACY.getValue(), 0.25) : 0.25;
        
        // 调用各子方法获取结果
        Map<String, Object> flightControlResult = this.listFlightPerformanceAccuracy();
        Map<String, Object> engineThrustResult = this.listEnginePowerModelAccuracy();
        Map<String, Object> flightAccelerationResult = this.listFlightAccelerationAccuracy();
        Map<String, Object> flightTurningResult = this.listFlightTurningAccuracy();
        
        // 获取各子项的加权得分
        double flightControlScore = (double) flightControlResult.getOrDefault("weighted_total_score", 0.0);
        double engineThrustScore = (double) engineThrustResult.getOrDefault("weighted_total_score", 0.0);
        double flightAccelerationScore = (double) flightAccelerationResult.getOrDefault("weighted_total_score", 0.0);
        double flightTurningScore = (double) flightTurningResult.getOrDefault("total_weighted_accuracy", 0.0);
        
        // 计算总加权准确度
        double totalAccuracy = (flightControlScore * flightControlWeight) + 
                              (engineThrustScore * engineThrustWeight) + 
                              (flightAccelerationScore * flightAccelerationWeight) + 
                              (flightTurningScore * flightTurningWeight);
        
        // 将结果存入映射
        maps.put("flight_control_accuracy", flightControlScore);
        maps.put("engine_thrust_accuracy", engineThrustScore);
        maps.put("flight_acceleration_accuracy", flightAccelerationScore);
        maps.put("flight_turning_accuracy", flightTurningScore);
        maps.put("flight_control_weight", flightControlWeight);
        maps.put("engine_thrust_weight", engineThrustWeight);
        maps.put("flight_acceleration_weight", flightAccelerationWeight);
        maps.put("flight_turning_weight", flightTurningWeight);
        maps.put("total_weighted_accuracy", totalAccuracy);
        
        return maps;
    }
    //雷达性能准确度
    /**
     * 雷达性能准确度
     * 雷达扫描范围准确度权重 * 得分+  雷达跟踪效能准确度权重 * 得分
     * 雷达扫描范围准确度
     * 雷达跟踪效能准确度
     */
    @Override
    public Map<String, Object> listRadarPerformanceAccuracy() {
        Map<String, Object> maps = new HashMap<>();
        
        // 获取权重映射
        HashMap<String, Double> weightMap = getWeightMap();
        
        // 获取各子项权重，默认值为0.5（两个子项平均）
        double radarScanRangeWeight = weightMap != null ? weightMap.getOrDefault(WeightType.RADAR_SCAN_RANGE_ACCURACY.getValue(), 0.5) : 0.5;
        double radarTrackingEfficiencyWeight = weightMap != null ? weightMap.getOrDefault(WeightType.RADAR_TRACKING_EFFICIENCY_ACCURACY.getValue(), 0.5) : 0.5;
        
        // 调用各子方法获取结果
        Map<String, Object> radarScanRangeResult = this.listRadarScanRangeAccuracy();
        Map<String, Object> radarTrackingEfficiencyResult = this.listRadarTrackingEfficiencyAccuracy();
        
        // 获取各子项的加权得分
        double radarScanRangeScore = (double) radarScanRangeResult.getOrDefault("total_weighted_accuracy", 0.0);
        double radarTrackingEfficiencyScore = (double) radarTrackingEfficiencyResult.getOrDefault("total_weighted_accuracy", 0.0);
        
        // 计算总加权准确度
        double totalAccuracy = (radarScanRangeScore * radarScanRangeWeight) + 
                              (radarTrackingEfficiencyScore * radarTrackingEfficiencyWeight);
        
        // 将结果存入映射
        maps.put("radar_scan_range_accuracy", radarScanRangeScore);
        maps.put("radar_tracking_efficiency_accuracy", radarTrackingEfficiencyScore);
        maps.put("radar_scan_range_weight", radarScanRangeWeight);
        maps.put("radar_tracking_efficiency_weight", radarTrackingEfficiencyWeight);
        maps.put("total_weighted_accuracy", totalAccuracy);
        
        return maps;
    }
    //导弹仿真系统性能准确度
    /**
     * 导弹仿真系统性能准确度
     * 导弹弹道准确度权重 * 得分+ 导弹性能参数准确度权重 * 得分+ 导弹发动机模型准确度权重 * 得分
     */
    @Override
    public Map<String, Object> listMissileSimulationSystemPerformanceAccuracy() {
        Map<String, Object> maps = new HashMap<>();
        
        // 获取权重映射
        HashMap<String, Double> weightMap = getWeightMap();
        
        // 获取各子项权重，默认值为1/3（三个子项平均）
        double missileTrajectoryWeight = weightMap != null ? weightMap.getOrDefault(WeightType.MISSILE_TRAJECTORY_ACCURACY.getValue(), 1.0/3) : 1.0/3;
        double missilePerformanceWeight = weightMap != null ? weightMap.getOrDefault(WeightType.MISSILE_PERFORMANCE_PARAMETER_ACCURACY.getValue(), 1.0/3) : 1.0/3;
        double missileEngineWeight = weightMap != null ? weightMap.getOrDefault(WeightType.MISSILE_ENGINE_MODEL_ACCURACY.getValue(), 1.0/3) : 1.0/3;
        
        // 调用各子方法获取结果
        Map<String, Object> missileTrajectoryResult = this.listMissileTrajectoryAccuracy();
        Map<String, Object> missilePerformanceResult = this.listMissilePerformanceParameterAccuracy();
        Map<String, Object> missileEngineResult = this.listMissileEngineModelAccuracy();
        
        // 获取各子项的加权得分
        double missileTrajectoryScore = (double) missileTrajectoryResult.getOrDefault("total_weighted_accuracy", 0.0);
        double missilePerformanceScore = (double) missilePerformanceResult.getOrDefault("total_weighted_accuracy", 0.0);
        double missileEngineScore = (double) missileEngineResult.getOrDefault("total_weighted_accuracy", 0.0);
        
        // 计算总加权准确度
        double totalAccuracy = (missileTrajectoryScore * missileTrajectoryWeight) + 
                              (missilePerformanceScore * missilePerformanceWeight) + 
                              (missileEngineScore * missileEngineWeight);
        
        // 将结果存入映射
        maps.put("missile_trajectory_accuracy", missileTrajectoryScore);
        maps.put("missile_performance_parameter_accuracy", missilePerformanceScore);
        maps.put("missile_engine_model_accuracy", missileEngineScore);
        maps.put("missile_trajectory_weight", missileTrajectoryWeight);
        maps.put("missile_performance_parameter_weight", missilePerformanceWeight);
        maps.put("missile_engine_model_weight", missileEngineWeight);
        maps.put("total_weighted_accuracy", totalAccuracy);
        
        return maps;
    }
    //性能准确度
    /**
     * 性能准确度
     * 飞行仿真性能准确度 权重 * 得分+  雷达性能准确度权重 * 得分+  导弹仿真系统性能准确度权重 * 得分
     * ================================返回格式===================================================
     * 返回值格式统一用
     *       {
     *          dataKind:支持只能决策模型生成与评估,//维度
     *          weight: 0.5,//权重
     *          score: 0.5,//得分
     *      }
     */
    @Override
    public Map<String, Object> listPerformanceAccuracy() {
        Map<String, Object> resultMap = new HashMap<>();
        
        try {
            // 创建三个任务，分别执行不同的性能计算
            Callable<Map<String, Object>> flightSimulationTask = this::listFlightSimulationPerformanceAccuracy;
            Callable<Map<String, Object>> radarPerformanceTask = this::listRadarPerformanceAccuracy;
            Callable<Map<String, Object>> missileSimulationTask = this::listMissileSimulationSystemPerformanceAccuracy;
            
            // 提交任务到线程池并获取Future对象
            Future<Map<String, Object>> flightSimulationFuture = executorService.submit(flightSimulationTask);
            Future<Map<String, Object>> radarPerformanceFuture = executorService.submit(radarPerformanceTask);
            Future<Map<String, Object>> missileSimulationFuture = executorService.submit(missileSimulationTask);
            
            // 获取任务结果
            Map<String, Object> flightSimulationResult = flightSimulationFuture.get();
            Map<String, Object> radarPerformanceResult = radarPerformanceFuture.get();
            Map<String, Object> missileSimulationResult = missileSimulationFuture.get();
            
            // 获取各子项的加权得分
            double flightSimulationScore = (double) flightSimulationResult.getOrDefault("total_weighted_accuracy", 0.0);
            double radarPerformanceScore = (double) radarPerformanceResult.getOrDefault("total_weighted_accuracy", 0.0);
            double missileSimulationScore = (double) missileSimulationResult.getOrDefault("total_weighted_accuracy", 0.0);
            
            // 保留2位小数
            flightSimulationScore = BigDecimal.valueOf(flightSimulationScore)
                                            .setScale(2, RoundingMode.DOWN)
                                            .doubleValue();
            radarPerformanceScore = BigDecimal.valueOf(radarPerformanceScore)
                                           .setScale(2, RoundingMode.DOWN)
                                           .doubleValue();
            missileSimulationScore = BigDecimal.valueOf(missileSimulationScore)
                                            .setScale(2, RoundingMode.DOWN)
                                            .doubleValue();
            
            // 打印各子项得分日志
            logger.info("获取各子项加权得分：飞行仿真性能得分={}, 雷达性能得分={}, 导弹仿真系统性能得分={}", 
                       flightSimulationScore, radarPerformanceScore, missileSimulationScore);
            
            // 获取权重
            HashMap<String, Double> weightMap = this.getWeightMap();
            // 从权重映射中获取各子项的权重，默认值设置为1/3
            double flightSimulationWeight = weightMap != null ? weightMap.getOrDefault(WeightType.FLIGHT_SIMULATION_PERFORMANCE_ACCURACY.getValue(), 1.0 / 3) : 1.0 / 3;
            double radarPerformanceWeight = weightMap != null ? weightMap.getOrDefault(WeightType.RADAR_PERFORMANCE_ACCURACY.getValue(), 1.0 / 3) : 1.0 / 3;
            double missileSimulationWeight = weightMap != null ? weightMap.getOrDefault(WeightType.MISSILE_SIMULATION_SYSTEM_PERFORMANCE_ACCURACY.getValue(), 1.0 / 3) : 1.0 / 3;
            
            // 打印各子项权重日志
            logger.info("获取各子项权重：飞行仿真性能权重={}, 雷达性能权重={}, 导弹仿真系统性能权重={}", 
                       flightSimulationWeight, radarPerformanceWeight, missileSimulationWeight);
            
            // 计算总加权准确度：飞行仿真性能准确度权重 * 得分+  雷达性能准确度权重 * 得分+  导弹仿真系统性能准确度权重 * 得分
            double totalAccuracy = (flightSimulationScore * flightSimulationWeight) + 
                                  (radarPerformanceScore * radarPerformanceWeight) + 
                                  (missileSimulationScore * missileSimulationWeight);
            
            // 保留2位小数
            totalAccuracy = BigDecimal.valueOf(totalAccuracy)
                                     .setScale(2, RoundingMode.DOWN)
                                     .doubleValue();
            
            // 打印总加权准确度日志
            logger.info("计算总加权准确度结果：总得分={}", totalAccuracy);
            
            // 按照统一格式设置返回结果
            resultMap.put("dataKind", WeightType.PERFORMANCE_ACCURACY.getValue()); // 维度
            // 获取性能准确度权重，默认值为1.0
            double performanceWeight = weightMap != null ? weightMap.getOrDefault(WeightType.PERFORMANCE_ACCURACY.getValue(), 1.0) : 1.0;
            
            // 打印性能准确度权重日志
            logger.info("获取性能准确度权重：权重={}", performanceWeight);
            
            resultMap.put("weight", performanceWeight*100); // 权重
            resultMap.put("score", totalAccuracy); // 得分

        } catch (InterruptedException | ExecutionException e) {
            // 处理异常情况
            e.printStackTrace();
            resultMap.put("error", "计算过程中发生异常: " + e.getMessage());
        }
        
        return resultMap;
    }

    private HashMap<String,Double> getWeightMap(){
        WeightMangeData weightMangeData1 = new WeightMangeData();
        weightMangeData1.setGroupId(1L);
        List<WeightMangeData> weightMangeDatasList = weightMangeMapper.selectWeightTreeAll(weightMangeData1);

        if(CollectionUtils.isEmpty(weightMangeDatasList)){
            return null;
        }
        HashMap<String,Double> weightMap = new HashMap<>();
        for (WeightMangeData weightMangeData:weightMangeDatasList) {
            weightMap.put(weightMangeData.getName(),weightMangeData.getWeight());
        }
        return weightMap;
    }
}
