package jx.yunda.com.terminal.modules.schedule.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.schedule.entity.ReceiveTrainBean;
import jx.yunda.com.terminal.modules.schedule.entity.ReceiveUserBean;

public interface IReceiveSchedule {
    void getTrainsSuccess(List<ReceiveTrainBean> list);
    void getUsersSuccess(List<ReceiveUserBean> list);
    void BookSuccess();
    void onLoadFaild(String msg);
}
