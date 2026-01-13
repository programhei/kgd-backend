package com.kgd.evaluate.mapper;

import java.util.List;

import com.kgd.evaluate.domain.MissileModel;
import org.apache.ibatis.annotations.Param;

/**
 * 导弹模型Mapper接口
 *
 * @author ruoyi
 * @date 2025-10-31
 */
public interface MissileModelMapper {
    /**
     * 查询导弹模型
     *
     * @param id 导弹模型主键
     * @return 导弹模型
     */
    public MissileModel selectMissileModelById(Long id);

    /**
     * 查询导弹模型列表
     *
     * @param missileModel 导弹模型
     * @return 导弹模型集合
     */
    public List<MissileModel> selectMissileModelList(MissileModel missileModel);

    /**
     * 新增导弹模型
     *
     * @param missileModel 导弹模型
     * @return 结果
     */
    public int insertMissileModel(MissileModel missileModel);

    /**
     * 新增导弹模型
     *
     * @param list 导弹模型
     */
    public void insertMissileModelBatch(@Param("list") List<MissileModel> list);

    /**
     * 修改导弹模型
     *
     * @param missileModel 导弹模型
     * @return 结果
     */
    public int updateMissileModel(MissileModel missileModel);

    /**
     * 删除导弹模型
     *
     * @param id 导弹模型主键
     * @return 结果
     */
    public int deleteMissileModelById(Long id);

    /**
     * 批量删除导弹模型
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMissileModelByIds(Long[] ids);

    /**
     * 删除导弹模型
     *
     * @param dataId 导弹模型数据ID
     */
    public void deleteMissileModelByDataId(String dataId);
}
