package com.kgd.web.controller.datasource;

import com.alibaba.fastjson2.JSONArray;
import com.kgd.common.annotation.Log;
import com.kgd.common.config.KgdConfig;
import com.kgd.common.core.controller.BaseController;
import com.kgd.common.core.domain.AjaxResult;
import com.kgd.common.core.page.TableDataInfo;
import com.kgd.common.enums.BusinessType;
import com.kgd.common.enums.WeightType;
import com.kgd.common.utils.poi.ExcelUtil;
import com.kgd.datasource.domain.Weight;
import com.kgd.datasource.domain.ComprehensiveEvalua;
import com.kgd.datasource.domain.vo.*;
import com.kgd.datasource.mapper.MaxAccelerationStandardMapper;
import com.kgd.datasource.mapper.MissileTrajectoryMapper;
import com.kgd.datasource.mapper.WeightMapper;
import com.kgd.datasource.service.FlightService;
import com.kgd.datasource.service.IWeightService;
import com.kgd.evaluate.domain.WeightMangeData;
import com.kgd.evaluate.domain.vo.CommonVo;
import com.kgd.evaluate.service.IEvaluateIntelligentDecisionService;
import com.kgd.evaluate.service.ISystemFunctionScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import com.kgd.evaluate.service.IEvaluateCommonService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 权值Controller
 *
 * @author kgd
 * @date 2025-11-13
 */
@RestController
@RequestMapping("/datasource/weight")
public class WeightController extends BaseController {
    @Resource
    private IWeightService weightService;

    @Resource
    private WeightMapper weightMapper;

    @Resource
    private MissileTrajectoryMapper missileTrajectoryMapper;

    @Resource
    private MaxAccelerationStandardMapper maxAccelerationStandardMapper;
    @Autowired
    private FlightService flightService;

    @Resource
    private IEvaluateCommonService iEvaluateCommonService;

    @Resource
    private IEvaluateIntelligentDecisionService iEvaluateIntelligentDecisionService;

    @Resource
    private ISystemFunctionScoreService iSystemFunctionScoreService;
    /**
     * 查询权值列表
     */
//    @PreAuthorize("@ss.hasPermi('datasource:weight:list')")
    @GetMapping("/list")
    public TableDataInfo list(Weight weight) {
        startPage();
        List<Weight> list = weightService.selectWeightList(weight);
        return getDataTable(list);
    }

    /**
     * 导出权值列表
     */
//    @PreAuthorize("@ss.hasPermi('datasource:weight:export')")
    @Log(title = "权值", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Weight weight) {
        List<Weight> list = weightService.selectWeightList(weight);
        ExcelUtil<Weight> util = new ExcelUtil<Weight>(Weight.class);
        util.exportExcel(response, list, "权值数据");
    }

    /**
     * 获取权值详细信息
     */
//    @PreAuthorize("@ss.hasPermi('datasource:weight:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(weightService.selectWeightById(id));
    }

    /**
     * 新增权值
     */
//    @PreAuthorize("@ss.hasPermi('datasource:weight:add')")
    @Log(title = "权值", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Weight weight) {
        return toAjax(weightService.insertWeight(weight));
    }

    /**
     * 修改权值
     */
//    @PreAuthorize("@ss.hasPermi('datasource:weight:edit')")
    @Log(title = "权值", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Weight weight) {
        return toAjax(weightService.updateWeight(weight));
    }

    /**
     * 删除权值
     */
//    @PreAuthorize("@ss.hasPermi('datasource:weight:remove')")
    @Log(title = "权值", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(weightService.deleteWeightByIds(ids));
    }

    /**
     * 清空权值
     */
