package View;

import Controller.OrdersControllers;
import Model.JTableModel;
import Model.Taula;
import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class OrdersView extends JFrame {
    private JButton exit;
    private JTable ordersTable;

    private final static String EXIT = "Sortir";

    public OrdersView(){
        populateView();
        setSize(700, 500);
        setTitle("Gestor de la carta");
        setLocationRelativeTo(null);
        Dimension dimension = new Dimension();
        dimension.height = 400;
        dimension.width = 600;
        setMinimumSize(dimension);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void populateView() {
        JPanel principal = new JPanel(new BorderLayout());
        JPanel center = new JPanel();
        ordersTable = new JTable(new JTableModel());
        center.add(ordersTable);
        JPanel south = new JPanel();
        JPanel southAux = new JPanel();
        exit = new JButton(EXIT);
        southAux.add(exit);
        south.add(southAux);
        principal.add(center, BorderLayout.CENTER);
        principal.add(south, BorderLayout.SOUTH);
    }

    public void displayOrders(ArrayList<Taula> aux) {
        DefaultTableModel model = (DefaultTableModel) ordersTable.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("Reserva");
        model.addColumn("Nombre de plats en la comanda");
        model.addColumn("Nombre de plats a servir");
        model.addColumn("Data de la comanda");

        for (int i = 0; i < aux.size(); i++){
            Vector<String> ID = new Vector(Arrays.asList(aux.get(i).getIdTaula()));
            Vector<String> nombreSeients = new Vector(Arrays.asList(aux.get(i).getNumSeients()));
            Vector<String> ocupada = new Vector(Arrays.asList(aux.get(i).isOcupada()));

            Vector<Object> row = new Vector<Object>();
            row.addElement(ID.get(0));
            row.addElement(nombreSeients.get(0));
            row.addElement(ocupada.get(0));
            model.addRow(row);
        }
    }

    public void registerListeners(OrdersControllers ordersControllers) {
        exit.addActionListener(ordersControllers);
    }
}
