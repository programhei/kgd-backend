package com.kgd.evaluate.service.impl;

import java.util.List;

import com.kgd.common.utils.DateUtils;
import com.kgd.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kgd.evaluate.mapper.RadarModelMapper;
import com.kgd.evaluate.domain.RadarModel;
import com.kgd.evaluate.service.IRadarModelService;

/**
 * 雷达模型Service业务层处理
 *
 * @author ruoyi
 * @date 2025-10-31
 */
@Service
public class RadarModelServiceImpl implements IRadarModelService {
    @Autowired
    private RadarModelMapper radarModelMapper;

    /**
     * 查询雷达模型
     *
     * @param id 雷达模型主键
     * @return 雷达模型
     */
    @Override
    public RadarModel selectRadarModelById(Long id) {
        return radarModelMapper.selectRadarModelById(id);
    }

    /**
     * 查询雷达模型列表
     *
     * @param radarModel 雷达模型
     * @return 雷达模型
     */
    @Override
    public List<RadarModel> selectRadarModelList(RadarModel radarModel) {
        return radarModelMapper.selectRadarModelList(radarModel);
    }

    /**
     * 新增雷达模型
     *
     * @param radarModel 雷达模型
     * @return 结果
     */
    @Override
    public int insertRadarModel(RadarModel radarModel) {
        radarModel.setCreateTime(DateUtils.getNowDate());
        radarModel.setCreateBy(SecurityUtils.getUsername());
        return radarModelMapper.insertRadarModel(radarModel);
    }

    /**
     * 新增雷达模型
     *
     * @param list 雷达模型
     */
    @Override
    public void insertRadarModelBatch(List<RadarModel> list) {
        radarModelMapper.insertRadarModelBatch(list);
    }

    /**
     * 修改雷达模型
     *
     * @param radarModel 雷达模型
     * @return 结果
     */
    @Override
    public int updateRadarModel(RadarModel radarModel) {
        radarModel.setUpdateTime(DateUtils.getNowDate());
        radarModel.setUpdateBy(SecurityUtils.getUsername());
        return radarModelMapper.updateRadarModel(radarModel);
    }

    /**
     * 批量删除雷达模型
     *
     * @param ids 需要删除的雷达模型主键
     * @return 结果
     */
    @Override
    public int deleteRadarModelByIds(Long[] ids) {
        return radarModelMapper.deleteRadarModelByIds(ids);
    }

    /**
     * 删除雷达模型信息
     *
     * @param id 雷达模型主键
     * @return 结果
     */
    @Override
    public int deleteRadarModelById(Long id) {
        return radarModelMapper.deleteRadarModelById(id);
    }


    /**
     * 删除雷达数据信息
     *
     * @param dataId 雷达数据ID
     */
    @Override
    public void deleteRadarModelByDataId(String dataId) {
        radarModelMapper.deleteRadarModelByDataId(dataId);
    }
}
