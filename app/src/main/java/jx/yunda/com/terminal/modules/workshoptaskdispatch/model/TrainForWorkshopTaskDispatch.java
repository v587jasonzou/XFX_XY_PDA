package jx.yunda.com.terminal.modules.workshoptaskdispatch.model;

import java.io.Serializable;

/**
 * Created by hed on 2018/6/11.
 */

public class TrainForWorkshopTaskDispatch implements Serializable {

    /**
     * trainTypeShortName : HXD1
     * fixDataFwh : 0
     * idx : 8a413411604538430160486fc18b01d8
     * seqNo : 1
     * repairClassName : C4
     * trainTypeIDX : 231
     * fixDataTP : 11
     * trainNo : 1348
     */

    private String trainTypeShortName;
    private int fixDataFwh;
    private String idx;
    private String seqNo;
    private String repairClassName;
    private String trainTypeIDX;
    private int fixDataTP;
    private String trainNo;
    private int state;

    public String getTrainTypeShortName() {
        return trainTypeShortName;
    }

    public void setTrainTypeShortName(String trainTypeShortName) {
        this.trainTypeShortName = trainTypeShortName;
    }

    public int getFixDataFwh() {
        return fixDataFwh;
    }

    public void setFixDataFwh(int fixDataFwh) {
        this.fixDataFwh = fixDataFwh;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getRepairClassName() {
        return repairClassName;
    }

    public void setRepairClassName(String repairClassName) {
        this.repairClassName = repairClassName;
    }

    public String getTrainTypeIDX() {
        return trainTypeIDX;
    }

    public void setTrainTypeIDX(String trainTypeIDX) {
        this.trainTypeIDX = trainTypeIDX;
    }

    public int getFixDataTP() {
        return fixDataTP;
    }

    public void setFixDataTP(int fixDataTP) {
        this.fixDataTP = fixDataTP;
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
