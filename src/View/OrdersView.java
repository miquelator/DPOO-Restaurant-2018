package View;

import Controller.WindowAdapter.OrderWindowClosing;
import Controller.ActionListener.OrdersController;
import Controller.MouseController.OrdersMouseController;
import Model.JTableModel;
import Model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class OrdersView extends JFrame{
    private JButton serve;
    private JLabel tableLabel;
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
        tableLabel = new JLabel("Taula numero -");
        east.add(eastScroll, BorderLayout.CENTER);
        JPanel southEast = new JPanel(new GridLayout(1,2));
        JPanel tableLabelAux = new JPanel();
        tableLabelAux.add(serve);
        tableLabelAux.add(tableLabel);
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

    private void createEmptyEastTable() {
        DefaultTableModel model = (DefaultTableModel) eastTable.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("Plat");
        model.addColumn("Hora de comanda");
        model.addColumn("Servit");
    }

    public void populateWestTable(ArrayList<Integer> orders) {
        DefaultTableModel model = (DefaultTableModel) westTable.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
        westTable.setRowHeight(30);
        westTable.setFont(new Font("Serif", Font.BOLD, 20));

        model.addColumn("Comanda");

        int size = orders.size();
        for (int i = 0; i < size; i++){
            Vector<String> preuPlat = new Vector(Arrays.asList(orders.get(i)));
            System.out.println(orders.get(i));
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

    public void registerListeners(OrdersController controller, OrdersMouseController ordersMouseController, OrderWindowClosing windowListener){
        serve.addActionListener(controller);
        westTable.addMouseListener(ordersMouseController);
        this.addWindowListener(windowListener);
    }

    public int getSelectedReservation() {

        return Integer.parseInt(westTable.getValueAt(westTable.getSelectedRow(), 0).toString().split("nº ")[1]);
    }

    public void populateEastTable(ArrayList<Order> ordersInfo) {
        DefaultTableModel model = (DefaultTableModel) eastTable.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("Plat");
        model.addColumn("Hora de comanda");
        model.addColumn("Servit");

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

    public int getSelectedOrder() {
        return eastTable.getSelectedRow() + 1;
    }

    public void updateTableLabel(int reserva) {
        tableLabel.setText("Taula numero " + reserva);
    }

    public void showPopError(String message) {
        String[] options = { "OK" };
        JOptionPane.showOptionDialog(this, message, "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);

    }
}
