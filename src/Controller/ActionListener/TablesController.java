// package where it belongs
package Controller.ActionListener;

// import our classes
import Exceptions.DataBaseException;
import Exceptions.TablesException;
import Exceptions.ViewNullException;
import Model.DatabaseConector;
import Model.Taula;
import View.TablesView;

// import java classes
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Class that manages the tables view and data
 */
public class TablesController implements ActionListener {

    // instance attributes
    private TablesView tablesView;
    private DatabaseConector databaseConector;
    private MainController parent;

    /**
     * Contructor for current class
     * @param tablesView Instance of TablesView
     * @param databaseConector Instance of DatabaseConector
     * @param parent Instance of MainController
     */
    public TablesController(TablesView tablesView, DatabaseConector databaseConector, MainController parent) {
        this.tablesView = tablesView;
        this.databaseConector = databaseConector;
        this.parent = parent;
    }

    /**
     * Executes upon action performed by view's element
     * @param e Event launched by the view
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case TablesView.ADD_TABLE:
                addTable();
                break;
            case TablesView.DELETE_TABLE:
                deleteTable();
                break;
            case TablesView.EXIT:
                parent.setVisible(true);
                tablesView.setVisible(false);
                break;
        }
    }

    /**
     * Method that deletes a selected table
     */
    private void deleteTable() {

        // deletes a table if there is a error shows the error
        try{
            databaseConector.getTableReserve(tablesView.getTableToDelete());
            databaseConector.deleteTable((tablesView.getTableToDelete()));
            tablesView.mostraInformacioServidor("Taula esborrada correctament.");
            updateDeleteTables();
        }catch (ViewNullException | DataBaseException e){
            tablesView.mostraErrorServidor(e.getMessage());

        }catch (TablesException te){
            try {
                if (tablesView.showDeleteWarning()) {
                    databaseConector.deleteReserveTable(tablesView.getTableToDelete());
                    databaseConector.deleteTable(tablesView.getTableToDelete());
                    tablesView.mostraInformacioServidor("Taula esborrada correctament.");
                    updateDeleteTables();
                }
            }catch (ViewNullException | DataBaseException e){
                tablesView.mostraErrorServidor(e.getMessage());
            }

        }

    }


    /**
     * Method that updates the delate tables
     */
    private void updateDeleteTables() {

        try {
            ArrayList<Taula> auxDelete = databaseConector.getTaula();
            int size = auxDelete.size();
            ArrayList<Integer> ID = new ArrayList<>();
            for (int i = 0; i < size; i++){
                ID.add(auxDelete.get(i).getIdTaula());
            }
            tablesView.loadTablesID(ID);
        } catch (DataBaseException de) {
            tablesView.mostraErrorServidor(de.getMessage());
        }

    }

    /**
     * Method that adds a table
     */
    private void addTable() {
        int numSeients = (int) tablesView.getTableToAdd();

        try {
            databaseConector.addTable(numSeients);
            tablesView.mostraInformacioServidor("Taula afegida correctament");
            tablesView.resetNumSeients();

        }catch (NumberFormatException ne){
            tablesView.mostraErrorServidor("Error a l'hora d'afegir la taula!");
        } catch (DataBaseException de) {
            tablesView.mostraErrorServidor(de.getMessage());
        }
    }
}