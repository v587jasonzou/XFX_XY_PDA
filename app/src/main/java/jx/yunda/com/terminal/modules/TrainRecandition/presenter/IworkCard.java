package jx.yunda.com.terminal.modules.TrainRecandition.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.TrainRecandition.model.OrderBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.WorkCardBean;

public interface IworkCard {
    void LoadDataSuccess(List<OrderBean> list);
    void LoadDataFaild(String msg);
    void LoadMoreDataSuccess();
    void LoadMoreDataFaild(String msg);
    void SubmitSuccess();
    void SubmitFaild(String msg);
    void ChangeOrderSuccess();
    void ChangeOrderFaild(String msg);
    void getCardsSortSuccess(List<WorkCardBean> list);
    void getCardsSortFaild(String msg);
    void OnUpLoadImagesSuccess(String path,int position ,int imageNo);
    void  OnLoadFail(String msg,int position,int imageNo);
}
