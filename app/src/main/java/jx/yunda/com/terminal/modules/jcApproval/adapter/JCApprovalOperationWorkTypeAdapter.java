package jx.yunda.com.terminal.modules.jcApproval.adapter;

import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.modules.jcApproval.fragment.JCApprovalOperationFragment;
import jx.yunda.com.terminal.modules.jcApproval.model.JCApprovalOperationWorkType;
import jx.yunda.com.terminal.utils.LogUtil;
import rx.functions.Action1;

public class JCApprovalOperationWorkTypeAdapter extends BaseRecyclerAdapter<JCApprovalOperationWorkType, JCApprovalOperationWorkTypeAdapter.JCApprovalOperationWorkTypeHolder> {
    JCApprovalOperationFragment fragment;
    JCApprovalOperationWorkTypeHolder selectHolder;

    public JCApprovalOperationWorkTypeAdapter(JCApprovalOperationFragment fragment) {
        super();
        this.fragment = fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.jc_approval_operation_type_item;
    }

    @Override
    protected JCApprovalOperationWorkTypeHolder createViewHolder(View view) {
        return new JCApprovalOperationWorkTypeHolder(view);
    }

    @Override
    public void onBindViewHolder(final JCApprovalOperationWorkTypeHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final String workPlanIdx = fragment.getSelectApproval().getWorkPlanIdx();
        final JCApprovalOperationWorkType workType = getData().get(position);
        holder.text.setBackgroundResource(R.drawable.blue_line_white_bg);
        if (position == 0) {
            holder.text.setBackgroundResource(R.drawable.blue_line_active_bg);
            fragment.getPresenter().getWorkContents(workPlanIdx, workType.getName());
            fragment.setSelectWorkType(workType);
            selectHolder = holder;
        }

        RxView.clicks(holder.text).throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (selectHolder != null)
                            selectHolder.text.setBackgroundResource(R.drawable.blue_line_white_bg);
                        holder.text.setBackgroundResource(R.drawable.blue_line_active_bg);
                        fragment.getPresenter().getWorkContents(workPlanIdx, workType.getName());
                        fragment.setSelectWorkType(workType);
                        selectHolder = holder;
                    }
                });
    }

    public static class JCApprovalOperationWorkTypeHolder extends BaseRecyclerAdapter.BaseViewHolder<JCApprovalOperationWorkType> {
        JCApprovalOperationWorkType jcApprovalWorkType;

        @BindView(R.id.jc_approval_operation_type_item_text)
        TextView text;

        public JCApprovalOperationWorkTypeHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(JCApprovalOperationWorkType data) {
            try {
                jcApprovalWorkType = data;
                text.setBackgroundResource(R.color.white);
                text.setText(data.getName() + " " + data.getDone() + "/" + data.getDone());
            } catch (Exception ex) {
                LogUtil.e("交车申请审批进度item显示", ex.toString());
            }
        }
    }
}
