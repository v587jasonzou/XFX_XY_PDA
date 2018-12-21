package jx.yunda.com.terminal.message.okhttp.client.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.base.JXApplication;
import jx.yunda.com.terminal.message.okhttp.client.base.IWebSocketManager;
import jx.yunda.com.terminal.message.okhttp.client.base.WebSocketStatus;
import jx.yunda.com.terminal.message.okhttp.client.listener.WsOnOpenHanleListener;
import jx.yunda.com.terminal.message.okhttp.client.listener.MsgWebSocketListener;
import jx.yunda.com.terminal.utils.LogUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static jx.yunda.com.terminal.utils.NetWorkUtils.isNetworkConnected;

public class WebSocketManager implements IWebSocketManager {
    private final static int RECONNECT_INTERVAL = 3 * 1000;    //重连自增步长
    private final static long RECONNECT_MAX_TIME = 120 * 1000;   //最大重连间隔
    private Context mContext;
    private String wsUrl;
    private WebSocket mWebSocket;
    private OkHttpClient mOkHttpClient;
    private Request mRequest;
    private int mCurrentStatus = WebSocketStatus.DISCONNECTED;     //websocket连接状态
    private boolean isNeedReconnect;

    public boolean getNeedReconnect() {
        return isNeedReconnect;
    }

    public void setNeedReconnect(boolean needReconnect) {
        isNeedReconnect = needReconnect;
    }

    //是否需要断线自动重连
    private boolean isManualClose = false;         //是否为手动关闭websocket连接

    private Lock mLock;
    private Handler wsMainHandler = new Handler(Looper.getMainLooper());
    private int reconnectCount = 0;   //重连次数


    public int getReconnectCount() {
        return reconnectCount;
    }

    public void setReconnectCount(int reconnectCount) {
        this.reconnectCount = reconnectCount;
    }

    private Runnable reconnectRunnable = new Runnable() {
        @Override
        public void run() {
            initWebSocket();
        }
    };
    private WebSocketListener mWebSocketListener = new MsgWebSocketListener();

    private static WebSocketManager wsManager = new WebSocketManager();

    public static WebSocketManager getInstance() {
        return wsManager;
    }

    private WebSocketManager() {
        mContext = JXApplication.getContext();
        isNeedReconnect = true;
        this.mLock = new ReentrantLock();
    }

