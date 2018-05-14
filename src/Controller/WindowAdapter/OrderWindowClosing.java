package Controller.WindowAdapter;

import View.MainView;
import View.OrdersView;

import java.awt.event.WindowAdapter;

public class OrderWindowClosing extends WindowAdapter {
    private MainView parent;
    private OrdersView ordersView;

    public OrderWindowClosing(MainView parent, OrdersView ordersView) {
        this.ordersView = ordersView;
        this.parent = parent;
    }

    @Override
    public void windowClosing(java.awt.event.WindowEvent e) {
        ordersView.setVisible(false);
        parent.setVisible(true);
    }
}
