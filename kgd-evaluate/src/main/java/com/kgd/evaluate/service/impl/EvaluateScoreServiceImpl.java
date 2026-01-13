package com.kgd.evaluate.service.impl;

import java.util.List;
import java.util.Map;

import com.kgd.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kgd.evaluate.mapper.EvaluateScoreMapper;
import com.kgd.evaluate.domain.EvaluateScore;
import com.kgd.evaluate.service.IEvaluateScoreService;

/**
 * 评估分数记录Service业务层处理
 *
 * @author kgd
 * @date 2025-11-19
 */
@Service
public class EvaluateScoreServiceImpl implements IEvaluateScoreService {
    @Autowired
    private EvaluateScoreMapper evaluateScoreMapper;

    /**
     * 查询评估分数记录
     *
     * @param id 评估分数记录主键
     * @return 评估分数记录
     */
    @Override
    public EvaluateScore selectEvaluateScoreById(Long id) {
        return evaluateScoreMapper.selectEvaluateScoreById(id);
    }

    /**
     * 查询评估分数记录列表
     *
     * @param evaluateScore 评估分数记录
     * @return 评估分数记录
     */
    @Override
    public List<EvaluateScore> selectEvaluateScoreList(EvaluateScore evaluateScore) {
        return evaluateScoreMapper.selectEvaluateScoreList(evaluateScore);
    }


    /**
     * 新增评估分数记录
     *
     * @param evaluateScore 评估分数记录
     * @return 结果
     */
    @Override
    public int insertEvaluateScore(EvaluateScore evaluateScore) {
        evaluateScore.setCreateTime(DateUtils.getNowDate());
        return evaluateScoreMapper.insertEvaluateScore(evaluateScore);
    }

    @Override
    public void insertEvaluateScoreBatch(List<EvaluateScore> list) {
        evaluateScoreMapper.insertEvaluateScoreBatch(list);
    }

    /**
     * 修改评估分数记录
     *
     * @param evaluateScore 评估分数记录
     * @return 结果
     */
    @Override
    public int updateEvaluateScore(EvaluateScore evaluateScore) {
        evaluateScore.setUpdateTime(DateUtils.getNowDate());
        return evaluateScoreMapper.updateEvaluateScore(evaluateScore);
    }

    /**
     * 批量删除评估分数记录
     *
     * @param ids 需要删除的评估分数记录主键
     * @return 结果
     */
    @Override
    public int deleteEvaluateScoreByIds(Long[] ids) {
        return evaluateScoreMapper.deleteEvaluateScoreByIds(ids);
    }

    /**
     * 删除评估分数记录信息
     *
     * @param id 评估分数记录主键
     * @return 结果
     */
    @Override
    public int deleteEvaluateScoreById(Long id) {
        return evaluateScoreMapper.deleteEvaluateScoreById(id);
    }
}
