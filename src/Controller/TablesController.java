package Controller;

import View.TablesView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TablesController implements ActionListener {
    private TablesView tablesView;

    public TablesController(TablesView tablesView) {
        this.tablesView = tablesView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case TablesView.ADD_TABLE:
                System.out.println("add tables");
                break;
            case TablesView.SHOW_TABLE_LIST:
                System.out.println("show table list");
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
