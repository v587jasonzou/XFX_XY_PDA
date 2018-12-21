package jx.yunda.com.terminal.modules.jcApproval.adapter;

import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.modules.jcApproval.fragment.JCApprovalOperationFragment;
import jx.yunda.com.terminal.modules.jcApproval.model.JCApprovalOperationWorkContent;
import jx.yunda.com.terminal.utils.LogUtil;
import rx.functions.Action1;

public class JCApprovalOperationWorkContentAdapter extends BaseRecyclerAdapter<JCApprovalOperationWorkContent, JCApprovalOperationWorkContentAdapter.JCApprovalOperationWorkContentHolder> {
    JCApprovalOperationFragment fragment;

    public JCApprovalOperationWorkContentAdapter(JCApprovalOperationFragment fragment) {
        super();
        this.fragment = fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.jc_approval_operation_content_item;
    }

    @Override
    protected JCApprovalOperationWorkContentHolder createViewHolder(View view) {
        return new JCApprovalOperationWorkContentHolder(view);
    }

    @Override
    public void onBindViewHolder(JCApprovalOperationWorkContentHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final JCApprovalOperationWorkContent workContent = getData().get(position);
        if (workContent.isShowBackBtn) {
            holder.back.setVisibility(View.VISIBLE);
            RxView.clicks(holder.back)
                    .throttleFirst(5, TimeUnit.SECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            if (fragment.getSelectWorkType() == null) return;
                            fragment.getPresenter().workContentBack(fragment.getSelectWorkType().getName(), position, workContent);
                        }
                    });
        } else
            holder.back.setVisibility(View.GONE);
    }

    public static class JCApprovalOperationWorkContentHolder extends BaseRecyclerAdapter.BaseViewHolder<JCApprovalOperationWorkContent> {
        JCApprovalOperationWorkContent jcApprovalWorkContent;
        @BindView(R.id.jc_approval_operation_content_item_num)
        TextView num;
        @BindView(R.id.jc_approval_operation_content_item_content)
        TextView content;
        @BindView(R.id.jc_approval_operation_content_item_back)
        TextView back;

        public JCApprovalOperationWorkContentHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(JCApprovalOperationWorkContent data) {
            try {
                jcApprovalWorkContent = data;
                num.setText((getAdapterPosition() + 1) + "");
                content.setText(jcApprovalWorkContent.getFaultDesc());
            } catch (Exception ex) {
                LogUtil.e("交车审批进度item显示", ex.toString());
            }
        }
    }
}
