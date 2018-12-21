package jx.yunda.com.terminal.modules.tpApproval.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.tpApproval.model.TPApproval;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FaultTicket;

public interface ITPApproval {
    void pageRecyclerNotifyDataSetChanged(List<FaultTicket> list);
}
