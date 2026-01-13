package com.kgd.evaluate.mapper;

import java.util.List;

import com.kgd.evaluate.domain.FlightData;
import org.apache.ibatis.annotations.Param;

/**
 * 飞行数据Mapper接口
 *
 * @author ruoyi
 * @date 2025-10-31
 */
public interface FlightDataMapper {
    /**
     * 查询飞行数据
     *
     * @param id 飞行数据主键
     * @return 飞行数据
     */
    public FlightData selectFlightDataById(Long id);

    /**
     * 查询飞行数据列表
     *
     * @param flightData 飞行数据
     * @return 飞行数据集合
     */
    public List<FlightData> selectFlightDataList(FlightData flightData);

    /**
     * 新增飞行数据
     *
     * @param flightData 飞行数据
     * @return 结果
     */
    public int insertFlightData(FlightData flightData);

    /**
     * 新增飞行数据
     *
     * @param list 飞行数据
     */
    public void insertFlightDataBatch(@Param("list") List<FlightData> list);

    /**
     * 修改飞行数据
     *
     * @param flightData 飞行数据
     * @return 结果
     */
    public int updateFlightData(FlightData flightData);

    /**
     * 删除飞行数据
     *
     * @param id 飞行数据主键
     * @return 结果
     */
    public int deleteFlightDataById(Long id);

    /**
     * 批量删除飞行数据
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFlightDataByIds(Long[] ids);

    /**
     * 删除飞行数据
     *
     * @param dataId 飞行数据ID
     */
    public void deleteFlightDataByDataId(String dataId);
    /**
     * 删除飞行数据
     *
     * @param flightData 飞行数据
     */
    public void deleteFlightData(FlightData flightData);

}
