package com.kgd.evaluate.domain.vo;


import com.kgd.common.annotation.Excel;
import lombok.Data;

@Data
public class MissileRangeCsvVo {
    @Excel(name = "导弹射程准确度")
    private String missileRange;
}
