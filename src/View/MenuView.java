package View;

import Controller.MenuChangeController;
import Controller.MenuController;
import Model.Carta;
import Model.JTableModel;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class MenuView extends JFrame {

    private JTabbedPane tabbedPane;
    private JButton addDish;
    private JButton deleteDish;
    private JButton exit;
    private JComboBox idABorrar;
    private JTableModel jTableModel;
    private JTable listOfDishes;
    private JTextField dishName;
    private JTextField dishPrice;
    private JTextField dishStock;


    public final static String ADD_DISH = "Afegir un plat";
    public final static String DELETE_DISH = "Eliminar un plat";
    public final static String ADD_STOCK = "Afegir existencies";
    public final static String EXIT = "Sortir";

    public MenuView() {
        populateView();
        setSize(700, 500);
        setTitle("Gestor de la carta");
        setLocationRelativeTo(null);
        Dimension dimension = new Dimension();
        dimension.height = 400;
        dimension.width = 600;
        setMinimumSize(dimension);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Draws the view
     */
    private void populateView() {
        JPanel principal = new JPanel(new BorderLayout());

        JPanel bottom = new JPanel(new BorderLayout());
        JPanel aux = new JPanel();
        exit = new JButton(EXIT);
        aux.add(exit);
        bottom.add(aux, BorderLayout.CENTER);
        setContentPane(bottom);

        //creem el jTabbedPane
        ImageIcon icon = new ImageIcon("java-swing-tutorial.JPG");
        tabbedPane = new JTabbedPane();


        JPanel jplInnerPanel1 = createInnerPanel(addDish());
        tabbedPane.addTab("Afegir nou plat", icon, jplInnerPanel1, "Tab 1");

        JPanel jplInnerPanel2 = createInnerPanel(new JPanel());
        tabbedPane.addTab("Mostar la carta", icon, jplInnerPanel2, "Tab 2");
        listDish();

        JPanel jplInnerPanel3 = createInnerPanel(deleteDish());
        tabbedPane.addTab("Esborrar un plat del menu", icon, jplInnerPanel3, "Tab 3");

        JPanel jplInnerPanel4 = createInnerPanel(deleteDish());
        tabbedPane.addTab("Actualitzar existències", icon, jplInnerPanel4, "Tab 4");


        principal.add(bottom, BorderLayout.SOUTH);
        principal.add(tabbedPane, BorderLayout.CENTER);
        setContentPane(principal);
    }

    private JPanel createInnerPanel(JPanel innerPane) {
        JPanel jplPanel = new JPanel();
        jplPanel.setLayout(new GridBagLayout());
        jplPanel.add(innerPane);
        return jplPanel;
    }


    private JPanel deleteDish() {
        JPanel deleteTablePanel = new JPanel(new FlowLayout());
        //TODO: RECUPERAR DEL SERVIDOR UNA LLISTA DELS ID NOMS DELS PLATS I POSAR-LES EN UN JCOMBOBOX
        String[] exempleABorrar = {"1", "2", "3", "4", "5"};
        idABorrar = new JComboBox<>(exempleABorrar);
        deleteDish = new JButton(DELETE_DISH);
        deleteTablePanel.add(idABorrar);
        deleteTablePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        deleteTablePanel.add(deleteDish);
        return deleteTablePanel;
    }


    private void listDish() {
        jTableModel = new JTableModel();
        listOfDishes = new JTable(jTableModel);
        listOfDishes.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(listOfDishes);
        tabbedPane.setComponentAt(1, scrollPane);
    }

    private JPanel addDish() {
        JPanel addDishPanel = new JPanel(new BorderLayout());
        JPanel center = new JPanel(new GridLayout(3,2));
        JPanel namePanel = new JPanel(new BorderLayout());
        dishName = new JTextField();
        namePanel.add(dishName, BorderLayout.CENTER);
        JPanel pricePanel = new JPanel(new BorderLayout());
        dishPrice = new JTextField();
        pricePanel.add(dishPrice, BorderLayout.CENTER);
        JPanel stockPanel = new JPanel(new BorderLayout());
        dishStock = new JTextField();
        stockPanel.add(dishStock, BorderLayout.CENTER);
        addDish = new JButton(ADD_DISH);

        center.add(new JLabel("Nom: "));
        center.add(namePanel);
        center.add(new JLabel("Preu: "));
        center.add(pricePanel);
        center.add(new JLabel("Quantitat: "));
        center.add(stockPanel);

        addDishPanel.add(center, BorderLayout.CENTER);
        addDishPanel.add(addDish, BorderLayout.SOUTH);
        return addDishPanel;
    }


    public int getTabbedPaneWindow() {
        return tabbedPane.getSelectedIndex();
    }

    public void updateMenu(ArrayList<Carta> aux) {
        DefaultTableModel model = (DefaultTableModel) listOfDishes.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("ID");
        model.addColumn("Nom del plat");
        model.addColumn("Preu");
        model.addColumn("Quantitat disponible");


        for (int i = 0; i < aux.size(); i++){
            Vector<String> ID = new Vector(Arrays.asList(aux.get(i).getIdPlat()));
            Vector<String> nombreSeients = new Vector(Arrays.asList(aux.get(i).getNomPlat()));
            Vector<String> ocupada = new Vector(Arrays.asList(aux.get(i).getPreu()));
            Vector<String> quantitat = new Vector(Arrays.asList(aux.get(i).getQuantitat()));


            Vector<Object> row = new Vector<Object>();
            row.addElement(ID.get(0));
            row.addElement(nombreSeients.get(0));
            row.addElement(ocupada.get(0));
            row.addElement(quantitat.get(0));
            model.addRow(row);
        }
    }

    public void confirmEntry(boolean found){
        String[] options = { "OK" };
        if (found == true){
            JOptionPane.showOptionDialog(this, "El plat ja existeix!", "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
        }else{
            JOptionPane.showOptionDialog(this, "Plat inserit correctament", "INFORMATION", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        }
    }

    public String getNewDishName() {
        return dishName.getText();
    }

    public String getNewDishPrice() {
        return dishPrice.getText();
    }

    public String getNewDishStock() {
        return dishStock.getText();
    }

    /**
     * Adds a listener to view's components
     * @param menuController ActionListener controller
     * @param menuChangeController
     */
    public void registerListeners(MenuController menuController, MenuChangeController menuChangeController) {
        addDish.addActionListener(menuController);
        deleteDish.addActionListener(menuController);
        exit.addActionListener(menuController);
        tabbedPane.addChangeListener(menuChangeController);
    }
}
