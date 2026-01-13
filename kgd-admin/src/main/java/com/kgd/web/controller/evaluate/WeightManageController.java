package com.kgd.web.controller.evaluate;


import com.kgd.common.annotation.Log;
import com.kgd.common.core.domain.AjaxResult;
import com.kgd.common.enums.BusinessType;
import com.kgd.evaluate.domain.WeightMangeData;
import com.kgd.evaluate.service.IWeightManageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 权值Controller
 *
 * @author kgd
 * @date 2025-12-02
 */
@RestController
@RequestMapping("/evaluate/weightManage")
public class WeightManageController {

    @Resource
    private IWeightManageService iWeightManageService;

    /**
     * 查询权值
     */
    @PostMapping("/selectWeightTree")
//    public AjaxResult selectWeightTree( WeightMangeData weightMangeData) {
    public AjaxResult selectWeightTree(@RequestParam(value = "type", required = false)Integer type,  @RequestParam(value = "groupId", required = false)Integer groupId) {
        // 如果 type 为 null，设置默认值为 0
        if (type == null) {
            type = 0;
        }
        Map<String, Object> maps = iWeightManageService.selectWeightTree(type,groupId);
        return AjaxResult.success(maps);
    }

    /**
     * 保存权值
     */
    @Log(title = "保存权值", businessType = BusinessType.EVALUATE)
    @PostMapping("/saveWeight")
    public AjaxResult saveWeight( WeightMangeData weightMangeData) throws Exception {
        iWeightManageService.saveWeight(weightMangeData);
        return AjaxResult.success();
    }
    /**
     * 查询权值
     */
    @PostMapping("/selectTotalScore")
    public AjaxResult selectTotalScore(WeightMangeData weightMangeData) {
        Map<String, Object> maps = iWeightManageService.selectTotalScore(weightMangeData);
        return AjaxResult.success(maps);
    }


    /**
     * 统计主观权值与综合权值
     */
    @PostMapping("/countAvgWeightWithType")
    public AjaxResult countAvgWeightWithType() {
        List<WeightMangeData> maps = iWeightManageService.countAvgWeightWithType();
        return AjaxResult.success(maps);
    }


    /**
     * 查询专家
     */
    @PostMapping("/selectUserIds")
    public AjaxResult selectUserIds() {
        List<Long> maps = iWeightManageService.selectUserIds();
        return AjaxResult.success(maps);
    }
}
