package jx.yunda.com.terminal.entity;

import jx.yunda.com.terminal.modules.message.model.MessageInfo;
/**
 * <li>说明：消息辅助model
 * <li>创建人：zhubs
 * <li>创建日期：2018年7月20日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 */
public class MessageSend {
    //0：非消息页； 1：消息群列表页面； 2：消息页面，,,查询消息，但不发送消息 3：消息页面，发送消息，但不查询
    private String pageSign;
    //班组idx
    private String groupId;
    //消息查询的起始消息idx，但不包括该消息
    private String startId;
    //单次消息查询的最大条数
    private String limit;
    //当前登录人的empid
    private String empId;
    private String empName;
    //当前登录人的operatorId
    private String operatorId;
    //单次发送消息内容
    private MessageInfo sendMessage;

    public String getPageSign() {
        return pageSign;
    }

    public void setPageSign(String pageSign) {
        this.pageSign = pageSign;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public MessageInfo getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(MessageInfo sendMessage) {
        this.sendMessage = sendMessage;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
}
