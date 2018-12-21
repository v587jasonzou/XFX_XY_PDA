package jx.yunda.com.terminal.modules.jcApproval.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.jcApproval.model.JCApprovalOperationWorkContent;
import jx.yunda.com.terminal.modules.jcApproval.model.JCApprovalOperationWorkType;

public interface IJCApprovalOperation {
    void closeJCApplyOperationFragment();

    void workTypeRecyclerNotifyDataSetChanged(List<JCApprovalOperationWorkType> datas);

    void workContentRecyclerNotifyDataSetChanged(List<JCApprovalOperationWorkContent> datas);

    void workContentRecyclerNotifyItemChanged(int position, JCApprovalOperationWorkContent workContent);

    void dismissApprovalDialog();

    void showApprovalDailog(String title) ;
}
