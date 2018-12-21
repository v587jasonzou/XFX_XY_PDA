package jx.yunda.com.terminal.message.okhttp.client.base;

import okhttp3.WebSocket;
import okio.ByteString;

public interface IWebSocketManager {
    WebSocket getWebSocket();

    void startConnect();

    void stopConnect();

    boolean isWsConnected();

    int getCurrentStatus();

    void setCurrentStatus(int currentStatus);

    boolean sendMessage(String msg);

    boolean sendMessage(ByteString byteString);
}
