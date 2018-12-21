package jx.yunda.com.terminal.modules.workshoptaskdispatch.presenter;

import java.util.List;

import jx.yunda.com.terminal.entity.FileBaseBean;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.EmpForWorkshopTaskDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.OrgForWorkshopTaskDispatch;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.WorkstationForWorkshopTaskDispatch;

public interface IWorkshopTaskDispatchTicket {
    void onTicketClassListLoadSuccess(List<OrgForWorkshopTaskDispatch> list);
    void onLoadFail(String msg);
    void onDispatchSuccess(String msg);
    void onDispatchFail(String msg);

    void onGetImagesSuccess(List<FileBaseBean> images);
}
