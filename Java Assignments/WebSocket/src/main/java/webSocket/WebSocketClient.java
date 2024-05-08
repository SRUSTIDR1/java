package webSocket;
import org.glassfish.tyrus.client.ClientManager;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
@ClientEndpoint
public class WebSocketClient {
    private static CountDownLatch latch;
    @OnOpen
    public void onOpen (Session session) {
        System.out.println("[CLIENT]: Connection established..... \n[CLIENT]: Session ID: " + session.getId() );
        try {
            session.getBasicRemote().sendText("Server is ready.....");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	@OnMessage
    public String onMessage (String message, Session session) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("[SERVER RESPONSE]: " + message);
        String clientInput = scanner.nextLine();
        return clientInput;
    }
	@OnClose
    public void onClose (Session session, CloseReason closeReason) {
        System.out.println("[CLIENT]: Session " + session.getId() + " close, because " + closeReason);
        latch.countDown();
    }
	@OnError
    public void onError (Session session, Throwable err) {
        System.out.println("[CLIENT]: Error!!!!!, Session ID: " + session.getId() + ", " + err.getMessage());
    }
	public static void main(String[] args) {
        latch = new CountDownLatch(1);
        ClientManager clientManager = ClientManager.createClient();
        URI uri = null;
        try {
            uri = new URI("ws://localhost:8080/java/demoApp");
            clientManager.connectToServer(WebSocketClient.class, uri);
            latch.await();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}