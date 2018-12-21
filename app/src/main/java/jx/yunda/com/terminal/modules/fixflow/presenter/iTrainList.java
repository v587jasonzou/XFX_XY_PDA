package jx.yunda.com.terminal.modules.fixflow.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.fixflow.adapter.FlowTrainAdapter;
import jx.yunda.com.terminal.modules.fixflow.entry.FlowTrainBean;

public interface iTrainList  {
    void OnLoadSuccess(List<FlowTrainBean> list);
    void OnLoadMoreSuccess();
    void OnLoadFaild(String msg);
}
