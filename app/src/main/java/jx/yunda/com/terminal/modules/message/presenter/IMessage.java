package jx.yunda.com.terminal.modules.message.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.message.model.MessageInfo;

public interface IMessage {
    void pageNotifyNewDataSetChanged(List<MessageInfo> messageGroups);
    void pageNotifyHistoryDataSetChanged(List<MessageInfo> messageGroups);
}
