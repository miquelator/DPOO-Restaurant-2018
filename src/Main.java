import Controller.MainController;
import Model.ConfigJson;
import Model.DatabaseConector;
import Network.RecepcioSocketThread;
import Network.ReservesSocketThread;
import View.MainView;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ConfigJson configJson = null;

                try {
                    File configFile = new File("resources/config.json");
                    configJson = new Gson().fromJson(new FileReader(configFile), ConfigJson.class);

                    if (configJson != null){
                        MainView mainView = new MainView();
                        ReservesSocketThread reservesThread = new ReservesSocketThread();
                        RecepcioSocketThread recepcioThread = new RecepcioSocketThread();
                        MainController mainController = new MainController(mainView, configJson, recepcioThread, reservesThread);
                        recepcioThread.registerController(mainController);
                        mainView.registerListeners(mainController);
                        mainView.setVisible(true);
                        reservesThread.start();
                        recepcioThread.start();
                    }
                } catch (FileNotFoundException e) {
                    System.err.println("El fichero \"config.json\" no ha sido encontrado.");
                }
            }
        });
    }
}
