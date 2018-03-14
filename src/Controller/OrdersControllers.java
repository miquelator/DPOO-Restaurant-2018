package Controller;

import View.OrdersView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrdersControllers implements ActionListener {
    private OrdersView ordersView;

    public OrdersControllers(OrdersView ordersView) {
        this.ordersView = ordersView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){

        }
    }
}
