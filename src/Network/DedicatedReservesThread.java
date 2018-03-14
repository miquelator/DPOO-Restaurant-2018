package Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DedicatedReservesThread extends Thread {
    private Socket sClient;
    private DataOutputStream doStream;
    private DataInputStream diStream;

    public DedicatedReservesThread(Socket sClient) {
        this.sClient = sClient;
    }

    @Override
    public void run() {
        try {
            //creem les instancies necesaries per rebre i enviar dades
            doStream = new DataOutputStream(sClient.getOutputStream());
            diStream = new DataInputStream(sClient.getInputStream());
            String request = diStream.readUTF();
            readRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readRequest(String request) {
        switch (request){
            case "hola":
                try {
                    doStream.writeUTF("ACK");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
