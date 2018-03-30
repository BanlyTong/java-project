/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.DataEntry;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mizter Lee
 */

public class Insurance extends javax.swing.JFrame {

    /**
     * Creates new form Insurance
     */
    private DefaultTableModel model,contactModel;
    private Statement st,stContact,stIcontact,stRemove,stDeleteContact;
    private ResultSet rs,rsContact,rsIcontact;
    private String sql="select * from Insurance",
            sqlContact="select * from InsuranceContact",
            sqlIcontact="select * from InsuranceContact where [Insurance.ID]=",
            sqlRemove="delete from InsuranceContact where [Phone]=",
            sqlDeleteContact="update InsuranceContact\n" +"set [Insurance.ID]=null\n" +"where [Insurance.ID]=";  
    
    public Insurance() {
        initComponents();
        String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url="jdbc:sqlserver://localhost:1433;" +
                "databaseName=HealthCareService;user=sa;password=b@nlyonly;";
        try{
            Class.forName(driver);
            Connection con=DriverManager.getConnection(url);
            st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stContact=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stIcontact=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stRemove=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stDeleteContact=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            model=(DefaultTableModel) tbInsurance.getModel();
            contactModel=(DefaultTableModel) tbContact.getModel();
            rsContact=stContact.executeQuery(sqlContact);
            
        }catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(InsuranceTab.class.getName()).log(Level.SEVERE, null, ex);
        }
        refreshData();
        controlFormLoad();
        tbInsurance.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                int i=tbInsurance.getSelectedRow();
                if(i>=0){
                    lbCount.setText(i+1+"/"+model.getRowCount());
                    try {
                        rs.absolute(i+1);
                        contactModel.setRowCount(0);
                        rsIcontact=stIcontact.executeQuery(sqlIcontact+"'"+rs.getString("ID")+"'");
                        while(rsIcontact.next()){
                            contactModel.addRow(new Object[]{rsIcontact.getString("Name"),rsIcontact.getString("Phone")});
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Provider.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int i=tbInsurance.getSelectedRow();
                if(i>=0){
                    lbCount.setText(i+1+"/"+model.getRowCount());
                    try {
                        rs.absolute(i+1);
                        contactModel.setRowCount(0);
                        rsIcontact=stIcontact.executeQuery(sqlIcontact+"'"+rs.getString("ID")+"'");
                        while(rsIcontact.next()){
                            contactModel.addRow(new Object[]{rsIcontact.getString("Name"),rsIcontact.getString("Phone")});
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Provider.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int i=tbInsurance.getSelectedRow();
                if(i>=0){
                    lbCount.setText(i+1+"/"+model.getRowCount());
                    try {
                        rs.absolute(i+1);
                        contactModel.setRowCount(0);
                        rsIcontact=stIcontact.executeQuery(sqlIcontact+"'"+rs.getString("ID")+"'");
                        while(rsIcontact.next()){
                            contactModel.addRow(new Object[]{rsIcontact.getString("Name"),rsIcontact.getString("Phone")});
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Provider.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tbInsurance = new javax.swing.JTable();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtID = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbCount = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtPerson = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnInsert = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbContact = new javax.swing.JTable();
        btnRemove = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(18, 55, 92));

        tbInsurance.setBackground(new java.awt.Color(57, 109, 160));
        tbInsurance.setForeground(new java.awt.Color(153, 153, 255));
        tbInsurance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Address"
            }
        ));
        tbInsurance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbInsuranceMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbInsurance);
        if (tbInsurance.getColumnModel().getColumnCount() > 0) {
            tbInsurance.getColumnModel().getColumn(0).setMinWidth(100);
            tbInsurance.getColumnModel().getColumn(0).setPreferredWidth(100);
            tbInsurance.getColumnModel().getColumn(0).setMaxWidth(100);
            tbInsurance.getColumnModel().getColumn(2).setMinWidth(250);
            tbInsurance.getColumnModel().getColumn(2).setPreferredWidth(250);
            tbInsurance.getColumnModel().getColumn(2).setMaxWidth(250);
        }

        btnNew.setBackground(new java.awt.Color(102, 204, 255));
        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnSave.setBackground(new java.awt.Color(102, 204, 255));
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(102, 204, 255));
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(102, 204, 255));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(102, 204, 255));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnClose.setBackground(new java.awt.Color(102, 204, 255));
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Insurance"));

        jLabel4.setText("Address:");

        jLabel3.setText("Name:");

        jLabel2.setText("ID:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(39, 39, 39)
                        .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        lbCount.setForeground(new java.awt.Color(255, 255, 255));
        lbCount.setText("Number");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Contact"));

        jLabel5.setText("Contact person:");

        jLabel6.setText("Phone:");

        btnInsert.setText("Insert");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                    .addComponent(txtPerson))
                .addGap(18, 18, 18)
                .addComponent(btnInsert)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPerson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(btnInsert))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        tbContact.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Contact"
            }
        ));
        jScrollPane2.setViewportView(tbContact);

        btnRemove.setBackground(new java.awt.Color(102, 204, 255));
        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(102, 204, 255));
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNew)
                            .addComponent(btnDelete))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSave)
                            .addComponent(btnCancel))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEdit)
                            .addComponent(btnClose))))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnRemove)
                                    .addComponent(btnAdd))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbCount)
                                .addGap(26, 26, 26))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemove))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbCount)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnNew)
                                        .addComponent(btnSave)
                                        .addComponent(btnEdit))
                                    .addGap(29, 29, 29)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnDelete)
                                        .addComponent(btnCancel)
                                        .addComponent(btnClose)))))))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        enableAllTextFields(true);
        txtID.requestFocus();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String id=txtID.getText();
        if(btnSave.getText().equalsIgnoreCase("Save")){
            try {
                // TODO add your handling code here:
                String name=txtName.getText();
                String add=txtAddress.getText();
                rs.moveToInsertRow();
                rs.updateString("ID", id);
                rs.updateString("Name",name);
                rs.updateString("Addresss",add);
                rs.insertRow();
                insertRowToTable(id,name,add);
                rsContact.moveToInsertRow();
                rsContact.updateString("Insurance.ID", id);
                rsContact.updateString("Name",txtPerson.getText());
                rsContact.updateString("Phone",txtPhone.getText());
                rsContact.insertRow();
            } catch (SQLException ex) {
                Logger.getLogger(InsuranceTab.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Saved");
        }
        else if(btnSave.getText().equalsIgnoreCase("Update")){
            int index=tbInsurance.getSelectedRow();
            String name=txtName.getText();
            String add=txtAddress.getText();
            try{
                rs.absolute(index+1);
                rs.updateString("Name", name);
                rs.updateString("Addresss", add);
                rs.updateRow();
                model.setValueAt(name, index, 1);
                model.setValueAt(add,index,2);
            }catch(Exception ex){
                Logger.getLogger(InsuranceTab.class.getName()).log(Level.SEVERE, null, ex);
            }
            btnSave.setText("Save");
            txtID.setEditable(true);
            JOptionPane.showMessageDialog(null, "Updated");
        }
        controlFormLoad();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        int i=tbInsurance.getSelectedRow();
        if(i==-1){
            if(tbInsurance.getRowCount()==0){
                JOptionPane.showMessageDialog(null, "The table contain no data","Healthcare Service System",JOptionPane.OK_OPTION);
            }
            JOptionPane.showMessageDialog(null, "Please select data to edit","Healthcare Service System",JOptionPane.OK_OPTION);
        }
        else{
            enableAllTextFields(true);
            txtPerson.setEnabled(false);
            txtPhone.setEnabled(false);
            btnEdit.setEnabled(false);
            txtID.setEditable(false);
            txtName.requestFocus();
            btnSave.setText("Update");
            btnSave.setEnabled(true);
            btnNew.setEnabled(false);
            btnDelete.setEnabled(false);
            btnCancel.setEnabled(true);
            tbInsurance.setEnabled(false);
            int index=tbInsurance.getSelectedRow();
            try{
                rs.absolute(index+1);
                String id=rs.getString("ID");
                String name=rs.getString("Name");
                String add=rs.getString("Addresss");
                txtID.setText(id);
                txtName.setText(name);
                txtAddress.setText(add);                
            }catch(Exception ex){
                Logger.getLogger(InsuranceTab.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int i=tbInsurance.getSelectedRow();
        if(i==-1){
            if(tbInsurance.getRowCount()==0){
                JOptionPane.showMessageDialog(null, "The table contain no data","Healthcare Service System",JOptionPane.OK_OPTION);
            }
            JOptionPane.showMessageDialog(null, "Please select data to delete","Healthcare Service System",JOptionPane.OK_OPTION);
        }
        else{
            if(JOptionPane.showConfirmDialog(null,"Warning: you can't delete the insurance that has reference from other table in database.\nAre you sure to Delete?","Delete",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
        
        boolean d=false;
        try{
            //stDeleteContact.executeUpdate(sqlDeleteContact+"'"+model.getValueAt(i, 0)+"'");
            rs.absolute(i+1);
            rs.deleteRow();
            d=rs.rowDeleted();
            model.removeRow(i);
        }catch(Exception ex){
            Logger.getLogger(InsuranceTab.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(null, ex.toString());
        }
        JOptionPane.showMessageDialog(null,"Deleted= "+d);
        refreshData();
        }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(null,"Are you sure to cancel?","Cancel",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            controlFormLoad();
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(null,"Confirm if you want to exit!","Exit",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            this.dispose();
        }
    }//GEN-LAST:event_btnCloseActionPerformed

    private void tbInsuranceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbInsuranceMouseClicked
        // TODO add your handling code here:
        int i=tbInsurance.getSelectedRow();
            if(i>=0){
                lbCount.setText(i+1+"/"+model.getRowCount());
                try {
                        rs.absolute(i+1);
                        contactModel.setRowCount(0);
                        rsIcontact=stIcontact.executeQuery(sqlIcontact+"'"+rs.getString("ID")+"'");
                        while(rsIcontact.next()){
                            contactModel.addRow(new Object[]{rsIcontact.getString("Name"),rsIcontact.getString("Phone")});
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Provider.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
    }//GEN-LAST:event_tbInsuranceMouseClicked
    
    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        btnInsert.setEnabled(false);
        int i=tbInsurance.getSelectedRow();
        if(i>=0){
            try {
            rsContact.moveToInsertRow();
            rsContact.updateString("Insurance.ID", (String) model.getValueAt(i, 0));
            rsContact.updateString("Name",txtPerson.getText());
            rsContact.updateString("Phone",txtPhone.getText());
            rsContact.insertRow();
            contactModel.addRow(new Object[]{txtPerson.getText(),txtPhone.getText()});
        } catch (SQLException ex) {
            Logger.getLogger(Insurance.class.getName()).log(Level.SEVERE, null, ex);
        }
            txtPerson.setText(null);
            txtPhone.setText(null);
            txtPerson.setEnabled(false);txtPhone.setEnabled(false);
        }
    }//GEN-LAST:event_btnInsertActionPerformed
    int next=0;
    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        // TODO add your handling code here:
        int i=tbContact.getSelectedRow();
        if(i==-1){
            if(tbContact.getRowCount()==0){
                JOptionPane.showMessageDialog(null, "The table contain no data","Healthcare Service System",JOptionPane.OK_OPTION);
            }
            JOptionPane.showMessageDialog(null, "Please select data to delete","Healthcare Service System",JOptionPane.OK_OPTION);
        }
        else{ 
            try {
                stRemove.executeUpdate(sqlRemove+"'"+contactModel.getValueAt(i,1)+"'");
            } catch (SQLException ex) {
                Logger.getLogger(Provider.class.getName()).log(Level.SEVERE, null, ex);
            }
            contactModel.removeRow(i);
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        int i=tbInsurance.getSelectedRow();
        if(i>0){
            btnInsert.setEnabled(true);
            txtPerson.setText(null);txtPhone.setText(null);
            txtPerson.setEnabled(true);txtPhone.setEnabled(true);
            txtPerson.requestFocus();
            txtID.setText((String) model.getValueAt(i, 0));
        }else JOptionPane.showMessageDialog(null, "Select the contact to remove");
        
    }//GEN-LAST:event_btnAddActionPerformed
    // FUNCTIONS    
    private void refreshData(){
        Object[] o=new Object[3];
        try{
            rs=st.executeQuery(sql);
            while(rs.next()){
                o[0]=rs.getString("ID");
                o[1]=rs.getString("Name");
                o[2]=rs.getString("Addresss");
                model.addRow(o);
            }
        }catch(SQLException ex){
            Logger.getLogger(InsuranceTab.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void insertRowToTable(Object o1,Object o2,Object o3){
        Object[] n=new Object[3];
        n[0]=o1;n[1]=o2;n[2]=o3;
        model.addRow(n);
    }
    private void controlFormLoad(){
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        enableAllTextFields(false);
        clearAllTextFields();
        btnNew.requestFocus();
        btnSave.setText("Save");
        btnInsert.setEnabled(false);
    }
    private void enableAllTextFields(boolean b){
        txtID.setEnabled(b);
        txtName.setEnabled(b);
        txtAddress.setEnabled(b);
        txtPerson.setEnabled(b);
        txtPhone.setEnabled(b);
        btnNew.setEnabled(!b);
        btnSave.setEnabled(b);
        btnEdit.setEnabled(!b);
        btnDelete.setEnabled(!b);
        btnCancel.setEnabled(b);
        tbInsurance.setEnabled(!b);
    }
    private void clearAllTextFields(){
        txtID.setText(null);
        txtName.setText(null);
        txtAddress.setText(null);
        txtPerson.setText(null);
        txtPhone.setText(null);
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
            java.util.logging.Logger.getLogger(Insurance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Insurance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Insurance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Insurance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Insurance().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbCount;
    private javax.swing.JTable tbContact;
    private javax.swing.JTable tbInsurance;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPerson;
    private javax.swing.JTextField txtPhone;
    // End of variables declaration//GEN-END:variables
}
