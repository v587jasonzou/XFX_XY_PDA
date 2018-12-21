package jx.yunda.com.terminal.modules.receiveTrain.model;

import java.io.Serializable;
import java.util.List;

import jx.yunda.com.terminal.modules.schedule.entity.NodeBean;

public class ReceivedTrain implements Serializable {
    /**
     * repairtimeName : null
     * seqNo : null
     * repairClassName : 辅修
     * planEndTimeTra : 2018-08-09 13:20
     * processName : SS6B辅修作业流程
     * trainTypeIDX : 215
     * remarks : null
     * delegateDId : null
     * trainNo : 3000
     * endTime : null
     * workPlanStatus : INITIALIZE
     * isCallName : 0
     * startNodes : []
     * isDelay : 1
     * rcTrainCategoryNAME : 单节机车
     * baseLineEndTimeTra : null
     * JT28Count : null
     * dID : 1101
     * idx : 8a8284c265127a340165128901a90013
     * repairClassIDX : 11
     * processIdx : 297edff8644adf3f01644eb3b3d80043
     * beginTime : null
     * processIDX : 297edff8644adf3f01644eb3b3d80043
     * delayCount : 11
     * trainTypeShortName : SS6B
     * repairtimeIDX : null
     * dNAME : 成
     * planBeginTimeTra : 2018-08-08 08:00
     * workCalendarIDX : ff8080815b096999015b096e5a070002
     * rcTrainCategoryCode : 10
     * delegateDName : null
     */

    private String repairtimeName;
    private Object seqNo;
    private String repairClassName;
    private String planEndTimeTra;
    private String processName;
    private String trainTypeIDX;
    private String remarks;
    private String delegateDId;
    private String trainNo;
    private Object endTime;
    private String workPlanStatus;
    private Integer isCallName;
    private Integer isDelay;
    private String rcTrainCategoryNAME;
    private Object baseLineEndTimeTra;
    private Object JT28Count;
    private String dID;
    private String idx;
    private String repairClassIDX;
    private String processIdx;
    private String beginTime;
    private String processIDX;
    private Integer delayCount;
    private String trainTypeShortName;
    private String repairtimeIDX;
    private String dNAME;
    private String planBeginTimeTra;
    private String workCalendarIDX;
    private String rcTrainCategoryCode;
    private String delegateDName;
    private List<NodeBean> startNodes;

    public String getRepairtimeName() {
        return repairtimeName;
    }

    public void setRepairtimeName(String repairtimeName) {
        this.repairtimeName = repairtimeName;
    }

    public Object getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Object seqNo) {
        this.seqNo = seqNo;
    }

    public String getRepairClassName() {
        return repairClassName;
    }

    public void setRepairClassName(String repairClassName) {
        this.repairClassName = repairClassName;
    }

    public String getPlanEndTimeTra() {
        return planEndTimeTra;
    }

    public void setPlanEndTimeTra(String planEndTimeTra) {
        this.planEndTimeTra = planEndTimeTra;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getTrainTypeIDX() {
        return trainTypeIDX;
    }

    public void setTrainTypeIDX(String trainTypeIDX) {
        this.trainTypeIDX = trainTypeIDX;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelegateDId() {
        return delegateDId;
    }

    public void setDelegateDId(String delegateDId) {
        this.delegateDId = delegateDId;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public String getWorkPlanStatus() {
        return workPlanStatus;
    }

    public void setWorkPlanStatus(String workPlanStatus) {
        this.workPlanStatus = workPlanStatus;
    }

    public Integer getIsCallName() {
        return isCallName;
    }

    public void setIsCallName(Integer isCallName) {
        this.isCallName = isCallName;
    }

    public Integer getIsDelay() {
        return isDelay;
    }

    public void setIsDelay(Integer isDelay) {
        this.isDelay = isDelay;
    }

    public String getRcTrainCategoryNAME() {
        return rcTrainCategoryNAME;
    }

    public void setRcTrainCategoryNAME(String rcTrainCategoryNAME) {
        this.rcTrainCategoryNAME = rcTrainCategoryNAME;
    }

    public Object getBaseLineEndTimeTra() {
        return baseLineEndTimeTra;
    }

    public void setBaseLineEndTimeTra(Object baseLineEndTimeTra) {
        this.baseLineEndTimeTra = baseLineEndTimeTra;
    }

    public Object getJT28Count() {
        return JT28Count;
    }

    public void setJT28Count(Object JT28Count) {
        this.JT28Count = JT28Count;
    }

    public String getDID() {
        return dID;
    }

    public void setDID(String dID) {
        this.dID = dID;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getRepairClassIDX() {
        return repairClassIDX;
    }

    public void setRepairClassIDX(String repairClassIDX) {
        this.repairClassIDX = repairClassIDX;
    }

    public String getProcessIdx() {
        return processIdx;
    }

    public void setProcessIdx(String processIdx) {
        this.processIdx = processIdx;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getProcessIDX() {
        return processIDX;
    }

    public void setProcessIDX(String processIDX) {
        this.processIDX = processIDX;
    }

    public Integer getDelayCount() {
        return delayCount;
    }

    public void setDelayCount(Integer delayCount) {
        this.delayCount = delayCount;
    }

    public String getTrainTypeShortName() {
        return trainTypeShortName;
    }

    public void setTrainTypeShortName(String trainTypeShortName) {
        this.trainTypeShortName = trainTypeShortName;
    }

    public String getRepairtimeIDX() {
        return repairtimeIDX;
    }

    public void setRepairtimeIDX(String repairtimeIDX) {
        this.repairtimeIDX = repairtimeIDX;
    }

    public String getDNAME() {
        return dNAME;
    }

    public void setDNAME(String dNAME) {
        this.dNAME = dNAME;
    }

    public String getPlanBeginTimeTra() {
        return planBeginTimeTra;
    }

    public void setPlanBeginTimeTra(String planBeginTimeTra) {
        this.planBeginTimeTra = planBeginTimeTra;
    }

    public String getWorkCalendarIDX() {
        return workCalendarIDX;
    }

    public void setWorkCalendarIDX(String workCalendarIDX) {
        this.workCalendarIDX = workCalendarIDX;
    }

    public String getRcTrainCategoryCode() {
        return rcTrainCategoryCode;
    }

    public void setRcTrainCategoryCode(String rcTrainCategoryCode) {
        this.rcTrainCategoryCode = rcTrainCategoryCode;
    }

    public String getDelegateDName() {
        return delegateDName;
    }

    public void setDelegateDName(String delegateDName) {
        this.delegateDName = delegateDName;
    }

    public List<NodeBean> getStartNodes() {
        return startNodes;
    }

    public void setStartNodes(List<NodeBean> startNodes) {
        this.startNodes = startNodes;
    }
}
