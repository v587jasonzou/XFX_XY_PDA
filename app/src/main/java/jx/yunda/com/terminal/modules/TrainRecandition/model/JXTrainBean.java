package jx.yunda.com.terminal.modules.TrainRecandition.model;

import java.io.Serializable;

public class JXTrainBean implements Serializable{


    /**
     * trainTypeShortName : HXD1
     * idx : 8a828489627473370162748d9fa30002
     * tphUndoCount : 0
     * repairClassRepairTime : C4一次
     * seqNo : null
     * fwhUndoCount : 0
     * trainNo : 0001
     */

    private String trainTypeShortName;
    private String idx;
    private int tphUndoCount;
    private String repairClassRepairTime;
    private Object seqNo;
    private int fwhUndoCount;
    private String trainNo;
    private int state;
    private String workPlanIDX;
    private String trainTypeIDX;
    private Integer fwhDoneCount;
    private Integer fwhUnDoneCount;

    public Integer getFwhUnDoneCount() {
        return fwhUnDoneCount;
    }

    public void setFwhUnDoneCount(Integer fwhUnDoneCount) {
        this.fwhUnDoneCount = fwhUnDoneCount;
    }

    public String getWorkPlanIDX() {
        return workPlanIDX;
    }

    public void setWorkPlanIDX(String workPlanIDX) {
        this.workPlanIDX = workPlanIDX;
    }

    public String getTrainTypeIDX() {
        return trainTypeIDX;
    }

    public void setTrainTypeIDX(String trainTypeIDX) {
        this.trainTypeIDX = trainTypeIDX;
    }

    public Integer getFwhDoneCount() {
        return fwhDoneCount;
    }

    public void setFwhDoneCount(Integer fwhDoneCount) {
        this.fwhDoneCount = fwhDoneCount;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public int getTphUndoCount() {
        return tphUndoCount;
    }

    public void setTphUndoCount(int tphUndoCount) {
        this.tphUndoCount = tphUndoCount;
    }

    public String getRepairClassRepairTime() {
        return repairClassRepairTime;
    }

    public void setRepairClassRepairTime(String repairClassRepairTime) {
        this.repairClassRepairTime = repairClassRepairTime;
    }

    public Object getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Object seqNo) {
        this.seqNo = seqNo;
    }

    public int getFwhUndoCount() {
        return fwhUndoCount;
    }

    public void setFwhUndoCount(int fwhUndoCount) {
        this.fwhUndoCount = fwhUndoCount;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }
}
