package jx.yunda.com.terminal.modules.tpprocess;

import java.util.List;

import jx.yunda.com.terminal.modules.tpprocess.model.TicketInfoBean;

public interface ITicketList {
    void OnTicketListLoadSuccess(List<TicketInfoBean> list);
    void OnTicketListLoadMoreSuccess(List<TicketInfoBean> list);
    void OnLoadFail(String msg);
    void OnImageUploadSuccess(int position);
    void OnImageUploadFail(int position,String msg);
    void OnTicketSubmitSuccess(int position);
    void OnTicketSubmitFail(int position,String msg);
}
