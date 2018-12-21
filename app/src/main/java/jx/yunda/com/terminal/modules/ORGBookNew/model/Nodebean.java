package jx.yunda.com.terminal.modules.ORGBookNew.model;

import java.io.Serializable;

public class Nodebean implements Serializable {

    /**
     * trainTypeShortName : SS6B
     * idx : 40288a36666611b00166662b4192006b
     * workerIdsStr : null
     * workerNameStr : null
     * status : NOTSTARTED
     * workPlanIdx : 40288a36666611b00166662b41350052
     * nodeName : 【电器组】主变
     * trainNo : 1031
     */

    private String trainTypeShortName;
    private String idx;
    private String workerIdsStr;
    private String workerNameStr;
    private String status;
    private String workPlanIdx;
    private String nodeName;
    private String trainNo;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTrainTypeShortName() {
        return trainTypeShortName;
    }

    public void setTrainTypeShortName(String trainTypeShortName) {
        this.trainTypeShortName = trainTypeShortName;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getWorkerIdsStr() {
        return workerIdsStr;
    }

    public void setWorkerIdsStr(String workerIdsStr) {
        this.workerIdsStr = workerIdsStr;
    }

    public String getWorkerNameStr() {
        return workerNameStr;
    }

    public void setWorkerNameStr(String workerNameStr) {
        this.workerNameStr = workerNameStr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWorkPlanIdx() {
        return workPlanIdx;
    }

    public void setWorkPlanIdx(String workPlanIdx) {
        this.workPlanIdx = workPlanIdx;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }
}
