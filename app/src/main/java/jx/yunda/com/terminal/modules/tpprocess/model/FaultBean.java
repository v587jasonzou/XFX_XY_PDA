package jx.yunda.com.terminal.modules.tpprocess.model;

import java.io.Serializable;

public class FaultBean implements Serializable {

    /**
     * faultName : 无故障
     * faultId : 1
     */

    private String faultName;
    private String faultId;

    public String getFaultName() {
        return faultName;
    }

    public void setFaultName(String faultName) {
        this.faultName = faultName;
    }

    public String getFaultId() {
        return faultId;
    }

    public void setFaultId(String faultId) {
        this.faultId = faultId;
    }
}
