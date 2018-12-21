package jx.yunda.com.terminal.message.okhttp.broadcastreciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.message.okhttp.client.manager.WebSocketManager;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.NetWorkUtils;
import jx.yunda.com.terminal.utils.ToastUtil;

public class MsgNewWorkReceiver extends BroadcastReceiver {

    //接收广播消息，断线重连
    @Override
    public void onReceive(Context context, Intent intent) {
        try {

            if (!NetWorkUtils.isNetworkConnected(context)) return;
            WebSocketManager.getInstance().startConnect();
        } catch (Exception ex) {
            LogUtil.e("网络连接信息", ex.toString());
        }

    }
}
