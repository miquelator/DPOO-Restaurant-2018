package Controller.MouseController;

import Exceptions.DataBaseException;
import Model.DatabaseConector;
import Model.Order;
import View.OrdersView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class OrdersMouseController implements MouseListener {
    private OrdersView ordersView;
    private DatabaseConector databaseConector;
    private int reserva;

    public OrdersMouseController(OrdersView ordersView, DatabaseConector databaseConector) {
        this.ordersView = ordersView;
        this.databaseConector = databaseConector;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
            try {
                reserva = ordersView.getSelectedReservation();
                ordersView.populateEastTable(databaseConector.getOrderInfo(reserva));
            } catch (DataBaseException de) {
                ordersView.showPopError(de.getMessage());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Gets the id from a reservation
     * @return
     */
    public int getIdReserva() {
        return reserva;
    }

    /**
     * Redraws view's table with new info
     */
    public void updateTables() {
        try {
            int selected = ordersView.getSelectedReservationIndex();
            ArrayList<Integer> orders = databaseConector.getReservation();
            ArrayList<Integer> servedOrders = new ArrayList<>();
            ArrayList<Integer> notServedOrders = new ArrayList<>();
            int size = orders.size();
            for (int i = 0; i < size; i++){
                System.out.print("La reserva amb id " + orders.get(i));
                if (allServed(orders.get(i))){
                    System.out.println(" te totes les comandes servides.");
                    servedOrders.add(orders.get(i));
                }else {
                    System.out.println(" no te totes les comandes servides.");
                    notServedOrders.add(orders.get(i));
                }
            }
            ordersView.populateWestTable(servedOrders, notServedOrders);
            ordersView.setSelectedReservationIndex(selected);
        } catch (DataBaseException de) {
            ordersView.showPopError(de.getMessage());
        }catch (IllegalArgumentException ignored){

        }

        try {
            ordersView.populateEastTable(databaseConector.getOrderInfo(reserva));
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
}
