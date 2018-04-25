package a3;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Form5 extends javax.swing.JFrame {

    private Connection con;
    private Statement stmt;
    private ResultSet rsDoc;
    private DefaultTableModel model;
    
    public Form5() {
        initComponents();
        setLocationRelativeTo(null);                
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;"
                    +"databasename=HealthCareService;user=vin;password=123;");
            stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rsDoc=stmt.executeQuery("select *from Doctor");
            while(rsDoc.next())
                cboDoc.addItem(rsDoc.getString("First name")+" "+rsDoc.getString("Last name"));
            
        }catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void findProviderName(){
        //Open Query select Provider Name
        if(cboDoc.getItemCount()<0) return;
        try{        
        rsDoc.absolute(cboDoc.getSelectedIndex()+1);
        Statement stmtPro = con.createStatement();
        ResultSet rsPro=stmtPro.executeQuery("Select *from Provider\n" 
                +"where Provider.ID='"+rsDoc.getString("Provider.ID")+"'");
        while(rsPro.next())
            txtPro.setText(rsPro.getString("Name"));
        rsPro.close();        
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cboDoc = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbInfo = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtPro = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Doctor");

        cboDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDocActionPerformed(evt);
            }
        });

        tbInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbInfo);

        jLabel2.setText("Provider Name");

        txtPro.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPro, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(cboDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 739, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDocActionPerformed
        model = new DefaultTableModel();
        model.addColumn("Patient ID");
        model.addColumn("Patien Name");
        model.addColumn("Disease");
        model.addColumn("Medication");
        String mcName="";
        if(cboDoc.getItemCount()<0) return;
        try{
            rsDoc.absolute(cboDoc.getSelectedIndex()+1);
            Statement stmtPt=con.createStatement();
            ResultSet rsPt=stmtPt.executeQuery("select vt.[Patient.Ssn] as [ptID],"+
                    "pt.[First Name]+' '+ pt.[Last Name] as [ptName], ds.Name as [dsName], ds.ID as [dsID]\n" +
                    "from Visit vt inner join Patient pt on vt.[Patient.Ssn] = pt.Ssn\n" +
                    "inner join Disease ds on ds.ID=vt.[Disease.ID]\n" +
                    "where vt.[Doctor.ID]='"+rsDoc.getString("ID")+"'");
            
            
            Statement stmtMd; ResultSet rsMd;
            Statement stmtMc; ResultSet rsMc;
            
            while(rsPt.next()){
                stmtMd=con.createStatement();
                rsMd=stmtMd.executeQuery("select Medication.[Medicine#] as [mcID]\n" +
                                        "from Medication\n" +
                                        "where Medication.[Disease.ID]='"+rsPt.getString("dsID")+"'");
                while(rsMd.next()){
                    stmtMc=con.createStatement();
                    rsMc=stmtMc.executeQuery("select Medicine.Name as [mcName]\n" +
                                            "from Medicine\n" +
                                        "where Medicine.[Medicine#]='"+rsMd.getString("mcID")+"'");  
                    
                    while(rsMc.next()){
                        mcName+=rsMc.getString("mcName")+",";
                    }                    
                }    
            model.addRow(new Object[]{
            rsPt.getString("ptID"),
            rsPt.getString("ptName"),
            rsPt.getString("dsName"),
            mcName});
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        tbInfo.setModel(model);
        findProviderName();
    }//GEN-LAST:event_cboDocActionPerformed

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
            java.util.logging.Logger.getLogger(Form5.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form5.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form5.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form5.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form5().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboDoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbInfo;
    private javax.swing.JTextField txtPro;
    // End of variables declaration//GEN-END:variables
}
