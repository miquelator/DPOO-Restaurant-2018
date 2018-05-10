package View;

import Controller.OrdersController;
import Model.Carta;
import Model.CartaSelection;
import Model.JTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class OrdersView extends JFrame{
    private OrdersController controller;

    private JButton serve;
    private JSplitPane jSplitPane;
    private JTable eastTable;
    private JTable westTable;

    private ArrayList<JButton> arrayButtons;

    public final static String SERVE = "Serveix";
    public final static String ADD = "Afegeix";

    public OrdersView(){
        setSize(1000,500);
        setTitle("Gestor de comandes");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
        JPanel southEast = new JPanel(new GridLayout(1,1));
        JPanel doOrderAux = new JPanel();
        doOrderAux.add(serve);
        southEast.add(doOrderAux);
        east.add(southEast, BorderLayout.SOUTH);

        westTable = new JTable(new JTableModel());
        JScrollPane westScroll = new JScrollPane(westTable);
        createEmptyWestTable();

        jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, westScroll, east);
        jSplitPane.setDividerLocation(500);
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

    public void populateWestTable() {

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


    public void drawInfo(ArrayList<Carta> cartes, int tab) {
        try{
            JPanel left = new JPanel(new GridLayout(cartes.size(),1));
            arrayButtons = new ArrayList<>();
            for (Carta carta :cartes){
                left.add(createMenuRow(carta));
            }
           // carta.setComponentAt(tab, left);
            setContentPane(jSplitPane);
            updateControllers();
        }catch (NullPointerException ignored){
        }
    }

    private JPanel createMenuRow(Carta carta) {
        JPanel menuRow = new JPanel();
        JLabel itemName = new JLabel(carta.getNomPlat());
        JPanel rightSideMenuRow = new JPanel(new GridLayout(1,2));
        JPanel quantitatMenuRow = new JPanel(new BorderLayout());
        JButton addButton = new JButton(ADD);
        addButton.setActionCommand(ADD + "#" + carta.getNomPlat());
        JLabel price = new JLabel(String.valueOf(carta.getPreu()) + " €");
        JLabel quantitat = new JLabel(String.valueOf("Disponibles: "+carta.getQuantitat()));
        rightSideMenuRow.add(price);
        quantitatMenuRow.add(quantitat, BorderLayout.CENTER);

        rightSideMenuRow.add(addButton);
        menuRow.add(itemName);
        menuRow.add(rightSideMenuRow);
        menuRow.add(quantitatMenuRow);

        arrayButtons.add(addButton);
        return menuRow;
    }

    private void updateControllers(){
        for (JButton buttons: arrayButtons){
            buttons.addActionListener(controller);
        }
    }

    public void registerListeners(OrdersController controller){
        this.controller = controller;
        serve.addActionListener(controller);
    }

    
    public void addDishToOrder(ArrayList<CartaSelection> selectedItems) {

        DefaultTableModel model = (DefaultTableModel) eastTable.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("Nom del plat");
        model.addColumn("Nombre d'unitats");
        model.addColumn("Preu unitari");

        for (int i = 0; i < selectedItems.size(); i++){
            Vector<String> nomPlat = new Vector(Arrays.asList(selectedItems.get(i).getNomPlat()));
            Vector<String> unitats = new Vector(Arrays.asList(selectedItems.get(i).getUnitatsDemanades()));
            Vector<String> preuPlat = new Vector(Arrays.asList(selectedItems.get(i).getPreu()));

            Vector<Object> row = new Vector<Object>();
            row.addElement(nomPlat.get(0));
            row.addElement(unitats.get(0));
            row.addElement(String.valueOf(preuPlat.get(0)) + "€");
            model.addRow(row);
        }
    }

    public CartaSelection getSelectedOrder() {
        try {
            return new CartaSelection(eastTable.getModel().getValueAt(eastTable.getSelectedRow(), 0).toString(),
                    Float.parseFloat(eastTable.getModel().getValueAt(eastTable.getSelectedRow(), 2).toString().replace("€", "")),
                    Integer.parseInt(eastTable.getModel().getValueAt(eastTable.getSelectedRow(), 1).toString()),
                    Float.parseFloat(eastTable.getModel().getValueAt(eastTable.getSelectedRow(), 3).toString().replace("€", "")));

        }catch (ArrayIndexOutOfBoundsException ignored){

        }
        return null;
    }

    public int getSelectedOrderIndex() {
        return  eastTable.getSelectedRow();
    }

    /**
     * Sets the specified row to selected status
     * @param selected Row to be marked as selected
     */
    public void setSelectedRow(int selected) {
        eastTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        eastTable.setColumnSelectionAllowed(false);
        eastTable.setRowSelectionAllowed(true);
        eastTable.setRowSelectionInterval(selected, selected);
    }

    public void showError(String message, String titol){
        JOptionPane.showMessageDialog(null,
                message,
                titol,
                JOptionPane.ERROR_MESSAGE);
    }

    public void informOrderDone() {
        String[] options = { "Ok" };
        JOptionPane.showOptionDialog(this, "Comanda realitzada!",
                "Notice", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);
    }

}
