// package where it belongs
package Controller.MouseController;

// import our classes
import View.TablesView;

// import java classes
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


/**
 * This class control the spinner of the seats
 */
public class SeatsSpinController implements MouseWheelListener {

    // get a view instance
    private TablesView tView;

    /**
     * Constructor of the class with view parameter
     * @param tView instance of TablesView
     */
    public SeatsSpinController(TablesView tView) {
        this.tView = tView;
    }

    /**
     * Method that manages the increment or decrement of the spinner number
     * @param e MouseWheelEvent that's raised
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation()<0){
            // up
            if(tView.getNumSeients() < tView.MAX_SEATS){
                tView.addSeients(1);
            }
        }else{
            // down
            if(tView.getNumSeients()>1){
                tView.addSeients(-1);
            }
        }
    }
}
