package jx.yunda.com.terminal.modules.TrainRecandition.model;

import java.io.Serializable;
import java.util.List;

public class ItemResultRespons implements Serializable {
    List<ItemResultBean> list;

    public List<ItemResultBean> getList() {
        return list;
    }

    public void setList(List<ItemResultBean> list) {
        this.list = list;
    }
}
