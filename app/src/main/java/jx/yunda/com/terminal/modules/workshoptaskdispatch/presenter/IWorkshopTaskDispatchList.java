package jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FaultTicket;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FwhBean;

public interface IWorkshopTaskDispatchList {
    void onTicketListLoadSuccess(List<FaultTicket> list, int total);
    void onTicketListLoadMoreSuccess(List<FaultTicket> list, int total);
    void onFwhListLoadSuccess(List<FwhBean> list, int total);
    void onFwhListLoadMoreSuccess(List<FwhBean> list, int total);
    void onLoadFail(String msg);
    void onDispatchSuccess(String msg);
    void onDispatchFail(String msg);
}
