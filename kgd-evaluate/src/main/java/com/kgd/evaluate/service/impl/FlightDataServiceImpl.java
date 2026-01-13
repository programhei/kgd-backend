package com.kgd.evaluate.service.impl;

import java.util.List;

import com.kgd.common.utils.DateUtils;
import com.kgd.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kgd.evaluate.mapper.FlightDataMapper;
import com.kgd.evaluate.domain.FlightData;
import com.kgd.evaluate.service.IFlightDataService;

/**
 * 飞行数据Service业务层处理
 *
 * @author ruoyi
 * @date 2025-10-31
 */
@Service
public class FlightDataServiceImpl implements IFlightDataService {
    @Autowired
    private FlightDataMapper flightDataMapper;

    /**
     * 查询飞行数据
     *
     * @param id 飞行数据主键
     * @return 飞行数据
     */
    @Override
    public FlightData selectFlightDataById(Long id) {
        return flightDataMapper.selectFlightDataById(id);
    }

    /**
     * 查询飞行数据列表
     *
     * @param flightData 飞行数据
     * @return 飞行数据
     */
    @Override
    public List<FlightData> selectFlightDataList(FlightData flightData) {
        return flightDataMapper.selectFlightDataList(flightData);
    }

    /**
     * 新增飞行数据
     *
     * @param flightData 飞行数据
     * @return 结果
     */
    @Override
    public int insertFlightData(FlightData flightData) {
        flightData.setCreateTime(DateUtils.getNowDate());
        flightData.setCreateBy(SecurityUtils.getUsername());
        return flightDataMapper.insertFlightData(flightData);
    }

    /**
     * 批量新增飞行数据
     *
     * @param list 飞行数据
     */
    @Override
    public void insertFlightDataBatch(List<FlightData> list) {
        flightDataMapper.insertFlightDataBatch(list);
    }

    /**
     * 修改飞行数据
     *
     * @param flightData 飞行数据
     * @return 结果
     */
    @Override
    public int updateFlightData(FlightData flightData) {
        flightData.setUpdateTime(DateUtils.getNowDate());
        flightData.setUpdateBy(SecurityUtils.getUsername());
        return flightDataMapper.updateFlightData(flightData);
    }

    /**
     * 批量删除飞行数据
     *
     * @param ids 需要删除的飞行数据主键
     * @return 结果
     */
    @Override
    public int deleteFlightDataByIds(Long[] ids) {
        return flightDataMapper.deleteFlightDataByIds(ids);
    }

    /**
     * 删除飞行数据信息
     *
     * @param id 飞行数据主键
     * @return 结果
     */
    @Override
    public int deleteFlightDataById(Long id) {
        return flightDataMapper.deleteFlightDataById(id);
    }

    /**
     * 删除飞行数据信息
     *
     * @param dataId 飞行数据ID
     */
    @Override
    public void deleteFlightDataByDataId(String dataId) {
        flightDataMapper.deleteFlightDataByDataId(dataId);
    }

    @Override
    public void deleteFlightData(FlightData flightData) {
        flightDataMapper.deleteFlightData(flightData);
    }
}
