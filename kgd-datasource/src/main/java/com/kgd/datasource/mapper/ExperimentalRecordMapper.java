package com.kgd.datasource.mapper;

import java.util.List;

import com.kgd.datasource.domain.ExperimentalRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 实验记录Mapper接口
 *
 * @author kgd
 * @date 2025-11-06
 */
public interface ExperimentalRecordMapper {
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
     * 数据库里按  dimension (第一维度)、 variable (第二维度)、 variableValue  三个字段查当前最大实验次数序号
     *
     * @param dimension     第一维度
     * @param variable      第二维度
     * @param variableValue 三个字段查当前最大实验次数序号
     * @return 当前最大实验次数的序号
     */

    int selectMaxExpSerial(@Param("dimension") String dimension,
                           @Param("variable") String variable,
                           @Param("variableValue") String variableValue);

    /**
     * 数据库里按  dimension (第一维度)、 variable (第二维度)、 variableValue  三个字段查当前最大实验ID
     *
     * @param dimension     第一维度
     * @param variable      第二维度
     * @param variableValue 三个字段查当前最大实验ID
     * @return 当前最大实验ID
     */
    String selectMaxExpId(@Param("dimension") String dimension,
                          @Param("variable") String variable,
                          @Param("variableValue") String variableValue);

    /**
     * 数据库里按  dimension (第一维度)、 variable (第二维度)、 variableValue  三个字段查当前最大实验ID
     *
     * @param dimension     第一维度
     * @param variable      第二维度
     * @param variableValue 三个字段查当前最大实验ID
     * @return 记录数量
     */
    int countByDimensionVariableValue(@Param("dimension") String dimension,
                                      @Param("variable") String variable,
                                      @Param("variableValue") String variableValue);


    /**
     * 新增实验记录
     *
     * @param experimentalRecord 实验记录
     * @return 结果
     */
    public int insertExperimentalRecord(ExperimentalRecord experimentalRecord);

    /**
     * 新增实验记录
     *
     * @param list 实验记录
     */
    public void insertExperimentalRecordBatch(@Param("list") List<ExperimentalRecord> list);

    /**
     * 修改实验记录
     *
     * @param experimentalRecord 实验记录
     * @return 结果
     */
    public int updateExperimentalRecord(ExperimentalRecord experimentalRecord);

    /**
     * 删除实验记录
     *
     * @param id 实验记录主键
     * @return 结果
     */
    public int deleteExperimentalRecordById(Long id);

    /**
     * 批量删除实验记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExperimentalRecordByIds(Long[] ids);

    /**
     * 清空实验记录
     *
     * @return 结果
     */
    public int deleteExperimentalRecord();
}
