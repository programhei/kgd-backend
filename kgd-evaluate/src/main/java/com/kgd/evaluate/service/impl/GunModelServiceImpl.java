package com.kgd.evaluate.service.impl;

import java.util.List;

import com.kgd.common.utils.DateUtils;
import com.kgd.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kgd.evaluate.mapper.GunModelMapper;
import com.kgd.evaluate.domain.GunModel;
import com.kgd.evaluate.service.IGunModelService;

/**
 * 航炮Service业务层处理
 *
 * @author kgd
 * @date 2025-11-05
 */
@Service
public class GunModelServiceImpl implements IGunModelService {
    @Autowired
    private GunModelMapper gunModelMapper;

    /**
     * 查询航炮
     *
     * @param id 航炮主键
     * @return 航炮
     */
    @Override
    public GunModel selectGunModelById(Long id) {
        return gunModelMapper.selectGunModelById(id);
    }

    /**
     * 查询航炮列表
     *
     * @param gunModel 航炮
     * @return 航炮
     */
    @Override
    public List<GunModel> selectGunModelList(GunModel gunModel) {
        return gunModelMapper.selectGunModelList(gunModel);
    }

    /**
     * 新增航炮
     *
     * @param gunModel 航炮
     * @return 结果
     */
    @Override
    public int insertGunModel(GunModel gunModel) {
        gunModel.setCreateTime(DateUtils.getNowDate());
        gunModel.setCreateBy(SecurityUtils.getUsername());
        return gunModelMapper.insertGunModel(gunModel);
    }

    /**
     * 新增航炮
     *
     * @param list 航炮
     */
    @Override
    public void insertGunModelBatch(List<GunModel> list) {
        gunModelMapper.insertGunModelBatch(list);
    }

    /**
     * 修改航炮
     *
     * @param gunModel 航炮
     * @return 结果
     */
    @Override
    public int updateGunModel(GunModel gunModel) {
        gunModel.setUpdateTime(DateUtils.getNowDate());
        gunModel.setUpdateBy(SecurityUtils.getUsername());
        return gunModelMapper.updateGunModel(gunModel);
    }

    /**
     * 批量删除航炮
     *
     * @param ids 需要删除的航炮主键
     * @return 结果
     */
    @Override
    public int deleteGunModelByIds(Long[] ids) {
        return gunModelMapper.deleteGunModelByIds(ids);
    }

    /**
     * 删除航炮信息
     *
     * @param id 航炮主键
     * @return 结果
     */
    @Override
    public int deleteGunModelById(Long id) {
        return gunModelMapper.deleteGunModelById(id);
    }

    /**
     * 删除航炮信息
     *
     * @param dataId 航炮数据ID
     */
    @Override
    public void deleteGunModelByDataId(String dataId) {
        gunModelMapper.deleteGunModelByDataId(dataId);
    }
}
