package com.kgd.evaluate.service.impl;

import java.util.List;

import com.kgd.common.utils.DateUtils;
import com.kgd.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kgd.evaluate.mapper.FlightModelMapper;
import com.kgd.evaluate.domain.FlightModel;
import com.kgd.evaluate.service.IFlightModelService;

/**
 * 飞行模型性能配置Service业务层处理
 *
 * @author kgd
 * @date 2025-10-31
 */
@Service
public class FlightModelServiceImpl implements IFlightModelService {
    @Autowired
    private FlightModelMapper flightModelMapper;

    /**
     * 查询飞行模型性能配置
     *
     * @param id 飞行模型性能配置主键
     * @return 飞行模型性能配置
     */
    @Override
    public FlightModel selectFlightModelById(Long id) {
        return flightModelMapper.selectFlightModelById(id);
    }

    /**
     * 查询飞行模型性能配置列表
     *
     * @param flightModel 飞行模型性能配置
     * @return 飞行模型性能配置
     */
    @Override
    public List<FlightModel> selectFlightModelList(FlightModel flightModel) {
        return flightModelMapper.selectFlightModelList(flightModel);
    }

    /**
     * 新增飞行模型性能配置
     *
     * @param flightModel 飞行模型性能配置
     * @return 结果
     */
    @Override
    public int insertFlightModel(FlightModel flightModel) {
        flightModel.setCreateTime(DateUtils.getNowDate());
        flightModel.setCreateBy(SecurityUtils.getUsername());
        return flightModelMapper.insertFlightModel(flightModel);
    }

    /**
     * 新增飞行模型性能配置
     *
     * @param list 飞行模型性能配置
     */
    @Override
    public void insertFlightModelBatch(List<FlightModel> list) {
        flightModelMapper.insertFlightModelBatch(list);
    }


    /**
     * 修改飞行模型性能配置
     *
     * @param flightModel 飞行模型性能配置
     * @return 结果
     */
    @Override
    public int updateFlightModel(FlightModel flightModel) {
        flightModel.setUpdateTime(DateUtils.getNowDate());
        flightModel.setUpdateBy(SecurityUtils.getUsername());
        return flightModelMapper.updateFlightModel(flightModel);
    }

    /**
     * 批量删除飞行模型性能配置
     *
     * @param ids 需要删除的飞行模型性能配置主键
     * @return 结果
     */
    @Override
    public int deleteFlightModelByIds(Long[] ids) {
        return flightModelMapper.deleteFlightModelByIds(ids);
    }

    /**
     * 删除飞行模型性能配置信息
     *
     * @param id 飞行模型性能配置主键
     * @return 结果
     */
    @Override
    public int deleteFlightModelById(Long id) {
        return flightModelMapper.deleteFlightModelById(id);
    }


    /**
     * 删除飞行模型信息
     *
     * @param dataId 飞行模型数据ID
     */
    @Override
    public void deleteFlightModelByDataId(String dataId) {
        flightModelMapper.deleteFlightModelByDataId(dataId);
    }
}
