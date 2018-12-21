package jx.yunda.com.terminal.modules.quality.model;

import java.io.Serializable;

public class NodeBean implements Serializable {

    /**
     * idx : E48A6FE0179143B9A827799965F86863
     * rdpIDX : 8a8284a36394f511016394f98d330004
     * wpNodeIDX : 8a82848963720b1e0163721d6ece0011
     * parentWPNodeIDX : ROOT_0
     * wpNodeName : <新节点>
     * wpNodeDesc : 检修
     * backContent : null
     * ratedPeriod : 0.0
     * seqNo : 1
     * isLeaf : 1
     * planStartTime : 1527212760000
     * planEndTime : 1527212760000
     * realStartTime : 1527212774000
     * realEndTime : 1527213858000
     * status : 03
     * recordStatus : 0
     * siteID : 1848
     * creator : 66427
     * createTime : 1527212772000
     * updator : 66427
     * updateTime : 1527213858000
     * nodeLevel : 1
     * calendarIdx : ff8080815b096999015b096e5a070002
     * handleOrgID : 1854
     * handleOrgName : 电器组
     * showFlag : square
     * isThisChange : false
     * partsRdp : null
     * thisChange : false
     */

    private String idx;
    private String rdpIDX;
    private String wpNodeIDX;
    private String parentWPNodeIDX;
    private String wpNodeName;
    private String wpNodeDesc;
    private Object backContent;
    private double ratedPeriod;
    private int seqNo;
    private int isLeaf;
    private long planStartTime;
    private long planEndTime;
    private long realStartTime;
    private long realEndTime;
    private String status;
    private int recordStatus;
    private String siteID;
    private int creator;
    private long createTime;
    private int updator;
    private long updateTime;
    private int nodeLevel;
    private String calendarIdx;
    private int handleOrgID;
    private String handleOrgName;
    private String showFlag;
    private boolean isThisChange;
    private Object partsRdp;
    private boolean thisChange;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getRdpIDX() {
        return rdpIDX;
    }

    public void setRdpIDX(String rdpIDX) {
        this.rdpIDX = rdpIDX;
    }

    public String getWpNodeIDX() {
        return wpNodeIDX;
    }

    public void setWpNodeIDX(String wpNodeIDX) {
        this.wpNodeIDX = wpNodeIDX;
    }

    public String getParentWPNodeIDX() {
        return parentWPNodeIDX;
    }

    public void setParentWPNodeIDX(String parentWPNodeIDX) {
        this.parentWPNodeIDX = parentWPNodeIDX;
    }

    public String getWpNodeName() {
        return wpNodeName;
    }

    public void setWpNodeName(String wpNodeName) {
        this.wpNodeName = wpNodeName;
    }

    public String getWpNodeDesc() {
        return wpNodeDesc;
    }

    public void setWpNodeDesc(String wpNodeDesc) {
        this.wpNodeDesc = wpNodeDesc;
    }

    public Object getBackContent() {
        return backContent;
    }

    public void setBackContent(Object backContent) {
        this.backContent = backContent;
    }

    public double getRatedPeriod() {
        return ratedPeriod;
    }

    public void setRatedPeriod(double ratedPeriod) {
        this.ratedPeriod = ratedPeriod;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public int getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(int isLeaf) {
        this.isLeaf = isLeaf;
    }

    public long getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(long planStartTime) {
        this.planStartTime = planStartTime;
    }

    public long getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(long planEndTime) {
        this.planEndTime = planEndTime;
    }

    public long getRealStartTime() {
        return realStartTime;
    }

    public void setRealStartTime(long realStartTime) {
        this.realStartTime = realStartTime;
    }

    public long getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(long realEndTime) {
        this.realEndTime = realEndTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(int nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public String getCalendarIdx() {
        return calendarIdx;
    }

    public void setCalendarIdx(String calendarIdx) {
        this.calendarIdx = calendarIdx;
    }

    public int getHandleOrgID() {
        return handleOrgID;
    }

    public void setHandleOrgID(int handleOrgID) {
        this.handleOrgID = handleOrgID;
    }

    public String getHandleOrgName() {
        return handleOrgName;
    }

    public void setHandleOrgName(String handleOrgName) {
        this.handleOrgName = handleOrgName;
    }

    public String getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(String showFlag) {
        this.showFlag = showFlag;
    }

    public boolean isIsThisChange() {
        return isThisChange;
    }

    public void setIsThisChange(boolean isThisChange) {
        this.isThisChange = isThisChange;
    }

    public Object getPartsRdp() {
        return partsRdp;
    }

    public void setPartsRdp(Object partsRdp) {
        this.partsRdp = partsRdp;
    }

    public boolean isThisChange() {
        return thisChange;
    }

    public void setThisChange(boolean thisChange) {
        this.thisChange = thisChange;
    }
}
