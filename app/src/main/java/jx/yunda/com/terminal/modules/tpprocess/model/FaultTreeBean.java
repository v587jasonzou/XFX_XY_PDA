package jx.yunda.com.terminal.modules.tpprocess.model;

import java.io.Serializable;

public class FaultTreeBean implements Serializable {

    /**
     * flbm : 012209
     * zylx : null
     * text : 车载安全监测监测设备
     * zylxID : null
     * isParent : true
     * gxwzbm : null
     * leaf : false
     * fljc : 车载安全监测监测设备
     * fjdID : e8a94d0a2bb945e0b56e790a8dc604ed
     * sycx : HXD1
     * id : 29165bc23f65408bb96d0d6bc0e61f1d
     * lbjbm : null
     * wzqm : HXD1/交流电力/车载安全监测监测设备
     * pyjc : czaqjcjcsb
     * level : 1
     * name : 车载安全监测监测设备
     * coID : 29165bc23f65408bb96d0d6bc0e61f1d
     */

    private String flbm;
    private String zylx;
    private String text;
    private String zylxID;
    private boolean isParent;
    private String gxwzbm;
    private boolean leaf;
    private String fljc;
    private String fjdID;
    private String sycx;
    private String id;
    private String lbjbm;
    private String wzqm;
    private String pyjc;
    private int level;
    private String name;
    private String coID;
    private int myLevel;
    private boolean isOpen = false;


    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getMyLevel() {
        return myLevel;
    }

    public void setMyLevel(int myLevel) {
        this.myLevel = myLevel;
    }

    public String getFlbm() {
        return flbm;
    }

    public void setFlbm(String flbm) {
        this.flbm = flbm;
    }

    public String getZylx() {
        return zylx;
    }

    public void setZylx(String zylx) {
        this.zylx = zylx;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getZylxID() {
        return zylxID;
    }

    public void setZylxID(String zylxID) {
        this.zylxID = zylxID;
    }

    public boolean isIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public String getGxwzbm() {
        return gxwzbm;
    }

    public void setGxwzbm(String gxwzbm) {
        this.gxwzbm = gxwzbm;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getFljc() {
        return fljc;
    }

    public void setFljc(String fljc) {
        this.fljc = fljc;
    }

    public String getFjdID() {
        return fjdID;
    }

    public void setFjdID(String fjdID) {
        this.fjdID = fjdID;
    }

    public String getSycx() {
        return sycx;
    }

    public void setSycx(String sycx) {
        this.sycx = sycx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLbjbm() {
        return lbjbm;
    }

    public void setLbjbm(String lbjbm) {
        this.lbjbm = lbjbm;
    }

    public String getWzqm() {
        return wzqm;
    }

    public void setWzqm(String wzqm) {
        this.wzqm = wzqm;
    }

    public String getPyjc() {
        return pyjc;
    }

    public void setPyjc(String pyjc) {
        this.pyjc = pyjc;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoID() {
        return coID;
    }

    public void setCoID(String coID) {
        this.coID = coID;
    }
}
