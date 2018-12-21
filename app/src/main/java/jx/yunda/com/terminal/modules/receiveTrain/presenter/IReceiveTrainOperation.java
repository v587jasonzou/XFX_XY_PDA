package jx.yunda.com.terminal.modules.receiveTrain.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.ORGBook.model.BookCalenderBean;
import jx.yunda.com.terminal.modules.receiveTrain.model.NodeEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.OrgEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.RepairEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.TrainEntity;
import jx.yunda.com.terminal.modules.receiveTrain.model.TrainNoEntity;

public interface IReceiveTrainOperation {
    void getTrainNameSuccess(List<TrainEntity> list);
    void getTrainNoSuccess(List<TrainNoEntity> list);
    void getRepairSuccess(List<RepairEntity> list);
    void getNodeSuccess(List<NodeEntity> list);
    void getCalenderSuccess(BookCalenderBean bean);
    void getOrgsSuccess(List<OrgEntity> list,int position);
    void ConfirmSuccess();
    void getDataFaild(String msg,String type);
    void OnUpLoadImagesSuccess(String path,int position ,int imageNo);
    void  OnLoadFail(String msg,int position,int imageNo);
}
