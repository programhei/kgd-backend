package com.kgd.datasource.domain.vo;

import com.kgd.common.annotation.Excel;
import com.kgd.common.core.domain.BaseEntity;


// 滚转角指令控制

public class RollAngleExcelVo extends BaseEntity {
    // id
    @Excel(name = "id")
    private int id;

    // 时间（秒）
    @Excel(name = "时间（秒）")
    private String time;

    // 俯仰角（30°滚转）
    @Excel(name = "俯仰角（30°滚转）")
    private String pitchAngle30;

    // 俯仰角（60°滚转）
    @Excel(name = "俯仰角（60°滚转）")
    private String pitchAngle60;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPitchAngle30() {
        return pitchAngle30;
    }

    public void setPitchAngle30(String pitchAngle30) {
        this.pitchAngle30 = pitchAngle30;
    }

    public String getPitchAngle60() {
        return pitchAngle60;
    }

    public void setPitchAngle60(String pitchAngle60) {
        this.pitchAngle60 = pitchAngle60;
    }

    @Override
    public String toString() {
        return "RollAngleExcelVo{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", pitchAngle30='" + pitchAngle30 + '\'' +
                ", pitchAngle60='" + pitchAngle60 + '\'' +
                '}';
    }
}