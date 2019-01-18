package com.ubisys.drone.common.webSocket;

import java.io.IOException;

import org.tio.server.ServerGroupContext;
import org.tio.websocket.server.WsServerStarter;

/**
 * @author tanyaowu
 * 2017年6月28日 下午5:34:04
 */
public class WsDemoStarter {
	/**
	 * @param args
	 * @author tanyaowu
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		WsDemoStarter appStarter = new WsDemoStarter(9321, new WsDemoMsgHandler());
		appStarter.start();
	}

	private static ServerGroupContext serverGroupContext;
	private static WsServerStarter wsServerStarter;

	/**
	 *
	 * @author tanyaowu
	 */
	public WsDemoStarter(int port, WsDemoMsgHandler wsMsgHandler) throws IOException {
		wsServerStarter = new WsServerStarter(port, wsMsgHandler);
		serverGroupContext = wsServerStarter.getServerGroupContext();
	}

	/**
	 * @return the serverGroupContext
	 */
	public static ServerGroupContext getServerGroupContext() {
		return serverGroupContext;
	}

	public static WsServerStarter getWsServerStarter() {
		return wsServerStarter;
	}

	public void start() throws IOException {
		wsServerStarter.start();
	}
}
