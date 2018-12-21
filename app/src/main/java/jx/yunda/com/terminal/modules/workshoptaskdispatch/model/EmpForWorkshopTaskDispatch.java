package jx.yunda.com.terminal.modules.workshoptaskdispatch.model;

import java.io.Serializable;

/**
 * Created by hed on 2018/6/11.
 */

public class EmpForWorkshopTaskDispatch implements Serializable {

    String id;
    String text;
    private Boolean isChecked;

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(String id) {
        this.id = id;
    }
}
