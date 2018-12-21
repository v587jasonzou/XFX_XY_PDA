package jx.yunda.com.terminal.modules.quality.model;

import java.io.Serializable;

public class TrainQualityBean implements Serializable {

    /**
     * checkItemName : 车间质检
     * nodeIdx : 8a8284d163dd91fa0163ddfc6d860014
     * checkWay : 2
     * workerNameStr : 陈宝顺
     * workStationBelongTeamName : 调试组
     * workPlanIdx : 8a8284d163dd91fa0163ddfc6c8d000c
     * realEndTime : 2018-06-11 10:29
     * planBeginTime : 2018-06-18 10:00
     * nodeName : B车高压调试
     * checkItemCode : checkfarmperoson
     * realBeginTime : 2018-06-11 10:19
     * planEndTime : 2018-06-18 15:20
     */

    private String checkItemName;
    private String nodeIdx;
    private int checkWay;
    private String workerNameStr;
    private String workStationBelongTeamName;
    private String workPlanIdx;
    private String realEndTime;
    private String planBeginTime;
    private String nodeName;
    private String checkItemCode;
    private String realBeginTime;
    private String planEndTime;

    public String getCheckItemName() {
        return checkItemName;
    }

    public void setCheckItemName(String checkItemName) {
        this.checkItemName = checkItemName;
    }

    public String getNodeIdx() {
        return nodeIdx;
    }

    public void setNodeIdx(String nodeIdx) {
        this.nodeIdx = nodeIdx;
    }

    public int getCheckWay() {
        return checkWay;
    }

    public void setCheckWay(int checkWay) {
        this.checkWay = checkWay;
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

    public String getWorkPlanIdx() {
        return workPlanIdx;
    }

    public void setWorkPlanIdx(String workPlanIdx) {
        this.workPlanIdx = workPlanIdx;
    }

    public String getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(String realEndTime) {
        this.realEndTime = realEndTime;
    }

    public String getPlanBeginTime() {
        return planBeginTime;
    }

    public void setPlanBeginTime(String planBeginTime) {
        this.planBeginTime = planBeginTime;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getCheckItemCode() {
        return checkItemCode;
    }

    public void setCheckItemCode(String checkItemCode) {
        this.checkItemCode = checkItemCode;
    }

    public String getRealBeginTime() {
        return realBeginTime;
    }

    public void setRealBeginTime(String realBeginTime) {
        this.realBeginTime = realBeginTime;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }
}
