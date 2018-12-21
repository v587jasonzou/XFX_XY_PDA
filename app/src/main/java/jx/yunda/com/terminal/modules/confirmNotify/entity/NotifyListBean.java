package jx.yunda.com.terminal.modules.confirmNotify.entity;

import java.io.Serializable;

public class NotifyListBean implements Serializable {

    /**
     * total : 1
     * createTime : 2018-09-03
     * idx : 40288afc659cf4c501659e1493f50047
     * noticeName : 2018-09-03-2调车通知单
     * confirmCount : 1
     * noticeTime : 2018-09-03
     * submitStatus : 1
     */

    private Integer total;
    private String createTime;
    private String idx;
    private String noticeName;
    private Integer confirmCount;
    private String noticeTime;
    private Integer submitStatus;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

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

    public Integer getConfirmCount() {
        return confirmCount;
    }

    public void setConfirmCount(Integer confirmCount) {
        this.confirmCount = confirmCount;
    }

    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }

    public Integer getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(Integer submitStatus) {
        this.submitStatus = submitStatus;
    }
}
