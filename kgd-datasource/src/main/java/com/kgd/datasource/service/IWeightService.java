package com.kgd.datasource.service;

import java.util.List;

import com.kgd.common.core.domain.AjaxResult;
import com.kgd.datasource.domain.Weight;
import com.kgd.datasource.domain.vo.MaxAccelerationStandardVo;

/**
 * 权值Service接口
 *
 * @author kgd
 * @date 2025-11-13
 */
public interface IWeightService {
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
     * 修改权值
     *
     * @param weight 权值
     * @return 结果
     */
    public int updateWeight(Weight weight);

    /**
     * 批量删除权值
     *
     * @param ids 需要删除的权值主键集合
     * @return 结果
     */
    public int deleteWeightByIds(Long[] ids);

    /**
     * 删除权值信息
     *
     * @param id 权值主键
     * @return 结果
     */
    public int deleteWeightById(Long id);

    /**
     * 清空权值
     *
     * @return 结果
     */
    public int deleteWeight();

    /**
     * 更新权值
     *
     */
    public void loadWeightData() throws Exception;

    /**
     * 最大加速度和最大速度公式计算
     */
    public MaxAccelerationStandardVo maxSpeed(List<MaxAccelerationStandardVo> standardList, List<MaxAccelerationStandardVo> testList);
}
