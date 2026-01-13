package com.kgd.evaluate.service;

import java.util.List;

import com.kgd.evaluate.domain.FlightModel;
import com.kgd.evaluate.domain.RadarModel;

/**
 * 雷达模型Service接口
 *
 * @author ruoyi
 * @date 2025-10-31
 */
public interface IRadarModelService {
    /**
     * 查询雷达模型
     *
     * @param id 雷达模型主键
     * @return 雷达模型
     */
    public RadarModel selectRadarModelById(Long id);

    /**
     * 查询雷达模型列表
     *
     * @param radarModel 雷达模型
     * @return 雷达模型集合
     */
    public List<RadarModel> selectRadarModelList(RadarModel radarModel);

    /**
     * 新增雷达模型
     *
     * @param radarModel 雷达模型
     * @return 结果
     */
    public int insertRadarModel(RadarModel radarModel);

    /**
     * 新增雷达模型
     *
     * @param list 雷达模型
     */
    public void insertRadarModelBatch(List<RadarModel> list);

    /**
     * 修改雷达模型
     *
     * @param radarModel 雷达模型
     * @return 结果
     */
    public int updateRadarModel(RadarModel radarModel);

    /**
     * 批量删除雷达模型
     *
     * @param ids 需要删除的雷达模型主键集合
     * @return 结果
     */
    public int deleteRadarModelByIds(Long[] ids);

    /**
     * 删除雷达模型信息
     *
     * @param id 雷达模型主键
     * @return 结果
     */
    public int deleteRadarModelById(Long id);


    /**
     * 删除雷达模型信息
     *
     * @param dataId 雷达模型数据ID
     */
    public void deleteRadarModelByDataId(String dataId);
}
