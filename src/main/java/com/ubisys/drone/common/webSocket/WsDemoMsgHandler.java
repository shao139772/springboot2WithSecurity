package com.ubisys.drone.common.webSocket;

import com.ubisys.drone.common.utils.WebSocketUserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.nio.ByteBuffer;

/**
 * @author tanyaowu
 *         2017年6月28日 下午5:32:38
 */
public class WsDemoMsgHandler implements IWsMsgHandler {
    private static Logger log = LoggerFactory.getLogger(WsDemoMsgHandler.class);

    /**
     * 握手时走这个方法，业务可以在这里获取cookie，request参数等
     */
    @Override
    public HttpResponse handshake(HttpRequest request, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        request.getClientIp();
        String userName = request.getParam("name");
        WebSocketUserUtils.putInUserMap(channelContext.getId(), userName);
        Tio.bindToken(channelContext, channelContext.getId());
        return httpResponse;
    }

    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {

    }

    /**
     * 字节消息（binaryType = arraybuffer）过来后会走这个方法
     */
    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        String ss = new String(bytes, "utf-8");
        log.info("收到byte消息:{},{}", bytes, ss);
        channelContext.getClientNode().getIp();

        //		byte[] bs1 = "收到byte消息".getBytes("utf-8");
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);

        return buffer;
    }

    /**
     * 当客户端发close flag时，会走这个方法
     */
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        WebSocketUserUtils.delUserMap(channelContext.getId());
        Tio.unbindToken(channelContext);
        Tio.remove(channelContext, "receive close flag");
        return null;
    }

    /**
     * 字符消息（binaryType = blob）过来后会走这个方法
     */
    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
        return "收到text消息:" + text;
    }
}
