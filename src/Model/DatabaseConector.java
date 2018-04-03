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

    public ArrayList<Taula> getTaula() {
        if (conexio()){
            ArrayList<Taula> taules = new ArrayList<>();
            try {
                Statement s = connection.createStatement();

                s.executeQuery("SELECT * FROM Taula");
                ResultSet rs = s.getResultSet();
                while (rs.next()) {
                    int idTaula = rs.getInt("id_taula");
                    int numSeients = rs.getInt("num_seients");
                    boolean ocupada = rs.getBoolean("ocupada");
                    taules.add(new Taula(idTaula,numSeients, ocupada));
                }

                connection.close();
                return taules;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<Reserva> getReserves() {
        if (conexio()){
            ArrayList<Reserva> reserves = new ArrayList<>();
            try {
                Statement s = connection.createStatement();

                s.executeQuery("SELECT * FROM Reserva");
                ResultSet rs = s.getResultSet();
                while (rs.next()) {
                    int idTaula = rs.getInt("id_taula");
                    String nomReserva = rs.getString("nom_reserva");
                    String password = rs.getString("password_");
                    int numComensals = rs.getInt("num_comensals");
                    Date data = rs.getDate("data_reserva");
                    reserves.add(new Reserva(idTaula, nomReserva, password, data, numComensals));
                }

                connection.close();
                return reserves;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean addDish(String nom, double preu, int quantitat) {
        if (conexio()){
            try {

                String query = "INSERT INTO Carta(nom_plat, preu, quantitat) VALUES (?,?,?)";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, nom);
                preparedStmt.setDouble(2, preu);
                preparedStmt.setInt(3, quantitat);

                preparedStmt.execute();

                connection.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean updateStock(String updatedDishName, int newStock) {
        if (conexio()){
            try {

                String query =  "UPDATE Carta SET quantitat = ? WHERE nom_plat = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, newStock);
                preparedStmt.setString(2, updatedDishName);
                preparedStmt.execute();

                connection.close();
                return true;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean deleteDish(String deletedDishName) {
        if (conexio()){
            try {
                String query =  "DELETE FROM Carta WHERE nom_plat = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, deletedDishName);
                preparedStmt.execute();

                connection.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
