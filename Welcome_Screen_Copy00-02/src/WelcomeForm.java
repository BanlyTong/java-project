import java.sql.*;

import java.util.Map;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.border.DropShadowBorder;

import static javax.swing.border.BevelBorder.RAISED;

public class WelcomeForm extends javax.swing.JFrame {
    ShowDataToTable sd = null;
    
    DefaultTableModel model;
    
    static boolean logout;

    public static boolean isLogout() {
        return logout;
    }

    public static void setLogout(boolean logout) {
        WelcomeForm.logout = logout;
    }
    
    Connection con = null;
    Statement stmt = null;
    ResultSet rsDoc = null;
    ResultSet rs = null;
    
    FrmLogin frmLogin  = new FrmLogin();    
    FrmSearchDoctor frmFDoctor = new FrmSearchDoctor();    
    FrmSearchPatient frmFPatient = new FrmSearchPatient();
    FrmSearchTreatmentMedicine frmFTreatment = new FrmSearchTreatmentMedicine(); 
    
    UpdateInsurance uIns = new UpdateInsurance(this, true);
    UpdateDoctor uDoc = new UpdateDoctor(this, true);
    UpdatePatient uPat = new UpdatePatient(this, true);
    UpdatePlan uPlan = new UpdatePlan(this, true);
     
    public WelcomeForm() {
        setLookAndFeel();
        
        initComponents();
        
        setDefaultTableRender();

//        setExtendedState(JFrame.MAXIMIZED_BOTH);

        panel_insurance_comMousePressed(null);
        
        panel_statistic.setVisible(false);
        
        showTotalToDashboard();
        
        refreshData();
                
        initForm5();
        
        showDataToTable();
        
        switchDoctorPatient();                     
        
        afterLogin();
        
        setEventMouseEnteredExited();       
    }
    
    private void refreshData() {
        windowsClosedEvent(uDoc);
        windowsClosedEvent(uPat);
        windowsClosedEvent(uIns);
        windowsClosedEvent(uPlan);
    }
    
