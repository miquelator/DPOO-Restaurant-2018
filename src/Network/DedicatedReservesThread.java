// package where it belongs
package Network;

// import our classes
import Controller.MainController;
//TODO: crec que no hauria de estar linkat amb el model
import Model.Carta;
import Model.CartaSelection;

// import java classes
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/***
 * This class manages the network specifies of the reservation
 */
public class DedicatedReservesThread extends Thread {

    // instance attributes
    private MainController mainController;

    // server attributes
    private Socket sClient;
    private DataOutputStream doStream;
    private DataInputStream diStream;
    private ObjectOutputStream ooStream;
    private ObjectInputStream oiStream;

    // utilities attributes
    private int idtaula;

    /***
     * Constructor of the class with parameters
     * @param sClient Socket with the client socket
     * @param mainController MainController instance variable
     */
    public DedicatedReservesThread(Socket sClient, MainController mainController) {
        this.sClient = sClient;
        this.mainController = mainController;
        idtaula = -1;
    }

    /***
     * Thos method is triggered when the server is started
     */
    @Override
    public void run() {

        try {

            // create the instances to read and send data
            doStream = new DataOutputStream(sClient.getOutputStream());
            diStream = new DataInputStream(sClient.getInputStream());
            ooStream = new ObjectOutputStream(sClient.getOutputStream());
            oiStream = new ObjectInputStream(sClient.getInputStream());

            // wait for connection of the client, then manage the petition
            while (true){
                String request = diStream.readUTF();
                readRequest(request);
            }
        } catch (IOException ignored) { // manage exceptions
            // TODO: manage exceptions
        }
    }

    /***
     * This function manages the read request
     * @param request String with the request
     * @throws IOException Exception that's throwed
     */
    private void readRequest(String request) throws IOException {

        // depending on the request make an action or other
        switch (request){

            case "AUTHENTICATE":

                // get user credentials
                String user = diStream.readUTF();
                String password = diStream.readUTF();

                // look if the credentials are correct
                idtaula = mainController.autenticar(user,password);

                // send result to the client
                if (idtaula == -1){
                    doStream.writeBoolean(false);
                }else{
                    doStream.writeBoolean(true);
                }
                break;

            case "SHOW_STATUS":
                System.out.println("SHOW_STATUS");
                ooStream.writeObject(mainController.getOrderStatus(idtaula));
                break;

            case "PAY":
                double totalPagar = mainController.pay(idtaula);
                doStream.writeDouble(totalPagar);
                break;

            case "SHOW_MENU":
                int seleccio = diStream.readInt();
                returnSelection(seleccio);
                break;

            case "DISCONNECT":
                mainController.disconnect(idtaula);

            case "ORDER":
                try {
                    ArrayList<CartaSelection> cartaSelection = (ArrayList<CartaSelection>) oiStream.readObject();
                    //classe primitiva de Boolean i no boolean??
                    Boolean enoughItems = mainController.checkQuantityOrder(cartaSelection);
                    if (enoughItems){
                        mainController.saveOrderUpdateStock(cartaSelection, idtaula);
                        doStream.writeBoolean(true);
                    }else{
                        doStream.writeBoolean(false);
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void returnSelection(int seleccio) throws IOException {
        ArrayList<Carta> menu = mainController.getMenu();
        ArrayList<Carta> peticio = new ArrayList<>();
        int size = menu.size();
        for (int i = 0; i < size; i++){
            if (menu.get(i).getTipus().equals(tipusDefinition(seleccio))){
                peticio.add(menu.get(i));
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
