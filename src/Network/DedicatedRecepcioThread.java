package Network;

import Controller.MainController;
import Controller.TablesController;
import Model.RandomString;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class DedicatedRecepcioThread extends Thread {
    private Socket sClient;
    private MainController mainController;
    private DataOutputStream doStream;
    private DataInputStream diStream;
    private ObjectInputStream oiStream;
    private RandomString randomString;

    public DedicatedRecepcioThread(Socket sClient, MainController mainController) {
        this.sClient = sClient;
        this.mainController = mainController;
    }

    @Override
    public void run() {
        try {
            //creem les instancies necesaries per rebre i enviar dades
            doStream = new DataOutputStream(sClient.getOutputStream());
            diStream = new DataInputStream(sClient.getInputStream());
            oiStream = new ObjectInputStream(sClient.getInputStream());
            randomString = new RandomString();
            System.out.println("esperant peticio del client");
            String request = diStream.readUTF();
            readRequest(request);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void readRequest(String request) throws IOException, ClassNotFoundException {
        switch (request){
            case "RESERVATION":
                String nomReserva = diStream.readUTF();
                int comensals = diStream.readInt();
                Object date = oiStream.readObject();

                String random = mainController.checkAndAddReserves(nomReserva, comensals, date);
                if (random.equals("No hi ha taules disponibles!") || random.equals("Error al afegir la nova reserva!")){
                    doStream.writeBoolean(false);
                    doStream.writeUTF(random);

                }else{
                    doStream.writeBoolean(true);
                    doStream.writeUTF(random);
                }



                break;
        }
    }
}
