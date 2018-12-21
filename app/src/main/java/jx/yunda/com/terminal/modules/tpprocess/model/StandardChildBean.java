package jx.yunda.com.terminal.modules.tpprocess.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StandardChildBean implements Serializable {

    private String idx; //
    private String content; //
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
