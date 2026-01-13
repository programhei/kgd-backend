package com.kgd.web.controller.datasource;

import java.util.List;
import javax.annotation.Resource;
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
import com.kgd.datasource.domain.ExperimentalIdCorrespondence;
import com.kgd.datasource.service.IExperimentalIdCorrespondenceService;
import com.kgd.common.utils.poi.ExcelUtil;
import com.kgd.common.core.page.TableDataInfo;

/**
 * 实验ID对应Controller
 *
 * @author kgd
 * @date 2025-11-06
 */
@RestController
@RequestMapping("/datasource/experimentalIdCorrespondence")
public class ExperimentalIdCorrespondenceController extends BaseController {
    @Resource
    private IExperimentalIdCorrespondenceService experimentalIdCorrespondenceService;

    /**
     * 查询实验ID对应列表
     */
//    @PreAuthorize("@ss.hasPermi('datasource:experimentalIdCorrespondence:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExperimentalIdCorrespondence experimentalIdCorrespondence) {
        startPage();
        List<ExperimentalIdCorrespondence> list = experimentalIdCorrespondenceService.selectExperimentalIdCorrespondenceList(experimentalIdCorrespondence);
        return getDataTable(list);
    }

    /**
     * 导出实验ID对应列表
     */
//    @PreAuthorize("@ss.hasPermi('datasource:experimentalIdCorrespondence:export')")
    @Log(title = "实验ID对应", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExperimentalIdCorrespondence experimentalIdCorrespondence) {
        List<ExperimentalIdCorrespondence> list = experimentalIdCorrespondenceService.selectExperimentalIdCorrespondenceList(experimentalIdCorrespondence);
        ExcelUtil<ExperimentalIdCorrespondence> util = new ExcelUtil<ExperimentalIdCorrespondence>(ExperimentalIdCorrespondence.class);
        util.exportExcel(response, list, "实验ID对应数据");
    }

    /**
     * 获取实验ID对应详细信息
     */
//    @PreAuthorize("@ss.hasPermi('datasource:experimentalIdCorrespondence:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(experimentalIdCorrespondenceService.selectExperimentalIdCorrespondenceById(id));
    }

    /**
     * 新增实验ID对应
     */
//    @PreAuthorize("@ss.hasPermi('datasource:experimentalIdCorrespondence:add')")
    @Log(title = "实验ID对应", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExperimentalIdCorrespondence experimentalIdCorrespondence) {
        return toAjax(experimentalIdCorrespondenceService.insertExperimentalIdCorrespondence(experimentalIdCorrespondence));
    }

    /**
     * 修改实验ID对应
     */
//    @PreAuthorize("@ss.hasPermi('datasource:experimentalIdCorrespondence:edit')")
    @Log(title = "实验ID对应", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExperimentalIdCorrespondence experimentalIdCorrespondence) {
        return toAjax(experimentalIdCorrespondenceService.updateExperimentalIdCorrespondence(experimentalIdCorrespondence));
    }

    /**
     * 删除实验ID对应
     */
//    @PreAuthorize("@ss.hasPermi('datasource:experimentalIdCorrespondence:remove')")
    @Log(title = "实验ID对应", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(experimentalIdCorrespondenceService.deleteExperimentalIdCorrespondenceByIds(ids));
    }
}
