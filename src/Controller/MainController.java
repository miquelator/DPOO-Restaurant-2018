package Controller;

import View.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController implements ActionListener{
    private MainView mainView;
    public MainController(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case MainView.MANAGE_TABLES:
                //TODO: IMPLEMENTAR VISTA I CONTROLADOR DE LES TAULES
                System.out.println("tables");

                TablesView tablesView = new TablesView();
                TablesController tablesController = new TablesController(tablesView);
                tablesController.registerListeners(tablesView);
                break;
            case MainView.MANAGE_MENU:
                //TODO: IMPLEMENTAR VISTA I CONTROLADOR DEL MENU
                System.out.println("menu");

                MenuView menuView = new MenuView();
                MenuController menuController = new MenuController(menuView);
                menuController.registerControllers(menuView);
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
}
