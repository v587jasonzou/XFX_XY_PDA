package jx.yunda.com.terminal.modules.schedule.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.schedule.entity.ScheduleTrainBean;

public interface IScheduleMain {
    void OnLoadTrainSuccess(List<ScheduleTrainBean> list);
    void OnLoadTrainFaild(String msg);
    void OnLoadMoreTrainSuccess(List<ScheduleTrainBean> list);
    void OnUpStationSuccess();
    void OnUpStationFaild(String msg);
}
