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
import com.kgd.evaluate.domain.MissileData;
import com.kgd.evaluate.service.IMissileDataService;
import com.kgd.common.utils.poi.ExcelUtil;
import com.kgd.common.core.page.TableDataInfo;

/**
 * 导弹数据Controller
 * 
 * @author ruoyi
 * @date 2025-10-31
 */
@RestController
@RequestMapping("/evaluate/missileData")
public class MissileDataController extends BaseController
{
    @Autowired
    private IMissileDataService missileDataService;

    /**
     * 查询导弹数据列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:missileData:list')")
    @GetMapping("/list")
    public TableDataInfo list(MissileData missileData)
    {
        startPage();
        List<MissileData> list = missileDataService.selectMissileDataList(missileData);
        return getDataTable(list);
    }

    /**
     * 导出导弹数据列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:missileData:export')")
    @Log(title = "导弹数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MissileData missileData)
    {
        List<MissileData> list = missileDataService.selectMissileDataList(missileData);
        ExcelUtil<MissileData> util = new ExcelUtil<MissileData>(MissileData.class);
        util.exportExcel(response, list, "导弹数据数据");
    }

    /**
     * 获取导弹数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:missileData:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(missileDataService.selectMissileDataById(id));
    }

    /**
     * 新增导弹数据
     */
    @PreAuthorize("@ss.hasPermi('evaluate:missileData:add')")
    @Log(title = "导弹数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MissileData missileData)
    {
        return toAjax(missileDataService.insertMissileData(missileData));
    }

    /**
     * 修改导弹数据
     */
    @PreAuthorize("@ss.hasPermi('evaluate:missileData:edit')")
    @Log(title = "导弹数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MissileData missileData)
    {
        return toAjax(missileDataService.updateMissileData(missileData));
    }

    /**
     * 删除导弹数据
     */
    @PreAuthorize("@ss.hasPermi('evaluate:missileData:remove')")
    @Log(title = "导弹数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(missileDataService.deleteMissileDataByIds(ids));
    }
}
