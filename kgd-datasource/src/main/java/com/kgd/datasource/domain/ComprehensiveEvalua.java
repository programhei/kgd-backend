package com.kgd.datasource.domain;


import java.util.List;
import java.util.Map;

public class ComprehensiveEvalua {
    //体系功能
    /**
     * {
     *    dataKind:体系功能,
     *    weight: 0.5,//权重
     *    score: 0.5,//得分
     * }
     * */
    private Map<String,Object> systemFunction;
    //性能准确度
    /**
     * {
     *    dataKind:性能准确度,
     *    weight: 0.5,//权重
     *    score: 0.5,//得分
     * }
     * */
    private Map<String,Object> performanceAccuracy;
    //想定合理性
    /**
     * {
     *    dataKind:想定合理性,
     *    weight: 0.5,//权重
     *    score: 0.5,//得分
     * }
     * */
    private Map<String,Object> assumedRationality;
    //软件质量
    /**
     * {
     *    dataKind:软件质量,
     *    weight: 0.5,//权重
     *    score: 0.5,//得分
     * }
     * */
    private Map<String,Object> softwareQuality ;
    //支持只能决策模型生成与评估
    /**
     * {
     *    dataKind:支持只能决策模型生成与评估,
     *    weight: 0.5,//权重
     *    score: 0.5,//得分
     * }
     * */
    private Map<String,Object> evaluation;
    //总体评估
    /**
     * {
     *    dataKind:总体评估,
     *    weight: 0.5,//权重
     *    score: 0.5,//得分
     * }
     * */
    private Map<String,Object> comprehensiveEvaluation;
    private List<Map<String, Object> > dataList;

    public List<Map<String, Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
    }


    public Map<String, Object> getSystemFunction() {
        return systemFunction;
    }

    public void setSystemFunction(Map<String, Object> systemFunction) {
        this.systemFunction = systemFunction;
    }

    public Map<String, Object> getPerformanceAccuracy() {
        return performanceAccuracy;
    }

    public void setPerformanceAccuracy(Map<String, Object> performanceAccuracy) {
        this.performanceAccuracy = performanceAccuracy;
    }

    public Map<String, Object> getAssumedRationality() {
        return assumedRationality;
    }

    public void setAssumedRationality(Map<String, Object> assumedRationality) {
        this.assumedRationality = assumedRationality;
    }

    public Map<String, Object> getSoftwareQuality() {
        return softwareQuality;
    }

    public void setSoftwareQuality(Map<String, Object> softwareQuality) {
        this.softwareQuality = softwareQuality;
    }

    public Map<String, Object> getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Map<String, Object> evaluation) {
        this.evaluation = evaluation;
    }

    public Map<String, Object> getComprehensiveEvaluation() {
        return comprehensiveEvaluation;
    }

    public void setComprehensiveEvaluation(Map<String, Object> comprehensiveEvaluation) {
        this.comprehensiveEvaluation = comprehensiveEvaluation;
    }
}
