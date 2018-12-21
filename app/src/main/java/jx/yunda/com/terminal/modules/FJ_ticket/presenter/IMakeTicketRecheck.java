package jx.yunda.com.terminal.modules.FJ_ticket.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.FJ_ticket.model.RecheckTrainBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTrainBean;

public interface IMakeTicketRecheck {
    void LoadTrainListSuccess(List<RecheckTrainBean> beans);
    void LoadMoreTrainListSuccess(List<RecheckTrainBean> beans);
    void OnLoadFaild(String msg);
    void OnSubmitSuccess();
    void OnSubmitFaild(String msg);
}
