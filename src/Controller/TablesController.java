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

    private void deleteTable() {
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

    private void updateDeleteTables() {
        ArrayList<Taula> auxDelete = databaseConector.getTaula();
        int size = auxDelete.size();
        ArrayList<Integer> ID = new ArrayList<>();
        for (int i = 0; i < size; i++){
            ID.add(auxDelete.get(i).getIdTaula());
        }
        tablesView.loadTablesID(ID);
    }

    private void addTable() {
        int numSeients = (int) tablesView.getTableToAdd();

        try {
            if (numSeients == 0){
                tablesView.mostraErrorServidor("El nombre de comensals no pot ser 0!", "Error");
            }else{
                if (databaseConector.addTable(numSeients)){
                    tablesView.mostraInformacioServidor("Taula afegida correctament", "INFORMACIO");
                    tablesView.resetNumSeients();
                }
            }
        }catch (NumberFormatException ne){
            tablesView.mostraErrorServidor("Error a l'hora d'afegir la taula!", "Error");
        }
    }

}
