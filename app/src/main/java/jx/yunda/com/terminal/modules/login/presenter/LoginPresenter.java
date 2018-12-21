package jx.yunda.com.terminal.modules.login.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.RequestFactory;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.BasePresenter;
import jx.yunda.com.terminal.base.JXApplication;
import jx.yunda.com.terminal.common.IntConstants;
import jx.yunda.com.terminal.common.StringConstants;
import jx.yunda.com.terminal.modules.login.model.UserInfo;
import jx.yunda.com.terminal.modules.api.LoginApi;
import jx.yunda.com.terminal.utils.DateUtil;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.NetWorkUtils;
import jx.yunda.com.terminal.utils.ToastUtil;

public class LoginPresenter extends BasePresenter<ILogin> {

    SharedPreferences accountSharePreferences;

    public LoginPresenter(ILogin view) {
        super(view);
        accountSharePreferences = JXApplication.getContext().getSharedPreferences(StringConstants.ACCOUNT_OF_SHARE_PREFERENCES, Context.MODE_PRIVATE);
    }

    //回显上次登录的用户名
    public String getLastLoginAccount() {
        return accountSharePreferences.getString(StringConstants.LAST_LOGIN_USER_ACCOUNT_KEY, "");
    }

    public List<String> getHistoryAccounts() {
        List<String> hisAccounts = new ArrayList<String>();
        //在SharePreferences中获取登录用户历史数据
        Set<String> hisAccountSet = accountSharePreferences.getStringSet(StringConstants.HISTORY_USER_ACCOUNT_KEY, new HashSet<String>());
        if (hisAccountSet.size() == 0) return hisAccounts;
        //通过TreeSet自动排序
        TreeSet<String> hisAccountTreeSet = new TreeSet<String>();
        hisAccountTreeSet.addAll(hisAccountSet);
        String[] acounts = new String[hisAccountTreeSet.size()];
        hisAccountTreeSet.toArray(acounts);
        int length = acounts.length;
        //获取最后登录的10个用户
        for (int i = length - 1; i >= 0 && i >= length - IntConstants.SHOW_HISTORY_ACCOUNT_COUNT; i--)
            hisAccounts.add(acounts[i].split("_")[1]);
        return hisAccounts;
    }

    public void doLogin(final String username) {
        try {
            if(!NetWorkUtils.isWifiConnected(JXApplication.getContext())){
                ToastUtil.toastShort("当前网络状态差，请检查当前网络后重试！");
                return;
            }
            Map<String, Object> params = new HashMap<>();
            params.put("userId", username);
            params.put("passWord", "111111");
            req(RequestFactory.getInstance().createApi(LoginApi.class).login(params), new HttpOnNextListener() {
                @Override
                public void onNext(String result, String mothead) {
                    SysInfo.userInfo = JSON.parseObject(result, UserInfo.class);
                    // 保存账号和密码
                    saveLastLoginAccount(username);
                    //保存到历史账号列表中
                    saveHistoryAccount(username);
                    getViewRef().PageTransformAfterLogin();
                }

                @Override
                public void onError(ApiException e) {
                    LogUtil.e("登录", e.getMessage());
                    ToastUtil.toastShort("登录失败！");
                }
            });
        } catch (Exception ex) {
            ToastUtil.toastShort("登录失败！");
            LogUtil.e("登录用户报错", ex.getMessage());
        }
    }

    /**
     * <li>说明：保存账号到本地
     * <li>创建人：zhubs
     * <li>创建日期：2018年5月9日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     *
     * @param username
     */
    private void saveLastLoginAccount(String username) {
        SharedPreferences.Editor accountEditor = accountSharePreferences.edit();
        accountEditor.putString(StringConstants.LAST_LOGIN_USER_ACCOUNT_KEY, username);
        accountEditor.commit();
    }

    /**
     * <li>说明：保存账号到本地
     * <li>创建人：何东
     * <li>创建日期：2016年7月6日
     * <li>修改人：
     * <li>修改日期：
     * <li>修改内容：
     *
     * @param username
     */
    private void saveHistoryAccount(String username) {
        String timeStr = DateUtil.date2String(new Date(), "yyyyMMddHHmm");
        Set<String> accounts = accountSharePreferences.getStringSet(StringConstants.HISTORY_USER_ACCOUNT_KEY, new HashSet<String>());
        Set<String> newAccounts = new HashSet<String>();
        if (accounts.size() > 0) {
            newAccounts.addAll(accounts);
            String exist = "";
            Iterator<String> it = newAccounts.iterator();
            while (it.hasNext()) {
                String acc = it.next();
                String[] accSplits = acc.split("_");
                // 判断是否存在历史，若存在将次数加1
                if (accSplits[1].equals(username)) {
                    exist = acc;
                    break;
                }
            }
            // 若已经存在，移除记录并加入次数自增后的记录
            if (!TextUtils.isEmpty(exist)) {
                newAccounts.remove(exist);
            }
        }
        newAccounts.add(timeStr + "_" + username);
        // 保存到本地
        SharedPreferences.Editor editor = accountSharePreferences.edit();
        editor.putStringSet(StringConstants.HISTORY_USER_ACCOUNT_KEY, newAccounts);
        editor.commit();
    }
}
