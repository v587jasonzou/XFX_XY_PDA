package jx.yunda.com.terminal.modules.quality.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.quality.model.NodeBean;
import jx.yunda.com.terminal.modules.quality.model.QualityEquipPlanBean;
import jx.yunda.com.terminal.modules.quality.model.TrainQualityBean;

public interface IEquipQuality {
    void OnLoadPlansSuccess(List<QualityEquipPlanBean> list);
    void OnLoadPlansFaild(String msg);
    void OnLoadMorePlansSuccess(List<QualityEquipPlanBean> list);
    void OnSubmitSuccess();
    void OnSubmitFaild(String msg);
    void OnDoBackSuccess();
    void OnDoBackFaild(String msg);
    void getNodesSuccess(List<NodeBean> list);
    void getNodesFaild(String msg);
}
