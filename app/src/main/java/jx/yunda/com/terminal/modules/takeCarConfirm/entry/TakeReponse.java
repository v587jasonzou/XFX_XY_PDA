package jx.yunda.com.terminal.modules.takeCarConfirm.entry;

import java.io.Serializable;
import java.util.List;

public class TakeReponse implements Serializable {

    /**
     * trainPlanIdx : 4028b881667b9f0d01667c0e29b1011a
     * idx : 40288a36667b8d0c01667c2f64a301a2
     * remark : null
     * updateTime : 2018-10-16 18:27
     * repairClassName : 一辅
     * updator : 70007
     * lastTime : 2018-10-16 17:55
     * updatorName : 魏少锋
     * trainTypeIDX : 215
     * trainNo : 1065
     * noticeTime : 2018-10-16 18:26
     * destination : 二十道南侧地沟
     * trainTypeShortName : SS6B
     * confirmTime : null
     * groupId : 297edff866622f4901667c10890c0172
     * confirmStatus : 0
     * jsonAcceptPerson : [{"empId":"70009","empName":"魏少锋"},{"empId":"70108","empName":"黄发勇"}]
     * confirmPersonId : null
     * confirmPersonName : null
     * homePosition : 二道库外地沟
     */

    private String trainPlanIdx;
    private String idx;
    private String remark;
    private String updateTime;
    private Integer updator;
    private String lastTime;
    private String updatorName;
    private String noticeTime;
    private String destination;
    private String confirmTime;
    private Integer confirmStatus;
    private String jsonAcceptPerson;
    private String confirmPersonId;
    private String confirmPersonName;
    private String homePosition;


    public String getTrainPlanIdx() {
        return trainPlanIdx;
    }

    public void setTrainPlanIdx(String trainPlanIdx) {
        this.trainPlanIdx = trainPlanIdx;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }



    public Integer getUpdator() {
        return updator;
    }

    public void setUpdator(Integer updator) {
        this.updator = updator;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }


    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }



    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }


    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getJsonAcceptPerson() {
        return jsonAcceptPerson;
    }

    public void setJsonAcceptPerson(String jsonAcceptPerson) {
        this.jsonAcceptPerson = jsonAcceptPerson;
    }

    public String getConfirmPersonId() {
        return confirmPersonId;
    }

    public void setConfirmPersonId(String confirmPersonId) {
        this.confirmPersonId = confirmPersonId;
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
