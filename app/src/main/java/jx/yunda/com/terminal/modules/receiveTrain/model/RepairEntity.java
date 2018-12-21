package jx.yunda.com.terminal.modules.receiveTrain.model;

import java.io.Serializable;

public class RepairEntity implements Serializable {

    /**
     * xcID : 11
     * TrainTypeIdx : 240
     * xcName : 辅修
     * xcType : 辅
     * undertakeOrgId : 1848
     */

    private String xcID;
    private String TrainTypeIdx;
    private String xcName;
    private String xcType;
    private int undertakeOrgId;
    private String xcCode;

    public String getXcCode() {
        return xcCode;
    }

    public void setXcCode(String xcCode) {
        this.xcCode = xcCode;
    }

    public String getXcID() {
        return xcID;
    }

    public void setXcID(String xcID) {
        this.xcID = xcID;
    }

    public String getTrainTypeIdx() {
        return TrainTypeIdx;
    }

    public void setTrainTypeIdx(String TrainTypeIdx) {
        this.TrainTypeIdx = TrainTypeIdx;
    }

    public String getXcName() {
        return xcName;
    }

    public void setXcName(String xcName) {
        this.xcName = xcName;
    }

    public String getXcType() {
        return xcType;
    }

    public void setXcType(String xcType) {
        this.xcType = xcType;
    }

    public int getUndertakeOrgId() {
        return undertakeOrgId;
    }

    public void setUndertakeOrgId(int undertakeOrgId) {
        this.undertakeOrgId = undertakeOrgId;
    }
}
