package jx.yunda.com.terminal.modules.ORGBookNew.model;

import java.io.Serializable;
import java.util.List;

public class BookUserBeanNew implements Serializable{

    /**
     * empId : 70163
     * fwhNum : null
     * empName : 陈畅
     * jt28Num : 1
     */

    private Long empId;
    private Integer fwhNum;
    private String empName;
    private Integer jt28Num;
    private List<Nodebean> nodes;
    private int states = 0;

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public List<Nodebean> getNodes() {
        return nodes;
    }

    public void setNodes(List<Nodebean> nodes) {
        this.nodes = nodes;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Integer getFwhNum() {
        return fwhNum;
    }

    public void setFwhNum(Integer fwhNum) {
        this.fwhNum = fwhNum;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getJt28Num() {
        return jt28Num;
    }

    public void setJt28Num(Integer jt28Num) {
        this.jt28Num = jt28Num;
    }
}
