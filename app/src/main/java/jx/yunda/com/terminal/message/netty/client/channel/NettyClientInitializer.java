package jx.yunda.com.terminal.message.netty.client.channel;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import jx.yunda.com.terminal.message.netty.client.handler.NettyClientHandler;
import jx.yunda.com.terminal.message.netty.client.listener.NettyListener;
import okhttp3.WebSocketListener;

/*
 *在Netty中，每个Channel被创建的时候都需要被关联一个对应的pipeline（通道），这种关联关系是永久的（整个程序运行的生命周期中）。
 *ChannelPipeline可以理解成一个消息（ 或消息事件，ChanelEvent）流转的通道，在这个通道中可以被附上许多用来处理消息的handler，当消息在这个通道中流转的时候，如果有与这个消息类型相对应的handler，就会触发这个handler去执行相应的动作。
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    private NettyListener listener;

    private int WRITE_WAIT_SECONDS = 10;

    private int READ_WAIT_SECONDS = 13;

    public NettyClientInitializer(NettyListener listener) {
        this.listener = listener;
    }

    //最大缓冲大小
    private static final int MAX_CONTENT_LENGTH = 64 * 1024;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(MAX_CONTENT_LENGTH));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new NettyClientHandler(listener));
    }
}
