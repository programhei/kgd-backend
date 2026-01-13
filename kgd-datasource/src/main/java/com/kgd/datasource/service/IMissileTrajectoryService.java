package com.kgd.datasource.service;

import java.util.List;
import com.kgd.datasource.domain.vo.MissileTrajectoryVo;

/**
 * 弹道轨迹Service接口
 *
 * @author kgd
 * @date 2025-01-20
 */
public interface IMissileTrajectoryService {

    /**
     * 查询弹道轨迹
     *
     * @param id 弹道轨迹主键
     * @return 弹道轨迹
     */
    public MissileTrajectoryVo selectMissileTrajectoryById(Long id);

    /**
     * 查询弹道轨迹列表
     *
     * @param missileTrajectoryVo 弹道轨迹
     * @return 弹道轨迹集合
     */
    public List<MissileTrajectoryVo> selectMissileTrajectoryList(MissileTrajectoryVo missileTrajectoryVo);

    /**
     * 新增弹道轨迹
     *
     * @param missileTrajectoryVo 弹道轨迹
     * @return 结果
     */
    public int insertMissileTrajectory(MissileTrajectoryVo missileTrajectoryVo);

    /**
     * 批量新增弹道轨迹
     *
     * @param list 弹道轨迹列表
     * @return 结果
     */
    public int insertMissileTrajectoryBatch(List<MissileTrajectoryVo> list);

    /**
     * 修改弹道轨迹
     *
     * @param missileTrajectoryVo 弹道轨迹
     * @return 结果
     */
    public int updateMissileTrajectory(MissileTrajectoryVo missileTrajectoryVo);

    /**
     * 批量删除弹道轨迹
     *
     * @param ids 需要删除的弹道轨迹主键集合
     * @return 结果
     */
    public int deleteMissileTrajectoryByIds(Long[] ids);

    /**
     * 删除弹道轨迹信息
     *
     * @param id 弹道轨迹主键
     * @return 结果
     */
    public int deleteMissileTrajectoryById(Long id);
}

