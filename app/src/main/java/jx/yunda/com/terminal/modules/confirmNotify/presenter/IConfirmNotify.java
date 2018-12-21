package jx.yunda.com.terminal.modules.confirmNotify.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.confirmNotify.entity.NotifyDetailBean;

public interface IConfirmNotify {
    void GetNotifySuccess(List<NotifyDetailBean> list);
    void GetNotifyFaild(String msg);
    void ConfirmSuccess();
    void ConfirmFaild(String msg);
    void OnConfirmLocationSuccess();
    void OnConfirmLoacationFaild(String msg);
}
