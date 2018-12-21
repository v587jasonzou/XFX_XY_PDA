package jx.yunda.com.terminal.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.api.BaseApi;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import jx.yunda.com.terminal.modules.login.LoginActivity;
import jx.yunda.com.terminal.utils.ActivityUtil;

/**
 * <li>类名: BaseActivity
 * <li>说明: 项目活动继承类
 * <li>创建人：Hed
 * <li>创建日期：2018/4/17 17:04
 * <li>修改人: Zhubs
 * <li>修改日期：2018/4/17 17:04
 * <li>版权: Copyright (c) 2015 运达科技公司
 * <li>版本:1.0
 */
public abstract class BaseActivity<T extends BasePresenter> extends RxAppCompatActivity implements IView {
    protected T mPresenter;
    /*加载框可自己定义*/
    protected ProgressDialog progressDialog;
    protected BaseApi baseApi;
    protected OnScanCodeCallBack onScanCodeCallBack;
    protected AlertDialog loginDialog;

    public interface OnScanCodeCallBack {
        void ScanCodeSuccess(String code);
    }

    public void setOnScanCodeCallBack(OnScanCodeCallBack onScanCodeCallBack) {
        this.onScanCodeCallBack = onScanCodeCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View containerView = getLayoutInflater().inflate(getContentViewId(), getViewGroupRoot());
        setContentView(containerView);
        ActivityCollector.addActivity(this);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCancelable(true);
        baseApi = new BaseApi();
        baseApi.setProgressDialog(progressDialog);

        initPresenter();
        initSubViews(containerView);
        initData();
    }

    /*扫码成功后回调*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCode(String messageEvent) {
        if (messageEvent.equals("UnLogin")) {
            if (loginDialog == null) {
                loginDialog = new AlertDialog.Builder(this).setTitle("提示").setMessage("当前用户登录已过期，请重新登录")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ActivityUtil.startActivity(BaseActivity.this, LoginActivity.class);
                            }
                        }).setCancelable(false).create();
            }
            if (!loginDialog.isShowing()) {
                if(!this.isFinishing()){
                    loginDialog.show();
                }
            }
        } else {
//            onScanCodeCallBack.ScanCodeSuccess(messageEvent);
        }

    }

    @Override
    protected void onResume() {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (progressDialog!=null&&progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        progressDialog.dismiss();
        progressDialog = null;
    }

    public BaseApi getBaseApi() {
        return baseApi;
    }

    private void initPresenter() {
        mPresenter = getPresenter();
    }

    protected abstract T getPresenter();

    public ProgressDialog getProgressDialog() {
        if(progressDialog==null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCancelable(true);
        }
        return progressDialog;
    }

    @Override
    public abstract ViewGroup getViewGroupRoot();

    @Override
    public abstract int getContentViewId();

    @Override
    public abstract void initSubViews(View view);

    @Override
    public abstract void initData();
}
