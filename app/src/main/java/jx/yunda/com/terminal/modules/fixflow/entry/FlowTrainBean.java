package jx.yunda.com.terminal.modules.fixflow.entry;

import java.io.Serializable;

public class FlowTrainBean implements Serializable {

    /**
     * trainTypeShortName : SS6B
     * tpUnfinishedNo : 0
     * idx : 4028800066c7dc7f0166c7fae2ff005e
     * fwUnfinishedNo : 33
     * repairClassName : 辅修
     * fwfinishedNo : 0
     * planBeginTime : 2018-11-01 08:00
     * trainTypeIDX : 215
     * tpfinishedNo : 0
     * trainNo : 1100
     */

    private String trainTypeShortName;
    private int tpUnfinishedNo;
    private String idx;
    private int fwUnfinishedNo;
    private String repairClassName;
    private int fwFinishedNo;
    private String planBeginTime;
    private String trainTypeIDX;
    private int tpFinishedNo;
    private String trainNo;
    private String deliverEmpName;

    public String getDeliverEmpName() {
        return deliverEmpName;
    }

    public void setDeliverEmpName(String deliverEmpName) {
        this.deliverEmpName = deliverEmpName;
    }

    public String getTrainTypeShortName() {
        return trainTypeShortName;
    }

    public void setTrainTypeShortName(String trainTypeShortName) {
        this.trainTypeShortName = trainTypeShortName;
    }

    public int getTpUnfinishedNo() {
        return tpUnfinishedNo;
    }

    public void setTpUnfinishedNo(int tpUnfinishedNo) {
        this.tpUnfinishedNo = tpUnfinishedNo;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public int getFwUnfinishedNo() {
        return fwUnfinishedNo;
    }

    public void setFwUnfinishedNo(int fwUnfinishedNo) {
        this.fwUnfinishedNo = fwUnfinishedNo;
    }

    public String getRepairClassName() {
        return repairClassName;
    }

    public void setRepairClassName(String repairClassName) {
        this.repairClassName = repairClassName;
    }



    public String getPlanBeginTime() {
        return planBeginTime;
    }

    public void setPlanBeginTime(String planBeginTime) {
        this.planBeginTime = planBeginTime;
    }

    public String getTrainTypeIDX() {
        return trainTypeIDX;
    }

    public void setTrainTypeIDX(String trainTypeIDX) {
        this.trainTypeIDX = trainTypeIDX;
    }

    public int getFwFinishedNo() {
        return fwFinishedNo;
    }

    public void setFwFinishedNo(int fwFinishedNo) {
        this.fwFinishedNo = fwFinishedNo;
    }

    public int getTpFinishedNo() {
        return tpFinishedNo;
    }

    public void setTpFinishedNo(int tpFinishedNo) {
        this.tpFinishedNo = tpFinishedNo;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }
}
