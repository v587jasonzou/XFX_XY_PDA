package jx.yunda.com.terminal.modules.main.model;

import java.io.Serializable;

public class SecondFuncBean implements Serializable {

    /**
     * funccode : lhpj
     * funcgroupname : pda客户端应用
     * funcaction : 1
     * funcgroupid : 1000000
     * funcname : 良好配件登记
     */

    private String funccode;
    private String funcgroupname;
    private String funcaction;
    private Integer funcgroupid;
    private String funcname;
    private Integer unfinishedNo;
    private MenuConfig menuConfig;
    private Boolean isFirst = false;

    public Integer getUnfinishedNo() {
        return unfinishedNo;
    }

    public void setUnfinishedNo(Integer unfinishedNo) {
        this.unfinishedNo = unfinishedNo;
    }

    public Boolean getFirst() {
        return isFirst;
    }

    public void setFirst(Boolean first) {
        isFirst = first;
    }

    public MenuConfig getMenuConfig() {
        return menuConfig;
    }

    public void setMenuConfig(MenuConfig menuConfig) {
        this.menuConfig = menuConfig;
    }

    public String getFunccode() {
        return funccode;
    }

    public void setFunccode(String funccode) {
        this.funccode = funccode;
    }

    public String getFuncgroupname() {
        return funcgroupname;
    }

    public void setFuncgroupname(String funcgroupname) {
        this.funcgroupname = funcgroupname;
    }

    public String getFuncaction() {
        return funcaction;
    }

    public void setFuncaction(String funcaction) {
        this.funcaction = funcaction;
    }

    public Integer getFuncgroupid() {
        return funcgroupid;
    }

    public void setFuncgroupid(Integer funcgroupid) {
        this.funcgroupid = funcgroupid;
    }

    public String getFuncname() {
        return funcname;
    }

    public void setFuncname(String funcname) {
        this.funcname = funcname;
    }
}
