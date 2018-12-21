package jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.TrainForWorkshopTaskDispatch;

public interface IWorkshopTaskDispatch {
    void LoadTrainListSuccess(List<TrainForWorkshopTaskDispatch> beans);
    void LoadMoreTrainListSuccess(List<TrainForWorkshopTaskDispatch> beans);
    void OnLoadFaild(String msg);
    void OnSubmitSuccess();
    void OnSubmitFaild(String msg);
}
