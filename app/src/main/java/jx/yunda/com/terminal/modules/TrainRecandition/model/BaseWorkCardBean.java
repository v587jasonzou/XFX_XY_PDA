package jx.yunda.com.terminal.modules.TrainRecandition.model;

import java.io.Serializable;
import java.util.List;

public class BaseWorkCardBean<T> implements Serializable {
   private List<T> workCardBeanList;

    public List<T> getWorkCardBeanList() {
        return workCardBeanList;
    }

    public void setWorkCardBeanList(List<T> workCardBeanList) {
        this.workCardBeanList = workCardBeanList;
    }
    public List<InspectorOrderBean> workTaskBeanList;

    public List<InspectorOrderBean> getWorkTaskBeanList() {
        return workTaskBeanList;
    }

    public void setWorkTaskBeanList(List<InspectorOrderBean> workTaskBeanList) {
        this.workTaskBeanList = workTaskBeanList;
    }
}
