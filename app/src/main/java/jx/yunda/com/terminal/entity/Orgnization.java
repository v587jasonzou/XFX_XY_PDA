package jx.yunda.com.terminal.entity;

/**
 * <li>标题: 机车整备管理信息系统-手持终端
 * <li>说明: 组织机构
 * <li>创建人：刘晓斌
 * <li>创建日期：2015年11月3日
 * <li>修改人: 
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2015 运达科技公司
 * @author 刘晓斌
 * @version 1.0
 */
public class Orgnization {
    private Long orgid;  //机构ID
    private String orgcode;  //机构代码
    private String orgname; //机构名称
    private Long orglevel; //机构层次
    private String orgdegree; //机构等级
    private String orgseq;  //机构序列
    private String orgtype;// 机构类型
    
    public Long getOrgid() {
        return orgid;
    }
    
    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }
    
    public String getOrgcode() {
        return orgcode;
    }
    
    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }
    
    public String getOrgname() {
        return orgname;
    }
    
    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }
    
    public Long getOrglevel() {
        return orglevel;
    }
    
    public void setOrglevel(Long orglevel) {
        this.orglevel = orglevel;
    }
    
    public String getOrgdegree() {
        return orgdegree;
    }
    
    public void setOrgdegree(String orgdegree) {
        this.orgdegree = orgdegree;
    }
    
    public String getOrgseq() {
        return orgseq;
    }
    
    public void setOrgseq(String orgseq) {
        this.orgseq = orgseq;
    }
    
    public String getOrgtype() {
        return orgtype;
    }
    
    public void setOrgtype(String orgtype) {
        this.orgtype = orgtype;
    }

  
}
