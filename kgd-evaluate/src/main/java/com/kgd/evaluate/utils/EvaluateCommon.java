package com.kgd.evaluate.utils;


import com.kgd.evaluate.domain.FlightData;
import com.kgd.evaluate.domain.MissileData;
import com.kgd.evaluate.domain.RadarData;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class EvaluateCommon {

    /**
     * 将按日期分组后的数据转换成前端所需的 Map 列表。
     * 每个 Map 包含以下键值：
     * - id：当前时间戳（毫秒），作为行唯一标识
     * - dataId：分组日期（Date 类型）
     * - dataList：该日期下的数据列表
     * 线程安全：返回的 Map 使用 ConcurrentHashMap 实现。
     *
     * @param grouped 分组结果：key=日期，value=该日期下的数据列表
     * @return 适配前端表格/节点的 List<Map> 结构
     */
    public List<Map<String, Object>> getMaps(Map<Date, List<Object>> grouped) {
        return grouped.entrySet().stream().map(e -> {
            Map<String, Object> m = new ConcurrentHashMap<>();
            m.put("id", System.currentTimeMillis());
            m.put("dataId", e.getKey());
            m.put("dataList", e.getValue());
            return m;
        }).collect(Collectors.toList());
    }

    /**
     * 将按日期分组后的数据转换成前端所需的 Map 列表。
     * 每个 Map 包含以下键值：
     * - id：当前时间戳（毫秒），作为行唯一标识
     * - dataId：分组日期（Date 类型）
     * - dataList：该日期下的数据列表
     * 线程安全：返回的 Map 使用 ConcurrentHashMap 实现。
     *
     * @param grouped 分组结果：key=日期，value=该日期下的数据列表
     * @return 适配前端表格/节点的 List<Map> 结构
     */
    public List<Map<String, Object>> getMapsByDataType(Map<String, List<Object>> grouped) {
        return grouped.entrySet().stream().map(e -> {
            Map<String, Object> m = new ConcurrentHashMap<>();
            m.put("id", System.currentTimeMillis());
            m.put("dataId", e.getKey());
            m.put("dataList", e.getValue());
            return m;
        }).collect(Collectors.toList());
    }

    /**
     * 逐记录计算相对误差得分：(1 - |对比-标准|/标准) * 100
     * 再对各字段求平均
     *
     * @param std 标准数据
     * @param cmp 比对数据（Map 形式）
     * @return 各字段平均得分
     */
    public Map<String, Double> calcFlightData(List<FlightData> std, List<Map<String, Object>> cmp) {
        if (std == null || cmp == null || std.size() != cmp.size() || std.isEmpty())
            throw new IllegalArgumentException("两组数据必须等长且>0");
        int n = std.size();

        double sumSp = 0, sumLo = 0, sumLa = 0, sumAl = 0, sumLd = 0;

        for (int i = 0; i < n; i++) {
            FlightData s = std.get(i);
            Map<String, Object> c = cmp.get(i);
            if (ObjectUtils.isNotEmpty(c.get("flightSpeed"))) {
                sumSp += singleScore(s.getFlightSpeed(), c.get("flightSpeed").toString());
            }
            if (ObjectUtils.isNotEmpty(c.get("longitude"))) {
                sumLo += singleScore(s.getLongitude(), c.get("longitude").toString());
            }
            if (ObjectUtils.isNotEmpty(c.get("latitude"))) {
                sumLa += singleScore(s.getLatitude(), c.get("latitude").toString());
            }
            if (ObjectUtils.isNotEmpty(c.get("height"))) {
                sumAl += singleScore(s.getHeight(), c.get("height").toString());
            }
            if (ObjectUtils.isNotEmpty(c.get("overload"))) {
                sumLd += singleScore(s.getOverload(), c.get("overload").toString());
            }

        }

        Map<String, Double> res = new LinkedHashMap<>();
        res.put("速度", sumSp / n);
        res.put("经度", sumLo / n);
        res.put("纬度", sumLa / n);
        res.put("高度", sumAl / n);
        res.put("过载", sumLd / n);
        return res;
    }

    /**
     * 雷达数据标准差业务实现
     * 公式：S = √[ Σ(yi-xi)² / (n) ]   （第一张图）
     * 示例：标准数据 [x1,x2,x3]  比对数据 [y1,y2,y3]
     *
     * @param std 标准数据
     * @param cmp 比对数据
     * @return 标准差
     */
    public Map<String, Double> calcRadarData(List<RadarData> std, List<Map<String, Object>> cmp) {
        if (std == null || cmp == null || std.size() != cmp.size() || std.isEmpty())
            throw new IllegalArgumentException("两组数据必须等长且>0");
        int n = std.size();
        double sumDistance = 0, sumTime = 0;

        for (int i = 0; i < n; i++) {
            // 标准数据
            RadarData s = std.get(i);
            // 比对数据
            Map<String, Object> c = cmp.get(i);
            if (ObjectUtils.isNotEmpty(c.get("discoveryDistance"))) {
                sumDistance += singleScore(s.getDiscoveryDistance(), c.get("discoveryDistance").toString());
            }
            if (ObjectUtils.isNotEmpty(c.get("trackTime"))) {
                sumTime += singleScore(s.getTrackTime(), c.get("trackTime").toString());
            }
        }

        Map<String, Double> res = new LinkedHashMap<>();
        res.put("发现距离", Math.sqrt(sumDistance / n));
        res.put("时间", Math.sqrt(sumTime / n));
        return res;
    }

    /**
     * 导弹数据标准差业务实现
     * 公式：S = √[ Σ(yi-xi)² / (n) ]   （第一张图）
     * 示例：标准数据 [x1,x2,x3]  比对数据 [y1,y2,y3]
     *
     * @param std 标准数据
     * @param cmp 比对数据
     * @return 标准差
     */
    public Map<String, Double> calcMissileData(List<MissileData> std, List<Map<String, Object>> cmp) {
        if (std == null || cmp == null || std.size() != cmp.size() || std.isEmpty())
            throw new IllegalArgumentException("两组数据必须等长且>0");
        int n = std.size();
        double sumX = 0, sumY = 0, sumZ = 0, sumEnhancedState = 0;

        for (int i = 0; i < n; i++) {
            // 标准数据
            MissileData s = std.get(i);
            // 比对数据
            Map<String, Object> c = cmp.get(i);
            if (ObjectUtils.isNotEmpty(c.get("x"))) {
                sumX += singleScore(s.getX(), c.get("x").toString());
            }
            if (ObjectUtils.isNotEmpty(c.get("y"))) {
                sumY += singleScore(s.getY(), c.get("y").toString());
            }
            if (ObjectUtils.isNotEmpty(c.get("z"))) {
                sumZ += singleScore(s.getZ(), c.get("z").toString());
            }
            if (ObjectUtils.isNotEmpty(c.get("enhancedState"))) {
                sumEnhancedState += singleScore(s.getEnhancedState(), c.get("enhancedState").toString());
            }
        }

        Map<String, Double> res = new LinkedHashMap<>();
        res.put("X（北向位置）", Math.sqrt(sumX / n));
        res.put("Y（天向位置）", Math.sqrt(sumY / n));
        res.put("Z（东向位置）", Math.sqrt(sumZ / n));
        res.put("加力状态", Math.sqrt(sumEnhancedState / n));
        return res;
    }

    /**
     * 单条记录单字段得分
     */
    private double singleScore(String stdVal, String cmpVal) {
        // 标准值为 0 时直接给 0 分（或按业务抛异常）
        if (Double.parseDouble(stdVal) == 0) return 0;
        double ratio = Math.abs(Double.parseDouble(cmpVal) - Double.parseDouble(stdVal)) / Double.parseDouble(stdVal);
        // 100 制，越高越吻合
        return (1 - ratio) * 100;
    }





}
