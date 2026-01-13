package com.kgd.evaluate.domain.vo;


import com.kgd.common.annotation.Excel;
import lombok.Data;

@Data
public class MaxTimeCsvVo {
    @Excel(name = "最大跟踪外推时间")
    private String maxTime;
}
