package jx.yunda.com.terminal.modules.schedule.entity;

import java.io.Serializable;
import java.util.List;

public class StationBean implements Serializable {

    /**
     * idx : 40288afc65886c3d016589570a6f0109
     * processIDX : null
     * nodeIDX : 8a8284c265177958016517af90610042
     * workPlanIDX : 40288afc65886c3d016589570a610108
     * nodeName : 机车复检
     * nodeDesc : null
     * seqNo : null
     * ratedWorkMinutes : null
     * realWorkMinutes : null
     * defWorkMinutes : null
     * planBeginTime : null
     * planEndTime : null
     * newPlanBeginTime : null
     * newPlanEndTime : null
     * editStatus : null
     * realBeginTime : null
     * realEndTime : null
     * status : null
     * workStationBelongTeamName : null
     * workStationBelongTeam : null
     * workStationIDX : null
     * workStationName : null
     * workStationTypeIDX : null
     * workStationTypeName : null
     * msgRemind : null
     * parentIDX : null
     * isLeaf : null
     * isSendNode : null
     * sendTime : null
     * sendEmpId : null
     * sendEmpName : null
     * nodeRdpType : 20
     * workCalendarIDX : null
     * planMode : null
     * updator : null
     * createTime : null
     * creator : null
     * siteID : null
     * recordStatus : null
     * updateTime : null
     * startTime : null
     * endTime : null
     * relayTime : null
     * startDay : null
     * endDay : null
     * showFlag : null
     * workerIdxStr : null
     * workerNameStr : null
     * qCBackReason : null
     * rcTrainCategoryCode : null
     * rcTrainCategoryName : null
     * plantOrgID : null
     * plantOrgName : null
     * plantOrgSeq : null
     * activateState : null
     * isActivate : null
     * nodeType : 1
     * emps : []
     * orgs : [{"idx":"8a8284c26518aef4016518b4c37a0004","processIDX":"8a8284c265177958016517aee9810041","nodeIDX":"8a8284c265177958016517af90610042","handeOrgID":1862,"handeOrgName":"总装组","handeOrgSeq":".0.11.1848.1850.1862.","recordStatus":0,"siteID":"1848","creator":63221,"createTime":1533717824000,"updator":63221,"updateTime":1533717824000,"defaultEmpIds":null,"defaultEmpNames":null},{"idx":"8a8284c26518aef4016518b4c1200003","processIDX":"8a8284c265177958016517aee9810041","nodeIDX":"8a8284c265177958016517af90610042","handeOrgID":1861,"handeOrgName":"综合组","handeOrgSeq":".0.11.1848.1850.1861.","recordStatus":0,"siteID":"1848","creator":63221,"createTime":1533717823000,"updator":63221,"updateTime":1533717823000,"defaultEmpIds":null,"defaultEmpNames":null},{"idx":"8a8284c26518aef4016518b4c6c90005","processIDX":"8a8284c265177958016517aee9810041","nodeIDX":"8a8284c265177958016517af90610042","handeOrgID":1854,"handeOrgName":"电器组","handeOrgSeq":".0.11.1848.1850.1854.","recordStatus":0,"siteID":"1848","creator":63221,"createTime":1533717825000,"updator":63221,"updateTime":1533717825000,"defaultEmpIds":null,"defaultEmpNames":null},{"idx":"8a8284c26518aef4016518b4cb370006","processIDX":"8a8284c265177958016517aee9810041","nodeIDX":"8a8284c265177958016517af90610042","handeOrgID":1855,"handeOrgName":"调度组","handeOrgSeq":".0.11.1848.1850.1855.","recordStatus":0,"siteID":"1848","creator":63221,"createTime":1533717826000,"updator":63221,"updateTime":1533717826000,"defaultEmpIds":null,"defaultEmpNames":null}]
     * workStations : [{"idx":"2c90808b5e7df574015e7e5b45bb001f","workStationCode":"000495","workStationName":"1004","repairLineIdx":"2c90808b5e7df574015e7e5aabd1001c","repairLineName":"J10","deskCode":"1004","teamOrgId":null,"teamOrgName":null,"teamOrgSeq":null,"parentIDX":null,"deskName":null,"ownerMap":"1848","status":20,"remarks":null,"recordStatus":0,"siteID":"1848","creator":1,"createTime":1505358464000,"updator":63221,"updateTime":1535439701000,"equipIDX":null,"equipName":null,"repairLineCode":null,"persons":null,"lineType":null,"nodeIDX":null,"twTypeIDX":"BJW","twTypeName":"钣金位"},{"idx":"2c90808b5e7df574015e7e5b6d290020","workStationCode":"000496","workStationName":"1003","repairLineIdx":"2c90808b5e7df574015e7e5aabd1001c","repairLineName":"J10","deskCode":"1003","teamOrgId":null,"teamOrgName":null,"teamOrgSeq":null,"parentIDX":null,"deskName":null,"ownerMap":"1848","status":20,"remarks":null,"recordStatus":0,"siteID":"1848","creator":1,"createTime":1505358474000,"updator":63221,"updateTime":1535439696000,"equipIDX":null,"equipName":null,"repairLineCode":null,"persons":null,"lineType":null,"nodeIDX":null,"twTypeIDX":"BJW","twTypeName":"钣金位"},{"idx":"2c90808b5e7df574015e7e6e38630043","workStationCode":"000518","workStationName":"2301钣金位","repairLineIdx":"2c90808b5e7df574015e7e6ddde90042","repairLineName":"J23","deskCode":"2301","teamOrgId":null,"teamOrgName":null,"teamOrgSeq":null,"parentIDX":null,"deskName":null,"ownerMap":"1848","status":20,"remarks":null,"recordStatus":0,"siteID":"1848","creator":1,"createTime":1505359706000,"updator":69949,"updateTime":1508493360000,"equipIDX":null,"equipName":null,"repairLineCode":null,"persons":null,"lineType":null,"nodeIDX":null,"twTypeIDX":"BJW","twTypeName":"钣金位"},{"idx":"2c90808b5e7df574015e7e6ea7300045","workStationCode":"000519","workStationName":"2401钣金位","repairLineIdx":"2c90808b5e7df574015e7e6e77240044","repairLineName":"J24","deskCode":"2401","teamOrgId":null,"teamOrgName":null,"teamOrgSeq":null,"parentIDX":null,"deskName":null,"ownerMap":"1848","status":20,"remarks":null,"recordStatus":0,"siteID":"1848","creator":1,"createTime":1505359734000,"updator":69949,"updateTime":1508493371000,"equipIDX":null,"equipName":null,"repairLineCode":null,"persons":null,"lineType":null,"nodeIDX":null,"twTypeIDX":"BJW","twTypeName":"钣金位"}]
     */

