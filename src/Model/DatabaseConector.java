// package where it bellongs
package Model;

// import java classes
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/***
 * This class has and manage the information with the database
 */
public class DatabaseConector {

    // atributes
    private Connection connection;
    private ConfigJson configJson;

    /***
     * Constructor of the class
     * @param configJson ConfigJson variable with the information of the JSON file
     */
    public DatabaseConector(ConfigJson configJson) {
        this.configJson = configJson;
    }

    /**
     * Connects to database
     * @return true if connected, false if not
     */
    private boolean conexio() {
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

    /**
     * Gets menu list from DDBB.
     * @return boolean whether the user exists.
     */
    public int autenticar(String user, String password) {

        int trobat = -1;
        if (conexio()){
            try {
                Statement s = connection.createStatement();
                s.executeQuery("SELECT * FROM Reserva");
                ResultSet rs = s.getResultSet();
                while (rs.next()) {
                    if ( rs.getString("nom_reserva").equals(user)&&rs.getString("password_").equals(password)){
                        trobat = rs.getInt("id_taula");
                        updateConectedReserva(true, trobat);

                    }
                }

                connection.close();
                return trobat;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return trobat;
    }


    /**
     * Gets menu list from DDBB.
     * @return ArrayList<Carta> containing all items from the menu.
     */
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
                    int tipus = rs.getInt("tipus_plat");
                    float preu = rs.getFloat("preu");
                    int quantitat = rs.getInt("quantitat");
                    int semanals = rs.getInt("semanals");
                    int totals = rs.getInt("totals");
                    carta.add(new Carta(idPlat, tipusDefinition(tipus), nomPlat, preu, quantitat, semanals, totals));
                }

                connection.close();
                return carta;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /***
     * Method that returns the name representation for a dish with a given value
     * @param tipus integer variable with the number of the dish
     * @return String varaible with the name representation of the dish
     */
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
        // TODO: fer-ho en el default del switch?
        return null;
    }

    /**
     * Gets Table's info from DDBB.
     * @return ArrayList<Taula> containing all items from Taula.
     */
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

    /**
     * Gets Reserve's info from DDBB.
     * @return ArrayList<Reserva> containing all items from Reserva.
     */
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
                    boolean conectat = rs.getBoolean("conectat");
                    reserves.add(new Reserva(idTaula, nomReserva, password, data, numComensals, conectat));
                }

