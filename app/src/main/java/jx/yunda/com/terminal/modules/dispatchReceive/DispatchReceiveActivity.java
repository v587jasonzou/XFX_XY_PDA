package jx.yunda.com.terminal.modules.dispatchReceive;

import android.app.AlertDialog;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.modules.dispatchReceive.fragment.DispatchReceiveByPlanFragment;
import jx.yunda.com.terminal.modules.dispatchReceive.fragment.DispatchReceiveByTemporaryFragment;
import jx.yunda.com.terminal.modules.dispatchReceive.presenter.DispatchReceivePresenter;
import jx.yunda.com.terminal.modules.dispatchReceive.presenter.IDispatchReceive;
import jx.yunda.com.terminal.utils.LogUtil;
import rx.functions.Action1;

public class DispatchReceiveActivity extends BaseActivity<DispatchReceivePresenter> implements IDispatchReceive {

    AlertDialog addTrainDialog;

    @Override
    protected DispatchReceivePresenter getPresenter() {
        return null;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.dispatch_receive_act;
    }

    @Override
    public void initSubViews(View view) {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.dispatch_receive_back, R.id.dispatch_receive_add})
    void viewClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.dispatch_receive_back:
                    finish();
                    break;
                case R.id.dispatch_receive_add:
                    showDialog();
                    break;
                default:
                    break;
            }

        } catch (Exception ex) {
            LogUtil.e("调度接车页面返回", ex.toString());
        }
    }

    void showDialog() {
        if (addTrainDialog == null) {
            View view = View.inflate(this, R.layout.dispatch_receive_add_dailog, this.getViewGroupRoot());
            ImageButton closeBtn = (ImageButton) view.findViewById(R.id.dispatch_receive_add_dailog_close);
            Button planBtn = (Button) view.findViewById(R.id.dispatch_receive_add_plan_btn);
            Button temporaryBtn = (Button) view.findViewById(R.id.dispatch_receive_add_temporary_btn);
            RxView.clicks(closeBtn)
                    .throttleFirst(5, TimeUnit.SECONDS)
                    .compose(this.<Void>bindToLifecycle())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            addTrainDialog.dismiss();
                        }
                    });
            RxView.clicks(planBtn)
                    .throttleFirst(5, TimeUnit.SECONDS)
                    .compose(this.<Void>bindToLifecycle())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.add(R.id.dispatch_receive_fragment_panel, new DispatchReceiveByPlanFragment());
                            transaction.commit();
                            addTrainDialog.dismiss();
                        }
                    });
            RxView.clicks(temporaryBtn)
                    .throttleFirst(5, TimeUnit.SECONDS)
                    .compose(this.<Void>bindToLifecycle())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.add(R.id.dispatch_receive_fragment_panel, new DispatchReceiveByTemporaryFragment());
                            transaction.commit();
                            addTrainDialog.dismiss();
                        }
                    });

            addTrainDialog = new AlertDialog.Builder(this).setView(view).create();
        }
        addTrainDialog.show();
    }
}
