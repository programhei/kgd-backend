package com.kgd.evaluate.domain.vo;

import com.kgd.common.annotation.Excel;
import lombok.Data;


@Data
public class LevelFlightCsvVo {

    /**
     * 时间
     */
    @Excel(name = "时间")
    private String flightTime;

    /**
     * 速度
     */
    @Excel(name = "速度")
    private String flightSpeed;

    /**
     * 经度
     */
    @Excel(name = "经度")
    private String longitude;

    /**
     * 纬度
     */
    @Excel(name = "纬度")
    private String latitude;

    /**
     * 高度
     */
    @Excel(name = "高度")
    private String height;


    @Excel(name = "油耗")
    private String fuelConsumption;
}
