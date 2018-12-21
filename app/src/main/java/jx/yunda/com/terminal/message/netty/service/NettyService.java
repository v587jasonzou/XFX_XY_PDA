package jx.yunda.com.terminal.message.netty.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ScheduledExecutorService;

import jx.yunda.com.terminal.common.StringConstants;
import jx.yunda.com.terminal.modules.main.model.MessageGroupReceive;
import jx.yunda.com.terminal.message.netty.client.NettyClient;
import jx.yunda.com.terminal.message.netty.broadcastreciever.MessageNewWorkReceiver;
import jx.yunda.com.terminal.message.netty.client.listener.NettyListener;
import jx.yunda.com.terminal.modules.main.model.MessageGroup;
import jx.yunda.com.terminal.modules.main.model.MessageReceive;
import jx.yunda.com.terminal.modules.message.model.MessageInfo;
import jx.yunda.com.terminal.modules.message.model.MessageInfoReceive;
import jx.yunda.com.terminal.utils.LogUtil;

/*
 *NettyService类实现了业务的处理逻辑。
 */
public class NettyService extends Service implements NettyListener {

    private MessageNewWorkReceiver receiver;
    private static String sessionId = null;

    private ScheduledExecutorService mScheduledExecutorService;

    private void shutdown() {
        if (mScheduledExecutorService != null) {
            mScheduledExecutorService.shutdown();
            mScheduledExecutorService = null;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("消息服务", "消息服务创建！");
        receiver = new MessageNewWorkReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
        // 自定义心跳，每隔20秒向服务器发送心跳包
        /*mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                byte[] requestBody = {(byte) 0xFE, (byte)0xED, (byte)0xFE, 5};
                NettyClient.getInstance().sendMsgToServer(requestBody, new ChannelFutureListener() {    //3
                    @Override
                    public void operationComplete(ChannelFuture future) {
                        if (future.isSuccess()) {                //4
                            LogUtil.d("sendMsgToServer","Write heartbeat successful");
                        } else {
                            LogUtil.d("sendMsgToServer","Write heartbeat error");
                        }
                    }
                });
            }
        }, 20, 20, TimeUnit.SECONDS);*/
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("消息服务", "消息服务开始！");
        NettyClient.getInstance().setListener(this);
        connect();
        return START_NOT_STICKY;
    }

    @Override
    public void onServiceStatusConnectChanged(int statusCode) {     //连接状态监听
        LogUtil.d("连接状态", statusCode + "");
        if (statusCode == NettyListener.STATUS_CONNECT_SUCCESS) {
            authenticData();
        } else {
            //LogUtil.d("连接状态", statusCode + "Netty TCP 连接错误");
        }
    }

    /**
     * 认证数据请求
     */
    private void authenticData() {
       /* AuthModel auth = new AuthModel();
        auth.setI(102);
        auth.setU("YAMSUCFS8G");
        auth.setN("G4-一单元");
        auth.setF("51");
        auth.setT((int)(System.currentTimeMillis() / 1000));
        byte[] content = RequestUtil.getEncryptBytes(auth);
        byte[] requestHeader = RequestUtil.getRequestHeader(content, 1, 1001);
        byte[] requestBody = RequestUtil.getRequestBody(requestHeader, content);
        NettyClient.getInstance().sendMsgToServer(requestBody, new ChannelFutureListener() {    //3
            @Override
            public void operationComplete(ChannelFuture future) {
                if (future.isSuccess()) {                //4
                    Timber.d("Write auth successful");
                } else {
                    Timber.d("Write auth error");
                    WriteLogUtil.writeLogByThread("tcp auth error");
                }
            }
        });*/
    }

    @Override
    public void onMessageResponse(String responseStr) {
        LogUtil.d("Netty Tcp接收的数据", responseStr);
        if (TextUtils.isEmpty(responseStr))
            return;
        try {
            if (TextUtils.isEmpty(responseStr))
                return;
            LogUtil.d("接收到的消息", responseStr);
            JSONObject ob = JSON.parseObject(responseStr);
            String pageSign = ob.getString("pageSign");
            Object msg = null;
            if (StringConstants.MESSAGE_0.equals(pageSign))
                msg = JSON.parseObject(responseStr, MessageReceive.class);
            else if (StringConstants.MESSAGE_1.equals(pageSign))
                msg = JSON.parseObject(responseStr, MessageGroupReceive.class);
            else if (StringConstants.MESSAGE_2.equals(pageSign))
                msg = JSON.parseObject(responseStr, MessageInfoReceive.class);
            if (msg == null)
                return;
            if (EventBus.getDefault() != null) {
                EventBus.getDefault().post(msg);
            }
        } catch (Exception ex) {
            LogUtil.e("消息接收", ex.toString());
        }

    }

    private void handle(int t, int i, int f) {
        // TODO 实现自己的业务逻辑
    }

    private void connect() {
        if (!NettyClient.getInstance().getConnectStatus()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NettyClient.getInstance().connect();//连接服务器
                }
            }).start();
        }
    }

    @Override
    public void onDestroy() {
        LogUtil.d("消息服务", "消息服务销毁！");
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        shutdown();
        NettyClient.getInstance().setReconnectNum(0);
        NettyClient.getInstance().disconnect();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