    private String idx;
    private Object processIDX;
    private String nodeIDX;
    private String workPlanIDX;
    private String nodeName;
    private Object nodeDesc;
    private Object seqNo;
    private Object ratedWorkMinutes;
    private Object realWorkMinutes;
    private Object defWorkMinutes;
    private Object planBeginTime;
    private Object planEndTime;
    private Object newPlanBeginTime;
    private Object newPlanEndTime;
    private Object editStatus;
    private Object realBeginTime;
    private Object realEndTime;
    private Object status;
    private Object workStationBelongTeamName;
    private Object workStationBelongTeam;
    private Object workStationIDX;
    private String workStationName;
    private Object workStationTypeIDX;
    private Object workStationTypeName;
    private Object msgRemind;
    private Object parentIDX;
    private Object isLeaf;
    private Object isSendNode;
    private Object sendTime;
    private Object sendEmpId;
    private Object sendEmpName;
    private String nodeRdpType;
    private Object workCalendarIDX;
    private Object planMode;
    private Object updator;
    private Object createTime;
    private Object creator;
    private Object siteID;
    private Object recordStatus;
    private Object updateTime;
    private Object startTime;
    private Object endTime;
    private Object relayTime;
    private Object startDay;
    private Object endDay;
    private Object showFlag;
    private Object workerIdxStr;
    private Object workerNameStr;
    private Object qCBackReason;
    private Object rcTrainCategoryCode;
    private Object rcTrainCategoryName;
    private Object plantOrgID;
    private Object plantOrgName;
    private Object plantOrgSeq;
    private Object activateState;
    private Object isActivate;
    private int nodeType;
    private List<?> emps;
    private List<OrgsBean> orgs;
    private List<WorkStationsBean> workStations;
    private String teamOrgIds;
    private String teamOrgName;

