package Controller;

import Model.DatabaseConector;
import View.TablesView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TablesController implements ActionListener {
    private TablesView tablesView;
    private DatabaseConector databaseConector;
    private MainController parent;

    public TablesController(TablesView tablesView, DatabaseConector databaseConector, MainController parent) {
        this.tablesView = tablesView;
        this.databaseConector = databaseConector;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case TablesView.ADD_TABLE:
                System.out.println("add tables");
                break;
            case TablesView.SHOW_TABLE_RESERVATIONS:
                System.out.println("show table reservations");
                break;
            case TablesView.DELETE_TABLE:
                System.out.println("delete table");
                break;
            case TablesView.EXIT:
                parent.setVisible(true);
                tablesView.setVisible(false);
                break;
        }
    }
}
