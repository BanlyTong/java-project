
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sathya
 */
public class SubTable extends javax.swing.JTable {
    public static void removeAllRows(JTable table, DefaultTableModel model) {
        while (table.getRowCount() > 0) {
            model.removeRow(0);
        }
    }
    
    public static void setTableHeader(JTable table, Color bg, Color fg, Font font) {
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(bg);
        table.getTableHeader().setForeground(fg);
        table.getTableHeader().setFont(font);
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    }
    
//    @Override
//    public Component prepareRenderer(TableCellRenderer ren, int r, int c) {
//        Component comp = super.prepareRenderer(ren, r, c);
//        
//        if (r % 2 == 0 && !isCellSelected(r, c)) {
//            comp.setBackground(new Color(242, 242, 242));
//        }
//        else if (!isCellSelected(r, c)) {
//            comp.setBackground(new Color(255, 255, 255));
//        }
//        else {
//            comp.setBackground(new Color(165, 207, 241));
//        }
//        
//        return comp;        
//    }
}
