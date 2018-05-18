package Controller.MouseController;

import Exceptions.DataBaseException;
import Model.DatabaseConector;
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
        if (e.getClickCount() == 2) {
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
            ordersView.populateWestTable(databaseConector.getReservation(), selected);
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
}
