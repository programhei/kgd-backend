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
import com.kgd.evaluate.domain.SoftwareQuality;
import com.kgd.evaluate.service.ISoftwareQualityService;
import com.kgd.common.utils.poi.ExcelUtil;
import com.kgd.common.core.page.TableDataInfo;

/**
 * 软件质量Controller
 * 
 * @author kgd
 * @date 2025-11-05
 */
@RestController
@RequestMapping("/evaluate/softwareQuality")
public class SoftwareQualityController extends BaseController
{
    @Autowired
    private ISoftwareQualityService softwareQualityService;

    /**
     * 查询软件质量列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:softwareQuality:list')")
    @GetMapping("/list")
    public TableDataInfo list(SoftwareQuality softwareQuality)
    {
        startPage();
        List<SoftwareQuality> list = softwareQualityService.selectSoftwareQualityList(softwareQuality);
        return getDataTable(list);
    }

    /**
     * 导出软件质量列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:softwareQuality:export')")
    @Log(title = "软件质量", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SoftwareQuality softwareQuality)
    {
        List<SoftwareQuality> list = softwareQualityService.selectSoftwareQualityList(softwareQuality);
        ExcelUtil<SoftwareQuality> util = new ExcelUtil<SoftwareQuality>(SoftwareQuality.class);
        util.exportExcel(response, list, "软件质量数据");
    }

    /**
     * 获取软件质量详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:softwareQuality:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(softwareQualityService.selectSoftwareQualityById(id));
    }

    /**
     * 新增软件质量
     */
    @PreAuthorize("@ss.hasPermi('evaluate:softwareQuality:add')")
    @Log(title = "软件质量", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SoftwareQuality softwareQuality)
    {
        return toAjax(softwareQualityService.insertSoftwareQuality(softwareQuality));
    }

    /**
     * 修改软件质量
     */
    @PreAuthorize("@ss.hasPermi('evaluate:softwareQuality:edit')")
    @Log(title = "软件质量", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SoftwareQuality softwareQuality)
    {
        return toAjax(softwareQualityService.updateSoftwareQuality(softwareQuality));
    }

    /**
     * 删除软件质量
     */
    @PreAuthorize("@ss.hasPermi('evaluate:softwareQuality:remove')")
    @Log(title = "软件质量", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(softwareQualityService.deleteSoftwareQualityByIds(ids));
    }
}