    public String getTeamOrgIds() {
        return teamOrgIds;
    }

    public void setTeamOrgIds(String teamOrgIds) {
        this.teamOrgIds = teamOrgIds;
    }

    public String getTeamOrgName() {
        return teamOrgName;
    }

    public void setTeamOrgName(String teamOrgName) {
        this.teamOrgName = teamOrgName;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public Object getProcessIDX() {
        return processIDX;
    }

    public void setProcessIDX(Object processIDX) {
        this.processIDX = processIDX;
    }

    public String getNodeIDX() {
        return nodeIDX;
    }

    public void setNodeIDX(String nodeIDX) {
        this.nodeIDX = nodeIDX;
    }

    public String getWorkPlanIDX() {
        return workPlanIDX;
    }

    public void setWorkPlanIDX(String workPlanIDX) {
        this.workPlanIDX = workPlanIDX;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Object getNodeDesc() {
        return nodeDesc;
    }

    public void setNodeDesc(Object nodeDesc) {
        this.nodeDesc = nodeDesc;
    }

    public Object getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Object seqNo) {
        this.seqNo = seqNo;
    }

    public Object getRatedWorkMinutes() {
        return ratedWorkMinutes;
    }

    public void setRatedWorkMinutes(Object ratedWorkMinutes) {
        this.ratedWorkMinutes = ratedWorkMinutes;
    }

    public Object getRealWorkMinutes() {
        return realWorkMinutes;
    }

    public void setRealWorkMinutes(Object realWorkMinutes) {
        this.realWorkMinutes = realWorkMinutes;
    }

    public Object getDefWorkMinutes() {
        return defWorkMinutes;
    }

    public void setDefWorkMinutes(Object defWorkMinutes) {
        this.defWorkMinutes = defWorkMinutes;
    }

    public Object getPlanBeginTime() {
        return planBeginTime;
    }

    public void setPlanBeginTime(Object planBeginTime) {
        this.planBeginTime = planBeginTime;
    }

    public Object getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(Object planEndTime) {
        this.planEndTime = planEndTime;
    }

    public Object getNewPlanBeginTime() {
        return newPlanBeginTime;
    }

    public void setNewPlanBeginTime(Object newPlanBeginTime) {
        this.newPlanBeginTime = newPlanBeginTime;
    }

    public Object getNewPlanEndTime() {
        return newPlanEndTime;
    }

    public void setNewPlanEndTime(Object newPlanEndTime) {
        this.newPlanEndTime = newPlanEndTime;
    }

    public Object getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Object editStatus) {
        this.editStatus = editStatus;
    }

    public Object getRealBeginTime() {
        return realBeginTime;
    }

    public void setRealBeginTime(Object realBeginTime) {
        this.realBeginTime = realBeginTime;
    }

