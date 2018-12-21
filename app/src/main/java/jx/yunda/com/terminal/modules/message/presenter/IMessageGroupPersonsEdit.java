package jx.yunda.com.terminal.modules.message.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.message.model.MessageGroupPerson;

public interface IMessageGroupPersonsEdit {
    void pageNotifyDataSetChanged(List<MessageGroupPerson> messageGroupPersons);
    void pageNotifyAllPersonDataSetChanged(List<MessageGroupPerson> messageGroupPersons);
    void dismissAddPersonDialog();
    void showAddPersonDialog();
}
