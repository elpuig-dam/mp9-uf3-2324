package mp9.uf3.tcp.jocObj;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class SrvMulticastTauler_Obj {
    MulticastSocket socket;
    InetAddress multicastIP;
    int port;
    boolean continueRunning = true;
    private Tauler tauler;

    public SrvMulticastTauler_Obj(String multicastIP, int port, Tauler tauler) throws IOException {
        this.multicastIP = InetAddress.getByName(multicastIP);
        this.port = port;
        this.tauler = tauler;
        socket = new MulticastSocket(port);
    }

    public void runServer() throws IOException {
        DatagramPacket packet;
        byte[] sendingData;
        ByteArrayInputStream is;
        ObjectOutputStream oos;

        while (continueRunning) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sendingData = getData();
            //System.out.println(tauler);
            packet = new DatagramPacket(sendingData,sendingData.length,multicastIP,port);
            socket.send(packet);
           if(tauler.getNumPlayers() > 0 && tauler.getNumPlayers() - tauler.acabats == 0) continueRunning = false;
        }
    }
    private byte[] getData() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(os);
            oos.writeObject(tauler);
            oos.flush();

        }catch (IOException e) {
            e.printStackTrace();
        }
        return os.toByteArray();
    }
}
