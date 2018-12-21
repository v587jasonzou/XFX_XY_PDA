package jx.yunda.com.terminal.modules.receiveTrain.model;

import java.io.Serializable;

public class NodeEntity implements Serializable {

    /**
     * idx : 8a8284c265177958016517aee9810041
     * processCode : LC-00000088
     * processName : SS6B辅修作业流程---测试
     * trainTypeIDX : 215
     * trainTypeName : null
     * trainTypeShortName : SS6B
     * rcIDX : 11
     * rcName : 辅修
     * ratedWorkDay : 1.0
     * status : 1
     * description : null
     * recordStatus : 0
     * siteID : 1848
     * creator : 63221
     * createTime : 1533700663000
     * updator : 63221
     * updateTime : 1533705514000
     * workCalendarIDX : ff8080815b096999015b096e5a070002
     */

    private String idx;
    private String processCode;
    private String processName;
    private String trainTypeIDX;
    private Object trainTypeName;
    private String trainTypeShortName;
    private String rcIDX;
    private String rcName;
    private double ratedWorkDay;
    private int status;
    private Object description;
    private int recordStatus;
    private String siteID;
    private int creator;
    private long createTime;
    private int updator;
    private long updateTime;
    private String workCalendarIDX;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getTrainTypeIDX() {
        return trainTypeIDX;
    }

    public void setTrainTypeIDX(String trainTypeIDX) {
        this.trainTypeIDX = trainTypeIDX;
    }

    public Object getTrainTypeName() {
        return trainTypeName;
    }

    public void setTrainTypeName(Object trainTypeName) {
        this.trainTypeName = trainTypeName;
    }

    public String getTrainTypeShortName() {
        return trainTypeShortName;
    }

    public void setTrainTypeShortName(String trainTypeShortName) {
        this.trainTypeShortName = trainTypeShortName;
    }

    public String getRcIDX() {
        return rcIDX;
    }

    public void setRcIDX(String rcIDX) {
        this.rcIDX = rcIDX;
    }

    public String getRcName() {
        return rcName;
    }

    public void setRcName(String rcName) {
        this.rcName = rcName;
    }

    public double getRatedWorkDay() {
        return ratedWorkDay;
    }

    public void setRatedWorkDay(double ratedWorkDay) {
        this.ratedWorkDay = ratedWorkDay;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
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

    public String getWorkCalendarIDX() {
        return workCalendarIDX;
    }

    public void setWorkCalendarIDX(String workCalendarIDX) {
        this.workCalendarIDX = workCalendarIDX;
    }
}
