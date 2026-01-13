package com.kgd.datasource.domain.vo;

import com.kgd.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CsvVo {

    /**
     * 仿真得分
     */
    @Excel(name = "仿真得分")
    private String score;

    /**
     * 击中数量
     */
    @Excel(name = "击中数量")
    private String hitCount;

    /**
     * 剩余弹数
     */
    @Excel(name = "剩余弹数")
    private String remainingAmmo;

    /**
     * 剩余油量
     */
    @Excel(name = "剩余油量")
    private String remainingFuel;

    /**
     * 平台ID
     */
    @Excel(name = "平台ID")
    private String platformId;

    /**
     * 总弹数
     */
    @Excel(name = "总弹数")
    private String totalAmmo;

    /**
     * 总油量
     */
    @Excel(name = "总油量")
    private String totalFuel;

    /**
     * 打偏数量
     */
    @Excel(name = "打偏数量")
    private String missCount;

    /**
     * 摧毁状态 0-未摧毁 1-已摧毁
     */
    @Excel(name = "摧毁状态", readConverterExp = "0=未摧毁,1=已摧毁")
    private String destroyStatus;

    /**
     * 规避数量
     */
    @Excel(name = "规避数量")
    private String evadeCount;

    /**
     * 角色
     */
    @Excel(name = "角色")
    private String role;


    public void setScore(String score) {
        this.score = score;
    }

    public String getScore() {
        return score;
    }

    public String getHitCount() {
        return hitCount;
    }

    public void setHitCount(String hitCount) {
        this.hitCount = hitCount;
    }

    public String getRemainingAmmo() {
        return remainingAmmo;
    }

    public void setRemainingAmmo(String remainingAmmo) {
        this.remainingAmmo = remainingAmmo;
    }

    public String getRemainingFuel() {
        return remainingFuel;
    }

    public void setRemainingFuel(String remainingFuel) {
        this.remainingFuel = remainingFuel;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getTotalAmmo() {
        return totalAmmo;
    }

    public void setTotalAmmo(String totalAmmo) {
        this.totalAmmo = totalAmmo;
    }

    public String getTotalFuel() {
        return totalFuel;
    }

    public void setTotalFuel(String totalFuel) {
        this.totalFuel = totalFuel;
    }

    public String getMissCount() {
        return missCount;
    }

    public void setMissCount(String missCount) {
        this.missCount = missCount;
    }

    public String getDestroyStatus() {
        return destroyStatus;
    }

    public void setDestroyStatus(String destroyStatus) {
        this.destroyStatus = destroyStatus;
    }

    public String getEvadeCount() {
        return evadeCount;
    }

    public void setEvadeCount(String evadeCount) {
        this.evadeCount = evadeCount;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("score", getScore())
                .append("hitCount", getHitCount())
                .append("remainingAmmo", getRemainingAmmo())
                .append("remainingFuel", getRemainingFuel())
                .append("platformId", getPlatformId())
                .append("totalAmmo", getTotalAmmo())
                .append("totalFuel", getTotalFuel())
                .append("missCount", getMissCount())
                .append("destroyStatus", getDestroyStatus())
                .append("evadeCount", getEvadeCount())
                .append("role", getRole())
                .toString();
    }
}
