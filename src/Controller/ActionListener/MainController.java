// this class belongs to controller package
package Controller.ActionListener;

// import our classes (model, view, network)
import Controller.ChangeListener.MenuChangeController;
import Controller.ChangeListener.TablesChangeController;
import Controller.MouseController.OrdersMouseController;
import Controller.MouseController.SeatsSpinController;
import Controller.WindowAdapter.MenuWindowClosing;
import Controller.WindowAdapter.OrderWindowClosing;
import Controller.WindowAdapter.TableWindowClosing;
import Exceptions.DataBaseException;
import Model.*;
import Network.RecepcioSocketThread;
import Network.ReservesSocketThread;
import View.*;
import jdk.management.resource.internal.inst.DatagramDispatcherRMHooks;
import sun.util.resources.cldr.ta.CalendarData_ta_IN;

// import java classes
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that controls the main view implements action listener
 */
public class MainController implements ActionListener{

    // create instance variables
    private MainView mainView;
    private DatabaseConector databaseConector;
    private RandomString randomString;
    private RecepcioSocketThread recepcioThread;
    private ReservesSocketThread reservesThread;
    private OrdersMouseController ordersMouseController;
    private boolean orderViewed = false;

    /**
     * Constructor with parameters of the controller
     * @param mainView Main
     * @param configJson
     * @param rec
     * @param res
     */
    public MainController(MainView mainView, ConfigJson configJson, RecepcioSocketThread rec, ReservesSocketThread res) {
        // makes the connections with the instances
        this.mainView = mainView;
        databaseConector = new DatabaseConector(configJson);
        recepcioThread = rec;
        reservesThread = res;
        reservesThread.addMainController(this);
    }


    /**
     * Executes depending upon selected window
     * @param e Event thrown when window is changed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // depending on the press button make an action
        switch (e.getActionCommand()){
            case MainView.MANAGE_TABLES:
                manageTables();
                break;

            case MainView.MANAGE_MENU:
                manageMenu();
                break;

            case MainView.MANAGE_ORDERS:
                manageOrders();
                break;

            case MainView.SHOW_TOP_FIVE:
                showGraphs();
                break;

            case MainView.EXIT:
                System.exit(1);
                break;

        }
    }

    /**
     * Initialises order's view and controllers
     */
    private void manageOrders() {
        orderViewed = true;
        OrdersView ordersView = new OrdersView();
        OrderWindowClosing windowClosing = new OrderWindowClosing(mainView, ordersView);
        ordersMouseController = new OrdersMouseController(ordersView, databaseConector);
        OrdersController ordersControllers = new OrdersController(ordersView, databaseConector, ordersMouseController);
        ordersView.registerListeners(ordersControllers, ordersMouseController, windowClosing);
        mainView.setVisible(false);
        ordersView.setVisible(true);
    }

    /**
     * This method creates the tables view and the controllers that manages those classes
     */
    private void manageTables() {
        // create view
        TablesView tablesView = new TablesView();

        // create controllers
        TableWindowClosing tableWindowClosing = new TableWindowClosing(mainView, tablesView);
        TablesController tablesController = new TablesController(tablesView, databaseConector, this);
        TablesChangeController tablesChangeController = new TablesChangeController(tablesView, databaseConector);
        SeatsSpinController seatsSpinController = new SeatsSpinController(tablesView);

        //register controllers
        tablesView.registerListeners(tablesController, tablesChangeController, seatsSpinController, tableWindowClosing);

        // change window visibility
        mainView.setVisible(false);
        tablesView.setVisible(true);
    }

    /**
     * This method creates the views and the controllers of the menu tab
     */
    private void manageMenu() {

        // create menu view
        MenuView menuView = new MenuView();

        // create controllers
        MenuWindowClosing menuWindowClosing = new MenuWindowClosing(mainView, menuView);
        MenuController menuController = new MenuController(menuView, databaseConector, this);
        MenuChangeController menuChangeController = new MenuChangeController(menuView, databaseConector);

        // register controllers
        menuView.registerListeners(menuController, menuChangeController, menuWindowClosing);

        // change window visibility
        mainView.setVisible(false);
        menuView.setVisible(true);
    }

