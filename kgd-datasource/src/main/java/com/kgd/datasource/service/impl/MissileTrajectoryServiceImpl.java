package com.kgd.datasource.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kgd.datasource.mapper.MissileTrajectoryMapper;
import com.kgd.datasource.domain.vo.MissileTrajectoryVo;
import com.kgd.datasource.service.IMissileTrajectoryService;

/**
 * 弹道轨迹Service业务层处理
 *
 * @author kgd
 * @date 2025-01-20
 */
@Service
public class MissileTrajectoryServiceImpl implements IMissileTrajectoryService {

    @Autowired
    private MissileTrajectoryMapper missileTrajectoryMapper;

    /**
     * 查询弹道轨迹
     *
     * @param id 弹道轨迹主键
     * @return 弹道轨迹
     */
    @Override
    public MissileTrajectoryVo selectMissileTrajectoryById(Long id) {
        return missileTrajectoryMapper.selectMissileTrajectoryById(id);
    }

    /**
     * 查询弹道轨迹列表
     *
     * @param missileTrajectoryVo 弹道轨迹
     * @return 弹道轨迹集合
     */
    @Override
    public List<MissileTrajectoryVo> selectMissileTrajectoryList(MissileTrajectoryVo missileTrajectoryVo) {
        return missileTrajectoryMapper.selectMissileTrajectoryList(missileTrajectoryVo);
    }

    /**
     * 新增弹道轨迹
     *
     * @param missileTrajectoryVo 弹道轨迹
     * @return 结果
     */
    @Override
    public int insertMissileTrajectory(MissileTrajectoryVo missileTrajectoryVo) {
        return missileTrajectoryMapper.insertMissileTrajectory(missileTrajectoryVo);
    }

    /**
     * 批量新增弹道轨迹
     *
     * @param list 弹道轨迹列表
     * @return 结果
     */
    @Override
    public int insertMissileTrajectoryBatch(List<MissileTrajectoryVo> list) {
        return missileTrajectoryMapper.insertMissileTrajectoryBatch(list);
    }

    /**
     * 修改弹道轨迹
     *
     * @param missileTrajectoryVo 弹道轨迹
     * @return 结果
     */
    @Override
    public int updateMissileTrajectory(MissileTrajectoryVo missileTrajectoryVo) {
        return missileTrajectoryMapper.updateMissileTrajectory(missileTrajectoryVo);
    }

    /**
     * 批量删除弹道轨迹
     *
     * @param ids 需要删除的弹道轨迹主键集合
     * @return 结果
     */
    @Override
    public int deleteMissileTrajectoryByIds(Long[] ids) {
        return missileTrajectoryMapper.deleteMissileTrajectoryByIds(ids);
    }

    /**
     * 删除弹道轨迹信息
     *
     * @param id 弹道轨迹主键
     * @return 结果
     */
    @Override
    public int deleteMissileTrajectoryById(Long id) {
        return missileTrajectoryMapper.deleteMissileTrajectoryById(id);
    }
}

