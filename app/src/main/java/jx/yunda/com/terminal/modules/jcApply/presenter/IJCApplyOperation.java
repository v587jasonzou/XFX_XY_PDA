package jx.yunda.com.terminal.modules.jcApply.presenter;

import java.util.List;

import jx.yunda.com.terminal.modules.jcApply.model.JCApplyOperationWorkContent;
import jx.yunda.com.terminal.modules.jcApply.model.JCApplyOperationWorkType;

public interface IJCApplyOperation {
    void closeJCApplyOperationFragment();

    void workTypeRecyclerNotifyDataSetChanged(List<JCApplyOperationWorkType> datas);

    void workContentRecyclerNotifyDataSetChanged(List<JCApplyOperationWorkContent> datas);

    void workContentRecyclerNotifyItemChanged(int position, JCApplyOperationWorkContent workContent);
}
