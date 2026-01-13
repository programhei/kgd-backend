package com.kgd.datasource.service;

import java.util.List;

import com.kgd.datasource.domain.ExperimentalRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 实验记录Service接口
 *
 * @author kgd
 * @date 2025-11-06
 */
public interface IExperimentalRecordService {
    /**
     * 查询实验记录
     *
     * @param id 实验记录主键
     * @return 实验记录
     */
    public ExperimentalRecord selectExperimentalRecordById(Long id);

    /**
     * 查询实验记录列表
     *
     * @param experimentalRecord 实验记录
     * @return 实验记录集合
     */
    public List<ExperimentalRecord> selectExperimentalRecordList(ExperimentalRecord experimentalRecord);

    /**
     * 新增实验记录
     *
     * @param experimentalRecord 实验记录
     * @return 结果
     */
    public int insertExperimentalRecord(ExperimentalRecord experimentalRecord);

    /**
     * 导入实验记录
     *
     * @param filePath 上传的文件地址
     */
    public void importData(String filePath) throws Exception;
    /**
     * 导入Afsim实验记录
     *
     * @param filePath 上传的文件地址
     */
    public void importAfSimData(String filePath) throws Exception;

    /**
     * 修改实验记录
     *
     * @param experimentalRecord 实验记录
     * @return 结果
     */
    public int updateExperimentalRecord(ExperimentalRecord experimentalRecord);

    /**
     * 批量删除实验记录
     *
     * @param ids 需要删除的实验记录主键集合
     * @return 结果
     */
    public int deleteExperimentalRecordByIds(Long[] ids);

    /**
     * 删除实验记录信息
     *
     * @param id 实验记录主键
     * @return 结果
     */
    public int deleteExperimentalRecordById(Long id);

    /**
     * 清空实验记录
     *
     * @return 结果
     */
    public int deleteExperimentalRecord();

}
