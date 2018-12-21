package jx.yunda.com.terminal.modules.specialApproval.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.specialApproval.model.SpecialApproval;

public interface ISpecialApproval {
    void pageRecyclerNotifyDataSetChanged(List<SpecialApproval> datas);
}
