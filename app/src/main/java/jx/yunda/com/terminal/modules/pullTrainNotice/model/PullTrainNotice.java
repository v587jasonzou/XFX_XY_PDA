package jx.yunda.com.terminal.modules.pullTrainNotice.model;

import java.io.Serializable;

public class PullTrainNotice implements Serializable {
    private String idx;
    private String trainTypeIDX;
    private String trainTypeShortName;
    private String trainNo;
    private String repairClassName;
    private String no;
    private String dictname;
    private String groupId;
    private String allNoticeNum;
    private String confirmNoticeNum;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getTrainTypeIDX() {
        return trainTypeIDX;
    }

    public void setTrainTypeIDX(String trainTypeIDX) {
        this.trainTypeIDX = trainTypeIDX;
    }

    public String getTrainTypeShortName() {
        return trainTypeShortName;
    }

    public void setTrainTypeShortName(String trainTypeShortName) {
        this.trainTypeShortName = trainTypeShortName;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getRepairClassName() {
        return repairClassName;
    }

    public void setRepairClassName(String repairClassName) {
        this.repairClassName = repairClassName;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDictname() {
        return dictname;
    }

    public void setDictname(String dictname) {
        this.dictname = dictname;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAllNoticeNum() {
        return allNoticeNum;
    }

    public void setAllNoticeNum(String allNoticeNum) {
        this.allNoticeNum = allNoticeNum;
    }

    public String getConfirmNoticeNum() {
        return confirmNoticeNum;
    }

    public void setConfirmNoticeNum(String confirmNoticeNum) {
        this.confirmNoticeNum = confirmNoticeNum;
    }
}
