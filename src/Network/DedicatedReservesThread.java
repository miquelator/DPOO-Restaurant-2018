package Network;

import Controller.MainController;
import Model.Carta;
import Model.DatabaseConector;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class DedicatedReservesThread extends Thread {
    private Socket sClient;
    private DataOutputStream doStream;
    private DataInputStream diStream;
    private ObjectOutputStream ooStream;
    private ObjectInputStream oiStream;
    private MainController mainController;

    public DedicatedReservesThread(Socket sClient, MainController mainController) {
        this.sClient = sClient;
        this.mainController = mainController;
    }

    @Override
    public void run() {
        try {
            //creem les instancies necesaries per rebre i enviar dades
            doStream = new DataOutputStream(sClient.getOutputStream());
            diStream = new DataInputStream(sClient.getInputStream());
            ooStream = new ObjectOutputStream(sClient.getOutputStream());
            oiStream = new ObjectInputStream(sClient.getInputStream());
            while (true){
                String request = diStream.readUTF();
                readRequest(request);
            }
        } catch (IOException ignored) {
        }
    }

    private void readRequest(String request) throws IOException {
        switch (request){
            case "AUTHENTICATE":
                System.out.println("AUTHENTICATE");
                break;

            case "SHOW_STATUS":
                System.out.println("SHOW_STATUS");
                break;

            case "PAY":
                System.out.println("PAY");
                break;

            case "SHOW_MENU":
                int seleccio = diStream.readInt();
                returnSelection(seleccio);

                break;
        }
    }

    private void returnSelection(int seleccio) throws IOException {
        ArrayList<Carta> menu = mainController.getMenu();
        //TODO: MODIFICAR BASE DE DADES DE LA CARTA PER AFEGIR TIPUS DE PLAT SI "PRIMER" = 1, "SEGON" = 2, "POSTRE" = 3 I "BEGUDES" = 4. AQUI RETORNAR A LES RESERVES EL TIPUS DE PLAT QUE ENS DEMANEN SEGONS EL PARAMETRE SELECCIO
        System.out.println(menu.size());
        for (int i = 0; i < menu.size(); i++){
            System.out.println(menu.get(i).toString());
        }
        ooStream.writeObject(menu);
    }
}
