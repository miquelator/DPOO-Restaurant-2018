package View;

import Controller.ChangeListener.MenuChangeController;
import Controller.ActionListener.MenuController;
import Controller.WindowAdapter.MenuWindowClosing;
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
    private JButton updateDish;
    private JButton exit;
    private JComboBox idABorrar;
    private JComboBox idStock;
    private JTable listOfDishes;
    private JTextField dishName;
    private JSpinner spinPrice;
    private JSpinner spinAmount;
    private JSpinner spinNewAmount;


    public final static String ADD_DISH = "Afegir un plat";
    public final static String DELETE_DISH = "Eliminar un plat";
    public final static String UPDATE_STOCK = "Actualitza existencies";
    public final static String EXIT = "Sortir";

    /**
     * Constructor without parameters of the class. Generates the view
     */
    public MenuView() {
        populateView();
        setSize(700, 500);
        setTitle("Gestor de la carta");
        setLocationRelativeTo(null);
        Dimension dimension = new Dimension();
        dimension.height = 400;
        dimension.width = 600;
        setMinimumSize(dimension);
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


        JPanel jplInnerPanel1 = addDish();
        tabbedPane.addTab("Afegir nou plat", icon, jplInnerPanel1);

        JPanel jplInnerPanel2 = createInnerPanel(new JPanel());
        tabbedPane.addTab("Mostar la carta", icon, jplInnerPanel2);
        listDish();

        JPanel jplInnerPanel3 = createInnerPanel(deleteDish());
        tabbedPane.addTab("Esborrar un plat del menu", icon, jplInnerPanel3);

        JPanel jplInnerPanel4 = createInnerPanel(updateDishStock());
        tabbedPane.addTab("Actualitzar existències", icon, jplInnerPanel4);

        principal.add(bottom, BorderLayout.SOUTH);
        principal.add(tabbedPane, BorderLayout.CENTER);
        setContentPane(principal);
    }

    /**
     * Fills a tabbed pane panel
     * @param innerPane Panel to be filled
     * @return panel with layouts created
     */
    private JPanel createInnerPanel(JPanel innerPane) {
        JPanel jplPanel = new JPanel();
        jplPanel.setLayout(new BorderLayout());
        jplPanel.add(innerPane, BorderLayout.CENTER);
        return jplPanel;
    }

    /**
     * Creates component for the delete of a dish
     * @return Panel with all components
     */
    private JPanel deleteDish() {
        JPanel deleteDishPanel = new JPanel(new FlowLayout());
        deleteDish = new JButton(DELETE_DISH);
        deleteDishPanel.add(deleteDish);
        return deleteDishPanel;
    }

    /**
     * Creates component for the update of a dish's stock
     * @return Panel with all components
     */
    private JPanel updateDishStock() {
        JPanel updateStockPanel = new JPanel();
        updateDish = new JButton("            "+
                UPDATE_STOCK
                + "            ");
        updateStockPanel.add(updateDish);
        return updateStockPanel;
    }

    private void listDish() {
        listOfDishes = new JTable(new JTableModel());
        listOfDishes.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(listOfDishes);
        tabbedPane.setComponentAt(1, scrollPane);
    }

    private JPanel addDish() {
        JPanel addDishPanel = new JPanel();

        addDishPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel jlName = new JLabel("Nom:   ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.insets = new Insets(5,50,5,0);
        addDishPanel.add(jlName, constraints);

        dishName = new JTextField("");
        constraints.gridx = 1;
        constraints.gridwidth = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5,0,5,50);
        addDishPanel.add(dishName, constraints);

        JLabel jlPrice = new JLabel("Preu:  ");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(5,50,5,0);
        addDishPanel.add(jlPrice, constraints);

        SpinnerModel modelPrice = new SpinnerNumberModel(1,0.01,Double.MAX_VALUE,0.1);
        spinPrice = new JSpinner(modelPrice);
        ((JSpinner.DefaultEditor) spinPrice.getEditor()).getTextField().setEditable(true);
        ((JSpinner.DefaultEditor) spinPrice.getEditor()).getTextField().setBackground(Color.white);
        spinPrice.setPreferredSize(new Dimension(2,20));
        constraints.gridx = 1;
        constraints.gridwidth = 4;
        constraints.insets = new Insets(5,0,5,50);
        addDishPanel.add(spinPrice, constraints);

        JLabel jlQuantity = new JLabel("Quantitat:  ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(5,50,5,0);
        addDishPanel.add(jlQuantity, constraints);

        SpinnerModel modelAmount = new SpinnerNumberModel(1,0,Integer.MAX_VALUE,1);
        spinAmount = new JSpinner(modelAmount);
        ((JSpinner.DefaultEditor) spinAmount.getEditor()).getTextField().setEditable(true);
        ((JSpinner.DefaultEditor) spinAmount.getEditor()).getTextField().setBackground(Color.white);
        spinAmount.setPreferredSize(new Dimension(2,20));
        constraints.gridx = 1;
        constraints.gridwidth = 4;
        constraints.insets = new Insets(5,0,5,50);
        addDishPanel.add(spinAmount, constraints);

        addDish = new JButton("            "
                + ADD_DISH
                + "            " );
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 5;
        constraints.insets = new Insets(5,50,5,50);
        addDishPanel.add(addDish, constraints);

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
        SpinnerModel modelNewAmount = new SpinnerNumberModel(1,0,Integer.MAX_VALUE,1);
        spinNewAmount = new JSpinner(modelNewAmount);
        ((JSpinner.DefaultEditor) spinNewAmount.getEditor()).getTextField().setEditable(true);
        ((JSpinner.DefaultEditor) spinNewAmount.getEditor()).getTextField().setBackground(Color.white);
        spinNewAmount.setPreferredSize(new Dimension(2,20));

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
        center.add(spinNewAmount, c);


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

    public int getUpdatedStock() {
        return Integer.parseInt(spinNewAmount.getValue().toString());
    }

    public String getDeletedDishName() {
        return idABorrar.getSelectedItem().toString();
    }

    public String getNewDishName() {
        return dishName.getText();
    }

    public double getNewDishPrice() {
        return Double.parseDouble(spinPrice.getValue().toString());
    }

    public int getNewDishStock() {
        return Integer.parseInt(spinAmount.getValue().toString());
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
     * @param windowListener
     */
    public void registerListeners(MenuController menuController, MenuChangeController menuChangeController, MenuWindowClosing windowListener) {
        addDish.setActionCommand(ADD_DISH);
        addDish.addActionListener(menuController);
        deleteDish.setActionCommand(DELETE_DISH);
        deleteDish.addActionListener(menuController);
        updateDish.setActionCommand(UPDATE_STOCK);
        updateDish.addActionListener(menuController);
        exit.setActionCommand(EXIT);
        exit.addActionListener(menuController);
        tabbedPane.addChangeListener(menuChangeController);
        this.addWindowListener(windowListener);
    }

    /***
     * This method clears the enter of a dish data
     */
    public void clearAddDish(){
        dishName.setText("");
        spinAmount.setValue(1);
        spinPrice.setValue(1.0);
    }

    public void showPopError(String message){
        String[] options = { "OK" };
        JOptionPane.showOptionDialog(this, message, "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
    }
}
