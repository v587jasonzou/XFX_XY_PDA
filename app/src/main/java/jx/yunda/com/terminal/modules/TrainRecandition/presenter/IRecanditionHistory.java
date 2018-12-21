package jx.yunda.com.terminal.modules.TrainRecandition.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.TrainRecandition.model.RecanditionBean;

public interface IRecanditionHistory {
    void getPlansSuccess(List<RecanditionBean> recanditionBeans);
    void getPlansFaild(String msg);
    void LoadMoreSuccess(List<RecanditionBean> recanditionBeans);
    void LoadMoreFaild(String msg);

}
