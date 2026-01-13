package com.kgd.evaluate.domain.vo;

import com.kgd.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * 本机运动状态实体
 * 列序与 CSV 完全一致
 */
@Data
public class GunInterFaceVo implements Serializable {




    /**
     * 射程
     */
    @Excel(name = "射程")
    private String range;

    /**
     * 范围
     */
    @Excel(name = "发射前置角")
    private String area;

    /**
     * 射速
     */
    @Excel(name = "射速")
    private String firingRate;
    /**
     * 射速
     */
    @Excel(name = "剩余弹药")
    private String ammunition;

//    /**
//     * 武器发射
//     */
//    @Excel(name = "武器发射")
//    private String weapon;
//    /**
//     * 航炮发射
//     */
//    @Excel(name = "航炮发射")
//    private String hangpao;

    /**
     * 武器系统
     */
    public Object getGunColumnValue(String columnName) {
        switch (columnName) {
            case "射程":
                return range;
            case "发射前置角":
                return area;
            case "射速":
                return firingRate;
            case "剩余弹药":
                return ammunition;
//            case "武器发射":
//                return weapon;
//            case "航炮发射":
//                 return hangpao;
            /* ---------- 后续继续往下追加即可 ---------- */
            default:
                throw new IllegalArgumentException("未知列名: " + columnName);
        }
    }

    /**
     * 武器系统
     */
    public Object getWeaponControlColumnValue(String columnName) {
        switch (columnName) {
//            case "武器发射":
//                return weapon;
//            case "航炮发射":
//                return hangpao;
            /* ---------- 后续继续往下追加即可 ---------- */
            default:
                throw new IllegalArgumentException("未知列名: " + columnName);
        }
    }
}
