package com.kgd.evaluate.service.impl;

import java.util.List;

import com.kgd.common.utils.DateUtils;
import com.kgd.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kgd.evaluate.mapper.RadarDataMapper;
import com.kgd.evaluate.domain.RadarData;
import com.kgd.evaluate.service.IRadarDataService;

/**
 * 雷达数据Service业务层处理
 *
 * @author ruoyi
 * @date 2025-10-31
 */
@Service
public class RadarDataServiceImpl implements IRadarDataService {
    @Autowired
    private RadarDataMapper radarDataMapper;

    /**
     * 查询雷达数据
     *
     * @param id 雷达数据主键
     * @return 雷达数据
     */
    @Override
    public RadarData selectRadarDataById(Long id) {
        return radarDataMapper.selectRadarDataById(id);
    }

    /**
     * 查询雷达数据列表
     *
     * @param radarData 雷达数据
     * @return 雷达数据
     */
    @Override
    public List<RadarData> selectRadarDataList(RadarData radarData) {
        return radarDataMapper.selectRadarDataList(radarData);
    }

    /**
     * 新增雷达数据
     *
     * @param radarData 雷达数据
     * @return 结果
     */
    @Override
    public int insertRadarData(RadarData radarData) {
        radarData.setCreateTime(DateUtils.getNowDate());
        radarData.setCreateBy(SecurityUtils.getUsername());
        return radarDataMapper.insertRadarData(radarData);
    }

    /**
     * 新增雷达数据
     *
     * @param list 雷达数据
     */
    @Override
    public void insertRadarDataBatch(List<RadarData> list) {
        radarDataMapper.insertRadarDataBatch(list);
    }

    /**
     * 修改雷达数据
     *
     * @param radarData 雷达数据
     * @return 结果
     */
    @Override
    public int updateRadarData(RadarData radarData) {
        radarData.setUpdateTime(DateUtils.getNowDate());
        radarData.setUpdateBy(SecurityUtils.getUsername());
        return radarDataMapper.updateRadarData(radarData);
    }

    /**
     * 批量删除雷达数据
     *
     * @param ids 需要删除的雷达数据主键
     * @return 结果
     */
    @Override
    public int deleteRadarDataByIds(Long[] ids) {
        return radarDataMapper.deleteRadarDataByIds(ids);
    }

    /**
     * 删除雷达数据信息
     *
     * @param id 雷达数据主键
     * @return 结果
     */
    @Override
    public int deleteRadarDataById(Long id) {
        return radarDataMapper.deleteRadarDataById(id);
    }

    /**
     * 删除雷达数据信息
     *
     * @param dataId 雷达数据ID
     */
    @Override
    public void deleteRadarDataByDataId(String dataId) {
        radarDataMapper.deleteRadarDataByDataId(dataId);
    }

    /**
     * 删除雷达数据
     *
     * @param radarData 雷达数据ID
     */
    @Override
    public void deleteRadarData(RadarData radarData){
        radarDataMapper.deleteRadarData(radarData);
    }
}
