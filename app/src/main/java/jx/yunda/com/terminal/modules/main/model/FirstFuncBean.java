package jx.yunda.com.terminal.modules.main.model;

import java.io.Serializable;
import java.util.List;

public class FirstFuncBean implements Serializable {
    private String funcgroupid;
    private String funcgroupname;
    private List<SecondFuncBean> functionChildList;

    public String getFuncgroupid() {
        return funcgroupid;
    }

    public void setFuncgroupid(String funcgroupid) {
        this.funcgroupid = funcgroupid;
    }

    public String getFuncgroupname() {
        return funcgroupname;
    }

    public void setFuncgroupname(String funcgroupname) {
        this.funcgroupname = funcgroupname;
    }

    public List<SecondFuncBean> getFunctionChildList() {
        return functionChildList;
    }

    public void setFunctionChildList(List<SecondFuncBean> functionChildList) {
        this.functionChildList = functionChildList;
    }
}
