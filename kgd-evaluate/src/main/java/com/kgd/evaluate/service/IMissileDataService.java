package com.kgd.evaluate.service;

import java.util.List;
import com.kgd.evaluate.domain.MissileData;

/**
 * 导弹数据Service接口
 * 
 * @author ruoyi
 * @date 2025-10-31
 */
public interface IMissileDataService 
{
    /**
     * 查询导弹数据
     * 
     * @param id 导弹数据主键
     * @return 导弹数据
     */
    public MissileData selectMissileDataById(Long id);

    /**
     * 查询导弹数据列表
     * 
     * @param missileData 导弹数据
     * @return 导弹数据集合
     */
    public List<MissileData> selectMissileDataList(MissileData missileData);

    /**
     * 新增导弹数据
     * 
     * @param missileData 导弹数据
     * @return 结果
     */
    public int insertMissileData(MissileData missileData);
    /**
     * 批量新增导弹数据
     *
     * @param list 导弹数据
     */
    public void insertMissileDataBatch(List<MissileData> list);

    /**
     * 修改导弹数据
     * 
     * @param missileData 导弹数据
     * @return 结果
     */
    public int updateMissileData(MissileData missileData);

    /**
     * 批量删除导弹数据
     * 
     * @param ids 需要删除的导弹数据主键集合
     * @return 结果
     */
    public int deleteMissileDataByIds(Long[] ids);

    /**
     * 删除导弹数据信息
     * 
     * @param id 导弹数据主键
     * @return 结果
     */
    public int deleteMissileDataById(Long id);

    /**
     * 删除导弹数据信息
     *
     * @param dataId 导弹数据数据ID
     */
    public void deleteMissileDataByDataId(String dataId);

    /**
     * 删除导弹数据
     *
     * @param missileData 导弹数据ID
     */
    public void deleteMissileData(MissileData missileData);
}
