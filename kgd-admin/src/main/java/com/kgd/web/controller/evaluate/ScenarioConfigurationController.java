package com.kgd.web.controller.evaluate;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kgd.common.annotation.Log;
import com.kgd.common.core.controller.BaseController;
import com.kgd.common.core.domain.AjaxResult;
import com.kgd.common.enums.BusinessType;
import com.kgd.evaluate.domain.ScenarioConfiguration;
import com.kgd.evaluate.service.IScenarioConfigurationService;
import com.kgd.common.utils.poi.ExcelUtil;
import com.kgd.common.core.page.TableDataInfo;

/**
 * 想定配置Controller
 * 
 * @author ruoyi
 * @date 2025-11-05
 */
@RestController
@RequestMapping("/evaluate/ScenarioConfiguration")
public class ScenarioConfigurationController extends BaseController
{
    @Autowired
    private IScenarioConfigurationService scenarioConfigurationService;

    /**
     * 查询想定配置列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:ScenarioConfiguration:list')")
    @GetMapping("/list")
    public TableDataInfo list(ScenarioConfiguration scenarioConfiguration)
    {
        startPage();
        List<ScenarioConfiguration> list = scenarioConfigurationService.selectScenarioConfigurationList(scenarioConfiguration);
        return getDataTable(list);
    }

    /**
     * 导出想定配置列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:ScenarioConfiguration:export')")
    @Log(title = "想定配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ScenarioConfiguration scenarioConfiguration)
    {
        List<ScenarioConfiguration> list = scenarioConfigurationService.selectScenarioConfigurationList(scenarioConfiguration);
        ExcelUtil<ScenarioConfiguration> util = new ExcelUtil<ScenarioConfiguration>(ScenarioConfiguration.class);
        util.exportExcel(response, list, "想定配置数据");
    }

    /**
     * 获取想定配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:ScenarioConfiguration:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(scenarioConfigurationService.selectScenarioConfigurationById(id));
    }

    /**
     * 新增想定配置
     */
    @PreAuthorize("@ss.hasPermi('evaluate:ScenarioConfiguration:add')")
    @Log(title = "想定配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ScenarioConfiguration scenarioConfiguration)
    {
        return toAjax(scenarioConfigurationService.insertScenarioConfiguration(scenarioConfiguration));
    }

    /**
     * 修改想定配置
     */
    @PreAuthorize("@ss.hasPermi('evaluate:ScenarioConfiguration:edit')")
    @Log(title = "想定配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ScenarioConfiguration scenarioConfiguration)
    {
        return toAjax(scenarioConfigurationService.updateScenarioConfiguration(scenarioConfiguration));
    }

    /**
     * 删除想定配置
     */
    @PreAuthorize("@ss.hasPermi('evaluate:ScenarioConfiguration:remove')")
    @Log(title = "想定配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(scenarioConfigurationService.deleteScenarioConfigurationByIds(ids));
    }
}
