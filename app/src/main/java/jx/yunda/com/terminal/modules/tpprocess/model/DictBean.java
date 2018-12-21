package jx.yunda.com.terminal.modules.tpprocess.model;

import java.io.Serializable;
import java.util.List;

public class DictBean implements Serializable {

    /**
     * id : 10
     * pId : null
     * text : 专业
     * name : 专业
     * isParent : true
     * parentid : null
     * leaf : false
     */

    private String id;
    private String pId;
    private String text;
    private String name;
    private boolean isParent;
    private String parentid;
    private boolean leaf;
    private List<DictBean> childList;
    private boolean checked;
    boolean isGetChildSuccess = false;
    private String filter1;

    public String getFilter1() {
        return filter1;
    }

    public void setFilter1(String filter1) {
        this.filter1 = filter1;
    }

    public boolean isGetChildSuccess() {
        return isGetChildSuccess;
    }

    public void setGetChildSuccess(boolean getChildSuccess) {
        isGetChildSuccess = getChildSuccess;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<DictBean> getChildList() {
        return childList;
    }

    public void setChildList(List<DictBean> childList) {
        this.childList = childList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
}
