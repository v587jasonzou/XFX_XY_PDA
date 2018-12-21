package jx.yunda.com.terminal.modules.tpprocess.model;

import java.io.Serializable;

public class TicketInfoBean implements Serializable {

    /**
     * idx : 8a82848963720b1e0163724d93bf0024
     * workPlanIDX : 8a828489636c327201636c88326d00f2
     * nodeIDX : null
     * ticketCode : TP-00002306
     * trainTypeIDX : 239
     * trainNo : 0173
     * trainTypeShortName : HXD3C
     * status : 20
     * resolutionStatus : null
     * resolutionContent : null
     * type : 入段复检
     * statusAffirm : null
     * ticketTime : 1526631076000
     * faultID : null
     * faultName : null
     * faultDesc : 不工作
     * faultFixPlaceIDX : 012205
     * fixPlaceFullCode : null
     * fixPlaceFullName : HXD3C/交流电力/制动系统
     * faultOccurDate : 1526631076000
     * ticketOrgid : 1848
     * ticketOrgseq : .0.11.1848.
     * ticketOrgname : 成都大功率机车检修基地
     * ticketEmpId : 69952
     * ticketEmp : 检修管理员
     * discoverID : null
     * discover : null
     * methodID : null
     * methodName : null
     * methodDesc : null
     * repairResult : null
     * completeTime : null
     * repairEmpID : null
     * repairEmp : null
     * repairTeam : null
     * repairTeamName : null
     * repairTeamOrgseq : null
     * responseTeam : null
     * responseTeamName : null
     * responseTeamOrgseq : null
     * completeEmpID : null
     * completeEmp : null
     * responseEmpID : null
     * responseEmp : null
     * professionalTypeIdx : null
     * professionalTypeName : null
     * repairStartTime : null
     * workTime : null
     * overRangeStatus : null
     * recordStatus : 0
     * siteID : 1848
     * creator : 69949
     * createTime : 1526631076000
     * updator : 69949
     * updateTime : 1526631076000
     * faultReason : null
     * dispatchEmpID : null
     * dispatchEmp : null
     * reasonAnalysisID : 1010
     * reasonAnalysis : 高压电器
     * FaultOccurDateStr : null
     * ticketTimeStr : null
     * trainTypeAndNo : null
     * completeTimeStr : null
     * affirmUser : null
     * checkUser : null
     * isAffirm : null
     * operatorId : null
     * repairClassName : null
     */

    private String idx;
    private String workPlanIDX;
    private Object nodeIDX;
    private String ticketCode;
    private String trainTypeIDX;
    private String trainNo;
    private String trainTypeShortName;
    private int status;
    private Object resolutionStatus;
    private String resolutionContent;
    private String type;
    private Object statusAffirm;
    private Long ticketTime;
    private Object faultID;
    private Object faultName;
    private String faultDesc;
    private String faultFixPlaceIDX;
    private Object fixPlaceFullCode;
    private String fixPlaceFullName;
    private long faultOccurDate;
    private int ticketOrgid;
    private String ticketOrgseq;
    private String ticketOrgname;
    private int ticketEmpId;
    private String ticketEmp;
    private Object discoverID;
    private Object discover;
    private Object methodID;
    private Object methodName;
    private String methodDesc;
    private String repairResult;
    private Long completeTime;
    private Object repairEmpID;
    private Object repairEmp;
    private Object repairTeam;
    private Object repairTeamName;
    private Object repairTeamOrgseq;
    private Object responseTeam;
    private Object responseTeamName;
    private Object responseTeamOrgseq;
    private Integer completeEmpID;
    private String completeEmp;
    private Object responseEmpID;
    private Object responseEmp;
    private Object professionalTypeIdx;
    private Object professionalTypeName;
    private Object repairStartTime;
    private Object workTime;
    private Integer overRangeStatus;
    private int recordStatus;
    private String siteID;
    private int creator;
    private Long createTime;
    private int updator;
    private long updateTime;
    private Object faultReason;
    private Object dispatchEmpID;
    private String dispatchEmp;
    private String reasonAnalysisID;
    private String reasonAnalysis;
    private Object FaultOccurDateStr;
    private Object ticketTimeStr;
    private Object trainTypeAndNo;
    private Object completeTimeStr;
    private Object affirmUser;
    private Object checkUser;
    private Object isAffirm;
    private long operatorId;
    private Object repairClassName;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getWorkPlanIDX() {
        return workPlanIDX;
    }

    public void setWorkPlanIDX(String workPlanIDX) {
        this.workPlanIDX = workPlanIDX;
    }

    public Object getNodeIDX() {
        return nodeIDX;
    }

