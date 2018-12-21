package jx.yunda.com.terminal.modules.foremandispatch.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.foremandispatch.model.EmpForForemanDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.OrgForWorkshopTaskDispatch;

public interface IForemanDispatchTicket {
    void onEmpListLoadSuccess(List<EmpForForemanDispatch> list);
    void onLoadFail(String msg);
    void onDispatchSuccess(String msg);
    void onDispatchFail(String msg);

    void onGetImagesSuccess(List<String> images);
}
