package Controller;

import Model.Carta;
import Model.DatabaseConector;
import View.MenuView;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

/**
 * Created by oriol on 3/4/2018.
 */
public class MenuChangeController implements ChangeListener{
    private MenuView menuView;
    private DatabaseConector databaseConector;

    public MenuChangeController(MenuView menuView, DatabaseConector databaseConector) {
        this.menuView = menuView;
        this.databaseConector = databaseConector;
    }

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
