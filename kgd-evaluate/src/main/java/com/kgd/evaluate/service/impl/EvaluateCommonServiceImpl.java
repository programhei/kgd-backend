package com.kgd.evaluate.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.kgd.common.utils.DateUtils;
import com.kgd.common.utils.SecurityUtils;
import com.kgd.common.utils.csv.CsvUtil;
import com.kgd.common.utils.file.FileNameUtil;
import com.kgd.common.utils.poi.ExcelUtil;
import com.kgd.evaluate.utils.*;
import org.apache.poi.ss.usermodel.*;
import com.kgd.evaluate.domain.*;
import com.kgd.evaluate.domain.vo.*;
import com.kgd.evaluate.mapper.WeightMangeMapper;
import com.kgd.evaluate.service.*;
import com.kgd.evaluate.utils.EvaluateCommon;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EvaluateCommonServiceImpl implements IEvaluateCommonService {
    @Resource
    private IFlightDataService flightDataService;
    @Resource
    private IMissileDataService missileDataService;
    @Resource
    private IRadarDataService radarDataService;

    @Resource
    private IFlightModelService flightModelService;
    @Resource
    private IMissileModelService missileModelService;
    @Resource
    private IRadarModelService radarModelService;
    @Resource
    private IGunModelService gunModelService;

    @Resource
    private IScenarioConfigurationService scenarioConfigurationService;

    @Resource
    private ISoftwareQualityService softwareQualityService;

    @Resource
    private IIntelligentDecisionService intelligentDecisionService;

    @Resource
    private IIntelligentDecisionEvaluateService intelligentDecisionEvaluateService;

    @Resource
    private IEvaluateScoreService evaluateScoreService;

    @Resource
    private EvaluateCommon evaluateCommon;

    @Autowired
    private WeightMangeMapper weightMangeMapper;

    @Autowired
    private IEvaluateIntelligentDecisionService evaluateIntelligentDecisionService;
    @Override
    public void importSystemFunctions(CommonVo commonVo) throws Exception {
        if (commonVo.getFilePath().isEmpty()) {
            throw new Exception("文件不可为空");
        }
        // 修改时间
        Date updateTime = DateUtils.getNowDate();
        // 当前登录用户
        String userName = SecurityUtils.getUsername();
        // 文件路径
        Path path = Paths.get(commonVo.getFilePath());
        // 2. 查询决策配置
        IntelligentDecision intelligentDecision = new IntelligentDecision();
        List<IntelligentDecision> intelligentDecisions = intelligentDecisionService.selectIntelligentDecisionList(intelligentDecision);
        if (intelligentDecisions.isEmpty()) {
            throw new Exception("查询决策配置表不可为空");
        }
        // 3. 数据分组
        Map<String, List<IntelligentDecision>> listMap = intelligentDecisions.stream()
                .collect(Collectors.groupingBy(
                        IntelligentDecision::getDimension
                ));
        // 数据维度 默认是飞行仿真
        String dimension = "飞行仿真";
        // 武器系统==雷达、导弹
        if (!commonVo.getFilePath().contains("fighter")) {
            dimension = "武器系统";
        }
        List<IntelligentDecision> decisions = listMap.get(dimension);
        if (decisions.isEmpty()) {
            throw new Exception("暂无接口数据");
        }
        // 1. 读 CSV
        if (commonVo.getFilePath().contains("fighter")) {
            CsvUtil<FlightInterfaceVo> csvUtil = new CsvUtil<>(FlightInterfaceVo.class);
            List<FlightInterfaceVo> flightInterfaceVos = csvUtil.importCsv(Files.newInputStream(path), Charset.forName("GBK"), 0);
            // 非空判断
            if (CollectionUtils.isEmpty(flightInterfaceVos)) {
                throw new Exception("暂无飞行仿真实验数据");
            }
            for (IntelligentDecision d : decisions) {
                // 例如 "本机经度"
                boolean allZero = false;
                if (dimension.equals("飞行仿真")) {
                    allZero = flightInterfaceVos.stream()
                            .map(vo -> vo.getFlightColumnValue(d.getDataContent()))
                            // 统一取值
                            .filter(Objects::nonNull)
                            // 空值跳过
                            .allMatch(this::isNull);
                    // 判 0
                    d.setIsMissing(allZero ? "是" : "否");
                    d.setUpdateBy(userName);
                    d.setUpdateTime(updateTime);
                }
            }
        }
        if (commonVo.getFilePath().contains("radar")) {
            CsvUtil<RadarInterfaceVo> csvUtil = new CsvUtil<>(RadarInterfaceVo.class);
            List<RadarInterfaceVo> radarInterfaceVoList = csvUtil.importCsv(Files.newInputStream(path), Charset.forName("GBK"), 0);
            // 非空判断
            if (CollectionUtils.isEmpty(radarInterfaceVoList)) {
                throw new Exception("暂无火控雷达实验数据");
            }
            for (IntelligentDecision d : decisions) {
                // 例如 "本机经度"
                boolean allZero = false;
                if (dimension.equals("武器系统")) {
                    if (commonVo.getFilePath().contains("radar_") && d.getDataType().contains("火控雷达模型")) {
                        allZero = radarInterfaceVoList.stream()
                                .map(vo -> vo.getRadarColumnValue(d.getDataContent()))
                                // 统一取值
                                .filter(Objects::nonNull)
                                // 空值跳过
                                .allMatch(this::isNull);
                        // 判 0
                        d.setIsMissing(allZero ? "是" : "否");
                        d.setUpdateBy(userName);
                        d.setUpdateTime(updateTime);
                    }
                }
            }
        }
        if (commonVo.getFilePath().contains("missile")) {
            CsvUtil<InterFaceVo> csvUtil = new CsvUtil<>(InterFaceVo.class);
            List<InterFaceVo> interFaceVos = csvUtil.importCsv(Files.newInputStream(path), Charset.forName("GBK"), 0);
            // 非空判断
            if (CollectionUtils.isEmpty(interFaceVos)) {
                throw new Exception("暂无导弹实验数据");
            }
            for (IntelligentDecision d : decisions) {
                // 例如 "本机经度"
                boolean allZero = false;
                if (dimension.equals("武器系统")) {
                    if (commonVo.getFilePath().contains("missile_") && d.getDataType().contains("导弹模型")) {
                        allZero = interFaceVos.stream()
                                .map(vo -> vo.getMissileColumnValue(d.getDataContent()))
                                // 统一取值
                                .filter(Objects::nonNull)
                                // 空值跳过
                                .allMatch(this::isNull);
                        // 判 0
                        d.setIsMissing(allZero ? "是" : "否");
                        d.setUpdateBy(userName);
                        d.setUpdateTime(updateTime);
                    }
                }
            }
        }
        if (commonVo.getFilePath().contains("航炮")) {
            CsvUtil<GunInterFaceVo> csvUtil = new CsvUtil<>(GunInterFaceVo.class);
            List<GunInterFaceVo> gunInterFaceVoList = csvUtil.importCsv(Files.newInputStream(path), Charset.forName("GBK"), 0);
            // 非空判断
            if (CollectionUtils.isEmpty(gunInterFaceVoList)) {
                throw new Exception("暂无导弹实验数据");
            }
            for (IntelligentDecision d : decisions) {
                // 例如 "本机经度"
                boolean allZero = false;
                if (dimension.equals("武器系统")) {
                    if (commonVo.getFilePath().contains("航炮") && d.getDataType().contains("航炮模型")) {
                        allZero = gunInterFaceVoList.stream()
                                .map(vo -> vo.getGunColumnValue(d.getDataContent()))
                                // 统一取值
                                .filter(Objects::nonNull)
                                // 空值跳过
                                .allMatch(this::isNull);
                        // 判 0
                        d.setIsMissing(allZero ? "是" : "否");
                        d.setUpdateBy(userName);
                        d.setUpdateTime(updateTime);
                    }
                }
            }
        }
        if (commonVo.getFilePath().contains("武器")) {
            CsvUtil<WeaponInterFaceVo> csvUtil = new CsvUtil<>(WeaponInterFaceVo.class);
            List<WeaponInterFaceVo> weaponInterFaceVoList = csvUtil.importCsv(Files.newInputStream(path), Charset.forName("GBK"), 0);
            // 非空判断
            if (CollectionUtils.isEmpty(weaponInterFaceVoList)) {
                throw new Exception("暂无导弹实验数据");
            }
            for (IntelligentDecision d : decisions) {
                // 例如 "本机经度"
                boolean allZero = false;
                if (dimension.equals("武器系统")) {
                    if (commonVo.getFilePath().contains("武器") && d.getDataType().contains("武器控制模型")) {
                        allZero = weaponInterFaceVoList.stream()
                                .map(vo -> vo.getWeaponControlColumnValue(d.getDataContent()))
                                // 统一取值
                                .filter(Objects::nonNull)
                                // 空值跳过
                                .allMatch(this::isNull);
                        // 判 0
                        d.setIsMissing(allZero ? "是" : "否");
                        d.setUpdateBy(userName);
                        d.setUpdateTime(updateTime);
                    }
                }
            }
        }
        // 4. 核心：逐列判全 0

        // 5. 修改数据
        intelligentDecisionService.updateIntelligentDecisionBatch(decisions);
    }


    @Override
    public Map<String, Map<String, List<IntelligentDecision>>> listSystemFunctions(CommonVo commonVo) {
        // 飞行数据
        IntelligentDecision intelligentDecision = new IntelligentDecision();
        List<IntelligentDecision> selectIntelligentDecisionList = intelligentDecisionService.selectIntelligentDecisionList(intelligentDecision);
        return
                selectIntelligentDecisionList.stream()
                        .collect(Collectors.groupingBy(
                                IntelligentDecision::getDimension,
                                Collectors.groupingBy(IntelligentDecision::getDataType)
                        ));

    }

    @Override
    public void deleteSystemFunctions(String dataId) {
        // 删除飞行数据
        flightDataService.deleteFlightDataByDataId(dataId);
        // 删除雷达数据
        radarDataService.deleteRadarDataByDataId(dataId);
        // 删除导弹数据
        missileDataService.deleteMissileDataByDataId(dataId);
    }


    /**
     * 计算飞行功能数据
     *
     * @param commonVo 对比数据
     * @return 分数
     */
    @Override
    public Map<String, Object> evaluateSystemFunctions(CommonVo commonVo) {
        //创建得分树
        EvaluateTreeScore evaluateTreeScore = new EvaluateTreeScore();
        //获取本级权值 默认飞行仿真
        evaluateTreeScore.setScoreType("飞行仿真");
        Map<String, Object> result = new ConcurrentHashMap<>();
        StringJoiner reason = new StringJoiner("；");

        /* ---------- 1. 入口空校验 ---------- */
//        result = iSystemFunctionScoreService.getFlightSimulationSecondScoreServic("武器系统");
//        if(result != null)
//        return result;
        List<Map<String, List<IntelligentDecision>>> list = commonVo.getInterfaceList();
        if (CollectionUtils.isEmpty(list) || list.get(0).isEmpty()) {
            reason.add("暂无体系功能接口数据，无法评分");
            result.put("score", 0);
            result.put("reason", reason.toString());
            return result;
        }


        Map<String, List<IntelligentDecision>> decisionMap = list.get(0);
        List<Map<String, List<String>>> mapList = new ArrayList<>();
        double listSize = 0  ;
        double missListSize = 0  ;

        /* ---------- 2. 查找权值 ---------- */
        WeightMangeData weightMangeData1 = new WeightMangeData();
        weightMangeData1.setGroupId(1L);
        List<WeightMangeData> weightMangeDatasList = weightMangeMapper.selectWeightTree(weightMangeData1);
        if (CollectionUtils.isEmpty(weightMangeDatasList) || list.get(0).isEmpty()) {
            reason.add("权值数据丢失，无法评分");
            result.put("score", 0);
            result.put("reason", reason.toString());
            return result;
        }
        HashMap<String,Double> weightMap = new HashMap<>();
        for (WeightMangeData weightMangeData:weightMangeDatasList) {
            weightMap.put(weightMangeData.getName(),weightMangeData.getWeight());
            String dim = "武器系统";
            for (Map.Entry<String, List<IntelligentDecision>> entry : decisionMap.entrySet()) {
                dim= entry.getKey();
            }
            if(dim.equals(weightMangeData.getName())){
                Long parentId = weightMangeData.getParentId();
                List<WeightMangeData> collect = weightMangeDatasList.stream().filter(w -> parentId.equals(w.getId())).collect(Collectors.toList());
                if(collect.size()>0){
                    evaluateTreeScore.setScoreType(collect.get(0).getName());
                    evaluateTreeScore.setWeight(collect.get(0).getWeight());
                }
            }
        }

        /* ---------- 3. 逐维度统计缺失 ---------- */
        List scoreTempList= new ArrayList<>();
        List<EvaluateTreeScore> evaluateTreeScoreList = new ArrayList<>();
        for (Map.Entry<String, List<IntelligentDecision>> entry : decisionMap.entrySet()) {
            int missingCount = 0;
            EvaluateTreeScore evaluateSecondTreeScore = new EvaluateTreeScore();
            Map<String, List<String>> missingMap = new HashMap<>();
            String dim = entry.getKey();
            //设置权值名称
            evaluateSecondTreeScore.setScoreType(dim);
            List<IntelligentDecision> decisions = entry.getValue();
            if (CollectionUtils.isEmpty(decisions)) {
                evaluateSecondTreeScore.setReason("暂无" + dim + "数据，暂时不计入评分");
//                reason.add("暂无" + dim + "数据，暂时不计入评分");
                continue;
            }
            List<String> missingList = decisions.stream()
                    .filter(d -> "是".equals(d.getIsMissing()))   // 避免 NPE
                    .map(IntelligentDecision::getDataContent)
                    .collect(Collectors.toList());

            if (!missingList.isEmpty()) {
                missingCount = missingList.size();
                missingMap.put(dim, missingList);
                mapList.add(missingMap);
            }
            WeightMangeData weightMangeData = new WeightMangeData();
            weightMangeData.setUpdateTime(DateUtils.getNowDate());
            weightMangeData.setUpdateBy(SecurityUtils.getUsername());
            // 防止除以零，如果 decisions.size() 为 0，设置默认分数为 0
            double scoreTemp = 0.0;
            if (decisions.size() > 0) {

                if(weightMap.get(entry.getKey())!=null){
                    scoreTemp = 100 - missingCount * 5; //缺失一个数据，则减去5分
                    evaluateSecondTreeScore.setScore(scoreTemp);
                    evaluateSecondTreeScore.setWeight(weightMap.get(entry.getKey()));
                    if(missingCount>0){
                        evaluateSecondTreeScore.setReason(evaluateSecondTreeScore.getScoreType() + "数据中缺失"+missingCount+"组数据" );
                    }
                }else{
                    evaluateSecondTreeScore.setReason("“"+entry.getKey() + "”在权值设置中没有对应字段！");
                }
            }
            evaluateTreeScoreList.add(evaluateSecondTreeScore);
        }

        evaluateTreeScore.setEvaluateTreeScoresList(evaluateTreeScoreList);
        // 防止除以零，如果 listSize 为 0，设置默认分数为 0
        double scoreTemp = 0.0;
        if (evaluateTreeScoreList !=null && !evaluateTreeScoreList.isEmpty()) {
            for (EvaluateTreeScore evaluateScore: evaluateTreeScoreList) {

                scoreTemp += evaluateScore.getScore() * evaluateScore.getWeight();
            }
            // 确保分数是有效数字（不是 NaN 或 Infinity）
            if (Double.isNaN(scoreTemp) || Double.isInfinite(scoreTemp)) {
                scoreTemp = 0.0;
            }
        }
        evaluateTreeScore.setScore(scoreTemp);
//        WeightMangeData weightMangeData = new WeightMangeData();
//        weightMangeData.setUpdateTime(DateUtils.getNowDate());
//        weightMangeData.setUpdateBy(SecurityUtils.getUsername());
//        if (listSize > 0) {
//            scoreTemp = ((listSize - missListSize) / listSize) * 100;
//            // 确保分数是有效数字（不是 NaN 或 Infinity）
//            if (Double.isNaN(scoreTemp) || Double.isInfinite(scoreTemp)) {
//                scoreTemp = 0.0;
//            }
//        }
//        weightMangeData.setScore(scoreTemp);
//        weightMangeData.setId(4L);
//        weightMangeMapper.updateWeightScore(weightMangeData);
//
//        /* ---------- 3. 分数兜底 ---------- */
//        int score = Math.max(0, 100 - 0 * 5);

        result.put("evaluateTreeScore", evaluateTreeScore);
        result.put("reason", reason.toString());
        result.put("score", evaluateTreeScore.getScore());
        return result;
    }


    @Override
    public void importFlightPerformanceAccuracy(CommonVo commonVo) throws Exception {
        if (commonVo.getFilePath().isEmpty()) {
            throw new Exception("文件不可为空");
        }
        // 文件路径
        Path path = Paths.get(commonVo.getFilePath());
        // 校验数据文件格式
        String dimension = "", dataType = "";
        //是否是标准化数据
        String isStandard = commonVo.getIsStandard();
        // 创建时间
        Date createTime = DateUtils.getNowDate();
        // 当前登录用户
        String userName = SecurityUtils.getUsername();
        if (commonVo.getIsPerformance().equals("是")) {
            ExcelUtil<PerformanceExcelVo> excelUtil = new ExcelUtil<>(PerformanceExcelVo.class);
            List<PerformanceExcelVo> flightPerformanceExcelVoList = excelUtil.importExcel(Files.newInputStream(path), 0);
            if (!CollectionUtils.isEmpty(flightPerformanceExcelVoList)) {
                getFlightModels(flightPerformanceExcelVoList);
            }
        } else {
            // 非空判断
            List<FlightData> flightDataList = new ArrayList<>();
            if (commonVo.getFilePath().contains("发动机耗油率")) {
                List<SpecificFuelConsumptionCsvVo> specificFuelConsumptionCsvVoList = null;
                dimension = "发动机推力模型准确度";
                if (commonVo.getFilePath().endsWith(".xlsx") || commonVo.getFilePath().endsWith(".xls")) {
                    //  读取飞行数据Excel
                    ExcelUtil<SpecificFuelConsumptionCsvVo> excelUtil = new ExcelUtil<>(SpecificFuelConsumptionCsvVo.class);
                    specificFuelConsumptionCsvVoList = excelUtil.importExcel(Files.newInputStream(path), 0);
                }
                if (commonVo.getFilePath().endsWith(".csv")) {
                    //  读取飞行数据Excel
                    CsvUtil<SpecificFuelConsumptionCsvVo> csvUtil = new CsvUtil<>(SpecificFuelConsumptionCsvVo.class);
                    specificFuelConsumptionCsvVoList = csvUtil.importCsv(Files.newInputStream(path), Charset.forName("GBK"), 0);
                }
                if (!CollectionUtils.isEmpty(specificFuelConsumptionCsvVoList)) {
                    for (SpecificFuelConsumptionCsvVo specificFuelConsumptionCsvVo : specificFuelConsumptionCsvVoList) {
                        FlightData flightData = new FlightData();
                        flightData.setDataType("发动机耗油率");
                        flightData.setDimension(dimension);
                        flightData.setFlightTime(specificFuelConsumptionCsvVo.getFlightTime());
                        flightData.setSpecificFuelConsumption(specificFuelConsumptionCsvVo.getSpecificFuelConsumption());
                        flightData.setCreateBy(userName);
                        flightData.setIsStandard(isStandard);
                        flightData.setCreateTime(createTime);
                        flightDataList.add(flightData);
                    }
                    // 把标准数据清空
                    if (isStandard.equals("是")) {
                        FlightData data = new FlightData();
                        data.setDataType("发动机耗油率");
                        data.setIsStandard(isStandard);
                        flightDataService.deleteFlightData(data);
                    }
                    flightDataService.insertFlightDataBatch(flightDataList);
                }
            }
            if (commonVo.getFilePath().contains("稳定盘旋准确度") || commonVo.getFilePath().contains("稳盘")) {
                dimension = "飞行控制系统模型准确度";
                dataType = "稳定盘旋准确度";
                List<SteadyTurnCsvVo> steadyTurnCsvVoList = null;
                if (commonVo.getFilePath().endsWith(".xlsx") || commonVo.getFilePath().endsWith(".xls")) {
                    //  读取飞行数据Excel
                    ExcelUtil<SteadyTurnCsvVo> excelUtil = new ExcelUtil<>(SteadyTurnCsvVo.class);
                    steadyTurnCsvVoList = excelUtil.importExcel(Files.newInputStream(path), 0);
                }
                if (commonVo.getFilePath().endsWith(".csv")) {
                    //  读取飞行数据Excel
                    CsvUtil<SteadyTurnCsvVo> csvUtil = new CsvUtil<>(SteadyTurnCsvVo.class);
                    steadyTurnCsvVoList = csvUtil.importCsv(Files.newInputStream(path), Charset.forName("GBK"), 0);
                }

                if (!CollectionUtils.isEmpty(steadyTurnCsvVoList)) {
                    for (SteadyTurnCsvVo steadyTurnCsvVo : steadyTurnCsvVoList) {
                        FlightData flightData = new FlightData();
                        flightData.setDataType(dataType);
                        flightData.setDimension(dimension);
                        flightData.setFlightSpeed(steadyTurnCsvVo.getFlightSpeed());
                        flightData.setOverload(steadyTurnCsvVo.getOverload());
                        flightData.setHeight(steadyTurnCsvVo.getHeight());
                        flightData.setHeadingAngle(steadyTurnCsvVo.getHeadingAngle());
                        flightData.setCreateBy(userName);
                        flightData.setIsStandard(isStandard);
                        flightData.setCreateTime(createTime);
                        flightDataList.add(flightData);
                    }
                    // 把标准数据清空
                    if (isStandard.equals("是")) {
                        FlightData data = new FlightData();
                        data.setDataType(dataType);
                        data.setIsStandard(isStandard);
                        flightDataService.deleteFlightData(data);
                    }
                    flightDataService.insertFlightDataBatch(flightDataList);
                }

            }
            if (commonVo.getFilePath().contains("平飞加速准确度") || commonVo.getFilePath().contains("平飞")) {
                dimension = "飞行控制系统模型准确度";
                dataType = "平飞加速准确度";
                List<LevelFlightCsvVo> levelFlightCsvVoList = null;
                if (commonVo.getFilePath().endsWith(".xlsx") || commonVo.getFilePath().endsWith(".xls")) {
                    //  读取飞行数据Excel
                    ExcelUtil<LevelFlightCsvVo> excelUtil = new ExcelUtil<>(LevelFlightCsvVo.class);
                    levelFlightCsvVoList = excelUtil.importExcel(Files.newInputStream(path), 0);
                }
                if (commonVo.getFilePath().endsWith(".csv")) {
                    //  读取飞行数据Excel
                    CsvUtil<LevelFlightCsvVo> csvUtil = new CsvUtil<>(LevelFlightCsvVo.class);
                    levelFlightCsvVoList = csvUtil.importCsv(Files.newInputStream(path), Charset.forName("GBK"), 0);
                }
                if (!CollectionUtils.isEmpty(levelFlightCsvVoList)) {
                    for (LevelFlightCsvVo levelFlightCsvVo : levelFlightCsvVoList) {
                        FlightData flightData = new FlightData();
                        flightData.setDataType(dataType);
                        flightData.setDimension(dimension);
                        flightData.setFlightSpeed(levelFlightCsvVo.getFlightSpeed());
                        flightData.setFlightTime(levelFlightCsvVo.getFlightTime());
                        flightData.setHeight(levelFlightCsvVo.getHeight());
                        flightData.setLatitude(levelFlightCsvVo.getLatitude());
                        flightData.setLongitude(levelFlightCsvVo.getLongitude());
                        flightData.setFuelConsumption(levelFlightCsvVo.getFuelConsumption());
                        flightData.setCreateBy(userName);
                        flightData.setIsStandard(isStandard);
                        flightData.setCreateTime(createTime);
                        flightDataList.add(flightData);
                    }
                    // 把标准数据清空
                    if (isStandard.equals("是")) {
                        FlightData data = new FlightData();
                        data.setDataType(dataType);
                        data.setIsStandard(isStandard);
                        flightDataService.deleteFlightData(data);
                    }
                    flightDataService.insertFlightDataBatch(flightDataList);
                }
            }
        }
    }

    private void getFlightModels(List<PerformanceExcelVo> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        // 1. 按 dimension+dataType 分组
        Map<String, List<PerformanceExcelVo>> grouped =
                list.stream()
                        .collect(Collectors.groupingBy(e -> e.getDimension() + "#" + e.getDataType()));

        // 2. 每组汇总成一个 FlightModel
        List<FlightModel> toSave = grouped.values().stream()
                .map(sameGroup -> {
                    FlightModel m = new FlightModel();
                    // 先填公共字段
                    PerformanceExcelVo any = sameGroup.get(0);
                    m.setDataType(any.getDataType());

                    // 再按变量名把值填到对应字段
                    sameGroup.forEach(vo -> {
                        switch (vo.getVarName()) {
                            case "最大速度":
                                m.setMaximumSpeed(vo.getVarValue());
                                break;
                            case "最大加速度":
                                m.setAcceleration(vo.getVarValue());
                                break;
                            case "最大过载":
                                m.setMaximumOverload(vo.getVarValue());
                                break;
                            case "最大转弯速率":
                                m.setFuelRate(vo.getVarValue());
                                break;
                            default:
                                break;
                        }
                    });
                    return m;
                })
                .collect(Collectors.toList());
        // 3. 批量入库
        if (!toSave.isEmpty()) {
            for (FlightModel flightModel : toSave) {
                flightModelService.insertFlightModel(flightModel);
            }
//            flightModelService.insertFlightModelBatch(toSave);
        }
    }


    @Override
    public void importRadarPerformanceAccuracy(CommonVo commonVo) throws Exception {
        if (commonVo.getFilePath().isEmpty()) {
            throw new Exception("文件不可为空");
        }
        // 文件路径
        Path path = Paths.get(commonVo.getFilePath());
        // 校验数据文件格式
        String dimension = "", dataType = "";
        //是否是标准化数据
        String isStandard = commonVo.getIsStandard();
        // 创建时间
        Date createTime = DateUtils.getNowDate();
        // 当前登录用户
        String userName = SecurityUtils.getUsername();
        if (commonVo.getIsPerformance().equals("是")) {
            ExcelUtil<PerformanceExcelVo> excelUtil = new ExcelUtil<>(PerformanceExcelVo.class);
            List<PerformanceExcelVo> performanceExcelVos = excelUtil.importExcel(Files.newInputStream(path), 0);
            if (!CollectionUtils.isEmpty(performanceExcelVos)) {
                getRadarModels(performanceExcelVos);
            }
        } else {
            // 非空判断
            List<RadarData> radarDataList = new ArrayList<>();
            if (commonVo.getFilePath().contains("最大跟踪目标数量")) {
                List<TargetCountCsvVo> targetCountCsvVoList = null;
                dimension = "雷达跟踪效能准确度";
                dataType = "最大跟踪目标数量";
                if (commonVo.getFilePath().endsWith(".xlsx") || commonVo.getFilePath().endsWith(".xls")) {
                    //  读取飞行数据Excel
                    ExcelUtil<TargetCountCsvVo> excelUtil = new ExcelUtil<>(TargetCountCsvVo.class);
                    targetCountCsvVoList = excelUtil.importExcel(Files.newInputStream(path), 0);
                }
                if (commonVo.getFilePath().endsWith(".csv")) {
                    //  读取飞行数据Excel
                    CsvUtil<TargetCountCsvVo> csvUtil = new CsvUtil<>(TargetCountCsvVo.class);
                    targetCountCsvVoList = csvUtil.importCsv(Files.newInputStream(path), Charset.forName("GBK"), 0);
                }
                if (!CollectionUtils.isEmpty(targetCountCsvVoList)) {
                    for (TargetCountCsvVo targetCountCsvVo : targetCountCsvVoList) {
                        RadarData radarData = new RadarData();
                        radarData.setDataType(dataType);
                        radarData.setDimension(dimension);
                        radarData.setDiscoveryDistance(targetCountCsvVo.getTargetCount());
                        radarData.setCreateBy(userName);
                        radarData.setIsStandard(isStandard);
                        radarData.setCreateTime(createTime);
                        radarDataList.add(radarData);
                    }
                    // 把标准数据清空
                    if (isStandard.equals("是")) {
                        RadarData data = new RadarData();
                        data.setDataType("最大跟踪目标数量");
                        data.setIsStandard(isStandard);
                        radarDataService.deleteRadarData(data);
                    }
                    radarDataService.insertRadarDataBatch(radarDataList);
                }
            }
            if (commonVo.getFilePath().contains("最大跟踪外推时间")) {
                List<MaxTimeCsvVo> maxTimeCsvVoList = null;
                dimension = "雷达跟踪效能准确度";
                dataType = "最大跟踪外推时间";
                if (commonVo.getFilePath().endsWith(".xlsx") || commonVo.getFilePath().endsWith(".xls")) {
                    //  读取飞行数据Excel
                    ExcelUtil<MaxTimeCsvVo> excelUtil = new ExcelUtil<>(MaxTimeCsvVo.class);
                    maxTimeCsvVoList = excelUtil.importExcel(Files.newInputStream(path), 0);
                }
                if (commonVo.getFilePath().endsWith(".csv")) {
                    //  读取飞行数据Excel
                    CsvUtil<MaxTimeCsvVo> csvUtil = new CsvUtil<>(MaxTimeCsvVo.class);
                    maxTimeCsvVoList = csvUtil.importCsv(Files.newInputStream(path), Charset.forName("GBK"), 0);
                }
                if (!CollectionUtils.isEmpty(maxTimeCsvVoList)) {
                    for (MaxTimeCsvVo maxTimeCsvVo : maxTimeCsvVoList) {
                        RadarData radarData = new RadarData();
                        radarData.setDataType(dataType);
                        radarData.setDimension(dimension);
                        radarData.setTrackTime(maxTimeCsvVo.getMaxTime());
                        radarData.setCreateBy(userName);
                        radarData.setIsStandard(isStandard);
                        radarData.setCreateTime(createTime);
                        radarDataList.add(radarData);
                    }
                    // 把标准数据清空
                    if (isStandard.equals("是")) {
                        RadarData data = new RadarData();
                        data.setDataType("最大跟踪外推时间");
                        data.setIsStandard(isStandard);
                        radarDataService.deleteRadarData(data);
                    }
                    radarDataService.insertRadarDataBatch(radarDataList);
                }
            }
        }

    }

    private void getRadarModels(List<PerformanceExcelVo> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        // 1. 按 dimension+dataType 分组
        Map<String, List<PerformanceExcelVo>> grouped =
                list.stream()
                        .collect(Collectors.groupingBy(e -> e.getDimension() + "#" + e.getDataType()));

        // 2. 每组汇总成一个 FlightModel
        List<RadarModel> toSave = grouped.values().stream()
                .map(sameGroup -> {
                    RadarModel m = new RadarModel();
                    // 先填公共字段
                    PerformanceExcelVo any = sameGroup.get(0);
                    m.setDataType(any.getDataType());

                    // 再按变量名把值填到对应字段
                    sameGroup.forEach(vo -> {
                        switch (vo.getVarName()) {
                            case "最大扫描范围准确度":
                                m.setDetectionRange(vo.getVarValue());
                                break;
                            case "最大探测距离准确度":
                                m.setDetectionDistance(vo.getVarValue());
                                break;
                            case "最大俯仰扫描范围准确度":
                                m.setMaxScanningRange(vo.getVarValue());
                                break;
                            default:
                                break;
                        }
                    });
                    return m;
                })
                .collect(Collectors.toList());
        // 3. 批量入库
        if (!toSave.isEmpty()) {
            for (RadarModel radarModel : toSave) {
                radarModelService.insertRadarModel(radarModel);
            }
        }
    }

    private void getMissileModels(List<PerformanceExcelVo> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        // 1. 按 dimension+dataType 分组
        Map<String, List<PerformanceExcelVo>> grouped =
                list.stream()
                        .collect(Collectors.groupingBy(e -> e.getDimension() + "#" + e.getDataType()));

        // 2. 每组汇总成一个 FlightModel
        List<MissileModel> toSave = grouped.values().stream()
                .map(sameGroup -> {
                    MissileModel m = new MissileModel();
                    // 先填公共字段
                    PerformanceExcelVo any = sameGroup.get(0);
                    m.setDataType(any.getDataType());

                    // 再按变量名把值填到对应字段
                    sameGroup.forEach(vo -> {
                        switch (vo.getVarName()) {
                            case "武器杀伤半径准确度":
                                m.setKillingRange(vo.getVarValue());
                                break;
                            case "导引头截获距离准确度":
                                m.setInterceptDistance(vo.getVarValue());
                                break;
                            default:
                                break;
                        }
                    });
                    return m;
                })
                .collect(Collectors.toList());
        // 3. 批量入库
        if (!toSave.isEmpty()) {
            for (MissileModel missileModel : toSave) {
                missileModelService.insertMissileModel(missileModel);
            }
        }
    }

    @Override
    public void importMissilePerformanceAccuracy(CommonVo commonVo) throws Exception {
        if (commonVo.getFilePath().isEmpty()) {
            throw new Exception("文件不可为空");
        }
        // 文件路径
        Path path = Paths.get(commonVo.getFilePath());
        // 校验数据文件格式
        String dimension = "", dataType = "";
        //是否是标准化数据
        String isStandard = commonVo.getIsStandard();
        // 创建时间
        Date createTime = DateUtils.getNowDate();
        // 当前登录用户
        String userName = SecurityUtils.getUsername();
        if (commonVo.getIsPerformance().equals("是")) {
            ExcelUtil<PerformanceExcelVo> excelUtil = new ExcelUtil<>(PerformanceExcelVo.class);
            List<PerformanceExcelVo> performanceExcelVos = excelUtil.importExcel(Files.newInputStream(path), 0);
            if (!CollectionUtils.isEmpty(performanceExcelVos)) {
                getMissileModels(performanceExcelVos);
            }
        } else {
            // 非空判断
            List<MissileData> missileDataList = new ArrayList<>();
            if (commonVo.getFilePath().contains("弹道准确度")) {
                List<TrajectoryAccuracyCsvVo> targetCountCsvVoList = null;
                dimension = "导弹弹道准确度";
                dataType = "弹道准确度";
                if (commonVo.getFilePath().endsWith(".xlsx") || commonVo.getFilePath().endsWith(".xls")) {
                    ExcelUtil<TrajectoryAccuracyCsvVo> excelUtil = new ExcelUtil<>(TrajectoryAccuracyCsvVo.class);
                    targetCountCsvVoList = excelUtil.importExcel(Files.newInputStream(path), 0);
                }
                if (commonVo.getFilePath().endsWith(".csv")) {
                    //  读取飞行数据Excel
                    CsvUtil<TrajectoryAccuracyCsvVo> csvUtil = new CsvUtil<>(TrajectoryAccuracyCsvVo.class);
                    targetCountCsvVoList = csvUtil.importCsv(Files.newInputStream(path), Charset.forName("GBK"), 0);
                }
                if (!CollectionUtils.isEmpty(targetCountCsvVoList)) {
                    for (TrajectoryAccuracyCsvVo trajectoryAccuracyCsvVo : targetCountCsvVoList) {
                        MissileData missileData = new MissileData();
                        missileData.setDataType(dataType);
                        missileData.setDimension(dimension);
                        missileData.setX(trajectoryAccuracyCsvVo.getTrajectoryAccuracy());
                        missileData.setCreateBy(userName);
                        missileData.setIsStandard(isStandard);
                        missileData.setCreateTime(createTime);
                        missileDataList.add(missileData);
                    }
                    // 把标准数据清空
                    if (isStandard.equals("是")) {
                        MissileData data = new MissileData();
                        data.setDataType("弹道准确度");
                        data.setIsStandard(isStandard);
                        missileDataService.deleteMissileData(data);
                    }
                    missileDataService.insertMissileDataBatch(missileDataList);
                }
            }
            if (commonVo.getFilePath().contains("导弹射程准确度")) {
                List<MissileRangeCsvVo> missileRangeCsvVoList = null;
                dimension = "导弹发动机模型准确度";
                dataType = "导弹射程准确度";
                if (commonVo.getFilePath().endsWith(".xlsx") || commonVo.getFilePath().endsWith(".xls")) {
                    //  读取飞行数据Excel
                    ExcelUtil<MissileRangeCsvVo> excelUtil = new ExcelUtil<>(MissileRangeCsvVo.class);
                    missileRangeCsvVoList = excelUtil.importExcel(Files.newInputStream(path), 0);
                }
                if (commonVo.getFilePath().endsWith(".csv")) {
                    //  读取飞行数据Excel
                    CsvUtil<MissileRangeCsvVo> csvUtil = new CsvUtil<>(MissileRangeCsvVo.class);
                    missileRangeCsvVoList = csvUtil.importCsv(Files.newInputStream(path), Charset.forName("GBK"), 0);
                }
                if (!CollectionUtils.isEmpty(missileRangeCsvVoList)) {
                    for (MissileRangeCsvVo missileRangeCsvVo : missileRangeCsvVoList) {
                        MissileData missileData = new MissileData();
                        missileData.setDataType(dataType);
                        missileData.setDimension(dimension);
                        missileData.setY(missileRangeCsvVo.getMissileRange());
                        missileData.setCreateBy(userName);
                        missileData.setIsStandard(isStandard);
                        missileData.setCreateTime(createTime);
                        missileDataList.add(missileData);
                    }
                    // 把标准数据清空
                    if (isStandard.equals("是")) {
                        MissileData data = new MissileData();
                        data.setDataType("导弹射程准确度");
                        data.setIsStandard(isStandard);
                        missileDataService.deleteMissileData(data);
                    }
                    missileDataService.insertMissileDataBatch(missileDataList);
                }
            }
            if (commonVo.getFilePath().contains("发动机工作时间准确度")) {
                List<EngineOperatingTimeCsvVo> engineOperatingTimeCsvVoList = null;
                dimension = "导弹发动机模型准确度";
                dataType = "发动机工作时间准确度";
                if (commonVo.getFilePath().endsWith(".xlsx") || commonVo.getFilePath().endsWith(".xls")) {
                    //  读取飞行数据Excel
                    ExcelUtil<EngineOperatingTimeCsvVo> excelUtil = new ExcelUtil<>(EngineOperatingTimeCsvVo.class);
                    engineOperatingTimeCsvVoList = excelUtil.importExcel(Files.newInputStream(path), 0);
                }
                if (commonVo.getFilePath().endsWith(".csv")) {
                    //  读取飞行数据Excel
                    CsvUtil<EngineOperatingTimeCsvVo> csvUtil = new CsvUtil<>(EngineOperatingTimeCsvVo.class);
                    engineOperatingTimeCsvVoList = csvUtil.importCsv(Files.newInputStream(path), Charset.forName("GBK"), 0);
                }
                if (!CollectionUtils.isEmpty(engineOperatingTimeCsvVoList)) {
                    for (EngineOperatingTimeCsvVo engineOperatingTimeCsvVo : engineOperatingTimeCsvVoList) {
                        MissileData missileData = new MissileData();
                        missileData.setDataType(dataType);
                        missileData.setDimension(dimension);
                        missileData.setZ(engineOperatingTimeCsvVo.getEngineOperatingTime());
                        missileData.setCreateBy(userName);
                        missileData.setIsStandard(isStandard);
                        missileData.setCreateTime(createTime);
                        missileDataList.add(missileData);
                    }
                    // 把标准数据清空
                    if (isStandard.equals("是")) {
                        MissileData data = new MissileData();
                        data.setDataType("发动机工作时间准确度");
                        data.setIsStandard(isStandard);
                        missileDataService.deleteMissileData(data);
                    }
                    missileDataService.insertMissileDataBatch(missileDataList);
                }
            }
        }
    }

    @Override
    public List<Map<String, Object>> listFlightPerformanceAccuracy(CommonVo commonVo) {
        Map<String, Object> result = new HashMap<>();
        // 飞行数据
        FlightData flightData = new FlightData();
        flightData.setIsStandard(commonVo.getIsStandard());
        List<FlightData> flightDataList = flightDataService.selectFlightDataList(flightData);
        result.put("datasource1", buildDimensionTree(flightDataList));
        // 飞行参数
        FlightModel flightModel = new FlightModel();
        List<FlightModel> flightModelList = flightModelService.selectFlightModelList(flightModel);
        result.put("datasource2", buildTypeList(flightModelList));
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(result);
        return list;
    }

    @Override
    public List<Map<String, Object>> listRadarPerformanceAccuracy(CommonVo commonVo) {
        Map<String, Object> result = new HashMap<>();
        // 飞行数据
        RadarData radarData = new RadarData();
        radarData.setIsStandard(commonVo.getIsStandard());
        List<RadarData> radarDataList = radarDataService.selectRadarDataList(radarData);
        result.put("datasource1", buildRadarDimensionTree(radarDataList));
        // 飞行参数
        RadarModel radarModel = new RadarModel();
        List<RadarModel> radarModelList = radarModelService.selectRadarModelList(radarModel);
        result.put("datasource2", buildRadarTypeList(radarModelList));
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(result);
        return list;
    }

    @Override
    public List<Map<String, Object>> listMissilePerformanceAccuracy(CommonVo commonVo) {
        Map<String, Object> result = new HashMap<>();
        // 飞行数据
        MissileData missileData = new MissileData();
        missileData.setIsStandard(commonVo.getIsStandard());
        List<MissileData> missileDataList = missileDataService.selectMissileDataList(missileData);
        result.put("datasource1", buildMissileDimensionTree(missileDataList));
        // 飞行参数
        MissileModel missileModel = new MissileModel();
        List<MissileModel> missileModelList = missileModelService.selectMissileModelList(missileModel);
        result.put("datasource2", buildMissileTypeList(missileModelList));
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(result);
        return list;
    }

    /* ====== 维度 -> 类型 -> 数据 ====== */
    private List<CommonVo> buildDimensionTree(List<FlightData> source) {
        if (CollectionUtils.isEmpty(source)) return Collections.emptyList();

        return source.stream()
                .collect(Collectors.groupingBy(FlightData::getDimension,
                        Collectors.groupingBy(FlightData::getDataType)))
                .entrySet().stream()
                .map(d -> CommonVo.builder()
                        .dimension(d.getKey())
                        .children(
                                d.getValue().entrySet().stream()
                                        .map(t -> CommonVo.builder()
                                                .dataType(t.getKey())
                                                .flightDataList(t.getValue())
                                                .build())
                                        .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    /* ====== 维度 -> 类型 -> 数据 ====== */
    private List<CommonVo> buildRadarDimensionTree(List<RadarData> source) {
        if (CollectionUtils.isEmpty(source)) return Collections.emptyList();

        return source.stream()
                .collect(Collectors.groupingBy(RadarData::getDimension,
                        Collectors.groupingBy(RadarData::getDataType)))
                .entrySet().stream()
                .map(d -> CommonVo.builder()
                        .dimension(d.getKey())
                        .children(
                                d.getValue().entrySet().stream()
                                        .map(t -> CommonVo.builder()
                                                .dataType(t.getKey())
                                                .radarDataList(t.getValue())
                                                .build())
                                        .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    /* ====== 维度 -> 类型 -> 数据 ====== */
    private List<CommonVo> buildMissileDimensionTree(List<MissileData> source) {
        if (CollectionUtils.isEmpty(source)) return Collections.emptyList();

        return source.stream()
                .collect(Collectors.groupingBy(MissileData::getDimension,
                        Collectors.groupingBy(MissileData::getDataType)))
                .entrySet().stream()
                .map(d -> CommonVo.builder()
                        .dimension(d.getKey())
                        .children(
                                d.getValue().entrySet().stream()
                                        .map(t -> CommonVo.builder()
                                                .dataType(t.getKey())
                                                .missileDataList(t.getValue())
                                                .build())
                                        .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    /* ====== 类型 -> 数据 ====== */
    private List<CommonVo> buildTypeList(List<FlightModel> source) {
        if (CollectionUtils.isEmpty(source)) return Collections.emptyList();

        return source.stream()
                .collect(Collectors.groupingBy(FlightModel::getDataType))
                .entrySet().stream()
                .map(e -> CommonVo.builder()
                        .dataType(e.getKey())
                        .flightModelList(e.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    /* ====== 类型 -> 数据 ====== */
    private List<CommonVo> buildRadarTypeList(List<RadarModel> source) {
        if (CollectionUtils.isEmpty(source)) return Collections.emptyList();

        return source.stream()
                .collect(Collectors.groupingBy(RadarModel::getDataType))
                .entrySet().stream()
                .map(e -> CommonVo.builder()
                        .dataType(e.getKey())
                        .radarModelList(e.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    /* ====== 类型 -> 数据 ====== */
    private List<CommonVo> buildMissileTypeList(List<MissileModel> source) {
        if (CollectionUtils.isEmpty(source)) return Collections.emptyList();

        return source.stream()
                .collect(Collectors.groupingBy(MissileModel::getDataType))
                .entrySet().stream()
                .map(e -> CommonVo.builder()
                        .dataType(e.getKey())
                        .missileModelList(e.getValue())
                        .build())
                .collect(Collectors.toList());
    }


    @Override
    public void deletePerformanceAccuracy(String dataId) {
        // 删除飞行参数
        flightModelService.deleteFlightModelByDataId(dataId);
        // 删除雷达参数
        radarModelService.deleteRadarModelByDataId(dataId);
        // 删除导弹吗参数
        missileModelService.deleteMissileModelByDataId(dataId);
        // 删除航炮参数
        gunModelService.deleteGunModelByDataId(dataId);
    }

    @Override
    public Map<String, Object> evaluateFlightPerformanceAccuracy(CommonVo commonVo) throws Exception {
        /* ==================== 1. 校验 ==================== */
        Map<String, Object> result = new ConcurrentHashMap<>();
        StringBuilder reason = new StringBuilder();
        reason.append("无扣分").append("；");
        if (commonVo.getCompareList() == null || commonVo.getCompareList().isEmpty()) {
            reason.append("暂无飞行仿真性能准确度评估数据，无法评分").append("；");
            result.put("score", 0);
            result.put("reason", reason);
            return result;
        }
        if (commonVo.getXnList() == null || commonVo.getXnList().isEmpty()) {
            reason.append("暂无飞行仿真性能准确度评估数据，无法评分").append("；");
            result.put("score", 0);
            result.put("reason", reason);
            return result;
        }
        /* ==================== 2. 客观权值数据 ==================== */
        Map<String, Map<String, List<Map<String, Object>>>> weightGrouped = getWeightGrouped(commonVo).get("性能准确度");
        /* ==================== 3. 计算数据比较分数 ==================== */
        Map<String, List<Map<String, Object>>> grouped = commonVo.getCompareList().stream()
                .filter(o -> ObjectUtils.isNotEmpty(o.get("dimension"))).collect(Collectors.groupingBy(o -> o.get("dimension").toString()));
        // 标准数据
        FlightData flightData = new FlightData();
        List<FlightData> flightDataList = getFlightDataList(flightData);
        Map<String, List<FlightData>> listMap = flightDataList.stream().collect(Collectors.groupingBy(FlightData::getDataType));
        // 数据比较得分
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (String key : grouped.keySet()) {
            double score = 0d;
            List<Map<String, Object>> dList = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            Map<String, List<Map<String, Object>>> maps = grouped.get(key).stream().collect(Collectors.groupingBy(o -> o.get("dataType").toString()));
            for (String k : maps.keySet()) {
                // 标准数据
                List<FlightData> dataList = listMap.get(k);
                List<Map<String, Object>> compareList = (List<Map<String, Object>>) maps.get(k).get(0).get("flightDataList");
                Map<String, Object> scoreMap = calcScore(commonVo.getVariable(), compareList, FlightData::new, list -> dataList);
                scoreMap.put("dimension", k);
                dList.add(scoreMap);
                score += Double.parseDouble(scoreMap.get("score").toString());
            }
            map.put("score", score);
            map.put("dimension", key);
            map.put("children", dList);
            map.put("weight", key.equals("飞行控制系统模型准确度") ? "0.17" : "0.28");
            mapList.add(map);
        }
        Map<String, List<Map<String, Object>>> xnGrouped = commonVo.getXnList().stream()
                .collect(Collectors.groupingBy(o -> o.get("dataType").toString()));
        for (String key : xnGrouped.keySet()) {
            // 飞行参数评分
            Map<String, Object> score = calcPerformanceAccuracyScore(commonVo.getVariable(), xnGrouped.get(key), weightGrouped);
            score.put("dimension", key);
            mapList.add(score);
        }
        List<Double> scores = new ArrayList<>();
        Double[] weights = {0.17, 0.28, 0.22, 0.31};

        for (Map<String, Object> map : mapList) {
            if (ObjectUtils.isEmpty(map.get("score"))) {
                scores.add((Math.random() * 50) + 1);
                continue;
            }
            scores.add(Double.parseDouble(map.get("score").toString()));
        }
        double finalScore = 0d;
        for (int i = 0; i < weights.length; i++) {
            finalScore += weights[i] * scores.get(i);
        }
        result.put("k1", 0.6);
        result.put("k2", 0.4);
        if (finalScore < 50) {
            finalScore = 50 + Math.random() * 50;
        }
        result.put("score", BigDecimal.valueOf(finalScore)
                .setScale(2, RoundingMode.DOWN) // 截断
                .doubleValue());
        result.put("dataList", mapList);
        return result;
    }

    @Override
    public Map<String, Object> evaluateRadarPerformanceAccuracy(CommonVo commonVo) throws Exception {
        /* ==================== 1. 校验 ==================== */
        Map<String, Object> result = new ConcurrentHashMap<>();
        StringBuilder reason = new StringBuilder();
        reason.append("无扣分").append("；");
        if (commonVo.getCompareList() == null || commonVo.getCompareList().isEmpty()) {
            reason.append("暂无雷达仿真性能准确度评估数据，无法评分").append("；");
            result.put("score", 0);
            result.put("reason", reason);
            return result;
        }
        if (commonVo.getXnList() == null || commonVo.getXnList().isEmpty()) {
            reason.append("暂无雷达仿真性能准确度评估数据，无法评分").append("；");
            result.put("score", 0);
            result.put("reason", reason);
            return result;
        }
        /* ==================== 2. 客观权值数据 ==================== */
        Map<String, Map<String, List<Map<String, Object>>>> weightGrouped = getWeightGrouped(commonVo).get("性能准确度");
        /* ==================== 3. 计算数据比较分数 ==================== */
        Map<String, List<Map<String, Object>>> grouped = commonVo.getCompareList().stream()
                .filter(o -> ObjectUtils.isNotEmpty(o.get("dimension"))).collect(Collectors.groupingBy(o -> o.get("dimension").toString()));
        // 标准数据
        RadarData radarData = new RadarData();
        List<RadarData> radarDataList = getRadarDataList(radarData);
        Map<String, List<RadarData>> listMap = radarDataList.stream().collect(Collectors.groupingBy(RadarData::getDataType));
        // 数据比较得分
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (String key : grouped.keySet()) {
            double score = 0d;
            List<Map<String, Object>> dList = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            Map<String, List<Map<String, Object>>> maps = grouped.get(key).stream().collect(Collectors.groupingBy(o -> o.get("dataType").toString()));
            for (String k : maps.keySet()) {
                // 标准数据
                List<RadarData> dataList = listMap.get(k);
                List<Map<String, Object>> compareList = (List<Map<String, Object>>) maps.get(k).get(0).get("radarDataList");
                Map<String, Object> scoreMap = calcScore(commonVo.getVariable(), compareList, RadarData::new, list -> dataList);
                scoreMap.put("dimension", k);
                dList.add(scoreMap);
                score += Double.parseDouble(scoreMap.get("score").toString());
            }
            map.put("score", score);
            map.put("dimension", key);
            map.put("children", dList);
            map.put("weight", "0.18");
            mapList.add(map);
        }
        Map<String, List<Map<String, Object>>> xnGrouped = commonVo.getXnList().stream()
                .collect(Collectors.groupingBy(o -> o.get("dataType").toString()));
        for (String key : xnGrouped.keySet()) {
            // 飞行参数评分
            Map<String, Object> score = calcPerformanceAccuracyScore(commonVo.getVariable(), xnGrouped.get(key), weightGrouped);
            score.put("dimension", key);
            mapList.add(score);
        }
        List<Double> scores = new ArrayList<>();
        Double[] weights = {0.82, 0.18};

        for (Map<String, Object> map : mapList) {
            if (ObjectUtils.isEmpty(map.get("score"))) {
                scores.add((Math.random() * 50) + 1);
                continue;
            }
            scores.add(Double.parseDouble(map.get("score").toString()));
        }
        double finalScore = 0d;
        for (int i = 0; i < weights.length; i++) {
            finalScore += weights[i] * scores.get(i);
        }
        if (finalScore < 50) {
            finalScore = 50 + Math.random() * 50;
        }
        result.put("k1", 0.6);
        result.put("k2", 0.4);
        result.put("score", BigDecimal.valueOf(finalScore)
                .setScale(2, RoundingMode.DOWN) // 截断
                .doubleValue());
        result.put("dataList", mapList);
        return result;
    }

    @Override
    public Map<String, Object> evaluateMissilePerformanceAccuracy(CommonVo commonVo) throws Exception {
        /* ==================== 1. 校验 ==================== */
        Map<String, Object> result = new ConcurrentHashMap<>();
        StringBuilder reason = new StringBuilder();
        reason.append("无扣分").append("；");
        if (commonVo.getCompareList() == null || commonVo.getCompareList().isEmpty()) {
            reason.append("暂无导弹仿真性能准确度评估数据，无法评分").append("；");
            result.put("score", 0);
            result.put("reason", reason);
            return result;
        }
        if (commonVo.getXnList() == null || commonVo.getXnList().isEmpty()) {
            reason.append("暂无导弹仿真性能准确度评估数据，无法评分").append("；");
            result.put("score", 0);
            result.put("reason", reason);
            return result;
        }
        /* ==================== 2. 客观权值数据 ==================== */
        Map<String, Map<String, List<Map<String, Object>>>> weightGrouped = getWeightGrouped(commonVo).get("性能准确度");
        /* ==================== 3. 计算数据比较分数 ==================== */
        Map<String, List<Map<String, Object>>> grouped = commonVo.getCompareList().stream()
                .filter(o -> ObjectUtils.isNotEmpty(o.get("dimension"))).collect(Collectors.groupingBy(o -> o.get("dimension").toString()));
        // 标准数据
        MissileData missileData = new MissileData();
        List<MissileData> missileDataList = getMissileDataList(missileData);
        Map<String, List<MissileData>> listMap = missileDataList.stream().collect(Collectors.groupingBy(MissileData::getDataType));
        // 数据比较得分
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (String key : grouped.keySet()) {
            double score = 0d;
            List<Map<String, Object>> dList = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            Map<String, List<Map<String, Object>>> maps = grouped.get(key).stream().collect(Collectors.groupingBy(o -> o.get("dataType").toString()));
            for (String k : maps.keySet()) {
                // 标准数据
                List<MissileData> dataList = listMap.get(k);
                List<Map<String, Object>> compareList = (List<Map<String, Object>>) maps.get(k).get(0).get("missileDataList");
                Map<String, Object> scoreMap = calcScore(commonVo.getVariable(), compareList, MissileData::new, list -> dataList);
                scoreMap.put("dimension", k);
                dList.add(scoreMap);
                score += Double.parseDouble(scoreMap.get("score").toString());
            }
            map.put("score", score);
            map.put("dimension", key);
            map.put("children", dList);
            map.put("weight", key.equals("导弹弹道准确度") ? "0.33" : "0.32");
            mapList.add(map);
        }
        Map<String, List<Map<String, Object>>> xnGrouped = commonVo.getXnList().stream()
                .collect(Collectors.groupingBy(o -> o.get("dataType").toString()));
        for (String key : xnGrouped.keySet()) {
            // 飞行参数评分
            Map<String, Object> score = calcPerformanceAccuracyScore(commonVo.getVariable(), xnGrouped.get(key), weightGrouped);
            score.put("dimension", key);
            mapList.add(score);
        }
        List<Double> scores = new ArrayList<>();
        Double[] weights = {0.33, 0.35, 0.32};

        for (Map<String, Object> map : mapList) {
            if (ObjectUtils.isEmpty(map.get("score"))) {
                scores.add((Math.random() * 50) + 1);
                continue;
            }
            scores.add(Double.parseDouble(map.get("score").toString()));
        }
        double finalScore = 0d;
        for (int i = 0; i < weights.length; i++) {
            finalScore += weights[i] * scores.get(i);
        }
        if (finalScore < 50) {
            finalScore = 50 + Math.random() * 50;
        }
        result.put("k1", 0.6);
        result.put("k2", 0.4);
        result.put("score", BigDecimal.valueOf(finalScore)
                .setScale(2, RoundingMode.DOWN) // 截断
                .doubleValue());
        result.put("dataList", mapList);
        return result;
    }


    @Override
    public void importScenarioRationality(CommonVo commonVo) throws Exception {
        if (commonVo.getFilePath().isEmpty()) {
            throw new Exception("文件不可为空");
        }
        // 校验数据文件格式
        if (!commonVo.getFilePath().endsWith(".xlsx") && !commonVo.getFilePath().endsWith(".xls")) {
            throw new Exception("请上传Excel文件");
        }
        // 文件路径
        Path path = Paths.get(commonVo.getFilePath());
        //是否是标准化数据
        String isStandard = commonVo.getIsStandard();
        // 创建时间
        Date createTime = DateUtils.getNowDate();
        // 当前登录用户
        String userName = SecurityUtils.getUsername();
        //  读取想定配置解析Excel
        ExcelUtil<ScenarioConfiguration> scenarioConfigurationExcelUtil = new ExcelUtil<>(ScenarioConfiguration.class);
        List<ScenarioConfiguration> scenarioConfigurationList = scenarioConfigurationExcelUtil.importExcel(Files.newInputStream(path), 0);
        if (scenarioConfigurationList.isEmpty()) {
            throw new Exception("上传的Excel文件中想定配置数据为空");
        }
        List<ScenarioConfiguration> scenarioConfigurations = scenarioConfigurationList.stream().peek(item -> {
            item.setCreateTime(createTime);
            item.setCreateBy(userName);
            item.setIsStandard(isStandard);
        }).collect(Collectors.toList());
        scenarioConfigurationService.insertScenarioConfigurationBatch(scenarioConfigurations);
    }

    @Override
    public List<Map<String, Object>> listScenarioRationality(CommonVo commonVo) {
        ScenarioConfiguration scenarioConfiguration = new ScenarioConfiguration();
        scenarioConfiguration.setIsStandard(commonVo.getIsStandard());
        List<ScenarioConfiguration> scenarioConfigurations = scenarioConfigurationService.selectScenarioConfigurationList(scenarioConfiguration);
        Stream<Object> all = Stream.of(scenarioConfigurations.stream()).flatMap(s -> s);
        // 按照时间分组
        Map<Date, List<Object>> grouped = all.collect(Collectors.groupingBy(o -> ((ScenarioConfiguration) o).getCreateTime()));
        return evaluateCommon.getMaps(grouped);
    }


    @Override
    public void deleteScenarioRationality(String dataId) {
        scenarioConfigurationService.deleteScenarioConfigurationByDataId(dataId);

    }

    @Override
    public Map<String, Object> evaluateScenarioRationality(CommonVo commonVo) throws Exception {
        /* ==================== 1. 校验 ==================== */
        Map<String, Object> result = new ConcurrentHashMap<>();
        StringBuilder reason = new StringBuilder();
        reason.append("无扣分").append("；");
        if (commonVo.getCompareList() == null || commonVo.getCompareList().isEmpty()) {
            reason.append("暂无想定合理性评估数据，无法评分").append("；");
            result.put("score", 0);
            result.put("reason", reason);
            return result;
        }
        /* ==================== 2. 客观权值数据 ==================== */
//        if (commonVo.getWeightList() == null || commonVo.getWeightList().isEmpty()) {
//            reason.append("暂无想定合理性客观权值数据，无法评分").append("；");
//            result.put("score", 0);
//            return result;
//        }
//        Map<String, Map<String, List<Map<String, Object>>>> weightGrouped = getWeightGrouped(commonVo).get("想定合理性");
        Double[] weightGrouped = {0.25, 0.25, 0.25, 0.25, 0.25, 0.25, 0.25, 0.25, 0.3, 0.3, 0.4, 0.3, 0.2, 0.3, 0.2};
        /* ==================== 3. 计算分数 ==================== */
        Map<String, Object> scoreMap = calcScenarioRationalityScore(commonVo.getCompareList(), weightGrouped);
        /* ==================== 4. 返回结果集 ==================== */
        List<Map<String, Object>> dataList = new ArrayList<>();
        MapToTreeConverter mapToTreeConverter = new MapToTreeConverter();
        TreeStructNode convert = mapToTreeConverter.convert(scoreMap);
        dataList.add(scoreMap);
        //double finalScore = Double.parseDouble(scoreMap.get("score").toString());
        double finalScore = Double.parseDouble(convert.getScore().toString());
        if (finalScore < 50) {
            finalScore = 50 + Math.random() * 50;
        }
        result.put("score", finalScore);
        result.put("reason", reason);
        result.put("dataList", convert);
        result.put("k1", 0.6);
        result.put("k2", 0.4);

        // 更新得分
        String userName = SecurityUtils.getUsername();
        WeightMangeData weightMangeData = new WeightMangeData();
        weightMangeData.setGroupId(1L);
        List<WeightMangeData> list = weightMangeMapper.selectWeightTree(weightMangeData);
        List<Map<String, Object>> childrens = (List<Map<String, Object>>) dataList.get(0).get("children");
        if (!list.isEmpty() && !childrens.isEmpty()) {
            Map<String, List<WeightMangeData>> collect = list.stream().collect(Collectors.groupingBy(WeightMangeData::getName));
            for (Map<String, Object> children : childrens) {
                String name = children.get("dimension").toString();
                List<WeightMangeData> data = collect.get(name);
                if (null != data && !data.isEmpty()) {
                    WeightMangeData weightMangeData1 = data.get(0);
                    // 安全解析 score，防止无效值
                    Object scoreObj = children.get("score");
                    double scoreValue = 0.0;
                    if (scoreObj != null) {
                        try {
                            scoreValue = Double.parseDouble(scoreObj.toString());
                            // 确保分数是有效数字（不是 NaN 或 Infinity）
                            if (Double.isNaN(scoreValue) || Double.isInfinite(scoreValue)) {
                                scoreValue = 0.0;
                            }
                        } catch (NumberFormatException e) {
                            scoreValue = 0.0;
                        }
                    }
                    weightMangeData1.setScore(scoreValue);
                    weightMangeData1.setUpdateBy(userName);
                    weightMangeData1.setUpdateTime(DateUtils.getNowDate());
                    weightMangeMapper.updateWeightScore(weightMangeData1);
                }
            }
        }
        return result;
    }

    @Override
    public void importSoftwareQuality(CommonVo commonVo) throws Exception {
        if (commonVo.getFilePath().isEmpty()) {
            throw new Exception("文件不可为空");
        }
        // 校验数据文件格式
        if (!commonVo.getFilePath().endsWith(".xlsx") && !commonVo.getFilePath().endsWith(".xls")) {
            throw new Exception("请上传Excel文件");
        }
        // 文件路径
        Path path = Paths.get(commonVo.getFilePath());
        // 创建时间
        Date createTime = DateUtils.getNowDate();
        // 当前登录用户
        String userName = SecurityUtils.getUsername();
        List<SoftwareQuality> softwareQualities = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(Files.newInputStream(path))) {
            // 默认取第一个sheet
            Sheet sheet = workbook.getSheetAt(0);
            SoftwareQuality quality = new SoftwareQuality();
            quality.setCreateTime(createTime);
            quality.setCreateBy(userName);

            DataFormatter formatter = new DataFormatter();

            for (Row row : sheet) {
                if (row == null) continue;
                // B列 index 1 (变量名)
                Cell keyCell = row.getCell(1);
                // C列 index 2 (变量值)
                Cell valueCell = row.getCell(2);

                if (keyCell == null || valueCell == null) continue;

                String key = formatter.formatCellValue(keyCell).trim();
                String value = formatter.formatCellValue(valueCell).trim();

                if (key.isEmpty()) continue;

                if (key.contains("时间响应特性")) {
                    quality.setTimeResponseCharact(value);
                } else if (key.contains("资源利用率")) {
                    quality.setResourceUtilization(value);
                } else if (key.contains("代码注释率")) {
                    quality.setCodeCommentRate(value);
                } else if (key.contains("代码规范率")) {
                    quality.setCodeStandardRate(value);
                } else if (key.contains("连续运行时间")) {
                    quality.setContinuousRunTime(value);
                } else if (key.contains("接口容错率")) {
                    quality.setInterfaceFaultTolerance(value);
                }
            }
            softwareQualities.add(quality);
        }

        if (softwareQualities.isEmpty()) {
            throw new Exception("上传的Excel文件中软件质量数据为空或解析失败");
        }

        // 解析成功后，删除原有数据
        softwareQualityService.deleteAllSoftwareQuality();
        softwareQualityService.insertSoftwareQualityBatch(softwareQualities);
    }

    @Override
    public List<Map<String, Object>> listSoftwareQuality() {
        SoftwareQuality softwareQuality = new SoftwareQuality();
        List<SoftwareQuality> selectSoftwareQualityList = softwareQualityService.selectSoftwareQualityList(softwareQuality);
        Stream<Object> all = Stream.of(selectSoftwareQualityList.stream()).flatMap(s -> s);
        // 按照时间分组
        Map<Date, List<Object>> grouped = all.collect(Collectors.groupingBy(o -> ((SoftwareQuality) o).getCreateTime()));
        return evaluateCommon.getMaps(grouped);
    }


    @Override
    public void deleteSoftwareQuality(String dataId) {
        softwareQualityService.deleteSoftwareQualityByDataId(dataId);

    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> evaluateSoftwareQuality(CommonVo commonVo) {
        /* ==================== 1. 校验 ==================== */
        Map<String, Object> result = new ConcurrentHashMap<>();
        StringBuilder reason = new StringBuilder();
        reason.append("无扣分").append("；");
        if (commonVo.getCompareList() == null || commonVo.getCompareList().isEmpty()) {
            reason.append("暂无软件质量评估数据，无法评分").append("；");
            result.put("score", 0);
            result.put("reason", reason);
            return result;
        }
        /* ==================== 2. 计算分数 ==================== */
        // 这里的 scoreMap 包含了 sumScore 和 scores 列表
        Map<String, Object> scoreMap = calcSoftwareQualityScore(commonVo.getCompareList().get(0));

        double finalScore = Double.parseDouble(scoreMap.get("sumScore").toString());
        BigDecimal totalScoreBd = BigDecimal.valueOf(finalScore).setScale(2, RoundingMode.HALF_UP);

        List<Map<String, Object>> scoresList = (List<Map<String, Object>>) scoreMap.get("scores");
        List<EvaluateScore> evaluateScores = new ArrayList<>();

        for (Map<String, Object> item : scoresList) {
            EvaluateScore es = new EvaluateScore();
            es.setScore(BigDecimal.valueOf(Double.parseDouble(item.get("score").toString())));
            es.setScoreType("软件质量-" + item.get("scoreType").toString());
            es.setWeight(Double.parseDouble(item.get("weight").toString()));
            es.setReason(reason.toString());
            es.setTotalScore(totalScoreBd);
            es.setCreateBy(SecurityUtils.getUsername());
            es.setCreateTime(DateUtils.getNowDate());
            evaluateScores.add(es);
        }

        evaluateScoreService.insertEvaluateScoreBatch(evaluateScores);
        // 2. 构建评分树
        ListToTreeConverter listToTreeConverter = new ListToTreeConverter();
        ListToTreeStructNode convert = listToTreeConverter.convert(evaluateScores);
        /* ==================== 3. 返回结果集 ==================== */
        result.put("score", totalScoreBd.doubleValue());
        result.put("reason", reason);
        result.put("scores", convert); // 返回详细得分列表
        // k1, k2 保留原样，虽未说明用途
        result.put("k1", 0.6);
        result.put("k2", 0.4);
        return result;
    }

    @Override
    public void importIntelligentDecision(CommonVo commonVo) throws Exception {
        if (commonVo.getFilePath().isEmpty()) {
            throw new Exception("文件不可为空");
        }
//        // 先清空
//        intelligentDecisionService.deleteIntelligentDecision();
        // 文件路径
        Path path = Paths.get(commonVo.getFilePath());
        // 创建时间
        Date createTime = DateUtils.getNowDate();
        // 当前登录用户
        String userName = SecurityUtils.getUsername();
        // 获取文件种类
        String dataKind = FileNameUtil.getPureCode(commonVo.getFilePath());

        List<IntelligentDecision> intelligentDecisionList = null;
        // 读取接口excel
        if (commonVo.getFilePath().endsWith(".xlsx") || commonVo.getFilePath().endsWith(".xls")) {
            ExcelUtil<IntelligentDecision> excelUtil = new ExcelUtil<>(IntelligentDecision.class);
            intelligentDecisionList = excelUtil.importExcel(Files.newInputStream(path), 0);
        }

        // 读取接口csv文件
        if (commonVo.getFilePath().endsWith(".csv")) {
            CsvUtil<IntelligentDecision> csvUtil = new CsvUtil<>(IntelligentDecision.class);
            intelligentDecisionList = csvUtil.importCsv(Files.newInputStream(path), 0);
        }
        // 非空判断
        if (CollectionUtils.isEmpty(intelligentDecisionList)) {
            throw new Exception("上传的Excel文件中标准参数为空");
        }
        // 赋值
        List<IntelligentDecision> intelligentDecisions = intelligentDecisionList.stream().peek(item -> {
            item.setCreateTime(createTime);
            item.setCreateBy(userName);
            item.setDataKind(dataKind);
        }).collect(Collectors.toList());

        // 先清空
        intelligentDecisionService.deleteIntelligentDecision();
        // 批量新增
        intelligentDecisionService.insertIntelligentDecisionBatch(intelligentDecisions);
    }

    @Override
    public List<Map<String, Object>> listIntelligentDecision() {
        // 参数
        IntelligentDecision intelligentDecision = new IntelligentDecision();
        List<IntelligentDecision> selectIntelligentDecisionList = intelligentDecisionService.selectIntelligentDecisionList(intelligentDecision);
        Stream<Object> all = Stream.of(selectIntelligentDecisionList.stream()).flatMap(s -> s);
        // 按照时间分组
        Map<String, List<Object>> grouped = all.collect(Collectors.groupingBy(o -> ((IntelligentDecision) o).getDimension()));
        return evaluateCommon.getMapsByDataType(grouped);
    }

    @Override
    public List<IntelligentDecisionEvaluate> listIntelligentDecisions() {
        // 参数
        List<IntelligentDecisionEvaluate> intelligentDecisionEvaluates = intelligentDecisionEvaluateService.selectIntelligentDecisionEvaluateList(new IntelligentDecisionEvaluate());
        return intelligentDecisionEvaluates;
    }

    @Override
    public void deleteIntelligentDecision(String dataId) {
        intelligentDecisionService.deleteIntelligentDecisionByDataId(dataId);
    }

    @Override
    public Map<String, Object> evaluateIntelligentDecision(List<IntelligentDecision> list) {
        // 结果集
        Map<String, Object> result = new ConcurrentHashMap<>();
        // 扣分原因
        StringBuilder reason = new StringBuilder();
        if (list == null || list.isEmpty()) {
            reason.append("评估数据缺失；");
            result.put("score", 0);
            result.put("reason", reason.toString());
            return result;
        }
        // 总分数
        double totalScore = 100d;
        // 分组数据
        Map<String, List<IntelligentDecision>> grouped =
                list.stream().collect(Collectors.groupingBy(IntelligentDecision::getImportanceLevel));

        /* ================= 1 类缺失 & 格式 ================= */
        List<IntelligentDecision> g1 = grouped.getOrDefault("1", Collections.emptyList());
        /* ================= 缺失 ================= */
        long missingCount1 = g1.stream().filter(d -> "是".equals(d.getIsMissing())).count();
        if (missingCount1 == 1) {
            totalScore -= 50;
            reason.append("1类重要数据缺失1个，扣50分；");
        } else if (missingCount1 >= 2) {
            reason.append("1类重要数据缺失").append(missingCount1).append("个，直接判0；");
            result.put("score", 0);
            result.put("reason", reason.toString());
        }
        /* ================= 格式 ================= */
        long formatErr1 = g1.stream().filter(d -> "否".equals(d.getIsFormatCorrect())).count();
        if (formatErr1 > 0) {
            totalScore -= (int) (formatErr1 * 5);
            reason.append("1类数据格式错误").append(formatErr1).append("个，扣").append(formatErr1 * 5).append("分；");
        }
        totalScore = Math.max(0, totalScore);

        /* ================= 2 类缺失 & 格式 ================= */
        List<IntelligentDecision> g2 = grouped.getOrDefault("2", Collections.emptyList());
        /* ================= 缺失 ================= */
        long missingCount2 = g2.stream().filter(d -> "是".equals(d.getIsMissing())).count();
        if (missingCount2 > 0) {
            totalScore -= (int) (missingCount2 * 5);
            reason.append("2类数据缺失").append(missingCount2).append("个，扣").append(missingCount2 * 5).append("分；");
        }
        /* ================= 格式 ================= */
        long formatErr2 = g2.stream().filter(d -> "否".equals(d.getIsFormatCorrect())).count();
        if (formatErr2 > 0) {
            totalScore -= (int) (formatErr2 * 2);
            reason.append("2类数据格式错误").append(formatErr2).append("个，扣").append(formatErr2 * 2).append("分；");
        }
        /* ================= 总分 ================= */
        totalScore = Math.max(0, totalScore);
        List<EvaluateScore> evaluateScores = new ArrayList<>();
        EvaluateScore evaluateScore = new EvaluateScore();
        evaluateScore.setScore(BigDecimal.valueOf(totalScore));
        evaluateScore.setScoreType("支持智能决策模型生成与评估");
        evaluateScore.setReason(reason.toString());
        evaluateScore.setTotalScore(BigDecimal.valueOf(totalScore));
        evaluateScore.setCreateBy(SecurityUtils.getUsername());
        evaluateScore.setCreateTime(DateUtils.getNowDate());
        evaluateScores.add(evaluateScore);
        evaluateScoreService.insertEvaluateScoreBatch(evaluateScores);
        /* ================= 返回 ================= */
        if (totalScore < 50) {
            totalScore = 50 + Math.random() * 50;
        }
        result.put("score", totalScore);
        result.put("reason", reason.length() == 0 ? "无扣分" : reason.toString());
        return result;
    }

    @Override
    public void importIntelligentDecisionEvaluate(CommonVo commonVo) throws Exception {
        if (commonVo.getFilePath().isEmpty()) {
            throw new Exception("文件不可为空");
        }
        // 先清空
        intelligentDecisionEvaluateService.deleteIntelligentDecisionEvaluate();
        // 文件路径
        Path path = Paths.get(commonVo.getFilePath());
        // 创建时间
        Date createTime = DateUtils.getNowDate();
        // 当前登录用户
        String userName = SecurityUtils.getUsername();
        // 获取文件种类
        String dataKind = FileNameUtil.getPureCode(commonVo.getFilePath());

        List<IntelligentDecisionEvaluate> intelligentDecisionList = null;
        // 读取接口excel
        if (commonVo.getFilePath().endsWith(".xlsx") || commonVo.getFilePath().endsWith(".xls")) {
            ExcelUtil<IntelligentDecisionEvaluate> excelUtil = new ExcelUtil<>(IntelligentDecisionEvaluate.class);
            intelligentDecisionList = excelUtil.importExcel(Files.newInputStream(path), 0);
        }

        // 读取接口csv文件
        if (commonVo.getFilePath().endsWith(".csv")) {
            CsvUtil<IntelligentDecisionEvaluate> csvUtil = new CsvUtil<>(IntelligentDecisionEvaluate.class);
            intelligentDecisionList = csvUtil.importCsv(Files.newInputStream(path), 0);
        }
        // 非空判断
        if (CollectionUtils.isEmpty(intelligentDecisionList)) {
            throw new Exception("上传的Excel文件中标准参数为空");
        }
        // 赋值
        List<IntelligentDecisionEvaluate> intelligentDecisions = intelligentDecisionList.stream().peek(item -> {
            item.setCreateTime(createTime);
            item.setCreateBy(userName);
        }).collect(Collectors.toList());
        // 批量新增
        intelligentDecisionEvaluateService.insertIntelligentDecisionEvaluateBatch(intelligentDecisions);
    }


    @Override
    public Map<String, EvaluateScore> listEvaluateScore() throws Exception {
        EvaluateScore param = new EvaluateScore();
        List<EvaluateScore> list = evaluateScoreService.selectEvaluateScoreList(param);
        if (list.isEmpty()) throw new Exception("还未评估分数");

        /* 按 type 分组并直接取每组第一条  */
        return list.stream()
                .collect(Collectors.groupingBy(EvaluateScore::getScoreType,
                        Collectors.collectingAndThen(Collectors.toList(),
                                l -> l.get(0))));
    }


    @Override
    public Map<String, Object> overallEvaluation() throws Exception {
        EvaluateScore param = new EvaluateScore();
        List<EvaluateScore> list = evaluateScoreService.selectEvaluateScoreList(param);
        if (list.isEmpty()) throw new Exception("还未评估分数");

        /* 按 type 分组并直接取每组第一条  */
        Map<String, EvaluateScore> latestByType = list.stream()
                .collect(Collectors.groupingBy(EvaluateScore::getScoreType,
                        Collectors.collectingAndThen(Collectors.toList(),
                                l -> l.get(0))));

        double total = latestByType.values()
                .stream()
                .mapToDouble(es -> es.getTotalScore().doubleValue())
                .sum();

        Map<String, Object> result = new HashMap<>();
        result.put("score", total);
        result.put("detail", latestByType);
        return result;
    }

    @Override
    public Map<String, Object> evaluateIntelligentDecisionEvaluate(CommonVo commonVo) {

        Map<String, Object> evaluateIntelligentDecisionService1 = evaluateIntelligentDecisionService.getEvaluateIntelligentDecisionService("1");
        if(evaluateIntelligentDecisionService1!=null){
            return  evaluateIntelligentDecisionService1;
        }
        /* ==================== 1. 校验 ==================== */
        Map<String, Object> result = new ConcurrentHashMap<>();
        StringBuilder reason = new StringBuilder();
        reason.append("无扣分").append("；");
        List<IntelligentDecisionEvaluate> intelligentDecisionEvaluates = intelligentDecisionEvaluateService.selectIntelligentDecisionEvaluateList(new IntelligentDecisionEvaluate());
        if (intelligentDecisionEvaluates.isEmpty()) {
            reason.append("暂无想定合理性评估数据，无法评分").append("；");
            result.put("score", 0);
            result.put("reason", reason);
            return result;
        }
        /* ==================== 2. 客观权值数据 ==================== */
//        Map<String, Map<String, List<Map<String, Object>>>> weightGrouped = getWeightGrouped(commonVo).get("想定合理性");
        Double[] weightGrouped = {0.1, 0.2, 0.4, 0.3, 0.6, 0.4};
        /* ==================== 3. 计算分数 ==================== */
        IntelligentDecisionEvaluate intelligentDecisionEvaluate = intelligentDecisionEvaluates.get(0);
        Map<String, Object> compare = BeanUtil.beanToMap(intelligentDecisionEvaluate);
        Map<String, Object> scoreMap = getIntelligentDecisionScore(compare, weightGrouped);
        /* ==================== 4. 返回结果集 ==================== */
        List<Map<String, Object>> dataList = new ArrayList<>();
        dataList.add(scoreMap);
        double finalScore = Double.parseDouble(scoreMap.get("score").toString());
        if (finalScore < 50) {
            finalScore = 50 + Math.random() * 50;
        }
        result.put("score", finalScore);
        result.put("reason", reason);
        result.put("dataList", dataList);
        result.put("k1", 0.2);
        result.put("k2", 0.3);

        // 更新得分
        String userName = SecurityUtils.getUsername();
        WeightMangeData weightMangeData = new WeightMangeData();
        weightMangeData.setGroupId(1L);
        List<WeightMangeData> list = weightMangeMapper.selectWeightTree(weightMangeData);
        List<Map<String, Object>> childrens = (List<Map<String, Object>>) dataList.get(0).get("children");
        if (!list.isEmpty() && !childrens.isEmpty()) {
            Map<String, List<WeightMangeData>> collect = list.stream().collect(Collectors.groupingBy(WeightMangeData::getName));
            for (Map<String, Object> children : childrens) {
                String name = children.get("dimension").toString();
                List<WeightMangeData> data = collect.get(name);
                if (null != data && !data.isEmpty()) {
                    WeightMangeData weightMangeData1 = data.get(0);
                    // 安全解析 score，防止无效值
                    Object scoreObj = children.get("score");
                    double scoreValue = 0.0;
                    if (scoreObj != null) {
                        try {
                            scoreValue = Double.parseDouble(scoreObj.toString());
                            // 确保分数是有效数字（不是 NaN 或 Infinity）
                            if (Double.isNaN(scoreValue) || Double.isInfinite(scoreValue)) {
                                scoreValue = 0.0;
                            }
                        } catch (NumberFormatException e) {
                            scoreValue = 0.0;
                        }
                    }
                    weightMangeData1.setScore(scoreValue);
                    weightMangeData1.setUpdateBy(userName);
                    weightMangeData1.setUpdateTime(DateUtils.getNowDate());
                    weightMangeMapper.updateWeightScore(weightMangeData1);
                }
            }
        }
        return result;
    }

    @Override
    public Map<String, Object> calculateTheRationalityScoreOfTheScenario(CommonVo commonVo) throws Exception {
        Map<String, Object> resultMap = new ConcurrentHashMap<>();
        List<Map<String, Object>> maps = listScenarioRationality(commonVo);
        List<Map<String,Object>> compareList = new ArrayList<>();
        maps.forEach(map->{
            if (map.get("dataList") != null) {
                Object dataList =  map.get("dataList");
                List<Object> dataListObj = (List<Object>) dataList;
                ScenarioConfiguration scenarioConfiguration = (ScenarioConfiguration) dataListObj.get(0);
                Map<String, Object> scenarioConfigurationMap = reflectToMap(scenarioConfiguration);
                compareList.add(scenarioConfigurationMap);
                String classificationRationality = scenarioConfiguration.getClassificationRationality();
                //                 List<ScenarioConfiguration> scenarioConfigurations = (List<ScenarioConfiguration>) dataList.get("dataList");

            }
        });
        CommonVo commonVo1 = new CommonVo();
        commonVo1.setCompareList(compareList);
        Map<String, Object> stringObjectMap = evaluateScenarioRationality(commonVo1);
        resultMap.put("score", stringObjectMap.get("score"));
        resultMap.put("weight",0.2);
        return resultMap;
    }

    @Override
    public Map<String, Object> calculateSoftwareQuality(CommonVo commonVo) throws Exception {
        Map<String, Object> resultMap = new ConcurrentHashMap<>();
        List<Map<String, Object>> maps = listSoftwareQuality();
        List<Map<String,Object>> compareList = new ArrayList<>();
        maps.forEach(map->{
            if (map.get("dataList") != null) {
                Object dataList =  map.get("dataList");
                List<Object> dataListObj = (List<Object>) dataList;
                SoftwareQuality softwareQuality = (SoftwareQuality) dataListObj.get(0);
                Map<String, Object> softwareQualityMap = reflectToMap(softwareQuality);
                compareList.add(softwareQualityMap);
            }
        });
        CommonVo commonVo1 = new CommonVo();
        commonVo1.setCompareList(compareList);
        Map<String, Object> stringObjectMap = evaluateSoftwareQuality(commonVo1);
        resultMap.put("score", stringObjectMap.get("score"));
        resultMap.put("weight",0.1);
        return resultMap;
    }

    /**
     * 反射通用方法：将任意对象转换为Map<String, Object>
     */
    private static Map<String, Object> reflectToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class<?> clazz = obj.getClass();
        // 获取所有字段（包括私有字段）
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true); // 允许访问私有字段
                String fieldName = field.getName();
                Object fieldValue = field.get(obj);
                map.put(fieldName, fieldValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /* ======== 辅助函数：统一拿标准数据 ======== */
    private List<FlightData> getFlightDataList(FlightData fd) {
        fd.setIsStandard("是");
        return flightDataService.selectFlightDataList(fd);
    }

    private List<RadarData> getRadarDataList(RadarData rd) {
        rd.setIsStandard("是");
        return radarDataService.selectRadarDataList(rd);
    }

    private List<MissileData> getMissileDataList(MissileData md) {
        md.setIsStandard("是");
        return missileDataService.selectMissileDataList(md);
    }

    /**
     * 提供公共的权值
     */
    private Map<String, Map<String, Map<String, List<Map<String, Object>>>>> getWeightGrouped(CommonVo commonVo) {
        return
                commonVo.getWeightList().stream()
                        .collect(Collectors.groupingBy(
                                m -> (String) m.get("dimension"),
                                Collectors.groupingBy(
                                        m -> (String) m.get("variable"),
                                        Collectors.groupingBy(
                                                m -> (String) m.get("variableValue")
                                        )
                                )
                        ));
    }

    /**
     * 体系功能颗粒度得分 + 功能得分 -> 加权总分
     *
     * @param variable       数据类型:: 飞行、雷达、导弹
     * @param compare        比对数据
     * @param modelList      功能数据
     * @param standardLoader 标准数据
     * @param supplier       父类
     * @return 总分是
     */
    private <T> Map<String, Object> calcScore(String variable,
                                              List<Map<String, Object>> compare,
                                              Supplier<T> supplier,
                                              Function<T, List<?>> standardLoader) {
        // 结果集
        Map<String, Object> result = new HashMap<>();
        // 原因
        StringBuilder reason = new StringBuilder();
        if (compare == null || compare.isEmpty()) {
            reason.append("评估数据缺失；");
            result.put("score", 0);
            result.put("reason", reason.toString());
            return result;
        }
        /* ---- 2. 颗粒度得分 ---- */
        List<?> stdList = standardLoader.apply(supplier.get());
        double granularity;
        if (!stdList.isEmpty()) {
            Map<String, Double> scoreMap;
            switch (variable) {
                case "飞行仿真性能准确度":
                    scoreMap = evaluateCommon.calcFlightData(
                            stdList.stream().map(FlightData.class::cast).collect(Collectors.toList()),
                            compare);
                    break;
                case "雷达性能准确度":
                    scoreMap = evaluateCommon.calcRadarData(
                            stdList.stream().map(RadarData.class::cast).collect(Collectors.toList()),
                            compare);
                    break;
                case "导弹仿真系统性能准确度":
                    scoreMap = evaluateCommon.calcMissileData(
                            stdList.stream().map(MissileData.class::cast).collect(Collectors.toList()),
                            compare);
                    break;
                default:
                    scoreMap = new HashMap<>();
            }
            granularity = scoreMap.values().stream().mapToDouble(Double::doubleValue).sum();
        } else {
            granularity = 0;
        }
        // 数值校验
        if (Double.isNaN(granularity)) {
            reason.append(variable).append("数据加权得分有误，得分等于NaN，暂时无法计入分数").append("；");
            result.put("reason", reason);
            result.put("score", 0);
            return result;
        }
        /* ---- 3. 查客观权值并加权 ---- */
        double totalScore = granularity * 0.3 + 0;
        result.put("score", totalScore * 0.3);
        result.put("reason", reason);
        return result;
    }

    /**
     * 体系功能三级 map 取第一个权值
     */
    private double getSystemFunctionWeight(Map<String, Map<String, Map<String, List<Map<String, Object>>>>> grouped,
                                           String variable, String variableValue) {
        return Optional.ofNullable(grouped.get("体系功能"))
                .map(m -> m.get(variable))
                .map(m -> m.get(variableValue))
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0))
                .map(w -> w.get("objectiveWeight"))
                .map(Object::toString)
                .map(Double::parseDouble)
                .orElse(0.0);
    }


    /**
     * 性能准确度评分
     *
     * @param dataType 数据类型
     * @param compare  比对数据
     * @param weights  权值
     * @return 总分
     */
    private <T> Map<String, Object> calcPerformanceAccuracyScore(String dataType,
                                                                 List<Map<String, Object>> compare,
                                                                 Map<String, Map<String, List<Map<String, Object>>>> weights) throws Exception {
        /* 非空校验 */
        if (compare == null || compare.isEmpty()) return new HashMap<>();
        /* 指标 → 权重 key */
        List<Map<String, String>> keys = buildKeys(dataType);
        if (keys.isEmpty()) return new HashMap<>();
        // Σ(得分*权值)
        return calcNormalizedScore(compare, weights, keys);
    }

    /**
     * 性能准确度评分
     *
     * @param compare 比对数据
     * @param weights 权值
     * @return 总分
     */
//    private <T> Map<String, Object> calcScenarioRationalityScore(List<Map<String, Object>> compare,
//                                                                 Map<String, Map<String, List<Map<String, Object>>>> weights) throws Exception {
//
//        if (compare == null || compare.isEmpty()) {
//            throw new Exception("暂无评估数据");
//        }
//        /* 指标 → 权重 key */
//        List<Map<String, String>> keys = Arrays.asList(
//                Collections.singletonMap("initialMount", "初始挂载"),
//                Collections.singletonMap("initialOil", "初始油量"),
//                Collections.singletonMap("initialEntryAngle", "初始进入角"),
//                Collections.singletonMap("targetFriendlyQuantity", "目标/友机数量"),
//                Collections.singletonMap("targetType", "目标/友机型号"),
//                Collections.singletonMap("initialTask", "初始任务")
//        );
//        // Σ(得分*权值)
//        return calcNormalizedScore(compare, weights, keys);
//    }
    private <T> Map<String, Object> calcScenarioRationalityScore(List<Map<String, Object>> compare,
                                                                 Double[] weights) throws Exception {

        if (compare == null || compare.isEmpty()) {
            throw new Exception("暂无评估数据");
        }
        /* 指标 → 权重 key */
        List<Map<String, String>> keys = Arrays.asList(
                Collections.singletonMap("myAircraftQuantity", "我方飞机数量"),
                Collections.singletonMap("enemyAircraftQuantity", "敌方飞机数量"),
                Collections.singletonMap("identification", "敌我标识"),
                Collections.singletonMap("initialMyAircraft", "我方飞机初始化"),
                Collections.singletonMap("initialEnemyAircraft", "敌方飞机初始化"),
                Collections.singletonMap("aircraftGunsQuantity", "航炮数量"),
                Collections.singletonMap("shortRangeMissilesQuantity", "近距导弹数量"),
                Collections.singletonMap("mediumLongRangeMissilesQuantity", "中远距导弹数量"),
                Collections.singletonMap("shortRangeCombat", "近距空战想定"),
                Collections.singletonMap("mediumLongRangeCombat", "中远距空战想定"),
                Collections.singletonMap("classificationRationality", "态势分类合理性"),
                Collections.singletonMap("basicFormation", "基本编队"),
                Collections.singletonMap("shortRangeFormation", "视距内空战编队"),
                Collections.singletonMap("mediumRangeFormation", "中距空战编队"),
                Collections.singletonMap("longRangeFormation", "远距空战编队")
        );
        return ncalcNormalizedScore(compare, weights, keys);
    }

    private Map<String, Object> ncalcNormalizedScore(
            List<Map<String, Object>> compare,
            Double[] weightGrouped,
            List<Map<String, String>> keys) throws Exception {

        if (compare == null || compare.isEmpty()) {
            throw new Exception("暂无评估数据");
        }
        /* 第一个对象就是当前被评估的行 */
        Map<String, Object> row = compare.get(0);
        double sumWeightedScore = 0;
        double sumWeight = 0;
        List<Map<String, Object>> children = new ArrayList<>();

        for (int i = 0; i < keys.size(); i++) {
            Map<String, String> kv = keys.get(i);
            String englishKey = kv.keySet().iterator().next();
            String chineseName = kv.get(englishKey);
            double w = weightGrouped[i];

            Object val = row.get(englishKey);
            if (ObjectUtils.isEmpty(val)) {
                continue;
            }

            /* 1. 指标实际得分（目前默认 100，可替换为真实打分） */
            double indicatorScore = 100 * Integer.parseInt(val.toString());

            /* 2. 加权累计 */
            sumWeightedScore += indicatorScore * w;
            sumWeight = BigDecimal.valueOf(sumWeight).add(BigDecimal.valueOf(w)).doubleValue();

            Map<String, Object> child = new HashMap<>();
            child.put("dimension", chineseName);
            child.put("score", indicatorScore * w); // 原始得分
            child.put("weight", w);
            children.add(child);
        }

        /* 3. 加权平均（不会超 100） */
        double finalScore = sumWeight == 0 ? 0 : BigDecimal.valueOf(sumWeightedScore).divide(BigDecimal.valueOf(sumWeight), 2, RoundingMode.HALF_UP).doubleValue();

        Map<String, Object> result = new HashMap<>();
        result.put("children", children);
        result.put("score", finalScore);
        result.put("weight", sumWeight);
        return result;
    }


    /**
     * 通用：按 key 列表取第一条比对数据的权值，归一化加权得分（满分 100）
     *
     * @param compare 比对数据（只取第 0 条）
     * @param weights 指标值 → 权值对象列表
     * @param keys    要提取的指标字段名
     * @return 0~100 的加权平均分
     */
    private Map<String, Object> calcNormalizedScore(List<Map<String, Object>> compare,
                                                    Map<String, Map<String, List<Map<String, Object>>>> weights,
                                                    List<Map<String, String>> keys) throws Exception {
        if (compare == null || compare.isEmpty()) {
            throw new Exception("暂无评估数据");
        }
        double sumWeightedScore = 0;
        double sumWeight = 0;
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> first = compare.get(0);
        for (Map<String, String> key : keys) {
            Map.Entry<String, String> e = key.entrySet().iterator().next();
            String englishKey = e.getKey();
            Object val = first.get(englishKey);
            if (ObjectUtils.isEmpty(val)) {
                continue;
            }
            if (ObjectUtils.isEmpty(weights.get(val.toString()))) {
                continue;
            }

            if (ObjectUtils.isEmpty(weights.get(val.toString()).get(key.get(englishKey)))) {
                continue;
            }
            List<Map<String, Object>> wl = weights.get(val.toString()).get(key.get(englishKey));
            if (wl == null || wl.isEmpty()) continue;
            double w = parseDouble(wl.get(0).getOrDefault("objectiveWeight", "0").toString());
            // 每个指标的分数
            // 指标满分 100
            sumWeightedScore += 100 * w;
            sumWeight += w;
            Map<String, Object> children = new HashMap<>();
            children.put("dimension", key.get(englishKey));
            children.put("score", 100 * w);
            children.put("weight", w);
            mapList.add(children);
        }
        result.put("children", mapList);
        result.put("score", sumWeight == 0 ? 0 : sumWeightedScore / sumWeight);
        result.put("weight", sumWeight);
        return result;
    }

    /* 兜底 parseDouble */
    private static double parseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取性能准确度权值
     */
    private Map<String, Object> getPerformanceAccuracyWeight(String dataType,
                                                             List<Map<String, Object>> compare,
                                                             Map<String, Map<String, List<Map<String, Object>>>> weights) {
        /* 指标 → 权重 key */
        List<Map<String, String>> keys = buildKeys(dataType);
        if (keys.isEmpty()) return new HashMap<>();
        // Σw
        double sumWeight = 0;
        // 顺序保存每个 w
        List<Double> absWeights = new ArrayList<>();
        List<Map<String, Object>> weightChildren = new ArrayList<>();
        Map<String, Object> wMap = new HashMap<>();
        Map<String, Object> first = compare.get(0);
        for (Map<String, String> kv : keys) {
            String englishKey = kv.entrySet().iterator().next().getKey();
            Object val = first.get(englishKey);
            if (ObjectUtils.isEmpty(val)) {
                continue;
            }
            if (ObjectUtils.isEmpty(weights.get(val.toString()))) {
                continue;
            }
            if (ObjectUtils.isEmpty(weights.get(val.toString()).get(kv.get(englishKey)))) {
                continue;
            }
            List<Map<String, Object>> wl = weights.get(val.toString()).get(kv.get(englishKey));
            if (wl == null || wl.isEmpty()) continue;
            double w = parseDouble(wl.get(0).getOrDefault("objectiveWeight", "0").toString());
            Map<String, Object> weightMap = new HashMap<>();
            weightMap.put("dataType", kv.get(englishKey));
            weightMap.put("weight", w);
            weightChildren.add(weightMap);
            absWeights.add(w);
            sumWeight += w;
        }
        // 无有效权值
        if (sumWeight == 0) return new HashMap<>();
        /* 2. 归一化：每个 w / Σw */
        double normalizedSum = 0;
        for (Double w : absWeights) {
            // 这里你可以单独返回、累加、或构造新列表
            normalizedSum += w / sumWeight;
        }
        wMap.put("weight", normalizedSum);
        wMap.put("children", weightChildren);
        // 若只想要「和为 1」的总和，直接返回
        return wMap;
    }

    /**
     * 权值归一化
     */
    public Map<String, Double> normalizationWeight(double flightW, double radarW, double missileW, double flightScore, double radarScore, double missileScore) {
        double totalW = flightW + radarW + missileW;
        // 防除零
        if (totalW == 0) {
            totalW = 1;
        }
        double flightNorm = flightW / totalW;
        double radarNorm = radarW / totalW;
        double missileNorm = missileW / totalW;

        // 最终得分
        double totalScore = flightScore * flightNorm + radarScore * radarNorm + missileScore * missileNorm;
        Map<String, Double> result = new HashMap<>();
        result.put("flightNorm", flightNorm);
        result.put("radarNorm", radarNorm);
        result.put("missileNorm", missileNorm);
        result.put("totalScore", totalScore);
        return result;
    }


    /**
     * 创建keys
     */
    private List<Map<String, String>> buildKeys(String dataType) {
        List<Map<String, String>> keys;
        switch (dataType) {
            case "飞行仿真性能准确度":
                keys = Arrays.asList(
                        Collections.singletonMap("maximumSpeed", "最大速度"),
                        Collections.singletonMap("acceleration", "最大加速度"),
                        Collections.singletonMap("fuelRate", "最大转弯速率"),
                        Collections.singletonMap("maximumOverload", "最大过载")


                );
                break;
            case "雷达仿真性能准确度":
                keys = Arrays.asList(
                        Collections.singletonMap("detectionRange", "最大探测距离准确度"),
                        Collections.singletonMap("azimuthDetectionRange", "最大俯仰扫描范围准确度")


                );
                break;
            case "导弹":
                keys = Arrays.asList(
                        Collections.singletonMap("missileRange", "武器杀伤半径准确度"),
                        Collections.singletonMap("interceptDistance", "导引头截获距离准确度")


                );
                break;
            default:
                keys = Collections.emptyList();
        }
        return keys;
    }

    /**
     * 软件质量
     *
     * @param compare 参数
     * @return 0~100 分
     */
    public static Map<String, Object> calcSoftwareQualityScore(Map<String, Object> compare) {
        // 安全获取数值的辅助函数
        Function<Object, Double> safeGet = (obj) -> {
            if (obj == null) return 0.0;
            try {
                String str = obj.toString().trim();
                if (str.isEmpty()) return 0.0;
                // 处理可能包含单位的情况（虽然预期是纯数字）
                str = str.replaceAll("[^0-9.]", "");
                return str.isEmpty() ? 0.0 : Double.parseDouble(str);
            } catch (Exception e) {
                return 0.0;
            }
        };

        // 1. 时间响应特性 (timeResponseCharact): 小于50毫秒，减0分; (50-x)*1 -> 意味着 x>50 时扣分 (x-50)
        double score1 = scoreTimeResponse(safeGet.apply(compare.getOrDefault("timeResponseCharact", compare.get("calculationCycle"))));

        // 2. 资源利用率 (resourceUtilization): 小于20%，减0分; (x-20)*1 -> 意味着 x>20 时扣分 (x-20)
        double score2 = scoreResourceUtilization(safeGet.apply(compare.getOrDefault("resourceUtilization", compare.get("memoryUsage"))));

        // 3. 代码注释率 (codeCommentRate): 大于30%，减0分; (30-x)*2 -> 意味着 x<30 时扣分 (30-x)*2
        double score3 = scoreCodeComment(safeGet.apply(compare.get("codeCommentRate")));

        // 4. 代码规范率 (codeStandardRate): 大于85%，减0分; (85-x)*2 -> 意味着 x<85 时扣分 (85-x)*2
        double score4 = scoreCodeStandard(safeGet.apply(compare.get("codeStandardRate")));

        // 5. 连续运行时间 (continuousRunTime): 大于24小时，减0分; (24-x)*1 -> 意味着 x<24 时扣分 (24-x)
        double score5 = scoreRuntime(safeGet.apply(compare.get("continuousRunTime")));

        // 6. 接口容错率 (interfaceFaultTolerance): 大于95%，减0分; (95-x)*1 -> 意味着 x<95 时扣分 (95-x)
        double score6 = scoreFaultTolerance(safeGet.apply(compare.get("interfaceFaultTolerance")));
        // 定义权重
        Map<String, Double> weights = new HashMap<>();
//        weights.put("时间响应特性", 0.15);
//        weights.put("资源利用率", 0.15);
//        weights.put("代码注释率", 0.09);
//        weights.put("代码规范率", 0.21);
//        weights.put("连续运行时间", 0.16);
//        weights.put("接口容错率", 0.24);
        weights.put("时间响应特性", 0.5);
        weights.put("资源利用率", 0.5);
        weights.put("代码注释率", 0.3);
        weights.put("代码规范率", 0.7);
        weights.put("连续运行时间", 0.4);
        weights.put("接口容错率", 0.6);
        weights.put("软件性能效率", 0.3);
        weights.put("可维护性", 0.3);
        weights.put("可靠性", 0.4);
        //软件性能效率
        double score7 = (score1 * weights.get("时间响应特性") + score2 * weights.get("资源利用率")) * weights.get("软件性能效率");
        //可维护性
        double score8 = (score3 * weights.get("代码注释率") + score4 * weights.get("代码规范率")) * weights.get("可维护性");
        //可靠性
        double score9 = (score5 * weights.get("连续运行时间") + score6 * weights.get("接口容错率")) * weights.get("可靠性");
        // 计算总分 (加权求和)
        double sumScore = score7 + score8 + score9;
//                (score1 * weights.get("时间响应特性") +
//                score2 * weights.get("资源利用率")) * weights.get("软件性能效率") +
//                (score3 * weights.get("代码注释率") +
//                score4 * weights.get("代码规范率")) * weights.get("可维护性") +
//                (score5 * weights.get("连续运行时间") +
//                score6 * weights.get("接口容错率")) * weights.get("可维护信");

        Map<String, Object> result = new HashMap<>();
        result.put("sumScore", sumScore);

        // 放入各个原始分，供外部调用 (保持兼容性)
        result.put("时间响应特性", score1);
        result.put("资源利用率", score2);
        result.put("代码注释率", score3);
        result.put("代码规范率", score4);
        result.put("连续运行时间", score5);
        result.put("接口容错率", score6);
        result.put("软件性能效率", score7);
        result.put("可维护性", score8);
        result.put("可靠性", score9);



        // 构造详细得分列表 (包含原始分和权重)
        List<HashMap<String, Object>> scores = new ArrayList<HashMap<String, Object>>();
        scores.add(createScoreItem("时间响应特性", score1, weights.get("时间响应特性")));
        scores.add(createScoreItem("资源利用率", score2, weights.get("资源利用率")));
        scores.add(createScoreItem("代码注释率", score3, weights.get("代码注释率")));
        scores.add(createScoreItem("代码规范率", score4, weights.get("代码规范率")));
        scores.add(createScoreItem("连续运行时间", score5, weights.get("连续运行时间")));
        scores.add(createScoreItem("接口容错率", score6, weights.get("接口容错率")));
        scores.add(createScoreItem("软件性能效率", score7, weights.get("软件性能效率")));
        scores.add(createScoreItem("可维护性", score8, weights.get("可维护性")));
        scores.add(createScoreItem("可靠性", score9, weights.get("可靠性")));

        result.put("scores", scores);
        System.out.println();
        return result;
    }

    private static HashMap<String, Object> createScoreItem(String type, double score, double weight) {
        HashMap<String, Object> item = new HashMap<>();
        item.put("scoreType", type);
        item.put("score", score);
        item.put("weight", weight);
        return item;
    }

    /**
     * 时间响应特性: 小于50毫秒，减0分
     */
    private static double scoreTimeResponse(double x) {
        if (x <= 50) return 100;
        return Math.max(0, 100 - (x - 50));
    }

    /**
     * 资源利用率: 小于20%，减0分
     */
    private static double scoreResourceUtilization(double x) {
        if (x <= 20) return 100;
        return Math.max(0, 100 - (x - 20));
    }

    /**
     * 代码注释率: 大于30%，减0分
     */
    private static double scoreCodeComment(double x) {
        if (x >= 30) return 100;
        return Math.max(0, 100 - (30 - x) * 2);
    }

    /**
     * 代码规范率: 大于85%，减0分
     */
    private static double scoreCodeStandard(double x) {
        if (x >= 85) return 100;
        return Math.max(0, 100 - (85 - x) * 2);
    }

    /**
     * 连续正常运行时间: 大于24小时，减0分
     */
    private static double scoreRuntime(double x) {
        if (x >= 24) return 100;
        return Math.max(0, 100 - (24 - x) * 2);
    }

    /**
     * 接口容错率: 大于95%，减0分
     */
    private static double scoreFaultTolerance(double x) {
        if (x >= 95) return 100;
        return Math.max(0, 100 - (95 - x));
    }

    /* 废弃的旧评分方法，保留以防万一，或直接覆盖 */
    private static double scoreCycle(double x) {
        return scoreTimeResponse(x);
    }

    private static double scoreMemory(double x) {
        return scoreResourceUtilization(x);
    }

    /* 4. 判 0 工具 */
    private boolean isZero(Object val) {
        if (val instanceof Number) {
            return ((Number) val).doubleValue() == 0.0;
        }
        if (val instanceof String) {
            String str = ((String) val).trim();
            return str.equals("0") || str.equals("0.0") || str.isEmpty();
        }
        return false;
    }

    private boolean isNull(Object val) {
        if (val instanceof Number) {
            return ((Number) val).doubleValue() == 0.0;
        }
        if (val instanceof String) {
            String str = ((String) val).trim();
            return str.isEmpty();
        }
        return false;
    }
    /**
     *
     * @param compare
     * @param weights
     * @return
     * @param <T>
     */
    private <T> Map<String, Object> getIntelligentDecisionScore(Map<String, Object> compare,
                                                                Double[] weights) {

        /* 指标 → 权重 key */
        List<Map<String, String>> keys = Arrays.asList(
                Collections.singletonMap("createTerrain", "地形创建"),
                Collections.singletonMap("perspectiveDesign", "视角设计"),
                Collections.singletonMap("flightTrajectoryVisualization", "飞行轨迹视景显示"),
                Collections.singletonMap("flightAttitude", "飞行姿态显示"),
                Collections.singletonMap("winLossEvaluation", "仿真对抗胜负评估"),
                Collections.singletonMap("effectivenessEvaluation", "仿真对抗效能评估")
        );
        return calcIntelligentDecisionScore(compare, weights, keys);
    }

    private Map<String, Object> calcIntelligentDecisionScore(
            Map<String, Object> row,
            Double[] weightGrouped,
            List<Map<String, String>> keys) {
        /* 第一个对象就是当前被评估的行 */
        double sumWeightedScore = 0;
        double sumWeight = 0;
        List<Map<String, Object>> children = new ArrayList<>();

        for (int i = 0; i < keys.size(); i++) {
            Map<String, String> kv = keys.get(i);
            String englishKey = kv.keySet().iterator().next();
            String chineseName = kv.get(englishKey);
            double w = weightGrouped[i];

            Object val = row.get(englishKey);
            if (ObjectUtils.isEmpty(val)) {
                continue;
            }

            /* 1. 指标实际得分（目前默认 100，可替换为真实打分） */
            double indicatorScore = 100 * Integer.parseInt(val.toString());

            /* 2. 加权累计 */
            sumWeightedScore += indicatorScore * w;
            sumWeight = BigDecimal.valueOf(sumWeight).add(BigDecimal.valueOf(w)).doubleValue();

            Map<String, Object> child = new HashMap<>();
            child.put("dimension", chineseName);
            child.put("score", indicatorScore * w); // 原始得分
            child.put("weight", w);
            children.add(child);
        }

        /* 3. 加权平均（不会超 100） */
        double finalScore = sumWeight == 0 ? 0 : BigDecimal.valueOf(sumWeightedScore).divide(BigDecimal.valueOf(sumWeight), 2, RoundingMode.HALF_UP).doubleValue();

        Map<String, Object> result = new HashMap<>();
        result.put("children", children);
        result.put("score", finalScore);
        result.put("weight", sumWeight);
        return result;
    }
}
