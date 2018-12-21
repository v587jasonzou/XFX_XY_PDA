package jx.yunda.com.terminal.modules.login.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;

import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.base.JXApplication;
import jx.yunda.com.terminal.common.StringConstants;
import jx.yunda.com.terminal.modules.login.model.SysSetInfo;
import jx.yunda.com.terminal.utils.LogUtil;

public class SettingPresenter extends BasePresenter<ISettingFragment> {
    SharedPreferences sysSharePreferences;

    public SettingPresenter(ISettingFragment view) {
        super(view);
        sysSharePreferences = JXApplication.getContext().getSharedPreferences(StringConstants.SYSTEM_BASE_DATA, Context.MODE_PRIVATE);
    }

    public boolean saveSysData(SysSetInfo sysSetInfo) {
        try {
            SharedPreferences.Editor editor = sysSharePreferences.edit();
            String baseURL = sysSetInfo.getBaseURL();
            String msgWebURL = sysSetInfo.getMsgWebURL();
            String msgSocketAddress = sysSetInfo.getMsgSocketAddress();
            int msgSocketPort = sysSetInfo.getMsgSocketPort();
            if (!TextUtils.isEmpty(baseURL)) {
                if (!baseURL.startsWith("http://")) {
                    baseURL = "http://" + baseURL;
                }
                if (!baseURL.endsWith("/")) {
                    baseURL += "/";
                }
            }
            if (!TextUtils.isEmpty(msgWebURL)) {
                if (!msgWebURL.startsWith("http://")) {
                    msgWebURL = "http://" + msgWebURL;
                }
                if (!msgWebURL.endsWith("/")) {
                    msgWebURL += "/";
                }
            }
            editor.putString(StringConstants.SYSTEM_BASE_URL_KEY, baseURL);
            editor.putString(StringConstants.SYSTEM_MESSAGE_WEB_URL_KEY, msgWebURL);
            editor.putString(StringConstants.SYSTEM_MESSAGE_SOCKET_ADDRESS_KEY, msgSocketAddress);
            editor.putInt(StringConstants.SYSTEM_MESSAGE_SOCKET_PORT_KEY, msgSocketPort);
            editor.commit();
            if (!TextUtils.isEmpty(baseURL)) {
                RxRetrofitApp.setBaseUrl(baseURL);
            }
            SysInfo.baseURL = baseURL;
            SysInfo.msgSocketAdress = msgSocketAddress;
            SysInfo.msgSocketPort = msgSocketPort;
            SysInfo.msgWebURL = msgWebURL;
            return true;
        } catch (Exception ex) {
            LogUtil.e("SysSetting", ex.getMessage());
            return false;
        }

    }
}