    public void setNodeIDX(Object nodeIDX) {
        this.nodeIDX = nodeIDX;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getResolutionStatus() {
        return resolutionStatus;
    }

    public void setResolutionStatus(Object resolutionStatus) {
        this.resolutionStatus = resolutionStatus;
    }

    public String getResolutionContent() {
        return resolutionContent;
    }

    public void setResolutionContent(String resolutionContent) {
        this.resolutionContent = resolutionContent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getStatusAffirm() {
        return statusAffirm;
    }

    public void setStatusAffirm(Object statusAffirm) {
        this.statusAffirm = statusAffirm;
    }

    public Long getTicketTime() {
        return ticketTime;
    }

    public void setTicketTime(Long ticketTime) {
        this.ticketTime = ticketTime;
    }

    public Object getFaultID() {
        return faultID;
    }

    public void setFaultID(Object faultID) {
        this.faultID = faultID;
    }

    public Object getFaultName() {
        return faultName;
    }

    public void setFaultName(Object faultName) {
        this.faultName = faultName;
    }

    public String getFaultDesc() {
        return faultDesc;
    }

    public void setFaultDesc(String faultDesc) {
        this.faultDesc = faultDesc;
    }

    public String getFaultFixPlaceIDX() {
        return faultFixPlaceIDX;
    }

    public void setFaultFixPlaceIDX(String faultFixPlaceIDX) {
        this.faultFixPlaceIDX = faultFixPlaceIDX;
    }

    public Object getFixPlaceFullCode() {
        return fixPlaceFullCode;
    }

    public void setFixPlaceFullCode(Object fixPlaceFullCode) {
        this.fixPlaceFullCode = fixPlaceFullCode;
    }

    public String getFixPlaceFullName() {
        return fixPlaceFullName;
    }

    public void setFixPlaceFullName(String fixPlaceFullName) {
        this.fixPlaceFullName = fixPlaceFullName;
    }

    public long getFaultOccurDate() {
        return faultOccurDate;
    }

    public void setFaultOccurDate(long faultOccurDate) {
        this.faultOccurDate = faultOccurDate;
    }

    public int getTicketOrgid() {
        return ticketOrgid;
    }

    public void setTicketOrgid(int ticketOrgid) {
        this.ticketOrgid = ticketOrgid;
    }

    public String getTicketOrgseq() {
        return ticketOrgseq;
    }

    public void setTicketOrgseq(String ticketOrgseq) {
        this.ticketOrgseq = ticketOrgseq;
    }

    public String getTicketOrgname() {
        return ticketOrgname;
    }

    public void setTicketOrgname(String ticketOrgname) {
        this.ticketOrgname = ticketOrgname;
    }

    public int getTicketEmpId() {
        return ticketEmpId;
    }

    public void setTicketEmpId(int ticketEmpId) {
        this.ticketEmpId = ticketEmpId;
    }

    public String getTicketEmp() {
        return ticketEmp;
    }

    public void setTicketEmp(String ticketEmp) {
        this.ticketEmp = ticketEmp;
    }

    public Object getDiscoverID() {
        return discoverID;
    }

    public void setDiscoverID(Object discoverID) {
        this.discoverID = discoverID;
    }

    public Object getDiscover() {
        return discover;
    }

    public void setDiscover(Object discover) {
        this.discover = discover;
    }

    public Object getMethodID() {
        return methodID;
    }

    public void setMethodID(Object methodID) {
        this.methodID = methodID;
    }

    public Object getMethodName() {
        return methodName;
    }

    public void setMethodName(Object methodName) {
        this.methodName = methodName;
    }

    public String getMethodDesc() {
        return methodDesc;
    }

    public void setMethodDesc(String methodDesc) {
        this.methodDesc = methodDesc;
    }

    public String getRepairResult() {
        return repairResult;
    }

    public void setRepairResult(String repairResult) {
        this.repairResult = repairResult;
    }

    public Long getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Long completeTime) {
        this.completeTime = completeTime;
    }

    public Object getRepairEmpID() {
        return repairEmpID;
    }

    public void setRepairEmpID(Object repairEmpID) {
        this.repairEmpID = repairEmpID;
    }

    public Object getRepairEmp() {
        return repairEmp;
    }

    public void setRepairEmp(Object repairEmp) {
        this.repairEmp = repairEmp;
    }

    public Object getRepairTeam() {
        return repairTeam;
    }

    public void setRepairTeam(Object repairTeam) {
        this.repairTeam = repairTeam;
    }

    public Object getRepairTeamName() {
        return repairTeamName;
    }

    public void setRepairTeamName(Object repairTeamName) {
        this.repairTeamName = repairTeamName;
    }

    public Object getRepairTeamOrgseq() {
        return repairTeamOrgseq;
    }

    public void setRepairTeamOrgseq(Object repairTeamOrgseq) {
        this.repairTeamOrgseq = repairTeamOrgseq;
    }

    public Object getResponseTeam() {
        return responseTeam;
    }

    public void setResponseTeam(Object responseTeam) {
        this.responseTeam = responseTeam;
    }

    public Object getResponseTeamName() {
        return responseTeamName;
    }

    public void setResponseTeamName(Object responseTeamName) {
        this.responseTeamName = responseTeamName;
    }

    public Object getResponseTeamOrgseq() {
        return responseTeamOrgseq;
    }

    public void setResponseTeamOrgseq(Object responseTeamOrgseq) {
        this.responseTeamOrgseq = responseTeamOrgseq;
    }

    public Integer getCompleteEmpID() {
        return completeEmpID;
    }

    public void setCompleteEmpID(Integer completeEmpID) {
        this.completeEmpID = completeEmpID;
    }

    public String getCompleteEmp() {
        return completeEmp;
    }

    public void setCompleteEmp(String completeEmp) {
        this.completeEmp = completeEmp;
    }

    public Object getResponseEmpID() {
        return responseEmpID;
    }

    public void setResponseEmpID(Object responseEmpID) {
        this.responseEmpID = responseEmpID;
    }

    public Object getResponseEmp() {
        return responseEmp;
    }

    public void setResponseEmp(Object responseEmp) {
        this.responseEmp = responseEmp;
    }

    public Object getProfessionalTypeIdx() {
        return professionalTypeIdx;
    }

    public void setProfessionalTypeIdx(Object professionalTypeIdx) {
        this.professionalTypeIdx = professionalTypeIdx;
    }

    public Object getProfessionalTypeName() {
        return professionalTypeName;
    }

    public void setProfessionalTypeName(Object professionalTypeName) {
        this.professionalTypeName = professionalTypeName;
    }

    public Object getRepairStartTime() {
        return repairStartTime;
    }

    public void setRepairStartTime(Object repairStartTime) {
        this.repairStartTime = repairStartTime;
    }

    public Object getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Object workTime) {
        this.workTime = workTime;
    }

