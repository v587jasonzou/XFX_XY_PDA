package jx.yunda.com.terminal.modules.foremandispatch.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.foremandispatch.model.TrainForForemanDispatch;

public interface IForemanDispatch {
    void LoadTrainListSuccess(List<TrainForForemanDispatch> beans);
    void LoadMoreTrainListSuccess(List<TrainForForemanDispatch> beans);
    void OnLoadFaild(String msg);
    void OnSubmitSuccess();
    void OnSubmitFaild(String msg);
}
