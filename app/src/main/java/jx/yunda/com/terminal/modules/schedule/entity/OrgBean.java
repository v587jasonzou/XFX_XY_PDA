package jx.yunda.com.terminal.modules.schedule.entity;

import java.io.Serializable;
import java.util.List;

public class OrgBean implements Serializable {

    /**
     * handeOrgSeq : .0.11.1848.1850.1854.
     * nodeRdpType : 20
     * workStationBelongTeam : 1855
     * idx : 8a8284c26511e79c016512628216019b
     * handeOrgId : 1854
     * nodeName : 机车复检
     * nodeNames :
     * handeOrgName : 电器组
     * nodeIdx : 297edff8644adf3f01644ee0b5db00d3
     * workStationBelongTeamName : è°？？o|？？？
     * nodeCaseIdxs :
     * nodeCaseIdx : 8a8284c26513382f016513aac38201ae
     * nodeList : [{"nodeName":"机车复检","nodeCaseIdx":"8a8284c26513382f016513aac38201ae"}]
     */

    private String handeOrgSeq;
    private String nodeRdpType;
    private int workStationBelongTeam;
    private String idx;
    private Long handeOrgID;
    private String nodeName;
    private String nodeNames;
    private String handeOrgName;
    private String nodeIdx;
    private String workStationBelongTeamName;
    private String nodeCaseIdxs;
    private String nodeCaseIdx;
    private int status = 0;
    private List<NodeListBean> nodeList;
    private List<ScheduleTrainBean> trainList;
    private String trainTypeShortName;
    private String workPlanIdx;
    private String repairClassName;
    private String trainNo;
    private String repairClassIdx;

    public String getTrainTypeShortName() {
        return trainTypeShortName;
    }

    public void setTrainTypeShortName(String trainTypeShortName) {
        this.trainTypeShortName = trainTypeShortName;
    }

    public String getWorkPlanIdx() {
        return workPlanIdx;
    }

    public void setWorkPlanIdx(String workPlanIdx) {
        this.workPlanIdx = workPlanIdx;
    }

    public String getRepairClassName() {
        return repairClassName;
    }

    public void setRepairClassName(String repairClassName) {
        this.repairClassName = repairClassName;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getRepairClassIdx() {
        return repairClassIdx;
    }

    public void setRepairClassIdx(String repairClassIdx) {
        this.repairClassIdx = repairClassIdx;
    }

    public Long getHandeOrgID() {
        return handeOrgID;
    }

    public void setHandeOrgID(Long handeOrgID) {
        this.handeOrgID = handeOrgID;
    }

    public List<ScheduleTrainBean> getTrainList() {
        return trainList;
    }

    public void setTrainList(List<ScheduleTrainBean> trainList) {
        this.trainList = trainList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHandeOrgSeq() {
        return handeOrgSeq;
    }

    public void setHandeOrgSeq(String handeOrgSeq) {
        this.handeOrgSeq = handeOrgSeq;
    }

    public String getNodeRdpType() {
        return nodeRdpType;
    }

    public void setNodeRdpType(String nodeRdpType) {
        this.nodeRdpType = nodeRdpType;
    }

    public int getWorkStationBelongTeam() {
        return workStationBelongTeam;
    }

    public void setWorkStationBelongTeam(int workStationBelongTeam) {
        this.workStationBelongTeam = workStationBelongTeam;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeNames() {
        return nodeNames;
    }

    public void setNodeNames(String nodeNames) {
        this.nodeNames = nodeNames;
    }

    public String getHandeOrgName() {
        return handeOrgName;
    }

    public void setHandeOrgName(String handeOrgName) {
        this.handeOrgName = handeOrgName;
    }

    public String getNodeIdx() {
        return nodeIdx;
    }

    public void setNodeIdx(String nodeIdx) {
        this.nodeIdx = nodeIdx;
    }

    public String getWorkStationBelongTeamName() {
        return workStationBelongTeamName;
    }

    public void setWorkStationBelongTeamName(String workStationBelongTeamName) {
        this.workStationBelongTeamName = workStationBelongTeamName;
    }

    public String getNodeCaseIdxs() {
        return nodeCaseIdxs;
    }

    public void setNodeCaseIdxs(String nodeCaseIdxs) {
        this.nodeCaseIdxs = nodeCaseIdxs;
    }

    public String getNodeCaseIdx() {
        return nodeCaseIdx;
    }

    public void setNodeCaseIdx(String nodeCaseIdx) {
        this.nodeCaseIdx = nodeCaseIdx;
    }

    public List<NodeListBean> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<NodeListBean> nodeList) {
        this.nodeList = nodeList;
    }

    public static class NodeListBean implements Serializable {
        /**
         * nodeName : 机车复检
         * nodeCaseIdx : 8a8284c26513382f016513aac38201ae
         */

        private String nodeName;
        private String nodeCaseIdx;

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public String getNodeCaseIdx() {
            return nodeCaseIdx;
        }

        public void setNodeCaseIdx(String nodeCaseIdx) {
            this.nodeCaseIdx = nodeCaseIdx;
        }
    }
}
