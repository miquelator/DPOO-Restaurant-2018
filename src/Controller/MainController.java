// this class belongs to controller package
package Controller;

// import our classes (model, view, network)
import Model.*;
import Network.RecepcioSocketThread;
import Network.ReservesSocketThread;
import View.*;

// import java classes
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/***
 * Class that controls the main view implements action listener
 */
public class MainController implements ActionListener{

    // create instance variables
    private MainView mainView;
    private DatabaseConector databaseConector;
    private RandomString randomString;
    private RecepcioSocketThread recepcioThread;
    private ReservesSocketThread reservesThread;

    /***
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

    private void manageOrders() {
        //TODO: IMPLEMENTAR VISTA I CONTROLADOR DE LES COMANDES
        System.out.println("orders");

        OrdersView ordersView = new OrdersView();
        OrdersControllers ordersControllers = new OrdersControllers(ordersView);
        ordersView.registerListeners(ordersControllers);
        ordersView.setVisible(true);
    }

    /***
     * This method creates the tables view and the controllers that manages those classes
     */
    private void manageTables() {

        // create view
        TablesView tablesView = new TablesView();

        // create controllers
        TablesController tablesController = new TablesController(tablesView, databaseConector, this);
        TablesChangeController tablesChangeController = new TablesChangeController(tablesView, databaseConector);
        SeatsSpinController seatsSpinController = new SeatsSpinController(tablesView);

        //register controllers
        tablesView.registerListeners(tablesController, tablesChangeController, seatsSpinController);

        // change window visibility
        mainView.setVisible(false);
        tablesView.setVisible(true);
    }

    /***
     * This method creates the views and the controllers of the menu tab
     */
    private void manageMenu() {

        // create menu view
        MenuView menuView = new MenuView();

        // create controllers
        MenuController menuController = new MenuController(menuView, databaseConector, this);
        MenuChangeController menuChangeController = new MenuChangeController(menuView, databaseConector);

        // register controllers
        menuView.registerListeners(menuController, menuChangeController);

        // change window visibility
        mainView.setVisible(false);
        menuView.setVisible(true);
    }

    /***
     * This method create the view graphs
     */
    private void showGraphs() {

        // get database data
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
    }


    /***
     * Method that shows the main view
     * @param visible
     */
    public void setVisible(boolean visible){
        mainView.setVisible(visible);
    }


    public String checkAndAddReserves(String nomReserva, int comensals, Object date) {
        ArrayList<Integer> taules = databaseConector.isTableOcuped(comensals, date);
         if(taules.size() == 0){
             return "No hi ha taules disponibles!";
         }else{
             randomString = new RandomString();
             //TODO: ACTUALITZAR TAULA RESERVES
             return databaseConector.addReserve(nomReserva, date, randomString.nextString(), taules.get(0), comensals);

         }
    }

    /***
     * Method that autenticates a user with a given user and pasword returns if it de user exists
     * @param user String with the username
     * @param password String with the password
     * @return integer that's -1 if the user do not exits and 1 if it exists
     */
    public int autenticar (String user, String password){
        // TODO: canviar en contes de retornar un -1 trar una excepcio propia?
        return databaseConector.autenticar(user,password);

    }


    public double pay (int id_taula){
        // TODO: No hauria de retornar algo?
        double totalPagar = databaseConector.getTotalPrice(id_taula);
        databaseConector.deleteComanda(id_taula);
        return totalPagar;
    }

    /***
     * Method that gets the menu of the restaurant
     * @return Arraylist of carta with the menu of the restaurant
     */
    public ArrayList<Carta> getMenu() {
         return databaseConector.getCarta();
    }


    public void getOrderStatus(int idtaula) {
        //TODO:QUE RETORNA AIXO?
        databaseConector.getOrderStatus(idtaula);
    }

    /***
     * Method that saves the order into the database
     * @param cartaSelection Arraylist of cartaSelection with a selection of dishes
     * @param idtaula integer varaible with the id of the table
     */
    public void saveOrder(ArrayList<CartaSelection> cartaSelection, int idtaula) {
        databaseConector.saveOrder(cartaSelection, idtaula);
    }
}
