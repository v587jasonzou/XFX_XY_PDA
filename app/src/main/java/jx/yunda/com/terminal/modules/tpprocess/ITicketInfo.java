package jx.yunda.com.terminal.modules.tpprocess;

import java.util.List;

import jx.yunda.com.terminal.entity.FileBaseBean;
import jx.yunda.com.terminal.modules.tpprocess.model.DictBean;
import jx.yunda.com.terminal.modules.tpprocess.model.FaultBean;
import jx.yunda.com.terminal.modules.tpprocess.model.FaultTreeBean;
import jx.yunda.com.terminal.modules.tpprocess.model.StandardBean;
import jx.yunda.com.terminal.modules.tpprocess.model.TicketTypeBean;

public interface ITicketInfo  {
     void OnLoadListSuccess(List<FaultBean> Faults);
     void OnLoadEquipSuccess(int position ,List<FaultTreeBean> faultTreeBeanList);
     void OnLoadMajorSuccess(List<TicketTypeBean> list);
     void OnLoadEmployeeSuccess(List<TicketTypeBean> list);
     void OnUpLoadImagesSuccess(String path,int position ,int imageNo);
     void OnGetImagesSuccess(List<FileBaseBean> images);
     void OnGetImagesXpSuccess(List<FileBaseBean> images);
     void  OnSubmitSuccess();
     void  OnLoadFail(String msg);
     void OnSubmitFaild(String msg);
     void  OnLoadFail(String msg,int position,int imageNo);
     void OnDicLoadSuccess(List<DictBean> list,int position);
     void OnDicLoadFaild(String msg);
     void OnBackSuccess();
     void OnLoadStandardSuccess(List<StandardBean> list);
}
