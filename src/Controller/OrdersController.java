// package where it bellongs
package Controller;


// import java classes
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
        displayOrders();
    }

    private void displayOrders() {
        ordersView.populateWestTable(databaseConector.getOrders());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case OrdersView.SERVE:
                databaseConector.setServed(ordersView.getSelectedOrder());
                ordersView.populateEastTable(databaseConector.getOrdersInfo(ordersMouseController.getIdReserva()));
                break;
        }
    }

}
