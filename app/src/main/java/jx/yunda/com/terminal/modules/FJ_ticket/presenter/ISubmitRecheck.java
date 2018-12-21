package jx.yunda.com.terminal.modules.FJ_ticket.presenter;

import java.util.List;

import jx.yunda.com.terminal.entity.FileBaseBean;
import jx.yunda.com.terminal.modules.tpprocess.model.FaultBean;
import jx.yunda.com.terminal.modules.tpprocess.model.FaultTreeBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTypeBean;

public interface ISubmitRecheck {
    void OnLoacationLoadsuccess(int position ,List<FaultTreeBean> faultTreeBeanList);
    void OnLoacationLoadFaild(String msg);
    void OnTypeLoadSuccess(List<TicketTypeBean> list);
    void OnTypeLoadFaild(String msg);
    void OnUpLoadImagesFaild(String msg,int position ,int imageNo);
    void OnUpLoadImagesSuccess(String path,int position ,int imageNo);
    void OnGetImagesSuccess(List<FileBaseBean> images);
    void OnGetImagesFaild(String images);
    void OnSubmitSuccess();
    void OnSubmitFaild(String msg);
    void OnLoadListSuccess(List<FaultBean> Faults);
    void OnLoadListFaild(String msg);
}
