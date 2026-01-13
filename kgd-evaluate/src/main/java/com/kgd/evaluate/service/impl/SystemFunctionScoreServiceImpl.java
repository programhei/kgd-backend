package com.kgd.evaluate.service.impl;

import com.kgd.common.enums.WeightType;
import com.kgd.common.utils.DateUtils;
import com.kgd.common.utils.SecurityUtils;
import com.kgd.evaluate.domain.EvaluateTreeScore;
import com.kgd.evaluate.domain.IntelligentDecision;
import com.kgd.evaluate.domain.WeightMangeData;
import com.kgd.evaluate.mapper.WeightMangeMapper;
import com.kgd.evaluate.service.IIntelligentDecisionService;
import com.kgd.evaluate.service.ISystemFunctionScoreService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class SystemFunctionScoreServiceImpl implements ISystemFunctionScoreService {

    @Resource
    private IIntelligentDecisionService intelligentDecisionService;

    @Autowired
    private WeightMangeMapper weightMangeMapper;

    @Override
    public Map<String, Object> getSystemFunctionFirstScoreServic() {
        Map<String, Object> result = new ConcurrentHashMap<>();
        StringJoiner reason = new StringJoiner("；");
        //查询权值
        HashMap<String,Double> weightMap = getWeightMap();
        if (weightMap == null) {
            reason.add("权值数据丢失，无法评分");
            result.put("score", 0);
            result.put("reason", reason.toString());
            return result;
        }

        Map<String, Object> flight = getFlightSimulationSecondScoreServic1("飞行仿真");
        Map<String, Object> sim= getFlightSimulationSecondScoreServic1("武器系统");
//        Map<String, Object> flight = getFlightSimulationSecondScoreServic("武器系统");
//        Map<String, Object> sim = getFlightSimulationSecondScoreServic("飞行仿真");
        Double flighScore = weightMap.get("飞行功能") == null ? 0.0 : weightMap.get("飞行功能");
        Double simScore = weightMap.get("武器系统") == null ? 0.0 : weightMap.get("武器系统");
        double score = 0.0;
        Double flightScore = MapUtils.getDouble(flight, "score", 0.0);
        Double siScore = MapUtils.getDouble(sim, "score", 0.0);
        score = flighScore * flightScore + simScore * siScore;
//        score =flighScore*(Double)flight.get("score")+simScore*(Double)sim.get("score");
        //        dataKind:支持只能决策模型生成与评估,//维度
        //         weight: 0.5,//权重
        //         score: 0.5,//得分
        result.put("dataKind","体系功能");
        result.put("weight",weightMap.get("体系功能"));
        result.put("score",score);
        return result;
    }

    public HashMap<String,Double> getWeightMap(){
        WeightMangeData weightMangeData1 = new WeightMangeData();
        weightMangeData1.setGroupId(1L);
        weightMangeData1.setWeight(0.0);
        List<WeightMangeData> weightMangeDatasList = weightMangeMapper.selectWeightTree(weightMangeData1);

        if(CollectionUtils.isEmpty(weightMangeDatasList)){
            return null;
        }
        HashMap<String,Double> weightMap = new HashMap<>();
        for (WeightMangeData weightMangeData:weightMangeDatasList) {
            weightMap.put(weightMangeData.getName(),weightMangeData.getWeight());
        }
        return weightMap;
    }
    @Override
    public Map<String, Object> getFlightSimulationSecondScoreServic(String dataName){
        Map<String, Object> result = new ConcurrentHashMap<>();
        StringJoiner reason = new StringJoiner("；");

        /* ---------- 1. 入口空校验 ---------- */

        //        // 获取导入的数据
//        dataName = "飞行仿真";
        IntelligentDecision intelligentDecision = new IntelligentDecision();
        intelligentDecision.setDimension(dataName);
        List<IntelligentDecision> selectIntelligentDecisionList = intelligentDecisionService.selectIntelligentDecisionList(intelligentDecision);
        Map<String, Map<String, List<IntelligentDecision>>> listSystemFunctions = selectIntelligentDecisionList.stream()
                .collect(Collectors.groupingBy(
                        IntelligentDecision::getDimension,
                        Collectors.groupingBy(IntelligentDecision::getDataType)
                ));
        Map<String, List<IntelligentDecision>> decisionMap = listSystemFunctions.get(dataName);
        if (CollectionUtils.isEmpty(selectIntelligentDecisionList) || listSystemFunctions.get(dataName) == null) {
            result.put("score", 0);
            result.put("reason", reason.toString());
            return result;
        }
        int missingCount = 0;
        List<Map<String, List<String>>> mapList = new ArrayList<>();
        double listSize = 0  ;
        double missListSize = 0  ;

        /* ---------- 2. 查找权值 ---------- */
        WeightMangeData weightMangeData1 = new WeightMangeData();
        weightMangeData1.setGroupId(1L);
        weightMangeData1.setWeight(0.0);
        List<WeightMangeData> weightMangeDatasList = weightMangeMapper.selectWeightTree(weightMangeData1);
        if (CollectionUtils.isEmpty(weightMangeDatasList)) {
            reason.add("权值数据丢失，无法评分");
            result.put("score", 0);
            result.put("reason", reason.toString());
            return result;
        }
        HashMap<String,Double> weightMap = new HashMap<>();
        for (WeightMangeData weightMangeData:weightMangeDatasList) {
            weightMap.put(weightMangeData.getName(),weightMangeData.getWeight());
        }
        /* ---------- 3. 逐维度统计缺失 ---------- */
        List<Double> scoreTempList= new ArrayList<>();
        for (Map.Entry<String, List<IntelligentDecision>> entry : decisionMap.entrySet()) {
            Map<String, List<String>> missingMap = new HashMap<>();
            String dim = entry.getKey();
            List<IntelligentDecision> decisions = entry.getValue();

            if (CollectionUtils.isEmpty(decisions)) {
                reason.add("暂无" + dim + "数据，暂时不计入评分");
                continue;
            }

            List<String> missingList = decisions.stream()
                    .filter(d -> "是".equals(d.getIsMissing()))   // 避免 NPE
                    .map(IntelligentDecision::getDataContent)
                    .collect(Collectors.toList());

            if (!missingList.isEmpty()) {
                missingCount += missingList.size();
                missingMap.put(dim, missingList);
                mapList.add(missingMap);
            }
            WeightMangeData weightMangeData = new WeightMangeData();
            weightMangeData.setUpdateTime(DateUtils.getNowDate());
            weightMangeData.setUpdateBy(SecurityUtils.getUsername());
            // 防止除以零，如果 decisions.size() 为 0，设置默认分数为 0
            double scoreTemp = 0.0;
            if (decisions.size() > 0) {

//                scoreTemp = ((decisions.size() - missingList.size()) / (double) decisions.size()) * 100;
                if(weightMap.get(entry.getKey())!=null){
                    scoreTemp = 100 - missingCount * 5;
                    scoreTemp = scoreTemp * weightMap.get(entry.getKey());
                    // 确保分数是有效数字（不是 NaN 或 Infinity）
                    if (Double.isNaN(scoreTemp) || Double.isInfinite(scoreTemp)) {
                        scoreTemp = 0.0;
                    }
                    scoreTempList.add(scoreTemp);
                }

            }
            weightMangeData.setScore(scoreTemp);
            if(entry.getKey().equals("飞控模型")) {
                listSize +=decisions.size();
                missListSize +=missingList.size();
            }
            if(entry.getKey().equals("运动学模型")) {
                listSize +=decisions.size();
                missListSize +=missingList.size();
            }
            if(entry.getKey().equals("运动学模型、气动参数模型")) {
                weightMangeData.setId(3L);
                weightMangeMapper.updateWeightScore(weightMangeData);
            }
            if(entry.getKey().equals("发动机模型")) {
                weightMangeData.setId(5L);
                weightMangeMapper.updateWeightScore(weightMangeData);
            }
        }


        WeightMangeData weightMangeData = new WeightMangeData();
        weightMangeData.setUpdateTime(DateUtils.getNowDate());
        weightMangeData.setUpdateBy(SecurityUtils.getUsername());
        // 防止除以零，如果 listSize 为 0，设置默认分数为 0
        double scoreTemp = 0.0;
        if (scoreTempList.size() > 0) {
            for (Double score: scoreTempList) {
                scoreTemp += score;
            }
//            scoreTemp = ((listSize - missListSize) / listSize) * 100;
            // 确保分数是有效数字（不是 NaN 或 Infinity）
            if (Double.isNaN(scoreTemp) || Double.isInfinite(scoreTemp)) {
                scoreTemp = 0.0;
            }
        }
        weightMangeData.setScore(scoreTemp);
        weightMangeData.setId(4L);
        weightMangeMapper.updateWeightScore(weightMangeData);

        /* ---------- 3. 分数兜底 ---------- */
        int score = Math.max(0, 100 - missingCount * 5);

        result.put("missingInterface", mapList);
        result.put("reason", reason.toString());
        result.put("score", scoreTemp);
        return result;
    }

    @Override
    public Map<String, Object> getFlightSimulationSecondScoreServic1(String dataName) {
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
        IntelligentDecision intelligentDecision = new IntelligentDecision();
        intelligentDecision.setDimension(dataName);
        List<IntelligentDecision> selectIntelligentDecisionList = intelligentDecisionService.selectIntelligentDecisionList(intelligentDecision);
        Map<String, Map<String, List<IntelligentDecision>>> listSystemFunctions = selectIntelligentDecisionList.stream()
                .collect(Collectors.groupingBy(
                        IntelligentDecision::getDimension,
                        Collectors.groupingBy(IntelligentDecision::getDataType)
                ));
        Map<String, List<IntelligentDecision>> decisionMap = listSystemFunctions.get(dataName);
        if (CollectionUtils.isEmpty(selectIntelligentDecisionList) || listSystemFunctions.get(dataName) == null) {
            reason.add("暂无体系功能接口数据，无法评分");
            result.put("score", 0);
            result.put("reason", reason.toString());
            return result;
        }

        List<Map<String, List<String>>> mapList = new ArrayList<>();
        double listSize = 0  ;
        double missListSize = 0  ;

        /* ---------- 2. 查找权值 ---------- */
        WeightMangeData weightMangeData1 = new WeightMangeData();
        weightMangeData1.setGroupId(1L);
        List<WeightMangeData> weightMangeDatasList = weightMangeMapper.selectWeightTree(weightMangeData1);
        if (CollectionUtils.isEmpty(weightMangeDatasList)) {
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
        result.put("evaluateTreeScore", evaluateTreeScore);
        result.put("reason", reason.toString());
        result.put("score", evaluateTreeScore.getScore());
        return result;
    }
}
