package com.kgd.evaluate.service;

import java.util.List;

import com.kgd.evaluate.domain.GunModel;
import org.apache.ibatis.annotations.Param;

/**
 * 航炮Service接口
 *
 * @author kgd
 * @date 2025-11-05
 */
public interface IGunModelService {
    /**
     * 查询航炮
     *
     * @param id 航炮主键
     * @return 航炮
     */
    public GunModel selectGunModelById(Long id);

    /**
     * 查询航炮列表
     *
     * @param gunModel 航炮
     * @return 航炮集合
     */
    public List<GunModel> selectGunModelList(GunModel gunModel);

    /**
     * 新增航炮
     *
     * @param gunModel 航炮
     * @return 结果
     */
    public int insertGunModel(GunModel gunModel);

    /**
     * 新增航炮
     *
     * @param list 航炮
     */
    public void insertGunModelBatch(List<GunModel> list);

    /**
     * 修改航炮
     *
     * @param gunModel 航炮
     * @return 结果
     */
    public int updateGunModel(GunModel gunModel);

    /**
     * 批量删除航炮
     *
     * @param ids 需要删除的航炮主键集合
     * @return 结果
     */
    public int deleteGunModelByIds(Long[] ids);

    /**
     * 删除航炮信息
     *
     * @param id 航炮主键
     * @return 结果
     */
    public int deleteGunModelById(Long id);

    /**
     * 删除航炮信息
     *
     * @param dataId 航炮数据ID
     */
    public void deleteGunModelByDataId(String dataId);
}
