package jx.yunda.com.terminal.modules.jcApproval.adapter;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.modules.jcApproval.JCApprovalActivity;
import jx.yunda.com.terminal.modules.jcApproval.fragment.JCApprovalOperationFragment;
import jx.yunda.com.terminal.modules.jcApproval.model.JCApproval;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;

public class JCApprovalAdapter extends BaseRecyclerAdapter<JCApproval, JCApprovalAdapter.JCApprovalHolder> {
    JCApprovalActivity myActivity;
    boolean isVisibleSubmitBtn = true;

    public JCApprovalAdapter(JCApprovalActivity myActivity) {
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
        return R.layout.jc_approval_item;
    }

    protected JCApprovalAdapter.JCApprovalHolder createViewHolder(View view) {
        return new JCApprovalAdapter.JCApprovalHolder(view);
    }

    @Override
    public void onBindViewHolder(final JCApprovalAdapter.JCApprovalHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final JCApproval model = getData().get(position);
        if (isVisibleSubmitBtn) {
            holder.submit.setVisibility(View.VISIBLE);
            holder.process.setVisibility(View.GONE);
            holder.submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction transaction = myActivity.getSupportFragmentManager().beginTransaction();
                    JCApprovalOperationFragment fragment = new JCApprovalOperationFragment();
                    fragment.setSelectApproval(model);
                    transaction.add(R.id.jc_approval_panel, fragment);
                    transaction.commit();
                }
            });
        } else {
            holder.submit.setVisibility(View.GONE);
            holder.process.setVisibility(View.VISIBLE);
            holder.process.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtil.toastShort("审批流程查看，正在开发中！");
                    //FragmentTransaction transaction = myActivity.getSupportFragmentManager().beginTransaction();
                    //JCApplyProcessFragment fragment = new JCApplyProcessFragment();
                    //fragment.setSelectApply(model);
                    //transaction.add(R.id.jc_apply_panel, fragment);
                    //transaction.commit();
                }
            });
        }
    }

    public static class JCApprovalHolder extends BaseRecyclerAdapter.BaseViewHolder<JCApproval> {
        JCApproval jcApproval;
        @BindView(R.id.jc_approval_item_train)
        TextView train;
        @BindView(R.id.jc_approval_item_repair)
        TextView repair;
        @BindView(R.id.jc_approval_item_time)
        TextView time;
        @BindView(R.id.jc_approval_item_submit)
        Button submit;
        @BindView(R.id.jc_approval_item_process)
        Button process;

        public JCApprovalHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(JCApproval data) {
            try {
                jcApproval = data;
                train.setText(data.getTrainTypeShortName() + " " + data.getTrainNo());
                repair.setText(data.getRepairClassName());
                time.setText(data.getEndTime());
            } catch (Exception ex) {
                LogUtil.e("交车审批列表显示", ex.toString());
            }
        }
    }
}
