package com.kgd.evaluate.domain.vo;


import com.kgd.common.annotation.Excel;
import lombok.Data;

@Data
public class SpecificFuelConsumptionCsvVo {
    @Excel(name = "发动机耗油率")
    private String specificFuelConsumption;
    /**
     * 时间
     */
    @Excel(name = "时间")
    private String flightTime;
}
