package jx.yunda.com.terminal.modules.main.model;

/**
 * <li>说明：获取组消息实体
 * <li>创建人：zhubs
 * <li>创建日期：2018年5月8日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 */
public class MessageGroup {

    //组idx
    private String groupId;
   //组类型
    private String groupType;
    //组名称
    private String groupName;
    //小组内最后一条消息
    private String lastMessage;
    //更新时间
    private String updateTime;
    //小组显示颜色
    private String backgroundColor;
    //未读数量
    private int unReadMsgCounts;
    private String senderName;

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getUnReadMsgCounts() {
        return unReadMsgCounts;
    }

    public void setUnReadMsgCounts(int unReadMsgCounts) {
        this.unReadMsgCounts = unReadMsgCounts;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
