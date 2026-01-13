package com.kgd.evaluate.service;

import java.util.List;

import com.kgd.evaluate.domain.ScenarioConfiguration;
import org.apache.ibatis.annotations.Param;

/**
 * 想定配置Service接口
 *
 * @author ruoyi
 * @date 2025-11-05
 */
public interface IScenarioConfigurationService {
    /**
     * 查询想定配置
     *
     * @param id 想定配置主键
     * @return 想定配置
     */
    public ScenarioConfiguration selectScenarioConfigurationById(Long id);

    /**
     * 查询想定配置列表
     *
     * @param scenarioConfiguration 想定配置
     * @return 想定配置集合
     */
    public List<ScenarioConfiguration> selectScenarioConfigurationList(ScenarioConfiguration scenarioConfiguration);

    /**
     * 新增想定配置
     *
     * @param scenarioConfiguration 想定配置
     * @return 结果
     */
    public int insertScenarioConfiguration(ScenarioConfiguration scenarioConfiguration);

    /**
     * 新增想定配置
     *
     * @param list 想定配置
     */
    public void insertScenarioConfigurationBatch(List<ScenarioConfiguration> list);

    /**
     * 修改想定配置
     *
     * @param scenarioConfiguration 想定配置
     * @return 结果
     */
    public int updateScenarioConfiguration(ScenarioConfiguration scenarioConfiguration);

    /**
     * 批量删除想定配置
     *
     * @param ids 需要删除的想定配置主键集合
     * @return 结果
     */
    public int deleteScenarioConfigurationByIds(Long[] ids);

    /**
     * 删除想定配置信息
     *
     * @param id 想定配置主键
     * @return 结果
     */
    public int deleteScenarioConfigurationById(Long id);

    /**
     * 批量删除想定配置
     *
     * @param dataId 需要删除的数据ID
     */
    public void deleteScenarioConfigurationByDataId(String dataId);
}
