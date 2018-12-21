package jx.yunda.com.terminal.modules.jcApply.model;

import java.util.Date;

public class JCApply {
    /* idx主键 */
    private String idx;
    /*当在未申请也面是workPlanIdx=idx；当在审批中页面时workPlanIdx!=idx*/
    private String workPlanIdx;
    /* 作业流程主键 */
    private String processIDX;

    /* 作业流程名称 */
    private String processName;

    /* 作业流程额定工期（小时） */
    private Double ratedWorkDay;

    /* 车型主键 */
    private String trainTypeIDX;

    /* 车号 */
    private String trainNo;

    /* 车型英文简称 */
    private String trainTypeShortName;

    /* 修程编码 */
    private String repairClassIDX;

    /* 修程名称 */
    private String repairClassName;

    /* 修次 */
    private String repairtimeIDX;

    /* 修次名称 */
    private String repairtimeName;

    /* 机车作业计划状态，新增；处理中；已处理；作废（数据字典） */
    private String workPlanStatus;

    /* 计划开始时间 */
    private String planBeginTime;

    /* 计划完成时间 */
    private String planEndTime;

    /* 实际开始时间 */
    private String beginTime;

    /* 实际完成时间 */
    private String endTime;

    /* 计划生成时间 */
    private String workPlanTime;

    /* 计划交车时间（基线时间 ） */
    private String baseLineEndTime;

    /* 备注 */
    private String remarks;

    /* 日历 */
    private String workCalendarIDX;

    /* 配属段ID */
    private String dID;

    /* 配属段名称 */
    private String dNAME;

    /* 委托维修段ID */
    private String delegateDID;

    /* 委托维修段名称 */
    private String delegateDName;

    /* 表示此条记录的状态：0为表示未删除；1表示删除 */
    private Integer recordStatus;

    /* 站点标识，为了同步数据而使用 */
    private String siteID;

    /* 创建人 */
    private Long creator;

    /* 创建时间 */
    private java.util.Date createTime;

    /* 修改人 */
    private Long updator;

    /* 修改时间 */
    private java.util.Date updateTime;

    /* 施修计划明细主键 */
    private String enforcePlanDetailIDX;

    /* 排序号 */
    private Integer seqNo;

    /* 是否点名  0:未点名 1：已点名 */
    //private Integer  isCallName = CALL_NAME_NO;

    /* 机车检修作业计划下属的机车检修计划流程节点（第一层） */
    //private List<JobProcessNode> jobProcessNodes;

    /* 机车检修作业计划下属的机车检修计划流程节点Map */
    //private List<Map<String, Object>> nodes;

    /* 机车检修作业计划下属的机车检修计划流程节点（第一层） */
    //private List<JobProcessNodeBean> jobProcessNodeBeans;

    /* 工长派工查询-生产任务单: 兑现单的车型简称+车号+修程名称+修次+状态*/
    private String groupRdpInfo;

    /* 兑现单文本，用于填充数据 */
    //private String rdpText;
    /* 进段时间 */
    //private String inTime;
    //private String minRealTime;
    //private String maxRealTime;
    //private String months;
    /*流程实例id*/
    private String processInstId;

    /* 机车出入段台账主键 添加检修任务与机车入段台账关联，在新增作业计划时查看在段机车，并关联；在机车入段时，查看当前车是否正在检修，并且没有关联台账，则进行关联 */
    private String trainAccessAccountIDX;


    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getWorkPlanIdx() {
        return workPlanIdx;
    }

    public void setWorkPlanIdx(String workPlanIdx) {
        this.workPlanIdx = workPlanIdx;
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

    public Double getRatedWorkDay() {
        return ratedWorkDay;
    }

    public void setRatedWorkDay(Double ratedWorkDay) {
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

    public String getPlanBeginTime() {
        return planBeginTime;
    }

    public void setPlanBeginTime(String planBeginTime) {
        this.planBeginTime = planBeginTime;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getWorkPlanTime() {
        return workPlanTime;
    }

    public void setWorkPlanTime(String workPlanTime) {
        this.workPlanTime = workPlanTime;
    }

    public String getBaseLineEndTime() {
        return baseLineEndTime;
    }

    public void setBaseLineEndTime(String baseLineEndTime) {
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

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getSiteID() {
        return siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdator() {
        return updator;
    }

    public void setUpdator(Long updator) {
        this.updator = updator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getEnforcePlanDetailIDX() {
        return enforcePlanDetailIDX;
    }

    public void setEnforcePlanDetailIDX(String enforcePlanDetailIDX) {
        this.enforcePlanDetailIDX = enforcePlanDetailIDX;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public String getGroupRdpInfo() {
        return groupRdpInfo;
    }

    public void setGroupRdpInfo(String groupRdpInfo) {
        this.groupRdpInfo = groupRdpInfo;
    }

    public String getTrainAccessAccountIDX() {
        return trainAccessAccountIDX;
    }

    public void setTrainAccessAccountIDX(String trainAccessAccountIDX) {
        this.trainAccessAccountIDX = trainAccessAccountIDX;
    }

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }
}
