package com.kgd.datasource.domain.vo;

import com.kgd.common.annotation.Excel;
import com.kgd.common.core.domain.BaseEntity;


// 航向角指令控制

public class HeadingAngleExcelVo extends BaseEntity {
    // id
    @Excel(name = "id")
    private int id;

    // 时间（秒）
    @Excel(name = "时间")
    private String time;

    // 指令航向（60°）
    @Excel(name = "指令航向（60°）")
    private String commandHeading60;

    // 指令航向（120°）
    @Excel(name = "指令航向（120°）")
    private String commandHeading120;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCommandHeading60() {
        return commandHeading60;
    }

    public void setCommandHeading60(String commandHeading60) {
        this.commandHeading60 = commandHeading60;
    }

    public String getCommandHeading120() {
        return commandHeading120;
    }

    public void setCommandHeading120(String commandHeading120) {
        this.commandHeading120 = commandHeading120;
    }

    @Override
    public String toString() {
        return "HeadingAngleExcelVo{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", commandHeading60='" + commandHeading60 + '\'' +
                ", commandHeading120='" + commandHeading120 + '\'' +
                '}';
    }
}