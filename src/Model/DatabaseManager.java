package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private ConfigJson serverConfig;
    private Connection connection;

    //TODO: AQUESTA CLASSE ES NECESSARIA? ESTÀ IMPLEMENTAT EL MATEIX A DATABASE CONECTOR

    public DatabaseManager(ConfigJson serverConfig) {
        this.serverConfig = serverConfig;

        System.out.println("Accessing database...");
        System.out.println("User: " + serverConfig.getDb_user());

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + serverConfig.getDb_ip() + ":" + serverConfig.getDb_port() + "/" + serverConfig.getDb_name() + "?autoReconnect=true&useSSL=false",
                    serverConfig.getDb_user(), serverConfig.getDb_pass());

        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        catch (SQLException e) {
            //TODO: AFEGIR MISSATGE DE ERROR DE CONEXIO
            System.out.println("Implement error message");
        }
    }

}
