package jx.yunda.com.terminal.entity;

import java.util.List;

/**
 * 回调信息统一封装类
 * Created by hed on 2017/10/30.
 */
public class BaseResultEntity<T> {
    /** 记录总数 */
    private Integer totalProperty = 0;
    /** 值对象 */
    private T vo;
    /** 分页后的记录 */
    private List<T> root;
    /** 操作结果 true成功，false失败 */
    private Boolean success;
    /** 错误信息 */
    private String errMsg;
    /** 主键字段名称 */
    private String id = null;

    /**
     * <li>说明：默认空构造方法
     * <li>创建人：何东
     * <li>创建日期：2017-10-30
     * <li>修改人：
     * <li>修改日期：
     */
    public BaseResultEntity(){}

    /**
     * <li>说明：构造方法
     * <li>创建人：何东
     * <li>创建日期：2017-10-30
     * <li>修改人：
     * <li>修改日期：
     * @param list 实体对象集合
     */
    public BaseResultEntity(List<T> list){
        this.root = list;
        if(list != null)    this.totalProperty = list.size();
    }

    /**
     * <li>说明：构造方法
     * <li>创建人：何东
     * <li>创建日期：2017-10-30
     * <li>修改人：
     * <li>修改日期：
     * @param vo 实体对象
     */
    public BaseResultEntity(T vo){
        this.vo = vo;
    }

    public Integer getTotalProperty() {
        return totalProperty;
    }
    public void setTotalProperty(Integer totalProperty) {
        this.totalProperty = totalProperty;
    }
    public Boolean getSuccess() {
        return success;
    }
    public void setSuccess(Boolean success) {
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

    public T getVo() {
        return vo;
    }

    public void setVo(T vo) {
        this.vo = vo;
    }

    public List<T> getRoot() {
        return root;
    }

    public void setRoot(List<T> root) {
        this.root = root;
    }
}
