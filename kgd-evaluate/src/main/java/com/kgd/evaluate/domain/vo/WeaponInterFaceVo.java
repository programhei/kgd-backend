package com.kgd.evaluate.domain.vo;

import com.kgd.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * 本机运动状态实体
 * 列序与 CSV 完全一致
 */
@Data
public class WeaponInterFaceVo implements Serializable {


    /**
     * 武器发射
     */
    @Excel(name = "武器发射")
    private String weapon;
    /**
     * 航炮发射
     */
    @Excel(name = "航炮发射")
    private String hangpao;


    /**
     * 武器系统
     */
    public Object getWeaponControlColumnValue(String columnName) {
        switch (columnName) {
            case "武器发射":
                return weapon;
            case "航炮发射":
                return hangpao;
            /* ---------- 后续继续往下追加即可 ---------- */
            default:
                throw new IllegalArgumentException("未知列名: " + columnName);
        }
    }
}
