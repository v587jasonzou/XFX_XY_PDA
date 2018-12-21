package jx.yunda.com.terminal.modules.foremandispatch.model;

import java.io.Serializable;

/**
 * Created by hed on 2018/6/11.
 */

public class EmpForForemanDispatch implements Serializable {


    /**
     * empid : 69814
     * empcode : 69814
     * empname : 白华军
     * gender : null
     * otel : null
     * mobileno : null
     * orgid : 1855
     * orgname : 调度组
     * orgseq : .0.11.1848.1850.1855.
     */

    private Long empid;
    private String empcode;
    private String empname;
    private String gender;
    private String  otel;
    private String mobileno;
    private Long orgid;
    private String orgname;
    private String orgseq;
    private Boolean isChecked;

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public Long getEmpid() {
        return empid;
    }

    public void setEmpid(Long empid) {
        this.empid = empid;
    }

    public String getEmpcode() {
        return empcode;
    }

    public void setEmpcode(String empcode) {
        this.empcode = empcode;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOtel() {
        return otel;
    }

    public void setOtel(String otel) {
        this.otel = otel;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getOrgseq() {
        return orgseq;
    }

    public void setOrgseq(String orgseq) {
        this.orgseq = orgseq;
    }
}
