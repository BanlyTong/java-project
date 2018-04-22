package updateTab;

import myClass.Database;
import myClass.ShowDataToTable;
import myClass.SubTable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;

import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.text.SimpleDateFormat;

public class UpdateUser extends javax.swing.JDialog {

    ShowDataToTable sd;
    
    Connection con;
    Statement stmt;
    ResultSet rs;
    
    DefaultTableModel model;
    
    boolean edit = false;
    
    public UpdateUser(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        SubTable.setDefaultTableRender(tbUser);
        SubTable.setTableHeader(tbUser, new Color(240, 240, 240), Color.BLACK, new Font("Tahoma", Font.PLAIN, 12));
        
        showDataToTable();
        
        formLoad();
    }
    
    private void clearAllText() {
        txtFName.setText(null);
        txtLName.setText(null);
        txtPwd.setText(null);
        txtUsername.setText(null);
        cbType.setSelectedIndex(0);
        dcJoin.setDate(null);
    }
    
    private void executeSQLQuery(String query, String message) {
        try {
            Class.forName(Database.driver);            
            con = DriverManager.getConnection(Database.url);           
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);            
            
            if (message.equals("Deleted")) {
                if (JOptionPane.showConfirmDialog(null, 
                        "Are you sure you want to " + message.substring(0, 6).toLowerCase() + " this record?", 
                        message.substring(0, 6), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (stmt.executeUpdate(query) == 1) {
                        JOptionPane.showMessageDialog(null, "Data " + message + " Successful", "Information", JOptionPane.INFORMATION_MESSAGE);
                        
                        formLoad();
                    } else {
                        JOptionPane.showMessageDialog(null, "Data cannot " + message, "Information", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                if (stmt.executeUpdate(query) == 1) {
                    JOptionPane.showMessageDialog(null, "Data " + message + " Successful", "Information", JOptionPane.INFORMATION_MESSAGE);              
                } else {
                    JOptionPane.showMessageDialog(null, "Data not " + message, "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void formLoad() {
        setPanelEnabled(panel_user, false);
        
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        
        btnNew.setEnabled(true);
        
        tbUser.setEnabled(true);
    }
    
    private void showDataToTable() {
        ShowDataToTable.show("SELECT * FROM [User]", tbUser, 6);
    }
    
    private void setPanelEnabled(JPanel panel, Boolean isEnabled) {
        Component[] components = panel.getComponents();

        for (Component component : components) {
            if (component.getClass().getName().equals("javax.swing.JPanel")) {
                setPanelEnabled((JPanel) component, isEnabled);
            }
            component.setEnabled(isEnabled);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_user = new javax.swing.JPanel();
        txtUsername = new javax.swing.JTextField();
        txtFName = new javax.swing.JTextField();
        txtLName = new javax.swing.JTextField();
        cbType = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtPwd = new javax.swing.JTextField();
        dcJoin = new com.toedter.calendar.JDateChooser();
        btnNew = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbUser = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel_user.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "User", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(255, 51, 51))); // NOI18N

        cbType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Select Type --", "Admin", "Insurance Company", "Healthcare Provider", "Normal" }));

        jLabel4.setText("Last Name:");

        jLabel3.setText("First Name:");

        jLabel2.setText("Username:");

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Save_20px.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.setFocusPainted(false);
        btnSave.setPreferredSize(new java.awt.Dimension(73, 28));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Cancel_20px.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setFocusPainted(false);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel5.setText("Password:");

        jLabel7.setText("Type:");

        jLabel8.setText("Join Date:");

        dcJoin.setDateFormatString("yyyy-MM-dd");
        dcJoin.setMaxSelectableDate(new java.util.Date(253370743277000L));
        dcJoin.setMinSelectableDate(new java.util.Date(-62135791123000L));

        javax.swing.GroupLayout panel_userLayout = new javax.swing.GroupLayout(panel_user);
        panel_user.setLayout(panel_userLayout);
        panel_userLayout.setHorizontalGroup(
            panel_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_userLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_userLayout.createSequentialGroup()
                        .addGroup(panel_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(45, 45, 45)
                        .addGroup(panel_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtLName)
                            .addComponent(txtUsername)))
                    .addGroup(panel_userLayout.createSequentialGroup()
                        .addGroup(panel_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(50, 50, 50)
                        .addGroup(panel_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dcJoin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panel_userLayout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panel_userLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(49, 49, 49)
                        .addComponent(txtPwd)))
                .addContainerGap())
        );
        panel_userLayout.setVerticalGroup(
            panel_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_userLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtFName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtLName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8)
                    .addComponent(dcJoin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Add_New_20px.png"))); // NOI18N
        btnNew.setText("New");
        btnNew.setFocusPainted(false);
        btnNew.setPreferredSize(new java.awt.Dimension(73, 28));
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Edit_20px.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.setFocusPainted(false);
        btnEdit.setPreferredSize(new java.awt.Dimension(73, 28));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Delete_20px.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setFocusPainted(false);
        btnDelete.setPreferredSize(new java.awt.Dimension(73, 28));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        tbUser.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        tbUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "First Name", "Last Name", "Password", "Type", "Join Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbUser.setRowHeight(20);
        tbUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbUser);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panel_user, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDelete, btnEdit});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDelete, btnEdit});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        boolean allRight = true;

        try {
            if (txtUsername.getText().equals("")) {
                allRight = false;
                throw new Exception("Plese Enter Username");
            }
            if (txtFName.getText().equals("")) {
                allRight = false;
                throw new Exception("Plese Enter First Name");
            }
            if (txtLName.getText().equals("")) {
                allRight = false;
                throw new Exception("Plese Enter Last Name");
            }
            if (txtPwd.getText().equals("")) {
                allRight = false;
                throw new Exception("Plese Enter Password");
            }
            if (cbType.getSelectedIndex() == 0) {
                allRight = false;
                throw new Exception("Please Select Type");
            }
            if (((JTextField)dcJoin.getDateEditor().getUiComponent()).getText().equals("")) {
                allRight = false;
                throw new Exception("Plese Enter Join Date");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        if (allRight) {
            Date date1 = dcJoin.getDate();
            DateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
            
            String joinDate = datef.format(date1);
            
            if (edit) {
                
                executeSQLQuery("UPDATE [User] "
                        + "SET [First Name] = '" + txtFName.getText() + "', [Last Name] = '" + txtLName.getText() + "',"
                        + " password = '" + txtPwd.getText() + "', type = '" + cbType.getSelectedItem() + "'"
                        + ", joinDate = '" + joinDate + "' "
                        + "WHERE username = '" + txtUsername.getText() + "'", "Updated");                      
            }
            else {
                executeSQLQuery("INSERT INTO [User](username, [First Name], [Last Name], password, type, joinDate) "
                        + "VALUES('" + txtUsername.getText() + "', '" + txtFName.getText() + "', '" + txtLName.getText() + "',"
                        + " '" + txtPwd.getText() + "', '" + cbType.getSelectedItem()+ "', "
                        + "'" + joinDate + "')"
                        , "Inserted");
            }
            formLoad();
            
            showDataToTable();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        if(JOptionPane.showConfirmDialog(null,"Are you sure to cancel?", "Cancel", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            formLoad();
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        edit = false;
        
        tbUser.setEnabled(false);
        
        btnNew.setEnabled(false);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        
        setPanelEnabled(panel_user, true);
        
        clearAllText();
        
        txtUsername.requestFocus();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        edit = true;
        
        int i = tbUser.getSelectedRow();
        
        if(i == -1){
            if(tbUser.getRowCount() == 0){
                JOptionPane.showMessageDialog(null, "The table contain no data!", "Informaion", JOptionPane.OK_OPTION);
            } else {
                JOptionPane.showMessageDialog(null, "Please select data to edit!", "Informaion", JOptionPane.OK_OPTION);
            }
        }
        else {
        
            tbUser.setEnabled(false);

            btnNew.setEnabled(false);
            btnEdit.setEnabled(false);
            btnDelete.setEnabled(false);

            setPanelEnabled(panel_user, true);

            txtUsername.setEnabled(false);
            txtFName.requestFocus();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void tbUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbUserMouseClicked
        int i = tbUser.getSelectedRow();
        
        model = (DefaultTableModel) tbUser.getModel();
        
        Date date = null;
        
        try {  
            date = new SimpleDateFormat("yyyy-MM-dd").parse((String) model.getValueAt(i, 5));
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        txtUsername.setText(model.getValueAt(i, 0).toString());
        txtFName.setText(model.getValueAt(i, 1).toString());
        txtLName.setText(model.getValueAt(i, 2).toString());
        txtPwd.setText(model.getValueAt(i, 3).toString());
        cbType.setSelectedItem(model.getValueAt(i, 4).toString());
        dcJoin.setDate(date);
        
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
    }//GEN-LAST:event_tbUserMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int i = tbUser.getSelectedRow();
       
        model = (DefaultTableModel) tbUser.getModel();
        
        
        
        if(i == -1) {
            if(tbUser.getRowCount() == 0){
                JOptionPane.showMessageDialog(null, "The table contain no data!", "Information", JOptionPane.OK_OPTION);
            }
            else {
                JOptionPane.showMessageDialog(null, "Please select data to delete!","Information",JOptionPane.OK_OPTION);
            }
        }
        else {
            String uname = model.getValueAt(i, 0).toString();
            
            executeSQLQuery("DELETE FROM [User] WHERE username = '" + uname + "'", "Deleted");
        }
        showDataToTable();     
    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UpdateUser dialog = new UpdateUser(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbType;
    private com.toedter.calendar.JDateChooser dcJoin;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel_user;
    private javax.swing.JTable tbUser;
    private javax.swing.JTextField txtFName;
    private javax.swing.JTextField txtLName;
    private javax.swing.JTextField txtPwd;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
