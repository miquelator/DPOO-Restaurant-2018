// this class belongs to network package
package Network;

// import our classes
import Controller.ActionListener.MainController;

// import java classes
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class handles the connection with the reception
 */
public class RecepcioSocketThread extends Thread {

    // instance attribute
    private DedicatedRecepcioThread dedicatedRecepcioThread;
    private MainController controller;

    // socket for the client
    private Socket sClient;


    /**
     * This method is generated by the thread start
     */
    @Override
    public void run() {
        try {

            // create our server socket
            ServerSocket serverSocket = new ServerSocket(33333);

            // wait for the connection from a user, and create a dedicated server each time it's connected
            while (true){
                sClient = serverSocket.accept();
                if (sClient.isConnected()) {
                    generaNouServidorDedicat(sClient);
                }
            }

        } catch (IOException e) { // manage exception
            // TODO: manage exception
            e.printStackTrace();
        }
    }

    /**
     * This method generates a dedicated server with a given client socket
     * @param sClient Socket where the client is casting
     */
    private void generaNouServidorDedicat(Socket sClient){

        dedicatedRecepcioThread = new DedicatedRecepcioThread(sClient, controller);
        // run the new thread
        dedicatedRecepcioThread.start();
    }

    /**
     * This method registers the controller and makes the union with it
     * @param controller MainController instance
     */
    public void registerController(MainController controller){
        this.controller = controller;
    }
}
