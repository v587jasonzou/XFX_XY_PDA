package jx.yunda.com.terminal.modules.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.Length;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.base.BaseActivity;
import jx.yunda.com.terminal.common.StringConstants;
import jx.yunda.com.terminal.common.update.UpdateManager;
import jx.yunda.com.terminal.modules.login.adapter.HisAccountOptionsAdapter;
import jx.yunda.com.terminal.modules.login.fragment.SettingFragment;
import jx.yunda.com.terminal.modules.login.presenter.ILogin;
import jx.yunda.com.terminal.modules.login.presenter.LoginPresenter;
import jx.yunda.com.terminal.modules.main.MainActivity;
import jx.yunda.com.terminal.utils.ActivityUtil;
import jx.yunda.com.terminal.utils.FontIconUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.EditTextByOneKey;
import jx.yunda.com.terminal.widget.treeview.utils.Utils;
import rx.functions.Action1;

/**
 * <li>说明：登录Acivity
 * <li>创建人：zhubs
 * <li>创建日期：2018年5月8日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements ILogin, ValidationListener {

    @Length(min = 1, max = 20, message = "用户名长度为1~20！")
    @BindView(R.id.login_username)
    EditTextByOneKey username;
    @BindView(R.id.login_btn)
    Button loginBtn;
    //验证
    Validator validator;
    //历史用户选择框
    PopupWindow hisAccountPopupWindow;
    ListView hisAccountOptions;
    //历史用户
    List<String> hisAccounts = new ArrayList<String>();
    @BindView(R.id.his_icon)
    TextView hisIcon;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    HisAccountOptionsAdapter hisAccountOptionsAdapter;

    /* 自动更新管理 */
    UpdateManager manager;

    @Override
    protected LoginPresenter getPresenter() {
        if (mPresenter == null)
            mPresenter = new LoginPresenter(this);
        return mPresenter;
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.login_act;
    }

    @Override
    public Bundle getInitParams() {
        return null;
    }


    //登录成功后页面跳转至主页面
    @Override
    public void PageTransformAfterLogin() {
        ActivityUtil.startActivityWithDelayed(this, MainActivity.class, getInitParams());
    }

    @Override
    public void initSubViews(View view) {
        // 指定字体
        FontIconUtil.fontReference(hisIcon, null);
        // 使用RxBinding实现登陆按钮根据是否填写账号激活或禁用
        RxTextView.textChanges(username).compose(this.<CharSequence>bindToLifecycle()).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                boolean isEmty = !TextUtils.isEmpty(charSequence);
                loginBtn.setBackgroundResource(isEmty ? R.drawable.button_selector : R.drawable.button_selector_disable);
                RxView.enabled(loginBtn).call(isEmty);
            }
        });
        // 设置用户名输入框焦点事件，获取焦点时关闭历史账号选择弹窗
        RxView.focusChanges(username).compose(this.<Boolean>bindToLifecycle()).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                hisAccountPopupWindowDismiss();
            }
        });
        // 初始化验证对象
        initValidator();

        // 登陆按钮，添加事件，并设置防抖动监听绑定
        RxView.clicks(loginBtn).throttleFirst(5, TimeUnit.SECONDS)
                .compose(this.<Void>bindToLifecycle())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        validator.validate();
                        hisAccountPopupWindowDismiss();
                    }
                });
        //设置上次登录的用户
        username.setText(mPresenter.getLastLoginAccount());

        //历史登陆数据设置
        View hisAccountPanel = getLayoutInflater().inflate(R.layout.account_history_options, null);
        hisAccountOptions = (ListView) hisAccountPanel.findViewById(R.id.his_account_options);
        hisAccountOptionsAdapter = new HisAccountOptionsAdapter(this, R.layout.account_history_item, hisAccounts);
        hisAccountOptions.setAdapter(hisAccountOptionsAdapter);
        hisAccountOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                username.setText(hisAccounts.get(position));
                hisAccountPopupWindowDismiss();
            }
        });
        hisAccountPopupWindow = new PopupWindow(hisAccountPanel, username.getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT, false);
        hisAccountPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hisIcon.setText(R.string.fa_angle_down);
            }
        });
        username.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“GO”键*/
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(
                                v.getApplicationWindowToken(), 0);
                    }
                    if (username.getText() != null && !username.getText().equals("")) {
                        mPresenter.doLogin(username.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.his_icon, R.id.sys_set,})
    void viewClick(View view) {
        switch (view.getId()) {
            case R.id.his_icon:
                if (hisAccountPopupWindow != null && hisAccountPopupWindow.isShowing())
                    hisAccountPopupWindowDismiss();
                else {
                    hisAccounts.clear();
                    hisAccounts.addAll(mPresenter.getHistoryAccounts());
                    if (hisAccounts.size() > 0) {
                        // 关闭软键盘
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm.isActive() && getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                        //用户名输入框失去焦点
                        username.clearFocus();
                        //更新历史用户列表
                        hisAccountOptionsAdapter.notifyDataSetChanged();
                        // 延迟弹窗历史账号选择框，因为立即弹出位置依然会错乱
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // 打开下拉窗口
                                hisAccountPopupWindow.setWidth(username.getWidth() + 100);
                                //selectPopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.popwin_bg_right));
                                hisAccountPopupWindow.showAsDropDown(username, -30, -40);
                                hisIcon.setText(R.string.fa_angle_up);
                            }
                        }, 100);
                    } else
                        ToastUtil.toastShort(StringConstants.HISTORY_ACCOUNTS_NULL);
                }
                break;
            case R.id.sys_set:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.login_panel, new SettingFragment());
                transaction.commit();
                break;
            default:
                break;
        }
    }

    /*关闭历史用户选择窗口*/
    private void hisAccountPopupWindowDismiss() {
        if (hisAccountPopupWindow != null && hisAccountPopupWindow.isShowing())
            hisAccountPopupWindow.dismiss();
    }

    @Override
    public void initData() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        tvVersion.setText("版本号：1.0." + Utils.getVersionCode(this));
        int width = metric.widthPixels;  //宽度（PX）
        int height = metric.heightPixels;  //高度（PX）
        float density = metric.density;  //密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  //密度DPI（120 / 160 / 240）
        Log.e("分辨率", width + "*" + height + "  " + densityDpi + "dpi");
    }

    /**
     * 初始化验证框架
     */
    public void initValidator() {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    //表单验证成功
    @Override
    public void onValidationSucceeded() {
        mPresenter.doLogin(username.getText().toString());
    }

    //表单验证失败
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String msgs = "";
        for (ValidationError error : errors) {
            msgs += error.getCollatedErrorMessage(this) + "\n";
        }
        if (!TextUtils.isEmpty(msgs)) {
            ToastUtil.toastShort(msgs);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hisAccountPopupWindowDismiss();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().webSocketDisConnnect();
        if (manager == null) {
            manager = new UpdateManager(this);
        }
        AlertDialog noticeDialog = manager.getNoticeDialog();
        if (noticeDialog == null || (noticeDialog != null && !noticeDialog.isShowing())) {
            // 检查软件更新
            manager.checkUpdate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hisAccountPopupWindowDismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //参考资料http://ivoter.iteye.com/blog/1596397
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }
    }
}
