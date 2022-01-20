

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.Scanner;

/**
 * Diese Klasse Startet einen TCP Server auf Port 6969
 * Es kann sich ein Client verbinden und ab diesem Zeitpunkt ist die Verbindung aufgebaut.
 * Das Programm läuft multithreaded dh. senden und empfangen sind zwei Threads.
 *
 * @author Severin Goddon
 */
public class TCPServer {

    private Scanner sc = new Scanner(System.in);

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
        ServerSocket serverSocket = new ServerSocket(port);
        Socket client = serverSocket.accept();
        new Thread(() -> {
            try {
                listen(client);
            }catch (IOException e){
                System.out.println("ups");
            }
        }).start();
        new Thread(() -> {
            try {
                send(client);
            }catch (IOException e){
                System.out.println("ups");
            }
        }).start();
    }



    public void listen(Socket client) throws IOException {
        while (true) {
            String nachricht = getMessage(client);
            System.out.println(nachricht);
        }
    }

    public void send(Socket client) throws IOException {
        while (true) {
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
