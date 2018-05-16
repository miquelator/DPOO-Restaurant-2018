// package where it bellongs
package Controller.ActionListener;


// import java classes
import Controller.MouseController.OrdersMouseController;
import Exceptions.DataBaseException;
import Model.DatabaseConector;
import View.OrdersView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/***
 * This class controls the Orders
 */
public class OrdersController implements ActionListener {

    // instance attributes
    private OrdersView ordersView;
    private DatabaseConector databaseConector;
    private OrdersMouseController ordersMouseController;

    /***
     * Constructor of the class
     * @param ordersView instance of Orders View
     * @param databaseConector
     * @param ordersMouseController
     */
    public OrdersController(OrdersView ordersView, DatabaseConector databaseConector, OrdersMouseController ordersMouseController) {
        this.ordersView = ordersView;
        this.databaseConector = databaseConector;
        this.ordersMouseController = ordersMouseController;
        try {
            ordersView.populateWestTable(databaseConector.getReservation());
        } catch (DataBaseException de) {
            ordersView.showPopError(de.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case OrdersView.SERVE:
                try{
                    databaseConector.getOrderInfo(ordersMouseController.getIdReserva());
                    databaseConector.setServed(ordersView.getSelectedOrder(), ordersView.getSelectedReservation());
                    ordersView.populateEastTable(databaseConector.getOrderInfo(ordersMouseController.getIdReserva()));
                }catch (DataBaseException de){
                    ordersView.showPopError(de.getMessage());
                }catch (ArrayIndexOutOfBoundsException e1){
                    ordersView.showPopError("No hi ha cap comanda seleccionada!");
                }

                break;
        }
    }

}
