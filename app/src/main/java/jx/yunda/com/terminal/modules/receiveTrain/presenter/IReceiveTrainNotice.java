package jx.yunda.com.terminal.modules.receiveTrain.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.receiveTrain.model.ReceiveTrainNotice;

public interface IReceiveTrainNotice {
    void OnLoadTrainListSuccess(List<ReceiveTrainNotice> list);
    void OnLoadTrainListFaild(String msg);
}
