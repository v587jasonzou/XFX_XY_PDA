package jx.yunda.com.terminal.modules.TrainRecandition.presenter;

import java.util.List;

import jx.yunda.com.terminal.entity.FileBaseBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.InspectorOrderBean;

public interface IWorkOrder {
    void submitOrderSuccess(String type);
    void submitOrderFaild(String msg,String type);
    void upLoadImagesSuccess(String path,String idx);
    void upLoadImagesFaild(String msg);
    void LoaddataSuccess(List<InspectorOrderBean> list);
    void LoaddataFaild(String msg);
    void getItemsSuccess(List<String> list);
    void getItemsFaild(String msg);
    void SubmitEditSuccess();
    void SubmitEditFaild(String msg);
    void OnGetImagesSuccess(List<FileBaseBean> images);
    void OnGetImagesFaild(String msg);
}
