package a3;

import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class Form6 extends javax.swing.JFrame {
    
    private Connection con;
    private Statement stmt;
    private ResultSet rsVt;
    private DefaultTableModel model;
    
    public Form6() {
        initComponents();
        setLocationRelativeTo(null);
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;"
                +"databasename=HealthCareService;user=vin;password=123;");
            stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rsVt=stmt.executeQuery("select Ssn\n"+"from Patient");
            
            Vector<String> v=new Vector<String>();
            while(rsVt.next())
                v.add(rsVt.getString("Ssn"));                                
            listPt.setListData(v);          
            
        }catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex);            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listPt = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbPt = new javax.swing.JTable();
        txtPaid = new javax.swing.JTextField();
        txtClaim = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        listPt.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listPtValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listPt);

        tbPt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tbPt);

        jLabel1.setText("Claim");

        jLabel2.setText("Paid");

        jLabel3.setText("Patient Identification");

        jLabel4.setText("Visit History");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtClaim, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txtPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(235, 235, 235))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtClaim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(57, 57, 57))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listPtValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listPtValueChanged
        model=new DefaultTableModel();
        model.addColumn("Date");
        model.addColumn("Time");                
        model.addColumn("Doctor");
        model.addColumn("Provider");
        model.addColumn("Symtom");   
        model.addColumn("Treatment");        
        model.addColumn("Medicine");
        String mcName="";
        String tmName="";
        if(listPt.getSelectedIndex()<0) return;
        try{
            Statement stmtVt=con.createStatement();
            ResultSet rsVt=stmtVt.executeQuery("select vt.Date as [date], vt.Time as [time], "+
                    "dt.[First name]+' '+dt.[Last name] as [dtName], pd.NAME as [pdName], "+
                    "ds.Name as [dsName], ds.ID as [dsID], vt.[Desc.Symtom] as [symtom]\n"+
                    "from Visit vt "+
                    "inner join Doctor dt on vt.[Doctor.ID]=dt.ID\n" +
                    "inner join Provider pd on vt.[Provider.ID]=pd.ID\n" +
                    "inner join Disease ds on vt.[Disease.ID]=ds.ID\n" +
                    "where vt.[Patient.Ssn]='"+listPt.getSelectedValue()+"'");    
            
            //variavble find medicince
            Statement stmtMd,stPremium,stVisit; ResultSet rsMd,rsPremium,rsVisit;
            Statement stmtMc; ResultSet rsMc;
            //variable find treatment
            Statement stmtTm; ResultSet rsTm;
            Statement stmtTmn; ResultSet rsTmn;
            String premium = "select sum([Amount Paid]) as [Paid] from Premium where [Transaction#] in" +
"(select [Transaction#] from [Transaction] where [Patient.Ssn]=";
            String inPay = "select sum([Insurance pay]) as [Claim] from Visit where [Patient.Ssn]=";
            
            stPremium=con.createStatement();
            stVisit=con.createStatement();
            rsPremium=stPremium.executeQuery(premium +"'"+listPt.getSelectedValue()+"')");
                rsVisit = stVisit.executeQuery(inPay+"'"+listPt.getSelectedValue()+"'");
                while(rsPremium.next()){
                    txtPaid.setText(rsPremium.getString("Paid"));
                }
                while(rsVisit.next()){
                    txtClaim.setText(rsVisit.getString("Claim"));
                }
            while(rsVt.next()){
                //find treatment
                stmtTm=con.createStatement();
                
                rsTm=stmtTm.executeQuery("select TreatmentDisease.Treatment# as [tmID]\n" +
                                        "from TreatmentDisease\n" +
                                        "where TreatmentDisease.[Disease.ID] ='"+rsVt.getString("dsID")+"'");
                
                
                
                while(rsTm.next()){
                    stmtTmn=con.createStatement();
                    rsTmn=stmtTmn.executeQuery("select Treatment.Name as [tmName]\n" +
                                        "from Treatment\n" +
                                        "where Treatment.Treatment#='"+rsTm.getString("tmID")+"'");  
                    
                    while(rsTmn.next()){
                        tmName+=rsTmn.getString("tmName")+", ";
                    } 
                }
                //find Medicince
                stmtMd=con.createStatement();
                rsMd=stmtMd.executeQuery("select Medication.[Medicine#] as [mcID]\n" +
                                        "from Medication\n" +
                                        "where Medication.[Disease.ID]='"+rsVt.getString("dsID")+"'");
                while(rsMd.next()){
                    stmtMc=con.createStatement();
                    rsMc=stmtMc.executeQuery("select Medicine.Name as [mcName]\n" +
                                            "from Medicine\n" +
                                        "where Medicine.[Medicine#]='"+rsMd.getString("mcID")+"'");  
                    
                    while(rsMc.next()){
                        mcName+=rsMc.getString("mcName")+", ";
                    } 
                }
                model.addRow(new Object[]{
                    rsVt.getString("date"),
                    rsVt.getTime("time"),
                    rsVt.getString("dtName"),
                    rsVt.getString("pdName"),                    
                    rsVt.getString("symtom"),
                    tmName, mcName});
                tmName = "";
                mcName = "";
                
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return;
        }
        tbPt.setModel(model);
    }//GEN-LAST:event_listPtValueChanged

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
            java.util.logging.Logger.getLogger(Form6.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form6.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form6.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form6.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form6().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> listPt;
    private javax.swing.JTable tbPt;
    private javax.swing.JTextField txtClaim;
    private javax.swing.JTextField txtPaid;
    // End of variables declaration//GEN-END:variables
}
