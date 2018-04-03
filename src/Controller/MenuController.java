package Controller;

import Model.Carta;
import Model.DatabaseConector;
import View.MenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuController implements ActionListener {
    private MenuView menuView;
    private DatabaseConector databaseConector;
    private MainController parent;

    public MenuController(MenuView menuView, DatabaseConector databaseConector, MainController parent) {
        this.menuView = menuView;
        this.databaseConector = databaseConector;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case MenuView.ADD_DISH:
                ArrayList<Carta> aux = databaseConector.getCarta();
                boolean found = false;
                for (Carta c: aux){
                    //TODO: FER REGEX DEL NOM
                    if (c.getNomPlat().equals(menuView.getNewDishName())){
                        found = true;
                    }
                }
                if (found == true){
                    menuView.confirmEntry(found);
                }else{
                    try{
                        double priceAux = Double.valueOf(menuView.getNewDishPrice());
                        int stockAux = Integer.valueOf(menuView.getNewDishStock());

                        databaseConector.addDish(menuView.getNewDishName(), priceAux, stockAux);
                        menuView.confirmEntry(found);
                    }catch (NumberFormatException e1){
                        //TODO: FER FUNCIO ERROR JDIALOG
                        e1.getMessage();
                    }
                }
                break;
            case MenuView.DELETE_DISH:
                System.out.println("delete dish");
                break;
            case MenuView.ADD_STOCK:
                System.out.println("add stock");
                break;
            case MenuView.EXIT:
                parent.setVisible(true);
                menuView.setVisible(false);
                break;
        }
    }

}