    public Integer getOverRangeStatus() {
        return overRangeStatus;
    }

    public void setOverRangeStatus(Integer overRangeStatus) {
        this.overRangeStatus = overRangeStatus;
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
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

    public Object getFaultReason() {
        return faultReason;
    }

    public void setFaultReason(Object faultReason) {
        this.faultReason = faultReason;
    }

    public Object getDispatchEmpID() {
        return dispatchEmpID;
    }

    public void setDispatchEmpID(Object dispatchEmpID) {
        this.dispatchEmpID = dispatchEmpID;
    }

    public String getDispatchEmp() {
        return dispatchEmp;
    }

    public void setDispatchEmp(String dispatchEmp) {
        this.dispatchEmp = dispatchEmp;
    }

    public String getReasonAnalysisID() {
        return reasonAnalysisID;
    }

    public void setReasonAnalysisID(String reasonAnalysisID) {
        this.reasonAnalysisID = reasonAnalysisID;
    }

    public String getReasonAnalysis() {
        return reasonAnalysis;
    }

    public void setReasonAnalysis(String reasonAnalysis) {
        this.reasonAnalysis = reasonAnalysis;
    }

    public Object getFaultOccurDateStr() {
        return FaultOccurDateStr;
    }

    public void setFaultOccurDateStr(Object FaultOccurDateStr) {
        this.FaultOccurDateStr = FaultOccurDateStr;
    }

    public Object getTicketTimeStr() {
        return ticketTimeStr;
    }

    public void setTicketTimeStr(Object ticketTimeStr) {
        this.ticketTimeStr = ticketTimeStr;
    }

    public Object getTrainTypeAndNo() {
        return trainTypeAndNo;
    }

    public void setTrainTypeAndNo(Object trainTypeAndNo) {
        this.trainTypeAndNo = trainTypeAndNo;
    }

    public Object getCompleteTimeStr() {
        return completeTimeStr;
    }

    public void setCompleteTimeStr(Object completeTimeStr) {
        this.completeTimeStr = completeTimeStr;
    }

    public Object getAffirmUser() {
        return affirmUser;
    }

    public void setAffirmUser(Object affirmUser) {
        this.affirmUser = affirmUser;
    }

    public Object getCheckUser() {
        return checkUser;
    }

    public void setCheckUser(Object checkUser) {
        this.checkUser = checkUser;
    }

    public Object getIsAffirm() {
        return isAffirm;
    }

    public void setIsAffirm(Object isAffirm) {
        this.isAffirm = isAffirm;
    }

    public long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(long operatorId) {
        this.operatorId = operatorId;
    }

    public Object getRepairClassName() {
        return repairClassName;
    }

    public void setRepairClassName(Object repairClassName) {
        this.repairClassName = repairClassName;
    }
}
