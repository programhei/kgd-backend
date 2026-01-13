package com.kgd.evaluate.service.impl;

import java.util.List;

import com.kgd.common.utils.DateUtils;
import com.kgd.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kgd.evaluate.mapper.MissileModelMapper;
import com.kgd.evaluate.domain.MissileModel;
import com.kgd.evaluate.service.IMissileModelService;

/**
 * 导弹模型Service业务层处理
 *
 * @author ruoyi
 * @date 2025-10-31
 */
@Service
public class MissileModelServiceImpl implements IMissileModelService {
    @Autowired
    private MissileModelMapper missileModelMapper;

    /**
     * 查询导弹模型
     *
     * @param id 导弹模型主键
     * @return 导弹模型
     */
    @Override
    public MissileModel selectMissileModelById(Long id) {
        return missileModelMapper.selectMissileModelById(id);
    }

    /**
     * 查询导弹模型列表
     *
     * @param missileModel 导弹模型
     * @return 导弹模型
     */
    @Override
    public List<MissileModel> selectMissileModelList(MissileModel missileModel) {
        return missileModelMapper.selectMissileModelList(missileModel);
    }

    /**
     * 新增导弹模型
     *
     * @param missileModel 导弹模型
     * @return 结果
     */
    @Override
    public int insertMissileModel(MissileModel missileModel) {
        missileModel.setCreateTime(DateUtils.getNowDate());
        missileModel.setCreateBy(SecurityUtils.getUsername());
        return missileModelMapper.insertMissileModel(missileModel);
    }

    /**
     * 新增导弹模型
     *
     * @param missileModel 导弹模型
     */
    @Override
    public void insertMissileModelBatch(List<MissileModel> list) {
        missileModelMapper.insertMissileModelBatch(list);
    }

    /**
     * 修改导弹模型
     *
     * @param missileModel 导弹模型
     * @return 结果
     */
    @Override
    public int updateMissileModel(MissileModel missileModel) {
        missileModel.setUpdateTime(DateUtils.getNowDate());
        missileModel.setUpdateBy(SecurityUtils.getUsername());
        return missileModelMapper.updateMissileModel(missileModel);
    }

    /**
     * 批量删除导弹模型
     *
     * @param ids 需要删除的导弹模型主键
     * @return 结果
     */
    @Override
    public int deleteMissileModelByIds(Long[] ids) {
        return missileModelMapper.deleteMissileModelByIds(ids);
    }

    /**
     * 删除导弹模型信息
     *
     * @param id 导弹模型主键
     * @return 结果
     */
    @Override
    public int deleteMissileModelById(Long id) {
        return missileModelMapper.deleteMissileModelById(id);
    }

    /**
     * 删除导弹模型信息
     *
     * @param dataId 导弹模型数据ID
     */
    @Override
    public void deleteMissileModelByDataId(String dataId) {
        missileModelMapper.deleteMissileModelByDataId(dataId);
    }
}
