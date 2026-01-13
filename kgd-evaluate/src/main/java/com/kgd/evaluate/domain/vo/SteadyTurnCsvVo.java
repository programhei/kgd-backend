package com.kgd.evaluate.domain.vo;

import com.kgd.common.annotation.Excel;
import lombok.Data;


@Data
public class SteadyTurnCsvVo {

    /**
     * 高度
     */
    @Excel(name = "高度")
    private String height;

    /**
     * 航向角
     */
    @Excel(name = "航向角")
    private String headingAngle;

    /**
     * 过载
     */
    @Excel(name = "过载")
    private String overload;
    /**
     * 速度
     */
    @Excel(name = "速度")
    private String flightSpeed;
}
