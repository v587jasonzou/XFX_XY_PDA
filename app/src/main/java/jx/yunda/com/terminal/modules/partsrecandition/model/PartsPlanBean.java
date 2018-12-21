package jx.yunda.com.terminal.modules.partsrecandition.model;

import java.io.Serializable;

public class PartsPlanBean implements Serializable {

    /**
     * status : 02
     * idx : 0FFCE62A86C44C12B72A2B54D06E1841
     * workStationName : null
     * rdpIDX : 8a828489638fc3de01638ff8f288000e
     * identificationCode : 18011803006236
     * partsNo : 9994
     * specificationModel : HXD3C-高压隔离开关
     * partsName : 高压隔离开关
     * workStationIDX : null
     * planEndTime : 2018-05-24 10:27:00
     * realEndTime : null
     * unloadPlace : 4
     * wpNodeName : <新节点>
     * planStartTime : 2018-05-24 10:27:00
     * unloadTrainType : HXD1
     * unloadTrainNo : 0001
     * unloadRepairClass : C4
     * unloadRepairTime : 一次
     * realStartTime : 2018-05-24 10:27:28
     * partsAccountIDX : 8a8284a361551ddf0161553169cb005e
     * repairOrgName : 电器组
     * partsOffCount : null
     * partsOnCount : null
     * taskCounts : 0
     * backCounts : 0
     */

    private String status;
    private String idx;
    private Object workStationName;
    private String rdpIDX;
    private String identificationCode;
    private String partsNo;
    private String specificationModel;
    private String partsName;
    private Object workStationIDX;
    private String planEndTime;
    private String realEndTime;
    private String unloadPlace;
    private String wpNodeName;
    private String planStartTime;
    private String unloadTrainType;
    private String unloadTrainNo;
    private String unloadRepairClass;
    private String unloadRepairTime;
    private String realStartTime;
    private String partsAccountIDX;
    private String repairOrgName;
    private String partsOffCount;
    private String partsOnCount;
    private int taskCounts;
    private int backCounts;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public Object getWorkStationName() {
        return workStationName;
    }

    public void setWorkStationName(Object workStationName) {
        this.workStationName = workStationName;
    }

    public String getRdpIDX() {
        return rdpIDX;
    }

    public void setRdpIDX(String rdpIDX) {
        this.rdpIDX = rdpIDX;
    }

    public String getIdentificationCode() {
        return identificationCode;
    }

    public void setIdentificationCode(String identificationCode) {
        this.identificationCode = identificationCode;
    }

    public String getPartsNo() {
        return partsNo;
    }

    public void setPartsNo(String partsNo) {
        this.partsNo = partsNo;
    }

    public String getSpecificationModel() {
        return specificationModel;
    }

    public void setSpecificationModel(String specificationModel) {
        this.specificationModel = specificationModel;
    }

    public String getPartsName() {
        return partsName;
    }

    public void setPartsName(String partsName) {
        this.partsName = partsName;
    }

    public Object getWorkStationIDX() {
        return workStationIDX;
    }

    public void setWorkStationIDX(Object workStationIDX) {
        this.workStationIDX = workStationIDX;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(String realEndTime) {
        this.realEndTime = realEndTime;
    }

    public String getUnloadPlace() {
        return unloadPlace;
    }

    public void setUnloadPlace(String unloadPlace) {
        this.unloadPlace = unloadPlace;
    }

    public String getWpNodeName() {
        return wpNodeName;
    }

    public void setWpNodeName(String wpNodeName) {
        this.wpNodeName = wpNodeName;
    }

    public String getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(String planStartTime) {
        this.planStartTime = planStartTime;
    }

    public String getUnloadTrainType() {
        return unloadTrainType;
    }

    public void setUnloadTrainType(String unloadTrainType) {
        this.unloadTrainType = unloadTrainType;
    }

    public String getUnloadTrainNo() {
        return unloadTrainNo;
    }

    public void setUnloadTrainNo(String unloadTrainNo) {
        this.unloadTrainNo = unloadTrainNo;
    }

    public String getUnloadRepairClass() {
        return unloadRepairClass;
    }

    public void setUnloadRepairClass(String unloadRepairClass) {
        this.unloadRepairClass = unloadRepairClass;
    }

    public String getUnloadRepairTime() {
        return unloadRepairTime;
    }

    public void setUnloadRepairTime(String unloadRepairTime) {
        this.unloadRepairTime = unloadRepairTime;
    }

    public String getRealStartTime() {
        return realStartTime;
    }

    public void setRealStartTime(String realStartTime) {
        this.realStartTime = realStartTime;
    }

    public String getPartsAccountIDX() {
        return partsAccountIDX;
    }

    public void setPartsAccountIDX(String partsAccountIDX) {
        this.partsAccountIDX = partsAccountIDX;
    }

    public String getRepairOrgName() {
        return repairOrgName;
    }

    public void setRepairOrgName(String repairOrgName) {
        this.repairOrgName = repairOrgName;
    }

    public String getPartsOffCount() {
        return partsOffCount;
    }

    public void setPartsOffCount(String partsOffCount) {
        this.partsOffCount = partsOffCount;
    }

    public String getPartsOnCount() {
        return partsOnCount;
    }

    public void setPartsOnCount(String partsOnCount) {
        this.partsOnCount = partsOnCount;
    }

    public int getTaskCounts() {
        return taskCounts;
    }

    public void setTaskCounts(int taskCounts) {
        this.taskCounts = taskCounts;
    }

    public int getBackCounts() {
        return backCounts;
    }

    public void setBackCounts(int backCounts) {
        this.backCounts = backCounts;
    }
}
