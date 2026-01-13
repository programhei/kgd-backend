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
import com.kgd.evaluate.domain.EvaluateScore;
import com.kgd.evaluate.service.IEvaluateScoreService;
import com.kgd.common.utils.poi.ExcelUtil;
import com.kgd.common.core.page.TableDataInfo;

/**
 * 评估分数记录Controller
 *
 * @author kgd
 * @date 2025-11-19
 */
@RestController
@RequestMapping("/evaluate/evaluateScore")
public class EvaluateScoreController extends BaseController
{
    @Autowired
    private IEvaluateScoreService evaluateScoreService;

    /**
     * 查询评估分数记录列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:evaluateScore:list')")
    @GetMapping("/list")
    public TableDataInfo list(EvaluateScore evaluateScore)
    {
        startPage();
        List<EvaluateScore> list = evaluateScoreService.selectEvaluateScoreList(evaluateScore);
        return getDataTable(list);
    }

    /**
     * 导出评估分数记录列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:evaluateScore:export')")
    @Log(title = "评估分数记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EvaluateScore evaluateScore)
    {
        List<EvaluateScore> list = evaluateScoreService.selectEvaluateScoreList(evaluateScore);
        ExcelUtil<EvaluateScore> util = new ExcelUtil<EvaluateScore>(EvaluateScore.class);
        util.exportExcel(response, list, "评估分数记录数据");
    }

    /**
     * 获取评估分数记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:evaluateScore:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(evaluateScoreService.selectEvaluateScoreById(id));
    }

    /**
     * 新增评估分数记录
     */
    @PreAuthorize("@ss.hasPermi('evaluate:evaluateScore:add')")
    @Log(title = "评估分数记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EvaluateScore evaluateScore)
    {
        return toAjax(evaluateScoreService.insertEvaluateScore(evaluateScore));
    }

    /**
     * 修改评估分数记录
     */
    @PreAuthorize("@ss.hasPermi('evaluate:evaluateScore:edit')")
    @Log(title = "评估分数记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EvaluateScore evaluateScore)
    {
        return toAjax(evaluateScoreService.updateEvaluateScore(evaluateScore));
    }

    /**
     * 删除评估分数记录
     */
    @PreAuthorize("@ss.hasPermi('evaluate:evaluateScore:remove')")
    @Log(title = "评估分数记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(evaluateScoreService.deleteEvaluateScoreByIds(ids));
    }
}
