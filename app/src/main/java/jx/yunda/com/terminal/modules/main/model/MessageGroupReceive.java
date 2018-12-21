package jx.yunda.com.terminal.modules.main.model;

import java.util.List;

import jx.yunda.com.terminal.modules.main.model.MessageGroup;

/**
 * <li>说明：服务端推送的消息模型
 * <li>创建人：zhubs
 * <li>创建日期：2018年7月24日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 */
public class MessageGroupReceive {
    //0：非消息页； 1：消息群列表页面； 2：消息页面，查询消息，但不发送消息 3：消息页面，发送消息，但不查询
    private String pageSign;
    //班组idx
    private String groupIdx;
    //消息是否是历史消息 0：最新消息 1：历史消息
    private String isHistoryMsg;
    //未读数量
    private String unReadCount;
    //结果数组
    private List<MessageGroup> rstList;

    public String getPageSign() {
        return pageSign;
    }

    public void setPageSign(String pageSign) {
        this.pageSign = pageSign;
    }

    public String getGroupIdx() {
        return groupIdx;
    }

    public void setGroupIdx(String groupIdx) {
        this.groupIdx = groupIdx;
    }

    public String getIsHistoryMsg() {
        return isHistoryMsg;
    }

    public void setIsHistoryMsg(String isHistoryMsg) {
        this.isHistoryMsg = isHistoryMsg;
    }

    public String getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(String unReadCount) {
        this.unReadCount = unReadCount;
    }

    public List<MessageGroup> getRstList() {
        return rstList;
    }

    public void setRstList(List<MessageGroup> rstList) {
        this.rstList = rstList;
    }
}
