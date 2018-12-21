package jx.yunda.com.terminal.modules.TrainRecandition.presenter;

import java.util.ArrayList;
import java.util.List;

import jx.yunda.com.terminal.modules.TrainRecandition.model.DownEquipBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.ModulesBean;

public interface IDownEquip {
    void OnDownSubmitSuccess();
    void OnDownsubmitFaild(String msg);
    void OnDeletSuccess();
    void OnDeletFaild(String msg);
    void getLocationSuccess();
    void getLocationFaild();
    void getTypeSuccess(List<ModulesBean> list);
    void getTypeFaild(String msg);
    void getDowmEquipPlansSuccess(ArrayList<DownEquipBean> list);
    void getDowmEquipPlansFaild(String msg);
    void OnPartsNoLoadSuccess(List<String> list);
    void OnPartsNoLoadFaild(String msg);
}
