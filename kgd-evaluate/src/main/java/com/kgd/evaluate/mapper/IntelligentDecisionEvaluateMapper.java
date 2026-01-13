package com.kgd.evaluate.mapper;

import com.kgd.evaluate.domain.IntelligentDecisionEvaluate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 支持智能决策模型生成与评估Mapper接口
 *
 * @author kgd
 * @date 2025-11-10
 */
public interface IntelligentDecisionEvaluateMapper {
    /**
     * 查询支持智能决策模型生成与评估
     *
     * @param id 支持智能决策模型生成与评估主键
     * @return 支持智能决策模型生成与评估
     */
    public IntelligentDecisionEvaluate selectIntelligentDecisionEvaluateById(Long id);

    /**
     * 查询支持智能决策模型生成与评估列表
     *
     * @param intelligentDecision 支持智能决策模型生成与评估
     * @return 支持智能决策模型生成与评估集合
     */
    public List<IntelligentDecisionEvaluate> selectIntelligentDecisionEvaluateList(IntelligentDecisionEvaluate intelligentDecision);

    /**
     * 新增支持智能决策模型生成与评估
     *
     * @param intelligentDecision 支持智能决策模型生成与评估
     * @return 结果
     */
    public int insertIntelligentDecisionEvaluate(IntelligentDecisionEvaluate intelligentDecision);


    /**
     * 批量插入，返回后 list 里每个对象的 id 会被赋值为自增主键
     */
    void insertIntelligentDecisionEvaluateBatch(@Param("list") List<IntelligentDecisionEvaluate> list);

    /**
     * 修改支持智能决策模型生成与评估
     *
     * @param intelligentDecision 支持智能决策模型生成与评估
     * @return 结果
     */
    public int updateIntelligentDecisionEvaluate(IntelligentDecisionEvaluate intelligentDecision);

    /**
     * 修改支持智能决策模型生成与评估
     *
     * @param list 支持智能决策模型生成与评估
     * @return 结果
     */
    int updateIntelligentDecisionEvaluateBatch(@Param("list") List<IntelligentDecisionEvaluate> list);

    /**
     * 删除支持智能决策模型生成与评估
     *
     * @param id 支持智能决策模型生成与评估主键
     * @return 结果
     */
    public int deleteIntelligentDecisionEvaluateById(Long id);

    /**
     * 批量删除支持智能决策模型生成与评估
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteIntelligentDecisionEvaluateByIds(Long[] ids);

    /**
     * 批量删除支持智能决策模型生成与评估
     *
     * @param dataId 需要删除的数据ID
     */
    public void deleteIntelligentDecisionEvaluateByDataId(String dataId);

    /**
     * 批量删除支持智能决策模型生成与评估
     */
    public void deleteIntelligentDecisionEvaluate();


}
