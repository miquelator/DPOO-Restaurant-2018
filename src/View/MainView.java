// This class belongs to view package
package View;

// import our connector classes
import Controller.ActionListener.MainController;

// import java classes
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * This class MainView, crates and control the principal window of the program
 */
public class MainView extends JFrame {

    // create utilities instances
    private JButton tables;
    private JButton menu;
    private JButton orders;
    private JButton topFive;
    private JButton exit;

    // set generic names as constants
    public final static String MANAGE_TABLES = "Gestionar taules";
    public final static String MANAGE_MENU = "Gestionar carta";
    public final static String MANAGE_ORDERS = "Gestionar comandes";
    public final static String SHOW_TOP_FIVE = "Mostra top 5";
    public final static String EXIT = "Sortir";

    /**
     * Constructor without parameters of the class. Generates the view
     */
    public MainView() {
        // place components in the view
        populateView();

        // set default values of usage
        setSize(700, 700);
        setTitle("Administrator view");
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 600));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    /**
     * This method sets the components to the view
     */
    private void populateView() {

        // define the panel layout
        JPanel principal = new JPanel(new GridLayout(0,1,10,10));
        // leave space on the borders
        principal.setBorder(new EmptyBorder(20,30,20,30));

        // set a principal title
        JLabel title = new JLabel("Servidor del restaurant");
        title.setFont(title.getFont().deriveFont(45.0f));
        title.setHorizontalAlignment(JLabel.CENTER);

        // init the items
        tables = new JButton(MANAGE_TABLES);
        menu = new JButton(MANAGE_MENU);
        orders = new JButton(MANAGE_ORDERS);
        topFive = new JButton(SHOW_TOP_FIVE);
        exit = new JButton(EXIT);

        // set the font size to BIG
        tables.setFont(new Font("Arial", Font.PLAIN, 30));
        menu.setFont(new Font("Arial", Font.PLAIN, 30));
        orders.setFont(new Font("Arial", Font.PLAIN, 30));
        topFive.setFont(new Font("Arial", Font.PLAIN, 30));
        exit.setFont(new Font("Arial", Font.PLAIN, 30));

        // add the items to the panel
        principal.add(title);
        principal.add(tables);
        principal.add(menu);
        principal.add(orders);
        principal.add(topFive);
        principal.add(exit);

        this.add(principal);
        this.pack();
    }

    /**
     * Adds a listener to view's components
     * @param mainController ActionListener controller
     */
    public void registerListeners(MainController mainController) {

        // set and action listener to all the buttons
        tables.addActionListener(mainController);
        menu.addActionListener(mainController);
        orders.addActionListener(mainController);
        topFive.addActionListener(mainController);
        exit.addActionListener(mainController);
    }
    public void showPopError(String message){
        String[] options = { "OK" };
        JOptionPane.showOptionDialog(this, message, "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
    }

}
