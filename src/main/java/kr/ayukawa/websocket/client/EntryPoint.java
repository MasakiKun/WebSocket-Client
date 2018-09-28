package kr.ayukawa.websocket.client;

import java.net.URI;
import java.util.Scanner;

public class EntryPoint {
	public static void main(String[] args) throws Exception {
		final WebSocketClient client = new WebSocketClient(new URI("wss://echo.websocket.org/"));
		
		Scanner scanner = new Scanner(System.in);
		String message = "";
		
		System.out.println("To quit, type ```/quit```");
		
		while(true) {
			message = scanner.nextLine();
			if(message.equals("/quit")) {
				client.closeSession();
				break;
			}
			
			client.sendMessage(message);
		}
	}
}
