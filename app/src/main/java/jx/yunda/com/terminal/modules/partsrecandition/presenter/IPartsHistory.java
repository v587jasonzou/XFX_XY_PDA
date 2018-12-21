package jx.yunda.com.terminal.modules.partsrecandition.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.TrainRecandition.model.RecanditionBean;
import jx.yunda.com.terminal.modules.partsrecandition.model.PartsPlanBean;

public interface IPartsHistory {
    void getPlansSuccess(List<PartsPlanBean> recanditionBeans);
    void getPlansFaild(String msg);
    void LoadMoreSuccess(List<PartsPlanBean> recanditionBeans);
    void LoadMoreFaild(String msg);

}
