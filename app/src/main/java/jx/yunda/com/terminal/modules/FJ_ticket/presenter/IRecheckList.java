package jx.yunda.com.terminal.modules.FJ_ticket.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.FJ_ticket.model.RecheckBean;

public interface IRecheckList {
    void getRecheckListSuccess(List<RecheckBean> RecheckList);
    void getReCheckListFaild(String msg);
    void DeleteRecheckSuccess();
    void DeleteRecheckFaild(String msg);
}
