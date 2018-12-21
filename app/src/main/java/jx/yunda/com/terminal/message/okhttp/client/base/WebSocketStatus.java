package jx.yunda.com.terminal.message.okhttp.client.base;

public class WebSocketStatus {
    public final static int CONNECTED = 1;
    public final static int CONNECTING = 0;
    public final static int RECONNECT = 2;
    public final static int DISCONNECTED = -1;

    public class CODE {

        public final static int NORMAL_CLOSE = 1000;
        public final static int ABNORMAL_CLOSE = 1001;
    }

    public class TIP {

        public final static String NORMAL_CLOSE = "normal close";
        public final static String ABNORMAL_CLOSE = "abnormal close";
    }
}
