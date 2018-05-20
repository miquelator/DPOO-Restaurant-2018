package View;

import Controller.MouseController.SeatsSpinController;
import Controller.ChangeListener.TablesChangeController;
import Controller.ActionListener.TablesController;
import Controller.WindowAdapter.TableWindowClosing;
import Exceptions.ViewNullException;
import Model.JTableModel;
import Model.Reserva;
import Model.Taula;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

/**
 * Window class for the controlling tables section
 */

public class TablesView extends JFrame {
    private JTabbedPane tabbedPane;
    private JButton addTable;
    private JButton deleteTables;
    private JButton exit;
    private JComboBox idABorrar;
    private JTableModel jTableModel;
    private JTable listOfTables;
    private JTable listOfReserves;
    private JSpinner spinner;

    public final static String ADD_TABLE = "Afegir una taula";
    public final static String DELETE_TABLE = "Borrar taula";
    public final static String EXIT = "Sortir";
    public final static int MAX_SEATS = 10;

    public TablesView() {
        populateView();
        setSize(700, 500);
        setTitle("Gestor de taules");
        setLocationRelativeTo(null);
        Dimension dimension = new Dimension();
        dimension.height = 400;
        dimension.width = 600;
        setMinimumSize(dimension);
    }

    public Object getTableToAdd() {
        return spinner.getValue();
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


        JPanel jplInnerPanel1 = createInnerPanel(spinnerNumberSample());
        tabbedPane.addTab("Afegir nova taula", icon, jplInnerPanel1);


        JPanel jplInnerPanel2 = createInnerPanel(new JPanel());
        tabbedPane.addTab("Mostar llistat taules", icon, jplInnerPanel2);
        listTable();

        JPanel jplInnerPanel3 = createInnerPanel(new JPanel());
        tabbedPane.addTab("Mostrar reserves", icon, jplInnerPanel3);
        showReservations();

        JPanel jplInnerPanel4 = createInnerPanel(deleteTable());
        tabbedPane.addTab("Eliminar taula", icon, jplInnerPanel4);

        principal.add(bottom, BorderLayout.SOUTH);
        principal.add(tabbedPane, BorderLayout.CENTER);
        setContentPane(principal);
    }

    /**
     * Function that creates the spinner to select the number of seats of a new table
     * @return Panel with the spinner and its label
     */
    private JPanel spinnerNumberSample() {
        JPanel addTablePanel = new JPanel(new GridLayout(1,2));
        JPanel panel = new JPanel();
        SpinnerModel model = new SpinnerNumberModel(1,1,MAX_SEATS,1);
        spinner = new JSpinner(model);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setBackground(Color.white);
        Dimension d = new Dimension();
        d.width = 50;
        spinner.setPreferredSize(d);

        JLabel label1 = new JLabel("Nombre seients: ");
        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.add(label1, BorderLayout.WEST);
        panel1.add(spinner, BorderLayout.CENTER);
        panel.add(panel1);

        addTable = new JButton(ADD_TABLE);
        addTablePanel.add(panel);
        addTablePanel.add(addTable);
        return addTablePanel;
    }

    private JPanel createInnerPanel(JPanel innerPane) {
        JPanel jplPanel = new JPanel();
        jplPanel.setLayout(new GridBagLayout());
        jplPanel.add(innerPane);
        return jplPanel;
    }


    /**
     * Function that creates the combobox with the tables available to delete
     * @return Panel with the combobox
     */
    private JPanel deleteTable() {
        JPanel deleteTablePanel = new JPanel(new FlowLayout());
        idABorrar = new JComboBox<>();
        deleteTables = new JButton(DELETE_TABLE);
        deleteTablePanel.add(idABorrar);
        deleteTablePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        deleteTablePanel.add(deleteTables);
        return deleteTablePanel;
    }

    /**
     * Function that creates the table for the reservations
     */
    private void showReservations() {
        jTableModel = new JTableModel();
        listOfReserves = new JTable(jTableModel);
        listOfReserves.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(listOfReserves);
        tabbedPane.setComponentAt(2, scrollPane);
    }

    /**
     * Function that creates the table available
     */
    private void listTable() {
        jTableModel = new JTableModel();
        listOfTables = new JTable(jTableModel);
        listOfTables.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(listOfTables);
        tabbedPane.setComponentAt(1, scrollPane);
    }


