package com.kgd.evaluate.service.impl;

import com.kgd.common.utils.DateUtils;
import com.kgd.common.utils.SecurityUtils;
import com.kgd.evaluate.domain.IntelligentDecisionEvaluate;
import com.kgd.evaluate.mapper.IntelligentDecisionEvaluateMapper;
import com.kgd.evaluate.service.IIntelligentDecisionEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支持智能决策模型生成与评估Service业务层处理
 *
 * @author kgd
 * @date 2025-11-10
 */
@Service
public class IntelligentDecisionEvaluateServiceImpl implements IIntelligentDecisionEvaluateService {
    @Autowired
    private IntelligentDecisionEvaluateMapper intelligentDecisionEvaluateMapper;

    /**
     * 查询支持智能决策模型生成与评估
     *
     * @param id 支持智能决策模型生成与评估主键
     * @return 支持智能决策模型生成与评估
     */
    @Override
    public IntelligentDecisionEvaluate selectIntelligentDecisionEvaluateById(Long id) {
        return intelligentDecisionEvaluateMapper.selectIntelligentDecisionEvaluateById(id);
    }

    /**
     * 查询支持智能决策模型生成与评估列表
     *
     * @param intelligentDecision 支持智能决策模型生成与评估
     * @return 支持智能决策模型生成与评估
     */
    @Override
    public List<IntelligentDecisionEvaluate> selectIntelligentDecisionEvaluateList(IntelligentDecisionEvaluate intelligentDecision) {
        return intelligentDecisionEvaluateMapper.selectIntelligentDecisionEvaluateList(intelligentDecision);
    }

    /**
     * 新增支持智能决策模型生成与评估
     *
     * @param intelligentDecision 支持智能决策模型生成与评估
     * @return 结果
     */
    @Override
    public int insertIntelligentDecisionEvaluate(IntelligentDecisionEvaluate intelligentDecision) {
        intelligentDecision.setCreateTime(DateUtils.getNowDate());
        intelligentDecision.setCreateBy(SecurityUtils.getUsername());
        return intelligentDecisionEvaluateMapper.insertIntelligentDecisionEvaluate(intelligentDecision);
    }

    /**
     * 新增支持智能决策模型生成与评估
     *
     * @param list 支持智能决策模型生成与评估
     */
    @Override
    public void insertIntelligentDecisionEvaluateBatch(List<IntelligentDecisionEvaluate> list) {
        intelligentDecisionEvaluateMapper.insertIntelligentDecisionEvaluateBatch(list);
    }

    /**
     * 修改支持智能决策模型生成与评估
     *
     * @param intelligentDecision 支持智能决策模型生成与评估
     * @return 结果
     */
    @Override
    public int updateIntelligentDecisionEvaluate(IntelligentDecisionEvaluate intelligentDecision) {
        intelligentDecision.setUpdateTime(DateUtils.getNowDate());
        intelligentDecision.setUpdateBy(SecurityUtils.getUsername());
        return intelligentDecisionEvaluateMapper.updateIntelligentDecisionEvaluate(intelligentDecision);
    }

    /**
     * 修改支持智能决策模型生成与评估
     *
     * @param list 支持智能决策模型生成与评估
     * @return 结果
     */
    @Override
    public int updateIntelligentDecisionEvaluateBatch(List<IntelligentDecisionEvaluate> list) {
        return intelligentDecisionEvaluateMapper.updateIntelligentDecisionEvaluateBatch(list);
    }

    /**
     * 批量删除支持智能决策模型生成与评估
     *
     * @param ids 需要删除的支持智能决策模型生成与评估主键
     * @return 结果
     */
    @Override
    public int deleteIntelligentDecisionEvaluateByIds(Long[] ids) {
        return intelligentDecisionEvaluateMapper.deleteIntelligentDecisionEvaluateByIds(ids);
    }

    /**
     * 删除支持智能决策模型生成与评估信息
     *
     * @param id 支持智能决策模型生成与评估主键
     * @return 结果
     */
    @Override
    public int deleteIntelligentDecisionEvaluateById(Long id) {
        return intelligentDecisionEvaluateMapper.deleteIntelligentDecisionEvaluateById(id);
    }

    /**
     * 删除支持智能决策模型生成与评估信息
     *
     * @param dataId 支持智能决策模型生成数据ID
     */
    @Override
    public void deleteIntelligentDecisionEvaluateByDataId(String dataId) {
        intelligentDecisionEvaluateMapper.deleteIntelligentDecisionEvaluateByDataId(dataId);
    }

    @Override
    public void deleteIntelligentDecisionEvaluate() {
        intelligentDecisionEvaluateMapper.deleteIntelligentDecisionEvaluate();
    }
}
