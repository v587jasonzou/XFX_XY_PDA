package jx.yunda.com.terminal.message.netty.client;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jx.yunda.com.terminal.SysInfo;
import jx.yunda.com.terminal.entity.MessageSend;
import jx.yunda.com.terminal.message.netty.client.channel.NettyClientInitializer;
import jx.yunda.com.terminal.message.netty.client.listener.NettyListener;
import jx.yunda.com.terminal.utils.LogUtil;

/*
 *NettyClient对Netty进行封装，用于建立连接、断开连接、异常断开重连以及向服务器发送消息
 */
public class NettyClient {
    private static NettyClient nettyClient = new NettyClient();

    private EventLoopGroup group;

    private NettyListener listener;

    private Channel channel;

    private boolean isConnect = false;

    private int reconnectNum = Integer.MAX_VALUE;

    private long reconnectIntervalTime = 5000;

    public static NettyClient getInstance() {
        return nettyClient;
    }

    public void connect() {
        if (!isConnect) {
            try {
                InetSocketAddress socketAddress = new InetSocketAddress(SysInfo.msgSocketAdress, SysInfo.msgSocketPort);
                group = new NioEventLoopGroup();
                Bootstrap bootstrap = new Bootstrap().group(group)
                        .option(ChannelOption.SO_KEEPALIVE, true)
                        .channel(NioSocketChannel.class)
                        .handler(new NettyClientInitializer(listener));
                bootstrap.connect(socketAddress).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (channelFuture.isSuccess()) {
                            isConnect = true;
                            channel = channelFuture.channel();
                        } else {
                            isConnect = false;
                        }
                    }
                }).sync();

            } catch (Exception e) {
                LogUtil.e("NettyClient连接错误", e.toString());
                listener.onServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_ERROR);
                reconnect();
            }
        }
    }

    public void disconnect() {
        if (channel != null && channel.isActive()) {
            channel.close();
            group.shutdownGracefully();
            isConnect = false;
        }
    }

    public void reconnect() {
        if (reconnectNum > 0 && !isConnect) {
            reconnectNum--;
            try {
                Thread.sleep(reconnectIntervalTime);
            } catch (InterruptedException e) {
            }
            LogUtil.e("NettyClient ReConnect", "重新连接");
            disconnect();
            connect();
        } else {
            disconnect();
        }
    }

    public boolean sendMsgToServer(MessageSend messageSend, ChannelFutureListener listener) {
        boolean flag = channel != null && isConnect && messageSend != null && !TextUtils.isEmpty(messageSend.getPageSign());
        if (flag) {
            String sendMsg = JSON.toJSONString(messageSend);
            LogUtil.d("发送消息", sendMsg);
            TextWebSocketFrame rspFrame = new TextWebSocketFrame(sendMsg);
            channel.writeAndFlush(rspFrame).addListener(listener);
        }
        return flag;
    }

    public void setReconnectNum(int reconnectNum) {
        this.reconnectNum = reconnectNum;
    }

    public void setReconnectIntervalTime(long reconnectIntervalTime) {
        this.reconnectIntervalTime = reconnectIntervalTime;
    }

    public boolean getConnectStatus() {
        return isConnect;
    }

    public void setConnectStatus(boolean status) {
        this.isConnect = status;
    }

    public void setListener(NettyListener listener) {
        this.listener = listener;
    }


}
