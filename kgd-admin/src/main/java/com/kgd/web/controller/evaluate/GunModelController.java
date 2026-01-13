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
import com.kgd.evaluate.domain.GunModel;
import com.kgd.evaluate.service.IGunModelService;
import com.kgd.common.utils.poi.ExcelUtil;
import com.kgd.common.core.page.TableDataInfo;

/**
 * 航炮Controller
 * 
 * @author kgd
 * @date 2025-11-05
 */
@RestController
@RequestMapping("/evaluate/gunModel")
public class GunModelController extends BaseController
{
    @Autowired
    private IGunModelService gunModelService;

    /**
     * 查询航炮列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:gunModel:list')")
    @GetMapping("/list")
    public TableDataInfo list(GunModel gunModel)
    {
        startPage();
        List<GunModel> list = gunModelService.selectGunModelList(gunModel);
        return getDataTable(list);
    }

    /**
     * 导出航炮列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:gunModel:export')")
    @Log(title = "航炮", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GunModel gunModel)
    {
        List<GunModel> list = gunModelService.selectGunModelList(gunModel);
        ExcelUtil<GunModel> util = new ExcelUtil<GunModel>(GunModel.class);
        util.exportExcel(response, list, "航炮数据");
    }

    /**
     * 获取航炮详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:gunModel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(gunModelService.selectGunModelById(id));
    }

    /**
     * 新增航炮
     */
    @PreAuthorize("@ss.hasPermi('evaluate:gunModel:add')")
    @Log(title = "航炮", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GunModel gunModel)
    {
        return toAjax(gunModelService.insertGunModel(gunModel));
    }

    /**
     * 修改航炮
     */
    @PreAuthorize("@ss.hasPermi('evaluate:gunModel:edit')")
    @Log(title = "航炮", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GunModel gunModel)
    {
        return toAjax(gunModelService.updateGunModel(gunModel));
    }

    /**
     * 删除航炮
     */
    @PreAuthorize("@ss.hasPermi('evaluate:gunModel:remove')")
    @Log(title = "航炮", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gunModelService.deleteGunModelByIds(ids));
    }
}
