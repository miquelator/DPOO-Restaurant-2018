package Controller;

import Model.Carta;
import Model.ConfigJson;
import Model.DatabaseConector;
import Model.RandomString;
import Network.RecepcioSocketThread;
import Network.ReservesSocketThread;
import View.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainController implements ActionListener{
    private MainView mainView;
    private DatabaseConector databaseConector;
    private RandomString randomString;
    private RecepcioSocketThread recepcioThread;
    private ReservesSocketThread reservesThread;

    public MainController(MainView mainView, ConfigJson configJson, RecepcioSocketThread rec, ReservesSocketThread res) {
        this.mainView = mainView;
        databaseConector = new DatabaseConector(configJson);
        recepcioThread = rec;
        reservesThread = res;
        reservesThread.addMainController(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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

    private void manageTables() {

        TablesView tablesView = new TablesView();
        TablesController tablesController = new TablesController(tablesView, databaseConector, this);
        TablesChangeController tablesChangeController = new TablesChangeController(tablesView, databaseConector);
        tablesView.registerListeners(tablesController, tablesChangeController);
        mainView.setVisible(false);
        tablesView.setVisible(true);
    }

    private void manageMenu() {
        MenuView menuView = new MenuView();
        MenuController menuController = new MenuController(menuView, databaseConector, this);
        MenuChangeController menuChangeController = new MenuChangeController(menuView, databaseConector);
        menuView.registerListeners(menuController, menuChangeController);
        mainView.setVisible(false);
        menuView.setVisible(true);
    }

    private void showGraphs() {
        //TODO: RESIZE GRAFICAS PERQUE SURTIN UNA AL COSTAT DEL ALTRE
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

        TopFiveGraphView topFiveGraphView = new TopFiveGraphView(cartaTotal.get(0).getTotals(), comandesPlatsTotals, nomPlatsTotals);
        topFiveGraphView.TopFiveGraph();
        topFiveGraphView.setVisible(true);

        TopFiveWeeklyGraphView topFiveWeeklyGraphView = new TopFiveWeeklyGraphView(cartaSemanal.get(0).getSemanals(), comandesPlatsSemanals, nomPlatsSemanals);
        topFiveWeeklyGraphView.TopFiveGraph();
        topFiveWeeklyGraphView.setVisible(true);
    }


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

    public boolean autenticar (String user, String password){
        return databaseConector.autenticar(user,password);

    }

    public ArrayList<Carta> getMenu() {
         return databaseConector.getCarta();
    }
}
