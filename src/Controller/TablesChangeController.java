package Controller;

import Model.DatabaseConector;
import Model.Taula;
import View.TablesView;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

public class TablesChangeController implements ChangeListener {
    private TablesView tablesView;
    private DatabaseConector databaseConector;

    public TablesChangeController(TablesView tablesView, DatabaseConector databaseConector) {
        this.tablesView = tablesView;
        this.databaseConector = databaseConector;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (tablesView.getTabbedPaneWindow() == 1){
            ArrayList<Taula> aux = databaseConector.getTaula();
            tablesView.updateTables(aux);
        }
    }
}
