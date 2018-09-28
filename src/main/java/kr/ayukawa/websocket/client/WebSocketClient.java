package kr.ayukawa.websocket.client;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.MessageHandler;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@ClientEndpoint
public class WebSocketClient {
	private Session userSession = null;
	private MessageHandler messageHandler;
	private URI endpoint;
	
	public WebSocketClient(URI endpoint) {
		try {
			this.endpoint = endpoint;
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			container.connectToServer(this, this.endpoint);
		} catch(DeploymentException e) {
			throw new RuntimeException(e);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@OnOpen
	public void onOpen(Session userSession) {
		System.out.println(this.endpoint.toString() + " connected.");
		this.userSession = userSession;
	}
	
	@OnClose
	public void onClose(CloseReason closeReason) {
		System.out.println(this.endpoint.toString() + " disconnected because " + closeReason.getReasonPhrase() + ".");
		this.userSession = null;
	}
	
	@OnMessage
	public void onMessage(String message) {
		System.out.println("Message received: " + message);
	}
	
	public void sendMessage(String message) {
		this.userSession.getAsyncRemote().sendText(message);
		System.out.println("Message Sent: " + message);
	}
	
	public void closeSession() {
		try {
			CloseReason closeReason = new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "client connection termination");
			this.userSession.close(closeReason);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}
