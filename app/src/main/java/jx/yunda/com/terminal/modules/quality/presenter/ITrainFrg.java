package jx.yunda.com.terminal.modules.quality.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.quality.model.QualityTrainBean;

public interface ITrainFrg {
    void OnLoadTrainSuccess(List<QualityTrainBean> list);
    void OnLoadMoreTrainSuccess(List<QualityTrainBean> list);
    void OnLoadTrainFaild(String msg);
}
