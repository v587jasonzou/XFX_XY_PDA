package jx.yunda.com.terminal.braodcastreciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

public class ScanReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String code = intent.getStringExtra("barcode_string");
        if(EventBus.getDefault()!=null){
            EventBus.getDefault().post(code);
        }
        Log.e("二维码：",code);
    }
}