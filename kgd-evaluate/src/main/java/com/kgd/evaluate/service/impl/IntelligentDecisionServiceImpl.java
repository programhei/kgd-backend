package com.kgd.evaluate.service.impl;

import java.util.List;

import com.kgd.common.utils.DateUtils;
import com.kgd.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kgd.evaluate.mapper.IntelligentDecisionMapper;
import com.kgd.evaluate.domain.IntelligentDecision;
import com.kgd.evaluate.service.IIntelligentDecisionService;

/**
 * 支持智能决策模型生成与评估Service业务层处理
 *
 * @author kgd
 * @date 2025-11-10
 */
@Service
public class IntelligentDecisionServiceImpl implements IIntelligentDecisionService {
    @Autowired
    private IntelligentDecisionMapper intelligentDecisionMapper;

    /**
     * 查询支持智能决策模型生成与评估
     *
     * @param id 支持智能决策模型生成与评估主键
     * @return 支持智能决策模型生成与评估
     */
    @Override
    public IntelligentDecision selectIntelligentDecisionById(Long id) {
        return intelligentDecisionMapper.selectIntelligentDecisionById(id);
    }

    /**
     * 查询支持智能决策模型生成与评估列表
     *
     * @param intelligentDecision 支持智能决策模型生成与评估
     * @return 支持智能决策模型生成与评估
     */
    @Override
    public List<IntelligentDecision> selectIntelligentDecisionList(IntelligentDecision intelligentDecision) {
        return intelligentDecisionMapper.selectIntelligentDecisionList(intelligentDecision);
    }

    /**
     * 新增支持智能决策模型生成与评估
     *
     * @param intelligentDecision 支持智能决策模型生成与评估
     * @return 结果
     */
    @Override
    public int insertIntelligentDecision(IntelligentDecision intelligentDecision) {
        intelligentDecision.setCreateTime(DateUtils.getNowDate());
        intelligentDecision.setCreateBy(SecurityUtils.getUsername());
        return intelligentDecisionMapper.insertIntelligentDecision(intelligentDecision);
    }

    /**
     * 新增支持智能决策模型生成与评估
     *
     * @param list 支持智能决策模型生成与评估
     */
    @Override
    public void insertIntelligentDecisionBatch(List<IntelligentDecision> list) {
        intelligentDecisionMapper.insertIntelligentDecisionBatch(list);
    }

    /**
     * 修改支持智能决策模型生成与评估
     *
     * @param intelligentDecision 支持智能决策模型生成与评估
     * @return 结果
     */
    @Override
    public int updateIntelligentDecision(IntelligentDecision intelligentDecision) {
        intelligentDecision.setUpdateTime(DateUtils.getNowDate());
        intelligentDecision.setUpdateBy(SecurityUtils.getUsername());
        return intelligentDecisionMapper.updateIntelligentDecision(intelligentDecision);
    }

    /**
     * 修改支持智能决策模型生成与评估
     *
     * @param list 支持智能决策模型生成与评估
     * @return 结果
     */
    @Override
    public int updateIntelligentDecisionBatch(List<IntelligentDecision> list) {
        return intelligentDecisionMapper.updateIntelligentDecisionBatch(list);
    }

    /**
     * 批量删除支持智能决策模型生成与评估
     *
     * @param ids 需要删除的支持智能决策模型生成与评估主键
     * @return 结果
     */
    @Override
    public int deleteIntelligentDecisionByIds(Long[] ids) {
        return intelligentDecisionMapper.deleteIntelligentDecisionByIds(ids);
    }

    /**
     * 删除支持智能决策模型生成与评估信息
     *
     * @param id 支持智能决策模型生成与评估主键
     * @return 结果
     */
    @Override
    public int deleteIntelligentDecisionById(Long id) {
        return intelligentDecisionMapper.deleteIntelligentDecisionById(id);
    }

    /**
     * 删除支持智能决策模型生成与评估信息
     *
     * @param dataId 支持智能决策模型生成数据ID
     */
    @Override
    public void deleteIntelligentDecisionByDataId(String dataId) {
        intelligentDecisionMapper.deleteIntelligentDecisionByDataId(dataId);
    }

    @Override
    public void deleteIntelligentDecision() {
        intelligentDecisionMapper.deleteIntelligentDecision();
    }
}
