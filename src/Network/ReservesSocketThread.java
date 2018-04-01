package Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ReservesSocketThread extends Thread{

    private DedicatedReservesThread dedicatedReservesThread;
    private Socket sClient;

    @Override
    public void run() {
        try {
            //creem el nostre socket
            ServerSocket serverSocket = new ServerSocket(33334);
            while (true){
                //esperem a la conexio d'algun usuari dins d'un bucle infinit. A cada usuari li crearem un nou servidor dedicat
                sClient = serverSocket.accept();
                if (sClient.isConnected()) {
                    generaNouServidorDedicat(sClient);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generaNouServidorDedicat(Socket sClient){
        System.out.println("Generant nou socket per a la recepcio");
        dedicatedReservesThread = new DedicatedReservesThread(sClient);
        dedicatedReservesThread.start();
    }
}
