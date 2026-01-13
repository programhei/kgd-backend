package com.kgd.datasource.service.impl;

import java.util.List;

import com.kgd.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kgd.datasource.mapper.ExperimentalIdCorrespondenceMapper;
import com.kgd.datasource.domain.ExperimentalIdCorrespondence;
import com.kgd.datasource.service.IExperimentalIdCorrespondenceService;

import javax.annotation.Resource;

/**
 * 实验ID对应Service业务层处理
 *
 * @author kgd
 * @date 2025-11-06
 */
@Service
public class ExperimentalIdCorrespondenceServiceImpl implements IExperimentalIdCorrespondenceService {
    @Resource
    private ExperimentalIdCorrespondenceMapper experimentalIdCorrespondenceMapper;

    /**
     * 查询实验ID对应
     *
     * @param id 实验ID对应主键
     * @return 实验ID对应
     */
    @Override
    public ExperimentalIdCorrespondence selectExperimentalIdCorrespondenceById(Long id) {
        return experimentalIdCorrespondenceMapper.selectExperimentalIdCorrespondenceById(id);
    }


    /**
     * 查询实验ID对应
     *
     * @param experimentalIdCorrespondence 实验ID对应
     * @return 实验ID对应集合
     */
    @Override
    public ExperimentalIdCorrespondence selectExperimentalIdCorrespondence(ExperimentalIdCorrespondence experimentalIdCorrespondence) {
        return experimentalIdCorrespondenceMapper.selectExperimentalIdCorrespondence(experimentalIdCorrespondence);
    }

    /**
     * 查询实验ID对应列表
     *
     * @param experimentalIdCorrespondence 实验ID对应
     * @return 实验ID对应
     */
    @Override
    public List<ExperimentalIdCorrespondence> selectExperimentalIdCorrespondenceList(ExperimentalIdCorrespondence experimentalIdCorrespondence) {
        return experimentalIdCorrespondenceMapper.selectExperimentalIdCorrespondenceList(experimentalIdCorrespondence);
    }

    /**
     * 新增实验ID对应
     *
     * @param experimentalIdCorrespondence 实验ID对应
     * @return 结果
     */
    @Override
    public int insertExperimentalIdCorrespondence(ExperimentalIdCorrespondence experimentalIdCorrespondence) {
        experimentalIdCorrespondence.setCreateTime(DateUtils.getNowDate());
        return experimentalIdCorrespondenceMapper.insertExperimentalIdCorrespondence(experimentalIdCorrespondence);
    }

    /**
     * 修改实验ID对应
     *
     * @param experimentalIdCorrespondence 实验ID对应
     * @return 结果
     */
    @Override
    public int updateExperimentalIdCorrespondence(ExperimentalIdCorrespondence experimentalIdCorrespondence) {
        experimentalIdCorrespondence.setUpdateTime(DateUtils.getNowDate());
        return experimentalIdCorrespondenceMapper.updateExperimentalIdCorrespondence(experimentalIdCorrespondence);
    }

    /**
     * 批量删除实验ID对应
     *
     * @param ids 需要删除的实验ID对应主键
     * @return 结果
     */
    @Override
    public int deleteExperimentalIdCorrespondenceByIds(Long[] ids) {
        return experimentalIdCorrespondenceMapper.deleteExperimentalIdCorrespondenceByIds(ids);
    }

    /**
     * 删除实验ID对应信息
     *
     * @param id 实验ID对应主键
     * @return 结果
     */
    @Override
    public int deleteExperimentalIdCorrespondenceById(Long id) {
        return experimentalIdCorrespondenceMapper.deleteExperimentalIdCorrespondenceById(id);
    }
}
