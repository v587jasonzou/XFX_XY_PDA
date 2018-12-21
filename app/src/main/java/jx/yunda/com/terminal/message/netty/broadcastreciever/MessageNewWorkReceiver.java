package jx.yunda.com.terminal.message.netty.broadcastreciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import jx.yunda.com.terminal.message.netty.client.NettyClient;
import jx.yunda.com.terminal.utils.LogUtil;
import jx.yunda.com.terminal.utils.ToastUtil;

public class MessageNewWorkReceiver extends BroadcastReceiver {
    //接收广播消息，断线重连
    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI
                        || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    if (!NettyClient.getInstance().getConnectStatus()) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                NettyClient.getInstance().connect();//连接服务器
                            }
                        }).start();
                    }
                }
            }else{
                ToastUtil.toastShort("网络连接不可用，请检查网络连接！");
            }
        }catch (Exception ex){
            LogUtil.e("网络连接信息",ex.toString());
        }

    }
}