//    @PreAuthorize("@ss.hasPermi('datasource:weight:remove')")
    @Log(title = "清空权值", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteWeight")
    public AjaxResult deleteWeight() {
        return toAjax(weightService.deleteWeight());
    }

    /**
     * 权值更新
     */
//    @PreAuthorize("@ss.hasPermi('datasource:weight:remove')")
    @Log(title = "权值更新", businessType = BusinessType.INSERT)
    @PostMapping("/loadWeightData")
    public AjaxResult loadWeightData() throws Exception {
        weightService.loadWeightData();
        return AjaxResult.success();
    }

    private final String algorithmType1 = "performance_accuracy";
    private final String algorithmType2 = "maximum_overload";
    private final String algorithmType3 = "fuel_consumption";
    private final String algorithmType4 = "heading_angle";
    private final String algorithmType5 = "roll_angle";
    private final String algorithmType6 = "speed";
    private final String algorithmType7 = "max_speed";
    private final String algorithmType8 = "max_acceleration";

    private final String standardData = "0";

    @Log(title = "导入准确度", businessType = BusinessType.IMPORT)
    @PostMapping("/uploadPerformanceAccuracy")
    public AjaxResult uploadPerformanceAccuracy(String path,String algorithmDifferentiation,String flag) throws Exception {

        System.out.println(algorithmDifferentiation + "tttttttttt");

        // 处理文件路径：如果路径是相对路径（以 /profile/upload/ 或 /upload/ 开头），需要转换为绝对路径
        String actualPath = path;
        if (path.startsWith("/profile/upload/")) {
            // 去掉 /profile 前缀，然后拼接实际的上传路径
            actualPath = KgdConfig.getUploadPath() + path.substring("/profile/upload".length());
        } else if (path.startsWith("/upload/")) {
            // 直接拼接 profile 路径
            actualPath = KgdConfig.getUploadPath() + path.substring("/upload".length());
        } else if (path.startsWith("/profile/")) {
            // 如果是以 /profile/ 开头但不是 /upload/，也处理
            actualPath = KgdConfig.getProfile() + path.substring("/profile".length());
        }

        // 文件路径
        Path filePath = Paths.get(actualPath);

        if (algorithmDifferentiation.equals(algorithmType1)) {
            if (flag.equals(standardData)) {
                weightMapper.deletePerformanceAccuracyExcelVo_standard(0L);
                ExcelUtil<PerformanceAccuracyExcelVo> excelUtil = new ExcelUtil<>(PerformanceAccuracyExcelVo.class);
                List<PerformanceAccuracyExcelVo> ExcelDataVoList = excelUtil.importExcel(Files.newInputStream(filePath), 0);

                for (int i = 0; i < ExcelDataVoList.size(); i++)
                {
                    PerformanceAccuracyExcelVo element = ExcelDataVoList.get(i);
                    weightMapper.insertPerformanceAccuracyExcelVo_standard(element);
                }
            }
            else {
                weightMapper.deletePerformanceAccuracyExcelVo_test(0L);
                ExcelUtil<PerformanceAccuracyExcelVo> excelUtil = new ExcelUtil<>(PerformanceAccuracyExcelVo.class);
                List<PerformanceAccuracyExcelVo> ExcelDataVoList = excelUtil.importExcel(Files.newInputStream(filePath), 0);

                for (int i = 0; i < ExcelDataVoList.size(); i++)
                {
                    PerformanceAccuracyExcelVo element = ExcelDataVoList.get(i);
                    weightMapper.insertPerformanceAccuracyExcelVo_test(element);
                }
            }
        }

        if (algorithmDifferentiation.equals(algorithmType2)) {
            if (flag.equals(standardData)) {
                weightMapper.deleteMaxOverloadExcelVo_standard(0L);
                ExcelUtil<MaxOverloadExcelVo> excelUtil = new ExcelUtil<>(MaxOverloadExcelVo.class);
                List<MaxOverloadExcelVo> ExcelDataVoList = excelUtil.importExcel(Files.newInputStream(filePath), 0);

                for (int i = 0; i < ExcelDataVoList.size(); i++)
                {
                    MaxOverloadExcelVo element = ExcelDataVoList.get(i);
                    weightMapper.insertMaxOverloadExcelVo_standard(element);
                }
            }
            else {
                weightMapper.deleteMaxOverloadExcelVo_test(0L);
                ExcelUtil<MaxOverloadExcelVo> excelUtil = new ExcelUtil<>(MaxOverloadExcelVo.class);
                List<MaxOverloadExcelVo> ExcelDataVoList = excelUtil.importExcel(Files.newInputStream(filePath), 0);

                for (int i = 0; i < ExcelDataVoList.size(); i++)
                {
                    MaxOverloadExcelVo element = ExcelDataVoList.get(i);
                    // 最后一个元素.
//                    if (i != ExcelDataVoList.size() - 1)
//                    {
                    weightMapper.insertMaxOverloadExcelVo_test(element);
//                    }
//                    else
//                    {
                    // 最后一个元素.
//                        WeightRecord record = new WeightRecord();
//                        record.setName("最大过载");
//                        record.setScore(element.getOverload());
//                        weightMapper.updateWeightRecord_score(record);
//                    }
                }
            }
        }

        if (algorithmDifferentiation.equals(algorithmType3)) {
            if (flag.equals(standardData)) {
                ExcelUtil<FuelConsumptionExcelVo> excelUtil = new ExcelUtil<>(FuelConsumptionExcelVo.class);
                List<FuelConsumptionExcelVo> ExcelDataVoList = excelUtil.importExcel(Files.newInputStream(filePath), 0);

                weightMapper.deleteFuelConsumptionExcelVo_standard(0L);
                for (int i = 0; i < ExcelDataVoList.size(); i++)
                {
                    FuelConsumptionExcelVo element = ExcelDataVoList.get(i);
                    weightMapper.insertFuelConsumptionExcelVo_standard(element);
                }
            }
            else {
                ExcelUtil<FuelConsumptionExcelVo> excelUtil = new ExcelUtil<>(FuelConsumptionExcelVo.class);
                List<FuelConsumptionExcelVo> ExcelDataVoList = excelUtil.importExcel(Files.newInputStream(filePath), 0);
                weightMapper.deleteFuelConsumptionExcelVo_test(0L);
                for (int i = 0; i < ExcelDataVoList.size(); i++)
                {
                    FuelConsumptionExcelVo element = ExcelDataVoList.get(i);
//                    if (i != ExcelDataVoList.size() - 1)
//                    {
                    weightMapper.insertFuelConsumptionExcelVo_test(element);
//                    }
//                    else // 最后一个元素.
//                    {
//                        WeightRecord record = new WeightRecord();
//
//                        record.setName("6000米油耗");
//                        record.setScore(element.getFuelConsumption6000());
//                        weightMapper.updateWeightRecord_score(record);
//
//                        record.setName("8000米油耗");
//                        record.setScore(element.getFuelConsumption8000());
//                        weightMapper.updateWeightRecord_score(record);
//
//                        record.setName("10000米油耗");
//                        record.setScore(element.getFuelConsumption10000());
//                        weightMapper.updateWeightRecord_score(record);
//
//                        record.setName("12000米油耗");
//                        record.setScore(element.getFuelConsumption12000());
//                        weightMapper.updateWeightRecord_score(record);
//
//                        record.setName("14000米油耗");
//                        record.setScore(element.getFuelConsumption14000());
//                        weightMapper.updateWeightRecord_score(record);
//                    }
                }
            }
        }

        if (algorithmDifferentiation.equals(algorithmType4)) {
            if (flag.equals(standardData)) {
                ExcelUtil<HeadingAngleExcelVo> excelUtil = new ExcelUtil<>(HeadingAngleExcelVo.class);
                List<HeadingAngleExcelVo> ExcelDataVoList = excelUtil.importExcel(Files.newInputStream(filePath), 0);

                weightMapper.deleteHeadingAngleExcelVo_standard(0L);
                for (int i = 0; i < ExcelDataVoList.size(); i++)
                {
                    HeadingAngleExcelVo element = ExcelDataVoList.get(i);
                    weightMapper.insertHeadingAngleExcelVo_standard(element);
                }
            }
            else {
                ExcelUtil<HeadingAngleExcelVo> excelUtil = new ExcelUtil<>(HeadingAngleExcelVo.class);
                List<HeadingAngleExcelVo> ExcelDataVoList = excelUtil.importExcel(Files.newInputStream(filePath), 0);

                weightMapper.deleteHeadingAngleExcelVo_test(0L);
                for (int i = 0; i < ExcelDataVoList.size(); i++)
                {
                    HeadingAngleExcelVo element = ExcelDataVoList.get(i);
                    weightMapper.insertHeadingAngleExcelVo_test(element);
                }
            }
        }

        if (algorithmDifferentiation.equals(algorithmType5)) {
            if (flag.equals(standardData)) {
                ExcelUtil<RollAngleExcelVo> excelUtil = new ExcelUtil<>(RollAngleExcelVo.class);
                List<RollAngleExcelVo> ExcelDataVoList = excelUtil.importExcel(Files.newInputStream(filePath), 0);
                weightMapper.deleteRollAngleExcelVo_standard(0L);
                for (int i = 0; i < ExcelDataVoList.size(); i++)
                {
                    RollAngleExcelVo element = ExcelDataVoList.get(i);
                    weightMapper.insertRollAngleExcelVo_standard(element);
                }
            }
            else {
                ExcelUtil<RollAngleExcelVo> excelUtil = new ExcelUtil<>(RollAngleExcelVo.class);
                List<RollAngleExcelVo> ExcelDataVoList = excelUtil.importExcel(Files.newInputStream(filePath), 0);
                weightMapper.deleteRollAngleExcelVo_test(0L);
                for (int i = 0; i < ExcelDataVoList.size(); i++)
                {
                    RollAngleExcelVo element = ExcelDataVoList.get(i);
                    weightMapper.insertRollAngleExcelVo_test(element);
                }
            }
        }

        if (algorithmDifferentiation.equals(algorithmType6)) {
            if (flag.equals(standardData)) {
                ExcelUtil<SpeedCommandExcelVo> excelUtil = new ExcelUtil<>(SpeedCommandExcelVo.class);
                List<SpeedCommandExcelVo> ExcelDataVoList = excelUtil.importExcel(Files.newInputStream(filePath), 0);
                weightMapper.deleteSpeedCommandExcelVo_standard(0L);
                for (int i = 0; i < ExcelDataVoList.size(); i++)
                {
                    SpeedCommandExcelVo element = ExcelDataVoList.get(i);
                    weightMapper.insertSpeedCommandExcelVo_standard(element);
                }
            }
            else {
                ExcelUtil<SpeedCommandExcelVo> excelUtil = new ExcelUtil<>(SpeedCommandExcelVo.class);
                List<SpeedCommandExcelVo> ExcelDataVoList = excelUtil.importExcel(Files.newInputStream(filePath), 0);
                weightMapper.deleteSpeedCommandExcelVo_test(0L);
                for (int i = 0; i < ExcelDataVoList.size(); i++)
                {
                    SpeedCommandExcelVo element = ExcelDataVoList.get(i);
                    weightMapper.insertSpeedCommandExcelVo_test(element);
                }
            }
        }

        //最大速度0加速度1，标准0，测试1
        //设置最大速度，setEvaluateType=0，
        if (algorithmDifferentiation.equals(algorithmType7)) {
            ExcelUtil<MaxAccelerationStandardVo> excelUtil = new ExcelUtil<>(MaxAccelerationStandardVo.class);
            List<MaxAccelerationStandardVo> maxAccelerationStandardVoList = excelUtil.importExcel(Files.newInputStream(filePath), 0);
            if (flag.equals(standardData)) {
                maxAccelerationStandardVoList.forEach(e->{e.setEvaluateType(0);e.setSpeedOrTest(0);});
                maxAccelerationStandardMapper.deleteMaxAccelerationStandardByType(0,0);
                maxAccelerationStandardMapper.insertMaxAccelerationStandardBatch(maxAccelerationStandardVoList);
            }else {
                maxAccelerationStandardVoList.forEach(e->{e.setEvaluateType(0);e.setSpeedOrTest(1);});
                maxAccelerationStandardMapper.deleteMaxAccelerationStandardByType(0,1);
                maxAccelerationStandardMapper.insertMaxAccelerationStandardBatch(maxAccelerationStandardVoList);
            }
        }

        //设置最大加速度，setEvaluateType=1
        if (algorithmDifferentiation.equals(algorithmType8)) {
            ExcelUtil<MaxAccelerationStandardVo> excelUtil = new ExcelUtil<>(MaxAccelerationStandardVo.class);
            List<MaxAccelerationStandardVo> maxAccelerationStandardVoList = excelUtil.importExcel(Files.newInputStream(filePath), 0);
            if (flag.equals(standardData)) {
                maxAccelerationStandardVoList.forEach(e->{e.setEvaluateType(1);e.setSpeedOrTest(0);});
                maxAccelerationStandardMapper.deleteMaxAccelerationStandardByType(1,0);
                maxAccelerationStandardMapper.insertMaxAccelerationStandardBatch(maxAccelerationStandardVoList);
            }else {
                maxAccelerationStandardVoList.forEach(e->{e.setEvaluateType(1);e.setSpeedOrTest(1);});
                maxAccelerationStandardMapper.deleteMaxAccelerationStandardByType(1,1);
                maxAccelerationStandardMapper.insertMaxAccelerationStandardBatch(maxAccelerationStandardVoList);
            }
        }

        return AjaxResult.success();
    }
    @PostMapping("/selectPerformanceAccuracy")
    public AjaxResult selectPerformanceAccuracy(WeightMangeData weightMangeData) {
        Map<String, Object> maps = new HashMap<>();

        //1.1滚转角指令控制-标准
        maps.put("roll_angle0", weightMapper.selectRollAngleExcelVo_standard(null));
        maps.put("roll_angle1", weightMapper.selectRollAngleExcelVo_test(null));

        //2.1航向角指令-标准
        maps.put("heading_angle0", weightMapper.selectHeadingAngleExcelVo_standard(null));
        maps.put("heading_angle1", weightMapper.selectHeadingAngleExcelVo_test(null));

        //3.1速度指令控制-标准
        maps.put("speed0", weightMapper.selectSpeedCommandExcelVo_standard(null));
        maps.put("speed1", weightMapper.selectSpeedCommandExcelVo_test(null));

        //4.1耗油率-标准
        maps.put("fuel consumption0", weightMapper.selectFuelConsumptionExcelVo_standard(null));
        maps.put("fuel consumption1", weightMapper.selectFuelConsumptionExcelVo_test(null));

        //5.1最大过载-标准
        maps.put("maximum overload0", weightMapper.selectMaxOverloadExcelVo_standard(null));
        maps.put("maximum overload1", weightMapper.selectMaxOverloadExcelVo_test(null));

        //6.1性能准确度-标准
        maps.put("performance_accuracy0", weightMapper.selectPerformanceAccuracyExcelVo_standard(null));
        maps.put("performance_accuracy1", weightMapper.selectPerformanceAccuracyExcelVo_test(null));

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

        List<MaxAccelerationStandardVo> maxAccelerationStandard = allList.stream().filter(e -> e.getEvaluateType() == 1 && e.getSpeedOrTest() == 0).collect(Collectors.toList());
        List<MaxAccelerationStandardVo> maxAccelerationTest = allList.stream().filter(e -> e.getEvaluateType() == 1 && e.getSpeedOrTest() == 1).collect(Collectors.toList());
        MaxAccelerationStandardVo maxAccelerationScore = weightService.maxSpeed(maxAccelerationStandard, maxAccelerationTest);
        maps.put("maxAccelerationStandard",maxAccelerationStandard);
        maps.put("maxAccelerationTest",maxAccelerationTest);
        maps.put("maxAccelerationScore",maxAccelerationScore);
        return AjaxResult.success(maps);
    }

    /**
     * 获取弹道精准度数据
     */
    @Log(title = "弹道精准度", businessType = BusinessType.OTHER)
    @PostMapping("/missileTrajectory")
    public AjaxResult missileTrajectory() {
        List<MissileTrajectoryVo> missileTrajectoryVos = missileTrajectoryMapper.selectMissileTrajectoryAll();
        List<MissileTrajectoryVo> standard = missileTrajectoryVos.stream().filter(e -> e.getFlag() == 0).collect(Collectors.toList());
        List<MissileTrajectoryVo> test = missileTrajectoryVos.stream().filter(e -> e.getFlag() == 1).collect(Collectors.toList());
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("missileTrajectory0",standard);
        hashMap.put("missileTrajectory1",test);
        return AjaxResult.success(hashMap);
    }

    @Log(title = "导入弹道准确度", businessType = BusinessType.IMPORT)
    @PostMapping("/uploadMissileTrajectory")
    public AjaxResult uploadMissileTrajectory(String path,int flag){
        try {
            // 处理文件路径：如果路径是相对路径（以 /profile/upload/ 或 /upload/ 开头），需要转换为绝对路径
            String actualPath = path;
            if (path.startsWith("/profile/upload/")) {
                // 去掉 /profile 前缀，然后拼接实际的上传路径
                actualPath = KgdConfig.getUploadPath() + path.substring("/profile/upload".length());
            } else if (path.startsWith("/upload/")) {
                // 直接拼接 profile 路径
                actualPath = KgdConfig.getUploadPath() + path.substring("/upload".length());
            } else if (path.startsWith("/profile/")) {
                // 如果是以 /profile/ 开头但不是 /upload/，也处理
                actualPath = KgdConfig.getProfile() + path.substring("/profile".length());
            } else if (path.startsWith("./uploadPath/upload/")) {
                // 处理 ./uploadPath/upload/ 格式的路径，去掉 ./uploadPath 前缀
                actualPath = KgdConfig.getUploadPath() + path.substring("./uploadPath/upload".length());
            } else if (path.startsWith("uploadPath/upload/")) {
                // 处理 uploadPath/upload/ 格式的路径（没有 ./ 前缀）
                actualPath = KgdConfig.getUploadPath() + "/" + path.substring("uploadPath/upload".length());
            }
            // 文件路径
            Path filePath = Paths.get(actualPath);
            ExcelUtil<MissileTrajectoryVo> excelUtil = new ExcelUtil<>(MissileTrajectoryVo.class);
            List<MissileTrajectoryVo> importExcel = excelUtil.importExcel(Files.newInputStream(filePath), 0);
            importExcel.forEach(e->e.setFlag(flag));
            missileTrajectoryMapper.deleteMissileTrajectoryByFlag(flag);
            missileTrajectoryMapper.insertMissileTrajectoryBatch(importExcel);
        }catch (Exception e){
            logger.info("上传失败：",e);
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    /**
     * 总体评估---得分显示
     * @param commonVo
     * @return
     */
    @GetMapping("/comprehensiveEvalua")
    public AjaxResult comprehensiveEvalua(CommonVo commonVo){
        try {
            ComprehensiveEvalua comprehensiveEvalua = new ComprehensiveEvalua();
            List<Map<String, Object> > dataList = new ArrayList<>();
            // 体系功能
//            Map<String, Object> systemFunctionMaps = new HashMap<>();
            Map<String, Object> systemFunctionMaps =iSystemFunctionScoreService.getSystemFunctionFirstScoreServic();
            systemFunctionMaps.put("dataKind", WeightType.SYSTEM_FUNCTION.getValue());
//            systemFunctionMaps.put("weight", systemFunctionMaps.get("weight")); // 默认权重
//            systemFunctionMaps.put("score", systemFunctionMaps.get("score")); // 默认得分
            dataList.add(systemFunctionMaps);
            comprehensiveEvalua.setSystemFunction(systemFunctionMaps);

            // 想定合理性
            //Map<String, Object> assumedRationalityMaps = new HashMap<>();
            Map<String, Object> assumedRationalityMaps = iEvaluateCommonService.calculateTheRationalityScoreOfTheScenario(commonVo);
            assumedRationalityMaps.put("dataKind", WeightType.SCENARIO_RATIONALITY.getValue());
            dataList.add(assumedRationalityMaps);
            comprehensiveEvalua.setAssumedRationality(assumedRationalityMaps);

            // 软件质量
            //Map<String, Object> softwareQualityMaps = new HashMap<>();
            Map<String, Object> softwareQualityMaps  = iEvaluateCommonService.calculateSoftwareQuality(commonVo);
            softwareQualityMaps.put("dataKind", WeightType.SOFTWARE_QUALITY.getValue());
            dataList.add(softwareQualityMaps);
            comprehensiveEvalua.setSoftwareQuality(softwareQualityMaps);

            // 支持智能决策模型生成与评估
//            Map<String, Object> evaluationMaps = new HashMap<>();
            Map<String, Object> evaluationMaps = iEvaluateIntelligentDecisionService.getEvaluateIntelligentDecisionService(null);
            evaluationMaps.put("dataKind", WeightType.INTELLIGENT_DECISION_SUPPORT.getValue());
//            evaluationMaps.put("weight", 25); // 默认权重
//            evaluationMaps.put("score", 0.0); // 默认得分
            dataList.add(evaluationMaps);
            comprehensiveEvalua.setEvaluation(evaluationMaps);

            // 性能准确度（已有的功能）

            Map<String, Object> maps = flightService.listPerformanceAccuracy();
            maps.put("dataKind", WeightType.PERFORMANCE_ACCURACY.getValue());
            //maps.put("weight", 25); // 默认权重
            //maps.put("score", 0.0); // 默认得分
            dataList.add(maps);
            comprehensiveEvalua.setPerformanceAccuracy(maps);

            // 总体评估
            Map<String, Object> comprehensiveEvaluationMaps = new HashMap<>();
            comprehensiveEvaluationMaps.put("dataKind", WeightType.COMPREHENSIVE_EVALUATION.getValue());

            // 使用Stream API计算总的权重和得分
            double totalWeight = dataList.stream()
                    .mapToDouble(data -> {
                        Object weightObj = data.get("weight");
                        return weightObj instanceof Number ? ((Number) weightObj).doubleValue() : 0.0;
                    })
                    .sum();

            double totalScore = dataList.stream()
                    .mapToDouble(data -> {
                        Object scoreObj = data.get("score");
                        return scoreObj instanceof Number ? ((Number) scoreObj).doubleValue() : 0.0;
                    })
                    .sum();

            comprehensiveEvaluationMaps.put("weight", totalWeight); // 总权重为其他维度权重之和
            comprehensiveEvaluationMaps.put("score", totalScore); // 总得分其他维度得分之和
            comprehensiveEvalua.setComprehensiveEvaluation(comprehensiveEvaluationMaps);
            dataList.add(comprehensiveEvaluationMaps);
            comprehensiveEvalua.setDataList(dataList);

            return AjaxResult.success(comprehensiveEvalua);
        } catch (Exception e) {
            //返回异常数据
            return AjaxResult.error(e.getMessage());
        }
    }


}
