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
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mizter Lee
 */
public class Doctor extends javax.swing.JFrame {

    /**
     * Creates new form Doctor
     */
    private DefaultTableModel model;
    private Statement st,stProvider,stWorkfor;
    private ResultSet rs,rsProvider,rsWorkfor;
    String sql="select * from Doctor";
    String sqlProvider="select * from Provider";
    String sqlWorkfor="select Doctor.[Provider.ID],Provider.Name \n" +
        "from Doctor inner join Provider on Doctor.[Provider.ID]=Provider.ID where Doctor.[ID]=";
            
    public Doctor() {
        initComponents();
        String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url="jdbc:sqlserver://localhost:1433;" +
                "databaseName=HealthCareService;user=sa;password=b@nlyonly;";
        
        try{
            Class.forName(driver);
            Connection con=DriverManager.getConnection(url);
            st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stProvider=con.createStatement();
            stWorkfor=con.createStatement();
            rsProvider=stProvider.executeQuery(sqlProvider);
            String p,n;
            while(rsProvider.next()){ 
                p=rsProvider.getString("ID");
                n=rsProvider.getString("NAME");
                cbProvider.addItem(p+"=>"+n);
            }
            model=(DefaultTableModel) tbDoctor.getModel();
           
        }catch(ClassNotFoundException | SQLException ex){
            Logger.getLogger(Plan.class.getName()).log(Level.SEVERE, null, ex);
        }
        refreshData();
        controlFormLoad();
        btnNew.requestFocus();
        gbGender.add(rbMale);gbGender.add(rbFemale);
        tbDoctor.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(tbDoctor.getSelectedRow()>=0){
                    try {
                        int index=tbDoctor.getSelectedRow();
                        String id=(String) model.getValueAt(index, 0);
                        rsWorkfor=stWorkfor.executeQuery(sqlWorkfor+"'"+id+"'");
                        String pro;
                        if(rsWorkfor.next()){
                            pro=rsWorkfor.getString("Provider.ID")+"=>"+rsWorkfor.getString("Name");
                            txtProvider.setText(pro);lbCount.setText(index+1+"/"+model.getRowCount());
                        }else{JOptionPane.showMessageDialog(null, "Result set has no current row!");}
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(tbDoctor.getSelectedRow()>=0){
                    try {
                        int index=tbDoctor.getSelectedRow();
                        String id=(String) model.getValueAt(index, 0);
                        rsWorkfor=stWorkfor.executeQuery(sqlWorkfor+"'"+id+"'");
                        String pro;
                        if(rsWorkfor.next()){
                            pro=rsWorkfor.getString("Provider.ID")+"=>"+rsWorkfor.getString("Name");
                            txtProvider.setText(pro);lbCount.setText(index+1+"/"+model.getRowCount());
                        }else{JOptionPane.showMessageDialog(null, "Result set has no current row!");}

                    } catch (SQLException ex) {
                        Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(tbDoctor.getSelectedRow()>=0){
                    try {
                        int index=tbDoctor.getSelectedRow();
                        String id=(String) model.getValueAt(index, 0);
                        rsWorkfor=stWorkfor.executeQuery(sqlWorkfor+"'"+id+"'");
                        String pro;
                        if(rsWorkfor.next()){
                            pro=rsWorkfor.getString("Provider.ID")+"=>"+rsWorkfor.getString("Name");
                            txtProvider.setText(pro);lbCount.setText(index+1+"/"+model.getRowCount());
                        }else{JOptionPane.showMessageDialog(null, "Result set has no current row!");}

                    } catch (SQLException ex) {
                        Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    private void refreshData(){
        Object[] o=new Object[8];
        model.setRowCount(0);
        try{
            rs=st.executeQuery(sql);
            while(rs.next()){
            o[0]=rs.getString("ID");
            o[1]=rs.getString("First name");
            o[2]=rs.getString("Last name");
            o[3]=rs.getString("Gender");
            o[4]=rs.getString("DOB");
            o[5]=rs.getString("Address");
            o[6]=rs.getString("Area");
            o[7]=rs.getString("Degree");
            model.addRow(o);
        }
        }catch(SQLException ex){
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
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDoctor = new javax.swing.JTable();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        txtAddress = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtLastname = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtFirstname = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtArea = new javax.swing.JTextField();
        txtDegree = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        dcDOB = new com.toedter.calendar.JDateChooser();
        rbMale = new javax.swing.JRadioButton();
        rbFemale = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        cbProvider = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtProvider = new javax.swing.JTextField();
        lbCount = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 204));

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

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        tbDoctor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Firstname", "Lastname", "Gender", "DOB", "Address", "Area", "Degree"
            }
        ));
        tbDoctor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDoctorMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDoctor);

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Doctor"));

        jLabel1.setText("ID");

        jLabel8.setText("Degree");

        jLabel4.setText("Gender");

        jLabel2.setText("First name");

        jLabel6.setText("Address");

        jLabel5.setText("DOB");

        jLabel3.setText("Last name");

        jLabel7.setText("Area");

        dcDOB.setDateFormatString("yyyy-MM-dd");
        dcDOB.setMaxSelectableDate(new java.util.Date(253370743277000L));
        dcDOB.setMinSelectableDate(new java.util.Date(-62135791123000L));

        rbMale.setText("Male");

        rbFemale.setText("Female");

        jLayeredPane1.setLayer(txtAddress, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txtID, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txtLastname, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txtFirstname, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txtArea, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txtDegree, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(dcDOB, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(rbMale, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(rbFemale, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(29, 29, 29)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtDegree, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                            .addComponent(txtArea, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAddress, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dcDOB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(rbMale)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbFemale))
                            .addComponent(txtLastname, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(7, 7, 7)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtLastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(rbMale)
                    .addComponent(rbFemale))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(dcDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDegree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jLabel9.setText("Provider");

        jLabel10.setText("Work for:");

        txtProvider.setEditable(false);

        lbCount.setText("Number");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLayeredPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addGap(28, 28, 28)
                        .addComponent(cbProvider, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(btnNew)
                .addGap(18, 18, 18)
                .addComponent(btnSave)
                .addGap(28, 28, 28)
                .addComponent(btnEdit)
                .addGap(18, 18, 18)
                .addComponent(btnDelete)
                .addGap(18, 18, 18)
                .addComponent(btnCancel)
                .addGap(18, 18, 18)
                .addComponent(btnExit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbCount, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbCount, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(cbProvider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExit)
                    .addComponent(btnCancel)
                    .addComponent(btnDelete)
                    .addComponent(btnEdit)
                    .addComponent(btnSave)
                    .addComponent(btnNew)
                    .addComponent(jLabel10)
                    .addComponent(txtProvider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

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
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        if(JOptionPane.showConfirmDialog(null,"Confirm if you want to exit!","Exit",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            this.dispose();
        }
        
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clearAllTextFields();
        enableAllTextFields(true);
        txtID.requestFocus();
        btnNew.setEnabled(false);
        btnSave.setEnabled(true);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        btnCancel.setEnabled(true);
        tbDoctor.setEnabled(false);
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
            String id=txtID.getText();
            String firstname=txtFirstname.getText();
            String lastname=txtLastname.getText();
            String gender;if(rbMale.isSelected()){gender=rbMale.getText();}else gender=rbFemale.getText();
            String dob=((JTextField)dcDOB.getDateEditor().getUiComponent()).getText();
            String add=txtAddress.getText();
            String area=txtArea.getText();
            String degree=txtDegree.getText();
            String provider=new StringTokenizer(cbProvider.getSelectedItem().toString(),"=>").nextToken();
        if(btnSave.getText().equalsIgnoreCase("Save")){
        try {
            rs.moveToInsertRow();
            rs.updateString("ID", id);
            rs.updateString("First name",firstname);
            rs.updateString("Last name",lastname);
            rs.updateString("Gender",gender);
            rs.updateString("DOB",dob);
            rs.updateString("Address",add);
            rs.updateString("Area", area);
            rs.updateString("Degree", degree);
            rs.updateString("Provider.ID",provider);
            rs.insertRow();
            Object[] o=new Object[]{id,firstname,lastname,gender,dob,add,area,degree};
            model.addRow(o);
        } catch (SQLException ex) {
            Logger.getLogger(DataEntryForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null,"Saved");
        }
        else if(btnSave.getText().equalsIgnoreCase("Update")){
            int index=tbDoctor.getSelectedRow();
                try{
                    rs.absolute(index+1);
                    rs.updateString("First name",firstname);
                    rs.updateString("Last name",lastname);
                    rs.updateString("Gender",gender);
                    rs.updateString("DOB",dob);
                    rs.updateString("Address",add);
                    rs.updateString("Area", area);
                    rs.updateString("Degree", degree);
                    rs.updateString("Provider.ID",provider);
                    rs.updateRow();
                    model.setValueAt(firstname, index, 1);
                    model.setValueAt(lastname,index,2);
                    model.setValueAt(gender, index,3);
                    model.setValueAt(dob,index,4);
                    model.setValueAt(add,index,5);
                    model.setValueAt(area, index,6);
                    model.setValueAt(degree,index,7);
                    
                }catch(SQLException ex){
                    Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
                }
            JOptionPane.showMessageDialog(null,"Updated");
        }
        controlFormLoad();
        btnSave.setText("Save");
        tbDoctor.setEnabled(true);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here: 
        int i=tbDoctor.getSelectedRow();
        if(i==-1){
            if(tbDoctor.getRowCount()==0){
                JOptionPane.showMessageDialog(null, "The table contain no data","Healthcare Service System",JOptionPane.OK_OPTION);
            }
            else JOptionPane.showMessageDialog(null, "Please select the data","Healthcare Service System",JOptionPane.OK_OPTION);
        }else{
        txtID.setEditable(false);
        btnSave.setText("Update");
        btnSave.setEnabled(true);
        btnNew.setEnabled(false);
        btnDelete.setEnabled(false);
        btnCancel.setEnabled(true);
        tbDoctor.setEnabled(false);
        enableAllTextFields(true);
        int index=tbDoctor.getSelectedRow();
        
        String pro=txtProvider.getText();
        for(int j=0;j<cbProvider.getItemCount();j++){
            if(cbProvider.getItemAt(j).equalsIgnoreCase(pro))cbProvider.setSelectedIndex(j);
        }
        
        try{
            rs.absolute(index+1);
            String id=rs.getString("ID");
            String firstname=rs.getString("First name");
            String lastname=rs.getString("Last name");
            String gender=rs.getString("Gender");
            Date dob=rs.getDate("DOB");
            String add=rs.getString("Address");
            String area=rs.getString("Area");
            String degree=rs.getString("Degree");
            txtID.setText(id);
            txtFirstname.setText(firstname);
            txtLastname.setText(lastname);
            if(gender.equalsIgnoreCase("Female")) rbFemale.setSelected(true);
            else rbMale.setSelected(true);
            dcDOB.setDate(dob);
            txtAddress.setText(add);
            txtArea.setText(area);
            txtDegree.setText(degree);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        btnEdit.setEnabled(false);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int i=tbDoctor.getSelectedRow();
        if(i==-1){
            if(tbDoctor.getRowCount()==0){
                JOptionPane.showMessageDialog(null, "The table contain no data","Healthcare Service System",JOptionPane.OK_OPTION);
            }
            JOptionPane.showMessageDialog(null, "Please select data to delete","Healthcare Service System",JOptionPane.OK_OPTION);
        }
        else{
            if(JOptionPane.showConfirmDialog(null,"Are you sure to delete?",
                    "Delete",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
                
        model.removeRow(i);//tbProvider.remove(i);
        boolean d=false;
        try{
            rs.absolute(i+1);
            rs.deleteRow();
            d=rs.rowDeleted();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null,"Deleted= "+d);
        refreshData();
        }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        if(JOptionPane.showConfirmDialog(null,"Are you sure to cancel?","Cancel",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            controlFormLoad();
        };
        
    }//GEN-LAST:event_btnCancelActionPerformed

    private void tbDoctorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDoctorMouseClicked
        if(tbDoctor.getSelectedRow()>=0){
        try {
            int index=tbDoctor.getSelectedRow();
            String id=(String) model.getValueAt(index, 0);
            rsWorkfor=stWorkfor.executeQuery(sqlWorkfor+"'"+id+"'");
            String pro;
            if(rsWorkfor.next()){
                pro=rsWorkfor.getString("Provider.ID")+"=>"+rsWorkfor.getString("Name");
                txtProvider.setText(pro);
                lbCount.setText(index+1+"/"+model.getRowCount());
            }else{JOptionPane.showMessageDialog(null, "Result set has no current row!");}
     
        } catch (SQLException ex) {
            Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }//GEN-LAST:event_tbDoctorMouseClicked

    private void controlFormLoad(){
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        enableAllTextFields(false);
        clearAllTextFields();
        btnNew.setEnabled(true);
        btnSave.setEnabled(false);
        btnSave.setText("Save");
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
        btnCancel.setEnabled(false);
        tbDoctor.setEnabled(true);
        tbDoctor.setFocusable(true);
    }
    private void enableAllTextFields(boolean b){
        txtID.setEnabled(b);
        txtFirstname.setEnabled(b);
        txtLastname.setEnabled(b);
        rbMale.setEnabled(b);rbFemale.setEnabled(b);
        dcDOB.setEnabled(b);
        txtAddress.setEnabled(b);
        txtArea.setEnabled(b);
        txtDegree.setEnabled(b);
        cbProvider.setEnabled(b);
    }
    private void clearAllTextFields(){
        txtID.setText(null);
        txtFirstname.setText(null);
        txtLastname.setText(null);
        //gbGender.setText(null);
        rbMale.setSelected(true);
        dcDOB.setDate(null);
        txtAddress.setText(null);
        txtArea.setText(null);
        txtDegree.setText(null);
        cbProvider.setSelectedIndex(0);
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
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Doctor().setVisible(true);
            }
        });
    }
    private ButtonGroup gbGender=new ButtonGroup();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbProvider;
    private com.toedter.calendar.JDateChooser dcDOB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbCount;
    private javax.swing.JRadioButton rbFemale;
    private javax.swing.JRadioButton rbMale;
    private javax.swing.JTable tbDoctor;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtArea;
    private javax.swing.JTextField txtDegree;
    private javax.swing.JTextField txtFirstname;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtLastname;
    private javax.swing.JTextField txtProvider;
    // End of variables declaration//GEN-END:variables
}
