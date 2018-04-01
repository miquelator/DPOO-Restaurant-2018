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
                //TODO: IMPLEMENTAR VISTA I CONTROLADOR DE LES TAULES (Angel)
                System.out.println("tables");

                TablesView tablesView = new TablesView();
                TablesController tablesController = new TablesController(tablesView, databaseConector);
                tablesView.registerListeners(tablesController);
                mainView.setVisible(false);
                tablesView.setVisible(true);
                break;
            case MainView.MANAGE_MENU:
                //TODO: IMPLEMENTAR VISTA I CONTROLADOR DEL MENU
                System.out.println("menu");

                //test per comprovar conexio correcta a la BBDD
                ArrayList<Carta> aux = databaseConector.getCarta();
                for (Carta c: aux){
                    System.out.println("id: " + c.getIdPlat());
                    System.out.println("nomPlat: " + c.getNomPlat());
                    System.out.println("preu: " + c.getPreu());
                    System.out.println("quantitat: " + c.getQuantitat());
                }


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
