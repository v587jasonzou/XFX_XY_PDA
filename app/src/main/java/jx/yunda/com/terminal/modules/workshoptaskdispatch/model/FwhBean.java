package jx.yunda.com.terminal.modules.workshoptaskdispatch.model;

import java.io.Serializable;

/**
 * Created by hed on 2018/6/11.
 */

public class FwhBean implements Serializable {

    /* 节点任务处理类型 10为选择科室，20为选择班组 */
    public static String NODERDPTYPE_EMP = "10";
    public static String NODERDPTYPE_CLASS = "20";

    /** 状态 - 未开工 */
    public static final String STATUS_UNSTART = "NOTSTARTED";
    public static final String STATUS_UNSTART_CH = "未开工";
    /** 状态 - 已开工 */
    public static final String STATUS_GOING = "RUNNING";
    public static final String STATUS_GOING_CH = "已开工";
    /** 状态-任务处理完成，质量检查处理中 */
    public static final String STATUS_FINISHED = "FINISHED";
    public static final String STATUS_FINISHED_CH = "质检中";
    /** 状态 - 完成 */
    public static final String STATUS_COMPLETE = "COMPLETED";
    public static final String STATUS_COMPLETE_CH = "已完成";
    /** 状态 - 终止 */
    public static final String STATUS_STOP = "TERMINATED";
    public static final String STATUS_STOP_CH = "已终止";
    /** 状态 - 已延期 */
    public static final String STATUS_DELAY = "DELAY";
    public static final String STATUS_DELAY_CH = "已延期";

    /* 是否下发,0:否；1：是 */
    public static int ISSENDNODE_N = 0;
    public static int ISSENDNODE_Y = 1;

    /**
     * nodeRdpType : 20
     * workStationBelongTeam : 1859
     * workerIdsStr : 63221
     * idx : 8a413411604538430160486fc2b00201
     * nodeStatus : NOTSTARTED
     * sendTime : null
     * realEndTime : null
     * nodeName : A车机械-车钩检修
     * parentNodeIdx : 8a413411604538430160486fc2a50200
     * isSendNode : 0
     * processNodeIdx : ff8080815de9003f015de9c6fe220338
     * parentNodeName : A-下部检修
     * workStationIDX : 2c90808b5e7df574015e7e5a4069001b
     * workStationName : 0901抬车位
     * nodeDesc : 牵引装置、车体、钩缓装置
     * workerNameStr : 陈宝顺
     * workStationBelongTeamName : 机械组
     * planBeginTime : 2017-12-15 10:00
     * workStationTypeName : 抬车位
     * workStationTypeIDX : TCW
     * realBeginTime : null
     * planEndTime : 2017-12-15 17:20
     */

    private String nodeRdpType;
    private Long workStationBelongTeam;
    private String workerIdsStr;
    private String workerIdxStr;
    private String idx;
    private String nodeStatus;
    private String nodeIdx;
    private String status;
    private String sendTime;
    private String realEndTime;
    private String nodeName;
    private String parentNodeIdx;
    private int isSendNode;
    private String processNodeIdx;
    private String parentNodeName;
    private String workStationIDX;
    private String workStationName;
    private String nodeDesc;
    private String workerNameStr;
    private String workStationBelongTeamName;
    private String planBeginTime;
    private String workStationTypeName;
    private String workStationTypeIDX;
    private String realBeginTime;
    private String planEndTime;

    public String getNodeRdpType() {
        return nodeRdpType;
    }

    public void setNodeRdpType(String nodeRdpType) {
        this.nodeRdpType = nodeRdpType;
    }

    public Long getWorkStationBelongTeam() {
        return workStationBelongTeam;
    }

    public void setWorkStationBelongTeam(Long workStationBelongTeam) {
        this.workStationBelongTeam = workStationBelongTeam;
    }

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

    public String getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(String nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public Object getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public Object getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(String realEndTime) {
        this.realEndTime = realEndTime;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getParentNodeIdx() {
        return parentNodeIdx;
    }

    public void setParentNodeIdx(String parentNodeIdx) {
        this.parentNodeIdx = parentNodeIdx;
    }

    public int getIsSendNode() {
        return isSendNode;
    }

    public void setIsSendNode(int isSendNode) {
        this.isSendNode = isSendNode;
    }

    public String getProcessNodeIdx() {
        return processNodeIdx;
    }

    public void setProcessNodeIdx(String processNodeIdx) {
        this.processNodeIdx = processNodeIdx;
    }

    public String getParentNodeName() {
        return parentNodeName;
    }

    public void setParentNodeName(String parentNodeName) {
        this.parentNodeName = parentNodeName;
    }

    public String getWorkStationIDX() {
        return workStationIDX;
    }

    public void setWorkStationIDX(String workStationIDX) {
        this.workStationIDX = workStationIDX;
    }

    public String getWorkStationName() {
        return workStationName;
    }

    public void setWorkStationName(String workStationName) {
        this.workStationName = workStationName;
    }

    public String getNodeDesc() {
        return nodeDesc;
    }

    public void setNodeDesc(String nodeDesc) {
        this.nodeDesc = nodeDesc;
    }

    public String getWorkerNameStr() {
        return workerNameStr;
    }

    public void setWorkerNameStr(String workerNameStr) {
        this.workerNameStr = workerNameStr;
    }

    public String getWorkStationBelongTeamName() {
        return workStationBelongTeamName;
    }

    public void setWorkStationBelongTeamName(String workStationBelongTeamName) {
        this.workStationBelongTeamName = workStationBelongTeamName;
    }

    public String getPlanBeginTime() {
        return planBeginTime;
    }

    public void setPlanBeginTime(String planBeginTime) {
        this.planBeginTime = planBeginTime;
    }

    public String getWorkStationTypeName() {
        return workStationTypeName;
    }

    public void setWorkStationTypeName(String workStationTypeName) {
        this.workStationTypeName = workStationTypeName;
    }

    public String getWorkStationTypeIDX() {
        return workStationTypeIDX;
    }

    public void setWorkStationTypeIDX(String workStationTypeIDX) {
        this.workStationTypeIDX = workStationTypeIDX;
    }

    public Object getRealBeginTime() {
        return realBeginTime;
    }

    public void setRealBeginTime(String realBeginTime) {
        this.realBeginTime = realBeginTime;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWorkerIdxStr() {
        return workerIdxStr;
    }

    public void setWorkerIdxStr(String workerIdxStr) {
        this.workerIdxStr = workerIdxStr;
    }

    public String getNodeIdx() {
        return nodeIdx;
    }

    public void setNodeIdx(String nodeIdx) {
        this.nodeIdx = nodeIdx;
    }
}
