package com.kgd.evaluate.mapper;

import java.util.List;

import com.kgd.evaluate.domain.MissileData;
import org.apache.ibatis.annotations.Param;

/**
 * 导弹数据Mapper接口
 *
 * @author ruoyi
 * @date 2025-10-31
 */
public interface MissileDataMapper {
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
     * 新增导弹数据
     *
     * @param list 导弹数据
     */
    void insertMissileDataBatch(@Param("list") List<MissileData> list);

    /**
     * 修改导弹数据
     *
     * @param missileData 导弹数据
     * @return 结果
     */
    public int updateMissileData(MissileData missileData);

    /**
     * 删除导弹数据
     *
     * @param id 导弹数据主键
     * @return 结果
     */
    public int deleteMissileDataById(Long id);

    /**
     * 批量删除导弹数据
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMissileDataByIds(Long[] ids);

    /**
     * 删除导弹数据
     *
     * @param dataId 导弹数据ID
     */
    public void deleteMissileDataByDataId(String dataId);

    /**
     * 删除导弹数据
     *
     * @param missileData 导弹数据ID
     */
    public void deleteMissileData(MissileData missileData);
}
