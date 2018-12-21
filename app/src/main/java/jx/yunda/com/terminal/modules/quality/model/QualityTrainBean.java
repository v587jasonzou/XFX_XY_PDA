package jx.yunda.com.terminal.modules.quality.model;

import java.io.Serializable;

public class QualityTrainBean implements Serializable {

    /**
     * trainTypeIdx : 231
     * undoCount : 0
     * trainNo : 6043
     * trainTypeShortname : HXD1
     * workPlanIdx : 8a41341160532fcb01605371c4a40129
     * doneCount : 0
     */

    private String trainTypeIdx;
    private int undoCount;
    private String trainNo;
    private String trainTypeShortname;
    private String workPlanIdx;
    private int doneCount;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTrainTypeIdx() {
        return trainTypeIdx;
    }

    public void setTrainTypeIdx(String trainTypeIdx) {
        this.trainTypeIdx = trainTypeIdx;
    }

    public int getUndoCount() {
        return undoCount;
    }

    public void setUndoCount(int undoCount) {
        this.undoCount = undoCount;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getTrainTypeShortname() {
        return trainTypeShortname;
    }

    public void setTrainTypeShortname(String trainTypeShortname) {
        this.trainTypeShortname = trainTypeShortname;
    }

    public String getWorkPlanIdx() {
        return workPlanIdx;
    }

    public void setWorkPlanIdx(String workPlanIdx) {
        this.workPlanIdx = workPlanIdx;
    }

    public int getDoneCount() {
        return doneCount;
    }

    public void setDoneCount(int doneCount) {
        this.doneCount = doneCount;
    }
}
