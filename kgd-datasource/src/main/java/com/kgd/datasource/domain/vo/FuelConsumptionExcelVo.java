package com.kgd.datasource.domain.vo;

import com.kgd.common.annotation.Excel;
import com.kgd.common.core.domain.BaseEntity;


// 耗油率

public class FuelConsumptionExcelVo extends BaseEntity {

    // id
    @Excel(name = "id")
    private int id;

    // 时间
    @Excel(name = "时间")
    private String time;

    // 耗油率（6000米）
    @Excel(name = "耗油率（6000米）")
    private String fuelConsumption6000;

    // 耗油率（8000米）
    @Excel(name = "耗油率（8000米）")
    private String fuelConsumption8000;

    // 耗油率（10000米）
    @Excel(name = "耗油率（10000米）")
    private String fuelConsumption10000;

    // 耗油率（12000米）
    @Excel(name = "耗油率（12000米）")
    private String fuelConsumption12000;

    // 耗油率（14000米）
    @Excel(name = "耗油率（14000米）")
    private String fuelConsumption14000;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFuelConsumption6000() {
        return fuelConsumption6000;
    }

    public void setFuelConsumption6000(String fuelConsumption6000) {
        this.fuelConsumption6000 = fuelConsumption6000;
    }

    public String getFuelConsumption8000() {
        return fuelConsumption8000;
    }

    public void setFuelConsumption8000(String fuelConsumption8000) {
        this.fuelConsumption8000 = fuelConsumption8000;
    }

    public String getFuelConsumption10000() {
        return fuelConsumption10000;
    }

    public void setFuelConsumption10000(String fuelConsumption10000) {
        this.fuelConsumption10000 = fuelConsumption10000;
    }

    public String getFuelConsumption12000() {
        return fuelConsumption12000;
    }

    public void setFuelConsumption12000(String fuelConsumption12000) {
        this.fuelConsumption12000 = fuelConsumption12000;
    }

    public String getFuelConsumption14000() {
        return fuelConsumption14000;
    }

    public void setFuelConsumption14000(String fuelConsumption14000) {
        this.fuelConsumption14000 = fuelConsumption14000;
    }

    @Override
    public String toString() {
        return "FuelConsumptionExcelVo{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", fuelConsumption6000='" + fuelConsumption6000 + '\'' +
                ", fuelConsumption8000='" + fuelConsumption8000 + '\'' +
                ", fuelConsumption10000='" + fuelConsumption10000 + '\'' +
                ", fuelConsumption12000='" + fuelConsumption12000 + '\'' +
                ", fuelConsumption14000='" + fuelConsumption14000 + '\'' +
                '}';
    }
}