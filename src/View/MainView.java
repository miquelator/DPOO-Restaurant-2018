package View;

import Controller.MainController;
import com.sun.javaws.util.JfxHelper;

import javax.swing.*;
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

    public MainView() {
        populateView();
        setSize(700, 700);
        setTitle("Administrator view");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void populateView() {
        JPanel principal = new JPanel(new GridLayout(6,1));

        JLabel title = new JLabel("Servidor del restaurant");
        title.setFont(title.getFont().deriveFont(40.0f));
        title.setHorizontalAlignment(JLabel.CENTER);

        tables = new JButton(MANAGE_TABLES);
        menu = new JButton(MANAGE_MENU);
        orders = new JButton(MANAGE_ORDERS);
        topFive = new JButton(SHOW_TOP_FIVE);
        exit = new JButton(EXIT);

        setButtonsSize();

        JPanel tablesAux = new JPanel();
        JPanel menuAux = new JPanel();
        JPanel ordersAux = new JPanel();
        JPanel topFiveAux = new JPanel();
        JPanel exitAux = new JPanel();

        tablesAux.add(tables);
        menuAux.add(menu);
        ordersAux.add(orders);
        topFiveAux.add(topFive);
        exitAux.add(exit);

        tables.setActionCommand(MANAGE_TABLES);
        menu.setActionCommand(MANAGE_MENU);
        orders.setActionCommand(MANAGE_ORDERS);
        topFive.setActionCommand(SHOW_TOP_FIVE);
        exit.setActionCommand(EXIT);

        principal.add(title);
        principal.add(tablesAux);
        principal.add(menuAux);
        principal.add(ordersAux);
        principal.add(topFiveAux);
        principal.add(exitAux);

        setContentPane(principal);
    }

    private void setButtonsSize() {

    }

    public void registerListeners(MainController mainController) {
        tables.addActionListener(mainController);
        menu.addActionListener(mainController);
        orders.addActionListener(mainController);
        topFive.addActionListener(mainController);
        exit.addActionListener(mainController);
    }
}
