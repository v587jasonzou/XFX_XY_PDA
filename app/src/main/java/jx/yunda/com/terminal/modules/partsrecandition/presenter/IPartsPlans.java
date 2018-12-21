package jx.yunda.com.terminal.modules.partsrecandition.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.partsrecandition.model.PartsPlanBean;

public interface IPartsPlans {
    void getPartSPlanSuccess(List<PartsPlanBean> list);
    void getPartsPlanFaild(String msg);
    void getMorePartSPlanSuccess(List<PartsPlanBean> list);
    void StartWorkSuccess();
    void StartWorkFaild(String msg);
    void getUpEquipSuccess(int position,int size);
    void getUpEquipFaild(String msg);
    void getDownEquipSuccess(int position,int size);
    void getDownEquipFaild(String msg);
}
