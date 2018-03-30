/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.DataEntry;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mizter Lee
 */
public class Plan extends javax.swing.JFrame {

    /**
     * Creates new form Plan
     */
    private Connection con;
    private Statement st,stInsur,stHas,stDesc;
    private ResultSet rs,rsInsur,rsHas,rsDesc;
    private DefaultTableModel model;
    private String sql="select * from [Plan];",
            sqlInsur="select ID,Name from Insurance",
            sqlDesc="select Description from [Plan] where ID=",
            sqlHas="select Insurance.ID,Insurance.Name from Insurance inner join [Plan] on Insurance.ID=[Plan].[Insurance.ID] where [Plan].ID=";
    private String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String url="jdbc:sqlserver://localhost:1433;" +
                "databaseName=HealthCareService;user=sa;password=b@nlyonly;";
    public Plan(){
        initComponents();
        controlFormload();
        model=(DefaultTableModel) tbPlan.getModel();
        try{
            Class.forName(driver);
            con=DriverManager.getConnection(url);
            st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stInsur=con.createStatement();
            stDesc=con.createStatement();
            stHas=con.createStatement();
            rsInsur=stInsur.executeQuery(sqlInsur);
            while(rsInsur.next()){
                cbInsurance.addItem(rsInsur.getString("ID")+"=>"+rsInsur.getString("Name"));
            }
            refreshData();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Plan.class.getName()).log(Level.SEVERE, null, ex);
        }
        tbPlan.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(tbPlan.getSelectedRow()>=0){
                    try {
                        int index=tbPlan.getSelectedRow();
                        String id=(String) model.getValueAt(index, 0);
                        rsHas=stHas.executeQuery(sqlHas+"'"+id+"'");
                        String pro;
                        if(rsHas.next()){
                            pro=rsHas.getString("ID")+"=>"+rsHas.getString("Name");
                            cbInsurance.setSelectedItem(pro);
                        }else{JOptionPane.showMessageDialog(null, "Result set has no current row!");}
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                 if(tbPlan.getSelectedRow()>=0){
                    try {
                        int index=tbPlan.getSelectedRow();
                        String id=(String) model.getValueAt(index, 0);
                        rsHas=stHas.executeQuery(sqlHas+"'"+id+"'");
                        String pro="";
                        if(rsHas.next()){
                            pro=rsHas.getString("ID")+"=>"+rsHas.getString("Name");
                            cbInsurance.setSelectedItem(pro);
                        }else{JOptionPane.showMessageDialog(null, "Result set has no current row!");}
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                 if(tbPlan.getSelectedRow()>=0){
                    try {
                        int index=tbPlan.getSelectedRow();
                        String id=(String) model.getValueAt(index, 0);
                        rsHas=stHas.executeQuery(sqlHas+"'"+id+"'");
                        String pro="";
                        if(rsHas.next()){
                            pro=rsHas.getString("ID")+"=>"+rsHas.getString("Name");
                            cbInsurance.setSelectedItem(pro);
                        }else{JOptionPane.showMessageDialog(null, "Result set has no current row!");}
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
    }

    private void refreshData(){
        Object[] o=new Object[3];
        model.setRowCount(0);
        try{
            rs=st.executeQuery(sql);
            while(rs.next()){
                o[0]=rs.getString("ID");
                o[1]=rs.getString("Name");
                o[2]=rs.getString("Description");                
                model.addRow(o);
            }
        }catch(SQLException ex ){
            Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPlan = new javax.swing.JTable();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDesc = new javax.swing.JTextArea();
        cbInsurance = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbPlan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Description"
            }
        ));
        tbPlan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPlanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbPlan);

        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Healthcare Plan"));

        jLabel1.setText("ID");

        jLabel2.setText("Name");

        jLabel3.setText("Description:");

        txtDesc.setColumns(20);
        txtDesc.setLineWrap(true);
        txtDesc.setRows(5);
        jScrollPane2.setViewportView(txtDesc);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(25, 25, 25)
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 86, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(17, 17, 17)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel4.setText("Insurance's Plan");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(cbInsurance, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbInsurance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(25, 25, 25)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNew, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCancel)
                        .addComponent(btnClose)
                        .addComponent(btnDelete)
                        .addComponent(btnEdit)
                        .addComponent(btnSave)))
                .addGap(39, 39, 39))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(null,"Do you want to cancel?!","Cancel",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            controlFormload();
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        enableControl(true);
        txtID.requestFocus();
        btnSave.setEnabled(true);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        btnCancel.setEnabled(true);
        btnNew.setEnabled(false);
        tbPlan.setEnabled(false);
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(btnSave.getText().equalsIgnoreCase("Save")){
            try {
            // TODO add your handling code here:
            rs.moveToInsertRow();
            rs.updateString("ID",txtID.getText());
            rs.updateString("Name",txtName.getText());
            rs.updateString("Description",txtDesc.getText());
            rs.updateString("Insurance.ID",new StringTokenizer(cbInsurance.getSelectedItem().toString(),"=>").nextToken());
            rs.insertRow();
            model.addRow(new Object[]{txtID.getText(),txtName.getText(),txtDesc.getText()});
        } catch (SQLException ex) {
            Logger.getLogger(Plan.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Saved");
        }else if(btnSave.getText().equalsIgnoreCase("Update")){
        
                int index=tbPlan.getSelectedRow();
                String name=txtName.getText();
                String desc=txtDesc.getText();
                
                //String provider=new StringTokenizer(cbProvider.getSelectedItem().toString(),"=>").nextToken();
                try{
                    
                    rs.absolute(index+1);
                    rs.updateString("Name",name);
                    rs.updateString("Description",desc);
                    rs.updateString("Insurance.ID",new StringTokenizer(cbInsurance.getSelectedItem().toString(),"=>").nextToken());
                    //rs.updateString("Provider.ID",provider);
                    rs.updateRow();
                    model.setValueAt(name, index, 1);
                    model.setValueAt(desc,index,2);
                    
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
        
            JOptionPane.showMessageDialog(null,"Updated");
        }
        controlFormload();
        btnSave.setText("Save");
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        enableControl(true);
        btnNew.setEnabled(false);
        btnSave.setText("Update");
        btnSave.setEnabled(true);
        btnDelete.setEnabled(false);
        btnCancel.setEnabled(true);
        tbPlan.setEnabled(false);
        int index=tbPlan.getSelectedRow();
        if(index<0){
            if(tbPlan.getRowCount()==0){
                JOptionPane.showMessageDialog(null, "The table contain no data","Healthcare Service System",JOptionPane.OK_OPTION);
            }
            else JOptionPane.showMessageDialog(null, "Please select the data","Healthcare Service System",JOptionPane.OK_OPTION);
        }else{
        txtID.setText((String) model.getValueAt(index,0));
        txtName.setText((String) model.getValueAt(index,1));
        txtDesc.setText((String) model.getValueAt(index,2));
        btnEdit.setEnabled(false);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(null,"Are you sure to delele this PLAN","DELETE",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
        int i=tbPlan.getSelectedRow();
        model.removeRow(i);//tbProvider.remove(i);
        boolean d=false;
        try{
            rs.absolute(i+1);
            rs.deleteRow();
            d=rs.rowDeleted();
            //rs.refreshRow();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null,"Deleted= "+d);
        refreshData();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(null,"Confirm if you want to exit!","Exit",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            this.dispose();
        }
    }//GEN-LAST:event_btnCloseActionPerformed

    private void tbPlanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPlanMouseClicked
        // TODO add your handling code here:
        if(tbPlan.getSelectedRow()>=0){
            try {
                int index=tbPlan.getSelectedRow();
                String id=(String) model.getValueAt(index, 0);
                rsHas=stHas.executeQuery(sqlHas+"'"+id+"'");
                rsDesc=stDesc.executeQuery(sqlDesc+"'"+id+"'");
                String pro;
                if(rsHas.next()){
                    pro=rsHas.getString("ID")+"=>"+rsHas.getString("Name");
                    cbInsurance.setSelectedItem(pro);
                }else{JOptionPane.showMessageDialog(null, "Result set has no current row!");}
                txtDesc.setText((String) model.getValueAt(index, 2));
            } catch (SQLException ex) {
                Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else { JOptionPane.showMessageDialog(null,"You haven't selected a row of data");}
    }//GEN-LAST:event_tbPlanMouseClicked

    private void clearAllTextFields(){
        txtID.setText(null);
        txtName.setText(null);
        txtDesc.setText(null);
    }
    private void enableControl(boolean b){
        txtID.setEnabled(b);
        txtName.setEnabled(b);
        txtDesc.setEnabled(b);
        cbInsurance.setEnabled(true);
    }
    private void controlFormload(){
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        clearAllTextFields();
        enableControl(false);
        btnNew.setEnabled(true);
        btnSave.setEnabled(false);btnSave.setText("Save");
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
        btnCancel.setEnabled(false);
        btnClose.setEnabled(true);
        tbPlan.setEnabled(true);
    }
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
            java.util.logging.Logger.getLogger(Plan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Plan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Plan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Plan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Plan().setVisible(true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbPlan;
    private javax.swing.JTextArea txtDesc;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
