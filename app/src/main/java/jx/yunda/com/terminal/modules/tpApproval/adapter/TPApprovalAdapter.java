package jx.yunda.com.terminal.modules.tpApproval.adapter;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.modules.tpApproval.TPApprovalActivity;
import jx.yunda.com.terminal.modules.tpApproval.fragment.TPApprovalContentFragment;
import jx.yunda.com.terminal.modules.tpApproval.model.TPApproval;
import jx.yunda.com.terminal.modules.workshoptaskdispatch.model.FaultTicket;
import jx.yunda.com.terminal.utils.LogUtil;

public class TPApprovalAdapter extends BaseRecyclerAdapter<FaultTicket, TPApprovalAdapter.TPApprovalHolder> {
    TPApprovalActivity myActivity;

    public TPApprovalAdapter(TPApprovalActivity myActivity) {
        super();
        this.myActivity = myActivity;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tp_approval_item;
    }

    protected TPApprovalAdapter.TPApprovalHolder createViewHolder(View view) {
        return new TPApprovalAdapter.TPApprovalHolder(view);
    }

    @Override
    public void onBindViewHolder(TPApprovalHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final FaultTicket model = getData().get(position);
        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = myActivity.getSupportFragmentManager().beginTransaction();
                TPApprovalContentFragment fragment = new TPApprovalContentFragment();
                fragment.setSelectApproval(model);
                transaction.add(R.id.tp_approval_panel, fragment);
                transaction.commit();
            }
        });
    }

    public static class TPApprovalHolder extends BaseRecyclerAdapter.BaseViewHolder<FaultTicket> {
        FaultTicket tpApproval;
        @BindView(R.id.tp_approval_item_train)
        TextView train;
        @BindView(R.id.tp_approval_item_fault_desc)
        TextView faultDesc;
        @BindView(R.id.tp_approval_item_submit)
        Button submit;

        public TPApprovalHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(FaultTicket data) {
            try {
                tpApproval = data;
                train.setText(data.getTrainTypeShortName() + " " + data.getTrainNo());
                faultDesc.setText(data.getFaultDesc());
            } catch (Exception ex) {
                LogUtil.e("提票审批列表显示", ex.toString());
            }
        }
    }
}
