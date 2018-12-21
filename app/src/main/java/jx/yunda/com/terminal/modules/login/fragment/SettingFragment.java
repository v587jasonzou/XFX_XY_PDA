package jx.yunda.com.terminal.modules.login.fragment;

import android.support.annotation.IntegerRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Digits;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import jx.yunda.com.terminal.R;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BaseFragment;
import jx.yunda.com.terminal.common.StringConstants;
import jx.yunda.com.terminal.modules.login.model.SysSetInfo;
import jx.yunda.com.terminal.modules.login.presenter.ISettingFragment;
import jx.yunda.com.terminal.modules.login.presenter.SettingPresenter;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;
import jx.yunda.com.terminal.widget.EditTextByOneKey;


public class SettingFragment extends BaseFragment<SettingPresenter> implements ISettingFragment, Validator.ValidationListener {

    @NotEmpty(message = "服务器地址不能为空！")
    @BindView(R.id.login_setting_baseurl)
    EditTextByOneKey loginSettingBaseUrl;
    @NotEmpty(message = "消息WebSocket地址不能为空！")
    @BindView(R.id.login_setting_msg_socket_address)
    EditTextByOneKey loginSettingMsgSocketAddress;
    @NotEmpty(message = "消息WebSocket端口不能为空！")
    @BindView(R.id.login_setting_msg_socket_port)
    EditTextByOneKey loginSettingMsgSocketPort;
    @NotEmpty(message = "消息服务器地址设置！")
    @BindView(R.id.login_setting_msg_web_url)
    EditTextByOneKey loginSettingMsgWebUrl;
    //验证
    Validator validator;

    @Override
    protected SettingPresenter getPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    public ViewGroup getViewGroupRoot() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.login_setting_fm;
    }

    @Override
    public void initSubViews(View view) {
        try {
            String baseUrl = SysInfo.baseURL.replace("http://", "");
            loginSettingBaseUrl.setText(baseUrl);
            loginSettingMsgSocketAddress.setText(SysInfo.msgSocketAdress);
            loginSettingMsgSocketPort.setText(SysInfo.msgSocketPort + "");
            String msgWebURL = SysInfo.msgWebURL.replace("http://", "");
            loginSettingMsgWebUrl.setText(msgWebURL);
            // 初始化验证对象
            initValidator();
        } catch (Exception ex) {
            LogUtil.e("初始化系统设置页面", ex.toString());
        }
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.login_setting_back, R.id.login_setting_save_btn})
    void viewClick(View view) {
        switch (view.getId()) {
            case R.id.login_setting_back:
                CloseSettingPage();
                break;
            case R.id.login_setting_save_btn:
                validator.validate();
                break;
            default:
                break;
        }
    }

    void CloseSettingPage() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(this);
        transaction.commit();
    }

    public void initValidator() {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public void onValidationSucceeded() {
        try {
            SysSetInfo sysSetInfo=new SysSetInfo();
            sysSetInfo.setBaseURL(loginSettingBaseUrl.getText().toString());
            sysSetInfo.setMsgWebURL(loginSettingMsgWebUrl.getText().toString());
            sysSetInfo.setMsgSocketAddress( loginSettingMsgSocketAddress.getText().toString());
            sysSetInfo.setMsgSocketPort(Integer.parseInt(loginSettingMsgSocketPort.getText().toString()));
            boolean isSuccess = mPresenter.saveSysData(sysSetInfo);
            if (isSuccess) {
                ToastUtil.toastShort(StringConstants.OPERATE_SUCCESS);
                CloseSettingPage();
            } else
                ToastUtil.toastShort(StringConstants.OPERATE_FAIL);
        } catch (Exception ex) {
            LogUtil.e("系统设置保存", ex.toString());
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }
}
