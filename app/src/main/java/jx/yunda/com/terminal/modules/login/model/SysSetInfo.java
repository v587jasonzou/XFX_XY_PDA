package jx.yunda.com.terminal.modules.login.model;

public class SysSetInfo {
    private String baseURL;
    private String msgWebURL;
    private String msgSocketAddress;
    private int msgSocketPort;

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getMsgWebURL() {
        return msgWebURL;
    }

    public void setMsgWebURL(String msgWebURL) {
        this.msgWebURL = msgWebURL;
    }

    public String getMsgSocketAddress() {
        return msgSocketAddress;
    }

    public void setMsgSocketAddress(String msgSocketAddress) {
        this.msgSocketAddress = msgSocketAddress;
    }

    public int getMsgSocketPort() {
        return msgSocketPort;
    }

    public void setMsgSocketPort(int msgSocketPort) {
        this.msgSocketPort = msgSocketPort;
    }
}
