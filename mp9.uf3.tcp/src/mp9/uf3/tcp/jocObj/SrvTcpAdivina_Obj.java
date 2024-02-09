package mp9.uf3.tcp.jocObj;

import mp9.uf3.udp.unicast.joc.SecretNum;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SrvTcpAdivina_Obj {
    /* Servidor TCP que genera un número perquè ClientTcpAdivina_Obj.java jugui a encertar-lo
     * i on la comunicació dels diferents jugadors la gestionaran els Threads : ThreadServidorAdivina_Obj.java
     * */

    private int port;
    private SecretNum ns;
    private Tauler t;
    Logger logger;

    private SrvTcpAdivina_Obj(int port ) {
        this.port = port;
        ns = new SecretNum(100);
        t = new Tauler();
        logger = Logger.getLogger(SrvTcpAdivina_Obj.class.toString());
    }

    private void listen() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while(true) { //esperar connexió del client i llançar thread
                clientSocket = serverSocket.accept();
                //Llançar Thread per establir la comunicació
                //sumem 1 al numero de jugadors
                t.addNumPlayers();
                logger.info("Qtat de jugadors: " + t.getNumPlayers());
                ThreadSevidorAdivina_Obj FilServidor = new ThreadSevidorAdivina_Obj(clientSocket, ns, t);
                Thread client = new Thread(FilServidor);
                client.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(SrvTcpAdivina_Obj.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Tauler getTauler() {
        return t;
    }

    public static void main(String[] args) throws IOException {
        SrvTcpAdivina_Obj srv = new SrvTcpAdivina_Obj(5558);
        Thread thTcp = new Thread(() -> srv.listen());
        thTcp.start();
        //srv.listen();
        SrvMulticastTauler_Obj srvMulti = new SrvMulticastTauler_Obj("224.0.11.111",5559,srv.getTauler());
        Thread thMulticast = new Thread(() -> {
            try {
                srvMulti.runServer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thMulticast.start();
    }


}
