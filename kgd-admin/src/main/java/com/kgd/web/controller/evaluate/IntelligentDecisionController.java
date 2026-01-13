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
import com.kgd.evaluate.domain.IntelligentDecision;
import com.kgd.evaluate.service.IIntelligentDecisionService;
import com.kgd.common.utils.poi.ExcelUtil;
import com.kgd.common.core.page.TableDataInfo;

/**
 * 支持智能决策模型生成与评估Controller
 * 
 * @author kgd
 * @date 2025-11-10
 */
@RestController
@RequestMapping("/evaluate/intelligentDecision")
public class IntelligentDecisionController extends BaseController
{
    @Autowired
    private IIntelligentDecisionService intelligentDecisionService;

    /**
     * 查询支持智能决策模型生成与评估列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:intelligentDecision:list')")
    @GetMapping("/list")
    public TableDataInfo list(IntelligentDecision intelligentDecision)
    {
        startPage();
        List<IntelligentDecision> list = intelligentDecisionService.selectIntelligentDecisionList(intelligentDecision);
        return getDataTable(list);
    }

    /**
     * 导出支持智能决策模型生成与评估列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:intelligentDecision:export')")
    @Log(title = "支持智能决策模型生成与评估", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, IntelligentDecision intelligentDecision)
    {
        List<IntelligentDecision> list = intelligentDecisionService.selectIntelligentDecisionList(intelligentDecision);
        ExcelUtil<IntelligentDecision> util = new ExcelUtil<IntelligentDecision>(IntelligentDecision.class);
        util.exportExcel(response, list, "支持智能决策模型生成与评估数据");
    }

    /**
     * 获取支持智能决策模型生成与评估详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:intelligentDecision:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(intelligentDecisionService.selectIntelligentDecisionById(id));
    }

    /**
     * 新增支持智能决策模型生成与评估
     */
    @PreAuthorize("@ss.hasPermi('evaluate:intelligentDecision:add')")
    @Log(title = "支持智能决策模型生成与评估", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IntelligentDecision intelligentDecision)
    {
        return toAjax(intelligentDecisionService.insertIntelligentDecision(intelligentDecision));
    }

    /**
     * 修改支持智能决策模型生成与评估
     */
    @PreAuthorize("@ss.hasPermi('evaluate:intelligentDecision:edit')")
    @Log(title = "支持智能决策模型生成与评估", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IntelligentDecision intelligentDecision)
    {
        return toAjax(intelligentDecisionService.updateIntelligentDecision(intelligentDecision));
    }

    /**
     * 删除支持智能决策模型生成与评估
     */
    @PreAuthorize("@ss.hasPermi('evaluate:intelligentDecision:remove')")
    @Log(title = "支持智能决策模型生成与评估", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(intelligentDecisionService.deleteIntelligentDecisionByIds(ids));
    }
}
