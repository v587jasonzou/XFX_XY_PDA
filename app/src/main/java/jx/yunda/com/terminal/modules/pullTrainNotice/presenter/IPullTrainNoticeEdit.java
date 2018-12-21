package jx.yunda.com.terminal.modules.pullTrainNotice.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.message.model.MessageGroupPerson;
import jx.yunda.com.terminal.modules.pullTrainNotice.model.PullTrainNotice;
import jx.yunda.com.terminal.modules.pullTrainNotice.model.PullTrainNoticeItem;

public interface IPullTrainNoticeEdit {
    void pageNotifyNewDataSetChangedForSelectPersonDialog(List<MessageGroupPerson> msgPersons);

    void pageNotifyNewDataSetChangedForSelectTrainDialog(List<PullTrainNotice> trains);

    void setPageByNoConfirmPullTrainDetailItem(PullTrainNoticeItem item);
}
