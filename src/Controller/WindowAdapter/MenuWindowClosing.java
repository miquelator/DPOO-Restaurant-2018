package Controller.WindowAdapter;

import View.MainView;
import View.MenuView;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MenuWindowClosing extends WindowAdapter {
    private MainView parent;
    private MenuView menuView;

    public MenuWindowClosing(MainView parent, MenuView menuView) {
        this.parent = parent;
        this.menuView = menuView;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        menuView.setVisible(false);
        parent.setVisible(true);
    }
}
