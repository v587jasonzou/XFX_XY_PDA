package jx.yunda.com.terminal.modules.quality.model;

import java.io.Serializable;
import java.util.List;

public class TrainBoby  implements Serializable{
    List<QualityTrainBean> entityList;

    public List<QualityTrainBean> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<QualityTrainBean> entityList) {
        this.entityList = entityList;
    }
}
