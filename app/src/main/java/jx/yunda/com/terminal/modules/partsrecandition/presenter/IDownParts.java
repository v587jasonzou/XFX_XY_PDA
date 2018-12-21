package jx.yunda.com.terminal.modules.partsrecandition.presenter;

import java.util.ArrayList;
import java.util.List;

import jx.yunda.com.terminal.modules.TrainRecandition.model.DownEquipBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.ModulesBean;
import jx.yunda.com.terminal.modules.partsrecandition.model.ChildEquipBean;

public interface IDownParts {
    void OnDownSubmitSuccess();
    void OnDownsubmitFaild(String msg);
    void OnDeletSuccess();
    void OnDeletFaild(String msg);
    void getTypeSuccess(List<ModulesBean> list);
    void getTypeFaild(String msg);
    void getDowmEquipPlansSuccess(ArrayList<ChildEquipBean> list);
    void getDowmEquipPlansFaild(String msg);
    void OnPartsNoLoadSuccess(List<String> list);
    void OnPartsNoLoadFaild(String msg);
}
