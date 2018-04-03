package Controller;

import Model.Carta;
import Model.ConfigJson;
import Model.DatabaseConector;
import View.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainController implements ActionListener{
    private MainView mainView;
    private DatabaseConector databaseConector;

    public MainController(MainView mainView, ConfigJson configJson) {
        this.mainView = mainView;
        databaseConector = new DatabaseConector(configJson);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case MainView.MANAGE_TABLES:
                //TODO: IMPLEMENTAR VISTA I CONTROLADOR DE LES TAULES
                System.out.println("tables");

                TablesView tablesView = new TablesView();
                TablesController tablesController = new TablesController(tablesView, databaseConector, this);
                TablesChangeController tablesChangeController = new TablesChangeController(tablesView, databaseConector);
                tablesView.registerListeners(tablesController, tablesChangeController);
                mainView.setVisible(false);
                tablesView.setVisible(true);
                break;
            case MainView.MANAGE_MENU:
                MenuView menuView = new MenuView();
                MenuController menuController = new MenuController(menuView, databaseConector, this);
                MenuChangeController menuChangeController = new MenuChangeController(menuView, databaseConector);
                menuView.registerListeners(menuController, menuChangeController);
                mainView.setVisible(false);
                menuView.setVisible(true);
                break;
            case MainView.MANAGE_ORDERS:
                //TODO: IMPLEMENTAR VISTA I CONTROLADOR DE LES COMANDES
                System.out.println("orders");

                OrdersView ordersView = new OrdersView();
                OrdersControllers ordersControllers = new OrdersControllers(ordersView);
                ordersView.registerListeners(ordersControllers);
                break;
            case MainView.SHOW_TOP_FIVE:
                //TODO: IMPLEMENTAR VISTA I CONTROLADOR DE LA GRAFICA (Angel)

                System.out.println("top five");

                TopFiveGraphView topFiveGraphView = new TopFiveGraphView();
                new TopFiveGraphController(topFiveGraphView);
                break;
            case MainView.EXIT:
                System.exit(1);
                break;
        }
    }

    public void setVisible(boolean visible){
        mainView.setVisible(visible);
    }
}
