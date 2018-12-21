package jx.yunda.com.terminal.modules.TrainRecandition.model;

import java.io.Serializable;

public class OrderBean implements Serializable {

    /**
     * workTaskName : 有电蚀、裂纹者更新；表面缺损须进行绝缘处理
     * idx : 01DBD1CFDA6D48CBA6EF6BB6452C9B70
     * status : INITIALIZE
     * workCardIdx : 8AFED0CCE78D484684B11FD3C0F76735
     * repairStandard : 按标准
     * nodeCaseIdx : 8a828489641aeb3b016425f645103f2d
     * workCardSeq : null
     * detectCount : null
     * workStepType : ;workcard;
     * isPhotograph : null
     * workCardName : 高压绝缘子
     */

    private String workTaskName;
    private String idx;
    private String status;
    private String workCardIdx;
    private String repairStandard;
    private String nodeCaseIdx;
    private String workCardSeq;
    private String detectCount;
    private String workStepType;
    private String isPhotograph;
    private String workCardName;
    private Integer allCount;
    private String flbm;
    private String coid;
    private String wzqm;
    private String exclusionWorkCard;

    public String getExclusionWorkCard() {
        return exclusionWorkCard;
    }

    public void setExclusionWorkCard(String exclusionWorkCard) {
        this.exclusionWorkCard = exclusionWorkCard;
    }

    public String getFlbm() {
        return flbm;
    }

    public void setFlbm(String flbm) {
        this.flbm = flbm;
    }

    public String getCoid() {
        return coid;
    }

    public void setCoid(String coid) {
        this.coid = coid;
    }

    public String getWzqm() {
        return wzqm;
    }

    public void setWzqm(String wzqm) {
        this.wzqm = wzqm;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public String getWorkTaskName() {
        return workTaskName;
    }

    public void setWorkTaskName(String workTaskName) {
        this.workTaskName = workTaskName;
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

    public String getWorkCardIdx() {
        return workCardIdx;
    }

    public void setWorkCardIdx(String workCardIdx) {
        this.workCardIdx = workCardIdx;
    }

    public String getRepairStandard() {
        return repairStandard;
    }

    public void setRepairStandard(String repairStandard) {
        this.repairStandard = repairStandard;
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

    public String getDetectCount() {
        return detectCount;
    }

    public void setDetectCount(String detectCount) {
        this.detectCount = detectCount;
    }

    public String getWorkStepType() {
        return workStepType;
    }

    public void setWorkStepType(String workStepType) {
        this.workStepType = workStepType;
    }

    public String getIsPhotograph() {
        return isPhotograph;
    }

    public void setIsPhotograph(String isPhotograph) {
        this.isPhotograph = isPhotograph;
    }

    public String getWorkCardName() {
        return workCardName;
    }

    public void setWorkCardName(String workCardName) {
        this.workCardName = workCardName;
    }
}
