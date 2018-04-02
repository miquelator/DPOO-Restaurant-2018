package Model;

import javax.swing.table.DefaultTableModel;

public class JTableModel extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
