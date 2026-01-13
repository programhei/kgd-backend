package com.kgd.evaluate.service.impl;


import com.kgd.evaluate.domain.EvaluateTreeScore;
import com.kgd.evaluate.domain.IntelligentDecisionEvaluate;
import com.kgd.evaluate.domain.WeightMangeData;
import com.kgd.evaluate.mapper.WeightMangeMapper;
import com.kgd.evaluate.service.IEvaluateIntelligentDecisionService;
import com.kgd.evaluate.service.IIntelligentDecisionEvaluateService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class EvaluateIntelligentDecisionServiceImpl implements IEvaluateIntelligentDecisionService {

    @Resource
    private IIntelligentDecisionEvaluateService intelligentDecisionEvaluateService;

    @Autowired
    private WeightMangeMapper weightMangeMapper;

    @Override
    public Map<String, Object> getEvaluateIntelligentDecisionService(String flag) {
        /* ==================== 1. 校验 ==================== */
        Map<String, Object> result = new ConcurrentHashMap<>();
        StringBuilder reason = new StringBuilder();
        reason.append("无扣分").append("；");
        List<IntelligentDecisionEvaluate> intelligentDecisionEvaluates = intelligentDecisionEvaluateService.selectIntelligentDecisionEvaluateList(new IntelligentDecisionEvaluate());
        if (intelligentDecisionEvaluates.isEmpty()) {
            reason.append("暂无支持智能决策模型生成与评估的数据，无法评分").append("；");
            result.put("score", 0);
            result.put("reason", reason);
            return result;
        }

        //获取得分权值
        WeightMangeData weightMangeData1 = new WeightMangeData();
        weightMangeData1.setGroupId(1L);
        List<WeightMangeData> weightMangeDatasList = weightMangeMapper.selectWeightTree(weightMangeData1);
        if (CollectionUtils.isEmpty(weightMangeDatasList)) {
            reason.append("权值数据丢失，无法评分");
            result.put("score", 0);
            result.put("reason", reason.toString());
            return result;
        }

        //创建得分树
        EvaluateTreeScore evaluateTreeScore = new EvaluateTreeScore();
        List<EvaluateTreeScore> evaluateFirstTreeScoreList = new ArrayList<>();
        evaluateTreeScore.setScoreType("支持只能决策模型生成与评估");
        List<WeightMangeData>  list = weightMangeDatasList.stream().filter(weightMangeData -> "支持只能决策模型生成与评估".equals(weightMangeData.getName())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(list)){
            reason.append("权值数据丢失，无法评分");
            result.put("score", 0);
            result.put("reason", reason.toString());
            return result;
        }
        evaluateTreeScore.setScoreType(list.get(0).getName());
        evaluateTreeScore.setWeight(list.get(0).getWeight());
        evaluateTreeScore.setId(list.get(0).getId());
        //寻找下级子层
        List<WeightMangeData>  firstList = weightMangeDatasList.stream().filter(weightMangeData -> evaluateTreeScore.getId().equals(weightMangeData.getParentId())).collect(Collectors.toList());

        if (firstList != null && firstList.size() > 0){

            for (WeightMangeData weightMangeData:firstList) {
                EvaluateTreeScore evaluateFirstTreeScore = new EvaluateTreeScore();
                evaluateFirstTreeScore.setScoreType(weightMangeData.getName());
                evaluateFirstTreeScore.setWeight(weightMangeData.getWeight());
                evaluateFirstTreeScore.setId(weightMangeData.getId());
                evaluateFirstTreeScore.setParentId(weightMangeData.getParentId());
                evaluateFirstTreeScoreList.add(evaluateFirstTreeScore);

                List<EvaluateTreeScore> evaluateSecondTreeScoreList = new ArrayList<>();
                //寻找三级子层
                List<WeightMangeData>  secondList = weightMangeDatasList.stream().filter(weightMange-> weightMangeData.getId().equals(weightMange.getParentId())).collect(Collectors.toList());

                if (secondList != null && secondList.size() > 0){
                    for (WeightMangeData weightMangeData2:secondList) {
                        EvaluateTreeScore evaluateSecondTreeScore = new EvaluateTreeScore();
                        evaluateSecondTreeScore.setScoreType(weightMangeData2.getName());
                        evaluateSecondTreeScore.setWeight(weightMangeData2.getWeight());
                        evaluateSecondTreeScore.setId(weightMangeData2.getId());
                        evaluateSecondTreeScore.setParentId(weightMangeData2.getParentId());
                        if("地形创建".equals(weightMangeData2.getName())){
                            double v = Double.parseDouble(intelligentDecisionEvaluates.get(0).getCreateTerrain() == null ? "0" : intelligentDecisionEvaluates.get(0).getCreateTerrain()) * 100;
                            v = new BigDecimal(v).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            evaluateSecondTreeScore.setScore(v);
                        }
                        else if("视角设计".equals(weightMangeData2.getName())){
                            double v = Double.parseDouble(intelligentDecisionEvaluates.get(0).getPerspectiveDesign() == null ? "0" : intelligentDecisionEvaluates.get(0).getPerspectiveDesign()) * 100;
                            v = new BigDecimal(v).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            evaluateSecondTreeScore.setScore(v);
                        }
                        else if("飞行轨迹视景显示".equals(weightMangeData2.getName())){
                            double v = Double.parseDouble(intelligentDecisionEvaluates.get(0).getFlightTrajectoryVisualization() == null ? "0" : intelligentDecisionEvaluates.get(0).getFlightTrajectoryVisualization()) * 100;
                            v = new BigDecimal(v).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            evaluateSecondTreeScore.setScore(v);
                        }
                        else if("飞行姿态显示".equals(weightMangeData2.getName())){
                            double v = Double.parseDouble(intelligentDecisionEvaluates.get(0).getFlightAttitude() == null ? "0" : intelligentDecisionEvaluates.get(0).getFlightAttitude()) * 100;
                            v = new BigDecimal(v).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            evaluateSecondTreeScore.setScore(v);
                        }
                        else if("仿真对抗胜负评估".equals(weightMangeData2.getName())){
                            double v = Double.parseDouble(intelligentDecisionEvaluates.get(0).getWinLossEvaluation() == null?"0":intelligentDecisionEvaluates.get(0).getWinLossEvaluation())*100;
                            v = new BigDecimal(v).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            evaluateSecondTreeScore.setScore(v);
                        }
                        else if("仿真对抗效能评估".equals(weightMangeData2.getName())){
                            double v = Double.parseDouble(intelligentDecisionEvaluates.get(0).getEffectivenessEvaluation() == null?"0":intelligentDecisionEvaluates.get(0).getEffectivenessEvaluation())*100;
                            v = new BigDecimal(v).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            evaluateSecondTreeScore.setScore(v);
                        }
                        else{
                            //进4级维度评分
                            //寻找三级子层
                            List<EvaluateTreeScore> evaluateForthTreeScoreList = new ArrayList<>();
                            List<WeightMangeData>  secondForthList = weightMangeDatasList.stream().filter(weightMange-> weightMangeData2.getId().equals(weightMange.getParentId())).collect(Collectors.toList());

                            if (secondForthList != null && secondForthList.size() > 0){

                                for (WeightMangeData weightMangeData3:secondForthList) {
                                    EvaluateTreeScore evaluateThirdForthScore = new EvaluateTreeScore();
                                    evaluateThirdForthScore.setScoreType(weightMangeData3.getName());
                                    evaluateThirdForthScore.setWeight(weightMangeData3.getWeight());
                                    evaluateThirdForthScore.setId(weightMangeData3.getId());
                                    evaluateThirdForthScore.setParentId(weightMangeData3.getParentId());
                                    evaluateThirdForthScore.setScore(100);
                                    evaluateForthTreeScoreList.add(evaluateThirdForthScore);
                                }
                                //获取4级子集总得分
                                double scoreTemp = 0.0;
                                if (evaluateForthTreeScoreList !=null && !evaluateForthTreeScoreList.isEmpty()) {
                                    for (EvaluateTreeScore evaluateScore: evaluateForthTreeScoreList) {
                                        scoreTemp += evaluateScore.getScore() * evaluateScore.getWeight();
                                    }
                                    // 确保分数是有效数字（不是 NaN 或 Infinity）
                                    if (Double.isNaN(scoreTemp) || Double.isInfinite(scoreTemp)) {
                                        scoreTemp = 0.0;
                                    }
                                }
                                if(scoreTemp == 0.0){
                                    evaluateSecondTreeScore.setReason(evaluateSecondTreeScore.getScoreType()+"数据缺失，暂时没有计入评分；");
                                }else {
                                    evaluateSecondTreeScore.setReason("无扣分");
                                }
                                scoreTemp = new BigDecimal(scoreTemp).setScale(2, RoundingMode.HALF_UP).doubleValue();
                                evaluateSecondTreeScore.setScore(scoreTemp);
                                evaluateSecondTreeScore.setEvaluateTreeScoresList(evaluateForthTreeScoreList);
                            }
                        }
                        if(evaluateSecondTreeScore.getScore() == 0.0){
                            evaluateSecondTreeScore.setReason(evaluateSecondTreeScore.getScoreType()+"数据缺失，暂时没有计入评分；");
                        }else {
                            evaluateSecondTreeScore.setReason("无扣分");
                        }
                        evaluateSecondTreeScoreList.add(evaluateSecondTreeScore);
                    }
                }
                //获取子集总得分
                double scoreTemp = 0.0;
                if (evaluateSecondTreeScoreList !=null && !evaluateSecondTreeScoreList.isEmpty()) {
                    for (EvaluateTreeScore evaluateScore: evaluateSecondTreeScoreList) {
                        scoreTemp += evaluateScore.getScore() * evaluateScore.getWeight();
                    }
                    // 确保分数是有效数字（不是 NaN 或 Infinity）
                    if (Double.isNaN(scoreTemp) || Double.isInfinite(scoreTemp)) {
                        scoreTemp = 0.0;
                    }
                }
                if(scoreTemp == 0.0){
                    evaluateFirstTreeScore.setReason(evaluateFirstTreeScore.getScoreType()+"数据缺失，暂时没有计入评分；");
                }else {
                    evaluateFirstTreeScore.setReason("无扣分");
                }
                scoreTemp = new BigDecimal(scoreTemp).setScale(2, RoundingMode.HALF_UP).doubleValue();
                evaluateFirstTreeScore.setScore(scoreTemp);
                evaluateFirstTreeScore.setEvaluateTreeScoresList(evaluateSecondTreeScoreList);
            }
        }
        double scoreTemp = 0.0;
        if (evaluateFirstTreeScoreList !=null && !evaluateFirstTreeScoreList.isEmpty()) {
            for (EvaluateTreeScore evaluateScore: evaluateFirstTreeScoreList) {
                scoreTemp += evaluateScore.getScore() * evaluateScore.getWeight();
            }
            // 确保分数是有效数字（不是 NaN 或 Infinity）
            if (Double.isNaN(scoreTemp) || Double.isInfinite(scoreTemp)) {
                scoreTemp = 0.0;
            }
        }
        if(scoreTemp == 0.0){
            evaluateTreeScore.setReason(evaluateTreeScore.getScoreType()+"数据缺失，暂时没有计入评分；");
        }else {
            evaluateTreeScore.setReason("无扣分");
        }
        scoreTemp = new BigDecimal(scoreTemp).setScale(2, RoundingMode.HALF_UP).doubleValue();
        evaluateTreeScore.setScore(scoreTemp);
        evaluateTreeScore.setEvaluateTreeScoresList(evaluateFirstTreeScoreList);
        if(flag !=null && flag.equals("1")){

            result.put("evaluateTreeScore", evaluateTreeScore);
            result.put("reason", reason.toString());
            result.put("score", evaluateTreeScore.getScore());
            return result;
        }

//     *    dataKind:体系功能,
//     *    weight: 0.5,//权重
//     *    score: 0.5,//得分
//     * }
        result.put("dataKind", evaluateTreeScore.getScoreType());
        result.put("score", evaluateTreeScore.getScore());
        result.put("weight", evaluateTreeScore.getWeight());
        return result;
    }

    private <T> Map<String, Object> getIntelligentDecisionScore(Map<String, Object> compare,
                                                                Double[] weights,Map<String,Double> weightMap)  {

        /* 指标 → 权重 key */
        List<Map<String, String>> keys = Arrays.asList(
                Collections.singletonMap("createTerrain", "地形创建"),
                Collections.singletonMap("perspectiveDesign", "视角设计"),
                Collections.singletonMap("flightTrajectoryVisualization", "飞行轨迹视景显示"),
                Collections.singletonMap("flightAttitude", "飞行姿态显示"),
                Collections.singletonMap("winLossEvaluation", "仿真对抗胜负评估"),
                Collections.singletonMap("effectivenessEvaluation", "仿真对抗效能评估")
        );

        return calcIntelligentDecisionScore(compare, weights, keys, weightMap);
    }

    private Map<String, Object> calcIntelligentDecisionScore(
            Map<String, Object> row,
            Double[] weightGrouped,
            List<Map<String, String>> keys, Map<String,Double> weightMap) {
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
}
