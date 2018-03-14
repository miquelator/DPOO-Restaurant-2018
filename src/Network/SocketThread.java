package Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketThread extends Thread {
    private DedicatedRecepcioThread dedicatedRecepcioThread;
    private DedicatedReservesThread dedicatedReservesThread;
    private Socket sClient;

    @Override
    public void run() {
        try {
            //creem el nostre socket
            ServerSocket serverSocket = new ServerSocket(33333);
            while (true){
                //esperem a la conexio d'algun usuari dins d'un bucle infinit. A cada usuari li crearem un nou servidor dedicat
                sClient = serverSocket.accept();
                if (sClient.isConnected()) {
                    generaNouServidorDedicat(sClient, true);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generaNouServidorDedicat(Socket sClient, boolean reserves){
        if (reserves){
            System.out.println("Generant nou socket per al client");
            dedicatedReservesThread = new DedicatedReservesThread(sClient);
            dedicatedReservesThread.start();
        }else if (!reserves){
            System.out.println("Generant nou socket per al client");
            dedicatedRecepcioThread = new DedicatedRecepcioThread(sClient);
            dedicatedRecepcioThread.start();
        }

    }
}
