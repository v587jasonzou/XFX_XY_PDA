package jx.yunda.com.terminal.modules.FJ_ticket.presenter;

import java.util.List;

import jx.yunda.com.terminal.entity.FileBaseBean;

public interface IRecheckInfo {
    void GetImagesSuccess(List<FileBaseBean> images);
    void GetImagesFaild(String msg);
}
