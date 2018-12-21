package jx.yunda.com.terminal.message.netty.client.listener;

/*
 *NettyListener类用于监听连接的状态和消息响应。
 */
public interface NettyListener {
    public final static byte STATUS_CONNECT_SUCCESS = 1;

    public final static byte STATUS_CONNECT_CLOSED = 0;

    public final static byte STATUS_CONNECT_ERROR = -1;


    /**
     * 对消息的处理
     */
    void onMessageResponse(String responseStr);

    /**
     * 当服务状态发生变化时触发
     */
    public void onServiceStatusConnectChanged(int statusCode);
}
