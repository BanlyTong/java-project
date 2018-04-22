/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mizter Lee
 */
public class UpdatePlan extends javax.swing.JDialog {

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
    private String driver=Database.driver;
    private String url=Database.url;
    
    public UpdatePlan(java.awt.Frame parent, boolean modal){
        super(parent, modal);
        initComponents();
        
        SubTable.setTableHeader(tbPlan, new Color(240, 240, 240), Color.BLACK, new Font("Tahoma", Font.PLAIN, 12));
        SubTable.setDefaultTableRender(tbPlan);
        
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
                cbInsurance.addItem(rsInsur.getString("ID")+" => "+rsInsur.getString("Name"));
            }
            refreshData();
            
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
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
                            pro=rsHas.getString("ID")+" => "+rsHas.getString("Name");
                            cbInsurance.setSelectedItem(pro);
                        }else{JOptionPane.showMessageDialog(null, "Result set has no current row!");}
                        
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
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
                        String pro = "";
                        if(rsHas.next()){
                            pro=rsHas.getString("ID")+" => "+rsHas.getString("Name");
                            cbInsurance.setSelectedItem(pro);
                        }else{JOptionPane.showMessageDialog(null, "Result set has no current row!");}
                        
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
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
                        String pro = "";
                        if(rsHas.next()){
                            pro=rsHas.getString("ID")+" => "+rsHas.getString("Name");
                            cbInsurance.setSelectedItem(pro);
                        }else{JOptionPane.showMessageDialog(null, "Result set has no current row!");}
                        
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
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
            JOptionPane.showMessageDialog(null, ex);
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
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDesc = new javax.swing.JTextArea();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        cbInsurance = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Plan Data Entry");
        setResizable(false);

        tbPlan.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        tbPlan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbPlan.setRowHeight(20);
        tbPlan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPlanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbPlan);

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Add_New_20px.png"))); // NOI18N
        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Edit_20px.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Delete_20px.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Healthcare Plan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(255, 51, 51))); // NOI18N

        jLabel1.setText("ID:");

        jLabel2.setText("Name:");

        jLabel3.setText("Description:");

        txtDesc.setColumns(20);
        txtDesc.setLineWrap(true);
        txtDesc.setRows(5);
        jScrollPane2.setViewportView(txtDesc);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Save_20px.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Cancel_20px.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName)
                            .addComponent(txtID)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCancel, btnSave});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCancel, btnSave});

        jLabel4.setText("Insurance's Plan:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbInsurance, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDelete, btnEdit, btnNew});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cbInsurance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEdit)
                            .addComponent(btnDelete))))
                .addGap(0, 16, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDelete, btnEdit, btnNew});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
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
            JOptionPane.showMessageDialog(null, ex);
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
                    JOptionPane.showMessageDialog(null, ex);
                }
        
            JOptionPane.showMessageDialog(null,"Updated");
        }
        controlFormload();
        btnSave.setText("Save");
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int i = tbPlan.getSelectedRow();
        if(i == -1){
            if(tbPlan.getRowCount() == 0){
                JOptionPane.showMessageDialog(null, "The table contain no data","Information",JOptionPane.OK_OPTION);
            } else {
                JOptionPane.showMessageDialog(null, "Please select the data","Information",JOptionPane.OK_OPTION);
            }
        } else {
            enableControl(true);
            btnNew.setEnabled(false);
            btnSave.setText("Update");
            btnSave.setEnabled(true);
            btnDelete.setEnabled(false);
            btnCancel.setEnabled(true);
            tbPlan.setEnabled(false);
            txtID.setText((String) model.getValueAt(i, 0));
            txtName.setText((String) model.getValueAt(i, 1));
            txtDesc.setText((String) model.getValueAt(i, 2));
            btnEdit.setEnabled(false);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int i = tbPlan.getSelectedRow();
        if(i == -1){
            if(tbPlan.getRowCount() == 0){
                JOptionPane.showMessageDialog(null, "The table contain no data.","Information",JOptionPane.OK_OPTION);
            }
            JOptionPane.showMessageDialog(null, "Please select the data.","Information",JOptionPane.OK_OPTION);
        } else {
            if(JOptionPane.showConfirmDialog(null,"Are you sure to delele this record?","Delelte",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
                model.removeRow(i);//tbProvider.remove(i);
                
                boolean d = false;
                
                try {
                    rs.absolute(i + 1);
                    rs.deleteRow();
                    
                    d = rs.rowDeleted();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
                
                JOptionPane.showMessageDialog(null, "Deleted = " + d);
                
                refreshData();
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tbPlanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPlanMouseClicked

        if(tbPlan.getSelectedRow() >= 0){
            try {
                int index=tbPlan.getSelectedRow();
                String id=(String) model.getValueAt(index, 0);
                rsHas=stHas.executeQuery(sqlHas+"'"+id+"'");
                rsDesc=stDesc.executeQuery(sqlDesc+"'"+id+"'");
                String pro;
                if(rsHas.next()){
                    pro=rsHas.getString("ID")+" => "+rsHas.getString("Name");
                    cbInsurance.setSelectedItem(pro);
                }else{JOptionPane.showMessageDialog(null, "Result set has no current row!");}
                txtDesc.setText((String) model.getValueAt(index, 2));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
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
        cbInsurance.setEnabled(b);
    }
    private void controlFormload(){
        clearAllTextFields();
        enableControl(false);
        btnNew.setEnabled(true);
        btnSave.setEnabled(false);btnSave.setText("Save");
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
        btnCancel.setEnabled(false);
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdatePlan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            UpdatePlan dialog = new UpdatePlan(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
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
