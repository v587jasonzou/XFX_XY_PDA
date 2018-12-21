package jx.yunda.com.terminal.modules.schedule.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.schedule.entity.OrgBean;
import jx.yunda.com.terminal.modules.schedule.entity.ScheduleTrainBean;
import jx.yunda.com.terminal.modules.schedule.entity.StationBean;

public interface IScheduleBook {
    void OnLoadOrgSuccess(List<OrgBean> list);
    void OnLoadOrgFaild(String msg);
    void OnloadStationSuccess(List<StationBean> list);
    void OnLoadStationFaild(String msg);
    void submitStationSuccess();
    void submitStationFaild(String msg);
    void OnLoadTrainSuccess(List<ScheduleTrainBean> list);
    void OnLoadTrainFaild(String msg);
    void submitBookSuccess();
    void submitBookFaild(String msg);
}
