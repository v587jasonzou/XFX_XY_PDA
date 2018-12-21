package jx.yunda.com.terminal.modules.foremandispatch.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.foremandispatch.model.FwFormenBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FaultTicket;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FwhBean;

public interface IForemanDispatchList {
    void onTicketListLoadSuccess(List<FaultTicket> list, int total);
    void onTicketListLoadMoreSuccess(List<FaultTicket> list, int total);
    void onFwhListLoadSuccess(List<FwFormenBean> list, int total);
    void onFwhListLoadMoreSuccess(List<FwFormenBean> list, int total);
    void onLoadFail(String msg);
    void onDispatchSuccess(String msg);
    void onDispatchFail(String msg);
}
