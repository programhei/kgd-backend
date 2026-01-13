package com.kgd.evaluate.mapper;

import java.util.List;

import com.kgd.evaluate.domain.EvaluateScore;
import org.apache.ibatis.annotations.Param;

/**
 * 评估分数记录Mapper接口
 *
 * @author kgd
 * @date 2025-11-19
 */
public interface EvaluateScoreMapper {
    /**
     * 查询评估分数记录
     *
     * @param id 评估分数记录主键
     * @return 评估分数记录
     */
    public EvaluateScore selectEvaluateScoreById(Long id);

    /**
     * 查询评估分数记录列表
     *
     * @param evaluateScore 评估分数记录
     * @return 评估分数记录集合
     */
    public List<EvaluateScore> selectEvaluateScoreList(EvaluateScore evaluateScore);

    /**
     * 新增评估分数记录
     *
     * @param evaluateScore 评估分数记录
     * @return 结果
     */
    public int insertEvaluateScore(EvaluateScore evaluateScore);

    /**
     * 新增评估分数记录
     *
     * @param list 评估分数记录
     */
    public void insertEvaluateScoreBatch(@Param("list") List<EvaluateScore> list);

    /**
     * 修改评估分数记录
     *
     * @param evaluateScore 评估分数记录
     * @return 结果
     */
    public int updateEvaluateScore(EvaluateScore evaluateScore);

    /**
     * 删除评估分数记录
     *
     * @param id 评估分数记录主键
     * @return 结果
     */
    public int deleteEvaluateScoreById(Long id);

    /**
     * 批量删除评估分数记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEvaluateScoreByIds(Long[] ids);
}
