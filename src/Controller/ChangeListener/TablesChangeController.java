// package where it bellongs
package Controller.ChangeListener;

// import our classes
import Exceptions.DataBaseException;
import Model.DatabaseConector;
import Model.Taula;
import View.TablesView;

// import java classes
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

/***
 * This class controls the changing of tables view
 */
public class TablesChangeController implements ChangeListener {
    private TablesView tablesView;
    private DatabaseConector databaseConector;

    /**
     * Constructor for current class
     * @param tablesView Instance of TablesView
     * @param databaseConector Instance of DatabaseConector
     */
    public TablesChangeController(TablesView tablesView, DatabaseConector databaseConector) {
        this.tablesView = tablesView;
        this.databaseConector = databaseConector;
    }

    /**
     * Executes depending upon selected window
     * @param e Event thrown when window is changed
     */
    @Override
    public void stateChanged(ChangeEvent e) {

        try {
            switch (tablesView.getTabbedPaneWindow()) {
                case 1:
                    ArrayList<Taula> aux = databaseConector.getTaula();
                    tablesView.updateTables(aux);
                    break;
                case 2:
                    tablesView.mostraReserves(databaseConector.getReserves());
                    break;
                case 3:
                    ArrayList<Taula> auxDelete = databaseConector.getTaula();
                    int size = auxDelete.size();
                    ArrayList<Integer> ID = new ArrayList<>();
                    for (int i = 0; i < size; i++) {
                        ID.add(auxDelete.get(i).getIdTaula());
                    }
                    tablesView.loadTablesID(ID);
                    break;
            }
        } catch (DataBaseException de) {
            tablesView.mostraErrorServidor(de.getMessage());
        }
    }
}
