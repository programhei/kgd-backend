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
import com.kgd.evaluate.domain.FlightModel;
import com.kgd.evaluate.service.IFlightModelService;
import com.kgd.common.utils.poi.ExcelUtil;
import com.kgd.common.core.page.TableDataInfo;

/**
 * 飞行模型性能配置Controller
 * 
 * @author kgd
 * @date 2025-10-31
 */
@RestController
@RequestMapping("/evaluate/FlightModel")
public class FlightModelController extends BaseController
{
    @Autowired
    private IFlightModelService flightModelService;

    /**
     * 查询飞行模型性能配置列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:FlightModel:list')")
    @GetMapping("/list")
    public TableDataInfo list(FlightModel flightModel)
    {
        startPage();
        List<FlightModel> list = flightModelService.selectFlightModelList(flightModel);
        return getDataTable(list);
    }

    /**
     * 导出飞行模型性能配置列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:FlightModel:export')")
    @Log(title = "飞行模型性能配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FlightModel flightModel)
    {
        List<FlightModel> list = flightModelService.selectFlightModelList(flightModel);
        ExcelUtil<FlightModel> util = new ExcelUtil<FlightModel>(FlightModel.class);
        util.exportExcel(response, list, "飞行模型性能配置数据");
    }

    /**
     * 获取飞行模型性能配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:FlightModel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(flightModelService.selectFlightModelById(id));
    }

    /**
     * 新增飞行模型性能配置
     */
    @PreAuthorize("@ss.hasPermi('evaluate:FlightModel:add')")
    @Log(title = "飞行模型性能配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FlightModel flightModel)
    {
        return toAjax(flightModelService.insertFlightModel(flightModel));
    }

    /**
     * 修改飞行模型性能配置
     */
    @PreAuthorize("@ss.hasPermi('evaluate:FlightModel:edit')")
    @Log(title = "飞行模型性能配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FlightModel flightModel)
    {
        return toAjax(flightModelService.updateFlightModel(flightModel));
    }

    /**
     * 删除飞行模型性能配置
     */
    @PreAuthorize("@ss.hasPermi('evaluate:FlightModel:remove')")
    @Log(title = "飞行模型性能配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(flightModelService.deleteFlightModelByIds(ids));
    }
}
