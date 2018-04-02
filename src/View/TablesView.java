package View;

import Controller.TablesChangeController;
import Controller.TablesController;
import Model.JTableModel;
import Model.Taula;
import com.sun.xml.internal.bind.v2.TODO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

public class TablesView extends JFrame {
    private JTabbedPane tabbedPane;
    private JButton addTable;
    private JButton showTablesReservations;
    private JButton deleteTables;
    private JButton exit;
    private JComboBox idABorrar;
    private JTableModel jTableModel;
    private JTable listOfTables;

    public final static String ADD_TABLE = "Afegir una taula";
    public final static String SHOW_TABLE_RESERVATIONS = "Mostrar reserves de taules";
    public final static String DELETE_TABLE = "Borrar taula";
    public final static String EXIT = "Sortir";

    public TablesView() {
        populateView();
        setSize(700, 500);
        setTitle("Gestor de taules");
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
        //afegim 3 elements al JTabbedPane
        JPanel jplInnerPanel1 = createInnerPanel(addTable());
        tabbedPane.addTab("Afegir nova taula", icon, jplInnerPanel1, "Tab 1");

        JPanel jplInnerPanel2 = createInnerPanel(listTable());
        tabbedPane.addTab("Mostar llistat taules", icon, jplInnerPanel2, "Tab 2");

        JPanel jplInnerPanel3 = createInnerPanel(showReservations());
        tabbedPane.addTab("Mostrar reserves", icon, jplInnerPanel3, "Tab 3");

        JPanel jplInnerPanel4 = createInnerPanel(deleteTable());
        tabbedPane.addTab("Eliminar taula", icon, jplInnerPanel4, "Tab 4");

        //add(tabbedPane);

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


    private JPanel deleteTable() {
        JPanel deleteTablePanel = new JPanel(new FlowLayout());
        //TODO: RECUPERAR DEL SERVIDOR UNA LLISTA DELS ID DE LES TAULES I POSAR-LES EN UN JCOMBOBOX
        String[] exempleABorrar = {"1", "2", "3", "4", "5"};
        idABorrar = new JComboBox<>(exempleABorrar);
        deleteTables = new JButton(DELETE_TABLE);
        deleteTablePanel.add(idABorrar);
        deleteTablePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        deleteTablePanel.add(deleteTables);
        return deleteTablePanel;
    }

    private JPanel showReservations() {
        JPanel showBookingPanel = new JPanel();
        showTablesReservations = new JButton(SHOW_TABLE_RESERVATIONS);
        showBookingPanel.add(showTablesReservations);
        return showBookingPanel;
    }

    private JPanel listTable() {
        //TODO: FER QUE LA TAULA S'EXPANDEIXI AL MAXIM (BORDER LAYOUT NO VA...)
        JPanel listTablePanel = new JPanel();
        jTableModel = new JTableModel();
        listOfTables = new JTable(jTableModel);
        listOfTables.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(listOfTables);
        listTablePanel.add(scrollPane);
        return listTablePanel;
    }

    private JPanel addTable() {
        JPanel addTablePanel = new JPanel();
        addTable = new JButton(ADD_TABLE);
        addTablePanel.add(addTable);
        return addTablePanel;
    }

    /**
     * Adds a listener to view's components
     * @param tablesController ActionListener controller
     * @param tablesChangeController
     */
    public void registerListeners(TablesController tablesController, TablesChangeController tablesChangeController) {
        addTable.addActionListener(tablesController);
    //    showTablesReservations.addActionListener(tablesController);
        deleteTables.addActionListener(tablesController);
        exit.addActionListener(tablesController);
        tabbedPane.addChangeListener(tablesChangeController);
    }

    public int getTabbedPaneWindow() {
        return tabbedPane.getSelectedIndex();
    }

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
}
