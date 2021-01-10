import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

/**
 * Diese Klasse stellt einen UDP Server zur Verfügung. Es ist ein einfacher Echobasierter Server, der vom Client empfangene Pakete einfach wieder zurück sendet.
 * @author Severin goddon
 */
public class UDPServer extends Thread {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];

    public UDPServer() throws SocketException {
        socket = new DatagramSocket(4446); //den UDP Socket erstellen auf port 4446
    }

    public void run() {
        running = true;
        while (running) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            InetAddress IPaddress = packet.getAddress(); //ipadresse für das Zurücksenden ermitteln
            int port = packet.getPort(); //port für das Zurücksenden ermitteln
            packet = new DatagramPacket(buf, buf.length, IPaddress, port); //Paket erstellen
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println(received);
            //System.out.println(Arrays.toString(buf)); //<---- kann genutzt werden, um sich den übertragenen bytecode anzuschauen. Er enthält maximal 256 ASCII nummern, welche jeweils einen Buchstaben darstellen

            try {
                socket.send(packet); //sende das Paket zurück an den Client
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }
}

