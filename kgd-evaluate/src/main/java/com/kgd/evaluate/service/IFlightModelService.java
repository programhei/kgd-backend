package com.kgd.evaluate.service;

import java.util.List;

import com.kgd.evaluate.domain.FlightData;
import com.kgd.evaluate.domain.FlightModel;

/**
 * 飞行模型性能配置Service接口
 * 
 * @author kgd
 * @date 2025-10-31
 */
public interface IFlightModelService 
{
    /**
     * 查询飞行模型性能配置
     * 
     * @param id 飞行模型性能配置主键
     * @return 飞行模型性能配置
     */
    public FlightModel selectFlightModelById(Long id);

    /**
     * 查询飞行模型性能配置列表
     * 
     * @param flightModel 飞行模型性能配置
     * @return 飞行模型性能配置集合
     */
    public List<FlightModel> selectFlightModelList(FlightModel flightModel);

    /**
     * 新增飞行模型性能配置
     * 
     * @param flightModel 飞行模型性能配置
     * @return 结果
     */
    public int insertFlightModel(FlightModel flightModel);

    /**
     * 新增飞行模型性能配置
     *
     * @param list 新增飞行模型性能配置
     */
    public void insertFlightModelBatch(List<FlightModel> list);

    /**
     * 修改飞行模型性能配置
     * 
     * @param flightModel 飞行模型性能配置
     * @return 结果
     */
    public int updateFlightModel(FlightModel flightModel);

    /**
     * 批量删除飞行模型性能配置
     * 
     * @param ids 需要删除的飞行模型性能配置主键集合
     * @return 结果
     */
    public int deleteFlightModelByIds(Long[] ids);

    /**
     * 删除飞行模型性能配置信息
     * 
     * @param id 飞行模型性能配置主键
     * @return 结果
     */
    public int deleteFlightModelById(Long id);

    /**
     * 删除飞行模型信息
     *
     * @param dataId 飞行模型数据ID
     */
    public void deleteFlightModelByDataId(String dataId);
}
