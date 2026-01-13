package com.kgd.datasource.domain.vo;

import com.kgd.common.annotation.Excel;
import com.kgd.common.core.domain.BaseEntity;


// 速度指令控制

public class SpeedCommandExcelVo extends BaseEntity {
    // id
    @Excel(name = "id")
    private int id;

    // 时间
    @Excel(name = "时间")
    private String time;

    // 速度
    @Excel(name = "速度")
    private String speed;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "SpeedCommandExcelVo{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", speed='" + speed + '\'' +
                '}';
    }
}