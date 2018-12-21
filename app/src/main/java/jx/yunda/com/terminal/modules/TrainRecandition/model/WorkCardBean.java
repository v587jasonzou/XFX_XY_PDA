package jx.yunda.com.terminal.modules.TrainRecandition.model;

import java.io.Serializable;

public class WorkCardBean implements Serializable {

    /**
     * nodeCaseIdx : 8a8284d163dd91fa0163ddfc6eaf0027
     * workCardSeq : null
     * idx : 36E38E1F7DAC4C128D54E6B08E8C74E2
     * workCardName : 车体
     */

    private String nodeCaseIdx;
    private String workCardSeq;
    private String idx;
    private String workCardName;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNodeCaseIdx() {
        return nodeCaseIdx;
    }

    public void setNodeCaseIdx(String nodeCaseIdx) {
        this.nodeCaseIdx = nodeCaseIdx;
    }

    public String getWorkCardSeq() {
        return workCardSeq;
    }

    public void setWorkCardSeq(String workCardSeq) {
        this.workCardSeq = workCardSeq;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getWorkCardName() {
        return workCardName;
    }

    public void setWorkCardName(String workCardName) {
        this.workCardName = workCardName;
    }
}
