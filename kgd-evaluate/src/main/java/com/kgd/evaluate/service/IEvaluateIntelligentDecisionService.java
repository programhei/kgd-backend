package com.kgd.evaluate.service;

import java.util.Map;

public interface IEvaluateIntelligentDecisionService {

    //获取支持智能决策模型生成与评估的一级得分
    public Map<String, Object> getEvaluateIntelligentDecisionService(String flag);
}
