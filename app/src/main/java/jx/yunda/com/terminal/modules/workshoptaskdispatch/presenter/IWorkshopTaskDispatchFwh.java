package jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.EmpForWorkshopTaskDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.OrgForWorkshopTaskDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.WorkstationForWorkshopTaskDispatch;

public interface IWorkshopTaskDispatchFwh {
    void onClassListLoadSuccess(List<OrgForWorkshopTaskDispatch> list);
    void onEmpListLoadSuccess(List<EmpForWorkshopTaskDispatch> list);
    void onWorkstationListLoadSuccess(List<WorkstationForWorkshopTaskDispatch> list);
    void onLoadFail(String msg);
    void onDispatchSuccess(String msg);
    void onDispatchFail(String msg);
}
