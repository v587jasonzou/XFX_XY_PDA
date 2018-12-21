package jx.yunda.com.terminal.modules.TrainRecandition.model;

import java.io.Serializable;

public class ItemResultBean implements Serializable {

    /**
     * idx : 8a82849e610c419901610c466d150006
     * detectItemIDX : 8a82849e610c419901610c46249d0003
     * detectItemCode : JCX-001350
     * resultCode : null
     * resultName : 苛大规模在
     * isDefault : 1
     * status : null
     * recordStatus : 0
     * siteID : 1848
     * creator : 69950
     * createTime : 1516329397000
     * updator : 69950
     * updateTime : 1516329397000
     */

    private String idx;
    private String detectItemIDX;
    private String detectItemCode;
    private String resultCode;
    private String resultName;
    private int isDefault;
    private Object status;
    private int recordStatus;
    private String siteID;
    private int creator;
    private long createTime;
    private int updator;
    private long updateTime;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getDetectItemIDX() {
        return detectItemIDX;
    }

    public void setDetectItemIDX(String detectItemIDX) {
        this.detectItemIDX = detectItemIDX;
    }

    public String getDetectItemCode() {
        return detectItemCode;
    }

    public void setDetectItemCode(String detectItemCode) {
        this.detectItemCode = detectItemCode;
    }
    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public int getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(int recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getUpdator() {
        return updator;
    }

    public void setUpdator(int updator) {
        this.updator = updator;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
