package jx.yunda.com.terminal.modules.fixflow.entry;

import java.io.Serializable;

public class FwhInfoBean implements Serializable {

    /**
     * workerIdsStr : null
     * idx : 4028800066c7dc7f0166c7fae48e008f
     * workerNameStr : null
     * status : NOTSTARTED
     * orgname : 制动组
     * belongTeamIdx : 1878
     * nodeDetailsStatus : 制动组关兵,张吉文未派
     * empNames : 关兵,张吉文
     * nodeName : 【制动组】高压试验
     * belongTeamName : 制动组
     * cardUnfinishedNo : 0
     * cardFinishedNo : 0
     */

    private String workerIdsStr;
    private String idx;
    private String workerNameStr;
    private String status;
    private String orgname;
    private Integer belongTeamIdx;
    private String nodeDetailsStatus;
    private String empNames;
    private String nodeName;
    private String belongTeamName;
    private Integer cardUnfinishedNo;
    private Integer cardFinishedNo;

    public String getWorkerIdsStr() {
        return workerIdsStr;
    }

    public void setWorkerIdsStr(String workerIdsStr) {
        this.workerIdsStr = workerIdsStr;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public Integer getBelongTeamIdx() {
        return belongTeamIdx;
    }

    public void setBelongTeamIdx(Integer belongTeamIdx) {
        this.belongTeamIdx = belongTeamIdx;
    }

    public String getNodeDetailsStatus() {
        return nodeDetailsStatus;
    }

    public void setNodeDetailsStatus(String nodeDetailsStatus) {
        this.nodeDetailsStatus = nodeDetailsStatus;
    }

    public String getEmpNames() {
        return empNames;
    }

    public void setEmpNames(String empNames) {
        this.empNames = empNames;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getBelongTeamName() {
        return belongTeamName;
    }

    public void setBelongTeamName(String belongTeamName) {
        this.belongTeamName = belongTeamName;
    }

    public Integer getCardUnfinishedNo() {
        return cardUnfinishedNo;
    }

    public void setCardUnfinishedNo(Integer cardUnfinishedNo) {
        this.cardUnfinishedNo = cardUnfinishedNo;
    }

    public Integer getCardFinishedNo() {
        return cardFinishedNo;
    }

    public void setCardFinishedNo(Integer cardFinishedNo) {
        this.cardFinishedNo = cardFinishedNo;
    }
}
