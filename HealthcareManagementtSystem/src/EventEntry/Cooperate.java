/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventEntry;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mizter Lee
 */
public class Cooperate extends javax.swing.JFrame {

    /**
     * Creates new form NewCooperate
     */
    private Connection con;
    private Statement st,stProvider,stInsurance;
    private ResultSet rs,rsProvider,rsInsurance;
    private DefaultTableModel model;
    private String sql="select * from Cooperate",
                    sqlProvider="select * from Provider",
                    sqlInsurance="select * from Insurance";
    public Cooperate() {
        initComponents();
        String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url="jdbc:sqlserver://localhost:1433;"+
                "databaseName=HealthCareService;user=sa;password=b@nlyonly;";
        try {
            Class.forName(driver);
            con=DriverManager.getConnection(url);
            st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stProvider=con.createStatement();
            stInsurance=con.createStatement();
            model=(DefaultTableModel) tbCooperate.getModel();
            rsProvider=stProvider.executeQuery(sqlProvider);
            while(rsProvider.next()){
                cbProvider.addItem(rsProvider.getString("ID")+"("+rsProvider.getString("Name")+")");
            }
            rsInsurance=stInsurance.executeQuery(sqlInsurance);
            while(rsInsurance.next()){
                cbInsurance.addItem(rsInsurance.getString("ID")+"("+rsInsurance.getString("Name")+")");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Cooperate.class.getName()).log(Level.SEVERE, null, ex);
        }
        controlFormLoad();
        refreshData();
        tableEventKey();
    }
    private void refreshData(){
        Object[] o=new Object[6];
        model.setRowCount(0);
        try {
            rs=st.executeQuery(sql);
            while(rs.next()){
                o[0]=rs.getString("Provider.ID");
                o[1]=rs.getString("Insurance.ID");
                o[2]=rs.getString("LevelofService");
                o[3]=rs.getString("Amount");
                o[4]=rs.getString("Distribution");
                o[5]=rs.getString("Description");
                model.addRow(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Cooperate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void clearAll(){
        txtLevelOfService.setText(null);
        txtAmount.setText(null);
        txtDistribution.setText(null);
        txtDesc.setText(null);
    }
    private void enableControls(boolean b){
        cbProvider.setEnabled(b);
        cbInsurance.setEnabled(b);
        txtLevelOfService.setEnabled(b);
        txtAmount.setEnabled(b);
        txtDistribution.setEnabled(b);
        txtDesc.setEnabled(b);
        btnNew.setEnabled(!b);
        btnSave.setEnabled(b);
        btnEdit.setEnabled(!b);
        btnDelete.setEnabled(!b);
        btnCancel.setEnabled(b);
        tbCooperate.setEnabled(!b);
    }
    private void controlFormLoad(){
        setLocationRelativeTo(null);
        clearAll();
        enableControls(false);
        btnSave.setText("Save");
    }
    private void tableEventKey(){
        tbCooperate.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                int i=tbCooperate.getSelectedRow();
                if(i>=0){
                    lbCount.setText(i+1+"/"+model.getRowCount());
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int i=tbCooperate.getSelectedRow();
                if(i>=0){
                    lbCount.setText((i+1)+"/"+model.getRowCount());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int i=tbCooperate.getSelectedRow();
                if(i>=0){
                    lbCount.setText(i+1+"/"+model.getRowCount());
                }
            }
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cbProvider = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cbInsurance = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtLevelOfService = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        txtDistribution = new javax.swing.JTextField();
        txtDesc = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCooperate = new javax.swing.JTable();
        btnNew = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        lbCount = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel5.setText("Distribution");

        jLabel4.setText("Amount");

        jLabel2.setText("Insurance");

        jLabel1.setText("Provider");

        jLabel3.setText("Level Of Service");

        txtDistribution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDistributionActionPerformed(evt);
            }
        });

        jLabel6.setText("Description");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cbProvider, 0, 122, Short.MAX_VALUE)
                        .addComponent(cbInsurance, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtAmount, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLevelOfService, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtDesc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .addComponent(txtDistribution, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbProvider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbInsurance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLevelOfService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDistribution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tbCooperate.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Provider", "Insurance", "LevelOfService", "Amount", "Distribution", "Description"
            }
        ));
        tbCooperate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCooperateMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbCooperate);

        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnSave.setText(" Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        lbCount.setText("Number");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(btnNew)
                .addGap(18, 18, 18)
                .addComponent(btnSave)
                .addGap(18, 18, 18)
                .addComponent(btnEdit)
                .addGap(18, 18, 18)
                .addComponent(btnDelete)
                .addGap(18, 18, 18)
                .addComponent(btnCancel)
                .addGap(18, 18, 18)
                .addComponent(btnClose)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbCount)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbCount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNew)
                    .addComponent(btnEdit)
                    .addComponent(btnSave)
                    .addComponent(btnDelete)
                    .addComponent(btnCancel)
                    .addComponent(btnClose))
                .addGap(44, 44, 44))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDistributionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDistributionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDistributionActionPerformed

    private void tbCooperateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCooperateMouseClicked
        // TODO add your handling code here:
        int i=tbCooperate.getSelectedRow();
        if(i>=0){
            lbCount.setText((i+1)+"/"+model.getRowCount());
        }
    }//GEN-LAST:event_tbCooperateMouseClicked

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        enableControls(true);
        cbProvider.requestFocus();

    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        int i=tbCooperate.getSelectedRow();
        if(i==-1){
            if(tbCooperate.getRowCount()==0){
                JOptionPane.showMessageDialog(null, "The table contain no data","Healthcare Service System",JOptionPane.OK_OPTION);
            }
            else JOptionPane.showMessageDialog(null, "Please select the data","Healthcare Service System",JOptionPane.OK_OPTION);
        }else{
            enableControls(true);cbProvider.setEditable(false);
            cbInsurance.setEditable(false);btnSave.setText("Update");
            txtLevelOfService.requestFocus();
            for(int j=0;j<cbProvider.getItemCount();j++)
                if(cbProvider.getItemAt(j).startsWith(model.getValueAt(i, 0).toString())){
                cbProvider.setSelectedIndex(j);
                }
            for(int j=0;j<cbInsurance.getItemCount();j++)
                if(cbInsurance.getItemAt(j).startsWith(model.getValueAt(i, 1).toString())){
                cbInsurance.setSelectedIndex(j);
                }
            txtLevelOfService.setText((String) model.getValueAt(i, 2));
            txtAmount.setText((String) model.getValueAt(i, 3));
            txtDistribution.setText((String) model.getValueAt(i, 4));
            txtDesc.setText((String) model.getValueAt(i, 5));
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        String proID=new StringTokenizer(cbProvider.getSelectedItem().toString(),"(").nextToken();
        String insID=new StringTokenizer(cbInsurance.getSelectedItem().toString(),"(").nextToken();
        String los=txtLevelOfService.getText();
        String amount=txtAmount.getText();
        String distribution=txtDistribution.getText();
        String desc=txtDesc.getText();
        if(btnSave.getText().equalsIgnoreCase("Save")){
            try {
                rs.moveToInsertRow();
                rs.updateString("Provider.ID", proID);
                rs.updateString("Insurance.ID", insID);
                rs.updateString("LevelOfService", los);
                rs.updateString("Amount", amount);
                rs.updateString("Distribution", distribution);
                rs.updateString("Description",desc);
                rs.insertRow();
                model.addRow(new Object[]{proID,insID,los,amount,distribution,desc});
            } catch (SQLException ex) {
                Logger.getLogger(Cooperate.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Saved");
        }else if(btnSave.getText().equalsIgnoreCase("Update")){
            int i=tbCooperate.getSelectedRow();
            try {
                rs.absolute(i+1);
                rs.updateString("LevelOfService", los);
                rs.updateString("Amount",amount);
                rs.updateString("Distribution", distribution);
                rs.updateString("Description",desc);
                rs.updateRow();
                model.setValueAt(los, i, 2);
                model.setValueAt(amount, i, 3);
                model.setValueAt(distribution, i, 4);
                model.setValueAt(desc, i, 5);
            } catch (SQLException ex) {
                Logger.getLogger(Cooperate.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Updated");
        }
        controlFormLoad();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int i=tbCooperate.getSelectedRow();boolean d=false;
        if(i==-1){
            if(tbCooperate.getRowCount()==0){
                JOptionPane.showMessageDialog(null, "The table contain no data","Healthcare Service System",JOptionPane.OK_OPTION);
            }
            else JOptionPane.showMessageDialog(null, "Please select the data","Healthcare Service System",JOptionPane.OK_OPTION);
        }else{
            if(JOptionPane.showConfirmDialog(null,"Are you sure to delete?","Delete",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            model.removeRow(i);
            try {
                rs.absolute(i+1);
                rs.deleteRow();
                d=rs.rowDeleted();
            } catch (SQLException ex) {
                Logger.getLogger(Cooperate.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Delete="+d);
            if(d) refreshData();
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(null,"Are you sure to cancel?","Cancel",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            controlFormLoad();
        };
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(null,"Are you sure to close?","Close",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            this.dispose();
        };
    }//GEN-LAST:event_btnCloseActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Cooperate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cooperate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cooperate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cooperate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cooperate().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbInsurance;
    private javax.swing.JComboBox<String> cbProvider;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbCount;
    private javax.swing.JTable tbCooperate;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextField txtDistribution;
    private javax.swing.JTextField txtLevelOfService;
    // End of variables declaration//GEN-END:variables
}
