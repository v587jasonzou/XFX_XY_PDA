package jx.yunda.com.terminal.modules.main.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.main.model.MessageGroup;

public interface IMessageGroup {
    void pageNotifyDataSetChanged(List<MessageGroup> messageGroups);
}