    /**
     * This method create the view graphs
     */
    private void showGraphs() {

        // get database data
        try {
            ArrayList<Carta> cartaTotal = databaseConector.getTopFiveTotals();
            ArrayList<Carta> cartaSemanal = databaseConector.getTopFiveWeekly();
        

            int[] comandesPlatsTotals = new int[cartaTotal.size()];
            String[] nomPlatsTotals = new String[cartaTotal.size()];
            int[] comandesPlatsSemanals = new int[cartaSemanal.size()];
            String[] nomPlatsSemanals = new String[cartaSemanal.size()];
    
    
            for (int i = 0; i < cartaTotal.size(); i++){
                nomPlatsTotals[i] = cartaTotal.get(i).getNomPlat();
                comandesPlatsTotals[i] = cartaTotal.get(i).getTotals();
            }
    
            for (int x = 0; x < cartaSemanal.size(); x++){
                nomPlatsSemanals[x] = cartaSemanal.get(x).getNomPlat();
                comandesPlatsSemanals[x] = cartaSemanal.get(x).getSemanals();
            }
    
            // create view top five with the given parameters
            TopFiveGraphView topFiveGraphView = new TopFiveGraphView(cartaTotal.get(0).getTotals(), comandesPlatsTotals, nomPlatsTotals);
            topFiveGraphView.TopFiveGraph();
            // show window
            topFiveGraphView.setVisible(true);
    
            // create view top five week with the given parameters
            TopFiveWeeklyGraphView topFiveWeeklyGraphView = new TopFiveWeeklyGraphView(cartaSemanal.get(0).getSemanals(), comandesPlatsSemanals, nomPlatsSemanals);
            topFiveWeeklyGraphView.TopFiveGraph();
            // show window
            topFiveWeeklyGraphView.setVisible(true);
        }catch (DataBaseException de){
            mainView.showPopError(de.getMessage());
        }
    }


    /**
     * Method that shows the main view
     * @param visible
     */
    public void setVisible(boolean visible){
        mainView.setVisible(visible);
    }


    public String checkAndAddReserves(String nomReserva, int comensals, Object date) {
        
        try {
            ArrayList<Integer> taules = databaseConector.isTableOcuped(comensals, date);
            if(taules.size() == 0){
                return "No hi ha taules disponibles!";
            }else{
                randomString = new RandomString();
                return databaseConector.addReserve(nomReserva, date, randomString.nextString(), taules.get(0), comensals);

            }
        }catch (DataBaseException de){
            mainView.showPopError(de.getMessage());
        }
        return "No hi ha taules disponibles!";
        
    }

    /**
     * Method that autenticates a user with a given user and pasword returns if it de user exists
     * @param user String with the username
     * @param password String with the password
     * @return integer that's -1 if the user do not exits and 1 if it exists
     */
    public int  autenticar (String user, String password) throws DataBaseException {
        return databaseConector.autenticar(user, password);

    }


    /**
     * Indicates user wants to pay
     * @param id_taula table which wishes to pay
     * @return amount to be payed
     */
    public double pay (int id_taula){
        double totalPagar = 0;
        try{
            totalPagar = databaseConector.getTotalPrice(id_taula);
            databaseConector.deleteComanda(id_taula);
        }catch (DataBaseException de){
            mainView.showPopError(de.getMessage());
        }
        
        return totalPagar;
    }

    /**
     * Method that gets the menu of the restaurant
     * @return Arraylist of carta with the menu of the restaurant
     */
    public ArrayList<Carta> getMenu() {
        ArrayList<Carta> menu = null;
        
        try{
            menu = databaseConector.getCarta();
        }catch (DataBaseException de){
            mainView.showPopError(de.getMessage());
        }
        return menu;
    }

    /**
     * Method that gets the status of an order
     * @param idtaula
     * @return
     */
    public ArrayList<CartaStatus> getOrderStatus(int idtaula) {
        try{
            return databaseConector.getOrderStatus(idtaula);
        }catch (DataBaseException de){
            mainView.showPopError(de.getMessage());
        }
        return null;
    }

    /**
     * Method that saves the order into the database and updates available stock
     * @param cartaSelection Arraylist of cartaSelection with a selection of dishes
     * @param idtaula integer varaible with the id of the table
     */
    public void saveOrderUpdateStock(ArrayList<CartaSelection> cartaSelection, int idtaula) {

        try {

            databaseConector.saveOrderUpdateStock(cartaSelection, idtaula);
        } catch (DataBaseException de) {
            mainView.showPopError(de.getMessage());
        }
    }

    /**
     * Disconects the specified table from the DDBB
     * @param id_taula id from the table to be disconnected
     */
    public void disconnect (int id_taula){

        try {
            databaseConector.disconnect(id_taula);
        } catch (DataBaseException de) {
            mainView.showPopError(de.getMessage());
        }
    }

    /**
     * Checks if there is enough quantity of a product before accepting the order
     * @param cartaSelection User's selection from the menu
     * @return true if there is enough quantity, false otherwise
     */
    public boolean checkQuantityOrder(ArrayList<CartaSelection> cartaSelection){
        try {
            for (CartaSelection cartaSelect : cartaSelection) {
                if (!databaseConector.checkQuantityPlat(cartaSelect)) {
                    return false;
                }
            }
        }catch (DataBaseException de){
            mainView.showPopError(de.getMessage());
        }
        return true;
    }

    /**
     * Redraws the orders view
     */
    public void updateOrdersView() {
        if (orderViewed){
            ordersMouseController.updateTables();
            //ordersMouseController.updateTables();
        }
    }

    public void setTableOccupied(int idtaula, boolean occupied) {
        try {
            databaseConector.setTableOccupied(idtaula, occupied);
        } catch (DataBaseException ignored) {

        }
    }
}
