package jx.yunda.com.terminal.modules.jcApply.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseRecyclerAdapter;
import jx.yunda.com.terminal.common.StringConstants;
import jx.yunda.com.terminal.modules.jcApply.model.JCApplyProcess;
import jx.yunda.com.terminal.utils.DateUtil;
import jx.yunda.com.terminal.utils.LogUtil;

public class JCApplyProcessAdapter extends BaseRecyclerAdapter<JCApplyProcess, JCApplyProcessAdapter.JCApplyProcessHolder> {

    @Override
    protected int getLayoutId() {
        return R.layout.jc_apply_process_item;
    }

    @Override
    protected JCApplyProcessHolder createViewHolder(View view) {
        return new JCApplyProcessHolder(view);
    }

    public static class JCApplyProcessHolder extends BaseRecyclerAdapter.BaseViewHolder<JCApplyProcess> {
        
        @BindView(R.id.jc_apply_process_panel)
        RelativeLayout itemPanel;
        @BindView(R.id.jc_apply_process_node_name)
        TextView nodeName;
        @BindView(R.id.jc_apply_process_persons)
        TextView persons;
        @BindView(R.id.jc_apply_process_approval_content)
        TextView content;
        @BindView(R.id.jc_apply_process_approver)
        TextView approver;
        @BindView(R.id.jc_apply_process_approval_time)
        TextView approvalTime;
        JCApplyProcess jcApplyProcess;

        public JCApplyProcessHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindData(JCApplyProcess data) {
            try {
                jcApplyProcess = data;
                if (!TextUtils.isEmpty(data.getNodeName())) {
                    itemPanel.setVisibility(View.VISIBLE);
                    nodeName.setText(data.getNodeName());
                    persons.setText(TextUtils.isEmpty(data.getApprovalNames()) ? "" : ("可审批人：" + data.getApprovalNames()));
                    if (TextUtils.isEmpty(data.getOpinionResult())) {
                        content.setText("未审批");
                    } else {
                        content.setText(("01".equals(data.getOpinionResult()) ? "同意" : "不同意") + (TextUtils.isEmpty(data.getOpinions()) ? "" : "：" + data.getOpinions()));
                        approver.setText((TextUtils.isEmpty(data.getApprovalUserName()) ? "" : ("审批人：" + data.getApprovalUserName())));
                        approvalTime.setText(DateUtil.millisecondStr2DateStr(data.getApprovalDate(), "yyyy-MM-dd HH:mm:ss"));
                        //approvalTime.setText(data.getApprovalDate());
                    }
                } else
                    itemPanel.setVisibility(View.GONE);


            } catch (Exception ex) {
                LogUtil.e("交车申请审批进度item显示", ex.toString());
            }
        }
    }
}
