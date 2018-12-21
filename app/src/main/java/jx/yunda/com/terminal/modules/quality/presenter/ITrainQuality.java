package jx.yunda.com.terminal.modules.quality.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.quality.model.TrainQualityBean;

public interface ITrainQuality {
    void OnLoadPlansSuccess(List<TrainQualityBean> list);
    void OnLoadPlansFaild(String msg);
    void OnLoadMorePlansSuccess(List<TrainQualityBean> list);
    void OnSubmitSuccess();
    void OnSubmitFaild(String msg);
    void OnDoBackSuccess();
    void OnDoBackFaild(String msg);
}
