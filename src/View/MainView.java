package View;

import Controller.MainController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainView extends JFrame {
    private JButton tables;
    private JButton menu;
    private JButton orders;
    private JButton topFive;
    private JButton exit;

    public final static String MANAGE_TABLES = "Gestionar taules";
    public final static String MANAGE_MENU = "Gestionar carta";
    public final static String MANAGE_ORDERS = "Gestionar comandes";
    public final static String SHOW_TOP_FIVE = "Mostra top 5";
    public final static String EXIT = "Sortir";

    private JButton open, process;
    private JLabel center;
    private JScrollPane scroll;
    private JPanel box;


    public MainView() {
        populateView();
        setSize(700, 700);
        setTitle("Administrator view");
        setLocationRelativeTo(null);
        Dimension dimension = new Dimension();
        dimension.height = 600;
        dimension.width = 600;
        setMinimumSize(dimension);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void populateView() {
        JPanel principal = new JPanel(new GridLayout(0,1,10,10));
        principal.setBorder(new EmptyBorder(20,30,20,30));

        JLabel title = new JLabel("Servidor del restaurant");
        title.setFont(title.getFont().deriveFont(45.0f));
        title.setHorizontalAlignment(JLabel.CENTER);

        tables = new JButton(MANAGE_TABLES);
        menu = new JButton(MANAGE_MENU);
        orders = new JButton(MANAGE_ORDERS);
        topFive = new JButton(SHOW_TOP_FIVE);
        exit = new JButton(EXIT);

        tables.setFont(new Font("Arial", Font.PLAIN, 30));
        menu.setFont(new Font("Arial", Font.PLAIN, 30));
        orders.setFont(new Font("Arial", Font.PLAIN, 30));
        topFive.setFont(new Font("Arial", Font.PLAIN, 30));
        exit.setFont(new Font("Arial", Font.PLAIN, 30));

        principal.add(title);
        principal.add(tables);
        principal.add(menu);
        principal.add(orders);
        principal.add(topFive);
        principal.add(exit);

        this.add(principal);
        this.pack();
    }

    public void registerListeners(MainController mainController) {
        tables.addActionListener(mainController);
        menu.addActionListener(mainController);
        orders.addActionListener(mainController);
        topFive.addActionListener(mainController);
        exit.addActionListener(mainController);
    }

}