    public int getTabbedPaneWindow() {
        return tabbedPane.getSelectedIndex();
    }

    /**
     * Method that updates the list and status of the available tables
     * @param aux array with the information of the tables
     */
    public void updateTables(ArrayList<Taula> aux) {
        DefaultTableModel model = (DefaultTableModel) listOfTables.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("ID");
        model.addColumn("Nombre seients");
        model.addColumn("Ocupada");

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

    /**
     * Method that inserts on the table the reserves and its information
     * @param reserves arraylist de reserves
     */
    public void mostraReserves(ArrayList<Reserva> reserves) {
        DefaultTableModel model = (DefaultTableModel) listOfReserves.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);

        model.addColumn("ID Taula");
        model.addColumn("Nom reserva");
        model.addColumn("Contrasenya");
        model.addColumn("Nombre comensals");
        model.addColumn("Data reserva");

        for (int i = 0; i < reserves.size(); i++){
            Vector<String> ID = new Vector(Arrays.asList(reserves.get(i).getIdTaula()));
            Vector<String> nomReserva = new Vector(Arrays.asList(reserves.get(i).getNomReserva()));
            Vector<String> password = new Vector(Arrays.asList(reserves.get(i).getPassword()));
            Vector<String> numComensals = new Vector(Arrays.asList(reserves.get(i).getNumComensals()));
            Vector<String> dataReserva = new Vector(Arrays.asList(reserves.get(i).getDataReserva()));

            Vector<Object> row = new Vector<Object>();
            row.addElement(ID.get(0));
            row.addElement(nomReserva.get(0));
            row.addElement(password.get(0));
            row.addElement(numComensals.get(0));
            row.addElement(dataReserva.get(0));
            model.addRow(row);
        }
    }

    /**
     * Displays error message on view
     * @param message Error message to be displayed
     */
    public void mostraErrorServidor(String message) {
        String[] options = { "OK" };
        JOptionPane.showOptionDialog(this, message,
                "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                null, options, options[0]);
    }

    /**
     * Displays information message on view
     * @param message Information message to be displayed
     */
    public void mostraInformacioServidor(String message) {
        String[] options = { "OK" };
        JOptionPane.showOptionDialog(this, message,
                "INFORMATION", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);
    }

    /**
     * Reseteja el nobre de seients del spinner
     */
    public void resetNumSeients() {
        spinner.setValue(1);
    }


    public int getNumSeients(){
        return (int) spinner.getValue();
    }

    /**
     * Actualitza els id de les taules
     * @param id
     */
    public void loadTablesID(ArrayList<Integer> id) {
        int size = id.size();
        idABorrar.removeAllItems();
        for (int i = 0; i < size; i++) {
            idABorrar.addItem(id.get(i));
        }
    }

    public int getTableToDelete() throws ViewNullException {
        try{
            return (int)idABorrar.getSelectedItem();

        }catch (NullPointerException e){
            throw new ViewNullException("No hi ha taules registrades!");
        }
    }
    /**
     * Displays warning about deleting tables when they are occupied
     * @return Boolean whether the user actually deletes them
     */
    public boolean showDeleteWarning() {
            Object[] options1 = { "Esborrar igualment", "Cancelar"};

            JPanel panel = new JPanel();
            panel.add(new JLabel("Existeixen reserves amb la taula que vol esborrar!"));

            int result = JOptionPane.showOptionDialog(null, panel, "Settings",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options1, null);
            if (result == JOptionPane.YES_OPTION){
                return true;
            }else{
                return false;
            }
    }


    /**
     * Adds a listener to view's components
     * @param tablesController ActionListener controller
     * @param tablesChangeController
     * @param seatsSpinController
     * @param windowListener
     */
    public void registerListeners(TablesController tablesController, TablesChangeController tablesChangeController, SeatsSpinController seatsSpinController, TableWindowClosing windowListener) {
        addTable.addActionListener(tablesController);
        deleteTables.addActionListener(tablesController);
        exit.addActionListener(tablesController);
        tabbedPane.addChangeListener(tablesChangeController);
        spinner.addMouseWheelListener(seatsSpinController);
        this.addWindowListener(windowListener);
    }


    public void addSeients(int i) {
        int old = (int) spinner.getValue();
        spinner.setValue(i + old);
    }
}
