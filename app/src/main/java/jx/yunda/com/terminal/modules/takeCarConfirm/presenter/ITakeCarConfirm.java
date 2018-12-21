package jx.yunda.com.terminal.modules.takeCarConfirm.presenter;

import java.util.List;

import jx.yunda.com.terminal.base.baseDictData.model.DicDataItem;
import jx.yunda.com.terminal.modules.takeCarConfirm.entry.TakeCarBean;

public interface ITakeCarConfirm {
    void OnLoadConfirmListSuccess(List<TakeCarBean> list);
    void OnLoadFaild(String msg);
    void OnLoadStationsSuccess(List<DicDataItem> list);
    void ConfirmSuccess();
}
