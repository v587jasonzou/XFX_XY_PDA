package jx.yunda.com.terminal.global.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import jx.yunda.com.terminal.global.listener.INetWorkChangeListener;
import jx.yunda.com.terminal.utils.NetWorkUtils;

public class NewWorkBroadcastReceiver extends BroadcastReceiver {
    INetWorkChangeListener netWorkChangeListener;

    public NewWorkBroadcastReceiver(INetWorkChangeListener netWorkChangeListener) {
        this.netWorkChangeListener = netWorkChangeListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        netWorkChangeListener.NetWorkChange(NetWorkUtils.isNetworkConnected(context));
    }
}
