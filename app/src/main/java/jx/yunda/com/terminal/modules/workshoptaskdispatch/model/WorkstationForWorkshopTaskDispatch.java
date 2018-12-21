package jx.yunda.com.terminal.modules.workshoptaskdispatch.model;

import java.io.Serializable;

/**
 * Created by hed on 2018/6/11.
 */

public class WorkstationForWorkshopTaskDispatch implements Serializable {

    String idx;
    String workStationName;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getWorkStationName() {
        return workStationName;
    }

    public void setWorkStationName(String workStationName) {
        this.workStationName = workStationName;
    }
}
