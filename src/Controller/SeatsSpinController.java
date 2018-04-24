package Controller;

import View.TablesView;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class SeatsSpinController implements MouseWheelListener {

    // get a view instance
    private TablesView tView;

    public SeatsSpinController(TablesView tView) {
        this.tView = tView;
    }

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
