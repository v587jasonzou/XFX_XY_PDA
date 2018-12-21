package jx.yunda.com.terminal.modules.main.model;

/**
 * <li>标题: 设备管理系统
 * <li>说明: 用户待办任务
 * <li>创建人：黄杨
 * <li>创建日期：2017年1月14日
 * <li>修改人:
 * <li>修改日期：
 * <li>修改内容：
 * <li>版权: Copyright (c) 2008 运达科技公司
 * @author 信息系统事业部设备管理系统项目组
 * @version 1.0
 */
public class TodoJob {

	/** 任务数量 */
	private Integer jobNum;

	/** 任务标题 */
	private String jobText;

	/** 任务类型 */
	private String jobType;

	/** 任务类型Id */
	private Integer jobTypeId;

	/** 任务地址 */
	private String jobUrl;

    private String objcetIDXString;

	public Integer getJobNum() {
		return jobNum;
	}

	public void setJobNum(Integer jobNum) {
		this.jobNum = jobNum;
	}

	public String getJobText() {
		return jobText;
	}

	public void setJobText(String jobText) {
		this.jobText = jobText;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public Integer getJobTypeId() {
		return jobTypeId;
	}

	public void setJobTypeId(Integer jobTypeId) {
		this.jobTypeId = jobTypeId;
	}

	public String getJobUrl() {
		return jobUrl;
	}

	public void setJobUrl(String jobUrl) {
		this.jobUrl = jobUrl;
	}

    public String getObjcetIDXString() {
        return objcetIDXString;
    }

    public void setObjcetIDXString(String objcetIDXString) {
        this.objcetIDXString = objcetIDXString;
    }
}
