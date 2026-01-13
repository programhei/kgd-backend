package com.kgd.evaluate.domain.vo;


import com.kgd.common.annotation.Excel;
import lombok.Data;

@Data
public class TrajectoryAccuracyCsvVo {
    @Excel(name = "弹道准确度")
    private String trajectoryAccuracy;
}
