package jx.yunda.com.terminal.modules.tpmanage.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.tpprocess.model.TicketInfoBean;

public interface ITicketManage {
    void OnTicketListLoadSuccess(List<TicketInfoBean> list);
    void OnTicketListLoadMoreSuccess(List<TicketInfoBean> list);
    void OnTicketListLoadFail(String msg);
    void OnTicketListLoadMoreFail(String msg);
}
