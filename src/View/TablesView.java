package View;

import Controller.TablesController;
import Model.Taula;
import com.sun.xml.internal.bind.v2.TODO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

public class TablesView extends JFrame {
    private JTabbedPane tabbedPane;
    private JButton addTable;
    private JButton showTables;
    private JButton showTablesReservations;
    private JButton deleteTables;
    private JButton exit;
    private JComboBox idABorrar;

    public final static String ADD_TABLE = "Afegir una taula";
    public final static String SHOW_TABLE_LIST = "Mostrar llistat de taules";
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
        tabbedPane.addTab("Mostar llistat taules", icon, jplInnerPanel2);

        JPanel jplInnerPanel3 = createInnerPanel(showReservations());
        tabbedPane.addTab("Mostrar reserves", icon, jplInnerPanel3, "Tab 3");

        JPanel jplInnerPanel4 = createInnerPanel(deleteTable());
        tabbedPane.addTab("Eliminar taula", icon, jplInnerPanel4, "Tab 4");

        setLayout(new GridLayout(1, 1));
        add(tabbedPane);

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
        JPanel listTablePanel = new JPanel();
        showTables = new JButton(SHOW_TABLE_LIST);
        listTablePanel.add(showTables);
        //JTable listOfTables = new JTable();
        //fillJTable(listOfTables);
        return listTablePanel;
    }

    private JPanel addTable() {
        JPanel addTablePanel = new JPanel();
        addTable = new JButton(ADD_TABLE);
        addTablePanel.add(addTable);
        return addTablePanel;
    }
/*
    private void fillJTable(JTable listOfTables){
        //creating data to add into the JTable. Here you might want to import your proper data from elsewhere
        Date date = new Date();

        UserReplay rep1 = new UserReplay(date, 12, 13,14);
        UserReplay rep2 = new UserReplay(date, 2,34,5);

        ArrayList<UserReplay> usuaris = new ArrayList<>();
        usuaris.add(rep1);
        usuaris.add(rep2);
//----Filling Jtable------
        DefaultTableModel model = (DefaultTableModel) listOfTables.getModel();
        model.addColumn("Fecha");
        model.addColumn("Puntuación");
        model.addColumn("Tiempo de duración");
        model.addColumn("Pico máximo de espectadores");

        for (int i = 0; i < usuaris.size(); i++){
            Vector<Date> fecha = new Vector<>(Arrays.asList(usuaris.get(i).getDate()));
            Vector<Integer> puntuacion = new Vector<>(Arrays.asList(usuaris.get(i).getPuntuacion()));
            Vector<Integer> tiempo = new Vector<>(Arrays.asList(usuaris.get(i).getTiempo()));
            Vector<Integer> espectadors = new Vector<>(Arrays.asList(usuaris.get(i).getTiempo()));

            Vector<Object> row = new Vector<Object>();
            row.addElement(fecha.get(0));
            row.addElement(puntuacion.get(0));
            row.addElement(tiempo.get(0));
            row.addElement(espectadors.get(0));
            model.addRow(row);
        }

    }
*/

    /**
     * Adds a listener to view's components
     * @param tablesController ActionListener controller
     */
    public void registerListeners(TablesController tablesController) {
        addTable.addActionListener(tablesController);
        showTables.addActionListener(tablesController);
    //    showTablesReservations.addActionListener(tablesController);
        deleteTables.addActionListener(tablesController);
        exit.addActionListener(tablesController);
    }

    public void updateTables(ArrayList<Taula> aux) {

        //TODO: QUE NO ES PUGUI EDITAR LA PUTA TAULA
        JTable listOfTables = new JTable(0, 3);
        DefaultTableModel model = (DefaultTableModel) listOfTables.getModel();
        model.addRow(new Object[]{"ID", "Nombre de seients", "Ocupada"});
        for (Taula t: aux){
            System.out.println("id: " + t.getIdTaula());
            System.out.println("num_seients: " + t.getNumSeients());
            System.out.println("ocupada: " + t.isOcupada());
            model.addRow(new Object[]{t.getIdTaula(), t.getNumSeients(), t.isOcupada()});
        }

        JPanel botoProvisional = new JPanel(new BorderLayout());
        botoProvisional.add(listOfTables, BorderLayout.CENTER);
        botoProvisional.add(showTables, BorderLayout.SOUTH);

        tabbedPane.setComponentAt(1,botoProvisional);

        //fillJTable(listOfTables);

    }
}
