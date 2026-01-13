package com.kgd.evaluate.domain.vo;


import com.kgd.evaluate.domain.*;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * 请求参数
 *
 * @author kgd
 * @date 2025-10-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonVo {

    /**
     * 文件地址
     */
    private String filePath;


    /**
     * 数据类型
     */
    private String extra;

    /**
     * 评估一级维度
     */
    private String variable;

    /**
     * 是否是标准数据
     */
    private String isStandard;


    /**
     * 对比数据
     */
    List<Map<String, Object>> compareList;

    /**
     * 性能数据
     */
    List<Map<String, Object>> xnList;

    /**
     * 权值数据
     */
    List<Map<String, Object>> weightList;


    /**
     * 接口数据
     */
    List<Map<String, List<IntelligentDecision>>> interfaceList;

    private String missingInterface;   // 缺失字段名
    private List<String> missingList;  // 该维度下全部缺失字段
    private String dimension;
    private String dataType;
    private List<FlightData> flightDataList;
    private List<FlightModel> flightModelList;
    private List<RadarData> radarDataList;
    private List<RadarModel> radarModelList;
    private List<MissileData> missileDataList;
    private List<MissileModel> missileModelList;
    // 仅 datasource1 用到
    private List<CommonVo> children;

    List<IntelligentDecision>  intelligentDecisionList;


    private String isPerformance;

    /**
     * 数据ID
     */
    private String dataId;
}
