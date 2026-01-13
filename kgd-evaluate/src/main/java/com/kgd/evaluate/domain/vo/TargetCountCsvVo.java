package com.kgd.evaluate.domain.vo;


import com.kgd.common.annotation.Excel;
import lombok.Data;

@Data
public class TargetCountCsvVo {


    @Excel(name = "最大跟踪目标数量")
    private String targetCount;
}
