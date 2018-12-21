package jx.yunda.com.terminal.modules.takeCarConfirm.entry;

import java.io.Serializable;

public class AcceptPerson implements Serializable {

    /**
     * empId : 70009
     * empName : 魏少锋
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
