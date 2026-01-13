package com.kgd.web.controller.datasource;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
import com.kgd.datasource.domain.ExperimentalRecord;
import com.kgd.datasource.service.IExperimentalRecordService;
import com.kgd.common.utils.poi.ExcelUtil;
import com.kgd.common.core.page.TableDataInfo;

/**
 * 实验记录Controller
 *
 * @author kgd
 * @date 2025-11-06
 */
@RestController
@RequestMapping("/datasource/experimentalRecord")
public class ExperimentalRecordController extends BaseController {
    @Resource
    private IExperimentalRecordService experimentalRecordService;

    /**
     * 查询实验记录列表
     */
//    @PreAuthorize("@ss.hasPermi('datasource:experimentalRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExperimentalRecord experimentalRecord) {
        startPage();
        List<ExperimentalRecord> list = experimentalRecordService.selectExperimentalRecordList(experimentalRecord);
        return getDataTable(list);
    }

    /**
     * 导出实验记录列表
     */
//    @PreAuthorize("@ss.hasPermi('datasource:experimentalRecord:export')")
    @Log(title = "实验记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExperimentalRecord experimentalRecord) {
        List<ExperimentalRecord> list = experimentalRecordService.selectExperimentalRecordList(experimentalRecord);
        ExcelUtil<ExperimentalRecord> util = new ExcelUtil<ExperimentalRecord>(ExperimentalRecord.class);
        util.exportExcel(response, list, "实验记录数据");
    }

    /**
     * 获取实验记录详细信息
     */
//    @PreAuthorize("@ss.hasPermi('datasource:experimentalRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(experimentalRecordService.selectExperimentalRecordById(id));
    }

    /**
     * 新增实验记录
     */
//    @PreAuthorize("@ss.hasPermi('datasource:experimentalRecord:add')")
    @Log(title = "实验记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExperimentalRecord experimentalRecord) {
        return toAjax(experimentalRecordService.insertExperimentalRecord(experimentalRecord));
    }

    /**
     * 修改实验记录
     */
//    @PreAuthorize("@ss.hasPermi('datasource:experimentalRecord:edit')")
    @Log(title = "实验记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExperimentalRecord experimentalRecord) {
        return toAjax(experimentalRecordService.updateExperimentalRecord(experimentalRecord));
    }

    /**
     * 删除实验记录
     */
//    @PreAuthorize("@ss.hasPermi('datasource:experimentalRecord:remove')")
    @Log(title = "实验记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(experimentalRecordService.deleteExperimentalRecordByIds(ids));
    }

    /**
     * 清空实验记录
     */
//    @PreAuthorize("@ss.hasPermi('datasource:weight:remove')")
    @Log(title = "清空实验记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteExperimentalRecord")
    public AjaxResult deleteExperimentalRecord() {
        return toAjax(experimentalRecordService.deleteExperimentalRecord());
    }

    /**
     * 导入实验记录
     */
    @Log(title = "导入实验记录", businessType = BusinessType.IMPORT)
    @GetMapping("/importData")
    public AjaxResult importData(String path) throws Exception {
        experimentalRecordService.importData(path);
        return AjaxResult.success();
    }

    /**
     * 导入AfSim实验记录
     */
    @Log(title = "导入AfSim实验记录", businessType = BusinessType.IMPORT)
    @GetMapping("/importAfSimData")
    public AjaxResult importAfSimData(String path) throws Exception {
        experimentalRecordService.importAfSimData(path);
        return AjaxResult.success();
    }
}
