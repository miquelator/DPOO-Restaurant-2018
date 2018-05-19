// package where it bellongs
package Controller.ActionListener;


// import java classes
import Controller.MouseController.OrdersMouseController;
import Exceptions.DataBaseException;
import Model.DatabaseConector;
import Model.Order;
import View.OrdersView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * This class controls the Orders
 */
public class OrdersController implements ActionListener {

    // instance attributes
    private OrdersView ordersView;
    private DatabaseConector databaseConector;
    private OrdersMouseController ordersMouseController;

    /**
     * Constructor of the class
     * @param ordersView instance of OrdersView
     * @param databaseConector instance of DatabaseConector
     * @param ordersMouseController instance of OrdersMouseController
     */
    public OrdersController(OrdersView ordersView, DatabaseConector databaseConector, OrdersMouseController ordersMouseController) {
        this.ordersView = ordersView;
        this.databaseConector = databaseConector;
        this.ordersMouseController = ordersMouseController;
        setWestTable(-1);
    }

    /**
     * Retrieves data from DDBB in order to populate wwest side or Order's view
     * @param selectedIndex Index from which to get data from
     */
    private void setWestTable(int selectedIndex) {
        try {
            ArrayList<Integer> orders = databaseConector.getReservation();
            ArrayList<Integer> servedOrders = new ArrayList<>();
            ArrayList<Integer> notServedOrders = new ArrayList<>();
            int size = orders.size();
            for (int i = 0; i < size; i++){
                if (allServed(orders.get(i))){
                    servedOrders.add(orders.get(i));
                }else {
                    notServedOrders.add(orders.get(i));
                }
            }
            ordersView.populateWestTable(servedOrders, notServedOrders);
            if (!(selectedIndex == -1)){
                ordersView.setSelectedReservationIndex(selectedIndex);
            }
        } catch (DataBaseException de) {
            ordersView.showPopError(de.getMessage());
        }
    }

    /**
     * Checks if all orders have been served
     * @param reservationID Reservation Id used to check it's orders
     * @return true if all have been served, false otherwise
     */
    private boolean allServed(Integer reservationID) {
        try {
            ArrayList<Order> order = databaseConector.getOrderInfo(reservationID);
            for (int i = 0; i < order.size(); i++){
                if (!order.get(i).isServed()){
                    return false;
                }
            }
            return true;
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case OrdersView.SERVE:
                try{
                    //databaseConector.getOrderInfo(ordersMouseController.getIdReserva());
                    databaseConector.setServed(ordersView.getSelectedOrder(), ordersView.getSelectedReservation());
                    ArrayList<Order> updatedOrders = databaseConector.getOrderInfo(ordersMouseController.getIdReserva());
                    int size = updatedOrders.size();
                    boolean allServed = true;
                    for (int i = 0; i < size; i++){
                        if (!updatedOrders.get(i).isServed()){
                            allServed = false;
                            break;
                        }
                    }
                    if (!allServed){
                        setWestTable(ordersView.getSelectedReservationIndex());
                        ordersView.populateEastTable(databaseConector.getOrderInfo(ordersMouseController.getIdReserva()));
                    }else {
                        ordersView.createEmptyEastTable();
                        setWestTable(-1);
                    }
                }catch (DataBaseException | ArrayIndexOutOfBoundsException de){
                    ordersView.showPopError("No hi ha cap plat de comanda seleccionada!");
                }

                break;
        }
    }

}
