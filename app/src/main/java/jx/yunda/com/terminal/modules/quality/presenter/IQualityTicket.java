package jx.yunda.com.terminal.modules.quality.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.quality.model.QualityTIcketPlanBean;
import jx.yunda.com.terminal.modules.quality.model.TrainQualityBean;

public interface IQualityTicket {
    void OnLoadPlansSuccess(List<QualityTIcketPlanBean> list);
    void OnLoadPlansFaild(String msg);
    void OnLoadMorePlansSuccess(List<QualityTIcketPlanBean> list);
    void OnSubmitSuccess();
    void OnSubmitFaild(String msg);

}
