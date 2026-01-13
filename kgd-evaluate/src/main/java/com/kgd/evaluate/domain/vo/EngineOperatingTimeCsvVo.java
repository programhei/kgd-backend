package com.kgd.evaluate.domain.vo;


import com.kgd.common.annotation.Excel;
import lombok.Data;

@Data
public class EngineOperatingTimeCsvVo {
    @Excel(name = "发动机工作时间准确度")
    private String engineOperatingTime;
}
