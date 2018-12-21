package jx.yunda.com.terminal.modules.jcApply.adapter;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.modules.jcApply.JCApplyActivity;
import jx.yunda.com.terminal.modules.jcApply.fragment.JCApplyOperationFragment;
import jx.yunda.com.terminal.modules.jcApply.fragment.JCApplyProcessFragment;
import jx.yunda.com.terminal.modules.jcApply.model.JCApply;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;

public class JCApplyAdapter extends BaseRecyclerAdapter<JCApply, JCApplyAdapter.JCApplyHolder> {

    JCApplyActivity myActivity;
    boolean isVisibleSubmitBtn = true;

    public JCApplyAdapter(JCApplyActivity myActivity) {
        super();
        this.myActivity = myActivity;
    }

    public boolean isVisibleSubmitBtn() {
        return isVisibleSubmitBtn;
    }

    public void setVisibleSubmitBtn(boolean visibleSubmitBtn) {
        isVisibleSubmitBtn = visibleSubmitBtn;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.jc_apply_item;
    }

    @Override
    protected JCApplyHolder createViewHolder(View view) {
        final JCApplyHolder holder = new JCApplyAdapter.JCApplyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final JCApplyHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final JCApply model = getData().get(position);
        if (isVisibleSubmitBtn) {
            holder.submit.setVisibility(View.VISIBLE);
            holder.process.setVisibility(View.GONE);
            holder.submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction transaction = myActivity.getSupportFragmentManager().beginTransaction();
                    JCApplyOperationFragment fragment = new JCApplyOperationFragment();
                    fragment.setSelectApply(model);
                    transaction.add(R.id.jc_apply_panel, fragment);
                    transaction.commit();
                }
            });
        } else {
            holder.submit.setVisibility(View.GONE);
            holder.process.setVisibility(View.VISIBLE);
            holder.process.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction transaction = myActivity.getSupportFragmentManager().beginTransaction();
                    JCApplyProcessFragment fragment = new JCApplyProcessFragment();
                    fragment.setSelectApply(model);
                    transaction.add(R.id.jc_apply_panel, fragment);
                    transaction.commit();
                }
            });
        }
    }

    public static class JCApplyHolder extends BaseRecyclerAdapter.BaseViewHolder<JCApply> {

        @BindView(R.id.jc_apply_item_train)
        TextView train;
        @BindView(R.id.jc_apply_item_repair)
        TextView repair;
        @BindView(R.id.jc_apply_item_time)
        TextView time;
        @BindView(R.id.jc_apply_item_submit)
        Button submit;
        @BindView(R.id.jc_apply_item_process)
        Button process;
        JCApply jcApply;

        public JCApplyHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(JCApply data) {
            try {
                jcApply = data;
                train.setText(data.getTrainTypeShortName() + " " + data.getTrainNo());
                repair.setText(data.getRepairClassName());
                time.setText(data.getEndTime());

            } catch (Exception ex) {
                LogUtil.e("交车申请列表显示", ex.toString());
            }
        }
    }
}
