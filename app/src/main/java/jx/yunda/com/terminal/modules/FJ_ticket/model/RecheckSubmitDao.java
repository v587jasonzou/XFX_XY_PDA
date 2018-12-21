package jx.yunda.com.terminal.modules.FJ_ticket.model;

import java.io.Serializable;

import io.realm.RealmObject;

public class RecheckSubmitDao extends RealmObject implements Serializable {
    private String idx;
    private long operatorid;
    private String trainNo;
    private String trainTypeIDX;
    private String trainTypeShortName;
    private String faultDesc;
    private String faultFixPlaceIDX;
    private String fixPlaceFullCode;
    private String fixPlaceFullName;
    private long faultOccurDate;
    private String reasonAnalysisZhuanYe;
    private String reasonAnalysisZhuanYeID;
    private String reasonAnalysisKaoHe;
    private String reasonAnalysisKaoHeId;
    private String resolutionContent;
    private String type;
    private long dispatchEmpID;

    public long getDispatchEmpID() {
        return dispatchEmpID;
    }

    public void setDispatchEmpID(long dispatchEmpID) {
        this.dispatchEmpID = dispatchEmpID;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
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

    public String getFixPlaceFullCode() {
        return fixPlaceFullCode;
    }

    public void setFixPlaceFullCode(String fixPlaceFullCode) {
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

    public String getReasonAnalysisZhuanYe() {
        return reasonAnalysisZhuanYe;
    }

    public void setReasonAnalysisZhuanYe(String reasonAnalysisZhuanYe) {
        this.reasonAnalysisZhuanYe = reasonAnalysisZhuanYe;
    }

    public String getReasonAnalysisZhuanYeID() {
        return reasonAnalysisZhuanYeID;
    }

    public void setReasonAnalysisZhuanYeID(String reasonAnalysisZhuanYeID) {
        this.reasonAnalysisZhuanYeID = reasonAnalysisZhuanYeID;
    }

    public String getReasonAnalysisKaoHe() {
        return reasonAnalysisKaoHe;
    }

    public void setReasonAnalysisKaoHe(String reasonAnalysisKaoHe) {
        this.reasonAnalysisKaoHe = reasonAnalysisKaoHe;
    }

    public String getReasonAnalysisKaoHeId() {
        return reasonAnalysisKaoHeId;
    }

    public void setReasonAnalysisKaoHeId(String reasonAnalysisKaoHeId) {
        this.reasonAnalysisKaoHeId = reasonAnalysisKaoHeId;
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

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public long getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(long operatorid) {
        this.operatorid = operatorid;
    }

}
