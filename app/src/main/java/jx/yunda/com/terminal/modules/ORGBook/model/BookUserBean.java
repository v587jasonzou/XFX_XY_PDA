package jx.yunda.com.terminal.modules.ORGBook.model;

import java.io.Serializable;

public class BookUserBean implements Serializable {
    private int status = 0;
    private String plan;
    /**
     * empid : 63221
     * empcode : 63221
     * empname : 陈宝顺
     * gender : null
     * otel : null
     * mobileno : null
     * orgid : 1853
     * orgname : 质验室
     * orgseq : .0.11.1848.1853.
     */

    private int empid;
    private String empcode;
    private String empname;
    private Object gender;
    private Object otel;
    private Object mobileno;
    private int orgid;
    private String orgname;
    private String orgseq;
    private String planid;
    private String Type;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPlanid() {
        return planid;
    }

    public void setPlanid(String planid) {
        this.planid = planid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
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

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Object getOtel() {
        return otel;
    }

    public void setOtel(Object otel) {
        this.otel = otel;
    }

    public Object getMobileno() {
        return mobileno;
    }

    public void setMobileno(Object mobileno) {
        this.mobileno = mobileno;
    }

    public int getOrgid() {
        return orgid;
    }

    public void setOrgid(int orgid) {
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
