package jx.yunda.com.terminal.modules.fixflow.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.fixflow.entry.FwhInfoBean;

public interface iGetFwhList {
    void LoadFwhListSuccess(List<FwhInfoBean> list);
    void LoadMoreFwhListSuccess(List<FwhInfoBean> list);
    void LoadFaild(String msg);
}
