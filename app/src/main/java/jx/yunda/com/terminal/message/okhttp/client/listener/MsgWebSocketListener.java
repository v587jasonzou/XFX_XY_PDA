package jx.yunda.com.terminal.message.okhttp.client.listener;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import org.greenrobot.eventbus.EventBus;

import javax.annotation.Nullable;

import jx.yunda.com.terminal.base.JXApplication;
import jx.yunda.com.terminal.common.StringConstants;
import jx.yunda.com.terminal.message.okhttp.client.base.WebSocketStatus;
import jx.yunda.com.terminal.message.okhttp.client.manager.WebSocketManager;
import jx.yunda.com.terminal.modules.main.model.MessageGroupReceive;
import jx.yunda.com.terminal.modules.main.model.MessageGroup;
import jx.yunda.com.terminal.modules.main.model.MessageReceive;
import jx.yunda.com.terminal.modules.message.model.MessageInfo;
import jx.yunda.com.terminal.modules.message.model.MessageInfoReceive;
import jx.yunda.com.terminal.modules.message.model.MessageReturn;
import jx.yunda.com.terminal.utils.LogUtil;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MsgWebSocketListener extends WebSocketListener {

    private Handler wsMainListenerHandler = new Handler(Looper.getMainLooper());

    public MsgWebSocketListener() {
        super();
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        WebSocketManager.getInstance().setCurrentStatus(WebSocketStatus.CONNECTED);
        WebSocketManager.getInstance().setReconnectCount(0);
        LogUtil.d("WebSocket连接", "WebSocket连接成功");
        if (WebSocketManager.getInstance().getWsOnOpenHanleListener() != null) {
            wsMainListenerHandler.post(new Runnable() {
                @Override
                public void run() {
                    WebSocketManager.getInstance().getWsOnOpenHanleListener().wsStartConnnectedHanle();
                }
            });
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        //消息处理
        LogUtil.d("WebSocket接收的数据", text);
        if (TextUtils.isEmpty(text))
            return;
        try {
            if (TextUtils.isEmpty(text))
                return;
            JSONObject ob = JSON.parseObject(text);
            String pageSign = ob.getString("pageSign");
            Object msg = null;
            if (StringConstants.MESSAGE_0.equals(pageSign))
                msg = JSON.parseObject(text, MessageReceive.class);
            else if (StringConstants.MESSAGE_1.equals(pageSign))
                msg = JSON.parseObject(text, MessageGroupReceive.class);
            else if (StringConstants.MESSAGE_2.equals(pageSign))
                msg = JSON.parseObject(text, MessageInfoReceive.class);
            else if (StringConstants.MESSAGE_3.equals(pageSign))
                msg = JSON.parseObject(text, MessageInfoReceive.class);
            else if (StringConstants.MESSAGE_4.equals(pageSign))
                msg = JSON.parseObject(text, MessageReturn.class);
            if (msg == null)
                return;
            if (EventBus.getDefault() != null) {
                EventBus.getDefault().post(msg);
            }
        } catch (Exception ex) {
            LogUtil.e("消息接收", ex.toString());
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        super.onMessage(webSocket, bytes);
        //消息处理
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        super.onClosing(webSocket, code, reason);
        WebSocketManager.getInstance().setCurrentStatus(WebSocketStatus.DISCONNECTED);
        LogUtil.d("WebSocket连接", "WebSocket连接正在关闭");
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
        WebSocketManager.getInstance().setCurrentStatus(WebSocketStatus.DISCONNECTED);
        LogUtil.d("WebSocket连接", "WebSocket连接已关闭");
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
        LogUtil.d("WebSocket连接", "WebSocket连接失败，失败原因：" + t.getMessage());
        if (WebSocketManager.getInstance().getWsOnOpenHanleListener() != null) {
            wsMainListenerHandler.post(new Runnable() {
                @Override
                public void run() {
                    WebSocketManager.getInstance().getWsOnOpenHanleListener().wsStartConnnectedHanle();
                }
            });
        }
        WebSocketManager.getInstance().setCurrentStatus(WebSocketStatus.DISCONNECTED);
        WebSocketManager.getInstance().setNeedReconnect(true);
        WebSocketManager.getInstance().tryReconnect();
    }
}
