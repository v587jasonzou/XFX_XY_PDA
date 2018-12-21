package jx.yunda.com.terminal.modules.foremandispatch.model;

import java.io.Serializable;

public class FwFormenBean implements Serializable {

    /**
     * workStationBelongTeam : 1854
     * idx : 8a41341164214ea701643ec887af02da
     * status : NOTSTARTED
     * workerIdxStr : 68612
     * nodeName : B车 - 制动机模块上车
     * trainTypeIDX : 231
     * trainNo : 6074
     * trainTypeShortName : HXD1
     * nodeIdx : 8a41342363634db7236366e57cfc012f
     * nodeDesc : null
     * workerNameStr : 吉庆武
     * workStationBelongTeamName : 电器组
     * planBeginTime : 2018-07-02 16:20
     * workPlanIDX : 8a41341164214ea701643ec8824d0260
     * planEndTime : 2018-07-03 14:20
     */

    private Integer workStationBelongTeam;
    private String idx;
    private String status;
    private String workerIdxStr;
    private String nodeName;
    private String trainTypeIDX;
    private String trainNo;
    private String trainTypeShortName;
    private String nodeIdx;
    private String nodeDesc;
    private String workerNameStr;
    private String workStationBelongTeamName;
    private String planBeginTime;
    private String workPlanIDX;
    private String planEndTime;

    public Integer getWorkStationBelongTeam() {
        return workStationBelongTeam;
    }

    public void setWorkStationBelongTeam(Integer workStationBelongTeam) {
        this.workStationBelongTeam = workStationBelongTeam;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWorkerIdxStr() {
        return workerIdxStr;
    }

    public void setWorkerIdxStr(String workerIdxStr) {
        this.workerIdxStr = workerIdxStr;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getTrainTypeIDX() {
        return trainTypeIDX;
    }

    public void setTrainTypeIDX(String trainTypeIDX) {
        this.trainTypeIDX = trainTypeIDX;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getTrainTypeShortName() {
        return trainTypeShortName;
    }

    public void setTrainTypeShortName(String trainTypeShortName) {
        this.trainTypeShortName = trainTypeShortName;
    }

    public String getNodeIdx() {
        return nodeIdx;
    }

    public void setNodeIdx(String nodeIdx) {
        this.nodeIdx = nodeIdx;
    }

    public String getNodeDesc() {
        return nodeDesc;
    }

    public void setNodeDesc(String nodeDesc) {
        this.nodeDesc = nodeDesc;
    }

    public String getWorkerNameStr() {
        return workerNameStr;
    }

    public void setWorkerNameStr(String workerNameStr) {
        this.workerNameStr = workerNameStr;
    }

    public String getWorkStationBelongTeamName() {
        return workStationBelongTeamName;
    }

    public void setWorkStationBelongTeamName(String workStationBelongTeamName) {
        this.workStationBelongTeamName = workStationBelongTeamName;
    }

    public String getPlanBeginTime() {
        return planBeginTime;
    }

    public void setPlanBeginTime(String planBeginTime) {
        this.planBeginTime = planBeginTime;
    }

    public String getWorkPlanIDX() {
        return workPlanIDX;
    }

    public void setWorkPlanIDX(String workPlanIDX) {
        this.workPlanIDX = workPlanIDX;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }
}
