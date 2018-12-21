package jx.yunda.com.terminal.modules.message.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.message.model.MessageGroupPerson;

public interface IMessageGroupPersons {
    void pageNotifyDataSetChanged(List<MessageGroupPerson> messageGroupPersons);

    void setMsgGroupPersonSelectTotal(int addCount, boolean isAdd);
}
