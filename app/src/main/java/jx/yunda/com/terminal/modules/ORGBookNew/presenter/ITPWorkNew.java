package jx.yunda.com.terminal.modules.ORGBookNew.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.ORGBook.model.BookUserBean;
import jx.yunda.com.terminal.modules.foremandispatch.model.TrainForForemanDispatch;
import jx.yunda.com.terminal.modules.schedule.entity.ScheduleTrainBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FaultTicket;


public interface ITPWorkNew {
    void LoadTrainListSuccess(List<TrainForForemanDispatch> beans);
    void LoadTrainListFaild(String msg);
    void onEmpListLoadSuccess(List<BookUserBean> list);
    void onLoadEmpListLoadFail(String msg);
    void onTicketListLoadSuccess(List<FaultTicket> list, int total);
    void onTicketListLoadMoreSuccess(List<FaultTicket> list, int total);
    void OnLoadTrainSuccess(List<ScheduleTrainBean> list);
//    void onFwhListLoadSuccess(List<FwFormenBean> list, int total);
//    void onFwhListLoadMoreSuccess(List<FwFormenBean> list, int total);
    void OnLoadFaid(String msg);
    void onDispatchSuccess(String msg);
    void onDispatchFail(String msg);
    void OnBackSuccess();
}
