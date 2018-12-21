package jx.yunda.com.terminal.modules.schedule.entity;

import java.io.Serializable;

public class NodeBean implements Serializable {

    /**
     * idx : 8a8284c26511e79c016512653de5019f
     * baseLineEndTime : 2018-08-08
     * status : RUNNING
     * workPlanIdx : 8a8284c26511e79c016512653dbe019d
     * baseLineStartTime : 2018-08-08
     * nodeName : 机车复检
     * realEndTime : null
     * realWorkminutes : null
     * ratedWorkMinutes : 120
     * planBeginTime : 2018-08-08 08:00
     * rcTrainCategoryCode : 10
     * parentIdx : null
     * planEndTime : 2018-08-08 10:00
     * realBeginTime : 2018-08-08 22:01
     */

    private String idx;
    private String baseLineEndTime;
    private String status;
    private String workPlanIdx;
    private String baseLineStartTime;
    private String nodeName;
    private Object realEndTime;
    private Object realWorkminutes;
    private int ratedWorkMinutes;
    private String planBeginTime;
    private String rcTrainCategoryCode;
    private Object parentIdx;
    private String planEndTime;
    private String realBeginTime;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getBaseLineEndTime() {
        return baseLineEndTime;
    }

    public void setBaseLineEndTime(String baseLineEndTime) {
        this.baseLineEndTime = baseLineEndTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWorkPlanIdx() {
        return workPlanIdx;
    }

    public void setWorkPlanIdx(String workPlanIdx) {
        this.workPlanIdx = workPlanIdx;
    }

    public String getBaseLineStartTime() {
        return baseLineStartTime;
    }

    public void setBaseLineStartTime(String baseLineStartTime) {
        this.baseLineStartTime = baseLineStartTime;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Object getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(Object realEndTime) {
        this.realEndTime = realEndTime;
    }

    public Object getRealWorkminutes() {
        return realWorkminutes;
    }

    public void setRealWorkminutes(Object realWorkminutes) {
        this.realWorkminutes = realWorkminutes;
    }

    public int getRatedWorkMinutes() {
        return ratedWorkMinutes;
    }

    public void setRatedWorkMinutes(int ratedWorkMinutes) {
        this.ratedWorkMinutes = ratedWorkMinutes;
    }

    public String getPlanBeginTime() {
        return planBeginTime;
    }

    public void setPlanBeginTime(String planBeginTime) {
        this.planBeginTime = planBeginTime;
    }

    public String getRcTrainCategoryCode() {
        return rcTrainCategoryCode;
    }

    public void setRcTrainCategoryCode(String rcTrainCategoryCode) {
        this.rcTrainCategoryCode = rcTrainCategoryCode;
    }

    public Object getParentIdx() {
        return parentIdx;
    }

    public void setParentIdx(Object parentIdx) {
        this.parentIdx = parentIdx;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getRealBeginTime() {
        return realBeginTime;
    }

    public void setRealBeginTime(String realBeginTime) {
        this.realBeginTime = realBeginTime;
    }
}
