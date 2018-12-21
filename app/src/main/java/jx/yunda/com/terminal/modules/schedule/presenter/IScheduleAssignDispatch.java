package jx.yunda.com.terminal.modules.schedule.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.ORGBook.model.BookUserBean;
import jx.yunda.com.terminal.modules.foremandispatch.model.FwFormenBean;
import jx.yunda.com.terminal.modules.foremandispatch.model.TrainForForemanDispatch;
import jx.yunda.com.terminal.modules.schedule.entity.OrgBean;
import jx.yunda.com.terminal.modules.schedule.entity.ScheduleTrainBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FwhBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.OrgForWorkshopTaskDispatch;

public interface IScheduleAssignDispatch {
    void LoadTrainListSuccess(List<TrainForForemanDispatch> beans);
    void LoadTrainListFaild(String msg);
    void onEmpListLoadSuccess(List<BookUserBean> list);
    void onLoadEmpListLoadFail(String msg);
//    void onTicketListLoadSuccess(List<FaultTicket> list, int total);
//    void onTicketListLoadMoreSuccess(List<FaultTicket> list, int total);
    void onFwhListLoadSuccess(List<FwhBean> list, int total);
    void onFwhListLoadMoreSuccess(List<FwhBean> list, int total);
    void OnLoadFaid(String msg);
    void onDispatchSuccess(String msg);
    void onDispatchFail(String msg);
    void onClassListLoadSuccess(List<OrgForWorkshopTaskDispatch> list);
    void OnLoadOrgSuccess(List<OrgBean> list);
    void OnLoadTrainSuccess(List<ScheduleTrainBean> list);
}
