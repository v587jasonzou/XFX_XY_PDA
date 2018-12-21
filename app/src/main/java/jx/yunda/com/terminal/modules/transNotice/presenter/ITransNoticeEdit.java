package jx.yunda.com.terminal.modules.transNotice.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.message.model.MessageGroupPerson;
import jx.yunda.com.terminal.modules.transNotice.model.TransNoticeTrain;
import jx.yunda.com.terminal.modules.transNotice.model.Train;

public interface ITransNoticeEdit {
    void pageNotifyNewDataSetChangedForSelectPersonDialog(List<MessageGroupPerson> msgPersons);

    void pageNotifyNewDataSetChangedForSelectTrainDialog(List<Train> trains);

    void pageNotifyNewDataSetChangedForNoticeTrain(List<TransNoticeTrain> trains);

    void pageAddNewDataSetChangedForNoticeTrain(TransNoticeTrain train);

    void setNoticeDefaultName(String name);

    void closeFragment(boolean isNewRefresh);
}
