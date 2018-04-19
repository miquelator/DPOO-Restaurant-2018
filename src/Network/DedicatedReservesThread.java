package Network;

import Controller.MainController;
import Model.Carta;

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
    private int idtaula;

    public DedicatedReservesThread(Socket sClient, MainController mainController) {
        this.sClient = sClient;
        this.mainController = mainController;
        idtaula = -1;
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
                String user = diStream.readUTF();
                String password = diStream.readUTF();
                idtaula = mainController.autenticar(user,password);
                if (idtaula == -1){
                    doStream.writeBoolean(false);
                }else{
                    doStream.writeBoolean(true);
                };



                break;

            case "SHOW_STATUS":
                System.out.println("SHOW_STATUS");
                break;

            case "PAY":
                System.out.println("PAY");
                mainController.pay(idtaula);
                break;

            case "SHOW_MENU":
                int seleccio = diStream.readInt();
                returnSelection(seleccio);

                break;
        }
    }

    private void returnSelection(int seleccio) throws IOException {
        ArrayList<Carta> menu = mainController.getMenu();
        //TODO: RETORNAR A LES RESERVES EL TIPUS DE PLAT QUE ENS DEMANEN SEGONS EL PARAMETRE SELECCIO
        ArrayList<Carta> peticio = new ArrayList<>();
        int size = menu.size();
        for (int i = 0; i < size; i++){
            if (menu.get(i).getTipus().equals(tipusDefinition(seleccio))){
                peticio.add(menu.get(i));
                System.out.println(menu.get(i).toString());
            }
        }
        ooStream.writeObject(peticio);
    }

    private String tipusDefinition(int tipus) {
        switch (tipus){
            case 1:
                return "Primer";
            case 2:
                return "Segon";
            case 3:
                return "Postre";
            case 4:
                return "Begudes";
        }
        return null;
    }
}
