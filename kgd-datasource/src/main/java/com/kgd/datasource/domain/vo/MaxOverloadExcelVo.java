package com.kgd.datasource.domain.vo;

import com.kgd.common.annotation.Excel;
import com.kgd.common.core.domain.BaseEntity;


// 最大过载

public class MaxOverloadExcelVo extends BaseEntity {
    // id
    @Excel(name = "id")
    private int id;

    // 时间
    @Excel(name = "时间")
    private String time;

    // 过载
    @Excel(name = "过载")
    private String overload;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOverload() {
        return overload;
    }

    public void setOverload(String overload) {
        this.overload = overload;
    }

    @Override
    public String toString() {
        return "MaxOverloadExcelVo{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", overload='" + overload + '\'' +
                '}';
    }
}