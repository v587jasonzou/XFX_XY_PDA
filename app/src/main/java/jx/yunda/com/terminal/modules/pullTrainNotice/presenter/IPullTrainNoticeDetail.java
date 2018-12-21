package jx.yunda.com.terminal.modules.pullTrainNotice.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.pullTrainNotice.model.PullTrainNoticeItem;

public interface IPullTrainNoticeDetail {

    void pageNotifyNewDataSetChangedForPullTrainDetails(List<PullTrainNoticeItem> items);
}