                connection.close();
                return reserves;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    public boolean updateConectedReserva(boolean b, int id) {
        if (conexio()){
            try {

                String query = "UPDATE Reserva SET conectat = ? WHERE id_reserva = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);

                preparedStmt.setBoolean(1, b);
                preparedStmt.setInt(2, id);


                preparedStmt.execute();

                connection.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;

    }

    public boolean addDish(String nom, double preu, int quantitat) {
        if (conexio()){
            try {

                String query = "INSERT INTO Carta(nom_plat, preu, quantitat, semanals, totals) VALUES (?,?,?,?,?)";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setString(1, nom);
                preparedStmt.setDouble(2, preu);
                preparedStmt.setInt(3, quantitat);
                preparedStmt.setInt(4, 0);
                preparedStmt.setInt(5, 0);

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

    public boolean deleteComanda(int id_taula) {
        if (conexio()){
            try {
                String query =  "DELETE FROM Comanda WHERE id_taula = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, id_taula);
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

    public boolean addTable(int numSeients) {
        if (conexio()){
            try {
                String query = "INSERT INTO Taula(num_seients, ocupada) VALUES (?,?)";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, numSeients);
                preparedStmt.setBoolean(2, false);

                preparedStmt.execute();

                connection.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public ArrayList<Carta> getTopFiveTotals() {
        if (conexio()){
            ArrayList<Carta> carta = new ArrayList<>();
            try {
                Statement s = connection.createStatement();

                s.executeQuery("SELECT * FROM Carta ORDER BY totals DESC LIMIT 5");
                ResultSet rs = s.getResultSet();
                while (rs.next()) {
                    int idPlat = rs.getInt("id_plat");
                    String nomPlat = rs.getString("nom_plat");
                    int tipus = rs.getInt("tipus_plat");
                    float preu = rs.getFloat("preu");
                    int quantitat = rs.getInt("quantitat");
                    int semanals = rs.getInt("semanals");
                    int totals = rs.getInt("totals");
                    carta.add(new Carta(idPlat, tipusDefinition(tipus), nomPlat, preu, quantitat, semanals, totals));
                }

                connection.close();
                return carta;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<Carta> getTopFiveWeekly() {
        if (conexio()){
            ArrayList<Carta> carta = new ArrayList<>();
            try {
                Statement s = connection.createStatement();

                s.executeQuery("SELECT * FROM Carta ORDER BY semanals DESC LIMIT 5");
                ResultSet rs = s.getResultSet();
                while (rs.next()) {
                    int idPlat = rs.getInt("id_plat");
                    String nomPlat = rs.getString("nom_plat");
                    int tipus = rs.getInt("tipus_plat");
                    float preu = rs.getFloat("preu");
                    int quantitat = rs.getInt("quantitat");
                    int semanals = rs.getInt("semanals");
                    int totals = rs.getInt("totals");
                    carta.add(new Carta(idPlat, tipusDefinition(tipus), nomPlat, preu, quantitat, semanals, totals));
                }

                connection.close();
                return carta;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public boolean deleteTable(int tableToDelete) {
        if (conexio()){
            try {
                String query = "DELETE FROM Taula WHERE id_taula = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, tableToDelete);
                preparedStmt.execute();

                connection.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public boolean getTableReserve(int tableToDelete) {
        if (conexio()){
            try {

                String query =  "SELECT COUNT(id_taula) AS rowCount FROM Reserva WHERE id_taula = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, tableToDelete);
                preparedStmt.execute();

                ResultSet rs = preparedStmt.getResultSet();
                int rowCount = 0;
                while (rs.next()) {
                    rowCount = rs.getInt("rowCount");
                }
                if(rowCount == 0){
                    connection.close();
                    return true;
                }else{
                    connection.close();
                    return false;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean deleteReserveTable(int tableToDelete) {
        if (conexio()){
            try {
                String query =  "DELETE FROM Reserva WHERE id_taula = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, tableToDelete);
                preparedStmt.execute();

                connection.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public ArrayList<Integer> isTableOcuped(int comensals, Object date) {
        ArrayList<Integer> taulesLliures = new ArrayList<Integer>();
        if (conexio()){
            try {

                String query =  "SELECT id_taula FROM Taula WHERE (num_seients = ? OR num_seients = ? + 1 OR num_seients = ? + 2) ORDER BY num_seients";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, comensals);
                preparedStmt.setInt(2, comensals);
                preparedStmt.setInt(3, comensals);


                preparedStmt.execute();

                ResultSet rs = preparedStmt.getResultSet();
                int idTaulaaux = 0;

                while (rs.next()) {
                    boolean found = false;

                    idTaulaaux = rs.getInt("id_taula");
                    System.out.println(idTaulaaux);
                    query =  "SELECT data_reserva FROM Reserva WHERE id_taula = ?";
                    PreparedStatement preparedStmt2 = connection.prepareStatement(query);
                    preparedStmt2.setInt(1, idTaulaaux);
                    preparedStmt2.execute();

                    ResultSet rs2 = preparedStmt2.getResultSet();
                    while (rs2.next()) {

                        SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String dmy = mdyFormat.format(date);
                        System.out.println(dmy);
                        System.out.println(rs2.getDate("data_reserva").toString());
                        if (rs2.getDate("data_reserva").toString().equals(dmy)){
                            found = true;
                        }
                    }
                    if (!found){
                        taulesLliures.add(idTaulaaux);
                    }
                }
                System.out.println(taulesLliures);
                return taulesLliures;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return taulesLliures;
    }

    public String addReserve(String nomReserva, Object date, String s, Integer integer, int comensals) {
        if (conexio()){
            try {
                SimpleDateFormat mdyFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String dmy = mdyFormat.format(date);

                String format = "%d-%m-%Y %H:%i:%s";
                String query = "INSERT INTO Reserva(id_taula, nom_reserva, password_, num_comensals, data_reserva, conectat) VALUES (?,?,?,?,STR_TO_DATE(?, ?), ?)";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, integer);
                preparedStmt.setString(2, nomReserva);
                preparedStmt.setString(3, s);
                preparedStmt.setInt(4, comensals);
                preparedStmt.setString(5, dmy.toString());
                preparedStmt.setString(6, format);
                preparedStmt.setBoolean(7, false);

                preparedStmt.execute();

                connection.close();
                return s;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "Error al afegir la nova reserva!";

    }

    public void getOrderStatus(int idtaula) {
        if (conexio()){
            try {

                String query =  "SELECT * FROM Comanda WHERE id_taula = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, idtaula);
                preparedStmt.execute();

                ResultSet rs = preparedStmt.getResultSet();

                //TODO: RETORNAR L'ESTAT DE LES COMANDES DELS USUARIS.

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveOrder(ArrayList<CartaSelection> cartaSelection, int idtaula) {
        if (conexio()){
            try {
                for (CartaSelection c : cartaSelection) {

                    for (int i = 0; i < c.getUnitatsDemanades(); i++){

                        String query = "INSERT INTO Comanda (id_plat, id_taula, servit) VALUES (?, ?, ?);";
                        PreparedStatement preparedStmt = connection.prepareStatement(query);
                        preparedStmt.setInt(1, 1);
                        preparedStmt.setInt(2, idtaula);
                        preparedStmt.setBoolean(3, false);

                        preparedStmt.execute();
                    }
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public float getTotalPrice(int idtaula) {
        if (conexio()){
            try {

                String query =  "SELECT SUM(preu) AS total FROM Comanda, Carta WHERE Comanda.id_taula = ? AND Carta.id_plat = Comanda.id_plat";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, idtaula);
                preparedStmt.execute();

                ResultSet rs = preparedStmt.getResultSet();
                float total = 0;
                while (rs.next()) {
                    System.out.println(rs.getFloat("total"));
                    total = rs.getFloat("total");
                }

                connection.close();
                return total;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
}
