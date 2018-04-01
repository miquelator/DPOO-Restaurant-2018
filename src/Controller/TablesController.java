package Controller;

import Model.DatabaseConector;
import Model.Taula;
import View.TablesView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TablesController implements ActionListener {
    private TablesView tablesView;
    private DatabaseConector databaseConector;

    public TablesController(TablesView tablesView, DatabaseConector databaseConector) {
        this.tablesView = tablesView;
        this.databaseConector = databaseConector;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            //TODO: POSAR LISTENERS ALS TABS
            case TablesView.ADD_TABLE:
                System.out.println("add tables");
                break;
            case TablesView.SHOW_TABLE_LIST:
                System.out.println("show table list");

                ArrayList<Taula> aux = databaseConector.getTaula();
                tablesView.updateTables(aux);
                break;
            case TablesView.SHOW_TABLE_RESERVATIONS:
                System.out.println("show table reservations");
                break;
            case TablesView.DELETE_TABLE:
                System.out.println("delete table");
                break;
            case TablesView.EXIT:
                //TODO: AFEGIR A LA CLASSE EL PARENT I FER EL CANVI DE VISTA ACTUAL A VISTA PARENT
                break;
        }
    }
}
