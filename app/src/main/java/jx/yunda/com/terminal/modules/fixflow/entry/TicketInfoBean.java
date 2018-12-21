package jx.yunda.com.terminal.modules.fixflow.entry;

import java.io.Serializable;

public class TicketInfoBean implements Serializable {

    /**
     * repairTeamOrgid : 1880
     * repairTeamName : null
     * desc : jdb dbf
     * idx : 8ae96ed266c7f3480166c920625702e8
     * workerNameStr : 陈超
     * orgname : 电器组
     * workPlanIdx : 8ae96ed266c7f3480166c8f1dbe7023c
     * nodeDetailsStatus : 调度未派
     * ticketEmp : 陈超
     * workerManagerNameStr : 方涛,沈志伟
     * ticketTime : 2018-10-31 15:57
     */

    private String repairTeamOrgid;
    private String repairTeamName;
    private String desc;
    private String idx;
    private String workerNameStr;
    private String orgname;
    private String workPlanIdx;
    private String nodeDetailsStatus;
    private String ticketEmp;
    private String workerManagerNameStr;
    private String ticketTime;

    public String getRepairTeamOrgid() {
        return repairTeamOrgid;
    }

    public void setRepairTeamOrgid(String repairTeamOrgid) {
        this.repairTeamOrgid = repairTeamOrgid;
    }

    public String getRepairTeamName() {
        return repairTeamName;
    }

    public void setRepairTeamName(String repairTeamName) {
        this.repairTeamName = repairTeamName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getWorkerNameStr() {
        return workerNameStr;
    }

    public void setWorkerNameStr(String workerNameStr) {
        this.workerNameStr = workerNameStr;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getWorkPlanIdx() {
        return workPlanIdx;
    }

    public void setWorkPlanIdx(String workPlanIdx) {
        this.workPlanIdx = workPlanIdx;
    }

    public String getNodeDetailsStatus() {
        return nodeDetailsStatus;
    }

    public void setNodeDetailsStatus(String nodeDetailsStatus) {
        this.nodeDetailsStatus = nodeDetailsStatus;
    }

    public String getTicketEmp() {
        return ticketEmp;
    }

    public void setTicketEmp(String ticketEmp) {
        this.ticketEmp = ticketEmp;
    }

    public String getWorkerManagerNameStr() {
        return workerManagerNameStr;
    }

    public void setWorkerManagerNameStr(String workerManagerNameStr) {
        this.workerManagerNameStr = workerManagerNameStr;
    }

    public String getTicketTime() {
        return ticketTime;
    }

    public void setTicketTime(String ticketTime) {
        this.ticketTime = ticketTime;
    }
}
