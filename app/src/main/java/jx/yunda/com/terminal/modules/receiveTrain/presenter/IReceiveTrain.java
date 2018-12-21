package jx.yunda.com.terminal.modules.receiveTrain.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.receiveTrain.model.ReceiveTrainNotice;
import jx.yunda.com.terminal.modules.receiveTrain.model.ReceivedTrain;

public interface IReceiveTrain {
    void pageRecyclerNotifyDataSetChanged(List<ReceivedTrain> datas);
    void OnLoadTrainsSuccess(List<ReceivedTrain> list);
    void OnLoadMoreTrainsSuccess(List<ReceivedTrain> list);
    void OnLoadFaild(String msg);
    void OnLoadTrainListSuccess(List<ReceiveTrainNotice> list);
}