    private void initWebSocket() {
        try {
            if (mCurrentStatus == WebSocketStatus.CONNECTED || mCurrentStatus == WebSocketStatus.CONNECTING)
                return;
            wsUrl = "ws://" + SysInfo.msgSocketAdress + ":" + SysInfo.msgSocketPort + "/ws?empId=" + SysInfo.userInfo.emp.getEmpid();
            setCurrentStatus(WebSocketStatus.CONNECTING);
            if (mOkHttpClient == null) {
                mOkHttpClient = new OkHttpClient.Builder()
                        .pingInterval(15, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build();
            }
            if (mRequest == null) {
                mRequest = new Request.Builder()
                        .url(wsUrl)
                        .build();
            }
            mOkHttpClient.dispatcher().cancelAll();
            mLock.lockInterruptibly();
            try {
                mWebSocket = mOkHttpClient.newWebSocket(mRequest, mWebSocketListener);
            } finally {
                mLock.unlock();
            }
        } catch (InterruptedException e) {
            LogUtil.e("WebSocket连接错误", e.toString());
            setCurrentStatus(WebSocketStatus.DISCONNECTED);
            isNeedReconnect = true;
            tryReconnect();
        }
    }

    @Override
    public WebSocket getWebSocket() {
        return mWebSocket;
    }

    @Override
    public synchronized boolean isWsConnected() {
        return mCurrentStatus == WebSocketStatus.CONNECTED;
    }

    @Override
    public synchronized int getCurrentStatus() {
        return mCurrentStatus;
    }

    @Override
    public synchronized void setCurrentStatus(int currentStatus) {
        this.mCurrentStatus = currentStatus;
    }

    @Override
    public void startConnect() {
        if (mCurrentStatus == WebSocketStatus.CONNECTED || mCurrentStatus == WebSocketStatus.CONNECTING)
            return;
        LogUtil.d("WebSocket连接", "WebSocket连接开始");
        if (SysInfo.userInfo == null || SysInfo.userInfo.emp == null || TextUtils.isEmpty(SysInfo.userInfo.emp.getEmpid() + ""))
            return;
        isManualClose = false;
        mOkHttpClient = null;
        mRequest = null;
        wsMainHandler.post(reconnectRunnable);
    }

    public void setWsOnOpenHanleListener(WsOnOpenHanleListener wsOnOpenHanleListener) {
        this.wsOnOpenHanleListener = wsOnOpenHanleListener;
    }

    public WsOnOpenHanleListener getWsOnOpenHanleListener() {
        return wsOnOpenHanleListener;
    }

    private WsOnOpenHanleListener wsOnOpenHanleListener;

    public void startConnect(WsOnOpenHanleListener listener) {
        if (mCurrentStatus == WebSocketStatus.CONNECTED || mCurrentStatus == WebSocketStatus.CONNECTING)
            return;
        LogUtil.d("WebSocket连接+连接OnOpenListener", "WebSocket连接开始");
        wsOnOpenHanleListener = listener;
        if (SysInfo.userInfo == null || SysInfo.userInfo.emp == null || TextUtils.isEmpty(SysInfo.userInfo.emp.getEmpid() + ""))
            return;
        isManualClose = false;
        mOkHttpClient = null;
        mRequest = null;
        wsMainHandler.post(reconnectRunnable);
    }

    @Override
    public void stopConnect() {
        disconnect();
        LogUtil.d("WebSocket断开", "WebSocket断开成功");
    }

    public void tryReconnect() {
        if (isManualClose || !isNeedReconnect)
            return;
        if (mCurrentStatus == WebSocketStatus.RECONNECT || mCurrentStatus == WebSocketStatus.CONNECTED || mCurrentStatus == WebSocketStatus.CONNECTING)
            return;
        if (!isNetworkConnected(mContext)) {
            setCurrentStatus(WebSocketStatus.DISCONNECTED);
            return;
        }
        isNeedReconnect = false;
        setCurrentStatus(WebSocketStatus.RECONNECT);
        long delay = reconnectCount * RECONNECT_INTERVAL;
        wsMainHandler
                .postDelayed(reconnectRunnable, delay > RECONNECT_MAX_TIME ? RECONNECT_MAX_TIME : delay);
        LogUtil.d("WebSocket重连", "重连" + (++reconnectCount) + "次");
        if (reconnectCount == Integer.MAX_VALUE)
            cancelReconnect();
    }

    private void cancelReconnect() {
        wsMainHandler.removeCallbacks(reconnectRunnable);
        reconnectCount = 0;
        isNeedReconnect = false;
        isManualClose = true;
    }

    public void connected() {
        cancelReconnect();
    }

    private void disconnect() {
        cancelReconnect();
        if (mCurrentStatus == WebSocketStatus.DISCONNECTED) {
            return;
        }
        if (mOkHttpClient != null) {
            mOkHttpClient.dispatcher().cancelAll();
        }
        if (mWebSocket != null) {
            boolean isClosed = mWebSocket.close(WebSocketStatus.CODE.NORMAL_CLOSE, WebSocketStatus.TIP.NORMAL_CLOSE);
            //非正常关闭连接
            if (!isClosed) {
                if (mWebSocketListener != null) {
                    mWebSocketListener.onClosed(mWebSocket, WebSocketStatus.CODE.ABNORMAL_CLOSE, WebSocketStatus.TIP.ABNORMAL_CLOSE);
                }
            }
        }
        setCurrentStatus(WebSocketStatus.DISCONNECTED);
    }

//    private synchronized void buildConnect() {
//        if (!isNetworkConnected(mContext)) {
//            setCurrentStatus(WebSocketStatus.DISCONNECTED);
//            return;
//        }
//        switch (getCurrentStatus()) {
//            case WebSocketStatus.CONNECTED:
//                break;
//            case WebSocketStatus.CONNECTING:
//                break;
//            default:
//                initWebSocket();
//        }
//    }

    //发送消息
    @Override
    public boolean sendMessage(String msg) {
        LogUtil.d("WebSocket发送数据", msg);
        return send(msg);
    }

    @Override
    public boolean sendMessage(ByteString byteString) {
        return send(byteString);
    }

    private boolean send(Object msg) {
        boolean isSend = false;
        if (mWebSocket != null && mCurrentStatus == WebSocketStatus.CONNECTED) {
            if (msg instanceof String) {
                isSend = mWebSocket.send((String) msg);
            } else if (msg instanceof ByteString) {
                isSend = mWebSocket.send((ByteString) msg);
            }
            //发送消息失败，尝试重连
            if (!isSend) {
                tryReconnect();
            }
        } else {
            LogUtil.d("WebSocket发送消息", "WebSocket未连接");
        }
        return isSend;
    }

}
