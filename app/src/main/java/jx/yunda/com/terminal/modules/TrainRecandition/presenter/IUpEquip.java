package jx.yunda.com.terminal.modules.TrainRecandition.presenter;

import java.util.ArrayList;
import java.util.List;

import jx.yunda.com.terminal.modules.TrainRecandition.model.EquipBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.ModulesBean;
import jx.yunda.com.terminal.modules.partsrecandition.model.ChildUpEquipBean;

public interface IUpEquip {
    void OnSearchSuccess();
    void OnSearchFaild();
    void OnSubmitSuccess();
    void OnsubmitFaild(String msg);
    void OnDeletSuccess();
    void OnDeletFaild(String msg);
    void getUpEquipSuccess(ArrayList<EquipBean> list);
    void getUnEquipFaild(String msg);
    void getTypeSuccess(List<ModulesBean> list);
    void getTypeFaild(String msg);
    void OnPartsNoLoadSuccess(List<String> list);
    void OnPartsNoLoadFaild(String msg);
}
