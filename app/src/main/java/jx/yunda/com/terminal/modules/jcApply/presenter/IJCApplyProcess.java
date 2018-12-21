package jx.yunda.com.terminal.modules.jcApply.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.jcApply.model.JCApplyProcess;

public interface IJCApplyProcess {
    void recyclerNotifyDataSetChanged(List<JCApplyProcess> datas);
}
