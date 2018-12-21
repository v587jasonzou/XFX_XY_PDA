package jx.yunda.com.terminal.entity;

/**
 * <li>标题: 机车整备管理信息系统-手持终端
 * <li>说明: 人员
 * <li>创建人：刘晓斌
 * <li>创建日期：2015年11月3日
 * <li>修改人: 
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2015 运达科技公司
 * @author 刘晓斌
 * @version 1.0
 */
public class Employee {
	/** 选中 */
	public static final int CHECKED = 0;
	/** 未选中 */
	public static final int UNCHECKED = 1;
    private Long empid; // 人员编号
    private String empcode; // 人员代码
    private Long operatorid; // 操作员编号
    private String userid; // 操作员登录号
    private String empname; // 人员姓名
    private String gender; // 性别
    private Long orgid; // 主机构编号
    private String orgname;// 机构名称
    private String orgseq;// 机构序列号
    
    /** 是否勾选：0 勾选，1 未勾选 */
	private Integer checked = UNCHECKED;
	
	public Employee() {
		super();
	}

	public Employee(Long empid, String empname) {
		super();
		this.empid = empid;
		this.empname = empname;
	}

	public Integer getChecked() {
		return checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}
    
    public Long getEmpid() {
        return empid;
    }
    
    public void setEmpid(Long empid) {
        this.empid = empid;
    }
    
    public String getEmpcode() {
        return empcode;
    }
    
    public void setEmpcode(String empcode) {
        this.empcode = empcode;
    }
    
    public Long getOperatorid() {
        return operatorid;
    }
    
    public void setOperatorid(Long operatorid) {
        this.operatorid = operatorid;
    }
    
    public String getUserid() {
        return userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    public String getEmpname() {
        return empname;
    }
    
    public void setEmpname(String empname) {
        this.empname = empname;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public Long getOrgid() {
        return orgid;
    }
    
    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }
    
    public String getOrgname() {
        return orgname;
    }
    
    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getOrgseq() {
        return orgseq;
    }

    public void setOrgseq(String orgSeq) {
        this.orgseq = orgSeq;
    }
}
