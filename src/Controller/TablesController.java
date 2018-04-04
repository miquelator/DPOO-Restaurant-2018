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
        if (databaseConector.deleteTable(tablesView.getTableToDelete() + 1)){
            tablesView.mostraInformacioServidor("Taula esborrada correctament.", "INFORMACIO");
            //TODO: ACTUALITZAR EL JCOMBOX DELS INDEX DE BORRAR DESPRES DE BORRAR
        }else {
            tablesView.mostraErrorServidor("Error al esborrar la taula!", "ERROR");
        }

    }

    private void addTable() {
        int numSeients = (int) tablesView.getTableToAdd();
        try {
            if (databaseConector.addTable(numSeients)){
                tablesView.mostraInformacioServidor("Taula afegida correctament", "INFORMACIO");
                tablesView.resetNumSeients();
            }
        }catch (NumberFormatException ne){
            tablesView.mostraErrorServidor("Error a l'hora d'afegir la taula!", "Error");
        }
    }
}
