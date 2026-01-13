package com.kgd.evaluate.service;

import com.kgd.evaluate.domain.WeightMangeData;
import com.kgd.evaluate.domain.vo.CommonVo;

import java.util.Map;

public interface ISystemFunctionScoreService {
    //获取体系功能的一级得分
    public Map<String, Object> getSystemFunctionFirstScoreServic();
    //获取体系功能二级得分
    public Map<String, Object> getFlightSimulationSecondScoreServic(String dataName);

    public Map<String, Object> getFlightSimulationSecondScoreServic1(String dataName);
}
