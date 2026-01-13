package com.kgd.evaluate.service.impl;

import java.util.List;

import com.kgd.common.utils.DateUtils;
import com.kgd.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kgd.evaluate.mapper.ScenarioConfigurationMapper;
import com.kgd.evaluate.domain.ScenarioConfiguration;
import com.kgd.evaluate.service.IScenarioConfigurationService;

/**
 * 想定配置Service业务层处理
 *
 * @author ruoyi
 * @date 2025-11-05
 */
@Service
public class ScenarioConfigurationServiceImpl implements IScenarioConfigurationService {
    @Autowired
    private ScenarioConfigurationMapper scenarioConfigurationMapper;

    /**
     * 查询想定配置
     *
     * @param id 想定配置主键
     * @return 想定配置
     */
    @Override
    public ScenarioConfiguration selectScenarioConfigurationById(Long id) {
        return scenarioConfigurationMapper.selectScenarioConfigurationById(id);
    }

    /**
     * 查询想定配置列表
     *
     * @param scenarioConfiguration 想定配置
     * @return 想定配置
     */
    @Override
    public List<ScenarioConfiguration> selectScenarioConfigurationList(ScenarioConfiguration scenarioConfiguration) {
        return scenarioConfigurationMapper.selectScenarioConfigurationList(scenarioConfiguration);
    }

    /**
     * 新增想定配置
     *
     * @param scenarioConfiguration 想定配置
     * @return 结果
     */
    @Override
    public int insertScenarioConfiguration(ScenarioConfiguration scenarioConfiguration) {
        scenarioConfiguration.setCreateTime(DateUtils.getNowDate());
        scenarioConfiguration.setCreateBy(SecurityUtils.getUsername());
        return scenarioConfigurationMapper.insertScenarioConfiguration(scenarioConfiguration);
    }

    /**
     * 新增想定配置
     *
     * @param list 想定配置
     * @return 结果
     */
    @Override
    public void insertScenarioConfigurationBatch(List<ScenarioConfiguration> list) {
        scenarioConfigurationMapper.insertScenarioConfigurationBatch(list);
    }

    /**
     * 修改想定配置
     *
     * @param scenarioConfiguration 想定配置
     * @return 结果
     */
    @Override
    public int updateScenarioConfiguration(ScenarioConfiguration scenarioConfiguration) {
        scenarioConfiguration.setUpdateTime(DateUtils.getNowDate());
        scenarioConfiguration.setUpdateBy(SecurityUtils.getUsername());
        return scenarioConfigurationMapper.updateScenarioConfiguration(scenarioConfiguration);
    }

    /**
     * 批量删除想定配置
     *
     * @param ids 需要删除的想定配置主键
     * @return 结果
     */
    @Override
    public int deleteScenarioConfigurationByIds(Long[] ids) {
        return scenarioConfigurationMapper.deleteScenarioConfigurationByIds(ids);
    }

    /**
     * 删除想定配置信息
     *
     * @param id 想定配置主键
     * @return 结果
     */
    @Override
    public int deleteScenarioConfigurationById(Long id) {
        return scenarioConfigurationMapper.deleteScenarioConfigurationById(id);
    }

    /**
     * 批量删除想定配置
     *
     * @param dataId 需要删除的数据ID
     */
    @Override
    public void deleteScenarioConfigurationByDataId(String dataId) {
        scenarioConfigurationMapper.deleteScenarioConfigurationByDataId(dataId);
    }
}
