package jx.yunda.com.terminal.modules.tpprocess.model;

import java.io.Serializable;

public class TicketTrainBean implements Serializable {
    private String idx;
    private String processIDX;
    private String processName;
    private double ratedWorkDay;
    private String trainTypeIDX;
    private String trainNo;
    private String trainTypeShortName;
    private String repairClassIDX;
    private String repairClassName;
    private String repairtimeIDX;
    private String repairtimeName;
    private String workPlanStatus;
    private long planBeginTime;
    private long planEndTime;
    private long beginTime;
    private long endTime;
    private long workPlanTime;
    private long baseLineEndTime;
    private String remarks;
    private String workCalendarIDX;
    private String dID;
    private String dNAME;
    private String delegateDID;
    private String delegateDName;
    private int recordStatus;
    private String siteID;
    private int creator;
    private long createTime;
    private int updator;
    private long updateTime;
    private String enforcePlanDetailIDX;
    private String jobProcessNodes;
    private String nodes;
    private String jobProcessNodeBeans;
    private String groupRdpInfo;
    private String rdpText;
    private String inTime;
    private long minRealTime;
    private long maxRealTime;
    private String months;
    private String trainAccessAccountIDX;
    private int state = 0;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getProcessIDX() {
        return processIDX;
    }

    public void setProcessIDX(String processIDX) {
        this.processIDX = processIDX;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public double getRatedWorkDay() {
        return ratedWorkDay;
    }

    public void setRatedWorkDay(double ratedWorkDay) {
        this.ratedWorkDay = ratedWorkDay;
    }

    public String getTrainTypeIDX() {
        return trainTypeIDX;
    }

    public void setTrainTypeIDX(String trainTypeIDX) {
        this.trainTypeIDX = trainTypeIDX;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getTrainTypeShortName() {
        return trainTypeShortName;
    }

    public void setTrainTypeShortName(String trainTypeShortName) {
        this.trainTypeShortName = trainTypeShortName;
    }

    public String getRepairClassIDX() {
        return repairClassIDX;
    }

    public void setRepairClassIDX(String repairClassIDX) {
        this.repairClassIDX = repairClassIDX;
    }

    public String getRepairClassName() {
        return repairClassName;
    }

    public void setRepairClassName(String repairClassName) {
        this.repairClassName = repairClassName;
    }

    public String getRepairtimeIDX() {
        return repairtimeIDX;
    }

    public void setRepairtimeIDX(String repairtimeIDX) {
        this.repairtimeIDX = repairtimeIDX;
    }

    public String getRepairtimeName() {
        return repairtimeName;
    }

    public void setRepairtimeName(String repairtimeName) {
        this.repairtimeName = repairtimeName;
    }

    public String getWorkPlanStatus() {
        return workPlanStatus;
    }

    public void setWorkPlanStatus(String workPlanStatus) {
        this.workPlanStatus = workPlanStatus;
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

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getWorkPlanTime() {
        return workPlanTime;
    }

    public void setWorkPlanTime(long workPlanTime) {
        this.workPlanTime = workPlanTime;
    }

    public long getBaseLineEndTime() {
        return baseLineEndTime;
    }

    public void setBaseLineEndTime(long baseLineEndTime) {
        this.baseLineEndTime = baseLineEndTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getWorkCalendarIDX() {
        return workCalendarIDX;
    }

    public void setWorkCalendarIDX(String workCalendarIDX) {
        this.workCalendarIDX = workCalendarIDX;
    }

    public String getdID() {
        return dID;
    }

    public void setdID(String dID) {
        this.dID = dID;
    }

    public String getdNAME() {
        return dNAME;
    }

    public void setdNAME(String dNAME) {
        this.dNAME = dNAME;
    }

    public String getDelegateDID() {
        return delegateDID;
    }

    public void setDelegateDID(String delegateDID) {
        this.delegateDID = delegateDID;
    }

    public String getDelegateDName() {
        return delegateDName;
    }

    public void setDelegateDName(String delegateDName) {
        this.delegateDName = delegateDName;
    }

    public int getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(int recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getUpdator() {
        return updator;
    }

    public void setUpdator(int updator) {
        this.updator = updator;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getEnforcePlanDetailIDX() {
        return enforcePlanDetailIDX;
    }

    public void setEnforcePlanDetailIDX(String enforcePlanDetailIDX) {
        this.enforcePlanDetailIDX = enforcePlanDetailIDX;
    }

    public String getJobProcessNodes() {
        return jobProcessNodes;
    }

    public void setJobProcessNodes(String jobProcessNodes) {
        this.jobProcessNodes = jobProcessNodes;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public String getJobProcessNodeBeans() {
        return jobProcessNodeBeans;
    }

    public void setJobProcessNodeBeans(String jobProcessNodeBeans) {
        this.jobProcessNodeBeans = jobProcessNodeBeans;
    }

    public String getGroupRdpInfo() {
        return groupRdpInfo;
    }

    public void setGroupRdpInfo(String groupRdpInfo) {
        this.groupRdpInfo = groupRdpInfo;
    }

    public String getRdpText() {
        return rdpText;
    }

    public void setRdpText(String rdpText) {
        this.rdpText = rdpText;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public long getMinRealTime() {
        return minRealTime;
    }

    public void setMinRealTime(long minRealTime) {
        this.minRealTime = minRealTime;
    }

    public long getMaxRealTime() {
        return maxRealTime;
    }

    public void setMaxRealTime(long maxRealTime) {
        this.maxRealTime = maxRealTime;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getTrainAccessAccountIDX() {
        return trainAccessAccountIDX;
    }

    public void setTrainAccessAccountIDX(String trainAccessAccountIDX) {
        this.trainAccessAccountIDX = trainAccessAccountIDX;
    }
}
