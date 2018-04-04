package Network;

import Controller.MainController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RecepcioSocketThread extends Thread {
    private DedicatedRecepcioThread dedicatedRecepcioThread;
    private Socket sClient;
    private MainController controller;


    @Override
    public void run() {
        try {
            //creem el nostre socket
            ServerSocket serverSocket = new ServerSocket(33333);
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
        dedicatedRecepcioThread = new DedicatedRecepcioThread(sClient);
        dedicatedRecepcioThread.start();
    }

    public void setController (MainController c){
        controller = c;

    }
}
