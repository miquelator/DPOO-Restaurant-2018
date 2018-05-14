// package where it belongs
package Network;

// import our classes
import Controller.ActionListener.MainController;
import Model.RandomString;

// import java classes
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/***
 * This class manages the network service of the reception program
 */
public class DedicatedRecepcioThread extends Thread {

    // instance attributes
    private MainController mainController;

    // server attributes
    private Socket sClient;
    private DataOutputStream doStream;
    private DataInputStream diStream;
    private ObjectInputStream oiStream;

    // utilities attributes
    private RandomString randomString;

    /***
     * Constructor with parameters of the class
     * @param sClient Socket of the client
     * @param mainController MainController instance that manages the server
     */
    public DedicatedRecepcioThread(Socket sClient, MainController mainController) {
        this.sClient = sClient;
        this.mainController = mainController;
    }

    /***
     * This method is triggered when start the tread
     */
    @Override
    public void run() {
        try {

            // create the instance to receive and send the info
            doStream = new DataOutputStream(sClient.getOutputStream());
            diStream = new DataInputStream(sClient.getInputStream());
            oiStream = new ObjectInputStream(sClient.getInputStream());

            // create a random string
            randomString = new RandomString();

            // wait client petition
            System.out.println("esperant peticio del client");
            String request = diStream.readUTF();

            // read request
            readRequest(request);
        } catch (IOException | ClassNotFoundException e) { // manage exceptions
            // TODO: Manage exceptions
            e.printStackTrace();
        }
    }

    /***
     * This function manages the received request and sends the answer
     * @param request String with the request
     * @throws IOException Exception that's raised
     * @throws ClassNotFoundException Exception that's raised
     */
    private void readRequest(String request) throws IOException, ClassNotFoundException {

        // look if the request is reservation
        switch (request){
            case "RESERVATION":

                // read input data
                String nomReserva = diStream.readUTF();
                int comensals = diStream.readInt();
                Object date = oiStream.readObject();

                // make assure that the input info is correct
                Pattern pattern = Pattern.compile("['\"*$]");
                Matcher matcher = pattern.matcher(nomReserva);
                Date dataActual = new Date();
                SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dataActualAux = mdyFormat.format(dataActual);
                String dataClientlAux = mdyFormat.format(date);

                if (nomReserva.contains("\"") || nomReserva.equals(" ") || nomReserva.equals("") || matcher.find()){

                    // if the format is not correct send error message
                    doStream.writeBoolean(false);
                    doStream.writeUTF("Error de format del nom de la reserva!");
                }else{

                    // if the format is correct continue
                    try {

                        // make assure the data is not old
                        if(mdyFormat.parse(dataActualAux).after(mdyFormat.parse(dataClientlAux))){
                            doStream.writeBoolean(false);
                            doStream.writeUTF("La data introduïda és anterior a la actual!");
                        }else{

                            // look if there is a available table and send confirm or error message
                            String random = mainController.checkAndAddReserves(nomReserva, comensals, date);
                            if (random.equals("No hi ha taules disponibles!") || random.equals("Error al afegir la nova reserva!")){

                                // send error message
                                doStream.writeBoolean(false);
                                doStream.writeUTF(random);

                            }else{

                                // send confirmation message
                                doStream.writeBoolean(true);
                                doStream.writeUTF(random);
                            }
                        }

                    } catch (ParseException e) { // manage exceptions
                        // TODO:Manage exceptions
                        e.printStackTrace();
                    }

                }
                break;
        }
    }
}
