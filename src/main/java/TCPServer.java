import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.Scanner;

/**
 * Diese Klasse Startet einen TCP Server auf Port 6969 (höhöh69)
 * Es kann sich ein Client verbinden und ab diesem Zeitpunkt ist die Verbindung aufgebaut. Die Kommunikation funktioniert im Ping-Pong Modus,
 * dh es kann immer der Server eine Nachricht schreiben und dann der Client immer abwechselnd. (Applikationsbedingt, um das zu beheben müsste man Multithreading machen)
 * Es ist ein Scanner integriert, dh. es kann in der Konsole geschrieben werden.
 * Der Client muss die Kommunikation beginnen!
 *
 * @author Severin Goddon
 */
public class TCPServer {

    public static void main(String[] args) {
        TCPServer server = new TCPServer();
        try {
            server.startConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startConnection() throws IOException {
        int port = 6969;
        Scanner sc = new Scanner(System.in);
        ServerSocket serverSocket = new ServerSocket(port);
        Socket client = serverSocket.accept();
        while (true) {
            String nachricht = getMessage(client);
            System.out.println(nachricht);
            String retour = sc.nextLine();
            sendMessage(client, retour);
        }
    }

    public String getMessage(Socket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        char[] buffer = new char[2000]; //maximale Länge der TCP Nachricht
        int anzahlZeichen = bufferedReader.read(buffer, 0, 2000); // blockiert bis eine Nachricht komplett empfangen ist.
        return new String(buffer, 0, anzahlZeichen);
    }

    public void sendMessage(Socket socket, String nachricht) throws IOException {
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        printWriter.print(nachricht);
        printWriter.flush();
    }
}
