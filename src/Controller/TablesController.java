package Controller;

import View.TablesView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TablesController implements ActionListener {
    private TablesView tablesView;

    public TablesController(TablesView tablesView) {
        this.tablesView = tablesView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){

        }
    }

    public void registerListeners(TablesView tablesView) {

    }
}
