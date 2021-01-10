import java.net.SocketException;

/**
 * Diese Klasse startet den UDP Server
 * @author Severin Goddon
 */
public class UDPServerTest {
    public static void main(String[] args) throws SocketException {
        UDPServer server = new UDPServer();
        server.start();
    }
}
