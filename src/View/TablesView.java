package View;

import Controller.TablesController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TablesView extends JFrame {
    private JButton addTable;
    private JButton showTableList;
    private JButton showTablesReservataions;
    private JButton deleteTables;
    private JButton exit;

    public final static String ADD_TABLE = "Afegir una taula";
    public final static String SHOW_TABLE_LIST = "Mostrar llistat de taules";
    public final static String SHOW_TABLE_RESERVATIONS = "Mostrar reserves de taules";
    public final static String DELETE_TABLE = "Borrar una taula";
    public final static String EXIT = "Sortir";

    public TablesView() {
        populateView();
        setSize(700, 700);
        setTitle("Gestor de taules");
        setLocationRelativeTo(null);
        Dimension dimension = new Dimension();
        dimension.height = 600;
        dimension.width = 600;
        setMinimumSize(dimension);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Draws the view
     */
    private void populateView() {
        JPanel principal = new JPanel(new GridLayout(0,1,10,10));
        principal.setBorder(new EmptyBorder(20,30,20,30));

        JLabel title = new JLabel("Servidor del restaurant");
        title.setFont(title.getFont().deriveFont(45.0f));
        title.setHorizontalAlignment(JLabel.CENTER);

        addTable = new JButton(ADD_TABLE);
        showTableList = new JButton(SHOW_TABLE_LIST);
        showTablesReservataions = new JButton(SHOW_TABLE_RESERVATIONS);
        deleteTables = new JButton(DELETE_TABLE);
        exit = new JButton(EXIT);

        addTable.setFont(new Font("Arial", Font.PLAIN, 30));
        showTableList.setFont(new Font("Arial", Font.PLAIN, 30));
        showTablesReservataions.setFont(new Font("Arial", Font.PLAIN, 30));
        deleteTables.setFont(new Font("Arial", Font.PLAIN, 30));
        exit.setFont(new Font("Arial", Font.PLAIN, 30));

        principal.add(title);
        principal.add(addTable);
        principal.add(showTableList);
        principal.add(showTablesReservataions);
        principal.add(deleteTables);
        principal.add(exit);

        this.add(principal);
        this.pack();
    }

    /**
     * Adds a listener to view's components
     * @param tablesController ActionListener controller
     */
    public void registerListeners(TablesController tablesController) {
        addTable.addActionListener(tablesController);
        showTableList.addActionListener(tablesController);
        showTablesReservataions.addActionListener(tablesController);
        deleteTables.addActionListener(tablesController);
        exit.addActionListener(tablesController);
    }
}
