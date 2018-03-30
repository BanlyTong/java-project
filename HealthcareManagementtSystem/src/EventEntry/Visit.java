 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventEntry;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Mizter Lee
 */
public class Visit extends javax.swing.JFrame {

    /**
     * Creates new form Visit
     */
    private Connection con;
    private Statement st,stPatient,stProvider,stDoctor,stDisease;
    private ResultSet rs,rsPatient,rsProvider,rsDoctor,rsDisease;
    private DefaultTableModel model;
    private String sql="select * from Visit",
            sqlPatient="select Ssn,[First Name],[Last Name] from [Patient]",
            sqlProvider="select * from Provider",
            sqlDoctor="select * from Doctor where [Provider.ID]=",
            sqlDisease="select [Disease.ID] from Cure where [Doctor.ID]=";
    
    public Visit() {
        initComponents();
        String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url="jdbc:sqlserver://localhost:1433;" +
                "databaseName=HealthCareService;user=sa;password=b@nlyonly;";
        try {
            Class.forName(driver);
            con=DriverManager.getConnection(url);
            st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stProvider=con.createStatement();
            stPatient=con.createStatement();
            stDoctor=con.createStatement();
            stDisease=con.createStatement();
            model=(DefaultTableModel) tbVisit.getModel();
            String p,n;
            rsProvider=stProvider.executeQuery(sqlProvider);
            while(rsProvider.next()){ 
                p=rsProvider.getString("ID");
                n=rsProvider.getString("NAME");
                cbProvider.addItem(p+"=>"+n);
            }
            rsPatient=stPatient.executeQuery(sqlPatient);
            while(rsPatient.next()){
                cbPatient.addItem(rsPatient.getString("Ssn")+'('+rsPatient.getString("Last Name")+rsPatient.getString("First Name")+')');
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Visit.class.getName()).log(Level.SEVERE, null, ex);
        }
        controlFormLoad();
        refreshData();
        tbVisit.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                int i=tbVisit.getSelectedRow();
                    if(i>=0){
                        try {
                            rs.absolute(i+1);
                            txtDisease.setText(rs.getString("Disease.ID"));
                            txtProvider.setText(rs.getString("Provider.ID"));
                            txtDoctor.setText(rs.getString("Doctor.ID"));
                        } catch (SQLException ex) {
                            Logger.getLogger(Visit.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }            
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int i=tbVisit.getSelectedRow();
                    if(i>=0){
                        try {
                            rs.absolute(i+1);
                            txtDisease.setText(rs.getString("Disease.ID"));
                            txtProvider.setText(rs.getString("Provider.ID"));
                            txtDoctor.setText(rs.getString("Doctor.ID"));
                        } catch (SQLException ex) {
                            Logger.getLogger(Visit.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int i=tbVisit.getSelectedRow();
                    if(i>=0){
                        try {
                            rs.absolute(i+1);
                            txtDisease.setText(rs.getString("Disease.ID"));
                            txtProvider.setText(rs.getString("Provider.ID"));
                            txtDoctor.setText(rs.getString("Doctor.ID"));
                        } catch (SQLException ex) {
                            Logger.getLogger(Visit.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
            }
        });
    }
    private void refreshData(){
        Object[] o=new Object[12];
        model.setRowCount(0);
        try {
            rs=st.executeQuery(sql);
            while(rs.next()){
                o[0]=rs.getString("Visit#");
                o[1]=rs.getString("Patient.Ssn");
                o[2]=rs.getString("Date");
                o[3]=rs.getString("Desc.Symtom");
                o[4]=rs.getString("Charge");
                o[5]=rs.getString("Patient Pay");
                o[6]=rs.getString("Insurance Pay");
                o[7]=rs.getString("Comment");
                //o[7]=rs.getString("Provider.ID");
                //o[8]=rs.getString("Doctor.ID");
                //o[9]=rs.getString("Disease.ID");
                
                model.addRow(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Visit.class.getName()).log(Level.SEVERE, null, ex);
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
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtInsurancePay = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cbDoctor = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtCharge = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cbDisease = new javax.swing.JComboBox<>();
        cbPatient = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbProvider = new javax.swing.JComboBox<>();
        txtPatientPay = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDescSymtom = new javax.swing.JTextField();
        txtVisit = new javax.swing.JTextField();
        txtComment = new javax.swing.JTextField();
        dcDate = new com.toedter.calendar.JDateChooser();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbVisit = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtProvider = new javax.swing.JTextField();
        txtDoctor = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtDisease = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Information"));

        jLabel10.setText("Disease");

        jLabel8.setText("Provider");

        jLabel5.setText("Charge");

        jLabel7.setText("Insurance Pay");

        jLabel11.setText("Comment");

        cbDoctor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbDoctorItemStateChanged(evt);
            }
        });

        jLabel3.setText("Date");

        jLabel2.setText("Patient.Ssn");

        jLabel1.setText("Visit#");

        jLabel9.setText("Doctor");

        jLabel4.setText("Desc. Symtom");

        cbProvider.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbProviderItemStateChanged(evt);
            }
        });

        jLabel6.setText("Patient Pay");

        dcDate.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescSymtom)
                            .addComponent(cbDisease, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbDoctor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbProvider, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dcDate, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtVisit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCharge, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPatientPay, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtInsurancePay, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtComment, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 60, Short.MAX_VALUE)))
                        .addGap(28, 28, 28))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cbPatient, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVisit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbPatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescSymtom))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCharge))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPatientPay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtInsurancePay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbProvider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbDisease, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtComment))
                .addContainerGap())
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

        tbVisit.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Visit#", "Patient.Ssn", "Date", "Desc. Symtom", "Charge", "Patient Pay", "Insurance Pay", "Comment"
            }
        ));
        tbVisit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbVisitMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbVisit);

        jLabel12.setText("Provider");

        jLabel13.setText("Doctor");

        jLabel14.setText("Disease");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 743, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(btnNew)
                                .addGap(18, 18, 18)
                                .addComponent(btnSave)
                                .addGap(18, 18, 18)
                                .addComponent(btnEdit)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancel)
                                .addGap(28, 28, 28)
                                .addComponent(btnClose)
                                .addGap(35, 35, 35)
                                .addComponent(jLabel12))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(536, 536, 536)
                                .addComponent(jLabel13))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(536, 536, 536)
                                .addComponent(jLabel14)))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDisease, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtDisease, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete)
                    .addComponent(btnSave)
                    .addComponent(btnNew)
                    .addComponent(btnCancel)
                    .addComponent(btnClose)
                    .addComponent(jLabel12)
                    .addComponent(txtProvider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
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

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        enableControl(true);
        txtVisit.requestFocus();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        //controlFormLoad();
        String visit=txtVisit.getText();
        String patient=new StringTokenizer(cbPatient.getSelectedItem().toString(),"(").nextToken();
        String date=((JTextField)dcDate.getDateEditor().getUiComponent()).getText();
        String descSymtom=txtDescSymtom.getText();
        String charge=txtCharge.getText();
        String patientPay=txtPatientPay.getText();
        String insurancePay=txtInsurancePay.getText();
        String provider=new StringTokenizer(cbProvider.getSelectedItem().toString(),"=>").nextToken();
        String doctor=new StringTokenizer(cbDoctor.getSelectedItem().toString(),"(").nextToken();
        String disease=new StringTokenizer(cbDisease.getSelectedItem().toString(),"(").nextToken();
        String comment=txtComment.getText();
        if(btnSave.getText().equalsIgnoreCase("Save")){
        try {
            rs.moveToInsertRow();
            rs.updateString("Visit#", visit);
            rs.updateString("Patient.Ssn", patient);
            rs.updateString("Date", date);
            rs.updateString("Desc.Symtom",descSymtom);
            rs.updateString("Charge", charge);
            rs.updateString("Patient Pay", patientPay);
            rs.updateString("Insurance Pay", insurancePay);
            rs.updateString("Provider.ID", provider);
            rs.updateString("Doctor.ID", doctor);
            rs.updateString("Disease.ID", disease);
            rs.updateString("Comment", comment);
            rs.insertRow();
            model.addRow(new Object[]{visit,patient,date,descSymtom,charge,patientPay,insurancePay,comment});
        } catch (SQLException ex) {
            Logger.getLogger(Visit.class.getName()).log(Level.SEVERE, null, ex);
        }
        }else if(btnSave.getText().equalsIgnoreCase("Update")){
            int i=tbVisit.getSelectedRow();
            try {
                rs.absolute(i+1);
                rs.updateString("Visit#", visit);
                rs.updateString("Patient.Ssn", patient);
                rs.updateString("Date", date);
                rs.updateString("Desc.Symtom",descSymtom);
                rs.updateString("Charge", charge);
                rs.updateString("Patient Pay", patientPay);
                rs.updateString("Insurance Pay", insurancePay);
                rs.updateString("Provider.ID", provider);
                rs.updateString("Doctor.ID", doctor);
                rs.updateString("Disease.ID", disease);
                rs.updateString("Comment", comment);
                rs.updateRow();
                model.setValueAt(patient, i, 1);
                model.setValueAt(date, i, 2); 
                model.setValueAt(descSymtom, i, 3);
                model.setValueAt(charge, i, 4);
                model.setValueAt(patientPay, i, 5);
                model.setValueAt(insurancePay, i, 6);
                model.setValueAt(comment, i, 7);
                txtProvider.setText(provider);
                txtDoctor.setText(doctor);
                txtDisease.setText(disease);
            } catch (SQLException ex) {
                Logger.getLogger(Visit.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        controlFormLoad();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        
        int i=tbVisit.getSelectedRow();
        if(i>=0){
            enableControl(true);txtVisit.setEditable(false);
            txtVisit.setToolTipText("You can't edit Visit#");
            btnSave.setText("Update");
            txtVisit.setText((String) model.getValueAt(i, 0));
            cbPatient.setSelectedItem(model.getValueAt(i, 1));
            ((JTextField)dcDate.getDateEditor().getUiComponent()).setText((String) model.getValueAt(i, 2));
            txtDescSymtom.setText((String) model.getValueAt(i, 3));
            txtCharge.setText((String) model.getValueAt(i, 4));
            txtPatientPay.setText((String) model.getValueAt(i, 5));
            txtInsurancePay.setText((String) model.getValueAt(i, 6));
            txtComment.setText((String) model.getValueAt(i, 7));
            for(int j=0;j<cbProvider.getItemCount();j++)
                if(cbProvider.getItemAt(j).startsWith(txtProvider.getText()))
                cbProvider.setSelectedIndex(j);
            cbDoctor.setSelectedItem(txtDoctor.getText());
            cbDisease.setSelectedItem(txtDisease.getText());
        }else JOptionPane.showMessageDialog(null, "There is no info is selected!");
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int i=tbVisit.getSelectedRow();boolean d=false;
        if(i>=0){
            model.removeRow(i);
            try {
                rs.absolute(i+1);
                rs.deleteRow();
                d=rs.rowDeleted();
            } catch (SQLException ex) {
                Logger.getLogger(Visit.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(d==true){
                JOptionPane.showMessageDialog(null,"Deleted");
                refreshData();
            }else JOptionPane.showMessageDialog(null,"Delete=false");
        }
        else JOptionPane.showMessageDialog(null, "There is no info is selected!");
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(null, "Are you sure to cancel ?","Cancel",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            controlFormLoad();
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(null, "Are you sure to close ?","Close",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            this.dispose();
        }
    }//GEN-LAST:event_btnCloseActionPerformed

    private void tbVisitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbVisitMouseClicked
        // TODO add your handling code here:
        int i=tbVisit.getSelectedRow();
        if(i>=0){
            try {
                rs.absolute(i+1);
                txtDisease.setText(rs.getString("Disease.ID"));
                txtProvider.setText(rs.getString("Provider.ID"));
                txtDoctor.setText(rs.getString("Doctor.ID"));
            } catch (SQLException ex) {
                Logger.getLogger(Visit.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }//GEN-LAST:event_tbVisitMouseClicked

    private void cbProviderItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbProviderItemStateChanged
        // TODO add your handling code here:
        String id=new StringTokenizer(cbProvider.getSelectedItem().toString(),"=>").nextToken();
        cbDoctor.removeAllItems();
        try {
            rsDoctor=stDoctor.executeQuery(sqlDoctor+"'"+id+"'");
            while(rsDoctor.next()){
                cbDoctor.addItem(rsDoctor.getString("ID")+'('+rsDoctor.getString("First name")+rsDoctor.getString("Last name")+')');
            }
        } catch (SQLException ex) {
            Logger.getLogger(Visit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cbProviderItemStateChanged

    private void cbDoctorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbDoctorItemStateChanged
        // TODO add your handling code here:
        if(cbDoctor.getItemCount()>0){
            String id=new StringTokenizer(cbDoctor.getSelectedItem().toString(),"(").nextToken();
            cbDisease.removeAllItems();
            try {
                rsDisease=stDisease.executeQuery(sqlDisease+"'"+id+"'");
                while(rsDisease.next()){
                    cbDisease.addItem(rsDisease.getString("Disease.ID"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(Visit.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_cbDoctorItemStateChanged
    private void clearAllTextFields(){
        txtVisit.setText(null);
        txtDescSymtom.setText(null);
        txtCharge.setText(null);
        txtPatientPay.setText(null);
        txtInsurancePay.setText(null);
        txtComment.setText(null);
    }
    private void enableControl(boolean b){
        tbVisit.setEnabled(!b);
        txtVisit.setEnabled(b);
        cbPatient.setEnabled(b);
        dcDate.setEnabled(b);
        txtDescSymtom.setEnabled(b);
        txtCharge.setEnabled(b);
        txtPatientPay.setEnabled(b);
        txtInsurancePay.setEnabled(b);
        cbProvider.setEnabled(b);
        cbDoctor.setEnabled(b);
        cbDisease.setEnabled(b);
        txtComment.setEnabled(b);
        btnNew.setEnabled(!b);
        btnSave.setEnabled(b);
        btnEdit.setEnabled(!b);
        btnDelete.setEnabled(!b);
        btnCancel.setEnabled(b);
    }
    private void controlFormLoad(){
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        enableControl(false);
        clearAllTextFields();
        btnSave.setText("Save");
        txtVisit.setEditable(true);
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
            java.util.logging.Logger.getLogger(Visit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Visit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Visit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Visit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Visit().setVisible(true);
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
    private javax.swing.JComboBox<String> cbDisease;
    private javax.swing.JComboBox<String> cbDoctor;
    private javax.swing.JComboBox<String> cbPatient;
    private javax.swing.JComboBox<String> cbProvider;
    private com.toedter.calendar.JDateChooser dcDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbVisit;
    private javax.swing.JTextField txtCharge;
    private javax.swing.JTextField txtComment;
    private javax.swing.JTextField txtDescSymtom;
    private javax.swing.JTextField txtDisease;
    private javax.swing.JTextField txtDoctor;
    private javax.swing.JTextField txtInsurancePay;
    private javax.swing.JTextField txtPatientPay;
    private javax.swing.JTextField txtProvider;
    private javax.swing.JTextField txtVisit;
    // End of variables declaration//GEN-END:variables
}
