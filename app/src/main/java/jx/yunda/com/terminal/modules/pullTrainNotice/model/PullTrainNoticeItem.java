package jx.yunda.com.terminal.modules.pullTrainNotice.model;

public class PullTrainNoticeItem {
    /**
     * 主键
     */
    private String idx;
    /**
     * trainPlanIdx计划主键
     */
    private String trainPlanIdx;
    /**
     * 起始台位
     */
    private String homePosition;
    /**
     * 到达台位
     */
    private String destination;
    /**
     * 截止时间
     */
    private String lastTime;
    /**
     * 通知人的集合
     */
    private String jsonAcceptPerson;
    /**
     * 通知单时间
     */
    private String noticeTime;
    /**
     * 确认状态 0未确认 1已确认
     */
    private String confirmStatus;
    /**
     * 确认人id
     */
    private String confirmPersonId;
    /**
     * 确认人姓名
     */
    private String confirmPersonName;
    /**
     * 确认时间
     */
    private String confirmTime;
    /**
     * 创建人id/修改人id
     */
    private String updator;
    /**
     * 创建人姓名/修改人姓名
     */
    private String updatorName;
    /**
     * 创建时间/修改时间
     */
    private String updateTime;
    /**
     * 备注
     */
    private String remark;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getTrainPlanIdx() {
        return trainPlanIdx;
    }

    public void setTrainPlanIdx(String trainPlanIdx) {
        this.trainPlanIdx = trainPlanIdx;
    }

    public String getHomePosition() {
        return homePosition;
    }

    public void setHomePosition(String homePosition) {
        this.homePosition = homePosition;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getJsonAcceptPerson() {
        return jsonAcceptPerson;
    }

    public void setJsonAcceptPerson(String jsonAcceptPerson) {
        this.jsonAcceptPerson = jsonAcceptPerson;
    }

    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getConfirmPersonId() {
        return confirmPersonId;
    }

    public void setConfirmPersonId(String confirmPersonId) {
        this.confirmPersonId = confirmPersonId;
    }

    public String getConfirmPersonName() {
        return confirmPersonName;
    }

    public void setConfirmPersonName(String confirmPersonName) {
        this.confirmPersonName = confirmPersonName;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
