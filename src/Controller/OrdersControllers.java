// package where it bellongs
package Controller;

// import our classes
import View.OrdersView;

// import java classes
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 * This class controls the Orders
 */
public class OrdersControllers implements ActionListener {

    // instance attributes
    private OrdersView ordersView;

    /***
     * Constructor of the class
     * @param ordersView instance of Orders View
     */
    public OrdersControllers(OrdersView ordersView) {
        this.ordersView = ordersView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){

        }
    }

}
