package com.ubisys.drone.common.utils;

import com.ubisys.drone.common.webSocket.WsDemoStarter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketUserUtils {

    private static Logger logger = LoggerFactory.getLogger(WebSocketUserUtils.class);
    public static Map<String, String> map = new ConcurrentHashMap<>();

    public static void putInUserMap(String key, String value) {
        String keys = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                keys = entry.getKey();
            }
        }
        delUserMap(keys);
        map.put(key, value);
    }

    public static void delUserMap(String key) {
        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            String keys = iter.next();
            if (key.equals(keys)) {
                iter.remove();
            }
        }
    }


    public static void sendDroneToCust(String message) {
        logger.info(" 向客户端推送消息 " + message);
        WsResponse wsResponse = WsResponse.fromText(message,
                "utf-8");
        Map<String, String> map = WebSocketUserUtils.map;// 查找全部连接用户
        for (Map.Entry<String, String> entry : map.entrySet()) {
            // 用户-->机构-->车场（二期工作）
            Tio.sendToToken(WsDemoStarter.getServerGroupContext(), entry.getKey(), wsResponse);// 将警告信息发送至页面
        }
    }

}
