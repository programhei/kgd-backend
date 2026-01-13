package com.kgd.datasource.mapper;

import java.util.List;

import com.kgd.datasource.domain.ExperimentalIdCorrespondence;

/**
 * 实验ID对应Mapper接口
 *
 * @author kgd
 * @date 2025-11-06
 */
public interface ExperimentalIdCorrespondenceMapper {
    /**
     * 查询实验ID对应
     *
     * @param id 实验ID对应主键
     * @return 实验ID对应
     */
    public ExperimentalIdCorrespondence selectExperimentalIdCorrespondenceById(Long id);

    /**
     * 查询实验ID对应
     *
     * @param experimentalIdCorrespondence 实验ID对应
     * @return 实验ID对应集合
     */
    public ExperimentalIdCorrespondence selectExperimentalIdCorrespondence(ExperimentalIdCorrespondence experimentalIdCorrespondence);

    /**
     * 查询实验ID对应列表
     *
     * @param experimentalIdCorrespondence 实验ID对应
     * @return 实验ID对应集合
     */
    public List<ExperimentalIdCorrespondence> selectExperimentalIdCorrespondenceList(ExperimentalIdCorrespondence experimentalIdCorrespondence);

    /**
     * 新增实验ID对应
     *
     * @param experimentalIdCorrespondence 实验ID对应
     * @return 结果
     */
    public int insertExperimentalIdCorrespondence(ExperimentalIdCorrespondence experimentalIdCorrespondence);

    /**
     * 修改实验ID对应
     *
     * @param experimentalIdCorrespondence 实验ID对应
     * @return 结果
     */
    public int updateExperimentalIdCorrespondence(ExperimentalIdCorrespondence experimentalIdCorrespondence);

    /**
     * 删除实验ID对应
     *
     * @param id 实验ID对应主键
     * @return 结果
     */
    public int deleteExperimentalIdCorrespondenceById(Long id);

    /**
     * 批量删除实验ID对应
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExperimentalIdCorrespondenceByIds(Long[] ids);
}
