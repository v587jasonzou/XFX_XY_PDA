package jx.yunda.com.terminal.modules.TrainRecandition.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.FJ_ticket.model.RecheckTrainBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.JXTrainBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTrainBean;

public interface IRecanditionTrain {
    void LoadTrainListSuccess(List<JXTrainBean> beans);
    void LoadMoreTrainListSuccess(List<JXTrainBean> beans);
    void OnLoadFaild(String msg);
}
