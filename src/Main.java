import Controller.MainController;
import Model.ConfigJson;
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
                        MainController mainController = new MainController(mainView);
                        mainView.registerListeners(mainController);
                        mainView.setVisible(true);//algo


                    }
                } catch (FileNotFoundException e) {
                    System.err.println("El fichero \"config.json\" no ha sido encontrado.");
                }
            }
        });
    }
}
