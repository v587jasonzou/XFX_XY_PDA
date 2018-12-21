package jx.yunda.com.terminal.widget.treeview.bean;




import java.io.Serializable;

import jx.yunda.com.terminal.widget.treeview.utils.annotation.TreeNodeId;
import jx.yunda.com.terminal.widget.treeview.utils.annotation.TreeNodeLabel;
import jx.yunda.com.terminal.widget.treeview.utils.annotation.TreeNodePid;

/**
 * @author sloop
 * @ClassName: TreeBean
 * @Description:
 * @date 2015年2月21日 上午2:45:13
 */
public class TreeBean implements Serializable {

    /**
     * 当前id
     */
    @TreeNodeId
    private String id;
    /**
     * 父节点id
     */
    @TreeNodePid
    private String pId;
    /**
     * 标记名称
     */
    @TreeNodeLabel
    private String lable;

    private String sortLetters;

    private boolean isRoot;
    private boolean isLeaf;

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public TreeBean() {
    }

    public TreeBean(String id, String pId, String lable) {
        this.id = id;
        this.pId = pId;
        this.lable = lable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }


}
