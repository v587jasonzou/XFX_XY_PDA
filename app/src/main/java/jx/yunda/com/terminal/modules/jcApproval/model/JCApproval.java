package jx.yunda.com.terminal.modules.jcApproval.model;

public class JCApproval {

    /*主键*/
    private String idx;
    /*操作者*/
    private String operator;
    /*操作者id*/
    private String operatorId;
    /*流程模板id*/
    private String processId;
    /*流程实例id*/
    private String processInstId;
    /*流程名称*/
    private String processName;
    /*修程id*/
    private String repairClassIDX;
    /*修程名称*/
    private String repairClassName;
    /*状态*/
    private String status;
    /*任务键*/
    private String taskKey;
    /*车号*/
    private String trainNo;
    /*车型简称*/
    private String trainTypeShortName;
    /*车型主键*/
    private String trainTypeIDX;
    /* 任务主键*/
    private String workId;
    /*任务名称*/
    private String workName;
    /*计划主键*/
    private String workPlanIdx;
    /* 实际完成时间 */
    private String endTime;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
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

    public String getTrainTypeIDX() {
        return trainTypeIDX;
    }

    public void setTrainTypeIDX(String trainTypeIDX) {
        this.trainTypeIDX = trainTypeIDX;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getWorkPlanIdx() {
        return workPlanIdx;
    }

    public void setWorkPlanIdx(String workPlanIdx) {
        this.workPlanIdx = workPlanIdx;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
