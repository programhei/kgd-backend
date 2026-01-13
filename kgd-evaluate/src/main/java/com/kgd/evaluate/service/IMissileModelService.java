package com.kgd.evaluate.service;

import java.util.List;

import com.kgd.evaluate.domain.FlightModel;
import com.kgd.evaluate.domain.MissileModel;

/**
 * 导弹模型Service接口
 *
 * @author ruoyi
 * @date 2025-10-31
 */
public interface IMissileModelService {
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
     * 新增导弹模型性能配置
     *
     * @param list 新增导弹模型性能配置
     */
    public void insertMissileModelBatch(List<MissileModel> list);


    /**
     * 修改导弹模型
     *
     * @param missileModel 导弹模型
     * @return 结果
     */
    public int updateMissileModel(MissileModel missileModel);

    /**
     * 批量删除导弹模型
     *
     * @param ids 需要删除的导弹模型主键集合
     * @return 结果
     */
    public int deleteMissileModelByIds(Long[] ids);

    /**
     * 删除导弹模型信息
     *
     * @param id 导弹模型主键
     * @return 结果
     */
    public int deleteMissileModelById(Long id);

    /**
     * 删除导弹模型信息
     *
     * @param dataId 导弹模型数据ID
     */
    public void deleteMissileModelByDataId(String dataId);
}
