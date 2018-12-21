package jx.yunda.com.terminal.modules.specialApproval.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.jakewharton.rxbinding.view.RxView;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.base.baseDictData.model.DicDataItem;
import jx.yunda.com.terminal.base.baseDictData.presenter.BaseDictPresenter;
import jx.yunda.com.terminal.common.StringConstants;
import jx.yunda.com.terminal.modules.specialApproval.SpecialApprovalActivity;
import jx.yunda.com.terminal.modules.specialApproval.model.SpecialApproval;
import jx.yunda.com.terminal.modules.specialApproval.presenter.ISpecialApprovalContent;
import jx.yunda.com.terminal.modules.specialApproval.presenter.SpecialApprovalContentPresenter;
import jx.yunda.com.terminal.modules.tpprocess.model.BaseBean;
import rx.functions.Action1;


public class SpecialApprovalContentFragment extends BaseFragment<SpecialApprovalContentPresenter> implements ISpecialApprovalContent {

    private SpecialApproval selectApproval;

    @BindView(R.id.special_approval_content_title)
    TextView title;
    /*车型车号*/
    @BindView(R.id.special_approval_content_train)
    TextView trainTypeAndNo;
    /*故障位置*/
    @BindView(R.id.special_approval_content_fault_position)
    TextView faultPosition;
    /*不良状态*/
    @BindView(R.id.special_approval_content_blzt)
    TextView blzt;
    /*专业*/
    @BindView(R.id.special_approval_content_zy)
    TextView zhuanYe;
    /*考核*/
    @BindView(R.id.special_approval_content_kh)
    TextView kaoHe;
    /*施修方法*/
    @BindView(R.id.special_approval_content_sxff)
    TextView shiXiuFangFa;

    AlertDialog approvalDialog;
    TextView approvalDialogTitle;
    TextView approvalDialogResult;
    //    Button approvalDialogCancel;
//    Button approvalDialogConfirm;
    boolean isAgreement = false;

    public SpecialApproval getSelectApproval() {
        return selectApproval;
    }

    public void setSelectApproval(SpecialApproval selectApproval) {
        this.selectApproval = selectApproval;
    }

    @Override
    protected SpecialApprovalContentPresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new SpecialApprovalContentPresenter(this);
        return mPresenter;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.special_approval_content_fm;
    }

    @Override
    public void initSubViews(View view) {
        if (approvalDialog == null) {
            View viewDailog = View.inflate(getContext(), R.layout.special_approval_dialog, null);
            approvalDialogTitle = (TextView) viewDailog.findViewById(R.id.special_approval_dialog_title);
            approvalDialogResult = (TextView) viewDailog.findViewById(R.id.special_approval_dialog_result);
            Button approvalDialogCancel = (Button) viewDailog.findViewById(R.id.special_approval_dialog_cancel);
            Button approvalDialogConfirm = (Button) viewDailog.findViewById(R.id.special_approval_dialog_confirm);
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
        if (selectApproval != null) {
            trainTypeAndNo.setText(selectApproval.getTrainTypeShortName() + " " + selectApproval.getTrainNo());
            faultPosition.setText(selectApproval.getFixPlaceFullName());
            blzt.setText(selectApproval.getFaultDesc());
            String[] reasonAnalysis = selectApproval.getReasonAnalysis().split(";");
            String[] reasonAnalysisIDs = selectApproval.getReasonAnalysisID().split(";");
            if (reasonAnalysis != null && reasonAnalysisIDs != null && reasonAnalysis.length > 0) {
                int len = reasonAnalysis.length;
                for (int i = 0; i < len; i++) {
                    String an = reasonAnalysis[i];
                    String anId = reasonAnalysisIDs[i];
                    TextView textView = StringConstants.DIC_JXGC_Fault_Ticket_YYFX_10.equals(anId.substring(0, 2)) ? zhuanYe : kaoHe;
                    if (TextUtils.isEmpty(textView.getText().toString())) {
                        textView.setText(an);
                        textView.setTag(anId);
                    } else {
                        textView.setText(textView.getText().toString() + ";" + an);
                        textView.setTag(textView.getTag() == null ? anId : (textView.getTag().toString() + ";" + anId));
                    }
                }
            }
            shiXiuFangFa.setText(selectApproval.getResolutionContent());
        }
    }

    @Override
    public void closeFragmentRefreshMainPage() {
        dismissApprovalDialog();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(this);
        transaction.commit();
        ((SpecialApprovalActivity) getActivity()).initData();
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

    @OnClick({R.id.special_approval_content_back, R.id.special_approval_content_agree, R.id.special_approval_content_disagree})
    void viewClick(View view) {
        switch (view.getId()) {
            case R.id.special_approval_content_back:
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.remove(this);
                transaction.commit();
                break;
            case R.id.special_approval_content_agree:
                isAgreement = true;
                showApprovalDailog("审批结果-同意");
                break;
            case R.id.special_approval_content_disagree:
                isAgreement = false;
                showApprovalDailog("审批结果-不同意");
                break;
            default:
                break;

        }
    }
}
