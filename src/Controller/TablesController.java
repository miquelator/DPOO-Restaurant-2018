// package where it belongs
package Controller;

// import our classes
import Model.DatabaseConector;
import Model.Taula;
import View.TablesView;

// import java classes
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/***
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

    /***
     * Method that deletes a selected table
     */
    private void deleteTable() {
        //TODO:CANVIAR PER TRY/CATCH?

        // delates a table if there is a error shows the error
        if (tablesView.getTableToDelete() != -1){
            if (databaseConector.getTableReserve(tablesView.getTableToDelete())) {
                if (databaseConector.deleteTable(tablesView.getTableToDelete())) {
                    tablesView.mostraInformacioServidor("Taula esborrada correctament.", "INFORMACIO");
                    updateDeleteTables();

                } else {
                    tablesView.mostraErrorServidor("Error al esborrar la taula!", "ERROR");
                }

            }else{
                if (tablesView.showDeleteWarning()) {
                    if (databaseConector.deleteReserveTable(tablesView.getTableToDelete())){
                        if (databaseConector.deleteTable(tablesView.getTableToDelete())) {
                            tablesView.mostraInformacioServidor("Taula esborrada correctament.", "INFORMACIO");
                            updateDeleteTables();
                        } else {
                            tablesView.mostraErrorServidor("Error al esborrar la taula!", "ERROR");
                        }
                    }
                    else {
                        tablesView.mostraErrorServidor("Error al esborrar la taula!", "ERROR");
                    }
                }else{
                    tablesView.mostraInformacioServidor("Esborrat de taula cancelat.", "INFORMACIO");                }
            }
        }else{
            tablesView.mostraErrorServidor("No hi ha taules registrades!", "ERROR");
        }

    }

    /***
     * Method that updates the delate tables
     */
    private void updateDeleteTables() {
        ArrayList<Taula> auxDelete = databaseConector.getTaula();
        int size = auxDelete.size();
        ArrayList<Integer> ID = new ArrayList<>();
        for (int i = 0; i < size; i++){
            ID.add(auxDelete.get(i).getIdTaula());
        }
        tablesView.loadTablesID(ID);
    }

    /***
     * Method that adds a table
     */
    private void addTable() {
        int numSeients = (int) tablesView.getTableToAdd();

        try {
            if (databaseConector.addTable(numSeients)){
                tablesView.mostraInformacioServidor("Taula afegida correctament", "INFORMACIO");
                tablesView.resetNumSeients();
            }else{
                tablesView.mostraErrorServidor("Error a l'hora d'afegir la taula!", "Error");
            }
        }catch (NumberFormatException ne){
            tablesView.mostraErrorServidor("Error a l'hora d'afegir la taula!", "Error");
        }
    }
}