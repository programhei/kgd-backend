package com.kgd.evaluate.service.impl;

import java.util.List;

import com.kgd.common.utils.DateUtils;
import com.kgd.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kgd.evaluate.mapper.MissileDataMapper;
import com.kgd.evaluate.domain.MissileData;
import com.kgd.evaluate.service.IMissileDataService;

/**
 * 导弹数据Service业务层处理
 *
 * @author ruoyi
 * @date 2025-10-31
 */
@Service
public class MissileDataServiceImpl implements IMissileDataService {
    @Autowired
    private MissileDataMapper missileDataMapper;

    /**
     * 查询导弹数据
     *
     * @param id 导弹数据主键
     * @return 导弹数据
     */
    @Override
    public MissileData selectMissileDataById(Long id) {
        return missileDataMapper.selectMissileDataById(id);
    }

    /**
     * 查询导弹数据列表
     *
     * @param missileData 导弹数据
     * @return 导弹数据
     */
    @Override
    public List<MissileData> selectMissileDataList(MissileData missileData) {
        return missileDataMapper.selectMissileDataList(missileData);
    }

    /**
     * 新增导弹数据
     *
     * @param missileData 导弹数据
     * @return 结果
     */
    @Override
    public int insertMissileData(MissileData missileData) {
        missileData.setCreateTime(DateUtils.getNowDate());
        missileData.setCreateBy(SecurityUtils.getUsername());
        return missileDataMapper.insertMissileData(missileData);
    }

    /**
     * 新增导弹数据
     *
     * @param list 导弹数据
     */
    @Override
    public void insertMissileDataBatch(List<MissileData> list) {
        missileDataMapper.insertMissileDataBatch(list);
    }

    /**
     * 修改导弹数据
     *
     * @param missileData 导弹数据
     * @return 结果
     */
    @Override
    public int updateMissileData(MissileData missileData) {
        missileData.setUpdateTime(DateUtils.getNowDate());
        missileData.setUpdateBy(SecurityUtils.getUsername());
        return missileDataMapper.updateMissileData(missileData);
    }

    /**
     * 批量删除导弹数据
     *
     * @param ids 需要删除的导弹数据主键
     * @return 结果
     */
    @Override
    public int deleteMissileDataByIds(Long[] ids) {
        return missileDataMapper.deleteMissileDataByIds(ids);
    }

    /**
     * 删除导弹数据信息
     *
     * @param id 导弹数据主键
     * @return 结果
     */
    @Override
    public int deleteMissileDataById(Long id) {
        return missileDataMapper.deleteMissileDataById(id);
    }


    /**
     * 删除导弹数据信息
     *
     * @param createTime 导弹数据ID
     */
    @Override
    public void deleteMissileDataByDataId(String dataId) {
        missileDataMapper.deleteMissileDataByDataId(dataId);
    }

    @Override
    public void deleteMissileData(MissileData missileData) {
        missileDataMapper.deleteMissileData(missileData);
    }
}
