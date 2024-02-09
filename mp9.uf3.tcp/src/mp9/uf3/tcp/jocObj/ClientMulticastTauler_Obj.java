package mp9.uf3.tcp.jocObj;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class ClientMulticastTauler_Obj {
    private MulticastSocket socket;
    private InetAddress ip;
    private int port;
    private NetworkInterface netIf;
    private InetSocketAddress group;
    private boolean continueRunning = true;

    public ClientMulticastTauler_Obj(String ip, int port) throws IOException {
        this.ip = InetAddress.getByName(ip);
        this.port = port;
        socket = new MulticastSocket(port);
        netIf = socket.getNetworkInterface();
        group = new InetSocketAddress(ip,port);
    }

    public void runClient() throws IOException {
        DatagramPacket packet;
        byte[] receiveData = new byte[1024];

        socket.joinGroup(group,netIf);
        System.out.printf("Connectat a %s:%d%n",group.getAddress(),group.getPort());

        while(continueRunning) {
            packet = new DatagramPacket(receiveData,1024);
            socket.receive(packet);
            processData(packet.getData());
        }
    }

    private void processData(byte[] data) {
        ByteArrayInputStream is = new ByteArrayInputStream(data);
        Tauler tauler = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(is);
            tauler = (Tauler) ois.readObject();
            System.out.println(tauler);
            if(tauler.getNumPlayers() > 0 && tauler.getNumPlayers() - tauler.acabats == 0) continueRunning = false;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
