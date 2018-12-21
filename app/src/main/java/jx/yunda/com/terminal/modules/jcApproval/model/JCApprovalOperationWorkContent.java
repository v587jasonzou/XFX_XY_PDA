package jx.yunda.com.terminal.modules.jcApproval.model;

public class JCApprovalOperationWorkContent {
    private String idx;
    private String workPlanIdx;
    /*提票描述*/
    private String faultDesc;
    /*提票描述*/
    private String dispatchEmpId;
    /*处理人员名称*/
    private String dispatchEmp;

    public boolean isShowBackBtn = true;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getWorkPlanIdx() {
        return workPlanIdx;
    }

    public void setWorkPlanIdx(String workPlanIdx) {
        this.workPlanIdx = workPlanIdx;
    }

    public String getFaultDesc() {
        return faultDesc;
    }

    public void setFaultDesc(String faultDesc) {
        this.faultDesc = faultDesc;
    }

    public String getDispatchEmpId() {
        return dispatchEmpId;
    }

    public void setDispatchEmpId(String dispatchEmpId) {
        this.dispatchEmpId = dispatchEmpId;
    }

    public String getDispatchEmp() {
        return dispatchEmp;
    }

    public void setDispatchEmp(String dispatchEmp) {
        this.dispatchEmp = dispatchEmp;
    }
}
