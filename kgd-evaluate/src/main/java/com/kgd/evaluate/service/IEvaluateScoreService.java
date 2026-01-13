package com.kgd.evaluate.service;

import java.util.List;
import java.util.Map;

import com.kgd.evaluate.domain.EvaluateScore;
import org.apache.ibatis.annotations.Param;

/**
 * 评估分数记录Service接口
 *
 * @author kgd
 * @date 2025-11-19
 */
public interface IEvaluateScoreService
{
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
    public void insertEvaluateScoreBatch(List<EvaluateScore> list);

    /**
     * 修改评估分数记录
     *
     * @param evaluateScore 评估分数记录
     * @return 结果
     */
    public int updateEvaluateScore(EvaluateScore evaluateScore);

    /**
     * 批量删除评估分数记录
     *
     * @param ids 需要删除的评估分数记录主键集合
     * @return 结果
     */
    public int deleteEvaluateScoreByIds(Long[] ids);

    /**
     * 删除评估分数记录信息
     *
     * @param id 评估分数记录主键
     * @return 结果
     */
    public int deleteEvaluateScoreById(Long id);
}
