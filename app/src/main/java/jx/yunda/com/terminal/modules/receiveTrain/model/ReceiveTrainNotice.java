package jx.yunda.com.terminal.modules.receiveTrain.model;

import java.io.Serializable;

public class ReceiveTrainNotice implements Serializable {

    /**
     * idx : 8a82848d65b6db0a0165b860b0960018
     * noticeIDX : null
     * trainTypeIDX : 231
     * trainTypeShortName : HXD1
     * trainNo : 2003
     * homePositionId : SSSD
     * homePosition : 43道
     * homePositionConfig : 1005
     * destinationId : EDKWDG
     * destination : 2道库外地沟
     * destinationConfig : 0904
     * acceptStatus : 0
     * confirmPersonId : null
     * confirmPersonName : null
     * confirmTime : null
     * updator : 63221
     * updatorName : 陈宝顺
     * updateTime : 1536396669000
     * dId : 1101
     * dName : 成都机务段
     * dShortName : 成
     */

    private String idx;
    private String noticeIDX;
    private String trainTypeIDX;
    private String trainTypeShortName;
    private String trainNo;
    private String homePositionId;
    private String homePosition;
    private String homePositionConfig;
    private String destinationId;
    private String destination;
    private String destinationConfig;
    private Long acceptStatus;
    private Long confirmPersonId;
    private String confirmPersonName;
    private String confirmTime;
    private Long updator;
    private String updatorName;
    private Long updateTime;
    private Long dId;
    private String dName;
    private String dShortName;
    private String repairClassName;
    private String repairClassIDX;

    public String getRepairClassIDX() {
        return repairClassIDX;
    }

    public void setRepairClassIDX(String repairClassIDX) {
        this.repairClassIDX = repairClassIDX;
    }

    public String getRepairClassName() {
        return repairClassName;
    }

    public void setRepairClassName(String repairClassName) {
        this.repairClassName = repairClassName;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getNoticeIDX() {
        return noticeIDX;
    }

    public void setNoticeIDX(String noticeIDX) {
        this.noticeIDX = noticeIDX;
    }

    public String getTrainTypeIDX() {
        return trainTypeIDX;
    }

    public void setTrainTypeIDX(String trainTypeIDX) {
        this.trainTypeIDX = trainTypeIDX;
    }

    public String getTrainTypeShortName() {
        return trainTypeShortName;
    }

    public void setTrainTypeShortName(String trainTypeShortName) {
        this.trainTypeShortName = trainTypeShortName;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getHomePositionId() {
        return homePositionId;
    }

    public void setHomePositionId(String homePositionId) {
        this.homePositionId = homePositionId;
    }

    public String getHomePosition() {
        return homePosition;
    }

    public void setHomePosition(String homePosition) {
        this.homePosition = homePosition;
    }

    public String getHomePositionConfig() {
        return homePositionConfig;
    }

    public void setHomePositionConfig(String homePositionConfig) {
        this.homePositionConfig = homePositionConfig;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestinationConfig() {
        return destinationConfig;
    }

    public void setDestinationConfig(String destinationConfig) {
        this.destinationConfig = destinationConfig;
    }

    public Long getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(Long acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public Long getConfirmPersonId() {
        return confirmPersonId;
    }

    public void setConfirmPersonId(Long confirmPersonId) {
        this.confirmPersonId = confirmPersonId;
    }

    public String getConfirmPersonName() {
        return confirmPersonName;
    }

    public void setConfirmPersonName(String confirmPersonName) {
        this.confirmPersonName = confirmPersonName;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Long getUpdator() {
        return updator;
    }

    public void setUpdator(Long updator) {
        this.updator = updator;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getDId() {
        return dId;
    }

    public void setDId(Long dId) {
        this.dId = dId;
    }

    public String getDName() {
        return dName;
    }

    public void setDName(String dName) {
        this.dName = dName;
    }

    public String getDShortName() {
        return dShortName;
    }

    public void setDShortName(String dShortName) {
        this.dShortName = dShortName;
    }
}
