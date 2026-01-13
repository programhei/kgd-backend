package com.kgd.evaluate.service;


import com.kgd.evaluate.domain.EvaluateScore;
import com.kgd.evaluate.domain.IntelligentDecision;
import com.kgd.evaluate.domain.IntelligentDecisionEvaluate;
import com.kgd.evaluate.domain.vo.CommonVo;

import java.util.List;
import java.util.Map;

public interface IEvaluateCommonService {

    /**
     * 导入体系功能
     *
     * @param commonVo 请求参数
     */
    public void importSystemFunctions(CommonVo commonVo) throws Exception;

    /**
     * 查询体系功能
     *
     * @param commonVo 参数
     * @return list
     */
//    public List<Map<String, Object>> listSystemFunctions(CommonVo commonVo);
    public Map<String, Map<String, List<IntelligentDecision>>> listSystemFunctions(CommonVo commonVo);

    /**
     * 删除体系功能
     */
    public void deleteSystemFunctions(String dataId);

    /**
     * 评估体系功能
     *
     * @param commonVo 评估参数
     * @return 评分结果
     */
    public Map<String, Object> evaluateSystemFunctions(CommonVo commonVo);


    /**
     * 导入飞行性能参数
     *
     * @param commonVo 请求参数
     */
    public void importFlightPerformanceAccuracy(CommonVo commonVo) throws Exception;

    /**
     * 导入雷达性能参数
     *
     * @param commonVo 请求参数
     */
    public void importRadarPerformanceAccuracy(CommonVo commonVo) throws Exception;

    /**
     * 导入导弹性能参数
     *
     * @param commonVo 请求参数
     */
    public void importMissilePerformanceAccuracy(CommonVo commonVo) throws Exception;

    /**
     * 查询飞行性能参数
     *
     * @param commonVo 参数
     * @return list
     */
    public List<Map<String, Object>> listFlightPerformanceAccuracy(CommonVo commonVo);

    /**
     * 查询飞行性能参数
     *
     * @param commonVo 参数
     * @return list
     */
    public List<Map<String, Object>> listRadarPerformanceAccuracy(CommonVo commonVo);

    /**
     * 查询飞行性能参数
     *
     * @param commonVo 参数
     * @return list
     */
    public List<Map<String, Object>> listMissilePerformanceAccuracy(CommonVo commonVo);

    /**
     * 删除性能参数
     */
    public void deletePerformanceAccuracy(String dataId);

    /**
     * 评估性能参数
     *
     * @param commonVo 评估数据
     * @return 评估分数结果
     */
    public Map<String, Object> evaluateFlightPerformanceAccuracy(CommonVo commonVo) throws Exception;

    /**
     * 评估性能参数
     *
     * @param commonVo 评估数据
     * @return 评估分数结果
     */
    public Map<String, Object> evaluateRadarPerformanceAccuracy(CommonVo commonVo) throws Exception;

    /**
     * 评估性能参数
     *
     * @param commonVo 评估数据
     * @return 评估分数结果
     */
    public Map<String, Object> evaluateMissilePerformanceAccuracy(CommonVo commonVo) throws Exception;

    /**
     * 导入想定合理性
     *
     * @param commonVo 请求参数
     */
    public void importScenarioRationality(CommonVo commonVo) throws Exception;

    /**
     * 查询想定合理性
     *
     * @param commonVo 参数
     * @return list
     */
    public List<Map<String, Object>> listScenarioRationality(CommonVo commonVo);

    /**
     * 删除想定合理性
     */
    public void deleteScenarioRationality(String dataId);

    /**
     * 评估想定合理性
     *
     * @param commonVo 评估数据
     * @return 评估分数结果
     */
    public Map<String, Object> evaluateScenarioRationality(CommonVo commonVo) throws Exception;

    /**
     * 导入软件质量
     *
     * @param commonVo 参数
     */
    public void importSoftwareQuality(CommonVo commonVo) throws Exception;

    /**
     * 查询软件质量
     *
     * @return list
     */
    public List<Map<String, Object>> listSoftwareQuality();

    /**
     * 删除软件质量
     */
    public void deleteSoftwareQuality(String dataId);

    /**
     * @param commonVo 评估数据
     * @return 评估分数结果
     */
    public Map<String, Object> evaluateSoftwareQuality(CommonVo commonVo);

    /**
     * 导入支持智能决策模型生成与评估
     *
     * @param commonVo 参数
     */
    public void importIntelligentDecision(CommonVo commonVo) throws Exception;

    /**
     * 查询支持智能决策模型生成与评估
     *
     * @return list
     */
    public List<Map<String, Object>> listIntelligentDecision();


    public List<IntelligentDecisionEvaluate> listIntelligentDecisions();
    /**
     * 删除支持智能决策模型生成与评估
     */
    public void deleteIntelligentDecision(String dataId);

    /**
     * 评估支持智能决策模型生成与评估
     *
     * @param list 集合
     * @return 评分结果
     */
    public Map<String, Object> evaluateIntelligentDecision(List<IntelligentDecision> list);

    public void importIntelligentDecisionEvaluate(CommonVo commonVo) throws Exception;

    /**
     * 查询评估分数记录列表
     *
     * @return 评估分数记录集合
     */
    public Map<String, EvaluateScore> listEvaluateScore() throws Exception;

    /**
     * 总体评估
     */
    public Map<String, Object> overallEvaluation() throws Exception;

    Map<String, Object> evaluateIntelligentDecisionEvaluate(CommonVo commonVo);

    /**
     * 计算想定合理性分数
     *
     * @param commonVo 参数
     * @return 想定合理性分数
     */
    Map<String,Object> calculateTheRationalityScoreOfTheScenario(CommonVo commonVo) throws Exception;

    /**
     * 计算软件质量分数
     *
     * @param commonVo 参数
     * @return 软件质量分数
     */
    Map<String,Object> calculateSoftwareQuality(CommonVo commonVo) throws Exception;
}
