package jx.yunda.com.terminal.modules.confirmNotify.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.confirmNotify.entity.NotifyListBean;
import jx.yunda.com.terminal.modules.schedule.entity.ScheduleTrainBean;

public interface IGetNotify {
    void OnLoadNotifySuccess(List<NotifyListBean> list);
    void OnLoadNotifyFaild(String msg);
    void OnLoadMoreNotifySuccess(List<NotifyListBean> list);

}
