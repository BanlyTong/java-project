import java.awt.Component;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;

public class UpdateInsurance extends javax.swing.JDialog {

    ShowDataToTable sd;
    
    DefaultTableModel model;
    
    MouseListener[] eventC;
    MouseListener[] eventI;
    
    Connection con;
    Statement stmt;
    ResultSet rs;
    
    boolean isUpdate = false;
    
    public UpdateInsurance(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        getEventFromTable();
        
        sd = new ShowDataToTable("SELECT * FROM Insurance", tbInsurance, 3);
        
        tbInsurance.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int i = tbInsurance.getSelectedRow();
                if (i > 0){
                    lbCount.setText(i + 1 + "/" + tbInsurance.getRowCount());
                    showDataToTableCP();
                    
                    txtID.setText(model.getValueAt(i, 0).toString());
                    txtName.setText(model.getValueAt(i, 1).toString());
                    txtAddress.setText(model.getValueAt(i, 2).toString());
                    
                    btnAdd.setEnabled(true);
                }               
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int i = tbInsurance.getSelectedRow();
                if (i >= 0){
                    lbCount.setText(i + 1 + "/" + tbInsurance.getRowCount());
                    showDataToTableCP();
                    
                    txtID.setText(model.getValueAt(i, 0).toString());
                    txtName.setText(model.getValueAt(i, 1).toString());
                    txtAddress.setText(model.getValueAt(i, 2).toString());
                    
                    btnAdd.setEnabled(true);
                }
            }
        });
        
        formLoad();
        
        textFieldInput();
    }
    
    private void executeSQLQuery(String query, String message) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");            
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=HealthCareService;user=sa;password=sathya123;");           
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);            
            
            if (message.equals("Deleted") || message.equals("Removed")) {
                if (JOptionPane.showConfirmDialog(null, 
                        "Are you sure you want to " + message.substring(0, 6).toLowerCase() + " this record?", 
                        "Confirm Action", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (stmt.executeUpdate(query) == 1) {
                        JOptionPane.showMessageDialog(null, "Data " + message + " Successful", "Information", JOptionPane.INFORMATION_MESSAGE);
                        
                        formLoad();
                        btnAdd.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Data cannot " + message, "Information", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (message.equals("Removed")) {
                    btnRemove.setEnabled(false);
                }
            } else {
                if (stmt.executeUpdate(query) == 1) {
                    JOptionPane.showMessageDialog(null, "Data " + message + " Successful", "Information", JOptionPane.INFORMATION_MESSAGE);              
                } else {
                    JOptionPane.showMessageDialog(null, "Data not " + message, "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void textFieldInput() {
        // Contact Person
        textFieldKeyReleased(txtNameCP, btnInsertCP);
        textFieldKeyReleased(txtPhoneCP, btnInsertCP);
        
        // Insurance
        textFieldKeyReleased(txtName, btnSave);
        textFieldKeyReleased(txtAddress, btnSave);
    }
    
    private void textFieldKeyReleased(JTextField text, JButton btn) {
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (text.getText().equals("")) {
                    btn.setEnabled(false);
                } else {
                    btn.setEnabled(true);
                } 
            }
        });
    }
    
    private void getEventFromTable() {
        eventC = tbContact.getMouseListeners();
        eventI = tbInsurance.getMouseListeners();
    }
    
    private void setEventEnabled(Component c, MouseListener[] e, boolean b) {
        if (b) {
            for (MouseListener l : e) {
                c.addMouseListener(l);
            }
        } else {
            MouseListener[] event = c.getMouseListeners();
            for (MouseListener l : event) {
                c.removeMouseListener(l);               
            }
        }
    }
    
    private void setPanelEnabled(JPanel panel, Boolean isEnabled) {
//        panel.setEnabled(isEnabled);

        Component[] components = panel.getComponents();

        for (Component component : components) {
            if (component.getClass().getName().equals("javax.swing.JPanel")) {
                setPanelEnabled((JPanel) component, isEnabled);
            }
            component.setEnabled(isEnabled);
        }
    }
    
    private void formLoad() {
        lbCount.setText("-- / --");
        
        setPanelEnabled(panel_ins, false);
        setPanelEnabled(panel_CP, false);
        setPanelEnabled(panel_CP1, false);
        
        btnNew.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        
        tbInsurance.setEnabled(true);
        tbContact.setEnabled(true);
        
        // Add mouseListener
        setEventEnabled(tbInsurance, eventI, true);
        setEventEnabled(tbContact, eventC, true);
    }
    
    private void showDataToTableCP() {
        int index = tbInsurance.getSelectedRow();

        model = (DefaultTableModel)tbInsurance.getModel();

        String ID = model.getValueAt(index, 0).toString();
        
        sd = new ShowDataToTable("SELECT Name, Phone FROM InsuranceContact WHERE [Insurance.ID] = '" + ID + "'", tbContact, 2);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_main = new javax.swing.JPanel();
        panel_ins = new javax.swing.JPanel();
        txtID = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        panel_CP = new javax.swing.JPanel();
        txtNameCP = new javax.swing.JTextField();
        txtPhoneCP = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnInsertCP = new javax.swing.JButton();
        btnCancelCP = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbInsurance = new javax.swing.JTable();
        lbCount = new javax.swing.JLabel();
        panel_CP1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbContact = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel_main.setPreferredSize(new java.awt.Dimension(1280, 700));

        panel_ins.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Insurance Company", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(255, 51, 51))); // NOI18N

        jLabel4.setText("Address:");

        jLabel3.setText("Name:");

        jLabel2.setText("ID:");

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Save_13px.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.setFocusPainted(false);
        btnSave.setPreferredSize(new java.awt.Dimension(73, 25));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Cancel_13px_1.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setFocusPainted(false);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_insLayout = new javax.swing.GroupLayout(panel_ins);
        panel_ins.setLayout(panel_insLayout);
        panel_insLayout.setHorizontalGroup(
            panel_insLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_insLayout.createSequentialGroup()
                        .addGroup(panel_insLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_insLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_insLayout.createSequentialGroup()
                                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 129, Short.MAX_VALUE))
                            .addComponent(txtAddress, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtName, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(panel_insLayout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_insLayout.setVerticalGroup(
            panel_insLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_insLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_insLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_insLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_insLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_insLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_insLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCancel, btnSave});

        panel_CP.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Contact Person", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(255, 51, 51))); // NOI18N

        jLabel5.setText("Name:");

        jLabel6.setText("Phone:");

        btnInsertCP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Arrow_13px_1.png"))); // NOI18N
        btnInsertCP.setText("Insert");
        btnInsertCP.setFocusPainted(false);
        btnInsertCP.setPreferredSize(new java.awt.Dimension(78, 25));
        btnInsertCP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertCPActionPerformed(evt);
            }
        });

        btnCancelCP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Cancel_13px_1.png"))); // NOI18N
        btnCancelCP.setText("Cancel");
        btnCancelCP.setFocusPainted(false);
        btnCancelCP.setPreferredSize(new java.awt.Dimension(81, 25));
        btnCancelCP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelCPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_CPLayout = new javax.swing.GroupLayout(panel_CP);
        panel_CP.setLayout(panel_CPLayout);
        panel_CPLayout.setHorizontalGroup(
            panel_CPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_CPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_CPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_CPLayout.createSequentialGroup()
                        .addComponent(btnInsertCP, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelCP, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_CPLayout.createSequentialGroup()
                        .addGroup(panel_CPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(panel_CPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNameCP, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                            .addComponent(txtPhoneCP))))
                .addContainerGap())
        );
        panel_CPLayout.setVerticalGroup(
            panel_CPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_CPLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_CPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNameCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_CPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhoneCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_CPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInsertCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_CPLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCancelCP, btnInsertCP});

        tbInsurance.setBackground(new java.awt.Color(57, 109, 160));
        tbInsurance.setForeground(new java.awt.Color(255, 255, 51));
        tbInsurance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Address"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbInsurance.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbInsurance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbInsuranceMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbInsurance);

        lbCount.setText("-- / --");

        panel_CP1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Contact Person", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(255, 51, 51))); // NOI18N

        tbContact.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Phone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbContact.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbContact.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbContactMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbContact);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Plus_Math_13px.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.setFocusPainted(false);
        btnAdd.setPreferredSize(new java.awt.Dimension(68, 25));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Subtract_13px.png"))); // NOI18N
        btnRemove.setText("Remove");
        btnRemove.setFocusPainted(false);
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_CP1Layout = new javax.swing.GroupLayout(panel_CP1);
        panel_CP1.setLayout(panel_CP1Layout);
        panel_CP1Layout.setHorizontalGroup(
            panel_CP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_CP1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_CP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(panel_CP1Layout.createSequentialGroup()
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panel_CP1Layout.setVerticalGroup(
            panel_CP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_CP1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_CP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemove))
                .addContainerGap())
        );

        panel_CP1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAdd, btnRemove});

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Add_New_13px.png"))); // NOI18N
        btnNew.setText("New");
        btnNew.setFocusPainted(false);
        btnNew.setPreferredSize(new java.awt.Dimension(70, 25));
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Refresh_13px.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setFocusPainted(false);
        btnUpdate.setPreferredSize(new java.awt.Dimension(84, 25));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_Delete_13px.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setFocusPainted(false);
        btnDelete.setPreferredSize(new java.awt.Dimension(80, 25));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_mainLayout = new javax.swing.GroupLayout(panel_main);
        panel_main.setLayout(panel_mainLayout);
        panel_mainLayout.setHorizontalGroup(
            panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_mainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_mainLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbCount))
                    .addGroup(panel_mainLayout.createSequentialGroup()
                        .addGroup(panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(panel_ins, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panel_CP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panel_mainLayout.createSequentialGroup()
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel_CP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 939, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panel_mainLayout.setVerticalGroup(
            panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_mainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_mainLayout.createSequentialGroup()
                        .addComponent(panel_ins, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(panel_CP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 54, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbCount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel_CP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertCPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertCPActionPerformed
        executeSQLQuery("INSERT INTO InsuranceContact([Insurance.ID], Name, Phone) "
                        + "VALUES('" + txtID.getText() + "', '" + txtNameCP.getText() + "', '" 
                        + txtPhoneCP.getText() + "')", "Inserted");
        
        txtNameCP.setText(null);
        txtPhoneCP.setText(null);
        
        showDataToTableCP();
        
        formLoad();
        
        btnAdd.setEnabled(true);    
    }//GEN-LAST:event_btnInsertCPActionPerformed

    private void btnCancelCPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelCPActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Are you sure to cancel?", "Confirm Action", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            txtNameCP.setText(null);
            txtPhoneCP.setText(null);
            
            formLoad();
            btnAdd.setEnabled(true);
            btnUpdate.setEnabled(true);
            btnDelete.setEnabled(true);
        }
    }//GEN-LAST:event_btnCancelCPActionPerformed

    private void tbInsuranceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbInsuranceMouseClicked
        int i = tbInsurance.getSelectedRow();
        model = (DefaultTableModel) tbInsurance.getModel();
        
        if (i >= 0){
            lbCount.setText(i + 1 + "/" + tbInsurance.getRowCount());
        }
        
        txtID.setText(model.getValueAt(i, 0).toString());
        txtName.setText(model.getValueAt(i, 1).toString());
        txtAddress.setText(model.getValueAt(i, 2).toString());
        
        showDataToTableCP();
   
        btnUpdate.setEnabled(true);
        btnDelete.setEnabled(true);
        
        btnAdd.setEnabled(true);
        btnRemove.setEnabled(false);
    }//GEN-LAST:event_tbInsuranceMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        setPanelEnabled(panel_CP, true);
        setPanelEnabled(panel_CP1, false);
        
        btnAdd.setEnabled(false);
        btnInsertCP.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnNew.setEnabled(false);
        btnDelete.setEnabled(false);
        
        tbInsurance.setEnabled(false);
        tbContact.setEnabled(false);
        
        // Remove event MouseClicked from tbContact
        setEventEnabled(tbContact, eventC, false);
        setEventEnabled(tbInsurance, eventI, false);  
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        int i = tbContact.getSelectedRow();
        
        model = (DefaultTableModel) tbContact.getModel();
        
        String name = model.getValueAt(i, 0).toString();
        
        executeSQLQuery("DELETE FROM InsuranceContact WHERE Name = '" + name + "'", "Removed");
        
        showDataToTableCP();
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        setPanelEnabled(panel_ins, true);
        
        btnNew.setEnabled(false);
        btnAdd.setEnabled(false);
        btnRemove.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        
        tbInsurance.setEnabled(false);
        tbContact.setEnabled(false);
        
        setEventEnabled(tbInsurance, eventI, false);
        setEventEnabled(tbContact, eventC, false);
        
        txtID.setText(null);
        txtName.setText(null);
        txtAddress.setText(null);
        
        btnSave.setEnabled(false);
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        isUpdate = true;
        
        setPanelEnabled(panel_ins, true);
        
        txtID.setEnabled(false);
        
        btnUpdate.setEnabled(false);
        btnNew.setEnabled(false);
        btnDelete.setEnabled(false);
        btnAdd.setEnabled(false);
        btnSave.setEnabled(false);
        
        btnCancel.setEnabled(true);
       
        tbInsurance.setEnabled(false);
        tbContact.setEnabled(false);
        
        // Remove event MouseClicked from tbContact
        setEventEnabled(tbContact, eventC, false);
        setEventEnabled(tbInsurance, eventI, false);  
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int i = tbInsurance.getSelectedRow();
        
        model = (DefaultTableModel) tbInsurance.getModel();
        
        String ID = model.getValueAt(i, 0).toString();
        
        executeSQLQuery("DELETE FROM Insurance WHERE ID = '" + ID + "'", "Deleted");
        
        sd = new ShowDataToTable("SELECT * FROM Insurance", tbInsurance, 3);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        int i = tbInsurance.getSelectedRow();
        model = (DefaultTableModel) tbInsurance.getModel();
 
        if (JOptionPane.showConfirmDialog(null, "Are you sure to cancel?", "Confirm Action", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){   
            txtID.setText(model.getValueAt(i, 0).toString());
            txtName.setText(model.getValueAt(i, 1).toString());
            txtAddress.setText(model.getValueAt(i, 2).toString());
            
            formLoad();
            btnAdd.setEnabled(true);
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (isUpdate) {
            executeSQLQuery("UPDATE Insurance SET Name = '" + txtName.getText() + ""
                    + "', Addresss = '" + txtAddress.getText() + "' WHERE ID = '" + txtID.getText() + "'", "Updated");
        } else {
            executeSQLQuery("INSERT INTO Insurance(ID, Name, Addresss) VALUES('" + txtID.getText() + "', '" + txtName.getText() + "', '" + txtAddress.getText() + "')", "Inserted");    
        }
        formLoad();
            
        sd = new ShowDataToTable("SELECT * FROM Insurance", tbInsurance, 3);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tbContactMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbContactMouseClicked
        btnRemove.setEnabled(true);
        btnAdd.setEnabled(true);
    }//GEN-LAST:event_tbContactMouseClicked

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
            java.util.logging.Logger.getLogger(UpdateInsurance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            UpdateInsurance dialog = new UpdateInsurance(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCancelCP;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInsertCP;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbCount;
    private javax.swing.JPanel panel_CP;
    private javax.swing.JPanel panel_CP1;
    private javax.swing.JPanel panel_ins;
    private javax.swing.JPanel panel_main;
    private javax.swing.JTable tbContact;
    private javax.swing.JTable tbInsurance;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNameCP;
    private javax.swing.JTextField txtPhoneCP;
    // End of variables declaration//GEN-END:variables
}
