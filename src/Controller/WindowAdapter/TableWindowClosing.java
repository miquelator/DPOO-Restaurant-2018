package Controller.WindowAdapter;

import View.MainView;
import View.TablesView;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TableWindowClosing extends WindowAdapter {
    private MainView parent;
    private TablesView tablesView;

    public TableWindowClosing(MainView parent, TablesView tablesView) {
        this.parent = parent;
        this.tablesView = tablesView;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        tablesView.setVisible(false);
        parent.setVisible(true);
    }
}
