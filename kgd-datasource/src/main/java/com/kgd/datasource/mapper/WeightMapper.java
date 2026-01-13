package com.kgd.datasource.mapper;

import java.util.List;

import com.kgd.datasource.domain.Weight;
import com.kgd.datasource.domain.WeightRecord;
import com.kgd.datasource.domain.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 权值Mapper接口
 *
 * @author kgd
 * @date 2025-11-13
 */
public interface WeightMapper {
    /**
     * 查询权值
     *
     * @param id 权值主键
     * @return 权值
     */
    public Weight selectWeightById(Long id);

    /**
     * 查询权值列表
     *
     * @param weight 权值
     * @return 权值集合
     */
    public List<Weight> selectWeightList(Weight weight);

    /**
     * 新增权值
     *
     * @param weight 权值
     * @return 结果
     */
    public int insertWeight(Weight weight);

    /**
     * 新增权值
     *
     * @param list 权值
     * @return 结果
     */
    public int insertWeightBatch(@Param("list") List<Weight> list);

    /**
     * 修改权值
     *
     * @param weight 权值
     * @return 结果
     */
    public int updateWeight(Weight weight);

    /**
     * 删除权值
     *
     * @param id 权值主键
     * @return 结果
     */
    public int deleteWeightById(Long id);

    /**
     * 批量删除权值
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWeightByIds(Long[] ids);

    /**
     * 清空权值
     *
     * @return 结果
     */
    public int deleteWeight();




    public List<WeightRecord> selectWeightRecordList(WeightRecord WeightRecord);
    public int insertWeightRecord(WeightRecord WeightRecord);
    public int updateWeightRecord(WeightRecord WeightRecord);
    public int insertaaa(WeightRecord WeightRecord);

    public int insertFuelConsumptionExcelVo_standard(FuelConsumptionExcelVo record);
    public int insertFuelConsumptionExcelVo_test(FuelConsumptionExcelVo record);
    public int insertHeadingAngleExcelVo_standard(HeadingAngleExcelVo record);
    public int insertHeadingAngleExcelVo_test(HeadingAngleExcelVo record);

    public List<FuelConsumptionExcelVo> selectFuelConsumptionExcelVo_standard(FuelConsumptionExcelVo record);
    public List<FuelConsumptionExcelVo> selectFuelConsumptionExcelVo_test(FuelConsumptionExcelVo record);
    public List<HeadingAngleExcelVo> selectHeadingAngleExcelVo_standard(HeadingAngleExcelVo record);
    public List<HeadingAngleExcelVo> selectHeadingAngleExcelVo_test(HeadingAngleExcelVo record);

    public int deleteFuelConsumptionExcelVo_standard(Long id);
    public int deleteFuelConsumptionExcelVo_test(Long id);
    public int deleteHeadingAngleExcelVo_standard(Long id);
    public int deleteHeadingAngleExcelVo_test(Long id);

    public int insertMaxOverloadExcelVo_standard(MaxOverloadExcelVo maxOverloadExcelVo);
    public int insertMaxOverloadExcelVo_test(MaxOverloadExcelVo maxOverloadExcelVo);
    public int insertPerformanceAccuracyExcelVo_standard(PerformanceAccuracyExcelVo performanceAccuracyExcelVo);
    public int insertPerformanceAccuracyExcelVo_test(PerformanceAccuracyExcelVo performanceAccuracyExcelVo);

    public List<MaxOverloadExcelVo> selectMaxOverloadExcelVo_standard(MaxOverloadExcelVo maxOverloadExcelVo);
    public List<MaxOverloadExcelVo> selectMaxOverloadExcelVo_test(MaxOverloadExcelVo maxOverloadExcelVo);
    public List<PerformanceAccuracyExcelVo> selectPerformanceAccuracyExcelVo_standard(PerformanceAccuracyExcelVo performanceAccuracyExcelVo);
    public List<PerformanceAccuracyExcelVo> selectPerformanceAccuracyExcelVo_test(PerformanceAccuracyExcelVo performanceAccuracyExcelVo);

    public int deleteMaxOverloadExcelVo_standard(Long id);
    public int deleteMaxOverloadExcelVo_test(Long id);
    public int deletePerformanceAccuracyExcelVo_standard(Long id);
    public int deletePerformanceAccuracyExcelVo_test(Long id);

    public int insertRollAngleExcelVo_standard(RollAngleExcelVo rollAngleExcelVo);
    public int insertRollAngleExcelVo_test(RollAngleExcelVo rollAngleExcelVo);
    public int insertSpeedCommandExcelVo_standard(SpeedCommandExcelVo speedCommandExcelVo);
    public int insertSpeedCommandExcelVo_test(SpeedCommandExcelVo speedCommandExcelVo);

    public List<RollAngleExcelVo> selectRollAngleExcelVo_standard(RollAngleExcelVo rollAngleExcelVo);
    public List<RollAngleExcelVo> selectRollAngleExcelVo_test(RollAngleExcelVo rollAngleExcelVo);
    public List<SpeedCommandExcelVo> selectSpeedCommandExcelVo_standard(SpeedCommandExcelVo speedCommandExcelVo);
    public List<SpeedCommandExcelVo> selectSpeedCommandExcelVo_test(SpeedCommandExcelVo speedCommandExcelVo);

    public int deleteRollAngleExcelVo_standard(Long id);
    public int deleteRollAngleExcelVo_test(Long id);
    public int deleteSpeedCommandExcelVo_standard(Long id);
    public int deleteSpeedCommandExcelVo_test(Long id);

    public int updateWeightRecord_score(WeightRecord WeightRecord);
}
