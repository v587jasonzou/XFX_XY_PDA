package jx.yunda.com.terminal.modules.schedule.entity;

import java.io.Serializable;

public class ReceiveTrainBean implements Serializable {

    /**
     * idx : 40288a36665681cd016656832ad20008
     * trainTypeIDX : 215
     * trainTypeShortName : SS6B
     * trainNo : 1101
     * repairClassName : 一辅
     * deliverEmpId : 70001
     * deliverEmpName : 田凯
     */

    private String idx;
    private String trainTypeIDX;
    private String trainTypeShortName;
    private String trainNo;
    private String repairClassName;
    private String deliverEmpId;
    private String deliverEmpName;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
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

    public String getRepairClassName() {
        return repairClassName;
    }

    public void setRepairClassName(String repairClassName) {
        this.repairClassName = repairClassName;
    }

    public String getDeliverEmpId() {
        return deliverEmpId;
    }

    public void setDeliverEmpId(String deliverEmpId) {
        this.deliverEmpId = deliverEmpId;
    }

    public String getDeliverEmpName() {
        return deliverEmpName;
    }

    public void setDeliverEmpName(String deliverEmpName) {
        this.deliverEmpName = deliverEmpName;
    }
}
