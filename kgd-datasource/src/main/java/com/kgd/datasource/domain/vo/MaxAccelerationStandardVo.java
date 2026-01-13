package com.kgd.datasource.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kgd.common.annotation.Excel;

/**
 * 最大加速度（标准）Excel 导入导出对象
 */
public class MaxAccelerationStandardVo {

    // 主键
    private int id;

    // 时间（秒）
    @Excel(name = "时间")
    private Double time;

    // 不同高度下的最大加速度
    @Excel(name = "6000米")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "0.#######")
    private Double height6000;

    @Excel(name = "8000米")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "0.#######")
    private Double height8000;

    @Excel(name = "10000米")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "0.#######")
    private Double height10000;

    @Excel(name = "12000米")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "0.#######")
    private Double height12000;

    @Excel(name = "14000米")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, pattern = "0.#######")
    private Double height14000;

    /**
     * 算法类型（0最大速度1最大加速度）
     */
    private int evaluateType;

    /**
     * 标准或者测试（0标准1测试）
     */
    private int speedOrTest;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Double getHeight6000() {
        return height6000;
    }

    public void setHeight6000(Double height6000) {
        this.height6000 = height6000;
    }

    public Double getHeight8000() {
        return height8000;
    }

    public void setHeight8000(Double height8000) {
        this.height8000 = height8000;
    }

    public Double getHeight10000() {
        return height10000;
    }

    public void setHeight10000(Double height10000) {
        this.height10000 = height10000;
    }

    public Double getHeight12000() {
        return height12000;
    }

    public void setHeight12000(Double height12000) {
        this.height12000 = height12000;
    }

    public Double getHeight14000() {
        return height14000;
    }

    public void setHeight14000(Double height14000) {
        this.height14000 = height14000;
    }

    public int getEvaluateType() {
        return evaluateType;
    }

    public void setEvaluateType(int evaluateType) {
        this.evaluateType = evaluateType;
    }

    public int getSpeedOrTest() {
        return speedOrTest;
    }

    public void setSpeedOrTest(int speedOrTest) {
        this.speedOrTest = speedOrTest;
    }

    @Override
    public String toString() {
        return "MaxAccelerationStandardVo{" +
                "id=" + id +
                ", time=" + time +
                ", height6000=" + height6000 +
                ", height8000=" + height8000 +
                ", height10000=" + height10000 +
                ", height12000=" + height12000 +
                ", height14000=" + height14000 +
                '}';
    }
}

