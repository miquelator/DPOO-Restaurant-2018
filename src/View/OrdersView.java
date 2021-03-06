package View;

import Controller.WindowAdapter.OrderWindowClosing;
import Controller.ActionListener.OrdersController;
import Controller.MouseController.OrdersMouseController;
import Model.JTableModel;
import Model.Order;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import static java.awt.Color.*;

/**
 * Window to show the current orders that need serving
 */
public class OrdersView extends JFrame{
    private JButton serve;
    private JTable eastTable;
    private JTable westTable;


    public final static String SERVE = "Serveix";
    public final static String ADD = "Afegeix";

    public OrdersView(){
        setSize(1000,500);
        setTitle("Gestor de comandes");
        setLocationRelativeTo(null);
        populateView();
    }

    /**
     * Fills view with components
     */
    private void populateView() {
        JPanel east = new JPanel(new BorderLayout());
        eastTable = new JTable(new JTableModel());
        eastTable.getTableHeader().setReorderingAllowed(false);
        createEmptyEastTable();
        JScrollPane eastScroll = new JScrollPane(eastTable);
        serve = new JButton(SERVE);
        east.add(eastScroll, BorderLayout.CENTER);
        JPanel southEast = new JPanel(new GridLayout(1,2));
        JPanel tableLabelAux = new JPanel();
        tableLabelAux.add(serve);
        southEast.add(tableLabelAux);
        southEast.add(tableLabelAux);
        east.add(southEast, BorderLayout.SOUTH);

        westTable = new JTable(new JTableModel());
        JScrollPane westScroll = new JScrollPane(westTable);
        createEmptyWestTable();

        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, westScroll, east);
        jSplitPane.setDividerLocation(300);
        setContentPane(jSplitPane);
    }

    /**
     * Generates empty eastern side of view
     */
    public void createEmptyEastTable() {
        DefaultTableModel model = (DefaultTableModel) eastTable.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("Plat");
        model.addColumn("Hora de comanda");
        model.addColumn("Servit");
    }

    /**
     * Fills west side of view
     * @param servedOrders ArrayList Containing info from the servedOrders to fill the view with
     * @param notServedOrders ArrayList Containing info from the non servedOrders to fill the view with
     */
    public void populateWestTable(ArrayList<Integer> servedOrders, ArrayList<Integer> notServedOrders) {
        DefaultTableModel model = (DefaultTableModel) westTable.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
        westTable.setRowHeight(30);
        westTable.setFont(new Font("Serif", Font.BOLD, 20));

        model.addColumn("Comanda");

        int notServedOrdersSize = notServedOrders.size();
        for (int i = 0; i < notServedOrdersSize; i++){
            Vector<String> preuPlat = new Vector(Arrays.asList(notServedOrders.get(i)));
            //Exception in thread "AWT-EventQueue-0" java.lang.ArrayIndexOutOfBoundsException: 0 >= 0 (sense indicacio de quina linia)
            Vector<Object> row = new Vector<Object>();
            row.addElement("Comandes de la reserva nº " + String.valueOf(preuPlat.get(0)));
            model.addRow(row);
        }

        int servedOrdersSize = servedOrders.size();
        for (int i = 0; i < servedOrdersSize; i++){
            Vector<String> preuPlat = new Vector(Arrays.asList(servedOrders.get(i)));
            //Exception in thread "AWT-EventQueue-0" java.lang.ArrayIndexOutOfBoundsException: 0 >= 0 (sense indicacio de quina linia)
            Vector<Object> row = new Vector<Object>();
            row.addElement("Comandes de la reserva nº " + String.valueOf(preuPlat.get(0)));
            model.addRow(row);
        }
    }

    /**
     * Creates an empty table representing starting user's order
     */
    public void createEmptyWestTable() {
        DefaultTableModel model = (DefaultTableModel) westTable.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("Comanda");
    }

    /**
     * Adds controllers to this view
     * @param controller instance of OrdersController
     * @param ordersMouseController instance of OrdersMouseController
     * @param windowListener instance of OrderWindowClosing
     */
    public void registerListeners(OrdersController controller, OrdersMouseController ordersMouseController, OrderWindowClosing windowListener){
        serve.addActionListener(controller);
        westTable.addMouseListener(ordersMouseController);
        this.addWindowListener(windowListener);
    }

    /**
     * Gives selected reservation
     * @return index from the reservation
     */
    public int getSelectedReservation() {
        return Integer.parseInt(westTable.getValueAt(westTable.getSelectedRow(), 0).toString().split("nº ")[1]);
    }

    /**
     * Fills east side of view
     * @param ordersInfo ArrayList containing all data to fill the view with
     */
    public void populateEastTable(ArrayList<Order> ordersInfo) {
        DefaultTableModel model = (DefaultTableModel) eastTable.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("Plat");
        model.addColumn("Hora de comanda");
        model.addColumn("Servit");

        // since we’re using values of floats and boolean here, we need
        // to set the cell renderer for every column.

        eastTable.setDefaultRenderer(eastTable.getColumnClass(1), new MenuCellRenderer());

        int size = ordersInfo.size();
        for (int i = 0; i < size; i++) {
            Vector<String> idPlat = new Vector(Arrays.asList(ordersInfo.get(i).getNomPlat()));
            Vector<String> hora = new Vector(Arrays.asList(ordersInfo.get(i).getHora()));
            Vector<String> served = new Vector(Arrays.asList(ordersInfo.get(i).isServed()));

            Vector<Object> row = new Vector<Object>();
            row.addElement(idPlat.get(0));
            row.addElement(hora.get(0));
            row.addElement(String.valueOf(served.get(0)));
            model.addRow(row);
        }
    }

    /**
     * Gives the selected order
     * @return The selected order
     */
    public int getSelectedOrder() {
        return eastTable.getSelectedRow() + 1;
    }


    /**
     * Displays error message
     * @param message Error message to be shown
     */
    public void showPopError(String message) {
        String[] options = { "OK" };
        JOptionPane.showOptionDialog(this, message, "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);

    }


    public ArrayList<Integer> getViewedReservations() {
        ArrayList viewedReservations = new ArrayList();
        int size = westTable.getRowCount();
        for (int i = 0; i < size; i++){
            viewedReservations.add(westTable.getModel().getValueAt(i,0));
        }
        return viewedReservations;
    }

    /**
     * Gives selected reservation index from the JTable
     * @return index from the JTable
     */
    public int getSelectedReservationIndex() {
        return westTable.getSelectedRow();
    }

    /**
     * Sets specified input as the selected reservation index
     * @param selection Integer to be set
     * @throws IllegalArgumentException
     */
    public void setSelectedReservationIndex(int selection) throws IllegalArgumentException{
        westTable.setRowSelectionInterval(selection, selection);
    }
}
