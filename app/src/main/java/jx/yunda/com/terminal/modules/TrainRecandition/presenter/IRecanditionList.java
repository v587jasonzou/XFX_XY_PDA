package jx.yunda.com.terminal.modules.TrainRecandition.presenter;

import java.util.ArrayList;
import java.util.List;

import jx.yunda.com.terminal.modules.TrainRecandition.model.EquipBean;
import jx.yunda.com.terminal.modules.TrainRecandition.model.RecanditionBean;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;

public interface IRecanditionList {
    void OnLoadPlanSuccess(List<RecanditionBean> recanditionBeans);
    void OnLoadMoreOlanSuccess(List<RecanditionBean> recanditionBeans);
    void OnLoadPlanFaild(String msg);
    void OnStartPlanSuccess(int position);
    void OnStartPlanFaild(String msg);
    void OnCompletPlanSuccess();
    void OnCompletPlanFaild(String msg, int position);
    void getUpPlanSuccess(int size,int position);
    void getUpPlanFaild(String masg);
    void getDownPlanSuccess(int size,int position);
    void getDownPlanFaild(String masg);
}
