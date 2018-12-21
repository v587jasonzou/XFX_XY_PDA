package jx.yunda.com.terminal.modules.specialApproval.adapter;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.modules.specialApproval.SpecialApprovalActivity;
import jx.yunda.com.terminal.modules.specialApproval.fragment.SpecialApprovalContentFragment;
import jx.yunda.com.terminal.modules.specialApproval.model.SpecialApproval;
import jx.yunda.com.terminal.utils.DateUtil;
import jx.yunda.com.terminal.utils.LogUtil;

public class SpecialApprovalAdapter extends BaseRecyclerAdapter<SpecialApproval, SpecialApprovalAdapter.SpecialApprovalHolder> {
    SpecialApprovalActivity myActivity;

    public SpecialApprovalAdapter(SpecialApprovalActivity myActivity) {
        super();
        this.myActivity = myActivity;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.special_approval_item;
    }

    protected SpecialApprovalAdapter.SpecialApprovalHolder createViewHolder(View view) {
        final SpecialApprovalAdapter.SpecialApprovalHolder holder = new SpecialApprovalAdapter.SpecialApprovalHolder(view);

        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = myActivity.getSupportFragmentManager().beginTransaction();
                SpecialApprovalContentFragment fragment = new SpecialApprovalContentFragment();
                fragment.setSelectApproval(getData().get(holder.getAdapterPosition()));
                transaction.add(R.id.special_approval_panel, fragment);
                transaction.commit();
            }
        });
        return holder;
    }

    public static class SpecialApprovalHolder extends BaseRecyclerAdapter.BaseViewHolder<SpecialApproval> {
        SpecialApproval specialApproval;
        @BindView(R.id.special_approval_item_train)
        TextView train;
        @BindView(R.id.special_approval_item_repair)
        TextView repair;
        @BindView(R.id.special_approval_item_time)
        TextView time;
        @BindView(R.id.special_approval_item_submit)
        Button submit;

        public SpecialApprovalHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(SpecialApproval data) {
            try {
                specialApproval = data;
                train.setText(data.getTrainTypeShortName() + " " + data.getTrainNo());
                repair.setText(data.getFaultDesc());
                time.setText(DateUtil.millisecondStr2DateStr(data.getFaultOccurDate(), "yyyy-MM-dd"));

            } catch (Exception ex) {
                LogUtil.e("提票审批列表显示", ex.toString());
            }
        }
    }
}
