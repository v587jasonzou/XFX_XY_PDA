package jx.yunda.com.terminal.modules.jcApply.adapter;

import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.modules.jcApply.fragment.JCApplyOperationFragment;
import jx.yunda.com.terminal.modules.jcApply.model.JCApplyOperationWorkContent;
import jx.yunda.com.terminal.utils.LogUtil;
import rx.functions.Action1;

public class JCApplyOperationWorkContentAdapter extends BaseRecyclerAdapter<JCApplyOperationWorkContent, JCApplyOperationWorkContentAdapter.JCApplyOperationWorkContentHolder> {
    JCApplyOperationFragment fragment;

    public JCApplyOperationWorkContentAdapter(JCApplyOperationFragment fragment) {
        super();
        this.fragment = fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.jc_apply_operation_content_item;
    }

    @Override
    protected JCApplyOperationWorkContentHolder createViewHolder(View view) {
        return new JCApplyOperationWorkContentHolder(view);
    }

    @Override
    public void onBindViewHolder(JCApplyOperationWorkContentHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final JCApplyOperationWorkContent workContent = getData().get(position);
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

    public static class JCApplyOperationWorkContentHolder extends BaseRecyclerAdapter.BaseViewHolder<JCApplyOperationWorkContent> {
        JCApplyOperationWorkContent jcApplyWorkContent;
        @BindView(R.id.jc_apply_operation_content_item_num)
        TextView num;
        @BindView(R.id.jc_apply_operation_content_item_content)
        TextView content;
        @BindView(R.id.jc_apply_operation_content_item_back)
        TextView back;

        public JCApplyOperationWorkContentHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(JCApplyOperationWorkContent data) {
            try {
                jcApplyWorkContent = data;
                num.setText((getAdapterPosition() + 1) + "");
                content.setText(jcApplyWorkContent.getFaultDesc());
            } catch (Exception ex) {
                LogUtil.e("交车申请审批进度item显示", ex.toString());
            }
        }
    }
}