    private void afterLogin() {
        frmLogin.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                WelcomeForm.this.setVisible(true); 
                   
                changeLoginTextAndIcon();
                
                disableUpdate();
                disableDashboard();
                
                if (frmLogin.isLoggedin()) {
                    panel_dashboardMousePressed(null);
                    
                    if (frmLogin.getAccountType().equals("Admin")) {
                        userLoginAdmin();
                    }   

                    if (frmLogin.getAccountType().equals("Insurance")) {
                        userLoginInsurance();
                    }

                    if (frmLogin.getAccountType().equals("Healthcare")) {
                        userLoginHealthcare();
                    }   

                    if (frmLogin.getAccountType().equals("Insurance")) {
                            
                    }
                } else {
                    panel_insurance_comMousePressed(null);
                }
            }
        });
    }
    
    private void windowsClosedEvent(JDialog dialog) {
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                showDataToTable();
                showTotalToDashboard();
            }
        });
    }
    
    private void showDataToTable() {
        sd = new ShowDataToTable("SELECT * FROM Provider", tblHP, 3);
        sd = new ShowDataToTable("SELECT * FROM Insurance", tblInsCom, 3);
        sd = new ShowDataToTable("SELECT * FROM [Plan]", tblPlan, 3);
        sd = new ShowDataToTable("SELECT ID, [First name] + ' ' + [Last name] AS [Name], Gender, DOB, Address, Area, Degree FROM Doctor", tblDoctor, 7);
        sd = new ShowDataToTable("SELECT Ssn, [First Name] + ' ' + [Last Name] AS [Name], Gender, DOB, Address, HealthCondition, Phone, [Employer Name], Contact FROM Patient", tblPatient, 9);
    }
    
    private void setDefaultTableRender() {
        SubTable.setDefaultTableRender(tbInfo);
        SubTable.setDefaultTableRender(tblDoctor);
        SubTable.setDefaultTableRender(tblHP);
        SubTable.setDefaultTableRender(tblInsCom);
        SubTable.setDefaultTableRender(tblPatient);
        SubTable.setDefaultTableRender(tblPlan);
        SubTable.setDefaultTableRender(tblUser);
    }
    
    private void findProviderName(){
        //Open Query select Provider Name
        if(cboDoc.getItemCount()<0) return;
        
        try{        
            rsDoc.absolute(cboDoc.getSelectedIndex()+1);
            Statement stmtPro = con.createStatement();
            try (ResultSet rsPro = stmtPro.executeQuery("Select * from Provider\n" 
                    +"where Provider.ID='"+rsDoc.getString("Provider.ID")+"'")) {
                while(rsPro.next())
                    txtPro.setText(rsPro.getString("Name"));
            }        
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void initForm5() { 
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;"
                    +"databasename=HealthCareService;user=sa;password=sathya123;");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rsDoc = stmt.executeQuery("select *from Doctor");
            
            while(rsDoc.next())
                cboDoc.addItem(rsDoc.getString("First name")+" "+rsDoc.getString("Last name"));
            
        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        
        SubTable.setTableHeader(tbInfo, new Color(240, 240, 240), Color.BLACK, new Font("Tahoma", Font.PLAIN, 12));
    }
    
    private void setLookAndFeel() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void switchDoctorPatient() {
        jXCollapsiblePane1.setLayout(new BorderLayout());
        jXCollapsiblePane1.add(frm5main, BorderLayout.CENTER);
        
        btnToggle.addActionListener(jXCollapsiblePane1.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION));      
    }
    
    private void showTotalToDashboard() {
        // Total Provider
        countDataShowToDashboard("SELECT * FROM Provider", lblAdminTotalHP);
        countDataShowToDashboard("SELECT * FROM Provider", lblHPTotal);
        
        // Total Insurance Company
        countDataShowToDashboard("SELECT * FROM Insurance", lblAdminTotalIns);
        countDataShowToDashboard("SELECT * FROM Insurance", lblInsTotal);
        
        // Total Doctor
        countDataShowToDashboard("SELECT * FROM Doctor", lblAdminTotalDoctor);
        countDataShowToDashboard("SELECT * FROM Doctor", lblHPTotalDoctor);
        
        // Total Patient
        countDataShowToDashboard("SELECT * FROM Patient", lblAdminTotalPatient);
        countDataShowToDashboard("SELECT * FROM Patient", lblInsTotalPatient);
        countDataShowToDashboard("SELECT * FROM Patient", lblHPTotalPatient);
        
        // Total Plan
        countDataShowToDashboard("SELECT * FROM [Plan]", lblAdminTotalPlan);
        countDataShowToDashboard("SELECT * FROM [Plan]", lblInsTotalPlan);
        
        // Total User
        countDataShowToDashboard("SELECT * FROM [User]", lblAdminTotalUser);
    }
    
    private void disableUpdate() {
        lblLock.setVisible(true);
        
        lblUpdate.setForeground(new Color(128, 128, 128));
        lblUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Update_Disable_25px.png")));
    }
    
    private void disableDashboard() {
        lblLock1.setVisible(true);
        
        lblDashboard.setForeground(new Color(128, 128, 128));
        lblDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Dashboard_25px_1.png")));
    }
    
    private void enableUpdate() {
        lblLock.setVisible(false);
        
        lblUpdate.setForeground(new Color(255, 255, 255));       
        lblUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Update_25px.png")));
    }
    
    private void enableDashboard() {
        lblLock1.setVisible(false);
        
        lblDashboard.setForeground(new Color(255, 255, 255));       
        lblDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Dashboard_25px.png")));
    }
    
    private void changeLoginTextAndIcon() {              
        if (frmLogin.isLoggedin()) {
            lblLogin.setText(frmLogin.getUsername());
            
            if (frmLogin.getAccountType().equals("Insurance")) {
                lblLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Company_25px.png")));
            } 

            if (frmLogin.getAccountType().equals("Healthcare")) {
                lblLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Hospital_3_25px_1.png")));
            }    

            if (frmLogin.getAccountType().equals("Normal")) {
                lblLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_User_30px.png")));
            }
        
            if (frmLogin.getAccountType().equals("Admin")) {
                lblLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Admin_30px.png")));
            }  
        }
        else {
            lblLogin.setText("Log in / Register");
            lblLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Add_User_Male_30px_4.png")));
        }
    }
    
    private void userLoginAdmin() {
        enableUpdate();
        enableDashboard();
    }
    
    private void userLoginInsurance() {       
        disableUpdate();
        enableDashboard();
    }
    
    private void userLoginHealthcare() {        
        disableUpdate();
        enableDashboard();
    }
    
    private void connectDatabase(String query) {        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");            
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=HealthCareService;user=sa;password=sathya123;");           
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);            
            rs = stmt.executeQuery(query);           
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void countDataShowToDashboard(String query, JLabel label) {        
        int count = 0;
        
        connectDatabase(query);
        
        try {         
            
            while (rs.next()) {
                count++;
            }
            
            label.setText(String.valueOf(count));
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void setColorPanel(JPanel panel) {
        panel.setBackground(new Color(57, 109, 160));
    }
    
    private void resetColorPanel(JPanel panel) {
        panel.setBackground(new Color(18,55,92));
    }
    
    private void setColorPanelButton(JPanel panel) {
        panel.setBackground(new Color(77, 201, 212));
    }
    
    private void resetColorPanelButton(JPanel panel) {
        panel.setBackground(new Color(0,160,174));
    }
    
    private void showPanelInCard(JPanel pnlParent, JPanel pnlChild) {
        pnlParent.removeAll();
        pnlParent.add(pnlChild);
        pnlParent.repaint();
        pnlParent.validate();
    }
    
    private void setColorChooseSidePane(JPanel p1, JPanel p2, JPanel p3, JPanel p4, JPanel p5, JPanel p6, JPanel p7, JPanel p8, JPanel p9, JPanel p10) {
        setColorPanel(p1);
        resetColorPanel(p2);
        resetColorPanel(p3);
        resetColorPanel(p4);
        resetColorPanel(p5);
        resetColorPanel(p6);
        resetColorPanel(p7);
        resetColorPanel(p8);
        resetColorPanel(p9);
        resetColorPanel(p10);
    }
    
    private void setIndexChooseSidePane(boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, boolean b6, boolean b7, boolean b8, boolean b9, boolean b10) {
        indDashboard.setOpaque(b1);
        indInsurance.setOpaque(b2);
        indHealthcarePlan.setOpaque(b3);
        indHP.setOpaque(b4);
        indDoctor.setOpaque(b5);
        indStatistic.setOpaque(b6);
        indUpdate.setOpaque(b7);
        indFind.setOpaque(b8);
        indAbout.setOpaque(b9);
        indPatient.setOpaque(b10);
    }
    
    private void mouseEnteredExited(JPanel panel, JLabel label) {
        panel.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBorder(BorderFactory.createBevelBorder(RAISED));
                label.setText("<html><u>Click here to view detail</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                DropShadowBorder shadow = new DropShadowBorder();
                shadow.setShadowSize(7);
                
                panel.setBorder(shadow);
                label.setText("Click here to view detail");
            }
        });
    }
    
    private void mouseEnteredExited(JPanel panel) {
        panel.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseEntered(MouseEvent e) {
                setColorPanelButton(panel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetColorPanelButton(panel);
            }
        });
    }
    
    private void mouseEnteredExited(JLabel label) {
        label.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setBackground(new Color(255, 255, 224));
                label.setBorder(BorderFactory.createLineBorder(Color.yellow, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setBackground(new Color(240, 240, 240));
                label.setBorder(null);
            }
        });
    }
    
    private void setEventMouseEnteredExited() {
        // Dashboard / Admin
        mouseEnteredExited(panel_dAdminHP, lblDetailHP);
        mouseEnteredExited(panel_dAdminInsurance, lblDetailInsurance);
        mouseEnteredExited(panel_dAdminPlan, lblDetailPlan);
        mouseEnteredExited(panel_dAdminDoctor, lblDetailDoctor);
        mouseEnteredExited(panel_dAdminPatient, lblDetailPatient);
        mouseEnteredExited(panel_dAdminUser, lblDetailUser);
        
        // Dashboard / Insurance
        mouseEnteredExited(panel_dInsTotal, lblDetailInsurance2);
        mouseEnteredExited(panel_dInsPlan, lblDetailPlan2);
        mouseEnteredExited(panel_dInsPatient, lblDetailPatient2);
        
        // Dashboard / Healthcare Provider
        mouseEnteredExited(panel_dHPTotal, lblDetailInsurance3);
        mouseEnteredExited(panel_dHPDoctor, lblDetailDoctor3);
        mouseEnteredExited(panel_dHPPatient, lblDetailPatient3);
        
        // Button view
        mouseEnteredExited(panel_viewDoctor);
        mouseEnteredExited(panel_viewDisease);
        mouseEnteredExited(panel_viewPlan);
        
        // Update tab
        mouseEnteredExited(lblUIns);
        mouseEnteredExited(lblUHP);
        mouseEnteredExited(lblUHPlan);
        mouseEnteredExited(lblUDoctor);
        mouseEnteredExited(lblUPatient);
        mouseEnteredExited(lblUUser);
        
        // Find tab
        mouseEnteredExited(lblDoctor);
        mouseEnteredExited(lblPatient);
        mouseEnteredExited(lblTreatment);
    }
    
     /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pmLogin = new javax.swing.JPopupMenu();
        menuLogout = new javax.swing.JMenuItem();
        panel_form5 = new javax.swing.JPanel();
        frm5main = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        cboDoc = new javax.swing.JComboBox<>();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbInfo = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        txtPro = new javax.swing.JTextField();
        scpMain = new javax.swing.JScrollPane();
        panel_main = new javax.swing.JPanel();
        panel_left = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        panel_dashboard = new javax.swing.JPanel();
        lblDashboard = new javax.swing.JLabel();
        indDashboard = new javax.swing.JPanel();
        lblLock1 = new javax.swing.JLabel();
        panel_insurance_com = new javax.swing.JPanel();
        lblInsuranceCom = new javax.swing.JLabel();
        indInsurance = new javax.swing.JPanel();
        panel_healthcarePlan = new javax.swing.JPanel();
        lblInsuranceCom2 = new javax.swing.JLabel();
        indHealthcarePlan = new javax.swing.JPanel();
        panel_healthcarePro = new javax.swing.JPanel();
        lblHealthcarePro = new javax.swing.JLabel();
        indHP = new javax.swing.JPanel();
        panel_doctor = new javax.swing.JPanel();
        lblHealthcarePro1 = new javax.swing.JLabel();
        indDoctor = new javax.swing.JPanel();
        panel_update = new javax.swing.JPanel();
        lblUpdate = new javax.swing.JLabel();
        indUpdate = new javax.swing.JPanel();
        lblLock = new javax.swing.JLabel();
        panel_find = new javax.swing.JPanel();
        lblFind = new javax.swing.JLabel();
        indFind = new javax.swing.JPanel();
        panel_about = new javax.swing.JPanel();
        lblAbout = new javax.swing.JLabel();
        indAbout = new javax.swing.JPanel();
        panel_patient = new javax.swing.JPanel();
        lblp = new javax.swing.JLabel();
        indPatient = new javax.swing.JPanel();
        panel_statistic = new javax.swing.JPanel();
        lblStatistic = new javax.swing.JLabel();
        indStatistic = new javax.swing.JPanel();
        panel_titlebar = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        panel_login = new javax.swing.JPanel();
        lblLogin = new javax.swing.JLabel();
        panel_home = new javax.swing.JPanel();
        panel_hDashboard = new javax.swing.JPanel();
        panel_dAdmin = new javax.swing.JPanel();
        panel_dAdminMain = new javax.swing.JPanel();
        lbl4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        panel_dAdminHP = new javax.swing.JPanel();
        lblAdminTotalHP = new javax.swing.JLabel();
        lblDetailHP = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        panel_dAdminInsurance = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblDetailInsurance = new javax.swing.JLabel();
        lblAdminTotalIns = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        panel_dAdminPlan = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblDetailPlan = new javax.swing.JLabel();
        lblAdminTotalPlan = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        panel_dAdminDoctor = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lblDetailDoctor = new javax.swing.JLabel();
        lblAdminTotalDoctor = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        panel_dAdminPatient = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        lblDetailPatient = new javax.swing.JLabel();
        lblAdminTotalPatient = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        panel_dAdminUser = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        lblDetailUser = new javax.swing.JLabel();
        lblAdminTotalUser = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        panel_dInsurance = new javax.swing.JPanel();
        panel_dInsMain = new javax.swing.JPanel();
        lbl5 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        panel_dInsTotal = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        lblDetailInsurance2 = new javax.swing.JLabel();
        lblInsTotal = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        panel_dInsPlan = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        lblDetailPlan2 = new javax.swing.JLabel();
        lblInsTotalPlan = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        panel_dInsPatient = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        lblDetailPatient2 = new javax.swing.JLabel();
        lblInsTotalPatient = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        panel_dHealthcare = new javax.swing.JPanel();
        panel_dHPMain = new javax.swing.JPanel();
        lbl8 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        panel_dHPTotal = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        lblDetailInsurance3 = new javax.swing.JLabel();
        lblHPTotal = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        panel_dHPDoctor = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        lblDetailDoctor3 = new javax.swing.JLabel();
        lblHPTotalDoctor = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        panel_dHPPatient = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        lblDetailPatient3 = new javax.swing.JLabel();
        lblHPTotalPatient = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        panel_dNormal = new javax.swing.JPanel();
        panel_hInsMain4 = new javax.swing.JPanel();
        lbl7 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        panel_user = new javax.swing.JPanel();
        panel_hInsMain1 = new javax.swing.JPanel();
        lbl9 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jSeparator6 = new javax.swing.JSeparator();
        tfSearchUser = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        cbSearchUser = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        panel_empty = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        panel_hInsurance_com = new javax.swing.JPanel();
        panel_hInsMain = new javax.swing.JPanel();
        lbl1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        tfSearchInsCom = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cbSearchIns = new javax.swing.JComboBox<>();
        panel_viewPlan = new javax.swing.JPanel();
        lblSearchHP2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblInsCom = new javax.swing.JTable();
        panel_hHealthcarePlan = new javax.swing.JPanel();
        panel_hHPlanMain = new javax.swing.JPanel();
        lbl2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        tfSearchPlan = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cbSearchPlan = new javax.swing.JComboBox<>();
        panel_viewDoctor2 = new javax.swing.JPanel();
        lblSearchHP3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPlan = new javax.swing.JTable();
        panel_hHealthcarePro = new javax.swing.JPanel();
        lblHealthcarePro2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        tfSearchHP = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cbSearchHP = new javax.swing.JComboBox<>();
        panel_viewDoctor = new javax.swing.JPanel();
        lblSearchHP1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHP = new javax.swing.JTable();
        panel_hDoctor = new javax.swing.JPanel();
        jXCollapsiblePane1 = new org.jdesktop.swingx.JXCollapsiblePane();
        panel_hDoctorMain = new javax.swing.JPanel();
        lbl3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jSeparator4 = new javax.swing.JSeparator();
        tfSearchDoctor = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cbSearchDoctor = new javax.swing.JComboBox<>();
        panel_viewProvider2 = new javax.swing.JPanel();
        lblSearchHP4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDoctor = new javax.swing.JTable();
        btnToggle = new javax.swing.JButton();
        panel_hPatient = new javax.swing.JPanel();
        panel_hDoctorMain1 = new javax.swing.JPanel();
        lbl6 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jSeparator5 = new javax.swing.JSeparator();
        tfSearchPatient = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        cbSearchPatient = new javax.swing.JComboBox<>();
        panel_viewDisease = new javax.swing.JPanel();
        lblSearchHP5 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblPatient = new javax.swing.JTable();
        panel_hStatistic = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        panel_hUpdates = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        lblUIns = new javax.swing.JLabel();
        lblUHP = new javax.swing.JLabel();
        lblUHPlan = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        lblUDoctor = new javax.swing.JLabel();
        lblUPatient = new javax.swing.JLabel();
        lblUUser = new javax.swing.JLabel();
        panel_hFind = new javax.swing.JPanel();
        panel_findOption = new javax.swing.JPanel();
        lblDoctor = new javax.swing.JLabel();
        lblPatient = new javax.swing.JLabel();
        lblTreatment = new javax.swing.JLabel();
        panel_hAbout = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();

        menuLogout.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        menuLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Logout_Rounded_Left_25px.png"))); // NOI18N
        menuLogout.setText("Log out");
        menuLogout.setIconTextGap(15);
        menuLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLogoutActionPerformed(evt);
            }
        });
        pmLogin.add(menuLogout);

        panel_form5.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        panel_form5.setMinimumSize(new java.awt.Dimension(311, 15));
        panel_form5.setPreferredSize(new java.awt.Dimension(995, 586));
        panel_form5.setLayout(new java.awt.BorderLayout());

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Doctor");

        cboDoc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cboDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDocActionPerformed(evt);
            }
        });

        tbInfo.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        tbInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Patient ID", "Patient Name", "Disease", "Medication"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbInfo.setRowHeight(24);
        tbInfo.setSelectionBackground(new java.awt.Color(18, 55, 92));
        tbInfo.setSelectionForeground(new java.awt.Color(250, 250, 250));
        jScrollPane7.setViewportView(tbInfo);

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel27.setText("Provider Name");

        txtPro.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtPro.setFocusable(false);

        javax.swing.GroupLayout frm5mainLayout = new javax.swing.GroupLayout(frm5main);
        frm5main.setLayout(frm5mainLayout);
        frm5mainLayout.setHorizontalGroup(
            frm5mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frm5mainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frm5mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(frm5mainLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(cboDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPro, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 231, Short.MAX_VALUE))
                    .addComponent(jScrollPane7))
                .addContainerGap())
        );

        frm5mainLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cboDoc, txtPro});

        frm5mainLayout.setVerticalGroup(
            frm5mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frm5mainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frm5mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(cboDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(txtPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addContainerGap())
        );

        frm5mainLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cboDoc, txtPro});

        panel_form5.add(frm5main, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Healthcare Management System v1.0");
        setBackground(new java.awt.Color(255, 255, 255));

        panel_left.setBackground(new java.awt.Color(18, 55, 92));

        lblLogo.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(255, 255, 255));
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Health_Book_48px.png"))); // NOI18N
        lblLogo.setText("Logo Here");

        panel_dashboard.setBackground(new java.awt.Color(18, 55, 92));
        panel_dashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_dashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_dashboardMousePressed(evt);
            }
        });
        panel_dashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDashboard.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblDashboard.setForeground(new java.awt.Color(128, 128, 128));
        lblDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Dashboard_25px_1.png"))); // NOI18N
        lblDashboard.setText("Dashboard");
        lblDashboard.setIconTextGap(20);
        panel_dashboard.add(lblDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 0, 280, 42));

        indDashboard.setBackground(new java.awt.Color(255, 204, 0));
        indDashboard.setOpaque(false);
        indDashboard.setPreferredSize(new java.awt.Dimension(10, 52));

        javax.swing.GroupLayout indDashboardLayout = new javax.swing.GroupLayout(indDashboard);
        indDashboard.setLayout(indDashboardLayout);
        indDashboardLayout.setHorizontalGroup(
            indDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        indDashboardLayout.setVerticalGroup(
            indDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        panel_dashboard.add(indDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 42));

        lblLock1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Lock_20px.png"))); // NOI18N
        panel_dashboard.add(lblLock1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, -1));

        panel_insurance_com.setBackground(new java.awt.Color(18, 55, 92));
        panel_insurance_com.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_insurance_com.setPreferredSize(new java.awt.Dimension(304, 42));
        panel_insurance_com.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_insurance_comMousePressed(evt);
            }
        });
        panel_insurance_com.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInsuranceCom.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblInsuranceCom.setForeground(new java.awt.Color(255, 255, 255));
        lblInsuranceCom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Company_25px_2.png"))); // NOI18N
        lblInsuranceCom.setText("Insurance Company");
        lblInsuranceCom.setIconTextGap(20);
        lblInsuranceCom.setPreferredSize(new java.awt.Dimension(112, 42));
        panel_insurance_com.add(lblInsuranceCom, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 0, 280, 42));

        indInsurance.setBackground(new java.awt.Color(255, 204, 0));
        indInsurance.setOpaque(false);
        indInsurance.setPreferredSize(new java.awt.Dimension(10, 52));

        javax.swing.GroupLayout indInsuranceLayout = new javax.swing.GroupLayout(indInsurance);
        indInsurance.setLayout(indInsuranceLayout);
        indInsuranceLayout.setHorizontalGroup(
            indInsuranceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        indInsuranceLayout.setVerticalGroup(
            indInsuranceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        panel_insurance_com.add(indInsurance, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 42));

        panel_healthcarePlan.setBackground(new java.awt.Color(18, 55, 92));
        panel_healthcarePlan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_healthcarePlan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_healthcarePlanMousePressed(evt);
            }
        });
        panel_healthcarePlan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInsuranceCom2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblInsuranceCom2.setForeground(new java.awt.Color(255, 255, 255));
        lblInsuranceCom2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Health_Book_25px_3.png"))); // NOI18N
        lblInsuranceCom2.setText("Health Care Plans");
        lblInsuranceCom2.setIconTextGap(20);
        lblInsuranceCom2.setPreferredSize(new java.awt.Dimension(112, 42));
        panel_healthcarePlan.add(lblInsuranceCom2, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 0, 280, 42));

        indHealthcarePlan.setBackground(new java.awt.Color(255, 204, 0));
        indHealthcarePlan.setOpaque(false);
        indHealthcarePlan.setPreferredSize(new java.awt.Dimension(10, 52));

        javax.swing.GroupLayout indHealthcarePlanLayout = new javax.swing.GroupLayout(indHealthcarePlan);
        indHealthcarePlan.setLayout(indHealthcarePlanLayout);
        indHealthcarePlanLayout.setHorizontalGroup(
            indHealthcarePlanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        indHealthcarePlanLayout.setVerticalGroup(
            indHealthcarePlanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        panel_healthcarePlan.add(indHealthcarePlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 42));

        panel_healthcarePro.setBackground(new java.awt.Color(18, 55, 92));
        panel_healthcarePro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_healthcarePro.setPreferredSize(new java.awt.Dimension(304, 42));
        panel_healthcarePro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_healthcareProMousePressed(evt);
            }
        });
        panel_healthcarePro.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblHealthcarePro.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblHealthcarePro.setForeground(new java.awt.Color(255, 255, 255));
        lblHealthcarePro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Hospital_3_25px.png"))); // NOI18N
        lblHealthcarePro.setText("Health Care Providers");
        lblHealthcarePro.setIconTextGap(20);
        lblHealthcarePro.setPreferredSize(new java.awt.Dimension(171, 42));
        panel_healthcarePro.add(lblHealthcarePro, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 0, 280, 42));

        indHP.setBackground(new java.awt.Color(255, 204, 0));
        indHP.setOpaque(false);
        indHP.setPreferredSize(new java.awt.Dimension(10, 52));

        javax.swing.GroupLayout indHPLayout = new javax.swing.GroupLayout(indHP);
        indHP.setLayout(indHPLayout);
        indHPLayout.setHorizontalGroup(
            indHPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        indHPLayout.setVerticalGroup(
            indHPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        panel_healthcarePro.add(indHP, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 42));

        panel_doctor.setBackground(new java.awt.Color(18, 55, 92));
        panel_doctor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_doctor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_doctorMousePressed(evt);
            }
        });
        panel_doctor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblHealthcarePro1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblHealthcarePro1.setForeground(new java.awt.Color(255, 255, 255));
        lblHealthcarePro1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Doctor_Male_25px_1.png"))); // NOI18N
        lblHealthcarePro1.setText("Doctors");
        lblHealthcarePro1.setIconTextGap(20);
        lblHealthcarePro1.setPreferredSize(new java.awt.Dimension(171, 42));
        panel_doctor.add(lblHealthcarePro1, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 0, 280, 42));

        indDoctor.setBackground(new java.awt.Color(255, 204, 0));
        indDoctor.setOpaque(false);
        indDoctor.setPreferredSize(new java.awt.Dimension(10, 52));

        javax.swing.GroupLayout indDoctorLayout = new javax.swing.GroupLayout(indDoctor);
        indDoctor.setLayout(indDoctorLayout);
        indDoctorLayout.setHorizontalGroup(
            indDoctorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        indDoctorLayout.setVerticalGroup(
            indDoctorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        panel_doctor.add(indDoctor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 42));

        panel_update.setBackground(new java.awt.Color(18, 55, 92));
        panel_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_update.setPreferredSize(new java.awt.Dimension(304, 42));
        panel_update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_updateMousePressed(evt);
            }
        });
        panel_update.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUpdate.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblUpdate.setForeground(new java.awt.Color(128, 128, 128));
        lblUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Update_Disable_25px.png"))); // NOI18N
        lblUpdate.setText("Updates");
        lblUpdate.setIconTextGap(20);
        panel_update.add(lblUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 0, 280, 42));

        indUpdate.setBackground(new java.awt.Color(255, 204, 0));
        indUpdate.setOpaque(false);
        indUpdate.setPreferredSize(new java.awt.Dimension(10, 52));

        javax.swing.GroupLayout indUpdateLayout = new javax.swing.GroupLayout(indUpdate);
        indUpdate.setLayout(indUpdateLayout);
        indUpdateLayout.setHorizontalGroup(
            indUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        indUpdateLayout.setVerticalGroup(
            indUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        panel_update.add(indUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 42));

        lblLock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Lock_20px.png"))); // NOI18N
        panel_update.add(lblLock, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, -1));

        panel_find.setBackground(new java.awt.Color(18, 55, 92));
        panel_find.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_find.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_findMousePressed(evt);
            }
        });
        panel_find.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFind.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblFind.setForeground(new java.awt.Color(255, 255, 255));
        lblFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Search_25px.png"))); // NOI18N
        lblFind.setText("Find");
        lblFind.setIconTextGap(20);
        panel_find.add(lblFind, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 0, 280, 42));

        indFind.setBackground(new java.awt.Color(255, 204, 0));
        indFind.setOpaque(false);
        indFind.setPreferredSize(new java.awt.Dimension(10, 52));

        javax.swing.GroupLayout indFindLayout = new javax.swing.GroupLayout(indFind);
        indFind.setLayout(indFindLayout);
        indFindLayout.setHorizontalGroup(
            indFindLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        indFindLayout.setVerticalGroup(
            indFindLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        panel_find.add(indFind, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 42));

        panel_about.setBackground(new java.awt.Color(18, 55, 92));
        panel_about.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_about.setPreferredSize(new java.awt.Dimension(304, 42));
        panel_about.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_aboutMousePressed(evt);
            }
        });
        panel_about.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAbout.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblAbout.setForeground(new java.awt.Color(255, 255, 255));
        lblAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_About_25px.png"))); // NOI18N
        lblAbout.setText("About");
        lblAbout.setIconTextGap(20);
        panel_about.add(lblAbout, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 0, 280, 42));

        indAbout.setBackground(new java.awt.Color(255, 204, 0));
        indAbout.setOpaque(false);
        indAbout.setPreferredSize(new java.awt.Dimension(10, 52));

        javax.swing.GroupLayout indAboutLayout = new javax.swing.GroupLayout(indAbout);
        indAbout.setLayout(indAboutLayout);
        indAboutLayout.setHorizontalGroup(
            indAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        indAboutLayout.setVerticalGroup(
            indAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        panel_about.add(indAbout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 42));

        panel_patient.setBackground(new java.awt.Color(18, 55, 92));
        panel_patient.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_patient.setPreferredSize(new java.awt.Dimension(304, 42));
        panel_patient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_patientMousePressed(evt);
            }
        });
        panel_patient.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblp.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblp.setForeground(new java.awt.Color(255, 255, 255));
        lblp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Cast_25px.png"))); // NOI18N
        lblp.setText("Patient");
        lblp.setIconTextGap(20);
        panel_patient.add(lblp, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 0, 280, 42));

        indPatient.setBackground(new java.awt.Color(255, 204, 0));
        indPatient.setOpaque(false);
        indPatient.setPreferredSize(new java.awt.Dimension(10, 52));

        javax.swing.GroupLayout indPatientLayout = new javax.swing.GroupLayout(indPatient);
        indPatient.setLayout(indPatientLayout);
        indPatientLayout.setHorizontalGroup(
            indPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        indPatientLayout.setVerticalGroup(
            indPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        panel_patient.add(indPatient, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 42));

        panel_statistic.setBackground(new java.awt.Color(18, 55, 92));
        panel_statistic.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_statistic.setPreferredSize(new java.awt.Dimension(304, 42));
        panel_statistic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_statisticMousePressed(evt);
            }
        });
        panel_statistic.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblStatistic.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblStatistic.setForeground(new java.awt.Color(255, 255, 255));
        lblStatistic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Statistics_25px.png"))); // NOI18N
        lblStatistic.setText("Statistic, Graph & Data Analysis");
        lblStatistic.setIconTextGap(20);
        panel_statistic.add(lblStatistic, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 0, 280, 42));

        indStatistic.setBackground(new java.awt.Color(255, 204, 0));
        indStatistic.setOpaque(false);
        indStatistic.setPreferredSize(new java.awt.Dimension(10, 52));

        javax.swing.GroupLayout indStatisticLayout = new javax.swing.GroupLayout(indStatistic);
        indStatistic.setLayout(indStatisticLayout);
        indStatisticLayout.setHorizontalGroup(
            indStatisticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        indStatisticLayout.setVerticalGroup(
            indStatisticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );

        panel_statistic.add(indStatistic, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 42));

        javax.swing.GroupLayout panel_leftLayout = new javax.swing.GroupLayout(panel_left);
        panel_left.setLayout(panel_leftLayout);
        panel_leftLayout.setHorizontalGroup(
            panel_leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panel_insurance_com, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panel_healthcarePro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panel_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panel_about, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panel_patient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(panel_leftLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(lblLogo))
            .addComponent(panel_healthcarePlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panel_doctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panel_find, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panel_statistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panel_leftLayout.setVerticalGroup(
            panel_leftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_leftLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblLogo)
                .addGap(77, 77, 77)
                .addComponent(panel_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_insurance_com, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_healthcarePlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_healthcarePro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_doctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(panel_patient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_find, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_about, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel_statistic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel_titlebar.setBackground(new java.awt.Color(18, 55, 92));
        panel_titlebar.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        panel_titlebar.setLayout(new java.awt.BorderLayout());

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Management of Health Care Services");
        panel_titlebar.add(jLabel2, java.awt.BorderLayout.CENTER);

        panel_login.setBackground(new java.awt.Color(255, 255, 255));

        lblLogin.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        lblLogin.setForeground(new java.awt.Color(60, 141, 188));
        lblLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Add_User_Male_30px_4.png"))); // NOI18N
        lblLogin.setText("Log in / Register");
        lblLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLoginMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblLoginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblLoginMouseExited(evt);
            }
        });

        javax.swing.GroupLayout panel_loginLayout = new javax.swing.GroupLayout(panel_login);
        panel_login.setLayout(panel_loginLayout);
        panel_loginLayout.setHorizontalGroup(
            panel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_loginLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblLogin)
                .addGap(21, 21, 21))
        );
        panel_loginLayout.setVerticalGroup(
            panel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panel_home.setBackground(new java.awt.Color(245, 245, 245));
        panel_home.setLayout(new java.awt.CardLayout());

        panel_hDashboard.setLayout(new java.awt.CardLayout());

        panel_dAdminMain.setBackground(new java.awt.Color(239, 240, 244));

        lbl4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbl4.setText("Dashboard / Admin");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

        panel_dAdminHP.setBackground(new java.awt.Color(1, 114, 182));
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder1 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder1.setShadowSize(7);
        panel_dAdminHP.setBorder(dropShadowBorder1);
        panel_dAdminHP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_dAdminHP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_dAdminHPMouseClicked(evt);
            }
        });
        panel_dAdminHP.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAdminTotalHP.setBackground(new java.awt.Color(255, 0, 0));
        lblAdminTotalHP.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblAdminTotalHP.setForeground(new java.awt.Color(255, 255, 255));
        lblAdminTotalHP.setText("39");
        panel_dAdminHP.add(lblAdminTotalHP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        lblDetailHP.setBackground(new java.awt.Color(1, 101, 165));
        lblDetailHP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDetailHP.setForeground(new java.awt.Color(255, 255, 255));
        lblDetailHP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetailHP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Double_Up_15px_2.png"))); // NOI18N
        lblDetailHP.setText("Click here to view detail");
        lblDetailHP.setOpaque(true);
        lblDetailHP.setPreferredSize(new java.awt.Dimension(288, 20));
        panel_dAdminHP.add(lblDetailHP, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 84, 280, 20));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Hospital_3_35px.png"))); // NOI18N
        panel_dAdminHP.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

        jLabel1.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Total Number of Healthcare Provider");
        jLabel1.setIconTextGap(20);
        panel_dAdminHP.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 280, 80));

        panel_dAdminInsurance.setBackground(new java.awt.Color(243, 156, 17));
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder2 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder2.setShadowSize(7);
        panel_dAdminInsurance.setBorder(dropShadowBorder2);
        panel_dAdminInsurance.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_dAdminInsurance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_dAdminInsuranceMouseClicked(evt);
            }
        });
        panel_dAdminInsurance.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Total Number of Insurance Company");
        jLabel3.setIconTextGap(20);
        panel_dAdminInsurance.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 280, 80));

        lblDetailInsurance.setBackground(new java.awt.Color(217, 139, 16));
        lblDetailInsurance.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDetailInsurance.setForeground(new java.awt.Color(255, 255, 255));
        lblDetailInsurance.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetailInsurance.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Double_Up_15px_2.png"))); // NOI18N
        lblDetailInsurance.setText("Click here to view detail");
        lblDetailInsurance.setOpaque(true);
        panel_dAdminInsurance.add(lblDetailInsurance, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 84, 280, 20));

        lblAdminTotalIns.setBackground(new java.awt.Color(255, 0, 0));
        lblAdminTotalIns.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblAdminTotalIns.setForeground(new java.awt.Color(255, 255, 255));
        lblAdminTotalIns.setText("39");
        panel_dAdminInsurance.add(lblAdminTotalIns, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Company_35px.png"))); // NOI18N
        panel_dAdminInsurance.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

        panel_dAdminPlan.setBackground(new java.awt.Color(1, 114, 182));
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder3 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder3.setShadowSize(7);
        panel_dAdminPlan.setBorder(dropShadowBorder3);
        panel_dAdminPlan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_dAdminPlan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_dAdminPlanMouseClicked(evt);
            }
        });
        panel_dAdminPlan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Total Number of Healthcare Plan");
        jLabel8.setIconTextGap(20);
        panel_dAdminPlan.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 280, 80));

        lblDetailPlan.setBackground(new java.awt.Color(1, 101, 165));
        lblDetailPlan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDetailPlan.setForeground(new java.awt.Color(255, 255, 255));
        lblDetailPlan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetailPlan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Double_Up_15px_2.png"))); // NOI18N
        lblDetailPlan.setText("Click here to view detail");
        lblDetailPlan.setOpaque(true);
        panel_dAdminPlan.add(lblDetailPlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 84, 280, 20));

        lblAdminTotalPlan.setBackground(new java.awt.Color(255, 0, 0));
        lblAdminTotalPlan.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblAdminTotalPlan.setForeground(new java.awt.Color(255, 255, 255));
        lblAdminTotalPlan.setText("39");
        panel_dAdminPlan.add(lblAdminTotalPlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Health_Book_35px.png"))); // NOI18N
        panel_dAdminPlan.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

        panel_dAdminDoctor.setBackground(new java.awt.Color(243, 156, 17));
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder4 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder4.setShadowSize(7);
        panel_dAdminDoctor.setBorder(dropShadowBorder4);
        panel_dAdminDoctor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_dAdminDoctor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_dAdminDoctorMouseClicked(evt);
            }
        });
        panel_dAdminDoctor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Total Number of Doctor");
        jLabel11.setIconTextGap(20);
        panel_dAdminDoctor.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 280, 80));

        lblDetailDoctor.setBackground(new java.awt.Color(217, 139, 16));
        lblDetailDoctor.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDetailDoctor.setForeground(new java.awt.Color(255, 255, 255));
        lblDetailDoctor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetailDoctor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Double_Up_15px_2.png"))); // NOI18N
        lblDetailDoctor.setText("Click here to view detail");
        lblDetailDoctor.setOpaque(true);
        panel_dAdminDoctor.add(lblDetailDoctor, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 84, 280, 20));

        lblAdminTotalDoctor.setBackground(new java.awt.Color(255, 0, 0));
        lblAdminTotalDoctor.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblAdminTotalDoctor.setForeground(new java.awt.Color(255, 255, 255));
        lblAdminTotalDoctor.setText("39");
        panel_dAdminDoctor.add(lblAdminTotalDoctor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Doctor_Male_35px.png"))); // NOI18N
        panel_dAdminDoctor.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

        panel_dAdminPatient.setBackground(new java.awt.Color(0, 192, 239));
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder5 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder5.setShadowSize(7);
        panel_dAdminPatient.setBorder(dropShadowBorder5);
        panel_dAdminPatient.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_dAdminPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_dAdminPatientMouseClicked(evt);
            }
        });
        panel_dAdminPatient.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Total Number of Patient");
        jLabel14.setIconTextGap(20);
        panel_dAdminPatient.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 280, 80));

        lblDetailPatient.setBackground(new java.awt.Color(0, 173, 214));
        lblDetailPatient.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDetailPatient.setForeground(new java.awt.Color(255, 255, 255));
        lblDetailPatient.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetailPatient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Double_Up_15px_2.png"))); // NOI18N
        lblDetailPatient.setText("Click here to view detail");
        lblDetailPatient.setOpaque(true);
        panel_dAdminPatient.add(lblDetailPatient, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 84, 280, 20));

        lblAdminTotalPatient.setBackground(new java.awt.Color(255, 0, 0));
        lblAdminTotalPatient.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblAdminTotalPatient.setForeground(new java.awt.Color(255, 255, 255));
        lblAdminTotalPatient.setText("39");
        panel_dAdminPatient.add(lblAdminTotalPatient, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Cast_35px_1.png"))); // NOI18N
        panel_dAdminPatient.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

        panel_dAdminUser.setBackground(new java.awt.Color(1, 114, 182));
        panel_dAdminUser.setBorder(new org.jdesktop.swingx.border.DropShadowBorder());
        panel_dAdminUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_dAdminUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_dAdminUserMouseClicked(evt);
            }
        });
        panel_dAdminUser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Total Number of User");
        jLabel15.setIconTextGap(20);
        panel_dAdminUser.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 280, 80));

        lblDetailUser.setBackground(new java.awt.Color(1, 101, 165));
        lblDetailUser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDetailUser.setForeground(new java.awt.Color(255, 255, 255));
        lblDetailUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetailUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Double_Up_15px_2.png"))); // NOI18N
        lblDetailUser.setText("Click here to view detail");
        lblDetailUser.setOpaque(true);
        panel_dAdminUser.add(lblDetailUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 84, 280, 20));

        lblAdminTotalUser.setBackground(new java.awt.Color(255, 0, 0));
        lblAdminTotalUser.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblAdminTotalUser.setForeground(new java.awt.Color(255, 255, 255));
        lblAdminTotalUser.setText("39");
        panel_dAdminUser.add(lblAdminTotalUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_User_Account_35px.png"))); // NOI18N
        panel_dAdminUser.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panel_dAdminUser, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_dAdminPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_dAdminHP, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_dAdminInsurance, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(panel_dAdminDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panel_dAdminPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panel_dAdminHP, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panel_dAdminInsurance, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(panel_dAdminPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panel_dAdminDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_dAdminPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(panel_dAdminUser, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(141, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_dAdminMainLayout = new javax.swing.GroupLayout(panel_dAdminMain);
        panel_dAdminMain.setLayout(panel_dAdminMainLayout);
        panel_dAdminMainLayout.setHorizontalGroup(
            panel_dAdminMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dAdminMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_dAdminMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_dAdminMainLayout.createSequentialGroup()
                        .addComponent(lbl4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_dAdminMainLayout.setVerticalGroup(
            panel_dAdminMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dAdminMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_dAdminLayout = new javax.swing.GroupLayout(panel_dAdmin);
        panel_dAdmin.setLayout(panel_dAdminLayout);
        panel_dAdminLayout.setHorizontalGroup(
            panel_dAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dAdminLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_dAdminMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        panel_dAdminLayout.setVerticalGroup(
            panel_dAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dAdminLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_dAdminMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        panel_hDashboard.add(panel_dAdmin, "card2");

        panel_dInsMain.setBackground(new java.awt.Color(239, 240, 244));

        lbl5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbl5.setText("Dashboard / Insurance");

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

        panel_dInsTotal.setBackground(new java.awt.Color(1, 114, 182));
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder6 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder6.setShadowSize(7);
        panel_dInsTotal.setBorder(dropShadowBorder6);
        panel_dInsTotal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_dInsTotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_dInsTotalMouseClicked(evt);
            }
        });
        panel_dInsTotal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Total Number of Insurance Company");
        jLabel20.setIconTextGap(20);
        panel_dInsTotal.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 280, 80));

        lblDetailInsurance2.setBackground(new java.awt.Color(1, 101, 165));
        lblDetailInsurance2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDetailInsurance2.setForeground(new java.awt.Color(255, 255, 255));
        lblDetailInsurance2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetailInsurance2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Double_Up_15px_2.png"))); // NOI18N
        lblDetailInsurance2.setText("Click here to view detail");
        lblDetailInsurance2.setOpaque(true);
        panel_dInsTotal.add(lblDetailInsurance2, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 84, 280, 20));

        lblInsTotal.setBackground(new java.awt.Color(255, 0, 0));
        lblInsTotal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblInsTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblInsTotal.setText("39");
        panel_dInsTotal.add(lblInsTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Company_35px.png"))); // NOI18N
        panel_dInsTotal.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

        panel_dInsPlan.setBackground(new java.awt.Color(1, 114, 182));
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder7 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder7.setShadowSize(7);
        panel_dInsPlan.setBorder(dropShadowBorder7);
        panel_dInsPlan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_dInsPlan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_dInsPlanMouseClicked(evt);
            }
        });
        panel_dInsPlan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Total Number of Plan");
        jLabel21.setIconTextGap(20);
        panel_dInsPlan.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 280, 80));

        lblDetailPlan2.setBackground(new java.awt.Color(1, 101, 165));
        lblDetailPlan2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDetailPlan2.setForeground(new java.awt.Color(255, 255, 255));
        lblDetailPlan2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetailPlan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Double_Up_15px_2.png"))); // NOI18N
        lblDetailPlan2.setText("Click here to view detail");
        lblDetailPlan2.setOpaque(true);
        panel_dInsPlan.add(lblDetailPlan2, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 84, 280, 20));

        lblInsTotalPlan.setBackground(new java.awt.Color(255, 0, 0));
        lblInsTotalPlan.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblInsTotalPlan.setForeground(new java.awt.Color(255, 255, 255));
        lblInsTotalPlan.setText("39");
        panel_dInsPlan.add(lblInsTotalPlan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Health_Book_35px.png"))); // NOI18N
        panel_dInsPlan.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

        panel_dInsPatient.setBackground(new java.awt.Color(243, 156, 17));
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder8 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder8.setShadowSize(7);
        panel_dInsPatient.setBorder(dropShadowBorder8);
        panel_dInsPatient.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_dInsPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_dInsPatientMouseClicked(evt);
            }
        });
        panel_dInsPatient.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Total Number of Patient");
        jLabel23.setIconTextGap(20);
        panel_dInsPatient.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 280, 80));

        lblDetailPatient2.setBackground(new java.awt.Color(217, 139, 16));
        lblDetailPatient2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDetailPatient2.setForeground(new java.awt.Color(255, 255, 255));
        lblDetailPatient2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetailPatient2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Double_Up_15px_2.png"))); // NOI18N
        lblDetailPatient2.setText("Click here to view detail");
        lblDetailPatient2.setOpaque(true);
        panel_dInsPatient.add(lblDetailPatient2, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 84, 280, 20));

        lblInsTotalPatient.setBackground(new java.awt.Color(255, 0, 0));
        lblInsTotalPatient.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblInsTotalPatient.setForeground(new java.awt.Color(255, 255, 255));
        lblInsTotalPatient.setText("39");
        panel_dInsPatient.add(lblInsTotalPatient, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Cast_35px_1.png"))); // NOI18N
        panel_dInsPatient.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panel_dInsTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_dInsPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(panel_dInsPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(368, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(panel_dInsTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panel_dInsPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_dInsPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(268, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_dInsMainLayout = new javax.swing.GroupLayout(panel_dInsMain);
        panel_dInsMain.setLayout(panel_dInsMainLayout);
        panel_dInsMainLayout.setHorizontalGroup(
            panel_dInsMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dInsMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_dInsMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_dInsMainLayout.createSequentialGroup()
                        .addComponent(lbl5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_dInsMainLayout.setVerticalGroup(
            panel_dInsMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dInsMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_dInsuranceLayout = new javax.swing.GroupLayout(panel_dInsurance);
        panel_dInsurance.setLayout(panel_dInsuranceLayout);
        panel_dInsuranceLayout.setHorizontalGroup(
            panel_dInsuranceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dInsuranceLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_dInsMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        panel_dInsuranceLayout.setVerticalGroup(
            panel_dInsuranceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dInsuranceLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_dInsMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        panel_hDashboard.add(panel_dInsurance, "card3");

        panel_dHPMain.setBackground(new java.awt.Color(239, 240, 244));

        lbl8.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbl8.setText("Dashboard / Healthcare Provider");

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

        panel_dHPTotal.setBackground(new java.awt.Color(1, 114, 182));
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder9 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder9.setShadowSize(7);
        panel_dHPTotal.setBorder(dropShadowBorder9);
        panel_dHPTotal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_dHPTotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_dHPTotalMouseClicked(evt);
            }
        });
        panel_dHPTotal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Total Number of Healthcare Provider");
        jLabel22.setIconTextGap(20);
        panel_dHPTotal.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 280, 80));

        lblDetailInsurance3.setBackground(new java.awt.Color(1, 101, 165));
        lblDetailInsurance3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDetailInsurance3.setForeground(new java.awt.Color(255, 255, 255));
        lblDetailInsurance3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetailInsurance3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Double_Up_15px_2.png"))); // NOI18N
        lblDetailInsurance3.setText("Click here to view detail");
        lblDetailInsurance3.setOpaque(true);
        panel_dHPTotal.add(lblDetailInsurance3, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 84, 280, 20));

        lblHPTotal.setBackground(new java.awt.Color(255, 0, 0));
        lblHPTotal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblHPTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblHPTotal.setText("39");
        panel_dHPTotal.add(lblHPTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Hospital_3_35px.png"))); // NOI18N
        panel_dHPTotal.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

        panel_dHPDoctor.setBackground(new java.awt.Color(1, 114, 182));
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder10 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder10.setShadowSize(7);
        panel_dHPDoctor.setBorder(dropShadowBorder10);
        panel_dHPDoctor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_dHPDoctor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_dHPDoctorMouseClicked(evt);
            }
        });
        panel_dHPDoctor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Total Number of Doctor");
        jLabel24.setIconTextGap(20);
        panel_dHPDoctor.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 280, 80));

        lblDetailDoctor3.setBackground(new java.awt.Color(1, 101, 165));
        lblDetailDoctor3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDetailDoctor3.setForeground(new java.awt.Color(255, 255, 255));
        lblDetailDoctor3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetailDoctor3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Double_Up_15px_2.png"))); // NOI18N
        lblDetailDoctor3.setText("Click here to view detail");
        lblDetailDoctor3.setOpaque(true);
        panel_dHPDoctor.add(lblDetailDoctor3, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 84, 280, 20));

        lblHPTotalDoctor.setBackground(new java.awt.Color(255, 0, 0));
        lblHPTotalDoctor.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblHPTotalDoctor.setForeground(new java.awt.Color(255, 255, 255));
        lblHPTotalDoctor.setText("39");
        panel_dHPDoctor.add(lblHPTotalDoctor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Doctor_Male_35px.png"))); // NOI18N
        panel_dHPDoctor.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

        panel_dHPPatient.setBackground(new java.awt.Color(243, 156, 17));
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder11 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder11.setShadowSize(7);
        panel_dHPPatient.setBorder(dropShadowBorder11);
        panel_dHPPatient.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_dHPPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_dHPPatientMouseClicked(evt);
            }
        });
        panel_dHPPatient.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Total Number of Patient");
        jLabel25.setIconTextGap(20);
        panel_dHPPatient.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 280, 80));

        lblDetailPatient3.setBackground(new java.awt.Color(217, 139, 16));
        lblDetailPatient3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDetailPatient3.setForeground(new java.awt.Color(255, 255, 255));
        lblDetailPatient3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetailPatient3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Double_Up_15px_2.png"))); // NOI18N
        lblDetailPatient3.setText("Click here to view detail");
        lblDetailPatient3.setOpaque(true);
        panel_dHPPatient.add(lblDetailPatient3, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 84, 280, 20));

        lblHPTotalPatient.setBackground(new java.awt.Color(255, 0, 0));
        lblHPTotalPatient.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblHPTotalPatient.setForeground(new java.awt.Color(255, 255, 255));
        lblHPTotalPatient.setText("39");
        panel_dHPPatient.add(lblHPTotalPatient, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Cast_35px_1.png"))); // NOI18N
        panel_dHPPatient.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panel_dHPTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_dHPDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(panel_dHPPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(368, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(panel_dHPTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panel_dHPDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_dHPPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(268, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_dHPMainLayout = new javax.swing.GroupLayout(panel_dHPMain);
        panel_dHPMain.setLayout(panel_dHPMainLayout);
        panel_dHPMainLayout.setHorizontalGroup(
            panel_dHPMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dHPMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_dHPMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_dHPMainLayout.createSequentialGroup()
                        .addComponent(lbl8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_dHPMainLayout.setVerticalGroup(
            panel_dHPMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dHPMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_dHealthcareLayout = new javax.swing.GroupLayout(panel_dHealthcare);
        panel_dHealthcare.setLayout(panel_dHealthcareLayout);
        panel_dHealthcareLayout.setHorizontalGroup(
            panel_dHealthcareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dHealthcareLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_dHPMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        panel_dHealthcareLayout.setVerticalGroup(
            panel_dHealthcareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dHealthcareLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_dHPMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        panel_hDashboard.add(panel_dHealthcare, "card4");

        panel_hInsMain4.setBackground(new java.awt.Color(239, 240, 244));

        lbl7.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbl7.setText("Dashboard / Normal");

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 996, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 522, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panel_hInsMain4Layout = new javax.swing.GroupLayout(panel_hInsMain4);
        panel_hInsMain4.setLayout(panel_hInsMain4Layout);
        panel_hInsMain4Layout.setHorizontalGroup(
            panel_hInsMain4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_hInsMain4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_hInsMain4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_hInsMain4Layout.createSequentialGroup()
                        .addComponent(lbl7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_hInsMain4Layout.setVerticalGroup(
            panel_hInsMain4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_hInsMain4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_dNormalLayout = new javax.swing.GroupLayout(panel_dNormal);
        panel_dNormal.setLayout(panel_dNormalLayout);
        panel_dNormalLayout.setHorizontalGroup(
            panel_dNormalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dNormalLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_hInsMain4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        panel_dNormalLayout.setVerticalGroup(
            panel_dNormalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_dNormalLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_hInsMain4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        panel_hDashboard.add(panel_dNormal, "card5");

        panel_hInsMain1.setBackground(new java.awt.Color(239, 240, 244));

        lbl9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbl9.setText("User List");

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

        jSeparator6.setBackground(new java.awt.Color(0, 0, 0));

        tfSearchUser.setForeground(java.awt.Color.gray);
        tfSearchUser.setText("Search here");
        tfSearchUser.setBorder(null);
        tfSearchUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSearchUserKeyReleased(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel26.setText("Search by:");

        cbSearchUser.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbSearchUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Username", "First Name", "Last Name", "Type", "Join Date" }));

        jScrollPane6.setBorder(null);

        tblUser.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tblUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "First Name", "Last Name", "Password", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUser.setOpaque(false);
        tblUser.setRowHeight(24);
        tblUser.setSelectionBackground(new java.awt.Color(18, 55, 92));
        tblUser.setSelectionForeground(new java.awt.Color(250, 250, 250));
        tblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUserMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblUser);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 976, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfSearchUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbSearchUser, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 503, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbSearchUser, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addComponent(tfSearchUser, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_hInsMain1Layout = new javax.swing.GroupLayout(panel_hInsMain1);
        panel_hInsMain1.setLayout(panel_hInsMain1Layout);
        panel_hInsMain1Layout.setHorizontalGroup(
            panel_hInsMain1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_hInsMain1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_hInsMain1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_hInsMain1Layout.createSequentialGroup()
                        .addComponent(lbl9)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_hInsMain1Layout.setVerticalGroup(
            panel_hInsMain1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_hInsMain1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_userLayout = new javax.swing.GroupLayout(panel_user);
        panel_user.setLayout(panel_userLayout);
        panel_userLayout.setHorizontalGroup(
            panel_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_userLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_hInsMain1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        panel_userLayout.setVerticalGroup(
            panel_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_userLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_hInsMain1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        panel_hDashboard.add(panel_user, "card6");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel17.setText("Please Log in to view this option.");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel18.setText("Nothing to show here.");

        javax.swing.GroupLayout panel_emptyLayout = new javax.swing.GroupLayout(panel_empty);
        panel_empty.setLayout(panel_emptyLayout);
        panel_emptyLayout.setHorizontalGroup(
            panel_emptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_emptyLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(panel_emptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addContainerGap())
        );
        panel_emptyLayout.setVerticalGroup(
            panel_emptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_emptyLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addContainerGap())
        );

        panel_hDashboard.add(panel_empty, "card7");

        panel_home.add(panel_hDashboard, "card2");

        panel_hInsMain.setBackground(new java.awt.Color(239, 240, 244));

        lbl1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbl1.setText("Insurance Company");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));

        tfSearchInsCom.setForeground(java.awt.Color.gray);
        tfSearchInsCom.setText("Search here");
        tfSearchInsCom.setBorder(null);
        tfSearchInsCom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSearchInsComKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Search by:");

        cbSearchIns.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbSearchIns.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Name", "Address" }));

        panel_viewPlan.setBackground(new java.awt.Color(0, 160, 174));
        panel_viewPlan.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_viewPlan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_viewPlan.setPreferredSize(new java.awt.Dimension(90, 26));
        panel_viewPlan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_viewPlanMouseClicked(evt);
            }
        });

        lblSearchHP2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblSearchHP2.setForeground(new java.awt.Color(255, 255, 255));
        lblSearchHP2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSearchHP2.setText("View Plan");

        javax.swing.GroupLayout panel_viewPlanLayout = new javax.swing.GroupLayout(panel_viewPlan);
        panel_viewPlan.setLayout(panel_viewPlanLayout);
        panel_viewPlanLayout.setHorizontalGroup(
            panel_viewPlanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSearchHP2, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        );
        panel_viewPlanLayout.setVerticalGroup(
            panel_viewPlanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSearchHP2, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
        );

        jScrollPane2.setBorder(null);

        tblInsCom.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        tblInsCom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Address"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblInsCom.setOpaque(false);
        tblInsCom.setRowHeight(24);
        tblInsCom.setSelectionBackground(new java.awt.Color(18, 55, 92));
        tblInsCom.setSelectionForeground(new java.awt.Color(250, 250, 250));
        tblInsCom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInsComMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblInsCom);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 976, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfSearchInsCom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbSearchIns, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel_viewPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbSearchIns, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10))
                            .addComponent(panel_viewPlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(tfSearchInsCom, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_hInsMainLayout = new javax.swing.GroupLayout(panel_hInsMain);
        panel_hInsMain.setLayout(panel_hInsMainLayout);
        panel_hInsMainLayout.setHorizontalGroup(
            panel_hInsMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_hInsMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_hInsMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_hInsMainLayout.createSequentialGroup()
                        .addComponent(lbl1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_hInsMainLayout.setVerticalGroup(
            panel_hInsMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_hInsMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_hInsurance_comLayout = new javax.swing.GroupLayout(panel_hInsurance_com);
        panel_hInsurance_com.setLayout(panel_hInsurance_comLayout);
        panel_hInsurance_comLayout.setHorizontalGroup(
            panel_hInsurance_comLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_hInsurance_comLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_hInsMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        panel_hInsurance_comLayout.setVerticalGroup(
            panel_hInsurance_comLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_hInsurance_comLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panel_hInsMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        panel_home.add(panel_hInsurance_com, "card4");

        panel_hHPlanMain.setBackground(new java.awt.Color(239, 240, 244));

        lbl2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbl2.setText("Healthcare Plans");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));

        tfSearchPlan.setForeground(java.awt.Color.gray);
        tfSearchPlan.setText("Search here");
        tfSearchPlan.setBorder(null);
        tfSearchPlan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSearchPlanKeyReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Search by:");

        cbSearchPlan.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbSearchPlan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Name", "Description" }));

        panel_viewDoctor2.setBackground(new java.awt.Color(0, 160, 174));
        panel_viewDoctor2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_viewDoctor2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_viewDoctor2.setPreferredSize(new java.awt.Dimension(90, 26));
        panel_viewDoctor2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_viewDoctor2MouseClicked(evt);
            }
        });

        lblSearchHP3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblSearchHP3.setForeground(new java.awt.Color(255, 255, 255));
        lblSearchHP3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSearchHP3.setText("View Doctor");

        javax.swing.GroupLayout panel_viewDoctor2Layout = new javax.swing.GroupLayout(panel_viewDoctor2);
        panel_viewDoctor2.setLayout(panel_viewDoctor2Layout);
        panel_viewDoctor2Layout.setHorizontalGroup(
            panel_viewDoctor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSearchHP3, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        );
        panel_viewDoctor2Layout.setVerticalGroup(
            panel_viewDoctor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSearchHP3, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
        );

        jScrollPane3.setBorder(null);

        tblPlan.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        tblPlan.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPlan.setOpaque(false);
        tblPlan.setRowHeight(24);
        tblPlan.setSelectionBackground(new java.awt.Color(18, 55, 92));
        tblPlan.setSelectionForeground(new java.awt.Color(250, 250, 250));
        tblPlan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPlanMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblPlan);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 976, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfSearchPlan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbSearchPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel_viewDoctor2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbSearchPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12))
                            .addComponent(panel_viewDoctor2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(tfSearchPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_hHPlanMainLayout = new javax.swing.GroupLayout(panel_hHPlanMain);
        panel_hHPlanMain.setLayout(panel_hHPlanMainLayout);
        panel_hHPlanMainLayout.setHorizontalGroup(
            panel_hHPlanMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_hHPlanMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_hHPlanMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_hHPlanMainLayout.createSequentialGroup()
                        .addComponent(lbl2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_hHPlanMainLayout.setVerticalGroup(
            panel_hHPlanMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_hHPlanMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_hHealthcarePlanLayout = new javax.swing.GroupLayout(panel_hHealthcarePlan);
        panel_hHealthcarePlan.setLayout(panel_hHealthcarePlanLayout);
        panel_hHealthcarePlanLayout.setHorizontalGroup(
            panel_hHealthcarePlanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_hHPlanMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_hHealthcarePlanLayout.setVerticalGroup(
            panel_hHealthcarePlanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_hHPlanMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panel_home.add(panel_hHealthcarePlan, "card10");

        panel_hHealthcarePro.setBackground(new java.awt.Color(239, 240, 244));

        lblHealthcarePro2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblHealthcarePro2.setText("Healthcare Providers");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));

        tfSearchHP.setForeground(java.awt.Color.gray);
        tfSearchHP.setText("Search here");
        tfSearchHP.setBorder(null);
        tfSearchHP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSearchHPKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Search by:");

        cbSearchHP.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbSearchHP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Name", "Address" }));

        panel_viewDoctor.setBackground(new java.awt.Color(0, 160, 174));
        panel_viewDoctor.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_viewDoctor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_viewDoctor.setPreferredSize(new java.awt.Dimension(90, 26));
        panel_viewDoctor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_viewDoctorMouseClicked(evt);
            }
        });

        lblSearchHP1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblSearchHP1.setForeground(new java.awt.Color(255, 255, 255));
        lblSearchHP1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSearchHP1.setText("View Doctor");

        javax.swing.GroupLayout panel_viewDoctorLayout = new javax.swing.GroupLayout(panel_viewDoctor);
        panel_viewDoctor.setLayout(panel_viewDoctorLayout);
        panel_viewDoctorLayout.setHorizontalGroup(
            panel_viewDoctorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSearchHP1, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        );
        panel_viewDoctorLayout.setVerticalGroup(
            panel_viewDoctorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSearchHP1, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
        );

        jScrollPane1.setBorder(null);

        tblHP.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        tblHP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Address"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHP.setOpaque(false);
        tblHP.setRowHeight(24);
        tblHP.setSelectionBackground(new java.awt.Color(18, 55, 92));
        tblHP.setSelectionForeground(new java.awt.Color(250, 250, 250));
        tblHP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHP);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 976, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfSearchHP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbSearchHP, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel_viewDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbSearchHP, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9))
                            .addComponent(panel_viewDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(tfSearchHP, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel9, tfSearchHP});

        javax.swing.GroupLayout panel_hHealthcareProLayout = new javax.swing.GroupLayout(panel_hHealthcarePro);
        panel_hHealthcarePro.setLayout(panel_hHealthcareProLayout);
        panel_hHealthcareProLayout.setHorizontalGroup(
            panel_hHealthcareProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_hHealthcareProLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_hHealthcareProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_hHealthcareProLayout.createSequentialGroup()
                        .addComponent(lblHealthcarePro2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_hHealthcareProLayout.setVerticalGroup(
            panel_hHealthcareProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_hHealthcareProLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHealthcarePro2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel_home.add(panel_hHealthcarePro, "card3");

        panel_hDoctor.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                panel_hDoctorComponentResized(evt);
            }
        });
        panel_hDoctor.setLayout(new java.awt.BorderLayout());

        jXCollapsiblePane1.setCollapsed(true);
        panel_hDoctor.add(jXCollapsiblePane1, java.awt.BorderLayout.PAGE_START);

        panel_hDoctorMain.setBackground(new java.awt.Color(239, 240, 244));

        lbl3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbl3.setText("Doctors");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

        jSeparator4.setBackground(new java.awt.Color(0, 0, 0));

        tfSearchDoctor.setForeground(java.awt.Color.gray);
        tfSearchDoctor.setText("Search here");
        tfSearchDoctor.setBorder(null);
        tfSearchDoctor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSearchDoctorKeyReleased(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Search by:");

        cbSearchDoctor.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbSearchDoctor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Name", "Date of Birth", "Address", "Area of Specialty", "Degree" }));

        panel_viewProvider2.setBackground(new java.awt.Color(0, 160, 174));
        panel_viewProvider2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_viewProvider2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_viewProvider2.setPreferredSize(new java.awt.Dimension(90, 26));
        panel_viewProvider2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_viewProvider2MouseClicked(evt);
            }
        });

        lblSearchHP4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblSearchHP4.setForeground(new java.awt.Color(255, 255, 255));
        lblSearchHP4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSearchHP4.setText("View Provider");

        javax.swing.GroupLayout panel_viewProvider2Layout = new javax.swing.GroupLayout(panel_viewProvider2);
        panel_viewProvider2.setLayout(panel_viewProvider2Layout);
        panel_viewProvider2Layout.setHorizontalGroup(
            panel_viewProvider2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSearchHP4, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        );
        panel_viewProvider2Layout.setVerticalGroup(
            panel_viewProvider2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSearchHP4, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
        );

        jScrollPane4.setBorder(null);

        tblDoctor.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        tblDoctor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Gender", "Date of Birth", "Address", "Area of Specialty", "Degree"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDoctor.setOpaque(false);
        tblDoctor.setRowHeight(24);
        tblDoctor.setSelectionBackground(new java.awt.Color(18, 55, 92));
        tblDoctor.setSelectionForeground(new java.awt.Color(250, 250, 250));
        tblDoctor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDoctorMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblDoctor);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 976, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfSearchDoctor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbSearchDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel_viewProvider2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbSearchDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13))
                            .addComponent(panel_viewProvider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(tfSearchDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_hDoctorMainLayout = new javax.swing.GroupLayout(panel_hDoctorMain);
        panel_hDoctorMain.setLayout(panel_hDoctorMainLayout);
        panel_hDoctorMainLayout.setHorizontalGroup(
            panel_hDoctorMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_hDoctorMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_hDoctorMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_hDoctorMainLayout.createSequentialGroup()
                        .addComponent(lbl3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_hDoctorMainLayout.setVerticalGroup(
            panel_hDoctorMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_hDoctorMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel_hDoctor.add(panel_hDoctorMain, java.awt.BorderLayout.CENTER);

        btnToggle.setBackground(new java.awt.Color(255, 255, 0));
        btnToggle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnToggle.setForeground(new java.awt.Color(18, 55, 92));
        btnToggle.setText(">>  Toggle Doctor List / Patient List By Specific Doctor  <<");
        btnToggle.setAlignmentX(0.5F);
        btnToggle.setBorder(null);
        btnToggle.setContentAreaFilled(false);
        btnToggle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnToggle.setFocusPainted(false);
        btnToggle.setOpaque(true);
        btnToggle.setPreferredSize(new java.awt.Dimension(311, 20));
        btnToggle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnToggleMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnToggleMouseExited(evt);
            }
        });
        panel_hDoctor.add(btnToggle, java.awt.BorderLayout.SOUTH);
        btnToggle.setBackground(Color.YELLOW);

        panel_home.add(panel_hDoctor, "card11");

        panel_hDoctorMain1.setBackground(new java.awt.Color(239, 240, 244));

        lbl6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lbl6.setText("Patient");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));

        tfSearchPatient.setForeground(java.awt.Color.gray);
        tfSearchPatient.setText("Search here");
        tfSearchPatient.setBorder(null);
        tfSearchPatient.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfSearchPatientKeyReleased(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setText("Search by:");

        cbSearchPatient.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbSearchPatient.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SSN", "Name", "Date of Birth", "Address", "Health Condition", "Phone", "Employer Name", "Employer Contact" }));

        panel_viewDisease.setBackground(new java.awt.Color(0, 160, 174));
        panel_viewDisease.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel_viewDisease.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_viewDisease.setPreferredSize(new java.awt.Dimension(90, 26));
        panel_viewDisease.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panel_viewDiseaseMouseClicked(evt);
            }
        });

        lblSearchHP5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblSearchHP5.setForeground(new java.awt.Color(255, 255, 255));
        lblSearchHP5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSearchHP5.setText("View Disease");

        javax.swing.GroupLayout panel_viewDiseaseLayout = new javax.swing.GroupLayout(panel_viewDisease);
        panel_viewDisease.setLayout(panel_viewDiseaseLayout);
        panel_viewDiseaseLayout.setHorizontalGroup(
            panel_viewDiseaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSearchHP5, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        );
        panel_viewDiseaseLayout.setVerticalGroup(
            panel_viewDiseaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSearchHP5, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
        );

        jScrollPane5.setBorder(null);

        tblPatient.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        tblPatient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SSN", "Name", "Gender", "Date of Birth", "Address", "Healthcare Condition", "Phone", "Employer Name", "Employer Contact"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPatient.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblPatient.setOpaque(false);
        tblPatient.setRowHeight(24);
        tblPatient.setSelectionBackground(new java.awt.Color(18, 55, 92));
        tblPatient.setSelectionForeground(new java.awt.Color(250, 250, 250));
        tblPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPatientMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblPatient);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfSearchPatient, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbSearchPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 359, Short.MAX_VALUE)
                .addComponent(panel_viewDisease, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbSearchPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel19))
                            .addComponent(panel_viewDisease, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(tfSearchPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_hDoctorMain1Layout = new javax.swing.GroupLayout(panel_hDoctorMain1);
        panel_hDoctorMain1.setLayout(panel_hDoctorMain1Layout);
        panel_hDoctorMain1Layout.setHorizontalGroup(
            panel_hDoctorMain1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_hDoctorMain1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_hDoctorMain1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_hDoctorMain1Layout.createSequentialGroup()
                        .addComponent(lbl6)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_hDoctorMain1Layout.setVerticalGroup(
            panel_hDoctorMain1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_hDoctorMain1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel_hPatientLayout = new javax.swing.GroupLayout(panel_hPatient);
        panel_hPatient.setLayout(panel_hPatientLayout);
        panel_hPatientLayout.setHorizontalGroup(
            panel_hPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_hDoctorMain1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_hPatientLayout.setVerticalGroup(
            panel_hPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_hDoctorMain1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panel_home.add(panel_hPatient, "card5");

        jLabel4.setText("Statistic");

        javax.swing.GroupLayout panel_hStatisticLayout = new javax.swing.GroupLayout(panel_hStatistic);
        panel_hStatistic.setLayout(panel_hStatisticLayout);
        panel_hStatisticLayout.setHorizontalGroup(
            panel_hStatisticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_hStatisticLayout.createSequentialGroup()
                .addContainerGap(651, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(331, 331, 331))
        );
        panel_hStatisticLayout.setVerticalGroup(
            panel_hStatisticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_hStatisticLayout.createSequentialGroup()
                .addGap(246, 246, 246)
                .addComponent(jLabel4)
                .addContainerGap(326, Short.MAX_VALUE))
        );

        panel_home.add(panel_hStatistic, "card6");

        jPanel12.setLayout(new java.awt.GridLayout(2, 0));

        jPanel13.setLayout(new java.awt.GridLayout(1, 0));

        lblUIns.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblUIns.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUIns.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Company_100px.png"))); // NOI18N
        lblUIns.setText("Insurance Company");
        lblUIns.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblUIns.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblUIns.setIconTextGap(5);
        lblUIns.setMaximumSize(new java.awt.Dimension(220, 137));
        lblUIns.setMinimumSize(new java.awt.Dimension(220, 137));
        lblUIns.setOpaque(true);
        lblUIns.setPreferredSize(new java.awt.Dimension(220, 137));
        lblUIns.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblUIns.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUInsMouseClicked(evt);
            }
        });
        jPanel13.add(lblUIns);

        lblUHP.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblUHP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUHP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Hospital_3_100px.png"))); // NOI18N
        lblUHP.setText("Healthcare Provider");
        lblUHP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblUHP.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblUHP.setIconTextGap(5);
        lblUHP.setMaximumSize(new java.awt.Dimension(220, 137));
        lblUHP.setMinimumSize(new java.awt.Dimension(220, 137));
        lblUHP.setOpaque(true);
        lblUHP.setPreferredSize(new java.awt.Dimension(220, 137));
        lblUHP.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel13.add(lblUHP);

        lblUHPlan.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblUHPlan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUHPlan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Health_Book_100px.png"))); // NOI18N
        lblUHPlan.setText("Healthcare Plan");
        lblUHPlan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblUHPlan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblUHPlan.setIconTextGap(5);
        lblUHPlan.setMaximumSize(new java.awt.Dimension(220, 137));
        lblUHPlan.setMinimumSize(new java.awt.Dimension(220, 137));
        lblUHPlan.setOpaque(true);
        lblUHPlan.setPreferredSize(new java.awt.Dimension(220, 137));
        lblUHPlan.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblUHPlan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUHPlanMouseClicked(evt);
            }
        });
        jPanel13.add(lblUHPlan);

        jPanel12.add(jPanel13);

        jPanel14.setLayout(new java.awt.GridLayout(1, 0));

        lblUDoctor.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblUDoctor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUDoctor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Doctor_Male_100px.png"))); // NOI18N
        lblUDoctor.setText("Doctor");
        lblUDoctor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblUDoctor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblUDoctor.setIconTextGap(5);
        lblUDoctor.setMaximumSize(new java.awt.Dimension(220, 137));
        lblUDoctor.setMinimumSize(new java.awt.Dimension(220, 137));
        lblUDoctor.setOpaque(true);
        lblUDoctor.setPreferredSize(new java.awt.Dimension(220, 137));
        lblUDoctor.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblUDoctor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUDoctorMouseClicked(evt);
            }
        });
        jPanel14.add(lblUDoctor);

        lblUPatient.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblUPatient.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUPatient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Cast_100px.png"))); // NOI18N
        lblUPatient.setText("Patient");
        lblUPatient.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblUPatient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblUPatient.setIconTextGap(5);
        lblUPatient.setMaximumSize(new java.awt.Dimension(220, 137));
        lblUPatient.setMinimumSize(new java.awt.Dimension(220, 137));
        lblUPatient.setOpaque(true);
        lblUPatient.setPreferredSize(new java.awt.Dimension(220, 137));
        lblUPatient.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblUPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUPatientMouseClicked(evt);
            }
        });
        jPanel14.add(lblUPatient);

        lblUUser.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblUUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_User_Account_100px.png"))); // NOI18N
        lblUUser.setText("User");
        lblUUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblUUser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblUUser.setIconTextGap(5);
        lblUUser.setMaximumSize(new java.awt.Dimension(220, 137));
        lblUUser.setMinimumSize(new java.awt.Dimension(220, 137));
        lblUUser.setOpaque(true);
        lblUUser.setPreferredSize(new java.awt.Dimension(220, 137));
        lblUUser.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel14.add(lblUUser);

        jPanel12.add(jPanel14);

        javax.swing.GroupLayout panel_hUpdatesLayout = new javax.swing.GroupLayout(panel_hUpdates);
        panel_hUpdates.setLayout(panel_hUpdatesLayout);
        panel_hUpdatesLayout.setHorizontalGroup(
            panel_hUpdatesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_hUpdatesLayout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 960, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        panel_hUpdatesLayout.setVerticalGroup(
            panel_hUpdatesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_hUpdatesLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                .addGap(71, 71, 71))
        );

        panel_home.add(panel_hUpdates, "card7");

        panel_hFind.setLayout(new java.awt.CardLayout());

        panel_findOption.setLayout(new java.awt.GridLayout(1, 0));

        lblDoctor.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblDoctor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDoctor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Medical_Doctor_104px.png"))); // NOI18N
        lblDoctor.setText("Doctor");
        lblDoctor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblDoctor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblDoctor.setIconTextGap(10);
        lblDoctor.setOpaque(true);
        lblDoctor.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblDoctor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDoctorMouseClicked(evt);
            }
        });
        panel_findOption.add(lblDoctor);

        lblPatient.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblPatient.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPatient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Nurse_Call_104px.png"))); // NOI18N
        lblPatient.setText("Patient");
        lblPatient.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPatient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblPatient.setIconTextGap(10);
        lblPatient.setOpaque(true);
        lblPatient.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPatientMouseClicked(evt);
            }
        });
        panel_findOption.add(lblPatient);

        lblTreatment.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblTreatment.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTreatment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Treatment_104px.png"))); // NOI18N
        lblTreatment.setText("Treatment");
        lblTreatment.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblTreatment.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblTreatment.setIconTextGap(10);
        lblTreatment.setOpaque(true);
        lblTreatment.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblTreatment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTreatmentMouseClicked(evt);
            }
        });
        panel_findOption.add(lblTreatment);

        panel_hFind.add(panel_findOption, "card2");

        panel_home.add(panel_hFind, "card9");

        jLabel6.setText("About");

        javax.swing.GroupLayout panel_hAboutLayout = new javax.swing.GroupLayout(panel_hAbout);
        panel_hAbout.setLayout(panel_hAboutLayout);
        panel_hAboutLayout.setHorizontalGroup(
            panel_hAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_hAboutLayout.createSequentialGroup()
                .addContainerGap(667, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(324, 324, 324))
        );
        panel_hAboutLayout.setVerticalGroup(
            panel_hAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_hAboutLayout.createSequentialGroup()
                .addGap(245, 245, 245)
                .addComponent(jLabel6)
                .addContainerGap(327, Short.MAX_VALUE))
        );

        panel_home.add(panel_hAbout, "card8");

        javax.swing.GroupLayout panel_mainLayout = new javax.swing.GroupLayout(panel_main);
        panel_main.setLayout(panel_mainLayout);
        panel_mainLayout.setHorizontalGroup(
            panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_mainLayout.createSequentialGroup()
                .addComponent(panel_left, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_login, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_home, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel_titlebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        panel_mainLayout.setVerticalGroup(
            panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_mainLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel_left, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_mainLayout.createSequentialGroup()
                        .addComponent(panel_titlebar, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(panel_home, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(0, 0, 0))
        );

        scpMain.setViewportView(panel_main);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scpMain, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1265, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scpMain, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Underline text
    private void lblLoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoginMouseEntered
        Font font = lblLogin.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);  
        lblLogin.setFont(font.deriveFont(attributes));
        
    }//GEN-LAST:event_lblLoginMouseEntered

    // No underline text
    private void lblLoginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoginMouseExited
        Font font = lblLogin.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, -1);
        lblLogin.setFont(font.deriveFont(attributes));
        
    }//GEN-LAST:event_lblLoginMouseExited

    private void panel_healthcareProMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_healthcareProMousePressed
        setColorChooseSidePane(panel_healthcarePro, panel_dashboard, panel_healthcarePlan, panel_insurance_com, panel_about, panel_statistic, panel_update, panel_find, panel_doctor, panel_patient);
        
        setIndexChooseSidePane(false, false, false, true, false, false, false, false, false, false);
        
        showPanelInCard(panel_home, panel_hHealthcarePro);
        
        SubTable.setTableHeader(tblHP, new Color(240, 240, 240), Color.BLACK, new Font("Tahoma", Font.PLAIN, 12));
        
        TextFieldSearch.setTextLook(tfSearchHP);
    }//GEN-LAST:event_panel_healthcareProMousePressed

    private void panel_dashboardMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_dashboardMousePressed
        if (frmLogin.isLoggedin()) {
            setColorChooseSidePane(panel_dashboard, panel_healthcarePro, panel_healthcarePlan, panel_insurance_com, panel_about, panel_statistic, panel_update, panel_find, panel_doctor, panel_patient);
        
            setIndexChooseSidePane(true, false, false, false, false, false, false, false, false, false);
        
        
            if (frmLogin.getAccountType().equals("Admin")) {
                showPanelInCard(panel_home, panel_hDashboard);
                showPanelInCard(panel_hDashboard, panel_dAdmin);
            }

            else if (frmLogin.getAccountType().equals("Insurance")) {
                showPanelInCard(panel_home, panel_hDashboard);
                showPanelInCard(panel_hDashboard, panel_dInsurance);
            }

            else if (frmLogin.getAccountType().equals("Healthcare")) {
                showPanelInCard(panel_home, panel_hDashboard);
                showPanelInCard(panel_hDashboard, panel_dHealthcare);            
            }

            else {
                showPanelInCard(panel_home, panel_hDashboard);
                showPanelInCard(panel_hDashboard, panel_dNormal);
            }
        }        
        else {
            JOptionPane.showConfirmDialog(null, "Please Login to view this option!", "Cannot use this option", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_panel_dashboardMousePressed

    private void panel_statisticMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_statisticMousePressed
        setColorChooseSidePane(panel_statistic, panel_dashboard, panel_healthcarePlan, panel_insurance_com, panel_about, panel_healthcarePro, panel_update, panel_find, panel_doctor, panel_patient);
        
        setIndexChooseSidePane(false, false, false, false, false, true, false, false, false, false);
        
        showPanelInCard(panel_home, panel_hStatistic);
    }//GEN-LAST:event_panel_statisticMousePressed

    private void panel_updateMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_updateMousePressed
        if (frmLogin.isLoggedin() && frmLogin.getAccountType().equals("Admin")) {
            setColorChooseSidePane(panel_update, panel_dashboard, panel_healthcarePlan, panel_insurance_com, panel_about, panel_healthcarePro, panel_statistic, panel_find, panel_doctor, panel_patient);
        
            setIndexChooseSidePane(false, false, false, false, false, false, true, false, false, false);
        
            showPanelInCard(panel_home, panel_hUpdates);
        }
        else {
            JOptionPane.showConfirmDialog(null, "Require an administrator login", "Cannot use this option", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_panel_updateMousePressed
 
    private void panel_aboutMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_aboutMousePressed
        setColorChooseSidePane(panel_about, panel_dashboard, panel_healthcarePlan, panel_insurance_com, panel_statistic, panel_healthcarePro, panel_update, panel_find, panel_doctor, panel_patient);
        
        setIndexChooseSidePane(false, false, false, false, false, false, false, false, true, false);
        
        showPanelInCard(panel_home, panel_hAbout);
    }//GEN-LAST:event_panel_aboutMousePressed

    private void panel_patientMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_patientMousePressed
        setColorChooseSidePane(panel_patient, panel_dashboard, panel_healthcarePlan, panel_insurance_com, panel_about, panel_healthcarePro, panel_update, panel_find, panel_doctor, panel_statistic);
        
        setIndexChooseSidePane(false, false, false, false, false, false, false, false, false, true);
        
        showPanelInCard(panel_home, panel_hPatient);
        
        SubTable.setTableHeader(tblPatient, new Color(240, 240, 240), Color.BLACK, new Font("Tahoma", Font.PLAIN, 12));
        
        TextFieldSearch.setTextLook(tfSearchPatient);
    }//GEN-LAST:event_panel_patientMousePressed

    private void panel_insurance_comMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_insurance_comMousePressed
        setColorChooseSidePane(panel_insurance_com, panel_dashboard, panel_healthcarePlan, panel_patient, panel_about, panel_healthcarePro, panel_update, panel_find, panel_doctor, panel_statistic);
        
        setIndexChooseSidePane(false, true, false, false, false, false, false, false, false, false);
        
        showPanelInCard(panel_home, panel_hInsurance_com);
        
        SubTable.setTableHeader(tblInsCom, new Color(240, 240, 240), Color.BLACK, new Font("Tahoma", Font.PLAIN, 12));
        
        TextFieldSearch.setTextLook(tfSearchInsCom);
    }//GEN-LAST:event_panel_insurance_comMousePressed

    private void tfSearchHPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchHPKeyReleased
        String search = tfSearchHP.getText();
        
        if (cbSearchHP.getSelectedIndex() == 0) {
            sd = new ShowDataToTable("Select * From Provider Where ID Like '%" + search + "%'", tblHP, 3);
        }
        
        if (cbSearchHP.getSelectedIndex() == 1) {
            sd = new ShowDataToTable("Select * From Provider Where NAME Like '%" + search + "%'", tblHP, 3);
        }
        
        if (cbSearchHP.getSelectedIndex() == 2) {
            sd = new ShowDataToTable("Select * From Provider Where Address Like '%" + search + "%'", tblHP, 3);
        }
    }//GEN-LAST:event_tfSearchHPKeyReleased

    private void tblHPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHPMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            
            int index = tblHP.getSelectedRow();

            model = (DefaultTableModel)tblHP.getModel();

            String ID = model.getValueAt(index, 0).toString();

            HPInsurance hpIn = new HPInsurance(ID, this, true);

            hpIn.setSize(800, 500);
            hpIn.setVisible(true);  
        }
    }//GEN-LAST:event_tblHPMouseClicked

    private void panel_viewDoctorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_viewDoctorMouseClicked
        int index = tblHP.getSelectedRow();
        
        model = (DefaultTableModel)tblHP.getModel();
        
        String ID = model.getValueAt(index, 0).toString();
        
        HPDoctor hpDoc = new HPDoctor(ID, this, true);
        
        hpDoc.setSize(600, 400);
        hpDoc.setVisible(true);
    }//GEN-LAST:event_panel_viewDoctorMouseClicked

    private void tfSearchInsComKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchInsComKeyReleased
        String search = tfSearchInsCom.getText();
        
        if (cbSearchIns.getSelectedIndex() == 0) {
            sd = new ShowDataToTable("Select * From Insurance Where ID Like '%" + search + "%'", tblInsCom, 3);
        }
        
        if (cbSearchIns.getSelectedIndex() == 1) {
            sd = new ShowDataToTable("Select * From Insurance Where Name Like '%" + search + "%'", tblInsCom, 3);
        }
        
        if (cbSearchIns.getSelectedIndex() == 2) {
            sd = new ShowDataToTable("Select * From Insurance Where Addresss Like '%" + search + "%'", tblInsCom, 3);
        }
    }//GEN-LAST:event_tfSearchInsComKeyReleased

    private void panel_viewPlanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_viewPlanMouseClicked
        try {
            int index = tblInsCom.getSelectedRow();
        
            model = (DefaultTableModel)tblInsCom.getModel();
        
            String ID = model.getValueAt(index, 0).toString();
        
            InsurancePlan insPlan = new InsurancePlan(ID, this, true);

            insPlan.setSize(800, 500);
            insPlan.setVisible(true); 
        } catch (Exception ex) { }
    }//GEN-LAST:event_panel_viewPlanMouseClicked

    private void tblInsComMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInsComMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();

            int index = tblInsCom.getSelectedRow();

            model = (DefaultTableModel)tblInsCom.getModel();

            String ID = model.getValueAt(index, 0).toString();

            InsuranceHP InsHP = new InsuranceHP(ID, this, true);
        
            InsHP.setSize(800, 500);
            InsHP.setVisible(true); 
        }
    }//GEN-LAST:event_tblInsComMouseClicked

    private void panel_healthcarePlanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_healthcarePlanMousePressed
        setColorChooseSidePane(panel_healthcarePlan, panel_dashboard, panel_healthcarePro, panel_insurance_com, panel_about, panel_statistic, panel_update, panel_find, panel_doctor, panel_patient);
        
        setIndexChooseSidePane(false, false, true, false, false, false, false, false, false, false);
        
        showPanelInCard(panel_home, panel_hHealthcarePlan);
        
        SubTable.setTableHeader(tblPlan, new Color(240, 240, 240), Color.BLACK, new Font("Tahoma", Font.PLAIN, 12));
        
        TextFieldSearch.setTextLook(tfSearchPlan);
        
        panel_viewDoctor2.setVisible(false);
    }//GEN-LAST:event_panel_healthcarePlanMousePressed

    private void panel_doctorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_doctorMousePressed
        setColorChooseSidePane(panel_doctor, panel_dashboard, panel_healthcarePlan, panel_insurance_com, panel_about, panel_statistic, panel_update, panel_find, panel_healthcarePro, panel_patient);
        
        setIndexChooseSidePane(false, false, false, false, true, false, false, false, false, false);
        
        showPanelInCard(panel_home, panel_hDoctor);
        
        SubTable.setTableHeader(tblDoctor, new Color(240, 240, 240), Color.BLACK, new Font("Tahoma", Font.PLAIN, 12));
        
        TextFieldSearch.setTextLook(tfSearchDoctor);
        
        if (jXCollapsiblePane1.isCollapsed()) {
            System.out.print("Collapsed");
        }
    }//GEN-LAST:event_panel_doctorMousePressed

    private void panel_findMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_findMousePressed
        setColorChooseSidePane(panel_find, panel_dashboard, panel_healthcarePlan, panel_insurance_com, panel_about, panel_statistic, panel_update, panel_healthcarePro, panel_doctor, panel_patient);
        
        setIndexChooseSidePane(false, false, false, false, false, false, false, true, false, false);
        
        showPanelInCard(panel_home, panel_hFind);
    }//GEN-LAST:event_panel_findMousePressed

    private void tfSearchPlanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchPlanKeyReleased
        String search = tfSearchPlan.getText();
        
        if (cbSearchPlan.getSelectedIndex() == 0) {
            sd = new ShowDataToTable("SELECT * FROM [Plan] WHERE ID LIKE '%" + search + "%'", tblPlan, 3);
        }
        
        if (cbSearchPlan.getSelectedIndex() == 1) {
            sd = new ShowDataToTable("SELECT * FROM [Plan] WHERE Name LIKE '%" + search + "%'", tblPlan, 3);
        }
        
        if (cbSearchPlan.getSelectedIndex() == 2) {
            sd = new ShowDataToTable("SELECT * FROM [Plan] WHERE Description LIKE '%" + search + "%'", tblPlan, 3);
        }
    }//GEN-LAST:event_tfSearchPlanKeyReleased

    private void panel_viewDoctor2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_viewDoctor2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panel_viewDoctor2MouseClicked

    private void tblPlanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPlanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblPlanMouseClicked

    private void tfSearchDoctorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchDoctorKeyReleased
        String search = tfSearchDoctor.getText();
        
        if (cbSearchDoctor.getSelectedIndex() == 0) {
            sd = new ShowDataToTable("SELECT ID, [First name] + ' ' + [Last name] AS [Name], Gender, DOB, Address, Area, Degree FROM Doctor WHERE ID LIKE '%" + search + "%'", tblDoctor, 7);
        }
        
        if (cbSearchDoctor.getSelectedIndex() == 1) {
            sd = new ShowDataToTable("SELECT ID, [First name] + ' ' + [Last name] AS [Name], Gender, DOB, Address, Area, Degree FROM Doctor WHERE [First name] + [Last name] LIKE '%" + search + "%'", tblDoctor, 7);
        }
        
        if (cbSearchDoctor.getSelectedIndex() == 2) {
            sd = new ShowDataToTable("SELECT ID, [First name] + ' ' + [Last name] AS [Name], Gender, DOB, Address, Area, Degree FROM Doctor WHERE DOB LIKE '%" + search + "%'", tblDoctor, 7);
        }
        
        if (cbSearchDoctor.getSelectedIndex() == 3) {
            sd = new ShowDataToTable("SELECT ID, [First name] + ' ' + [Last name] AS [Name], Gender, DOB, Address, Area, Degree FROM Doctor WHERE Address LIKE '%" + search + "%'", tblDoctor, 7);
        }
        
        if (cbSearchDoctor.getSelectedIndex() == 4) {
            sd = new ShowDataToTable("SELECT ID, [First name] + ' ' + [Last name] AS [Name], Gender, DOB, Address, Area, Degree FROM Doctor WHERE Area LIKE '%" + search + "%'", tblDoctor, 7);
        }
        
        if (cbSearchDoctor.getSelectedIndex() == 5) {
            sd = new ShowDataToTable("SELECT ID, [First name] + ' ' + [Last name] AS [Name], Gender, DOB, Address, Area, Degree FROM Doctor WHERE Degree LIKE '%" + search + "%'", tblDoctor, 7);
        }
    }//GEN-LAST:event_tfSearchDoctorKeyReleased

    private void panel_viewProvider2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_viewProvider2MouseClicked
        int index = tblDoctor.getSelectedRow();
        
        model = (DefaultTableModel)tblDoctor.getModel();
        
        String ID = model.getValueAt(index, 0).toString();
        
        DoctorHP docHP = new DoctorHP(ID, this, true);
        
        docHP.setSize(800, 500);
        docHP.setVisible(true);
    }//GEN-LAST:event_panel_viewProvider2MouseClicked

    private void tblDoctorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDoctorMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            
            int index = tblDoctor.getSelectedRow();

            model = (DefaultTableModel)tblDoctor.getModel();

            String ID = model.getValueAt(index, 0).toString();

            DoctorDisease dd = new DoctorDisease(ID, this, true);

            dd.setSize(600, 400);
            dd.setVisible(true);  
        }
    }//GEN-LAST:event_tblDoctorMouseClicked
    
    private void lblDoctorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDoctorMouseClicked
        frmFDoctor.setVisible(true);
    }//GEN-LAST:event_lblDoctorMouseClicked
       
    private void lblPatientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPatientMouseClicked
        frmFPatient.setVisible(true);
    }//GEN-LAST:event_lblPatientMouseClicked
    
    private void lblTreatmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTreatmentMouseClicked
        frmFTreatment.setVisible(true);
    }//GEN-LAST:event_lblTreatmentMouseClicked
        
    private void lblLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoginMouseClicked
        if (frmLogin.isLoggedin()) {
            pmLogin.show(panel_home, lblLogin.getX(), lblLogin.getY());
        }
        else {
            this.setVisible(false);
        
            frmLogin.setVisible(true);
        }
    }//GEN-LAST:event_lblLoginMouseClicked

    private void menuLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLogoutActionPerformed
        setLogout(true);
        
        this.setVisible(false);

        frmLogin.setVisible(true);
    }//GEN-LAST:event_menuLogoutActionPerformed

    private void panel_dAdminHPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_dAdminHPMouseClicked
        panel_healthcareProMousePressed(null);
    }//GEN-LAST:event_panel_dAdminHPMouseClicked

    private void panel_dAdminInsuranceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_dAdminInsuranceMouseClicked
        panel_insurance_comMousePressed(null);
    }//GEN-LAST:event_panel_dAdminInsuranceMouseClicked

    private void panel_dAdminPlanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_dAdminPlanMouseClicked
        panel_healthcarePlanMousePressed(null);
    }//GEN-LAST:event_panel_dAdminPlanMouseClicked

    private void panel_dAdminDoctorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_dAdminDoctorMouseClicked
        panel_doctorMousePressed(null);
    }//GEN-LAST:event_panel_dAdminDoctorMouseClicked

    private void panel_dAdminPatientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_dAdminPatientMouseClicked
        panel_patientMousePressed(null);
    }//GEN-LAST:event_panel_dAdminPatientMouseClicked

    private void panel_dAdminUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_dAdminUserMouseClicked
        showPanelInCard(panel_home, panel_hDashboard);
        showPanelInCard(panel_hDashboard, panel_user);
    }//GEN-LAST:event_panel_dAdminUserMouseClicked

    private void panel_dInsPlanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_dInsPlanMouseClicked
        panel_healthcarePlanMousePressed(null);
    }//GEN-LAST:event_panel_dInsPlanMouseClicked

    private void panel_dInsTotalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_dInsTotalMouseClicked
        panel_insurance_comMousePressed(null);
    }//GEN-LAST:event_panel_dInsTotalMouseClicked

    private void panel_dInsPatientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_dInsPatientMouseClicked
        panel_patientMousePressed(null);
    }//GEN-LAST:event_panel_dInsPatientMouseClicked

    private void panel_dHPTotalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_dHPTotalMouseClicked
        panel_healthcareProMousePressed(null);
    }//GEN-LAST:event_panel_dHPTotalMouseClicked

    private void panel_dHPDoctorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_dHPDoctorMouseClicked
        panel_doctorMousePressed(null);
    }//GEN-LAST:event_panel_dHPDoctorMouseClicked

    private void panel_dHPPatientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_dHPPatientMouseClicked
        panel_patientMousePressed(null);
    }//GEN-LAST:event_panel_dHPPatientMouseClicked

    private void tfSearchPatientKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchPatientKeyReleased
        String search = tfSearchPatient.getText();
        
        if (cbSearchPatient.getSelectedIndex() == 0) {
            sd = new ShowDataToTable("SELECT Ssn, [First Name] + ' ' + [Last Name] AS [Name], Gender, DOB, Address, HealthCondition, Phone, [Employer Name], Contact FROM Patient WHERE Ssn LIKE '%" + search + "%'", tblPatient, 9);
        }
        
        if (cbSearchPatient.getSelectedIndex() == 1) {
            sd = new ShowDataToTable("SELECT Ssn, [First Name] + ' ' + [Last Name] AS [Name], Gender, DOB, Address, HealthCondition, Phone, [Employer Name], Contact FROM Patient WHERE [First Name] + [Last Name] LIKE '%" + search + "%'", tblPatient, 9);
        }
        
        if (cbSearchPatient.getSelectedIndex() == 2) {
            sd = new ShowDataToTable("SELECT Ssn, [First Name] + ' ' + [Last Name] AS [Name], Gender, DOB, Address, HealthCondition, Phone, [Employer Name], Contact FROM Patient WHERE DOB LIKE '%" + search + "%'", tblPatient, 9);
        }
        
        if (cbSearchPatient.getSelectedIndex() == 3) {
            sd = new ShowDataToTable("SELECT Ssn, [First Name] + ' ' + [Last Name] AS [Name], Gender, DOB, Address, HealthCondition, Phone, [Employer Name], Contact FROM Patient WHERE Address LIKE '%" + search + "%'", tblPatient, 9);
        }
        
        if (cbSearchPatient.getSelectedIndex() == 4) {
            sd = new ShowDataToTable("SELECT Ssn, [First Name] + ' ' + [Last Name] AS [Name], Gender, DOB, Address, HealthCondition, Phone, [Employer Name], Contact FROM Patient WHERE HealthCondition LIKE '%" + search + "%'", tblPatient, 9);
        }
        
        if (cbSearchPatient.getSelectedIndex() == 5) {
            sd = new ShowDataToTable("SELECT Ssn, [First Name] + ' ' + [Last Name] AS [Name], Gender, DOB, Address, HealthCondition, Phone, [Employer Name], Contact FROM Patient WHERE Phone LIKE '%" + search + "%'", tblPatient, 9);
        }
        
        if (cbSearchPatient.getSelectedIndex() == 6) {
            sd = new ShowDataToTable("SELECT Ssn, [First Name] + ' ' + [Last Name] AS [Name], Gender, DOB, Address, HealthCondition, Phone, [Employer Name], Contact FROM Patient WHERE [Employer Name] LIKE '%" + search + "%'", tblPatient, 9);
        }
        
        if (cbSearchPatient.getSelectedIndex() == 7) {
            sd = new ShowDataToTable("SELECT Ssn, [First Name] + ' ' + [Last Name] AS [Name], Gender, DOB, Address, HealthCondition, Phone, [Employer Name], Contact FROM Patient WHERE Contact LIKE '%" + search + "%'", tblPatient, 9);
        }
    }//GEN-LAST:event_tfSearchPatientKeyReleased

    private void panel_viewDiseaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_viewDiseaseMouseClicked
        try {
            int index = tblPatient.getSelectedRow();
        
            model = (DefaultTableModel)tblPatient.getModel();

            String ID = model.getValueAt(index, 0).toString();

            PatientDisease pd = new PatientDisease(ID, this, true);

            pd.setSize(600, 400);
            pd.setVisible(true);
        } catch (Exception ex) { }
    }//GEN-LAST:event_panel_viewDiseaseMouseClicked

    private void tblPatientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPatientMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            
            int index = tblPatient.getSelectedRow();

            model = (DefaultTableModel)tblPatient.getModel();

            String ID = model.getValueAt(index, 0).toString();

            PatientPlan patientPlan = new PatientPlan(ID, this, true);

            patientPlan.setSize(800, 500);
            patientPlan.setVisible(true);  
        }
    }//GEN-LAST:event_tblPatientMouseClicked

    private void tfSearchUserKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSearchUserKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tfSearchUserKeyReleased

    private void tblUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblUserMouseClicked

    private void btnToggleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnToggleMouseEntered
        btnToggle.setText("<html><u>>>  Toggle Doctor List / Patient List By Specific Doctor  &#60&#60</u></html>");
    }//GEN-LAST:event_btnToggleMouseEntered

    private void btnToggleMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnToggleMouseExited
        btnToggle.setText(">>  Toggle Doctor List / Patient List By Specific Doctor  <<");
    }//GEN-LAST:event_btnToggleMouseExited

    private void cboDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDocActionPerformed
        model = (DefaultTableModel) tbInfo.getModel();

        SubTable.removeAllRows(tbInfo, model);
        
        String mcName = "";
        
        if(cboDoc.getItemCount()<0) return;
        
        try{
            rsDoc.absolute(cboDoc.getSelectedIndex()+1);
            Statement stmtPt = con.createStatement();
            ResultSet rsPt = stmtPt.executeQuery("select vt.[Patient.Ssn] as [ptID],"+
                "pt.[First Name]+' '+ pt.[Last Name] as [ptName], ds.Name as [dsName], ds.ID as [dsID]\n" +
                "from Visit vt inner join Patient pt on vt.[Patient.Ssn] = pt.Ssn\n" +
                "inner join Disease ds on ds.ID=vt.[Disease.ID]\n" +
                "where vt.[Doctor.ID]='"+rsDoc.getString("ID")+"'");

            Statement stmtMd; ResultSet rsMd;
            Statement stmtMc; ResultSet rsMc;

            while(rsPt.next()){
                stmtMd = con.createStatement();
                rsMd = stmtMd.executeQuery("select Medication.[Medicine#] as [mcID]\n" +
                    "from Medication\n" +
                    "where Medication.[Disease.ID]='"+rsPt.getString("dsID")+"'");
                while(rsMd.next()) {
                    stmtMc = con.createStatement();
                    rsMc = stmtMc.executeQuery("select Medicine.Name as [mcName]\n" +
                        "from Medicine\n" +
                        "where Medicine.[Medicine#]='"+rsMd.getString("mcID")+"'");

                    while(rsMc.next()) {
                        mcName += rsMc.getString("mcName")+",";
                    }
                }
                model.addRow(new Object[]{
                    rsPt.getString("ptID"),
                    rsPt.getString("ptName"),
                    rsPt.getString("dsName"),
                    mcName});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
   
        findProviderName();
    }//GEN-LAST:event_cboDocActionPerformed

    private void panel_hDoctorComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_panel_hDoctorComponentResized
        int width = panel_hDoctorMain.getWidth();
        int height = panel_hDoctorMain.getHeight();
        
        if (jXCollapsiblePane1.isCollapsed()) {
            frm5main.setPreferredSize(panel_hDoctorMain.getSize());
        } else {           
            frm5main.setSize(new Dimension(width, height));
        }
    }//GEN-LAST:event_panel_hDoctorComponentResized

    private void lblUInsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUInsMouseClicked
        uIns.setVisible(true);
    }//GEN-LAST:event_lblUInsMouseClicked

    private void lblUDoctorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUDoctorMouseClicked
        uDoc.setVisible(true);
    }//GEN-LAST:event_lblUDoctorMouseClicked

    private void lblUPatientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUPatientMouseClicked
        uPat.setVisible(true);
    }//GEN-LAST:event_lblUPatientMouseClicked

    private void lblUHPlanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUHPlanMouseClicked
        uPlan.setVisible(true);
    }//GEN-LAST:event_lblUHPlanMouseClicked
           
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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WelcomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new WelcomeForm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnToggle;
    private javax.swing.JComboBox<String> cbSearchDoctor;
    private javax.swing.JComboBox<String> cbSearchHP;
    private javax.swing.JComboBox<String> cbSearchIns;
    private javax.swing.JComboBox<String> cbSearchPatient;
    private javax.swing.JComboBox<String> cbSearchPlan;
    private javax.swing.JComboBox<String> cbSearchUser;
    private javax.swing.JComboBox<String> cboDoc;
    private javax.swing.JPanel frm5main;
    private javax.swing.JPanel indAbout;
    private javax.swing.JPanel indDashboard;
    private javax.swing.JPanel indDoctor;
    private javax.swing.JPanel indFind;
    private javax.swing.JPanel indHP;
    private javax.swing.JPanel indHealthcarePlan;
    private javax.swing.JPanel indInsurance;
    private javax.swing.JPanel indPatient;
    private javax.swing.JPanel indStatistic;
    private javax.swing.JPanel indUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private org.jdesktop.swingx.JXCollapsiblePane jXCollapsiblePane1;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lbl2;
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lbl4;
    private javax.swing.JLabel lbl5;
    private javax.swing.JLabel lbl6;
    private javax.swing.JLabel lbl7;
    private javax.swing.JLabel lbl8;
    private javax.swing.JLabel lbl9;
    private javax.swing.JLabel lblAbout;
    private javax.swing.JLabel lblAdminTotalDoctor;
    private javax.swing.JLabel lblAdminTotalHP;
    private javax.swing.JLabel lblAdminTotalIns;
    private javax.swing.JLabel lblAdminTotalPatient;
    private javax.swing.JLabel lblAdminTotalPlan;
    private javax.swing.JLabel lblAdminTotalUser;
    private javax.swing.JLabel lblDashboard;
    private javax.swing.JLabel lblDetailDoctor;
    private javax.swing.JLabel lblDetailDoctor3;
    private javax.swing.JLabel lblDetailHP;
    private javax.swing.JLabel lblDetailInsurance;
    private javax.swing.JLabel lblDetailInsurance2;
    private javax.swing.JLabel lblDetailInsurance3;
    private javax.swing.JLabel lblDetailPatient;
    private javax.swing.JLabel lblDetailPatient2;
    private javax.swing.JLabel lblDetailPatient3;
    private javax.swing.JLabel lblDetailPlan;
    private javax.swing.JLabel lblDetailPlan2;
    private javax.swing.JLabel lblDetailUser;
    private javax.swing.JLabel lblDoctor;
    private javax.swing.JLabel lblFind;
    private javax.swing.JLabel lblHPTotal;
    private javax.swing.JLabel lblHPTotalDoctor;
    private javax.swing.JLabel lblHPTotalPatient;
    private javax.swing.JLabel lblHealthcarePro;
    private javax.swing.JLabel lblHealthcarePro1;
    private javax.swing.JLabel lblHealthcarePro2;
    private javax.swing.JLabel lblInsTotal;
    private javax.swing.JLabel lblInsTotalPatient;
    private javax.swing.JLabel lblInsTotalPlan;
    private javax.swing.JLabel lblInsuranceCom;
    private javax.swing.JLabel lblInsuranceCom2;
    private javax.swing.JLabel lblLock;
    private javax.swing.JLabel lblLock1;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblPatient;
    private javax.swing.JLabel lblSearchHP1;
    private javax.swing.JLabel lblSearchHP2;
    private javax.swing.JLabel lblSearchHP3;
    private javax.swing.JLabel lblSearchHP4;
    private javax.swing.JLabel lblSearchHP5;
    private javax.swing.JLabel lblStatistic;
    private javax.swing.JLabel lblTreatment;
    private javax.swing.JLabel lblUDoctor;
    private javax.swing.JLabel lblUHP;
    private javax.swing.JLabel lblUHPlan;
    private javax.swing.JLabel lblUIns;
    private javax.swing.JLabel lblUPatient;
    private javax.swing.JLabel lblUUser;
    private javax.swing.JLabel lblUpdate;
    private javax.swing.JLabel lblp;
    private javax.swing.JMenuItem menuLogout;
    private javax.swing.JPanel panel_about;
    private javax.swing.JPanel panel_dAdmin;
    private javax.swing.JPanel panel_dAdminDoctor;
    private javax.swing.JPanel panel_dAdminHP;
    private javax.swing.JPanel panel_dAdminInsurance;
    private javax.swing.JPanel panel_dAdminMain;
    private javax.swing.JPanel panel_dAdminPatient;
    private javax.swing.JPanel panel_dAdminPlan;
    private javax.swing.JPanel panel_dAdminUser;
    private javax.swing.JPanel panel_dHPDoctor;
    private javax.swing.JPanel panel_dHPMain;
    private javax.swing.JPanel panel_dHPPatient;
    private javax.swing.JPanel panel_dHPTotal;
    private javax.swing.JPanel panel_dHealthcare;
    private javax.swing.JPanel panel_dInsMain;
    private javax.swing.JPanel panel_dInsPatient;
    private javax.swing.JPanel panel_dInsPlan;
    private javax.swing.JPanel panel_dInsTotal;
    private javax.swing.JPanel panel_dInsurance;
    private javax.swing.JPanel panel_dNormal;
    private javax.swing.JPanel panel_dashboard;
    private javax.swing.JPanel panel_doctor;
    private javax.swing.JPanel panel_empty;
    private javax.swing.JPanel panel_find;
    private javax.swing.JPanel panel_findOption;
    private javax.swing.JPanel panel_form5;
    private javax.swing.JPanel panel_hAbout;
    private javax.swing.JPanel panel_hDashboard;
    private javax.swing.JPanel panel_hDoctor;
    private javax.swing.JPanel panel_hDoctorMain;
    private javax.swing.JPanel panel_hDoctorMain1;
    private javax.swing.JPanel panel_hFind;
    private javax.swing.JPanel panel_hHPlanMain;
    private javax.swing.JPanel panel_hHealthcarePlan;
    private javax.swing.JPanel panel_hHealthcarePro;
    private javax.swing.JPanel panel_hInsMain;
    private javax.swing.JPanel panel_hInsMain1;
    private javax.swing.JPanel panel_hInsMain4;
    private javax.swing.JPanel panel_hInsurance_com;
    private javax.swing.JPanel panel_hPatient;
    private javax.swing.JPanel panel_hStatistic;
    private javax.swing.JPanel panel_hUpdates;
    private javax.swing.JPanel panel_healthcarePlan;
    private javax.swing.JPanel panel_healthcarePro;
    private javax.swing.JPanel panel_home;
    private javax.swing.JPanel panel_insurance_com;
    private javax.swing.JPanel panel_left;
    private javax.swing.JPanel panel_login;
    private javax.swing.JPanel panel_main;
    private javax.swing.JPanel panel_patient;
    private javax.swing.JPanel panel_statistic;
    private javax.swing.JPanel panel_titlebar;
    private javax.swing.JPanel panel_update;
    private javax.swing.JPanel panel_user;
    private javax.swing.JPanel panel_viewDisease;
    private javax.swing.JPanel panel_viewDoctor;
    private javax.swing.JPanel panel_viewDoctor2;
    private javax.swing.JPanel panel_viewPlan;
    private javax.swing.JPanel panel_viewProvider2;
    private javax.swing.JPopupMenu pmLogin;
    private javax.swing.JScrollPane scpMain;
    private javax.swing.JTable tbInfo;
    private javax.swing.JTable tblDoctor;
    private javax.swing.JTable tblHP;
    private javax.swing.JTable tblInsCom;
    private javax.swing.JTable tblPatient;
    private javax.swing.JTable tblPlan;
    private javax.swing.JTable tblUser;
    private javax.swing.JTextField tfSearchDoctor;
    private javax.swing.JTextField tfSearchHP;
    private javax.swing.JTextField tfSearchInsCom;
    private javax.swing.JTextField tfSearchPatient;
    private javax.swing.JTextField tfSearchPlan;
    private javax.swing.JTextField tfSearchUser;
    private javax.swing.JTextField txtPro;
    // End of variables declaration//GEN-END:variables
}
