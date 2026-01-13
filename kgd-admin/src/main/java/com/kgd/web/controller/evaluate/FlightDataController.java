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
import com.kgd.evaluate.domain.FlightData;
import com.kgd.evaluate.service.IFlightDataService;
import com.kgd.common.utils.poi.ExcelUtil;
import com.kgd.common.core.page.TableDataInfo;

/**
 * 飞行数据Controller
 *
 * @author kgd
 * @date 2025-10-31
 */
@RestController
@RequestMapping("/evaluate/flightData")
public class FlightDataController extends BaseController {
    @Autowired
    private IFlightDataService flightDataService;

    /**
     * 查询飞行数据列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:flightData:list')")
    @GetMapping("/list")
    public TableDataInfo list(FlightData flightData) {
        startPage();
        List<FlightData> list = flightDataService.selectFlightDataList(flightData);
        return getDataTable(list);
    }

    /**
     * 导出飞行数据列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:flightData:export')")
    @Log(title = "飞行数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FlightData flightData) {
        List<FlightData> list = flightDataService.selectFlightDataList(flightData);
        ExcelUtil<FlightData> util = new ExcelUtil<FlightData>(FlightData.class);
        util.exportExcel(response, list, "飞行数据数据");
    }

    /**
     * 获取飞行数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:flightData:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(flightDataService.selectFlightDataById(id));
    }

    /**
     * 新增飞行数据
     */
    @PreAuthorize("@ss.hasPermi('evaluate:flightData:add')")
    @Log(title = "飞行数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FlightData flightData) {
        return toAjax(flightDataService.insertFlightData(flightData));
    }

    /**
     * 修改飞行数据
     */
    @PreAuthorize("@ss.hasPermi('evaluate:flightData:edit')")
    @Log(title = "飞行数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FlightData flightData) {
        return toAjax(flightDataService.updateFlightData(flightData));
    }

    /**
     * 删除飞行数据
     */
    @PreAuthorize("@ss.hasPermi('evaluate:flightData:remove')")
    @Log(title = "飞行数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(flightDataService.deleteFlightDataByIds(ids));
    }
}
