package jx.yunda.com.terminal.modules.tpprocess.model;

import java.io.Serializable;

public class TicketTypeBean implements Serializable {

    /**
     * dictid : 10
     * dictname : 入段复检
     */

    private String dictid;
    private String dictname;
    private int state = 0;
    private int UnSubmit = 0;
    private String filter1;
    private String filter2;

    public String getFilter1() {
        return filter1;
    }

    public void setFilter1(String filter1) {
        this.filter1 = filter1;
    }

    public String getFilter2() {
        return filter2;
    }

    public void setFilter2(String filter2) {
        this.filter2 = filter2;
    }

    public int getUnSubmit() {
        return UnSubmit;
    }

    public void setUnSubmit(int unSubmit) {
        UnSubmit = unSubmit;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDictid() {
        return dictid;
    }

    public void setDictid(String dictid) {
        this.dictid = dictid;
    }

    public String getDictname() {
        return dictname;
    }

    public void setDictname(String dictname) {
        this.dictname = dictname;
    }
}
