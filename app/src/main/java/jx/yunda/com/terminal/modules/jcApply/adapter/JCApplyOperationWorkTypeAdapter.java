package jx.yunda.com.terminal.modules.jcApply.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.modules.jcApply.fragment.JCApplyOperationFragment;
import jx.yunda.com.terminal.modules.jcApply.model.JCApplyOperationWorkType;
import jx.yunda.com.terminal.utils.LogUtil;
import rx.functions.Action1;

public class JCApplyOperationWorkTypeAdapter extends BaseRecyclerAdapter<JCApplyOperationWorkType, JCApplyOperationWorkTypeAdapter.JCApplyOperationWorkTypeHolder> {
    JCApplyOperationFragment fragment;
    JCApplyOperationWorkTypeHolder selectHolder;

    public JCApplyOperationWorkTypeAdapter(JCApplyOperationFragment fragment) {
        super();
        this.fragment = fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.jc_apply_operation_type_item;
    }

    @Override
    protected JCApplyOperationWorkTypeHolder createViewHolder(View view) {
        return new JCApplyOperationWorkTypeHolder(view);
    }

    @Override
    public void onBindViewHolder(final JCApplyOperationWorkTypeHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final String workPlanIdx = fragment.getSelectApply().getWorkPlanIdx();
        final JCApplyOperationWorkType workType = getData().get(position);
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

    public static class JCApplyOperationWorkTypeHolder extends BaseRecyclerAdapter.BaseViewHolder<JCApplyOperationWorkType> {
        JCApplyOperationWorkType jcApplyWorkType;

        @BindView(R.id.jc_apply_operation_type_item_text)
        TextView text;

        public JCApplyOperationWorkTypeHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(JCApplyOperationWorkType data) {
            try {
                jcApplyWorkType = data;
                text.setBackgroundResource(R.color.white);
                text.setText(data.getName() + " " + data.getDone() + "/" + data.getDone());
            } catch (Exception ex) {
                LogUtil.e("交车申请审批进度item显示", ex.toString());
            }
        }
    }
}
