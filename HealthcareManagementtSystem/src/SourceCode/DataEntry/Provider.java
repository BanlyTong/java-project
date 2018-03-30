/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.DataEntry;

import SourceCode.DataEntryForm;
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
public class Provider extends javax.swing.JFrame {

    /**
     * Creates new form Provider
     */
    private DefaultTableModel model,contactModel;
    private Statement st,stContact,stPcontact,stRemove;
    private ResultSet rs,rsContact,rsPcontact;
    String sql="select * from Provider";
    String sqlContact="select * from ProviderContact",
            sqlPcontact="select * from ProviderContact where [Provider.ID]=",
            sqlPcontactRemove="delete from ProviderContact where [Phone]=";
    public Provider() {
        initComponents();
        String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url="jdbc:sqlserver://localhost:1433;" +
                "databaseName=HealthCareService;user=sa;password=b@nlyonly;";
        
        try{
            Class.forName(driver);
            Connection con=DriverManager.getConnection(url);
            //String sql="select * from Insurance";
            st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stContact=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stPcontact=con.createStatement();
            stRemove=con.createStatement();
            rsContact=stContact.executeQuery(sqlContact);
            model=(DefaultTableModel) tbProvider.getModel();
            contactModel=(DefaultTableModel) tbContact.getModel();
        }catch(Exception ex){
            Logger.getLogger(DataEntryForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        controlFormLoad();
        refreshData();
        tbProvider.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                int i=tbProvider.getSelectedRow();
                if(i>=0){
                    lbCount.setText(i+1+"/"+model.getRowCount());
                    try {
                        rs.absolute(i+1);contactModel.setRowCount(0);
                        rsPcontact=stPcontact.executeQuery(sqlPcontact+"'"+rs.getString("ID")+"'");
                        while(rsPcontact.next()){
                            contactModel.addRow(new Object[]{rsPcontact.getString("Name"),rsPcontact.getString("Phone")});
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Provider.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int i=tbProvider.getSelectedRow();
                if(i>=0){
                    lbCount.setText(i+1+"/"+model.getRowCount());
                    try {
                        rs.absolute(i+1);
                        contactModel.setRowCount(0);
                        rsPcontact=stPcontact.executeQuery(sqlPcontact+"'"+rs.getString("ID")+"'");
                        while(rsPcontact.next()){
                            contactModel.addRow(new Object[]{rsPcontact.getString("Name"),rsPcontact.getString("Phone")});
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Provider.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int i=tbProvider.getSelectedRow();
                if(i>=0){
                    lbCount.setText(i+1+"/"+model.getRowCount());
                    try {
                        rs.absolute(i+1);
                        contactModel.setRowCount(0);
                        rsPcontact=stPcontact.executeQuery(sqlPcontact+"'"+rs.getString("ID")+"'");
                        while(rsPcontact.next()){
                            contactModel.addRow(new Object[]{rsPcontact.getString("Name"),rsPcontact.getString("Phone")});
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Provider.class.getName()).log(Level.SEVERE, null, ex);
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
            o[1]=rs.getString("NAME");
            o[2]=rs.getString("Address");
            model.addRow(o);
            }
        }catch(SQLException ex){
            Logger.getLogger(DataEntryForm.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbProvider = new javax.swing.JTable();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel4 = new javax.swing.JLabel();
        txtPerson = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnInsert = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lbCount = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbContact = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtAddress = new javax.swing.JTextField();
        btnRemove = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbProvider.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Address"
            }
        ));
        tbProvider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProviderMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbProvider);

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Contact"));
        jLayeredPane1.setName(""); // NOI18N

        jLabel4.setText("Tel:");

        jLabel5.setText("Person name:");

        btnInsert.setText("Insert");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txtPerson, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txtPhone, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(btnInsert, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jLayeredPane1Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtPerson, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnInsert)
                .addContainerGap())
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPerson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnInsert)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        btnExit.setBackground(new java.awt.Color(255, 0, 0));
        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        lbCount.setText("Number");

        tbContact.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Person", "Phone"
            }
        ));
        tbContact.setFocusable(false);
        jScrollPane2.setViewportView(tbContact);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Provider"));

        jLabel3.setText("Address:");

        jLabel2.setText("Name:");

        jLabel1.setText("ID:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAddress))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(22, 22, 22))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(38, 38, 38)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(41, 41, 41))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemove))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbCount, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLayeredPane1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(65, 65, 65))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(443, 443, 443)
                .addComponent(btnExit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(45, 45, 45)
                    .addComponent(btnNew)
                    .addGap(18, 18, 18)
                    .addComponent(btnSave)
                    .addGap(18, 18, 18)
                    .addComponent(btnEdit)
                    .addGap(18, 18, 18)
                    .addComponent(btnDelete)
                    .addGap(18, 18, 18)
                    .addComponent(btnCancel)
                    .addContainerGap(560, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbCount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemove))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(btnAdd)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExit)
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap(413, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnNew)
                        .addComponent(btnSave)
                        .addComponent(btnEdit)
                        .addComponent(btnDelete)
                        .addComponent(btnCancel))
                    .addGap(57, 57, 57)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        txtID.requestFocus();
        enableAllTextFields(true);
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(null,"Confirm if you want to exit!","Exit",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            this.dispose();
        }
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        String id=txtID.getText();
        if(btnSave.getText().equalsIgnoreCase("Save")){
        try {
            // TODO add your handling code here:
            //int index=tbInsurance.getSelectedRow();
            //Object[] o=new Object[3];
            
            String name=txtName.getText();
            String add=txtAddress.getText();
            rs.moveToInsertRow();
            rs.updateString("ID", id);
            rs.updateString("NAME",name);
            rs.updateString("Address",add);
            rs.insertRow();
            insertRowToTable(id,name,add);
            rsContact.moveToInsertRow();
            rsContact.updateString("Provider.ID",id);
            rsContact.updateString("Phone",txtPhone.getText());
            rsContact.updateString("Name",txtPerson.getText());
            rsContact.insertRow();
                
        } catch (SQLException ex) {
            Logger.getLogger(DataEntryForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null,"Saved");
        }
        else if(btnSave.getText().equalsIgnoreCase("Update")){
            int index=tbProvider.getSelectedRow();
            String name=txtName.getText();
            String add=txtAddress.getText();
            try{
                rs.absolute(index+1);
                rs.updateString("Name", name);
                rs.updateString("Address", add);
                rs.updateRow();
                model.setValueAt(name, index, 1);
                model.setValueAt(add,index,2);
            }catch(Exception ex){
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null,"Updated");
        }
        btnSave.setText("Save"); 
        controlFormLoad();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        txtID.setEditable(false);
        btnSave.setText("Update");
        btnSave.setEnabled(true);
        btnNew.setEnabled(false);
        btnDelete.setEnabled(false);
        btnCancel.setEnabled(true);
        tbProvider.setEnabled(false);
        enableAllTextFields(true);
        int index=tbProvider.getSelectedRow();
        try{
            rs.absolute(index+1);
            String id=rs.getString("ID");
            String name=rs.getString("NAME");
            String add=rs.getString("Address");
            txtID.setText(id);
            txtName.setText(name);
            txtAddress.setText(add);
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        btnEdit.setEnabled(false);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int i=tbProvider.getSelectedRow();
        if(i==-1){
            if(tbProvider.getRowCount()==0){
                JOptionPane.showMessageDialog(null, "The table contain no data","Healthcare Service System",JOptionPane.OK_OPTION);
            }
            JOptionPane.showMessageDialog(null, "Please select data to delete","Healthcare Service System",JOptionPane.OK_OPTION);
        }
        else{
            if(JOptionPane.showConfirmDialog(null,"Are you sure to delete?","Delete",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
        model.removeRow(i);
        boolean d=false;
        try{
            rs.absolute(i+1);
            rs.deleteRow();
            d=rs.rowDeleted();
            
        }catch(Exception ex){
            Logger.getLogger(InsuranceTab.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(null,"The data was related to other table in database");
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
    
    private void tbProviderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProviderMouseClicked
        // TODO add your handling code here:
        int i=tbProvider.getSelectedRow();
        if(i>=0){
            lbCount.setText(i+1+"/"+model.getRowCount());
            try {
                rs.absolute(i+1);
                contactModel.setRowCount(0);
                rsPcontact=stPcontact.executeQuery(sqlPcontact+"'"+rs.getString("ID")+"'");
                while(rsPcontact.next()){
                    contactModel.addRow(new Object[]{rsPcontact.getString("Name"),rsPcontact.getString("Phone")});
                }
            } catch (SQLException ex) {
                Logger.getLogger(Provider.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }//GEN-LAST:event_tbProviderMouseClicked

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        int i=tbProvider.getSelectedRow();
        if(i>=0){
            try {
            rsContact.moveToInsertRow();
            rsContact.updateString("Provider.ID", (String) model.getValueAt(i, 0));
            rsContact.updateString("Name",txtPerson.getText());
            rsContact.updateString("Phone",txtPhone.getText());
            rsContact.insertRow();
            contactModel.addRow(new Object[]{txtPerson.getText(),txtPhone.getText()});
        } catch (SQLException ex) {
            Logger.getLogger(Insurance.class.getName()).log(Level.SEVERE, null, ex);
        }
            txtPerson.setText(null);txtPerson.setEnabled(false);
            txtPhone.setText(null);txtPhone.setEnabled(false);
            txtPhone.requestFocus();
            JOptionPane.showMessageDialog(null,"Inserted");
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        // TODO add your handling code here:
        int i=tbContact.getSelectedRow();
        if(i>=0){ 
            try {
                stRemove.executeUpdate(sqlPcontactRemove+"'"+contactModel.getValueAt(i,1)+"'");
            } catch (SQLException ex) {
                Logger.getLogger(Provider.class.getName()).log(Level.SEVERE, null, ex);
            }
            contactModel.removeRow(i);
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        txtPerson.setEnabled(true);btnInsert.setEnabled(true);
        txtPhone.setEnabled(true);txtPhone.requestFocus();
        txtPhone.requestFocus();
    }//GEN-LAST:event_btnAddActionPerformed
    private void insertRowToTable(Object o1,Object o2,Object o3){
        Object[] n=new Object[3];
        n[0]=o1;n[1]=o2;n[2]=o3;
        model.addRow(n);
    }
    private void clearAllTextFields(){
        txtID.setText(null);
        txtName.setText(null);
        txtAddress.setText(null);
        txtPhone.setText(null);
        txtPerson.setText(null);
    }
    private void enableAllTextFields(boolean b){
        txtID.setEnabled(b);
        txtName.setEnabled(b);
        txtAddress.setEnabled(b);
        txtPhone.setEnabled(b);
        txtPerson.setEnabled(b);
        btnNew.setEnabled(!b);
        btnDelete.setEnabled(!b);
        btnSave.setEnabled(b);
        btnCancel.setEnabled(b);
        btnEdit.setEnabled(!b);
        tbProvider.setEnabled(!b);
        tbProvider.setFocusable(!b);
    }
    private void controlFormLoad(){
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        clearAllTextFields();
        enableAllTextFields(false);
        txtID.setEditable(true);
        btnSave.setText("Save");
        btnInsert.setEnabled(false);
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
            java.util.logging.Logger.getLogger(Provider.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Provider.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Provider.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Provider.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Provider().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbCount;
    private javax.swing.JTable tbContact;
    private javax.swing.JTable tbProvider;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPerson;
    private javax.swing.JTextField txtPhone;
    // End of variables declaration//GEN-END:variables
}
