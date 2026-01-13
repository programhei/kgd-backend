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
import com.kgd.evaluate.domain.RadarModel;
import com.kgd.evaluate.service.IRadarModelService;
import com.kgd.common.utils.poi.ExcelUtil;
import com.kgd.common.core.page.TableDataInfo;

/**
 * 雷达模型Controller
 * 
 * @author ruoyi
 * @date 2025-10-31
 */
@RestController
@RequestMapping("/evaluate/radarModel")
public class RadarModelController extends BaseController
{
    @Autowired
    private IRadarModelService radarModelService;

    /**
     * 查询雷达模型列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:radarModel:list')")
    @GetMapping("/list")
    public TableDataInfo list(RadarModel radarModel)
    {
        startPage();
        List<RadarModel> list = radarModelService.selectRadarModelList(radarModel);
        return getDataTable(list);
    }

    /**
     * 导出雷达模型列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:radarModel:export')")
    @Log(title = "雷达模型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RadarModel radarModel)
    {
        List<RadarModel> list = radarModelService.selectRadarModelList(radarModel);
        ExcelUtil<RadarModel> util = new ExcelUtil<RadarModel>(RadarModel.class);
        util.exportExcel(response, list, "雷达模型数据");
    }

    /**
     * 获取雷达模型详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:radarModel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(radarModelService.selectRadarModelById(id));
    }

    /**
     * 新增雷达模型
     */
    @PreAuthorize("@ss.hasPermi('evaluate:radarModel:add')")
    @Log(title = "雷达模型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RadarModel radarModel)
    {
        return toAjax(radarModelService.insertRadarModel(radarModel));
    }

    /**
     * 修改雷达模型
     */
    @PreAuthorize("@ss.hasPermi('evaluate:radarModel:edit')")
    @Log(title = "雷达模型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RadarModel radarModel)
    {
        return toAjax(radarModelService.updateRadarModel(radarModel));
    }

    /**
     * 删除雷达模型
     */
    @PreAuthorize("@ss.hasPermi('evaluate:radarModel:remove')")
    @Log(title = "雷达模型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(radarModelService.deleteRadarModelByIds(ids));
    }
}