    public Object getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(Object realEndTime) {
        this.realEndTime = realEndTime;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getWorkStationBelongTeamName() {
        return workStationBelongTeamName;
    }

    public void setWorkStationBelongTeamName(Object workStationBelongTeamName) {
        this.workStationBelongTeamName = workStationBelongTeamName;
    }

    public Object getWorkStationBelongTeam() {
        return workStationBelongTeam;
    }

    public void setWorkStationBelongTeam(Object workStationBelongTeam) {
        this.workStationBelongTeam = workStationBelongTeam;
    }

    public Object getWorkStationIDX() {
        return workStationIDX;
    }

    public void setWorkStationIDX(Object workStationIDX) {
        this.workStationIDX = workStationIDX;
    }

    public String getWorkStationName() {
        return workStationName;
    }

    public void setWorkStationName(String workStationName) {
        this.workStationName = workStationName;
    }

    public Object getWorkStationTypeIDX() {
        return workStationTypeIDX;
    }

    public void setWorkStationTypeIDX(Object workStationTypeIDX) {
        this.workStationTypeIDX = workStationTypeIDX;
    }

    public Object getWorkStationTypeName() {
        return workStationTypeName;
    }

    public void setWorkStationTypeName(Object workStationTypeName) {
        this.workStationTypeName = workStationTypeName;
    }

    public Object getMsgRemind() {
        return msgRemind;
    }

    public void setMsgRemind(Object msgRemind) {
        this.msgRemind = msgRemind;
    }

    public Object getParentIDX() {
        return parentIDX;
    }

    public void setParentIDX(Object parentIDX) {
        this.parentIDX = parentIDX;
    }

    public Object getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Object isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Object getIsSendNode() {
        return isSendNode;
    }

    public void setIsSendNode(Object isSendNode) {
        this.isSendNode = isSendNode;
    }

    public Object getSendTime() {
        return sendTime;
    }

    public void setSendTime(Object sendTime) {
        this.sendTime = sendTime;
    }

    public Object getSendEmpId() {
        return sendEmpId;
    }

    public void setSendEmpId(Object sendEmpId) {
        this.sendEmpId = sendEmpId;
    }

    public Object getSendEmpName() {
        return sendEmpName;
    }

    public void setSendEmpName(Object sendEmpName) {
        this.sendEmpName = sendEmpName;
    }

    public String getNodeRdpType() {
        return nodeRdpType;
    }

    public void setNodeRdpType(String nodeRdpType) {
        this.nodeRdpType = nodeRdpType;
    }

    public Object getWorkCalendarIDX() {
        return workCalendarIDX;
    }

    public void setWorkCalendarIDX(Object workCalendarIDX) {
        this.workCalendarIDX = workCalendarIDX;
    }

    public Object getPlanMode() {
        return planMode;
    }

    public void setPlanMode(Object planMode) {
        this.planMode = planMode;
    }

    public Object getUpdator() {
        return updator;
    }

    public void setUpdator(Object updator) {
        this.updator = updator;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public Object getCreator() {
        return creator;
    }

    public void setCreator(Object creator) {
        this.creator = creator;
    }

    public Object getSiteID() {
        return siteID;
    }

    public void setSiteID(Object siteID) {
        this.siteID = siteID;
    }

    public Object getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Object recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public Object getStartTime() {
        return startTime;
    }

    public void setStartTime(Object startTime) {
        this.startTime = startTime;
    }

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public Object getRelayTime() {
        return relayTime;
    }

    public void setRelayTime(Object relayTime) {
        this.relayTime = relayTime;
    }

    public Object getStartDay() {
        return startDay;
    }

    public void setStartDay(Object startDay) {
        this.startDay = startDay;
    }

    public Object getEndDay() {
        return endDay;
    }

    public void setEndDay(Object endDay) {
        this.endDay = endDay;
    }

    public Object getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(Object showFlag) {
        this.showFlag = showFlag;
    }

    public Object getWorkerIdxStr() {
        return workerIdxStr;
    }

    public void setWorkerIdxStr(Object workerIdxStr) {
        this.workerIdxStr = workerIdxStr;
    }

    public Object getWorkerNameStr() {
        return workerNameStr;
    }

    public void setWorkerNameStr(Object workerNameStr) {
        this.workerNameStr = workerNameStr;
    }

    public Object getQCBackReason() {
        return qCBackReason;
    }

    public void setQCBackReason(Object qCBackReason) {
        this.qCBackReason = qCBackReason;
    }

    public Object getRcTrainCategoryCode() {
        return rcTrainCategoryCode;
    }

    public void setRcTrainCategoryCode(Object rcTrainCategoryCode) {
        this.rcTrainCategoryCode = rcTrainCategoryCode;
    }

    public Object getRcTrainCategoryName() {
        return rcTrainCategoryName;
    }

    public void setRcTrainCategoryName(Object rcTrainCategoryName) {
        this.rcTrainCategoryName = rcTrainCategoryName;
    }

    public Object getPlantOrgID() {
        return plantOrgID;
    }

    public void setPlantOrgID(Object plantOrgID) {
        this.plantOrgID = plantOrgID;
    }

    public Object getPlantOrgName() {
        return plantOrgName;
    }

    public void setPlantOrgName(Object plantOrgName) {
        this.plantOrgName = plantOrgName;
    }

    public Object getPlantOrgSeq() {
        return plantOrgSeq;
    }

    public void setPlantOrgSeq(Object plantOrgSeq) {
        this.plantOrgSeq = plantOrgSeq;
    }

    public Object getActivateState() {
        return activateState;
    }

    public void setActivateState(Object activateState) {
        this.activateState = activateState;
    }

    public Object getIsActivate() {
        return isActivate;
    }

    public void setIsActivate(Object isActivate) {
        this.isActivate = isActivate;
    }

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public List<?> getEmps() {
        return emps;
    }

    public void setEmps(List<?> emps) {
        this.emps = emps;
    }

    public List<OrgsBean> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<OrgsBean> orgs) {
        this.orgs = orgs;
    }

    public List<WorkStationsBean> getWorkStations() {
        return workStations;
    }

    public void setWorkStations(List<WorkStationsBean> workStations) {
        this.workStations = workStations;
    }

    public static class OrgsBean implements Serializable {
        /**
         * idx : 8a8284c26518aef4016518b4c37a0004
         * processIDX : 8a8284c265177958016517aee9810041
         * nodeIDX : 8a8284c265177958016517af90610042
         * handeOrgID : 1862
         * handeOrgName : 总装组
         * handeOrgSeq : .0.11.1848.1850.1862.
         * recordStatus : 0
         * siteID : 1848
         * creator : 63221
         * createTime : 1533717824000
         * updator : 63221
         * updateTime : 1533717824000
         * defaultEmpIds : null
         * defaultEmpNames : null
         */

        private String idx;
        private String processIDX;
        private String nodeIDX;
        private int handeOrgID;
        private String handeOrgName;
        private String handeOrgSeq;
        private int recordStatus;
        private String siteID;
        private int creator;
        private long createTime;
        private int updator;
        private long updateTime;
        private Object defaultEmpIds;
        private Object defaultEmpNames;

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

        public String getNodeIDX() {
            return nodeIDX;
        }

        public void setNodeIDX(String nodeIDX) {
            this.nodeIDX = nodeIDX;
        }

        public int getHandeOrgID() {
            return handeOrgID;
        }

        public void setHandeOrgID(int handeOrgID) {
            this.handeOrgID = handeOrgID;
        }

        public String getHandeOrgName() {
            return handeOrgName;
        }

        public void setHandeOrgName(String handeOrgName) {
            this.handeOrgName = handeOrgName;
        }

        public String getHandeOrgSeq() {
            return handeOrgSeq;
        }

        public void setHandeOrgSeq(String handeOrgSeq) {
            this.handeOrgSeq = handeOrgSeq;
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

        public Object getDefaultEmpIds() {
            return defaultEmpIds;
        }

        public void setDefaultEmpIds(Object defaultEmpIds) {
            this.defaultEmpIds = defaultEmpIds;
        }

        public Object getDefaultEmpNames() {
            return defaultEmpNames;
        }

        public void setDefaultEmpNames(Object defaultEmpNames) {
            this.defaultEmpNames = defaultEmpNames;
        }
    }

    public static class WorkStationsBean implements Serializable {
        /**
         * idx : 2c90808b5e7df574015e7e5b45bb001f
         * workStationCode : 000495
         * workStationName : 1004
         * repairLineIdx : 2c90808b5e7df574015e7e5aabd1001c
         * repairLineName : J10
         * deskCode : 1004
         * teamOrgId : null
         * teamOrgName : null
         * teamOrgSeq : null
         * parentIDX : null
         * deskName : null
         * ownerMap : 1848
         * status : 20
         * remarks : null
         * recordStatus : 0
         * siteID : 1848
         * creator : 1
         * createTime : 1505358464000
         * updator : 63221
         * updateTime : 1535439701000
         * equipIDX : null
         * equipName : null
         * repairLineCode : null
         * persons : null
         * lineType : null
         * nodeIDX : null
         * twTypeIDX : BJW
         * twTypeName : 钣金位
         */

        private String idx;
        private String workStationCode;
        private String workStationName;
        private String repairLineIdx;
        private String repairLineName;
        private String deskCode;
        private Object teamOrgId;
        private Object teamOrgName;
        private Object teamOrgSeq;
        private Object parentIDX;
        private Object deskName;
        private String ownerMap;
        private int status;
        private Object remarks;
        private int recordStatus;
        private String siteID;
        private int creator;
        private long createTime;
        private int updator;
        private long updateTime;
        private Object equipIDX;
        private Object equipName;
        private Object repairLineCode;
        private Object persons;
        private Object lineType;
        private Object nodeIDX;
        private String twTypeIDX;
        private String twTypeName;

        public String getIdx() {
            return idx;
        }

        public void setIdx(String idx) {
            this.idx = idx;
        }

        public String getWorkStationCode() {
            return workStationCode;
        }

        public void setWorkStationCode(String workStationCode) {
            this.workStationCode = workStationCode;
        }

        public String getWorkStationName() {
            return workStationName;
        }

        public void setWorkStationName(String workStationName) {
            this.workStationName = workStationName;
        }

        public String getRepairLineIdx() {
            return repairLineIdx;
        }

        public void setRepairLineIdx(String repairLineIdx) {
            this.repairLineIdx = repairLineIdx;
        }

        public String getRepairLineName() {
            return repairLineName;
        }

        public void setRepairLineName(String repairLineName) {
            this.repairLineName = repairLineName;
        }

        public String getDeskCode() {
            return deskCode;
        }

        public void setDeskCode(String deskCode) {
            this.deskCode = deskCode;
        }

        public Object getTeamOrgId() {
            return teamOrgId;
        }

        public void setTeamOrgId(Object teamOrgId) {
            this.teamOrgId = teamOrgId;
        }

        public Object getTeamOrgName() {
            return teamOrgName;
        }

        public void setTeamOrgName(Object teamOrgName) {
            this.teamOrgName = teamOrgName;
        }

        public Object getTeamOrgSeq() {
            return teamOrgSeq;
        }

        public void setTeamOrgSeq(Object teamOrgSeq) {
            this.teamOrgSeq = teamOrgSeq;
        }

        public Object getParentIDX() {
            return parentIDX;
        }

        public void setParentIDX(Object parentIDX) {
            this.parentIDX = parentIDX;
        }

        public Object getDeskName() {
            return deskName;
        }

        public void setDeskName(Object deskName) {
            this.deskName = deskName;
        }

        public String getOwnerMap() {
            return ownerMap;
        }

        public void setOwnerMap(String ownerMap) {
            this.ownerMap = ownerMap;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
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

        public Object getEquipIDX() {
            return equipIDX;
        }

        public void setEquipIDX(Object equipIDX) {
            this.equipIDX = equipIDX;
        }

        public Object getEquipName() {
            return equipName;
        }

        public void setEquipName(Object equipName) {
            this.equipName = equipName;
        }

        public Object getRepairLineCode() {
            return repairLineCode;
        }

        public void setRepairLineCode(Object repairLineCode) {
            this.repairLineCode = repairLineCode;
        }

        public Object getPersons() {
            return persons;
        }

        public void setPersons(Object persons) {
            this.persons = persons;
        }

        public Object getLineType() {
            return lineType;
        }

        public void setLineType(Object lineType) {
            this.lineType = lineType;
        }

        public Object getNodeIDX() {
            return nodeIDX;
        }

        public void setNodeIDX(Object nodeIDX) {
            this.nodeIDX = nodeIDX;
        }

        public String getTwTypeIDX() {
            return twTypeIDX;
        }

        public void setTwTypeIDX(String twTypeIDX) {
            this.twTypeIDX = twTypeIDX;
        }

        public String getTwTypeName() {
            return twTypeName;
        }

        public void setTwTypeName(String twTypeName) {
            this.twTypeName = twTypeName;
        }
    }
}
