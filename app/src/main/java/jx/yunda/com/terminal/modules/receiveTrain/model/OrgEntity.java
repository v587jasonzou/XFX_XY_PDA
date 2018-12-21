package jx.yunda.com.terminal.modules.receiveTrain.model;

import java.io.Serializable;

public class OrgEntity implements Serializable {

    /**
     * id : 01
     * text : 哈尔滨铁路局
     * orgname : 哈
     * name : 哈尔滨铁路局
     * isleaf : true
     * isParent : true
     * code : B
     * leaf : false
     */

    private String id;
    private String text;
    private String orgname;
    private String name;
    private boolean isleaf;
    private boolean isParent;
    private String code;
    private boolean leaf;
    private int myLevel;
    private boolean isOpen = false;
    private String delegateDShortName;
    private String delegateDName;
    private String FjdId;

    public String getFjdId() {
        return FjdId;
    }

    public void setFjdId(String fjdId) {
        FjdId = fjdId;
    }

    public String getDelegateDShortName() {
        return delegateDShortName;
    }

    public void setDelegateDShortName(String delegateDShortName) {
        this.delegateDShortName = delegateDShortName;
    }

    public String getDelegateDName() {
        return delegateDName;
    }

    public void setDelegateDName(String delegateDName) {
        this.delegateDName = delegateDName;
    }

    public int getMyLevel() {
        return myLevel;
    }

    public void setMyLevel(int myLevel) {
        this.myLevel = myLevel;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsleaf() {
        return isleaf;
    }

    public void setIsleaf(boolean isleaf) {
        this.isleaf = isleaf;
    }

    public boolean isIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
}
