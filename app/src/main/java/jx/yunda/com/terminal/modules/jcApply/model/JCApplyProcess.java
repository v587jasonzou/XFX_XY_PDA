package jx.yunda.com.terminal.modules.jcApply.model;

public class JCApplyProcess {
    /*节点名称*/
   private String nodeName;
   /*可审批人列表*/
   private String approvalNames;
    /*审批意见 01 同意 02 不同意*/
   private String opinionResult;
    /*审批意见内容*/
   private String opinions;
    /*审批人员id*/
   private String approvalUserID;
    /*审批人名称*/
   private String approvalUserName;
    /*流程实例id*/
   private String processInstID;
    /*任务id*/
   private String taskID;
    /*任务键*/
   private String taskKey;
    /*审批记录id*/
   private String idx;
   /*审批时间*/
   private String approvalDate;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getApprovalNames() {
        return approvalNames;
    }

    public void setApprovalNames(String approvalNames) {
        this.approvalNames = approvalNames;
    }

    public String getOpinionResult() {
        return opinionResult;
    }

    public void setOpinionResult(String opinionResult) {
        this.opinionResult = opinionResult;
    }

    public String getOpinions() {
        return opinions;
    }

    public void setOpinions(String opinions) {
        this.opinions = opinions;
    }

    public String getApprovalUserID() {
        return approvalUserID;
    }

    public void setApprovalUserID(String approvalUserID) {
        this.approvalUserID = approvalUserID;
    }

    public String getApprovalUserName() {
        return approvalUserName;
    }

    public void setApprovalUserName(String approvalUserName) {
        this.approvalUserName = approvalUserName;
    }

    public String getProcessInstID() {
        return processInstID;
    }

    public void setProcessInstID(String processInstID) {
        this.processInstID = processInstID;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }
}
