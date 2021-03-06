// packacge where it belongs
package Controller.ActionListener;

// import our classes
import Exceptions.DataBaseException;
import Model.Carta;
import Model.DatabaseConector;
import View.MenuView;

// import java classes
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class controls the menu
 */
public class MenuController implements ActionListener {

    // instance attributes
    private MenuView menuView;
    private DatabaseConector databaseConector;
    private MainController parent;

    /**
     * Constructor for current class
     * @param menuView Instance of MenuView
     * @param databaseConector Instance of DatabaseConector
     * @param parent Instance of MainController
     */
    public MenuController(MenuView menuView, DatabaseConector databaseConector, MainController parent) {
        this.menuView = menuView;
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
            case MenuView.ADD_DISH:
                addDish();
                break;
            case MenuView.DELETE_DISH:
                deleteDish();
                break;
            case MenuView.UPDATE_STOCK:
                updateStock();
                break;
            case MenuView.EXIT:
                parent.setVisible(true);
                menuView.setVisible(false);
                break;
        }
    }

    /**
     * This method updates the stock of the dishes
     */
    private void updateStock() {

        // updates the stock of the items and
        // looks if the stock is bigger than zero or if it's not an integer, in that case raises and exception
        // and shows error message on input data
        try {
            int newStock = menuView.getUpdatedStock();
            if (newStock < 0){
                menuView.confirmEntry(4);
            }else{
                if(databaseConector.updateStock(menuView.getUpdatedDishName(), newStock)){
                    menuView.confirmEntry(7);
                }else{
                    menuView.confirmEntry(8);
                }
            }
        }catch (NumberFormatException e1){
            menuView.confirmEntry(4);
            e1.getMessage();
        }catch (DataBaseException de){
            menuView.showPopError(de.getMessage());
        }
    }

    /**
     * Deletes a dish from the DDBB
     */
    private void deleteDish() {
        try {
            if(databaseConector.deleteDish(menuView.getDeletedDishName())){
                menuView.confirmEntry(5);
                ArrayList<Carta> plats = databaseConector.getCarta();
                String[] list = new String[plats.size()];
                for(int i = 0; i < plats.size();i++){
                    list[i] = plats.get(i).getNomPlat();
                }
                menuView.populateDelete(list);
            }else{
                menuView.confirmEntry(6);
            }
        } catch (DataBaseException de) {
            menuView.showPopError(de.getMessage());
        }
    }


    /**
     * Method that adds a dish from window settings
     */
    private void addDish() {

        try {
            ArrayList<Carta> aux = databaseConector.getCarta();
            boolean found = false;
            Pattern pattern = Pattern.compile("['\"*$]");
            Matcher matcher = pattern.matcher(menuView.getNewDishName());
            if (menuView.getNewDishName().contains("\"") || menuView.getNewDishName().equals(" ") || menuView.getNewDishName().equals("") || matcher.find()) {
                menuView.confirmEntry(9);
            } else {
                for (Carta c : aux) {
                    if (c.getNomPlat().equals(menuView.getNewDishName())) {
                        found = true;
                    }
                }
                if (found == true) {
                    menuView.confirmEntry(1);
                } else {
                    try {
                        double priceAux = menuView.getNewDishPrice();
                        int stockAux = menuView.getNewDishStock();

                        // if there isn't any error of entry add the dish
                        databaseConector.addDish(menuView.getNewDishName(), menuView.getDishType(), priceAux, stockAux);
                        menuView.confirmEntry(2);
                    } catch (NumberFormatException e1) {
                        menuView.confirmEntry(3);
                        e1.getMessage();
                    }
                }
            }
        }catch (DataBaseException de){
            menuView.showPopError(de.getMessage());
        }
        menuView.clearAddDish();

    }
}
