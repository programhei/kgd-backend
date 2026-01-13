package com.kgd.datasource.service;

import java.util.List;
import com.kgd.datasource.domain.ExperimentalIdCorrespondence;

/**
 * 实验ID对应Service接口
 * 
 * @author kgd
 * @date 2025-11-06
 */
public interface IExperimentalIdCorrespondenceService 
{
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
     * 批量删除实验ID对应
     * 
     * @param ids 需要删除的实验ID对应主键集合
     * @return 结果
     */
    public int deleteExperimentalIdCorrespondenceByIds(Long[] ids);

    /**
     * 删除实验ID对应信息
     * 
     * @param id 实验ID对应主键
     * @return 结果
     */
    public int deleteExperimentalIdCorrespondenceById(Long id);
}
