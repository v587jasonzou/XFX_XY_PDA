package jx.yunda.com.terminal.modules.jcApproval.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.jcApproval.model.JCApproval;

public interface IJCApproval {
    void pageRecyclerNotifyDataSetChanged(List<JCApproval> datas);
}
