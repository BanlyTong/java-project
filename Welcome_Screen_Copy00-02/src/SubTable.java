
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sathya
 */
public class SubTable {
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
    
    public static void setDefaultTableRender(JTable jTable) {
        jTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(isSelected ? new Color(0,120,215) : row % 2 == 0 ? new Color(255, 255, 255) : new Color(242, 242, 242));
                c.setForeground(isSelected ? Color.WHITE : Color.BLACK);
                return c;
            }
        });
    }
}
