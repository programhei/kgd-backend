package com.kgd.evaluate.domain.vo;


import com.kgd.common.annotation.Excel;
import lombok.Data;

@Data
public class PerformanceExcelVo {

    /**
     * 维度，如：性能、油耗、稳盘 …
     */
    @Excel(name = "维度")
    private String dimension;

    /**
     * 类型，如：巡航、爬升、下降 …
     */
    @Excel(name = "类型")
    private String dataType;

    /**
     * 变量名，如：速度、耗油率、坡度 …
     */
    @Excel(name = "变量")
    private String varName;

    /**
     * 变量值，统一用字符串，可再 parse 成 Double/Integer …
     */
    @Excel(name = "变量值")
    private String varValue;

    /**
     * 备注，如：单位、计算条件、数据来源 …
     */
    @Excel(name = "备注")
    private String remark;


}
