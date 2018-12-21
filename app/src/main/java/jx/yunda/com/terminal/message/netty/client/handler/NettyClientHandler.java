package jx.yunda.com.terminal.message.netty.client.handler;

import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import jx.yunda.com.terminal.message.netty.client.NettyClient;
import jx.yunda.com.terminal.message.netty.client.listener.NettyListener;

/*
 *该类用于获取状态发生改变和消息响应。
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Object> {

    /**
     * 全局WebSocket
     */
    private WebSocketServerHandshaker handshaker;
    private static final Logger logger = Logger.getLogger(WebSocketServerHandshaker.class.getName());
    private NettyListener listener;
    private static final String WEBSOCKET = "websocket";
    private static final String UPGRADE = "Upgrade";
    private static final String WEBSOCKET_URI_ROOT_PATTERN = "ws://%s:%d";

    public NettyClientHandler(NettyListener listener) {
        this.listener = listener;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettyClient.getInstance().setConnectStatus(true);
        listener.onServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_SUCCESS);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettyClient.getInstance().setConnectStatus(false);
        listener.onServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_CLOSED);
        NettyClient.getInstance().reconnect();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object frame) throws Exception {
        //处理HTTP握手请求
        if (frame instanceof FullHttpRequest) {
            handleHttpRequest(ctx, ((FullHttpRequest) frame));
        }
        //WebSocket请求
        else if (frame instanceof WebSocketFrame) {
            handlerWebSocketFrame(ctx, (WebSocketFrame) frame);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        //如果HTTP解码失败，则返回HTTP异常，并且判断消息头有没有包含Upgrade字段(协议升级)
        if (!req.decoderResult().isSuccess() || (!WEBSOCKET.equals(req.headers().get(UPGRADE)))) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        //构造握手响应返回
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(WEBSOCKET_URI_ROOT_PATTERN, null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            //版本不支持
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        //处理HTTP握手请求
        if (frame instanceof FullHttpRequest) {
            handleHttpRequest(ctx, ((FullHttpRequest) frame));
        }
        //WebSocket请求
        else if (frame instanceof WebSocketFrame) {
            handlerWebSocketFrame(ctx, (WebSocketFrame) frame);
        }

        //判断是否关闭链路指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        //判断是否Ping消息 -- ping/pong心跳包
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        //本例程仅支持文本消息，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException("当前只支持文本消息，不支持二进制消息！");
        }
        //客服端发送过来的消息
        if (frame instanceof TextWebSocketFrame) {
            String msg = ((TextWebSocketFrame) frame).text();
            //消息处理
            listener.onMessageResponse(msg);
        }
        return;
    }

    /**
     * 服务器响应客户端
     *
     * @param ctx
     * @param req
     * @param res
     */
    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        //返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        //如果不是KeepAlive那么就关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * 是否保持激活
     *
     * @param req
     * @return
     */
    private static boolean isKeepAlive(FullHttpRequest req) {
        return false;
    }
    /*@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE){
                ctx.close();
            }else if (event.state() == IdleState.WRITER_IDLE){
                try{
                    ctx.channel().writeAndFlush("Chilent-Ping\r\n");
                } catch (Exception e){
                    Timber.e(e.getMessage());
                }
            }
        }
        super.userEventTriggered(ctx, evt);
    }*/
}
