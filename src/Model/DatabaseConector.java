package Model;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConector {
    private Connection connection;
    private ConfigJson configJson;

    public DatabaseConector(ConfigJson configJson) {
        this.configJson = configJson;
    }

    private boolean conexio() {
        /*
        System.out.println("Accessing database...");
        System.out.println("User: " + configJson.getDb_user());
*/
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + configJson.getDb_ip() + ":" + configJson.getDb_port() + "/" + configJson.getDb_name() + "?autoReconnect=true&useSSL=false",
                    configJson.getDb_user(), configJson.getDb_pass());
            return true;

        } catch (ClassNotFoundException | SQLException cnfe) {
            cnfe.printStackTrace();
            return false;
        }
    }

    public ArrayList<Carta> getCarta() {
        if (conexio()){
            ArrayList<Carta> carta = new ArrayList<>();
            try {
                Statement s = connection.createStatement();

                s.executeQuery("SELECT * FROM Carta");
                ResultSet rs = s.getResultSet();
                while (rs.next()) {
                    int idPlat = rs.getInt("id_plat");
                    String nomPlat = rs.getString("nom_plat");
                    float preu = rs.getFloat("preu");
                    int quantitat = rs.getInt("quantitat");
                    carta.add(new Carta(idPlat, nomPlat, preu, quantitat));
                }

                connection.close();
                return carta;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
