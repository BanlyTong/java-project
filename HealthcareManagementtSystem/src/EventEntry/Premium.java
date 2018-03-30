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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mizter Lee
 */
public class Premium extends javax.swing.JFrame {

    /**
     * Creates new form Premium
     */
    private Connection con;
    private Statement st,stTransaction,stPatient;
    private ResultSet rs,rsTransaction,rsPatient;
    private DefaultTableModel model;
    private String sql="select * from Premium",
                    sqlTransaction="select * from [Transaction]",
                    sqlPatient="select A.[Patient.Ssn],B.[First Name]+' '+B.[Last Name] as [Full name]\n" +
                    "from [Transaction] A inner join Patient B on A.[Patient.Ssn]=B.Ssn\n" +
                    "where A.[Transaction#]=";                
    public Premium() {
        initComponents();
        String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url="jdbc:sqlserver://localhost:1433;"+
                "databaseName=HealthCareService;user=sa;password=b@nlyonly;";
        try {
            Class.forName(driver);
            con=DriverManager.getConnection(url);
            st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stTransaction=con.createStatement();
            stPatient=con.createStatement();
            model=(DefaultTableModel) tbPremium.getModel();
            rsTransaction=stTransaction.executeQuery(sqlTransaction);
            while(rsTransaction.next()){
                cbTransaction.addItem(rsTransaction.getString("Transaction#"));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Premium.class.getName()).log(Level.SEVERE, null, ex);
        }
        controlFormLoad();
        refreshData();
        tableEventKey();
    }
    private void tableEventKey(){
        tbPremium.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                int i=tbPremium.getSelectedRow();
                if(i>=0){
                    lbCount.setText(i+1+"/"+model.getRowCount());
                    try {
                        rsPatient=stPatient.executeQuery(sqlPatient+"'"+model.getValueAt(i, 5)+"'");
                        if(rsPatient.next())
                        txtPatient.setText(rsPatient.getString("Patient.Ssn")+"("+rsPatient.getString("Full name")+")");
                    } catch (SQLException ex) {
                        Logger.getLogger(Premium.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int i=tbPremium.getSelectedRow();
                if(i>=0){
                    lbCount.setText(i+1+"/"+model.getRowCount());
                    try {
                        rsPatient=stPatient.executeQuery(sqlPatient+"'"+model.getValueAt(i, 5)+"'");
                        if(rsPatient.next())
                        txtPatient.setText(rsPatient.getString("Patient.Ssn")+"("+rsPatient.getString("Full name")+")");
                    } catch (SQLException ex) {
                        Logger.getLogger(Premium.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int i=tbPremium.getSelectedRow();
                if(i>=0){
                    lbCount.setText(i+1+"/"+model.getRowCount());
                    try {
                        rsPatient=stPatient.executeQuery(sqlPatient+"'"+model.getValueAt(i, 5)+"'");
                        if(rsPatient.next())
                        txtPatient.setText(rsPatient.getString("Patient.Ssn")+"("+rsPatient.getString("Full name")+")");
                    } catch (SQLException ex) {
                        Logger.getLogger(Premium.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    private void refreshData(){
        Object[] o=new Object[6];
        model.setRowCount(0);
        try {
            rs=st.executeQuery(sql);
            while(rs.next()){
                o[0]=rs.getString("Premium#");
                o[1]=rs.getString("Date");
                o[2]=rs.getString("Time");
                o[3]=rs.getString("Amount Paid");
                o[4]=rs.getString("Month");
                o[5]=rs.getString("Transaction#");
                model.addRow(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Premium.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void clearControls(){
        txtPremium.setText(null);
        dcDate.setDate(null);
        txtTime.setText(null);
        txtAmount.setText(null);
        txtMonth.setText(null);
        
    }
    private void enableControl(boolean b){
        txtPremium.setEnabled(b);
        dcDate.setEnabled(b);
        txtTime.setEnabled(b);
        txtAmount.setEnabled(b);
        txtMonth.setEnabled(b);
        cbTransaction.setEnabled(b);
        btnNew.setEnabled(!b);
        btnSave.setEnabled(b);
        btnEdit.setEnabled(!b);
        btnDelete.setEnabled(!b);
        btnCancel.setEnabled(b);
    }
    private void controlFormLoad(){
        setLocationRelativeTo(null);
        clearControls();
        enableControl(false);
        btnNew.requestFocus();
        btnSave.setText("Save");
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
        tbPremium = new javax.swing.JTable();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMonth = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPremium = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTime = new javax.swing.JTextField();
        dcDate = new com.toedter.calendar.JDateChooser();
        cbTransaction = new javax.swing.JComboBox<>();
        txtPatient1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        lbCount = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtPatient = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbPremium.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Premium", "Date", "Time", "Amount", "Month", "Transaction"
            }
        ));
        tbPremium.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPremiumMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbPremium);

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

        jLabel2.setText("Date");

        jLabel5.setText("Month");

        jLabel1.setText("Premium");

        jLabel3.setText("Time");

        jLabel6.setText("Transaction.ID");

        jLabel4.setText("Amount");

        dcDate.setDateFormatString("yyyy-MM-dd");

        cbTransaction.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTransactionItemStateChanged(evt);
            }
        });
        cbTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTransactionActionPerformed(evt);
            }
        });

        jLabel8.setText("Patient");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPatient1)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(18, 18, 18)
                                    .addComponent(cbTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                        .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel2))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtPremium)
                                            .addComponent(dcDate, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))))))
                        .addContainerGap(40, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPremium, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(dcDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addGap(1, 1, 1)
                .addComponent(txtPatient1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        lbCount.setText("Number");

        jLabel7.setText("Patient");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(btnNew)
                                .addGap(43, 43, 43)
                                .addComponent(btnSave)
                                .addGap(43, 43, 43)
                                .addComponent(btnEdit))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(btnDelete)
                                .addGap(43, 43, 43)
                                .addComponent(btnCancel)
                                .addGap(43, 43, 43)
                                .addComponent(btnClose)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(txtPatient))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbCount)))
                .addGap(28, 28, 28))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbCount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNew, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSave, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEdit, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCancel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnClose)
                        .addComponent(jLabel7)
                        .addComponent(txtPatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(45, 45, 45))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(null,"Confirm if you want to close!","Close",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            this.dispose();
        }
    }//GEN-LAST:event_btnCloseActionPerformed

    private void tbPremiumMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPremiumMouseClicked
        // TODO add your handling code here:
        int i=tbPremium.getSelectedRow();
        if(i>=0){
            lbCount.setText(i+1+"/"+model.getRowCount());
            try {
                rsPatient=stPatient.executeQuery(sqlPatient+"'"+model.getValueAt(i, 5)+"'");
                if(rsPatient.next())
                txtPatient.setText(rsPatient.getString("Patient.Ssn")+"("+rsPatient.getString("Full name")+")");
            } catch (SQLException ex) {
                Logger.getLogger(Premium.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_tbPremiumMouseClicked

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        enableControl(true);
        clearControls();
        txtPremium.requestFocus();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        String premium=txtPremium.getText();
        String date=((JTextField)dcDate.getDateEditor().getUiComponent()).getText();
        String time=txtTime.getText();
        String amount=txtAmount.getText();
        String month=txtMonth.getText();
        String transaction=cbTransaction.getSelectedItem().toString();
        if(btnSave.getText().equalsIgnoreCase("Save")){
            try {
                rs.moveToInsertRow();
                rs.updateString("Premium#",premium);
                rs.updateString("Date", date);
                rs.updateString("Time",time);
                rs.updateString("Amount Paid", amount);
                rs.updateString("Month",month);
                rs.updateString("Transaction#", transaction);
                rs.insertRow();
                model.addRow(new Object[]{premium,date,time,amount,month,transaction});
            } catch (SQLException ex) {
                Logger.getLogger(Premium.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(btnSave.getText().equalsIgnoreCase("Update")){
            int i=tbPremium.getSelectedRow();
            try {
                rs.absolute(i+1);
                rs.updateString("Date", date);
                rs.updateString("Time", time);
                rs.updateString("Amount Paid", amount);
                rs.updateString("Month", month);
                rs.updateString("Transaction#", transaction);
                model.setValueAt(date, i, 1);
                model.setValueAt(time, i, 2);
                model.setValueAt(amount, i, 3);
                model.setValueAt(month, i, 4);
                model.setValueAt(transaction, i, 5);
            } catch (SQLException ex) {
                Logger.getLogger(Premium.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        controlFormLoad();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(null,"Are you sure to cancel?","Cancel",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            controlFormLoad();
        };
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        int i=tbPremium.getSelectedRow();
        if(i==-1){
            if(tbPremium.getRowCount()==0){
                JOptionPane.showMessageDialog(null, "The table contain no data","Healthcare Service System",JOptionPane.OK_OPTION);
            }
            else JOptionPane.showMessageDialog(null, "Please select the data","Healthcare Service System",JOptionPane.OK_OPTION);
        }else{
            clearControls();
            enableControl(true);btnSave.setText("Update");
            txtPremium.setText((String) model.getValueAt(i,0));
            ((JTextField)dcDate.getDateEditor().getUiComponent()).setText((String) model.getValueAt(i, 1));
            txtTime.setText((String) model.getValueAt(i, 2));
            txtAmount.setText((String) model.getValueAt(i, 3));
            txtMonth.setText((String) model.getValueAt(i, 4));
            cbTransaction.setSelectedItem(model.getValueAt(i, 5));
        }  
        
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int i=tbPremium.getSelectedRow();boolean d=false;
        if(i==-1){
            if(tbPremium.getRowCount()==0){
                JOptionPane.showMessageDialog(null, "The table contain no data","Healthcare Service System",JOptionPane.OK_OPTION);
            }
            JOptionPane.showMessageDialog(null, "Please select data to delete","Healthcare Service System",JOptionPane.OK_OPTION);
        }
        else{
            try {
                //model.removeRow(i);
                rs.absolute(i+1);
                rs.deleteRow();
                d=rs.rowDeleted();
            } catch (SQLException ex) {
                Logger.getLogger(Premium.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null,"Delete="+d);
            if(d) refreshData();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void cbTransactionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTransactionItemStateChanged
        // TODO add your handling code here:
        
        try {
                rsPatient=stPatient.executeQuery(sqlPatient+"'"+cbTransaction.getSelectedItem().toString()+"'");
                if(rsPatient.next())
                txtPatient1.setText(rsPatient.getString("Patient.Ssn")+"("+rsPatient.getString("Full name")+")");
            } catch (SQLException ex) {
                Logger.getLogger(Premium.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_cbTransactionItemStateChanged

    private void cbTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTransactionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTransactionActionPerformed

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
            java.util.logging.Logger.getLogger(Premium.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Premium.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Premium.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Premium.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Premium().setVisible(true);
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
    private javax.swing.JComboBox<String> cbTransaction;
    private com.toedter.calendar.JDateChooser dcDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbCount;
    private javax.swing.JTable tbPremium;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtMonth;
    private javax.swing.JTextField txtPatient;
    private javax.swing.JTextField txtPatient1;
    private javax.swing.JTextField txtPremium;
    private javax.swing.JTextField txtTime;
    // End of variables declaration//GEN-END:variables
}
