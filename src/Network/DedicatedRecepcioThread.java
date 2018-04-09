package Network;

import Model.RandomString;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class DedicatedRecepcioThread extends Thread {
    private Socket sClient;
    private DataOutputStream doStream;
    private DataInputStream diStream;
    private ObjectInputStream oiStream;
    private RandomString randomString;

    public DedicatedRecepcioThread(Socket sClient) {
        this.sClient = sClient;
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
                System.out.println(nomReserva);
                System.out.println(comensals);
                System.out.println(date);
                doStream.writeBoolean(true);
                doStream.writeUTF(randomString.nextString());
                break;
        }
    }
}
