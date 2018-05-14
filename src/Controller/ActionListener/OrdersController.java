// package where it bellongs
package Controller.ActionListener;


// import java classes
import Controller.MouseController.OrdersMouseController;
import Model.DatabaseConector;
import View.OrdersView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        ordersView.populateWestTable(databaseConector.getReservation());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case OrdersView.SERVE:
                databaseConector.getOrderInfo(ordersMouseController.getIdReserva());
                databaseConector.setServed(ordersView.getSelectedOrder(), ordersView.getSelectedReservation());
                ordersView.populateEastTable(databaseConector.getOrderInfo(ordersMouseController.getIdReserva()));
                break;
        }
    }

}
