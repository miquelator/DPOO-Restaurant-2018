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

    /***
     * Constructor of the class
     * @param ordersView instance of Orders View
     * @param databaseConector
     */
    public OrdersController(OrdersView ordersView, DatabaseConector databaseConector) {
        this.ordersView = ordersView;
        this.databaseConector = databaseConector;
        displayOrders();
    }

    private void displayOrders() {
        databaseConector.getOrders();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){

        }
    }

}
