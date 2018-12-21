package jx.yunda.com.terminal.modules.message.model;

public class MessageReturn {
    private String pageSign;
    private String msgInfoId;
    private String receiverName;

    public String getPageSign() {
        return pageSign;
    }

    public void setPageSign(String pageSign) {
        this.pageSign = pageSign;
    }

    public String getMsgInfoId() {
        return msgInfoId;
    }

    public void setMsgInfoId(String msgInfoId) {
        this.msgInfoId = msgInfoId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
}
