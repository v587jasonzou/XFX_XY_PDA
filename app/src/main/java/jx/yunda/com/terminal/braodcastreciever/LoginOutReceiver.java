package jx.yunda.com.terminal.braodcastreciever;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import jx.yunda.com.terminal.modules.login.LoginActivity;
import jx.yunda.com.terminal.utils.ActivityUtil;

public class LoginOutReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        EventBus.getDefault().post("UnLogin");
    }
}
