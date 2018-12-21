package jx.yunda.com.terminal.modules.main.model;

import java.util.List;

/**
 * <li>说明：服务端推送的消息模型
 * <li>创建人：zhubs
 * <li>创建日期：2018年7月24日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 */
public class MessageReceive {
    //0：非消息页； 1：消息群列表页面； 2：消息页面，查询消息，但不发送消息 3：消息页面，发送消息，但不查询
    private String pageSign;
    //班组idx
    private String groupIdx;
    //未读数量
    private int unReadCount;

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

    public int getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }
}
