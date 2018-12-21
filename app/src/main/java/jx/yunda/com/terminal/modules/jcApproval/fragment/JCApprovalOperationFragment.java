package jx.yunda.com.terminal.modules.jcApproval.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.modules.jcApproval.JCApprovalActivity;
import jx.yunda.com.terminal.modules.jcApproval.adapter.JCApprovalOperationWorkContentAdapter;
import jx.yunda.com.terminal.modules.jcApproval.adapter.JCApprovalOperationWorkTypeAdapter;
import jx.yunda.com.terminal.modules.jcApproval.model.JCApproval;
import jx.yunda.com.terminal.modules.jcApproval.model.JCApprovalOperationWorkContent;
import jx.yunda.com.terminal.modules.jcApproval.model.JCApprovalOperationWorkType;
import jx.yunda.com.terminal.modules.jcApproval.presenter.IJCApprovalOperation;
import jx.yunda.com.terminal.modules.jcApproval.presenter.JCApprovalOperationPresenter;
import jx.yunda.com.terminal.utils.LogUtil;
import rx.functions.Action1;


public class JCApprovalOperationFragment extends BaseFragment<JCApprovalOperationPresenter> implements IJCApprovalOperation {
    private JCApproval selectApproval;
    private JCApprovalOperationWorkType selectWorkType;
    JCApprovalOperationWorkTypeAdapter workTypeAdapter;
    JCApprovalOperationWorkContentAdapter workContentAdapter;
    @BindView(R.id.jc_approval_operation_work_type_recycler)
    RecyclerView workTypeRecycler;
    @BindView(R.id.jc_approval_operation_work_content_recycler)
    RecyclerView workContentRecycler;
    @BindView(R.id.jc_approval_operation_title)
    TextView title;


    AlertDialog approvalDialog;
    TextView approvalDialogTitle;
    TextView approvalDialogResult;
    Button approvalDialogCancel;
    Button approvalDialogConfirm;

    boolean isAgreement = false;

    public JCApproval getSelectApproval() {
        return selectApproval;
    }

    public void setSelectApproval(JCApproval selectApproval) {
        this.selectApproval = selectApproval;
    }

    public JCApprovalOperationWorkType getSelectWorkType() {
        return selectWorkType;
    }

    public void setSelectWorkType(JCApprovalOperationWorkType selectWorkType) {
        this.selectWorkType = selectWorkType;
    }


    @Override
    public JCApprovalOperationPresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new JCApprovalOperationPresenter(this);
        return mPresenter;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.jc_approval_operation_fm;
    }

    @Override
    public void initSubViews(View view) {
        workTypeAdapter = new JCApprovalOperationWorkTypeAdapter(this);
        workContentAdapter = new JCApprovalOperationWorkContentAdapter(this);
        workTypeRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayout.VERTICAL, false));
        workTypeRecycler.setAdapter(workTypeAdapter);
        workContentRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        workContentRecycler.setAdapter(workContentAdapter);
        if (selectApproval != null)
            title.setText("【" + selectApproval.getTrainTypeShortName() + " " + selectApproval.getTrainNo() + "】" + (TextUtils.isEmpty(selectApproval.getRepairClassName()) ? "" : selectApproval.getRepairClassName()) + " " + (TextUtils.isEmpty(selectApproval.getEndTime()) ? "" : selectApproval.getEndTime()));
        if (approvalDialog == null) {
            View viewDailog = View.inflate(getContext(), R.layout.jc_approval_dialog, null);
            approvalDialogTitle = (TextView) viewDailog.findViewById(R.id.jc_approval_dialog_title);
            approvalDialogResult = (TextView) viewDailog.findViewById(R.id.jc_approval_dialog_result);
            approvalDialogCancel = (Button) viewDailog.findViewById(R.id.jc_approval_dialog_cancel);
            approvalDialogConfirm = (Button) viewDailog.findViewById(R.id.jc_approval_dialog_confirm);
            RxView.clicks(approvalDialogCancel)
                    .throttleFirst(5, TimeUnit.SECONDS)
                    .compose(this.<Void>bindToLifecycle())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            dismissApprovalDialog();
                        }
                    });
            RxView.clicks(approvalDialogConfirm)
                    .throttleFirst(5, TimeUnit.SECONDS)
                    .compose(this.<Void>bindToLifecycle())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            getPresenter().submitApproval(selectApproval, isAgreement, approvalDialogResult.getText().toString());
                        }
                    });
            approvalDialog = new AlertDialog.Builder(getContext()).setView(viewDailog).create();
            approvalDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    approvalDialogTitle.setText("");
                    approvalDialogResult.setText("");
                }
            });
        }
    }

    @Override
    public void initData() {
        getPresenter().getWorkTypes(selectApproval.getWorkPlanIdx());
    }

    @OnClick({R.id.jc_approval_operation_back, R.id.jc_approval_content_agree, R.id.jc_approval_content_disagree})
    void viewClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.jc_approval_operation_back:
                    closeJCApplyOperationFragment();
                    break;
                case R.id.jc_approval_content_agree:
                    isAgreement = true;
                    showApprovalDailog("审批结果-同意");
                    break;
                case R.id.jc_approval_content_disagree:
                    isAgreement = false;
                    showApprovalDailog("审批结果-不同意");
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            LogUtil.e("交车审批操作页面", ex.toString());
        }
    }

    @Override
    public void dismissApprovalDialog() {
        if (approvalDialog != null)
            approvalDialog.dismiss();
    }

    @Override
    public void showApprovalDailog(String title) {
        if (approvalDialogTitle != null)
            approvalDialogTitle.setText(title);
        if (approvalDialog != null)
            approvalDialog.show();
    }

    @Override
    public void closeJCApplyOperationFragment() {
        dismissApprovalDialog();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(this);
        transaction.commit();
        ((JCApprovalActivity) getActivity()).setPageData();
    }

    @Override
    public void workTypeRecyclerNotifyDataSetChanged(List<JCApprovalOperationWorkType> datas) {
        try {
            workTypeAdapter.setData(datas == null ? new ArrayList<JCApprovalOperationWorkType>() : datas);
            workTypeAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            LogUtil.e("交车审批主页面列表刷新", ex.toString());
        }
    }

    @Override
    public void workContentRecyclerNotifyDataSetChanged(List<JCApprovalOperationWorkContent> datas) {
        try {
            workContentAdapter.setData(datas == null ? new ArrayList<JCApprovalOperationWorkContent>() : datas);
            workContentAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            LogUtil.e("交车审批主页面列表刷新", ex.toString());
        }
    }

    @Override
    public void workContentRecyclerNotifyItemChanged(int position, JCApprovalOperationWorkContent workContent) {
        try {
            workContentAdapter.getData().get(position).isShowBackBtn = workContent.isShowBackBtn;
            workContentAdapter.notifyItemChanged(position);
        } catch (Exception ex) {
            LogUtil.e("交车审批主页面列表刷新", ex.toString());
        }
    }
}
