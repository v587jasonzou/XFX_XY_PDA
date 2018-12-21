package jx.yunda.com.terminal.modules.tpprocess.model;

import java.io.Serializable;
import java.util.List;

public class StandardBean implements Serializable {
    private String stepName;
    private List<StandardChildBean> contentList;

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public List<StandardChildBean> getContentList() {
        return contentList;
    }

    public void setContentList(List<StandardChildBean> contentList) {
        this.contentList = contentList;
    }
}
