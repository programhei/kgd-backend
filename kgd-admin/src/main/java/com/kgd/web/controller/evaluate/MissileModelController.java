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
import com.kgd.evaluate.domain.MissileModel;
import com.kgd.evaluate.service.IMissileModelService;
import com.kgd.common.utils.poi.ExcelUtil;
import com.kgd.common.core.page.TableDataInfo;

/**
 * 导弹模型Controller
 * 
 * @author ruoyi
 * @date 2025-10-31
 */
@RestController
@RequestMapping("/evaluate/missileModel")
public class MissileModelController extends BaseController
{
    @Autowired
    private IMissileModelService missileModelService;

    /**
     * 查询导弹模型列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:missileModel:list')")
    @GetMapping("/list")
    public TableDataInfo list(MissileModel missileModel)
    {
        startPage();
        List<MissileModel> list = missileModelService.selectMissileModelList(missileModel);
        return getDataTable(list);
    }

    /**
     * 导出导弹模型列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:missileModel:export')")
    @Log(title = "导弹模型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MissileModel missileModel)
    {
        List<MissileModel> list = missileModelService.selectMissileModelList(missileModel);
        ExcelUtil<MissileModel> util = new ExcelUtil<MissileModel>(MissileModel.class);
        util.exportExcel(response, list, "导弹模型数据");
    }

    /**
     * 获取导弹模型详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:missileModel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(missileModelService.selectMissileModelById(id));
    }

    /**
     * 新增导弹模型
     */
    @PreAuthorize("@ss.hasPermi('evaluate:missileModel:add')")
    @Log(title = "导弹模型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MissileModel missileModel)
    {
        return toAjax(missileModelService.insertMissileModel(missileModel));
    }

    /**
     * 修改导弹模型
     */
    @PreAuthorize("@ss.hasPermi('evaluate:missileModel:edit')")
    @Log(title = "导弹模型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MissileModel missileModel)
    {
        return toAjax(missileModelService.updateMissileModel(missileModel));
    }

    /**
     * 删除导弹模型
     */
    @PreAuthorize("@ss.hasPermi('evaluate:missileModel:remove')")
    @Log(title = "导弹模型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(missileModelService.deleteMissileModelByIds(ids));
    }
}
