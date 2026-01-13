package com.kgd.datasource.domain.vo;

import com.kgd.common.annotation.Excel;

/**
 * 弹道轨迹数据对象
 *
 * @author kgd
 * @date 2025-01-20
 */
public class MissileTrajectoryVo {

    // id
    private int id;
    /**
     * 时间（秒）
     */
    @Excel(name = "时间")
    private Double time;

    /**
     * 经度
     */
    @Excel(name = "经度")
    private Double longitude;

    /**
     * 纬度
     */
    @Excel(name = "纬度")
    private Double latitude;

    /**
     * 高度（米）
     */
    @Excel(name = "高度")
    private Double altitude;

    private int flag;

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
   public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MissileTrajectoryVo{" +
                "time=" + time +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", altitude=" + altitude +
                '}';
    }
}

