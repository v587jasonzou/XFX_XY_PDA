package jx.yunda.com.terminal.modules.transNotice.model;

public class TransNotice {
    private String idx;
    //通知单名称
    private String noticeName;
    //该通知单的总条数，如果为null解析为0
    private String total;
    //该通知单的已确认的条数，如果为null解析为0
    private String confirmCount;
    //提交状态，0为未提交，1为已提交
    private String submitStatus;
    //通知单创建时间
    private String createTime;
    //班组
    private String department;
    //通知时间
    private String noticeTime;
    //通知人
    private String jsonAcceptPerson;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getConfirmCount() {
        return confirmCount;
    }

    public void setConfirmCount(String confirmCount) {
        this.confirmCount = confirmCount;
    }

    public String getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getJsonAcceptPerson() {
        return jsonAcceptPerson;
    }

    public void setJsonAcceptPerson(String jsonAcceptPerson) {
        this.jsonAcceptPerson = jsonAcceptPerson;
    }
}
