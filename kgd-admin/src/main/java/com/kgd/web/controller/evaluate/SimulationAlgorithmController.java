package com.kgd.web.controller.evaluate;

import com.kgd.common.annotation.Log;
import com.kgd.common.core.controller.BaseController;
import com.kgd.common.core.domain.AjaxResult;
import com.kgd.common.core.page.TableDataInfo;
import com.kgd.common.enums.BusinessType;
import com.kgd.evaluate.domain.SimulationAlgorithm;
import com.kgd.evaluate.service.ISimulationAlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 仿真系统性能准确度算法管理
 */
@RestController
@RequestMapping("/evaluate/algorithm")
public class SimulationAlgorithmController extends BaseController {

    @Autowired
    private ISimulationAlgorithmService simulationAlgorithmService;

    /**
     * 查询算法列表
     */
//    @PreAuthorize("@ss.hasPermi('evaluate:algorithm:list')")
    @GetMapping("/list")
    public TableDataInfo list(SimulationAlgorithm simulationAlgorithm) {
        startPage();
        List<SimulationAlgorithm> list = simulationAlgorithmService.selectSimulationAlgorithmList(simulationAlgorithm);
        return getDataTable(list);
    }

    /**
     * 获取算法详情
     */
//    @PreAuthorize("@ss.hasPermi('evaluate:algorithm:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(simulationAlgorithmService.selectSimulationAlgorithmById(id));
    }

    /**
     * 新增算法
     */
//    @PreAuthorize("@ss.hasPermi('evaluate:algorithm:add')")
    @Log(title = "算法管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SimulationAlgorithm simulationAlgorithm) {
        simulationAlgorithm.setCreateBy(getUsername());
        return toAjax(simulationAlgorithmService.insertSimulationAlgorithm(simulationAlgorithm));
    }

    /**
     * 修改算法
     */
//    @PreAuthorize("@ss.hasPermi('evaluate:algorithm:edit')")
    @Log(title = "算法管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SimulationAlgorithm simulationAlgorithm) {
        simulationAlgorithm.setUpdateBy(getUsername());
        return toAjax(simulationAlgorithmService.updateSimulationAlgorithm(simulationAlgorithm));
    }

    /**
     * 删除算法
     */
//    @PreAuthorize("@ss.hasPermi('evaluate:algorithm:remove')")
    @Log(title = "算法管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(simulationAlgorithmService.deleteSimulationAlgorithmByIds(ids));
    }
}

