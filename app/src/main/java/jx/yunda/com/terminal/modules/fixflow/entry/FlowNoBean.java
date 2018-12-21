package jx.yunda.com.terminal.modules.fixflow.entry;

import java.io.Serializable;

public class FlowNoBean implements Serializable {
    private Integer tpUnfinishedNo;
    private Integer fwUnfinishedNo;
    private Integer fwFinishedNo;
    private Integer tpFinishedNo;

    public Integer getTpUnfinishedNo() {
        return tpUnfinishedNo;
    }

    public void setTpUnfinishedNo(Integer tpUnfinishedNo) {
        this.tpUnfinishedNo = tpUnfinishedNo;
    }

    public Integer getFwUnfinishedNo() {
        return fwUnfinishedNo;
    }

    public void setFwUnfinishedNo(Integer fwUnfinishedNo) {
        this.fwUnfinishedNo = fwUnfinishedNo;
    }

    public Integer getFwFinishedNo() {
        return fwFinishedNo;
    }

    public void setFwFinishedNo(Integer fwFinishedNo) {
        this.fwFinishedNo = fwFinishedNo;
    }

    public Integer getTpFinishedNo() {
        return tpFinishedNo;
    }

    public void setTpFinishedNo(Integer tpFinishedNo) {
        this.tpFinishedNo = tpFinishedNo;
    }
}
