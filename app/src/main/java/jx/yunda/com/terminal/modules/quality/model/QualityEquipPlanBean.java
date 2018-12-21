package jx.yunda.com.terminal.modules.quality.model;

import java.io.Serializable;

public class QualityEquipPlanBean implements Serializable {

    /**
     * rdpIdx : 8a828489638fc3de01638ff8f288000e
     * realStarttime : 2018-05-24 10:27
     * unloadTraintype : HXD1
     * unloadTrainno : 0001
     * specificationModel : HXD3C-高压隔离开关
     * qcItemName : 车间质检
     * realEndtime : 2018-06-12 14:00
     * repairEmpNames : 陈东
     * planEndtime : 2018-05-24 10:27
     * partsName : 高压隔离开关
     * qcItemNo : checkfarmperoson
     * partsNo : 9994
     * repairOrgname : 电器组
     * unloadPlace : 4
     * planStarttime : 2018-05-24 10:27
     * repairOrgid : 1854
     */

    private String rdpIdx;
    private String realStarttime;
    private String unloadTraintype;
    private String unloadTrainno;
    private String specificationModel;
    private String qcItemName;
    private String realEndtime;
    private String repairEmpNames;
    private String planEndtime;
    private String partsName;
    private String qcItemNo;
    private String partsNo;
    private String repairOrgname;
    private String unloadPlace;
    private String planStarttime;
    private int repairOrgid;

    public String getRdpIdx() {
        return rdpIdx;
    }

    public void setRdpIdx(String rdpIdx) {
        this.rdpIdx = rdpIdx;
    }

    public String getRealStarttime() {
        return realStarttime;
    }

    public void setRealStarttime(String realStarttime) {
        this.realStarttime = realStarttime;
    }

    public String getUnloadTraintype() {
        return unloadTraintype;
    }

    public void setUnloadTraintype(String unloadTraintype) {
        this.unloadTraintype = unloadTraintype;
    }

    public String getUnloadTrainno() {
        return unloadTrainno;
    }

    public void setUnloadTrainno(String unloadTrainno) {
        this.unloadTrainno = unloadTrainno;
    }

    public String getSpecificationModel() {
        return specificationModel;
    }

    public void setSpecificationModel(String specificationModel) {
        this.specificationModel = specificationModel;
    }

    public String getQcItemName() {
        return qcItemName;
    }

    public void setQcItemName(String qcItemName) {
        this.qcItemName = qcItemName;
    }

    public String getRealEndtime() {
        return realEndtime;
    }

    public void setRealEndtime(String realEndtime) {
        this.realEndtime = realEndtime;
    }

    public String getRepairEmpNames() {
        return repairEmpNames;
    }

    public void setRepairEmpNames(String repairEmpNames) {
        this.repairEmpNames = repairEmpNames;
    }

    public String getPlanEndtime() {
        return planEndtime;
    }

    public void setPlanEndtime(String planEndtime) {
        this.planEndtime = planEndtime;
    }

    public String getPartsName() {
        return partsName;
    }

    public void setPartsName(String partsName) {
        this.partsName = partsName;
    }

    public String getQcItemNo() {
        return qcItemNo;
    }

    public void setQcItemNo(String qcItemNo) {
        this.qcItemNo = qcItemNo;
    }

    public String getPartsNo() {
        return partsNo;
    }

    public void setPartsNo(String partsNo) {
        this.partsNo = partsNo;
    }

    public String getRepairOrgname() {
        return repairOrgname;
    }

    public void setRepairOrgname(String repairOrgname) {
        this.repairOrgname = repairOrgname;
    }

    public String getUnloadPlace() {
        return unloadPlace;
    }

    public void setUnloadPlace(String unloadPlace) {
        this.unloadPlace = unloadPlace;
    }

    public String getPlanStarttime() {
        return planStarttime;
    }

    public void setPlanStarttime(String planStarttime) {
        this.planStarttime = planStarttime;
    }

    public int getRepairOrgid() {
        return repairOrgid;
    }

    public void setRepairOrgid(int repairOrgid) {
        this.repairOrgid = repairOrgid;
    }
}
