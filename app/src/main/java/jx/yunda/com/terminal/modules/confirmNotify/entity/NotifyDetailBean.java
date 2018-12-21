package jx.yunda.com.terminal.modules.confirmNotify.entity;

import java.io.Serializable;

public class NotifyDetailBean implements Serializable {

    /**
     * createTime : 2018-09-05 17:23
     * idx : 40288afc65a22cdb0165a25ddb250003
     * destinationConfig : 0302
     * updatorName : 陈宝顺
     * trainTypeIDX : 215
     * acceptStatus : 1
     * trainNo : 1345
     * destination : 20道南头地沟
     * trainTypeShortName : SS6B
     * confirmTime : 2018-09-05 17:23
     * homePositionId : SSSD
     * destinationId : ESDNTDG
     * homePositionConfig : 1005
     * confirmPersonId : 69814
     * noticeIDX : 40288afc65a22cdb0165a2571c160002
     * confirmPersonName : 白华军
     * homePosition : 43道
     */

    private String createTime;
    private String idx;
    private String destinationConfig;
    private String updatorName;
    private String trainTypeIDX;
    private Integer acceptStatus;
    private String trainNo;
    private String destination;
    private String trainTypeShortName;
    private String confirmTime;
    private String homePositionId;
    private String destinationId;
    private String homePositionConfig;
    private Integer confirmPersonId;
    private String noticeIDX;
    private String confirmPersonName;
    private String homePosition;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getDestinationConfig() {
        return destinationConfig;
    }

    public void setDestinationConfig(String destinationConfig) {
        this.destinationConfig = destinationConfig;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public String getTrainTypeIDX() {
        return trainTypeIDX;
    }

    public void setTrainTypeIDX(String trainTypeIDX) {
        this.trainTypeIDX = trainTypeIDX;
    }

    public Integer getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(Integer acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTrainTypeShortName() {
        return trainTypeShortName;
    }

    public void setTrainTypeShortName(String trainTypeShortName) {
        this.trainTypeShortName = trainTypeShortName;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getHomePositionId() {
        return homePositionId;
    }

    public void setHomePositionId(String homePositionId) {
        this.homePositionId = homePositionId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getHomePositionConfig() {
        return homePositionConfig;
    }

    public void setHomePositionConfig(String homePositionConfig) {
        this.homePositionConfig = homePositionConfig;
    }

    public Integer getConfirmPersonId() {
        return confirmPersonId;
    }

    public void setConfirmPersonId(Integer confirmPersonId) {
        this.confirmPersonId = confirmPersonId;
    }

    public String getNoticeIDX() {
        return noticeIDX;
    }

    public void setNoticeIDX(String noticeIDX) {
        this.noticeIDX = noticeIDX;
    }

    public String getConfirmPersonName() {
        return confirmPersonName;
    }

    public void setConfirmPersonName(String confirmPersonName) {
        this.confirmPersonName = confirmPersonName;
    }

    public String getHomePosition() {
        return homePosition;
    }

    public void setHomePosition(String homePosition) {
        this.homePosition = homePosition;
    }
}
