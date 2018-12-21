package jx.yunda.com.terminal.modules.message.model;

public class MessageGroupPerson {
    public boolean isChecked = false;
    private String idx;
    /* 人员状态 1:正常，2:移除 */
    private Integer userStatus;
    /* 群ID */
    private String groupId;
    /* 人员ID */
    private String empId;
    /* 人员名称 */
    private String empName;
    /* 人员岗位 */
    private String empPost;
    /* 最后查看消息时间 */
    private String lastReadTime;
    /* 添加人员时间 */
    private String addUserTime;
    /* 移除人员时间 */
    private String removeUserTime;
    /* 人员姓名拼音 */
    private String namePinyIn;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpPost() {
        return empPost;
    }

    public void setEmpPost(String empPost) {
        this.empPost = empPost;
    }

    public String getLastReadTime() {
        return lastReadTime;
    }

    public void setLastReadTime(String lastReadTime) {
        this.lastReadTime = lastReadTime;
    }

    public String getAddUserTime() {
        return addUserTime;
    }

    public void setAddUserTime(String addUserTime) {
        this.addUserTime = addUserTime;
    }

    public String getRemoveUserTime() {
        return removeUserTime;
    }

    public void setRemoveUserTime(String removeUserTime) {
        this.removeUserTime = removeUserTime;
    }

    public String getNamePinyIn() {
        return namePinyIn;
    }

    public void setNamePinyIn(String namePinyIn) {
        this.namePinyIn = namePinyIn;
    }
}
