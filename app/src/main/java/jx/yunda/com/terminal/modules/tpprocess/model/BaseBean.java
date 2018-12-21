package jx.yunda.com.terminal.modules.tpprocess.model;

import java.io.Serializable;
import java.util.List;

import jx.yunda.com.terminal.entity.FileBaseBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.OrderBean;
import jx.yunda.com.terminal.modules.quality.model.NodeBean;

public class BaseBean<T> implements Serializable {
    private int totalProperty;
    private T root;
    private boolean success;
    private String errMsg;
    private String id;
    private List<FileBaseBean> fileUrlList;
    private String msg;
    private List<NodeBean> nodeList;
    private List<OrderBean> workCardBeanList;
    public List<OrderBean> getWorkCardBeanList() {
        return workCardBeanList;
    }

    public void setWorkCardBeanList(List<OrderBean> workCardBeanList) {
        this.workCardBeanList = workCardBeanList;
    }

    public List<NodeBean> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<NodeBean> nodeList) {
        this.nodeList = nodeList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<FileBaseBean> getFileUrlList() {
        return fileUrlList;
    }

    public void setFileUrlList(List<FileBaseBean> fileUrlList) {
        this.fileUrlList = fileUrlList;
    }

    public int getTotalProperty() {
        return totalProperty;
    }

    public void setTotalProperty(int totalProperty) {
        this.totalProperty = totalProperty;
    }

    public T getRoot() {
        return root;
    }

    public void setRoot(T root) {
        this.root = root;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
