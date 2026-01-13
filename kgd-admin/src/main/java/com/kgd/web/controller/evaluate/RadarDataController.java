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
import com.kgd.evaluate.domain.RadarData;
import com.kgd.evaluate.service.IRadarDataService;
import com.kgd.common.utils.poi.ExcelUtil;
import com.kgd.common.core.page.TableDataInfo;

/**
 * 雷达数据Controller
 * 
 * @author ruoyi
 * @date 2025-10-31
 */
@RestController
@RequestMapping("/evaluate/radarData")
public class RadarDataController extends BaseController
{
    @Autowired
    private IRadarDataService radarDataService;

    /**
     * 查询雷达数据列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:radarData:list')")
    @GetMapping("/list")
    public TableDataInfo list(RadarData radarData)
    {
        startPage();
        List<RadarData> list = radarDataService.selectRadarDataList(radarData);
        return getDataTable(list);
    }

    /**
     * 导出雷达数据列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:radarData:export')")
    @Log(title = "雷达数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RadarData radarData)
    {
        List<RadarData> list = radarDataService.selectRadarDataList(radarData);
        ExcelUtil<RadarData> util = new ExcelUtil<RadarData>(RadarData.class);
        util.exportExcel(response, list, "雷达数据数据");
    }

    /**
     * 获取雷达数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:radarData:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(radarDataService.selectRadarDataById(id));
    }

    /**
     * 新增雷达数据
     */
    @PreAuthorize("@ss.hasPermi('evaluate:radarData:add')")
    @Log(title = "雷达数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RadarData radarData)
    {
        return toAjax(radarDataService.insertRadarData(radarData));
    }

    /**
     * 修改雷达数据
     */
    @PreAuthorize("@ss.hasPermi('evaluate:radarData:edit')")
    @Log(title = "雷达数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RadarData radarData)
    {
        return toAjax(radarDataService.updateRadarData(radarData));
    }

    /**
     * 删除雷达数据
     */
    @PreAuthorize("@ss.hasPermi('evaluate:radarData:remove')")
    @Log(title = "雷达数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(radarDataService.deleteRadarDataByIds(ids));
    }
}
