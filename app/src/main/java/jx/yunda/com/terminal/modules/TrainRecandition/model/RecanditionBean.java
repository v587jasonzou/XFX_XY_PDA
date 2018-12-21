package jx.yunda.com.terminal.modules.TrainRecandition.model;

import java.io.Serializable;

public class RecanditionBean implements Serializable {

    /**
     * nodeName : 修前试验
     * status : COMPLETED
     * idx : 8a413411605986b201605993121f005b
     * trainNo : 0002
     * workStationName : null
     * trainTypeShortName : HXD1
     * delegateDName : 成
     * dname : 成
     * repairClassName : C4
     * repairtimeName : 一次
     * workStationIDX : null
     * processName : HXD1 C4 标准作业流程
     * workPlanIDX : 8a413411605986b2016059930f430014
     * delayType : null
     * planBeginTime : 1513382400000
     * planEndTime : 1513389600000
     * realBeginTime : 1513402749000
     * realEndTime : 1513403158000
     * trainTypeIdx : 231
     * delayReason : null
     * ratedWorkHours : 2.0
     * partsOffCount : null
     * partsOnCount : null
     * matsCount : 7
     * rdpPlanBeginTime : 1513382400000
     * rdpPlanEndTime : 1514280000000
     * workCalendarName : 7*8小时
     * delayIdx : null
     * cardCount : 0/4
     */

    private String nodeName;
    private String status;
    private String idx;
    private String trainNo;
    private Object workStationName;
    private String trainTypeShortName;
    private String delegateDName;
    private String dname;
    private String repairClassName;
    private String repairtimeName;
    private Object workStationIDX;
    private String processName;
    private String workPlanIDX;
    private Object delayType;
    private long planBeginTime;
    private long planEndTime;
    private long realBeginTime;
    private Long realEndTime;
    private String trainTypeIdx;
    private Object delayReason;
    private double ratedWorkHours;
    private String partsOffCount;
    private String partsOnCount;
    private String matsCount;
    private long rdpPlanBeginTime;
    private long rdpPlanEndTime;
    private String workCalendarName;
    private Object delayIdx;
    private String cardCount;
    private String dictid;
    private String dictname;
    private String filter2;
    private String professionTypeIdx;

    public String getProfessionTypeIdx() {
        return professionTypeIdx;
    }

    public void setProfessionTypeIdx(String professionTypeIdx) {
        this.professionTypeIdx = professionTypeIdx;
    }

    public String getDictid() {
        return dictid;
    }

    public void setDictid(String dictid) {
        this.dictid = dictid;
    }

    public String getDictname() {
        return dictname;
    }

    public void setDictname(String dictname) {
        this.dictname = dictname;
    }

    public String getFilter2() {
        return filter2;
    }

    public void setFilter2(String filter2) {
        this.filter2 = filter2;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public Object getWorkStationName() {
        return workStationName;
    }

    public void setWorkStationName(Object workStationName) {
        this.workStationName = workStationName;
    }

    public String getTrainTypeShortName() {
        return trainTypeShortName;
    }

    public void setTrainTypeShortName(String trainTypeShortName) {
        this.trainTypeShortName = trainTypeShortName;
    }

    public String getDelegateDName() {
        return delegateDName;
    }

    public void setDelegateDName(String delegateDName) {
        this.delegateDName = delegateDName;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getRepairClassName() {
        return repairClassName;
    }

    public void setRepairClassName(String repairClassName) {
        this.repairClassName = repairClassName;
    }

    public String getRepairtimeName() {
        return repairtimeName;
    }

    public void setRepairtimeName(String repairtimeName) {
        this.repairtimeName = repairtimeName;
    }

    public Object getWorkStationIDX() {
        return workStationIDX;
    }

    public void setWorkStationIDX(Object workStationIDX) {
        this.workStationIDX = workStationIDX;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getWorkPlanIDX() {
        return workPlanIDX;
    }

    public void setWorkPlanIDX(String workPlanIDX) {
        this.workPlanIDX = workPlanIDX;
    }

    public Object getDelayType() {
        return delayType;
    }

    public void setDelayType(Object delayType) {
        this.delayType = delayType;
    }

    public long getPlanBeginTime() {
        return planBeginTime;
    }

    public void setPlanBeginTime(long planBeginTime) {
        this.planBeginTime = planBeginTime;
    }

    public long getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(long planEndTime) {
        this.planEndTime = planEndTime;
    }

    public long getRealBeginTime() {
        return realBeginTime;
    }

    public void setRealBeginTime(long realBeginTime) {
        this.realBeginTime = realBeginTime;
    }

    public Long getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(Long realEndTime) {
        this.realEndTime = realEndTime;
    }

    public String getTrainTypeIdx() {
        return trainTypeIdx;
    }

    public void setTrainTypeIdx(String trainTypeIdx) {
        this.trainTypeIdx = trainTypeIdx;
    }

    public Object getDelayReason() {
        return delayReason;
    }

    public void setDelayReason(Object delayReason) {
        this.delayReason = delayReason;
    }

    public double getRatedWorkHours() {
        return ratedWorkHours;
    }

    public void setRatedWorkHours(double ratedWorkHours) {
        this.ratedWorkHours = ratedWorkHours;
    }

    public String getPartsOffCount() {
        return partsOffCount;
    }

    public void setPartsOffCount(String partsOffCount) {
        this.partsOffCount = partsOffCount;
    }

    public String getPartsOnCount() {
        return partsOnCount;
    }

    public void setPartsOnCount(String partsOnCount) {
        this.partsOnCount = partsOnCount;
    }

    public String getMatsCount() {
        return matsCount;
    }

    public void setMatsCount(String matsCount) {
        this.matsCount = matsCount;
    }

    public long getRdpPlanBeginTime() {
        return rdpPlanBeginTime;
    }

    public void setRdpPlanBeginTime(long rdpPlanBeginTime) {
        this.rdpPlanBeginTime = rdpPlanBeginTime;
    }

    public long getRdpPlanEndTime() {
        return rdpPlanEndTime;
    }

    public void setRdpPlanEndTime(long rdpPlanEndTime) {
        this.rdpPlanEndTime = rdpPlanEndTime;
    }

    public String getWorkCalendarName() {
        return workCalendarName;
    }

    public void setWorkCalendarName(String workCalendarName) {
        this.workCalendarName = workCalendarName;
    }

    public Object getDelayIdx() {
        return delayIdx;
    }

    public void setDelayIdx(Object delayIdx) {
        this.delayIdx = delayIdx;
    }

    public String getCardCount() {
        return cardCount;
    }

    public void setCardCount(String cardCount) {
        this.cardCount = cardCount;
    }
}
