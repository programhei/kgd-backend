package com.kgd.web.controller.evaluate;


import com.kgd.common.annotation.Log;
import com.kgd.common.core.domain.AjaxResult;
import com.kgd.common.enums.BusinessType;
import com.kgd.evaluate.domain.IntelligentDecision;
import com.kgd.evaluate.domain.vo.CommonVo;
import com.kgd.evaluate.service.IEvaluateCommonService;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 通用Controller
 *
 * @author kgd
 * @date 2025-10-31
 */
@RestController
@RequestMapping("/evaluate/common")
public class EvaluateCommonController {
    @Resource
    private IEvaluateCommonService iEvaluateCommonService;

    /**
     * 导入体系功能
     */
    @Log(title = "导入体系功能", businessType = BusinessType.IMPORT)
    @GetMapping("/importSystemFunctions")
    public AjaxResult importSystemFunctions(CommonVo commonVo) throws Exception {
        try {
            iEvaluateCommonService.importSystemFunctions(commonVo);
            return AjaxResult.success("体系功能数据导入成功");
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 查询体系功能
     */
    @GetMapping("/listSystemFunctions")
    public AjaxResult listSystemFunctions(CommonVo commonVo) {
        return AjaxResult.success(iEvaluateCommonService.listSystemFunctions(commonVo));
    }

    /**
     * 删除体系功能
     */
    @Log(title = "删除体系功能", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteSystemFunctions/{dataId}")
    public AjaxResult deleteSystemFunctions(@PathVariable String dataId) {
        iEvaluateCommonService.deleteSystemFunctions(dataId);
        return AjaxResult.success();
    }

    /**
     * 评估体系功能
     */
    @Log(title = "评估体系功能", businessType = BusinessType.EVALUATE)
    @PostMapping("/evaluateSystemFunctions")
    public AjaxResult evaluateSystemFunctions(@RequestBody CommonVo commonVo) {
        return AjaxResult.success(iEvaluateCommonService.evaluateSystemFunctions(commonVo));
    }

    /**
     * 导入飞行系统性能准确度
     */
    @Log(title = "导入飞行系统性能准确度", businessType = BusinessType.IMPORT)
    @GetMapping("/importFlightPerformanceAccuracy")
    public AjaxResult importFlightPerformanceAccuracy(CommonVo commonVo) throws Exception {
        try {
            iEvaluateCommonService.importFlightPerformanceAccuracy(commonVo);
            return AjaxResult.success("飞行系统性能准确度数据导入成功");
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 导入雷达系统性能准确度
     */
    @Log(title = "导入雷达系统性能准确度", businessType = BusinessType.IMPORT)
    @GetMapping("/importRadarPerformanceAccuracy")
    public AjaxResult importRadarPerformanceAccuracy(CommonVo commonVo) throws Exception {
        try {
            iEvaluateCommonService.importRadarPerformanceAccuracy(commonVo);
            return AjaxResult.success("雷达系统性能准确度数据导入成功");
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 导入导弹系统性能准确度
     */
    @Log(title = "导入导弹系统性能准确度", businessType = BusinessType.IMPORT)
    @GetMapping("/importMissilePerformanceAccuracy")
    public AjaxResult importMissilePerformanceAccuracy(CommonVo commonVo) throws Exception {
        try {
            iEvaluateCommonService.importMissilePerformanceAccuracy(commonVo);
            return AjaxResult.success("导弹系统性能准确度度数据导入成功");
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 查询性能准确度
     */
    @GetMapping("/listFlightPerformanceAccuracy")
    public AjaxResult listFlightPerformanceAccuracy(CommonVo commonVo) {
        List<Map<String, Object>> maps = iEvaluateCommonService.listFlightPerformanceAccuracy(commonVo);
        return AjaxResult.success(maps);
    }

    /**
     * 查询性能准确度
     */
    @GetMapping("/listRadarPerformanceAccuracy")
    public AjaxResult listRadarPerformanceAccuracy(CommonVo commonVo) {
        List<Map<String, Object>> maps = iEvaluateCommonService.listRadarPerformanceAccuracy(commonVo);
        return AjaxResult.success(maps);
    }

    /**
     * 查询性能准确度
     */
    @GetMapping("/listMissilePerformanceAccuracy")
    public AjaxResult listMissilePerformanceAccuracy(CommonVo commonVo) {
        List<Map<String, Object>> maps = iEvaluateCommonService.listMissilePerformanceAccuracy(commonVo);
        return AjaxResult.success(maps);
    }

    /**
     * 删除性能准确度
     */
    @Log(title = "删除性能准确度", businessType = BusinessType.DELETE)
    @DeleteMapping("/deletePerformanceAccuracy/{dataId}")
    public AjaxResult deletePerformanceAccuracy(@PathVariable String dataId) {
        iEvaluateCommonService.deletePerformanceAccuracy(dataId);
        return AjaxResult.success();
    }

    /**
     * 评估性能准确度
     */
    @Log(title = "评估性能准确度", businessType = BusinessType.EVALUATE)
    @PostMapping("/evaluateFlightPerformanceAccuracy")
    public AjaxResult evaluateFlightPerformanceAccuracy(@RequestBody CommonVo commonVo) throws Exception {
        return AjaxResult.success(iEvaluateCommonService.evaluateFlightPerformanceAccuracy(commonVo));
    }

    /**
     * 评估性能准确度
     */
    @Log(title = "评估性能准确度", businessType = BusinessType.EVALUATE)
    @PostMapping("/evaluateRadarPerformanceAccuracy")
    public AjaxResult evaluateRadarPerformanceAccuracy(@RequestBody CommonVo commonVo) throws Exception {
        return AjaxResult.success(iEvaluateCommonService.evaluateRadarPerformanceAccuracy(commonVo));
    }

    /**
     * 评估性能准确度
     */
    @Log(title = "评估性能准确度", businessType = BusinessType.EVALUATE)
    @PostMapping("/evaluateMissilePerformanceAccuracy")
    public AjaxResult evaluateMissilePerformanceAccuracy(@RequestBody CommonVo commonVo) throws Exception {
        return AjaxResult.success(iEvaluateCommonService.evaluateMissilePerformanceAccuracy(commonVo));
    }

    /**
     * 导入想定合理性
     */
    @Log(title = "导入想定合理性", businessType = BusinessType.IMPORT)
    @GetMapping("/importReasonableAssumption")
    public AjaxResult importReasonableAssumption(CommonVo commonVo) {
        try {
            iEvaluateCommonService.importScenarioRationality(commonVo);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 查询想定合理性
     */
    //TODO 这里是点击加载数据的接口位置
    @GetMapping("/listReasonableAssumption")
    public AjaxResult listReasonableAssumption(CommonVo commonVo) {
        List<Map<String, Object>> maps = iEvaluateCommonService.listScenarioRationality(commonVo);
        return AjaxResult.success(maps);
    }

    /**
     * 删除想定合理性
     */
    @Log(title = "删除想定合理性", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteReasonableAssumption/{dataId}")
    public AjaxResult deleteReasonableAssumption(@PathVariable String dataId) {
        iEvaluateCommonService.deleteScenarioRationality(dataId);
        return AjaxResult.success();
    }

    /**
     * 评估想定合理性
     */
    @Log(title = "评估想定合理性", businessType = BusinessType.EVALUATE)
    @PostMapping("/evaluateScenarioRationality")
    //todo 这里是点击评估数据的接口位置
    public AjaxResult evaluateScenarioRationality(@RequestBody CommonVo commonVo) throws Exception {
        Map<String, Object> calculateTheRationalityScoreOfTheScenario = iEvaluateCommonService.calculateTheRationalityScoreOfTheScenario(commonVo);
        return AjaxResult.success(iEvaluateCommonService.evaluateScenarioRationality(commonVo));
    }

    /**
     * 导入软件质量
     */
    @Log(title = "导入软件质量", businessType = BusinessType.IMPORT)
    @GetMapping("/importSoftwareQuality")
    public AjaxResult importSoftwareQuality(CommonVo commonVo) throws Exception {
        try {
            iEvaluateCommonService.importSoftwareQuality(commonVo);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 查询软件质量
     */
    //todo 这里是点击加载数据的接口位置
    @GetMapping("/listSoftwareQuality")
    public AjaxResult listSoftwareQuality() {
        List<Map<String, Object>> maps = iEvaluateCommonService.listSoftwareQuality();
        return AjaxResult.success(maps);
    }

    /**
     * 删除软件质量
     */
    @Log(title = "删除软件质量", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteSoftwareQuality/{dataId}")
    public AjaxResult deleteSoftwareQuality(@PathVariable String dataId) {
        iEvaluateCommonService.deleteSoftwareQuality(dataId);
        return AjaxResult.success();
    }

    /**
     * 评估软件质量
     */
    //todo 这里是点击评估数据的接口位置
    @Log(title = "评估软件质量", businessType = BusinessType.EVALUATE)
    @PostMapping("/evaluateSoftwareQuality")
    public AjaxResult evaluateSoftwareQuality(@RequestBody CommonVo commonVo) throws Exception{
        Map<String, Object> calculateSoftwareQuality = iEvaluateCommonService.calculateSoftwareQuality(commonVo);
        return AjaxResult.success(iEvaluateCommonService.evaluateSoftwareQuality(commonVo));
    }

    /**
     * 导入支持智能决策模型生成与评估
     */
    @Log(title = "导入支持智能决策模型生成与评估", businessType = BusinessType.IMPORT)
    @GetMapping("/importIntelligentDecision")
    public AjaxResult importIntelligentDecision(CommonVo commonVo) throws Exception {
        try {
            iEvaluateCommonService.importIntelligentDecision(commonVo);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 查询支持智能决策模型生成与评估
     */
    @GetMapping("/listIntelligentDecision")
    public AjaxResult listIntelligentDecision() {
        return AjaxResult.success(iEvaluateCommonService.listIntelligentDecision());
    }

    /**
     * 查询支持智能决策模型生成与评估
     */
    @GetMapping("/listIntelligentDecisions")
    public AjaxResult listIntelligentDecisions() {
        return AjaxResult.success(iEvaluateCommonService.listIntelligentDecisions());
    }

    /**
     * 删除支持智能决策模型生成与评估
     */
    @Log(title = "删除支持智能决策模型生成与评估", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteIntelligentDecision/{dataId}")
    public AjaxResult deleteIntelligentDecision(@PathVariable String dataId) {
        iEvaluateCommonService.deleteIntelligentDecision(dataId);
        return AjaxResult.success();
    }

    /**
     * 评估支持智能决策模型生成与评估
     */
    @Log(title = "评估支持智能决策模型生成与评估", businessType = BusinessType.EVALUATE)
    @PostMapping("/evaluateIntelligentDecision")
    public AjaxResult evaluateIntelligentDecision(@RequestBody CommonVo commonVo) {
        return AjaxResult.success(iEvaluateCommonService.evaluateIntelligentDecision(commonVo.getIntelligentDecisionList()));
    }

    /**
     * 导入支持智能决策模型评估
     */
    @Log(title = "导入支持智能决策模型生成与评估-评估", businessType = BusinessType.IMPORT)
    @GetMapping("/importIntelligentDecisionEvaluate")
    public AjaxResult importIntelligentDecisionEvaluate(CommonVo commonVo) throws Exception {
        try {
            iEvaluateCommonService.importIntelligentDecisionEvaluate(commonVo);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 评估支持智能决策模型生成与评估-评估
     */
    @Log(title = "评估支持智能决策模型生成与评估-评估", businessType = BusinessType.EVALUATE)
    @PostMapping("/evaluateIntelligentDecisionEvaluate")
    public AjaxResult evaluateIntelligentDecisionEvaluate(@RequestBody CommonVo commonVo) {
        return AjaxResult.success(iEvaluateCommonService.evaluateIntelligentDecisionEvaluate(commonVo));
    }

    /**
     * 查询评分记录
     */
    @PostMapping("/listEvaluateScore")
    public AjaxResult listEvaluateScore() throws Exception {
        return AjaxResult.success(iEvaluateCommonService.listEvaluateScore());
    }

    /**
     * 总体评估
     */
    @Log(title = "总体评估", businessType = BusinessType.EVALUATE)
    @PostMapping("/overallEvaluation")
    public AjaxResult overallEvaluation() throws Exception {
        return AjaxResult.success(iEvaluateCommonService.overallEvaluation());
    }

    /**
     * 删除通用数据
     */
    @Log(title = "删除通用数据", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public AjaxResult delete(@RequestBody CommonVo commonVo) {
        if ("RJ".equals(commonVo.getExtra())) {
            iEvaluateCommonService.deleteSoftwareQuality(commonVo.getDataId());
        }
        return AjaxResult.success();
    }
}
