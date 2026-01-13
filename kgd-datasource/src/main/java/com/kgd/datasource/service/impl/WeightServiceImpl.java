package com.kgd.datasource.service.impl;

import com.kgd.common.core.redis.RedisCache;
import com.kgd.common.utils.DateUtils;
import com.kgd.common.utils.SecurityUtils;
import com.kgd.datasource.domain.ExperimentalRecord;
import com.kgd.datasource.domain.Weight;
import com.kgd.datasource.domain.vo.MaxAccelerationStandardVo;
import com.kgd.datasource.mapper.WeightMapper;
import com.kgd.datasource.service.IExperimentalRecordService;
import com.kgd.datasource.service.IWeightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 权值Service业务层处理
 *
 * @author kgd
 * @date 2025-11-13
 */
@Service
public  class WeightServiceImpl implements IWeightService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WeightMapper weightMapper;

    @Resource
    private IExperimentalRecordService experimentalRecordService;

    @Resource
    private RedisCache redisCache;

    /**
     * 查询权值
     *
     * @param id 权值主键
     * @return 权值
     */
    @Override
    public Weight selectWeightById(Long id) {
        return weightMapper.selectWeightById(id);
    }

    /**
     * 查询权值列表
     *
     * @param weight 权值
     * @return 权值
     */
    @Override
    public List<Weight> selectWeightList(Weight weight) {
        return weightMapper.selectWeightList(weight);
    }

    /**
     * 新增权值
     *
     * @param weight 权值
     * @return 结果
     */
    @Override
    public int insertWeight(Weight weight) {
        weight.setCreateTime(DateUtils.getNowDate());
        return weightMapper.insertWeight(weight);
    }

    /**
     * 修改权值
     *
     * @param weight 权值
     * @return 结果
     */
    @Override
    public int updateWeight(Weight weight) {
        weight.setUpdateTime(DateUtils.getNowDate());
        return weightMapper.updateWeight(weight);
    }

    /**
     * 批量删除权值
     *
     * @param ids 需要删除的权值主键
     * @return 结果
     */
    @Override
    public int deleteWeightByIds(Long[] ids) {
        return weightMapper.deleteWeightByIds(ids);
    }

    /**
     * 删除权值信息
     *
     * @param id 权值主键
     * @return 结果
     */
    @Override
    public int deleteWeightById(Long id) {
        return weightMapper.deleteWeightById(id);
    }

    /**
     * 清空权值
     *
     * @return 结果
     */
    public int deleteWeight() {
        return weightMapper.deleteWeight();
    }

    /**
     * 更新权值
     */
    @Override
    public void loadWeightData() throws Exception {
        weightMapper.deleteWeight();
        List<ExperimentalRecord> list = experimentalRecordService.selectExperimentalRecordList(new ExperimentalRecord());
        if (list.isEmpty()) {
            throw new Exception("暂无实验数据");
        }
        List<Weight> weightList = compute(list);
        weightMapper.insertWeightBatch(weightList);
    }

    @Override
    public MaxAccelerationStandardVo maxSpeed(List<MaxAccelerationStandardVo> standardList, List<MaxAccelerationStandardVo> testList) {
        MaxAccelerationStandardVo maxAccelerationStandardVo = new MaxAccelerationStandardVo();
        // 按时间建立标准数据映射
        Map<Double, MaxAccelerationStandardVo> standardMap = standardList.stream()
                .collect(Collectors.toMap(MaxAccelerationStandardVo::getTime, s -> s));

        for (MaxAccelerationStandardVo test : testList) {
            MaxAccelerationStandardVo standard = standardMap.get(test.getTime());
            if (standard == null) {
                logger.info("未找到时间 " + test.getTime() + " 对应的标准数据，跳过。");
                continue;
            }

            logger.info("=== 时间: " + test.getTime() + " 秒 ===");

            // 逐高度计算得分（严格按照公式：EXP(-((|test - std| / std)^2))）
            double score6000   = calculateGaussianScore(standard.getHeight6000(),   test.getHeight6000());
            double score8000   = calculateGaussianScore(standard.getHeight8000(),   test.getHeight8000());
            double score10000  = calculateGaussianScore(standard.getHeight10000(),  test.getHeight10000());
            double score12000  = calculateGaussianScore(standard.getHeight12000(),  test.getHeight12000());
            double score14000  = calculateGaussianScore(standard.getHeight14000(),  test.getHeight14000());

            // 输出结果（保留7位小数，与Excel一致）
            maxAccelerationStandardVo.setHeight6000(score6000);
            maxAccelerationStandardVo.setHeight8000(score8000);
            maxAccelerationStandardVo.setHeight10000(score10000);
            maxAccelerationStandardVo.setHeight12000(score12000);
            maxAccelerationStandardVo.setHeight14000(score14000);
            logger.info("maxAccelerationStandardVo:{}",maxAccelerationStandardVo);
        }
        return maxAccelerationStandardVo;
    }

    /**
     * 核心计算方法：高斯相似度得分
     * 公式：EXP( - (|test - standard| / standard)^2 )
     */
    private static double calculateGaussianScore(Double standard, Double test) {
        // 处理 null 值
        if (standard == null || test == null) {
            return 0.0; // 或抛异常，根据业务
        }

        // 防止除零：若标准值为0
        if (Math.abs(standard) < 1e-12) {
            return (Math.abs(test) < 1e-12) ? 1.0 : 0.0;
        }

        double relativeError = Math.abs(test - standard) / standard;
        return Math.exp(-relativeError * relativeError);
    }

    // 归一化
    public List<Weight> compute(List<ExperimentalRecord> list) {
        if (list == null || list.isEmpty()) throw new IllegalArgumentException("数据为空");
        // 结果集
        List<Weight> result = new ArrayList<>();
        /* 1. 分组：维度#变量值#变量 */
        Map<String, List<ExperimentalRecord>> indicatorGroup = list.stream()
                .collect(Collectors.groupingBy(r ->
                        r.getDimension() + "#" + r.getVariableValue() + "#" + r.getVariable()));
        /* 2. 计算每个指标的 Gj */
        Map<String, Double> gMap = new LinkedHashMap<>();
        for (Map.Entry<String, List<ExperimentalRecord>> e : indicatorGroup.entrySet()) {
            List<ExperimentalRecord> rows = e.getValue();
            int n = rows.size();

            // 标准化 [0,1]
            double[] F = rows.stream()
                    .mapToDouble(r -> Math.max(0, Math.min(100, Double.parseDouble(r.getScore()))) / 100)
                    .toArray();

            double sumF = Arrays.stream(F).sum();
            double[] P = Arrays.stream(F).map(f -> f / sumF).toArray();

            // 熵值 Ej
            double entropy = Arrays.stream(P)
                    .filter(p -> p > 0)
                    .map(p -> p * Math.log(p))
                    .sum();
            double Ej = -entropy / Math.log(n);
            double Gj = 1 - Ej;
            gMap.put(e.getKey(), Gj);
        }

        /* 3. 维度内归一化 qj = Gj / ΣGj */
        Map<String, List<String>> dimGroup = gMap.keySet().stream()
                .collect(Collectors.groupingBy(k -> k.split("#")[0]));
        final double k1 = 0.6;
        final double k2 = 0.4;
        final double P = 0.3;

        for (Map.Entry<String, List<String>> e : dimGroup.entrySet()) {
            String dimension = e.getKey();
            List<String> keys = e.getValue();
            double sumG = keys.stream().mapToDouble(gMap::get).sum();
            if (sumG == 0) sumG = 1e-12;

            for (String key : keys) {
                Weight w = new Weight();
                String[] arr = key.split("#");
                w.setCoefficientK1(BigDecimal.valueOf(k1));
                w.setCoefficientK2(BigDecimal.valueOf(k2));
                w.setSubjectiveWeight(BigDecimal.valueOf(P));
                w.setCreateTime(DateUtils.getNowDate());
                w.setCreateBy(SecurityUtils.getUsername());
                w.setDimension(arr[0]);
                w.setVariable(arr[1]);
                w.setVariableValue(arr[2]);
                w.setObjectiveWeight(
                        BigDecimal.valueOf(gMap.get(key))
                                .divide(BigDecimal.valueOf(sumG), 2, RoundingMode.DOWN)
                );
                result.add(w);
            }
        }
        return result;
    }
}
