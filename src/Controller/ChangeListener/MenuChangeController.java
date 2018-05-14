package Controller.ChangeListener;

// import our classes
import Model.Carta;
import Model.DatabaseConector;
import View.MenuView;

// import java classes
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

/***
 * This class manages the menu
 */
public class MenuChangeController implements ChangeListener{

    // instance attributes
    private MenuView menuView;
    private DatabaseConector databaseConector;

    /**
     * Constructor for current class
     * @param menuView Instance of MenuView
     * @param databaseConector Instance of DatabaseConector
     */
    public MenuChangeController(MenuView menuView, DatabaseConector databaseConector) {
        this.menuView = menuView;
        this.databaseConector = databaseConector;
    }

    /**
     * Executes depending upon selected window
     * @param e Event thrown when window is changed
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        switch (menuView.getTabbedPaneWindow()){
            case 0:
                break;
            case 1:
                ArrayList<Carta> aux = databaseConector.getCarta();
                menuView.updateMenu(aux);
                break;
            case 2:
                ArrayList<Carta> plats = databaseConector.getCarta();
                String[] list = new String[plats.size()];
                for(int i = 0; i < plats.size();i++){
                    list[i] = plats.get(i).getNomPlat();
                }
                menuView.populateDelete(list);
                break;
            case 3:
                ArrayList<Carta> carta = databaseConector.getCarta();
                String[] llista = new String[carta.size()];
                for(int i = 0; i < carta.size();i++){
                    llista[i] = carta.get(i).getNomPlat();
                }
                menuView.populateStock(llista);
                break;
        }
    }
}
