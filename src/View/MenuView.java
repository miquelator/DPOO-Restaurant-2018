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


    //TODO: ANGEL, JA VA TOT LO DE CARTA, PERO LES VISTES SON UNA MERDA, ME LES POTS ARREGLAR AL FINAL QUAN PUGUIS???
    private JTabbedPane tabbedPane;
    private JButton addDish;
    private JButton deleteDish;
    private JButton updateDish;
    private JButton exit;
    private JComboBox idABorrar;
    private JComboBox idStock;
    private JTextField numStock;
    private JTableModel jTableModel;
    private JTable listOfDishes;
    private JTextField dishName;
    private JTextField dishPrice;
    private JTextField dishStock;


    public final static String ADD_DISH = "Afegir un plat";
    public final static String DELETE_DISH = "Eliminar un plat";
    public final static String UPDATE_STOCK = "Actualitza existencies";
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
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
        tabbedPane.addTab("Afegir nou plat", icon, jplInnerPanel1);

        JPanel jplInnerPanel2 = createInnerPanel(new JPanel());
        tabbedPane.addTab("Mostar la carta", icon, jplInnerPanel2);
        listDish();

        //TODO: FER-HO NUMBER SPINNER
        JPanel jplInnerPanel3 = createInnerPanel(deleteDish());
        tabbedPane.addTab("Esborrar un plat del menu", icon, jplInnerPanel3);

        //TODO: FER-HO NUMBER SPINNER
        JPanel jplInnerPanel4 = createInnerPanel(updateDishStock());
        tabbedPane.addTab("Actualitzar existències", icon, jplInnerPanel4);


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
        JPanel deleteDishPanel = new JPanel(new FlowLayout());
        deleteDish = new JButton(DELETE_DISH);
        deleteDishPanel.add(deleteDish);
        return deleteDishPanel;
    }

    private JPanel updateDishStock() {
        JPanel updateStockPanel = new JPanel();
        updateDish = new JButton(UPDATE_STOCK);
        updateStockPanel.add(updateDish);
        return updateStockPanel;
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
        model.addColumn("Tipus");
        model.addColumn("Preu");
        model.addColumn("Quantitat disponible");


        for (int i = 0; i < aux.size(); i++){
            Vector<String> ID = new Vector(Arrays.asList(aux.get(i).getIdPlat()));
            Vector<String> nombPlat = new Vector(Arrays.asList(aux.get(i).getNomPlat()));
            Vector<String> tipus = new Vector(Arrays.asList(aux.get(i).getTipus()));
            Vector<String> preu = new Vector(Arrays.asList(aux.get(i).getPreu()));
            Vector<String> quantitat = new Vector(Arrays.asList(aux.get(i).getQuantitat()));


            Vector<Object> row = new Vector<Object>();
            row.addElement(ID.get(0));
            row.addElement(nombPlat.get(0));
            row.addElement(tipus.get(0));
            row.addElement(preu.get(0));
            row.addElement(quantitat.get(0));
            model.addRow(row);
        }
    }

    public void populateStock(String[] list) {
        // create panel type
        JPanel updateStockPanel = new JPanel(new BorderLayout());

        // create upper view objects
        idStock = new JComboBox<>(list);
        JLabel numStockLabel = new JLabel("Nou nombre d'unitats:");
        numStock = new JTextField();

        // create a grid bag layout and create the standard constraints
        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10,0,10,0);
        c.gridwidth = 3;

        // add stock
        center.add(idStock, c);

        // add label for choosing
        c.gridwidth = 1;
        c.gridy = 1;
        c.gridx = 0;
        c.ipadx = (10);
        center.add(numStockLabel, c);

        // add textfield
        c.gridwidth = 2;
        c.gridx = 1;
        center.add(numStock, c);


        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 3;
        center.add(updateDish, c);

        updateStockPanel.add(center, BorderLayout.CENTER);


        // add the tabed panel to the view
        tabbedPane.setComponentAt(3, updateStockPanel);
    }

    public void populateDelete(String[] list) {

        // set the panel and the layout and it's constraints
        JPanel deleteDishPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // create and add items
        idABorrar = new JComboBox<>(list);
        deleteDishPanel.add(idABorrar);
        c.insets = new Insets(0,10,0,0);
        deleteDishPanel.add(deleteDish, c);

        // add to the tabbed pane
        tabbedPane.setComponentAt(2, deleteDishPanel);
    }

    public String getUpdatedDishName() {
        return idStock.getSelectedItem().toString();
    }

    public String getUpdatedStock() {
        return numStock.getText();
    }

    public String getDeletedDishName() {
        return idABorrar.getSelectedItem().toString();
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

    public void confirmEntry(int error){
        String[] options = { "OK" };
        switch (error){
            case 1:
                JOptionPane.showOptionDialog(this, "El plat ja existeix!", "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
                break;
            case 2:
                JOptionPane.showOptionDialog(this, "Plat inserit correctament", "INFORMATION", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                break;
            case 3:
                JOptionPane.showOptionDialog(this, "El format del preu/quantitat és incorrecte!", "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
                break;
            case 4:
                JOptionPane.showOptionDialog(this, "El format de la quantitat és incorrecte!", "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
                break;
            case 5:
                JOptionPane.showOptionDialog(this, "Plat esborrat correctament", "INFORMATION", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                break;
            case 6:
                JOptionPane.showOptionDialog(this, "No es pot esborrar el plat per XXXXX", "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
                break;
            case 7:
                JOptionPane.showOptionDialog(this, "Quantitat d'existències actualitzada correctament", "INFORMATION", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                break;
            case 8:
                JOptionPane.showOptionDialog(this, "No es poden actualitzar les existències!", "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
                break;
            case 9:
                JOptionPane.showOptionDialog(this, "Format del nom del plat incorrecte!", "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
                break;
        }
    }

    public void formatError() {
        String[] options = { "OK" };
        JOptionPane.showOptionDialog(this, "El format del preu/quantitat és incorrecte!", "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
    }


    /**
     * Adds a listener to view's components
     * @param menuController ActionListener controller
     * @param menuChangeController
     */
    public void registerListeners(MenuController menuController, MenuChangeController menuChangeController) {
        addDish.addActionListener(menuController);
        deleteDish.addActionListener(menuController);
        updateDish.addActionListener(menuController);
        exit.addActionListener(menuController);
        tabbedPane.addChangeListener(menuChangeController);
    }
}
