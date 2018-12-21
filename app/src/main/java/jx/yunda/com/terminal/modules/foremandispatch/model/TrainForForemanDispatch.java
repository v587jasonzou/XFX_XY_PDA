package jx.yunda.com.terminal.modules.foremandispatch.model;

import java.io.Serializable;

/**
 * Created by hed on 2018/6/11.
 */

public class TrainForForemanDispatch implements Serializable {


    /**
     * doneCountTp : 171
     * unDoneCountTp : 0
     * trainTypeShortName : HXD1
     * seqNo : null
     * workPlanIDX : 8a82848963f7b1010163f8403e980055
     * doneCountFwh : 48
     * trainTypeIDX : 231
     * unDoneCountFwh : 0
     * trainNo : 1361
     */

    private int doneCountTp;
    private int unDoneCountTp;
    private String trainTypeShortName;
    private Object seqNo;
    private String workPlanIDX;
    private int doneCountFwh;
    private String trainTypeIDX;
    private int unDoneCountFwh;
    private String trainNo;
    private int state;

    public int getDoneCountTp() {
        return doneCountTp;
    }

    public void setDoneCountTp(int doneCountTp) {
        this.doneCountTp = doneCountTp;
    }

    public int getUnDoneCountTp() {
        return unDoneCountTp;
    }

    public void setUnDoneCountTp(int unDoneCountTp) {
        this.unDoneCountTp = unDoneCountTp;
    }

    public String getTrainTypeShortName() {
        return trainTypeShortName;
    }

    public void setTrainTypeShortName(String trainTypeShortName) {
        this.trainTypeShortName = trainTypeShortName;
    }

    public Object getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Object seqNo) {
        this.seqNo = seqNo;
    }

    public String getWorkPlanIDX() {
        return workPlanIDX;
    }

    public void setWorkPlanIDX(String workPlanIDX) {
        this.workPlanIDX = workPlanIDX;
    }

    public int getDoneCountFwh() {
        return doneCountFwh;
    }

    public void setDoneCountFwh(int doneCountFwh) {
        this.doneCountFwh = doneCountFwh;
    }

    public String getTrainTypeIDX() {
        return trainTypeIDX;
    }

    public void setTrainTypeIDX(String trainTypeIDX) {
        this.trainTypeIDX = trainTypeIDX;
    }

    public int getUnDoneCountFwh() {
        return unDoneCountFwh;
    }

    public void setUnDoneCountFwh(int unDoneCountFwh) {
        this.unDoneCountFwh = unDoneCountFwh;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
