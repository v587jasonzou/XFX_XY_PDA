package jx.yunda.com.terminal.modules.message.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <li>说明：消息Model
 * <li>创建人：zhubs
 * <li>创建日期：2018年7月20日
 * <li>修改人：
 * <li>修改日期：
 * <li>修改内容：
 */
public class MessageInfo {
    /* idx主键 */
    private String idx;

    /*群Id*/
    private String groupId;

    /*发送者Id*/
    private String senderId;

    /*发送者名称*/
    private String senderName;

    /*发送者所在班组Id*/
    private String workTeamId;

    /*发送者所在班组名称*/
    private String workTeamName;

    /*接收者Id 以逗号分隔*/
    private String receiversId;

    /*接收者名称 以逗号分隔*/
    private String receiversName;
    List<Receiver> receivers;

    /*消息类型*/
    private Integer msgType;

    /*消息内容*/
    private String msgContent;

    /*语音消息Id*/
    private String msgVoiceId;

    /*整备标识（Y：整备 N：非整备）*/
    private String isZb;

    /*检修标识（Y：检修 N：非检修）*/
    private String isJx;

    /*业务类型Id*/
    private String businessId;

    /*业务类型*/
    private String businessType;

    /*Web链接地址*/
    private String webUrl;

    /*Web打开方式（0：不打开页面；1：打开模块主页面；2：打开模块子页面）*/
    private String webOpenMode;

    /*链接参数*/
    private String linkParam;

    /*段平台WEB端标识（Y：是，N：否）*/
    private String isSectionWeb;

    /*段平台PDA端标识（Y：是，N：否）*/
    private String isSectionPda;

    /*段平台工位终端标识（Y：是，N：否）*/
    private String isSectionSeat;

    /*小辅修WEB端标识（Y：是，N：否）*/
    private String isXfxWeb;

    /*小辅修PDA端标识（Y：是，N：否）*/
    private String isXfxPda;

    /*小辅修工位终端标识（Y：是，N：否）*/
    private String isXfxSeat;

    /*整备WEB端标识（Y：是，N：否）*/
    private String isZbWeb;

    /*整备PDA端标识（Y：是，N：否）*/
    private String isZbPda;

    /*整备工位终端标识（Y：是，N：否）*/
    private String isZbSeat;

    /*发送标识（Y：发送成功，N：未发送）*/
    private String isSend;

    /*创建时间*/
    private String createTime;

    /*是否已读，已读的人员，已“,”隔开*/
    private String isReadedSign;

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getWorkTeamId() {
        return workTeamId;
    }

    public void setWorkTeamId(String workTeamId) {
        this.workTeamId = workTeamId;
    }

    public String getWorkTeamName() {
        return workTeamName;
    }

    public void setWorkTeamName(String workTeamName) {
        this.workTeamName = workTeamName;
    }

    public String getReceiversId() {
        return receiversId;
    }

    public void setReceiversId(String receiversId) {
        this.receiversId = receiversId;
    }

    public String getReceiversName() {
        return receiversName;
    }

    public void setReceiversName(String receiversName) {
        this.receiversName = receiversName;
    }

    public List<Receiver> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<Receiver> receivers) {
        this.receivers = receivers;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgVoiceId() {
        return msgVoiceId;
    }

    public void setMsgVoiceId(String msgVoiceId) {
        this.msgVoiceId = msgVoiceId;
    }

    public String getIsZb() {
        return isZb;
    }

    public void setIsZb(String isZb) {
        this.isZb = isZb;
    }

    public String getIsJx() {
        return isJx;
    }

    public void setIsJx(String isJx) {
        this.isJx = isJx;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getWebOpenMode() {
        return webOpenMode;
    }

    public void setWebOpenMode(String webOpenMode) {
        this.webOpenMode = webOpenMode;
    }

    public String getLinkParam() {
        return linkParam;
    }

    public void setLinkParam(String linkParam) {
        this.linkParam = linkParam;
    }

    public String getIsSectionWeb() {
        return isSectionWeb;
    }

    public void setIsSectionWeb(String isSectionWeb) {
        this.isSectionWeb = isSectionWeb;
    }

    public String getIsSectionPda() {
        return isSectionPda;
    }

    public void setIsSectionPda(String isSectionPda) {
        this.isSectionPda = isSectionPda;
    }

    public String getIsSectionSeat() {
        return isSectionSeat;
    }

    public void setIsSectionSeat(String isSectionSeat) {
        this.isSectionSeat = isSectionSeat;
    }

    public String getIsXfxWeb() {
        return isXfxWeb;
    }

    public void setIsXfxWeb(String isXfxWeb) {
        this.isXfxWeb = isXfxWeb;
    }

    public String getIsXfxPda() {
        return isXfxPda;
    }

    public void setIsXfxPda(String isXfxPda) {
        this.isXfxPda = isXfxPda;
    }

    public String getIsXfxSeat() {
        return isXfxSeat;
    }

    public void setIsXfxSeat(String isXfxSeat) {
        this.isXfxSeat = isXfxSeat;
    }

    public String getIsZbWeb() {
        return isZbWeb;
    }

    public void setIsZbWeb(String isZbWeb) {
        this.isZbWeb = isZbWeb;
    }

    public String getIsZbPda() {
        return isZbPda;
    }

    public void setIsZbPda(String isZbPda) {
        this.isZbPda = isZbPda;
    }

    public String getIsZbSeat() {
        return isZbSeat;
    }

    public void setIsZbSeat(String isZbSeat) {
        this.isZbSeat = isZbSeat;
    }

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsReadedSign() {
        return isReadedSign;
    }

    public void setIsReadedSign(String isReadedSign) {
        this.isReadedSign = isReadedSign;
    }
}
