package jx.yunda.com.terminal.modules.ORGBookNew.model;

import java.io.Serializable;
import java.util.List;

public class BookNodeResponse implements Serializable {
    public List<Nodebean> doList;
    public List<Nodebean> unList;

    public List<Nodebean> getDoList() {
        return doList;
    }

    public void setDoList(List<Nodebean> doList) {
        this.doList = doList;
    }

    public List<Nodebean> getUnList() {
        return unList;
    }

    public void setUnList(List<Nodebean> unList) {
        this.unList = unList;
    }
}
