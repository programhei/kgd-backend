package com.kgd.evaluate.service;

import java.util.List;

import com.kgd.evaluate.domain.IntelligentDecision;
import org.apache.ibatis.annotations.Param;

/**
 * 支持智能决策模型生成与评估Service接口
 *
 * @author kgd
 * @date 2025-11-10
 */
public interface IIntelligentDecisionService {
    /**
     * 查询支持智能决策模型生成与评估
     *
     * @param id 支持智能决策模型生成与评估主键
     * @return 支持智能决策模型生成与评估
     */
    public IntelligentDecision selectIntelligentDecisionById(Long id);

    /**
     * 查询支持智能决策模型生成与评估列表
     *
     * @param intelligentDecision 支持智能决策模型生成与评估
     * @return 支持智能决策模型生成与评估集合
     */
    public List<IntelligentDecision> selectIntelligentDecisionList(IntelligentDecision intelligentDecision);

    /**
     * 新增支持智能决策模型生成与评估
     *
     * @param intelligentDecision 支持智能决策模型生成与评估
     * @return 结果
     */
    public int insertIntelligentDecision(IntelligentDecision intelligentDecision);


    /**
     * 批量插入，返回后 list 里每个对象的 id 会被赋值为自增主键
     */
    void insertIntelligentDecisionBatch(List<IntelligentDecision> list);

    /**
     * 修改支持智能决策模型生成与评估
     *
     * @param intelligentDecision 支持智能决策模型生成与评估
     * @return 结果
     */
    public int updateIntelligentDecision(IntelligentDecision intelligentDecision);

    /**
     * 修改支持智能决策模型生成与评估
     *
     * @param list 支持智能决策模型生成与评估
     * @return 结果
     */
    public int updateIntelligentDecisionBatch(List<IntelligentDecision> list);

    /**
     * 批量删除支持智能决策模型生成与评估
     *
     * @param ids 需要删除的支持智能决策模型生成与评估主键集合
     * @return 结果
     */
    public int deleteIntelligentDecisionByIds(Long[] ids);

    /**
     * 删除支持智能决策模型生成与评估信息
     *
     * @param id 支持智能决策模型生成与评估主键
     * @return 结果
     */
    public int deleteIntelligentDecisionById(Long id);

    /**
     * 批量删除支持智能决策模型生成与评估
     *
     * @param dataId 需要删除的数据ID
     */
    public void deleteIntelligentDecisionByDataId(String dataId);

    /**
     * 批量删除支持智能决策模型生成与评估
     */
    public void deleteIntelligentDecision();

}
