/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Query;

import EventEntry.Premium;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mizter Lee
 */
public class Q6 extends javax.swing.JFrame {

    /**
     * Creates new form Q6
     */
    private Connection con;
    private Statement st,stMedicine,stTreatment;
    private ResultSet rs,rsMedicine,rsTreatment;
    private DefaultTableModel model,modelMedicine,modelTreatment;
    private String sql="select ID,Name from Disease",
                    sqlMedicine="select Medicine.Name from (Medicine inner join Medication on Medicine.[Medicine#]= Medication.[Medicine#]) \n" +
"									inner join Disease on Medication.[Disease.ID]=Disease.ID where Disease.ID=",
                    sqlTreatment="select Treatment.Name from (Treatment inner join TreatmentDisease on Treatment.[Treatment#]= TreatmentDisease.[Treatment#]) \n" +
"									  inner join Disease on TreatmentDisease.[Disease.ID]=Disease.ID where Disease.ID=";
    
    public Q6() {
        initComponents();
        String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url="jdbc:sqlserver://localhost:1433;"+
                "databaseName=HealthCareService;user=sa;password=b@nlyonly;";
        try {
            Class.forName(driver);
            con=DriverManager.getConnection(url);
            st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            stMedicine=con.createStatement();
            stTreatment=con.createStatement();
            rs=st.executeQuery(sql);
            model=(DefaultTableModel) tbDisease.getModel();
            modelMedicine=(DefaultTableModel) tbMedicine.getModel();
            modelTreatment=(DefaultTableModel) tbTreatment.getModel();
            Object[] o=new Object[3];
            while(rs.next()){
                o[0]=rs.getString("ID");
                o[1]=rs.getString("Name");
                model.addRow(o);
            }
        }catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Premium.class.getName()).log(Level.SEVERE, null, ex);
        }
        tbDisease.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                modelMedicine.setRowCount(0);modelTreatment.setRowCount(0);
        int i=tbDisease.getSelectedRow();
                if(i>=0){
                    String id=(String) model.getValueAt(i, 0);
                    try {
                        rsMedicine=stMedicine.executeQuery(sqlMedicine+"'"+id+"'");
                        rsTreatment=stTreatment.executeQuery(sqlTreatment+"'"+id+"'");
                        while(rsMedicine.next()){
                            modelMedicine.addRow(new Object[]{rsMedicine.getString("Name")});
                        }
                        while(rsTreatment.next()){
                            modelTreatment.addRow(new Object[]{rsTreatment.getString("Name")});
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Q6.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                modelMedicine.setRowCount(0);modelTreatment.setRowCount(0);
                int i=tbDisease.getSelectedRow();
                if(i>=0){
                    String id=(String) model.getValueAt(i, 0);
                    try {
                        rsMedicine=stMedicine.executeQuery(sqlMedicine+"'"+id+"'");
                        rsTreatment=stTreatment.executeQuery(sqlTreatment+"'"+id+"'");
                        while(rsMedicine.next()){
                            modelMedicine.addRow(new Object[]{rsMedicine.getString("Name")});
                        }
                        while(rsTreatment.next()){
                            modelTreatment.addRow(new Object[]{rsTreatment.getString("Name")});
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Q6.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                modelMedicine.setRowCount(0);modelTreatment.setRowCount(0);
                int i=tbDisease.getSelectedRow();
                if(i>=0){
                    String id=(String) model.getValueAt(i, 0);
                    try {
                        rsMedicine=stMedicine.executeQuery(sqlMedicine+"'"+id+"'");
                        rsTreatment=stTreatment.executeQuery(sqlTreatment+"'"+id+"'");
                        while(rsMedicine.next()){
                            modelMedicine.addRow(new Object[]{rsMedicine.getString("Name")});
                        }
                        while(rsTreatment.next()){
                            modelTreatment.addRow(new Object[]{rsTreatment.getString("Name")});
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Q6.class.getName()).log(Level.SEVERE, null, ex);
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
        tbDisease = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbTreatment = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbMedicine = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbDisease.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name"
            }
        ));
        tbDisease.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDiseaseMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDisease);
        if (tbDisease.getColumnModel().getColumnCount() > 0) {
            tbDisease.getColumnModel().getColumn(1).setHeaderValue("");
        }

        tbTreatment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Treatment"
            }
        ));
        jScrollPane2.setViewportView(tbTreatment);

        tbMedicine.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Medicine"
            }
        ));
        jScrollPane3.setViewportView(tbMedicine);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 15, Short.MAX_VALUE))
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

    private void tbDiseaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDiseaseMouseClicked
        // TODO add your handling code here:
        modelMedicine.setRowCount(0);modelTreatment.setRowCount(0);
        int i=tbDisease.getSelectedRow();
                if(i>=0){
                    String id=(String) model.getValueAt(i, 0);
                    try {
                        rsMedicine=stMedicine.executeQuery(sqlMedicine+"'"+id+"'");
                        rsTreatment=stTreatment.executeQuery(sqlTreatment+"'"+id+"'");
                        while(rsMedicine.next()){
                            modelMedicine.addRow(new Object[]{rsMedicine.getString("Name")});
                        }
                        while(rsTreatment.next()){
                            modelTreatment.addRow(new Object[]{rsTreatment.getString("Name")});
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Q6.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
    }//GEN-LAST:event_tbDiseaseMouseClicked

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
            java.util.logging.Logger.getLogger(Q6.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Q6.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Q6.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Q6.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Q6().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tbDisease;
    private javax.swing.JTable tbMedicine;
    private javax.swing.JTable tbTreatment;
    // End of variables declaration//GEN-END:variables
}
