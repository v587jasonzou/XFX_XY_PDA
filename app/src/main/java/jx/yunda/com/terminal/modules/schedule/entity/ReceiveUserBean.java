package jx.yunda.com.terminal.modules.schedule.entity;

import java.io.Serializable;

public class ReceiveUserBean implements Serializable {

    /**
     * empId : 70001
     * empName : 张三
     */

    private String empId;
    private String empName;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
}
