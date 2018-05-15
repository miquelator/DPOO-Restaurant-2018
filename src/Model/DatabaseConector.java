// package where it bellongs
package Model;

// import java classes
import Exceptions.DataBaseException;
import Exceptions.TablesException;
import com.sun.org.apache.regexp.internal.RE;

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
    private String ERROR_BBDD = "Error en la base de dades";

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
    public int autenticar(String user, String password) throws DataBaseException {

        int trobat = -1;
        if (conexio()){
            try {
                Statement s = connection.createStatement();
                s.executeQuery("SELECT * FROM Reserva");
                ResultSet rs = s.getResultSet();
                while (rs.next()) {
                    if (rs.getString("nom_reserva").equals(user) && rs.getString("password_").equals(password)){
                        trobat = rs.getInt("id_taula");
                        updateConectedReserva(true, trobat);
                    }
                }

                if (trobat == -1){
                    throw new DataBaseException();
                }

                connection.close();

            } catch (SQLException e) {
                throw new DataBaseException(ERROR_BBDD);
            }
        }
        return trobat;
    }


    /**
     * Gets menu list from DDBB.
     * @return ArrayList<Carta> containing all items from the menu.
     */
    public ArrayList<Carta> getCarta() throws DataBaseException {
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
                throw new DataBaseException(ERROR_BBDD);
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
        return null;
    }

    /**
     * Gets Table's info from DDBB.
     * @return ArrayList<Taula> containing all items from Taula.
     */
    public ArrayList<Taula> getTaula() throws DataBaseException {

        // stables connection
        if (conexio()){

            // create recipient to fill
            ArrayList<Taula> taules = new ArrayList<>();
            try {
                // create the connection statment
                Statement s = connection.createStatement();
                s.executeQuery("SELECT * FROM Taula");
                ResultSet rs = s.getResultSet();

                // fill the table array with the proper values
                while (rs.next()) {
                    int idTaula = rs.getInt("id_taula");
                    int numSeients = rs.getInt("num_seients");
                    boolean ocupada = rs.getBoolean("ocupada");
                    taules.add(new Taula(idTaula,numSeients, ocupada));
                }

                // close msql connection and return tables
                connection.close();
                return taules;
            } catch (SQLException e) { // manage exception
                throw new DataBaseException(ERROR_BBDD);
            }
        }
        return null;
    }

    /**
     * Gets Reserve's info from DDBB.
     * @return ArrayList<Reserva> containing all items from Reserva.
     */
    public ArrayList<Reserva> getReserves() throws DataBaseException {

        // stables connection
        if (conexio()){

            // create the item to fill
            ArrayList<Reserva> reserves = new ArrayList<>();
            try {

                // create the statement
                Statement s = connection.createStatement();

                s.executeQuery("SELECT * FROM Reserva");
                ResultSet rs = s.getResultSet();

                // fill the reserve array with proper values form data base
                while (rs.next()) {
                    int idTaula = rs.getInt("id_taula");
                    String nomReserva = rs.getString("nom_reserva");
                    String password = rs.getString("password_");
                    int numComensals = rs.getInt("num_comensals");
                    Date data = rs.getDate("data_reserva");
                    boolean conectat = rs.getBoolean("conectat");
                    reserves.add(new Reserva(idTaula, nomReserva, password, data, numComensals, conectat));
                }

                // close mysql connection and return the array item
                connection.close();
                return reserves;
            } catch (SQLException e) { // manage exception
                throw new DataBaseException(ERROR_BBDD);
            }
        }
        return null;
    }



    /***
     * This method updates the state of the connected value on the data base, with given values
     * @param b boolean with the state of the connexion
     * @param id integer with the value of the table id
     * @return boolean with the information if the process is achieved or not
     */
    public boolean updateConectedReserva(boolean b, int id) throws DataBaseException {

        // generate connection
        if (conexio()){
            try {

                // generate the new query string
                String query = "UPDATE Reserva SET conectat = ? WHERE id_reserva = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);

                // fill the arguments
                preparedStmt.setBoolean(1, b);
                preparedStmt.setInt(2, id);

                // send the order
                preparedStmt.execute();

                // close connection after usage
                connection.close();
                return true;
            } catch (SQLException e) { // manage exception
                throw new DataBaseException(ERROR_BBDD);
        }
        }
        return false;

    }

    /***
     * This method adds a Dish information to the data base
     * @param nom String with the name of the dish
     * @param preu double with the value of the price
     * @param quantitat integer with the quantity of available dishes
     * @return boolean if it's achieved or not
     */
    public boolean addDish(String nom, double preu, int quantitat) throws DataBaseException {

        // stables connection, end if it's not achieved
        if (conexio()){
            try {

                // prepare the mysql order
                String query = "INSERT INTO Carta(nom_plat, preu, quantitat, semanals, totals) VALUES (?,?,?,?,?)";
                PreparedStatement preparedStmt = connection.prepareStatement(query);

                // fill the msql order
                preparedStmt.setString(1, nom);
                preparedStmt.setDouble(2, preu);
                preparedStmt.setInt(3, quantitat);
                preparedStmt.setInt(4, 0);
                preparedStmt.setInt(5, 0);

                // execute the order
                preparedStmt.execute();

                // close connection
                connection.close();
                return true;
            } catch (SQLException e) { // manges exception
                throw new DataBaseException(ERROR_BBDD);
            }
        }
        return false;

    }

    /***
     * This method updates the stock of a given dish
     * @param updatedDishName String with the name of the dish
     * @param newStock integer with the value of the stock
     * @return boolean if it's achieved or not
     */
    public boolean updateStock(String updatedDishName, int newStock) throws DataBaseException {

        // stables the connection with the data basel, end if not
        if (conexio()){
            try {

                // create the mysql order
                String query =  "UPDATE Carta SET quantitat = ? WHERE nom_plat = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);

                // fill the mysql order
                preparedStmt.setInt(1, newStock);
                preparedStmt.setString(2, updatedDishName);

                // execute the mysql order
                preparedStmt.execute();

                // close connection
                connection.close();
                return true;

            } catch (SQLException e) { // manages exception
                throw new DataBaseException(ERROR_BBDD);
            }
        }
        return false;
    }


    /***
     * This method deletes a command form the data base with a given name
     * @param id_taula integer with the table id to delete
     * @return boolean if success true, otherwise false
     */
    public boolean deleteComanda(int id_taula) throws DataBaseException {

        // stables connection with the data base
        if (conexio()){
            try {

                // create the mysql order
                String query =  "DELETE FROM Comanda WHERE id_taula = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);

                // fill the mysql order
                preparedStmt.setInt(1, id_taula);

                // execute the order
                preparedStmt.execute();

                // close connection
                connection.close();
                return true;
            } catch (SQLException e) { // manage exception
                throw new DataBaseException(ERROR_BBDD);
            }
        }
        return false;
    }

    /***
     * This method deletes a given dish from the data base
     * @param deletedDishName String with the dish name
     * @return boolean if success true, if not false
     */
    public boolean deleteDish(String deletedDishName) throws DataBaseException {

        // stables connection with the data base
        if (conexio()){
            try {

                // creates mysql order
                String query =  "DELETE FROM Carta WHERE nom_plat = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);

                // fill the mysql order
                preparedStmt.setString(1, deletedDishName);

                // execute order
                preparedStmt.execute();

                // close connection
                connection.close();
                return true;
            } catch (SQLException e) { // manages exception
                throw new DataBaseException(ERROR_BBDD);
            }
        }
        return false;
    }


    /***
     * This method adds a table with a given number of seats
     * @param numSeients integer with the number of seats
     * @return boolean if success true, if not false
     */
    public void addTable(int numSeients) throws DataBaseException {

        // stables connection
        if (conexio()){
            try {

                // create mysql order
                String query = "INSERT INTO Taula(num_seients, ocupada) VALUES (?,?)";
                PreparedStatement preparedStmt = connection.prepareStatement(query);

                // populate the statement
                preparedStmt.setInt(1, numSeients);
                preparedStmt.setBoolean(2, false);

                // execute the order
                preparedStmt.execute();

                // close connection
                connection.close();
            } catch (SQLException e) { // manage exceptions
                throw new DataBaseException("Error a l'hora d'afegir la taula!");
            }
        }
    }

    /***
     * This method look for the top five dishes
     * @return ArrayList of Carta with the top 5 global
     */
    public ArrayList<Carta> getTopFiveTotals() throws DataBaseException {

        // stables connection
        if (conexio()){

            // create recipient to fill
            ArrayList<Carta> carta = new ArrayList<>();
            try {

                // create connection statement
                Statement s = connection.createStatement();
                s.executeQuery("SELECT * FROM Carta ORDER BY totals DESC LIMIT 5");
                ResultSet rs = s.getResultSet();

                // fill the recipient with database data
                while (rs.next()) {
                    carta.add(fillCartaData(rs));
                }

                // close connection and return the data
                connection.close();
                return carta;
            } catch (SQLException e) { // manage exception
                throw new DataBaseException(ERROR_BBDD);
            }
        }
        return null;
    }

    /***
     * This method look for the top five dishes
     * @return ArrayList of Carta with the top 5 weekly
     */
    public ArrayList<Carta> getTopFiveWeekly() throws DataBaseException {

        // stables connection
        if (conexio()){

            // create recipient to fill
            ArrayList<Carta> carta = new ArrayList<>();
            try {
                // create query statement
                Statement s = connection.createStatement();

                s.executeQuery("SELECT * FROM Carta ORDER BY semanals DESC LIMIT 5");
                ResultSet rs = s.getResultSet();

                // fill the recipient
                while (rs.next()) {
                    carta.add(fillCartaData(rs));
                }

                // close msql connection and send the result
                connection.close();
                return carta;
            } catch (SQLException e) { // manage exception
                throw new DataBaseException(ERROR_BBDD);
            }
        }
        return null;
    }

    /***
     * This method fills a Carta item with a given result set variable
     * @param rs ResultSet variable with the information to fill
     * @return Carta object with the data filled
     * @throws SQLException SQL Exception is throw if something goes wrong
     */
    private Carta fillCartaData(ResultSet rs) throws SQLException {

        // read items
        int idPlat = rs.getInt("id_plat");
        String nomPlat = rs.getString("nom_plat");
        int tipus = rs.getInt("tipus_plat");
        float preu = rs.getFloat("preu");
        int quantitat = rs.getInt("quantitat");
        int semanals = rs.getInt("semanals");
        int totals = rs.getInt("totals");

        // fill and return item
        return new Carta(idPlat, tipusDefinition(tipus), nomPlat, preu, quantitat, semanals, totals);
    }

    /***
     * This method deletes a table with a given number
     * @param tableToDelete integer with the table to delete
     * @return boolean if success true, if not false
     */
    public void deleteTable(int tableToDelete) throws DataBaseException {

        // stables connection
        if (conexio()){
            try {

                // create query string
                String query = "DELETE FROM Taula WHERE id_taula = ?";

                // complete the query statment
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, tableToDelete);

                // execute the statement
                preparedStmt.execute();

                // close connection and return result
                connection.close();

            } catch (SQLException e) { // manage exception
                throw new DataBaseException("Error al esborrar la taula!");
            }
        }
    }



    public void getTableReserve(int tableToDelete) throws DataBaseException, TablesException {
        // stables connection
        if (conexio()){
            try {
                // create query string
                String query =  "SELECT COUNT(id_taula) AS rowCount FROM Reserva WHERE id_taula = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                // fill query string
                preparedStmt.setInt(1, tableToDelete);
                // execute the command
                preparedStmt.execute();
                ResultSet rs = preparedStmt.getResultSet();
                int rowCount = 0;
                while (rs.next()) {
                    rowCount = rs.getInt("rowCount");
                }
                if(rowCount == 0){
                    connection.close();
                }else{
                    connection.close();
                    throw new TablesException();
                }
            } catch (SQLException e) { // manage exception
                throw new DataBaseException(ERROR_BBDD);
            }
        }

    }

    /***
     * This method delates a reserve table form a given number
     * @param tableToDelete number of the table to delete
     * @return boolean if success true, if not false
     */
    public void deleteReserveTable(int tableToDelete) throws DataBaseException {

        // stables connection
        if (conexio()){
            try {

                // create sql command to delete table
                String query =  "DELETE FROM Reserva WHERE id_taula = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);

                // fill the string command
                preparedStmt.setInt(1, tableToDelete);

                // execute the command
                preparedStmt.execute();

                // close connection and return the result
                connection.close();
            } catch (SQLException e) { // manage exception
                throw new DataBaseException("Error al esborrar la taula!");
            }
        }
    }

    /***
     * Method that looks if there is a available table for a given number of people
     * and a given date. And returns an array list of integer with the id of the available tables.
     * @param comensals integer with the number of people
     * @param date Date with a reservation date
     * @return ArrayList of integers with the id of the available tables
     */
    public ArrayList<Integer> isTableOcuped(int comensals, Object date) throws DataBaseException {

        // create recipient to fill
        ArrayList<Integer> taulesLliures = new ArrayList<Integer>();

        // stables connection
        if (conexio()){
            try {

                // set the query command
                String query =  "SELECT id_taula FROM Taula WHERE (num_seients = ? OR num_seients = ? + 1 OR num_seients = ? + 2) ORDER BY num_seients";
                PreparedStatement preparedStmt = connection.prepareStatement(query);

                // fill the statement
                preparedStmt.setInt(1, comensals);
                preparedStmt.setInt(2, comensals);
                preparedStmt.setInt(3, comensals);

                // execute the statement
                preparedStmt.execute();

                // get the result
                ResultSet rs = preparedStmt.getResultSet();
                int idTaulaaux = 0;

                // look on all tables
                while (rs.next()) {
                    boolean found = false;

                    idTaulaaux = rs.getInt("id_taula");
                    System.out.println(idTaulaaux);

                    // look if the table is occupied on the date
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

                    // if it's not occupied append it to the list
                    if (!found){
                        taulesLliures.add(idTaulaaux);
                    }
                }
                System.out.println(taulesLliures);

                // return the list of available tables
                return taulesLliures;

            } catch (SQLException e) { // manage exception
                throw new DataBaseException(ERROR_BBDD);
            }
        }
        return taulesLliures;
    }

    /***
     * This method adds a reserve to the data base with a given values
     * @param nomReserva String with the name of the reservation
     * @param date Date where the reservation will be held
     * @param s String with the password
     * @param integer integer with the id of the table
     * @param comensals integer with the number of people
     * @return String with the password
     */
    public String addReserve(String nomReserva, Object date, String s, Integer integer, int comensals) throws DataBaseException {

        // stables connection
        if (conexio()){
            try {

                // create and fill the mysql statement to add a reservation item to the data base
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

                // execute the command
                preparedStmt.execute();

                // close connection and return the password
                connection.close();
                return s;
            } catch (SQLException e) { // manage exception
                throw new DataBaseException(ERROR_BBDD);
            }
        }
        return "Error al afegir la nova reserva!";

    }

    /***
     * This method looks if there is available stock from a dish
     * @param cartaSelection CartaSelection with the info of the selected dish
     * @return boolean, true if there is more quantity than the demand, false if not
     */
    public boolean checkQuantityPlat(CartaSelection cartaSelection) throws DataBaseException {

        // stables connection
        if (conexio()) {
            try {

                // create the query command
                String query = "SELECT * FROM Carta WHERE nom_plat = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);

                // fill the query command
                preparedStmt.setString(1, cartaSelection.getNomPlat());

                // execute the command
                preparedStmt.execute();

                // get the result of the query
                ResultSet rs = preparedStmt.getResultSet();


                // look if there is available stock
                int quantitat = 0;
                while (rs.next()) {
                    quantitat = rs.getInt("quantitat");
                }

                if (quantitat < cartaSelection.getUnitatsDemanades()) {
                    return false;
                } else {
                    return true;
                }

            } catch (SQLException e) { // manage exception
                throw new DataBaseException(ERROR_BBDD);
            }
        }
        return false;

    }


    public ArrayList<CartaStatus> getOrderStatus(int idtaula) throws DataBaseException {
        if (conexio()){

            try{
                String query =  "SELECT * FROM Comanda WHERE id_taula = ?";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, idtaula);
                preparedStmt.execute();

                ResultSet rs = preparedStmt.getResultSet();

                ArrayList<CartaStatus > resultat = new ArrayList<CartaStatus>();
                while (rs.next()) {
                  CartaStatus c = new CartaStatus();
                        c.setIdPlat(rs.getInt("id_plat"));
                        c.setServit(rs.getBoolean("servit"));
                        resultat.add(c);
                }

                for (CartaStatus cartaStatus : resultat){

                    query =  "SELECT nom_plat FROM Carta WHERE id_plat = ?";
                    preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setInt(1, cartaStatus.getIdPlat());
                    preparedStmt.execute();
                    rs = preparedStmt.getResultSet();

                    while (rs.next()){
                        cartaStatus.setNomPlat(rs.getString("nom_plat"));
                    }

                }

                // TODO: tancar connexió?
                return resultat;

            } catch (SQLException e) {
                throw new DataBaseException(ERROR_BBDD);
            }
        }
        return null;
    }

    /***
     * This method disconnects from the database a given table
     * @param id_taula integer with the id of the table
     */
    public void disconnect(int id_taula) throws DataBaseException {

        // stables connection
        if (conexio()){
            try {

                // create, set and execute the set data command
                String query = "DELETE FROM Reserva WHERE id_taula = ? AND conectat = true;";
                PreparedStatement preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, id_taula);
                preparedStmt.execute();

                // close connection
                connection.close();
            } catch (SQLException e) { // manage exceptions
                throw new DataBaseException("Error al desconectar la taula");
            }

        }

    }

    /***
     * This method saves a order of a given table, it's command and updates available units
     * @param cartaSelection ArrayList of CartaSelection with the order of the table
     * @param idtaula integer with the id of the table
     */
    public void saveOrderUpdateStock(ArrayList<CartaSelection> cartaSelection, int idtaula) throws DataBaseException {
        // stables connection
        if (conexio()){
            try {
                //TODO: ACTUALITZAR SEMANALS I TOTALS - PANDO
                // save into the database all the order information
                for (CartaSelection c : cartaSelection) {

                    String query = "SELECT id_reserva FROM Reserva WHERE id_taula = ?;";
                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setInt(1, idtaula);
                    preparedStmt.execute();
                    ResultSet rs = preparedStmt.getResultSet();
                    int idreserva = -1;
                    while (rs.next()){
                        idreserva = rs.getInt("id_reserva");
                    }


                    // create, set and execute the query command
                    query = "SELECT id_plat,quantitat FROM Carta WHERE nom_plat = ?";
                    preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setString(1, c.getNomPlat());
                    preparedStmt.execute();

                    // get the result form database
                    rs = preparedStmt.getResultSet();

                    // look if there is enough quantity of stock
                    int id = 0;
                    int quantitat = 0;
                    while (rs.next()) {
                        id = rs.getInt("id_plat");
                        quantitat = rs.getInt("quantitat");
                    }



                    // add all the ordered units of a dish
                    for (int i = 0; i < c.getUnitatsDemanades(); i++){

                        // create the set statement, fill it and execute it
                        query = "INSERT INTO Comanda (id_plat, id_taula, id_reserva, servit) VALUES (?, ?, ?, ?);";
                        preparedStmt = connection.prepareStatement(query);
                        preparedStmt.setInt(1, id);
                        preparedStmt.setInt(2, idtaula);
                        preparedStmt.setInt(3, idreserva);
                        preparedStmt.setBoolean(4, false);

                        preparedStmt.execute();
                    }


                    // update the quantity weekly and total
                    query = "UPDATE Carta SET totals = totals + ?, semanals = semanals + ? WHERE id_plat=?";
                    //quantitat = c.getUnitatsDemanades();
                    preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setInt(1, c.getUnitatsDemanades());
                    preparedStmt.setInt(2, c.getUnitatsDemanades());

                    preparedStmt.setInt(3, id);
                    preparedStmt.execute();


                    // update the quantity of available dish stock
                    query = "UPDATE Carta SET quantitat=? WHERE id_plat=?";
                    quantitat = quantitat - c.getUnitatsDemanades();
                    preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setInt(1, quantitat);
                    preparedStmt.setInt(2, id);
                    preparedStmt.execute();

                }

                // close connection
                connection.close();
            } catch (SQLException e) { // manage exception
                throw new DataBaseException(ERROR_BBDD);
            }
        }
    }

    /***
     * This method gets the total price to pay with a given table id
     * @param idtaula integer with the id of the table
     * @return float with the amount of cost of a table (-1 if error)
     */
    public float getTotalPrice(int idtaula) throws DataBaseException {

        // stables connection
        if (conexio()){
            try {

                // set the query statment
                String query =  "SELECT SUM(preu) AS total FROM Comanda, Carta WHERE Comanda.id_taula = ? AND Carta.id_plat = Comanda.id_plat";
                PreparedStatement preparedStmt = connection.prepareStatement(query);

                // fill the query statement
                preparedStmt.setInt(1, idtaula);

                // execute the statement
                preparedStmt.execute();

                // get the result and place it to total
                ResultSet rs = preparedStmt.getResultSet();
                // crete info recipient
                float total = 0;
                while (rs.next()) {
                    total = rs.getFloat("total");
                }

                // close connection and return result
                connection.close();
                return total;
            } catch (SQLException e) { // manage exceptions
                throw new DataBaseException(ERROR_BBDD);
            }
        }
        return -1;
    }

    /**
     * Retrieves reservation's id from DDBB
     * @return ArrayList containing all distinct id
     */
    public ArrayList<Integer> getReservation() throws DataBaseException {
        if (conexio()){
            String query =  "SELECT DISTINCT (id_reserva) FROM Comanda;";
            PreparedStatement preparedStmt = null;
            ArrayList<Integer> comanda = new ArrayList<>();
            try {
                preparedStmt = connection.prepareStatement(query);
                preparedStmt.execute();
                ResultSet rs = preparedStmt.getResultSet();
                while (rs.next()){
                    int idReserva = rs.getInt("id_reserva");
                    if (comanda.isEmpty()){
                        comanda.add(idReserva);
                    }else {
                        int size = comanda.size();
                        boolean flag = false;
                        for (int i = 0; i < size; i++){
                            if (comanda.get(i) == idReserva){
                                flag = true;
                                break;
                            }
                        }

                        if (!flag){
                            comanda.add(idReserva);
                        }
                    }
                }

                //TODO: tancar connexió?
            } catch (SQLException e) {
                throw new DataBaseException(ERROR_BBDD);
            }
            return comanda;
        }
        return null;
    }

    /**
     * Retrieves detailed info of the orders from a reservation
     * @param idReserva id that specifies which reservation
     * @return ArrayList containing all necessary fields data from a reservation
     */
    public ArrayList<Order> getOrderInfo(int idReserva) throws DataBaseException {
        if (conexio()){
            ArrayList<Order> orders = new ArrayList<>();
            String query = "SELECT id_plat, id_taula, hora, servit, id_comanda FROM Comanda WHERE id_reserva = ?";
            PreparedStatement preparedStmt = null;
            try {
                preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, idReserva);
                preparedStmt.execute();
                ResultSet rs = preparedStmt.getResultSet();
                while (rs.next()){
                    query = "SELECT nom_plat FROM Carta WHERE id_plat = ?";
                    preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setInt(1, rs.getInt("id_plat"));
                    preparedStmt.execute();
                    ResultSet rs2 = preparedStmt.getResultSet();
                    rs2.next();
                    orders.add(new Order(rs.getInt("id_plat"), rs2.getString("nom_plat"), rs.getInt("id_taula"), new java.sql.Date(rs.getDate("hora").getTime()), rs.getBoolean("servit"), rs.getInt("id_comanda")));
                }


                //TODO: tancar la connexió?
            }catch (SQLException e){
                throw new DataBaseException("Error amb la informació de l'ordre");
            }
            return orders;
        }
        return null;
    }

    /**
     * Changes served status in an order
     * @param order Order to be modifiec
     * @param reserva Reservation to which this order belongs
     */
    public void setServed(int order, int reserva) throws DataBaseException {
        if (conexio()){
            String auxQuery = "SELECT id_comanda FROM Comanda WHERE id_reserva  = ? LIMIT 1 OFFSET ?;";
            String query = "UPDATE Comanda SET servit = TRUE WHERE id_reserva = ? AND id_comanda = ?;";
            PreparedStatement preparedStmt = null;
            try {
                preparedStmt = connection.prepareStatement(auxQuery);
                preparedStmt.setInt(1, reserva);
                preparedStmt.setInt(2, order - 1);
                preparedStmt.execute();
                ResultSet rs = preparedStmt.getResultSet();
                rs.next();
                int idComanda = rs.getInt("id_comanda");

                preparedStmt = connection.prepareStatement(query);
                preparedStmt.setInt(1, reserva);
                preparedStmt.setInt(2, idComanda);
                preparedStmt.execute();

                //TODO: tancar connexió?
            }catch (SQLException e){ //manage exception
                throw new DataBaseException(ERROR_BBDD);
            }
        }
    }
}
