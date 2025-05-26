import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static java.util.Calendar.MONTH;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



public class Records extends javax.swing.JFrame {

    public Records() {
        initComponents();
        loadRecords();
        getConnection();
    }

      private void loadRecords() {
        try (Connection connection = getConnection()) {
            String sql = "SELECT p.fullname AS patient_fullname, p.address AS patient_address, p.email, " +
                         "d.fullname AS doctor_fullname, d.address AS doctor_address, d.contact, p.examination_date, " +
                         "p.patient_condition, p.purpose " +
                         "FROM patients p " +
                         "JOIN doctors d ON p.doctor_id = d.id";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) showPatients.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                String patientFullname = resultSet.getString("patient_fullname");
                String patientAddress = resultSet.getString("patient_address");
                String email = resultSet.getString("email");
                String doctorFullname = resultSet.getString("doctor_fullname");
                String doctorAddress = resultSet.getString("doctor_address");
                String contact = resultSet.getString("contact");
                String examinationDate = resultSet.getString("examination_date");
                String patientCondition = resultSet.getString("patient_condition");
                String purpose = resultSet.getString("purpose");

                model.addRow(new Object[]{patientFullname, patientAddress, email, doctorFullname, doctorAddress, contact, examinationDate, patientCondition, purpose});
            }
        } catch (SQLException e) {
            showErrorMessage("Error loading records: " + e.getMessage());
        }
    }


    private void searchRecordsByName(String patientName) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT fullname, age, address, email, patient_condition, doctor_id, status, examination_date, certificate_date, slot_number FROM patients WHERE fullname LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + patientName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) showPatients.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                int age = resultSet.getInt("age");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                String patientCondition = resultSet.getString("patient_condition");
                int doctorId = resultSet.getInt("doctor_id");
                String status = resultSet.getString("status");
                String examinationDate = resultSet.getString("examination_date");
                String certificateDate = resultSet.getString("certificate_date");
                int slotNumber = resultSet.getInt("slot_number");

                model.addRow(new Object[]{patientName, age, address, email, patientCondition, doctorId, status, examinationDate, certificateDate, slotNumber});
            }
        } catch (SQLException e) {
            showErrorMessage("Error searching records: " + e.getMessage());
        }
    }


    private void searchRecordsByYear(String year) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT fullname, age, address, email, patient_condition, doctor_id, status, examination_date, certificate_date, slot_number FROM patients WHERE YEAR(examination_date) = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, year);
            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) showPatients.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                String fullname = resultSet.getString("fullname");
                int age = resultSet.getInt("age");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                String patientCondition = resultSet.getString("patient_condition");
                int doctorId = resultSet.getInt("doctor_id");
                String status = resultSet.getString("status");
                String examinationDate = resultSet.getString("examination_date");
                String certificateDate = resultSet.getString("certificate_date");
                int slotNumber = resultSet.getInt("slot_number");

                model.addRow(new Object[]{fullname, age, address, email, patientCondition, doctorId, status, examinationDate, certificateDate, slotNumber});
            }
        } catch (SQLException e) {
            showErrorMessage("Error searching records: " + e.getMessage());
        }
    }

    private void searchRecordsByMonth(String month) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT fullname, age, address, email, patient_condition, doctor_id, status, examination_date, certificate_date, slot_number FROM patients WHERE MONTHNAME(examination_date) = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, month);
            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) showPatients.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                String fullname = resultSet.getString("fullname");
                int age = resultSet.getInt("age");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                String patientCondition = resultSet.getString("patient_condition");
                int doctorId = resultSet.getInt("doctor_id");
                String status = resultSet.getString("status");
                String examinationDate = resultSet.getString("examination_date");
                String certificateDate = resultSet.getString("certificate_date");
                int slotNumber = resultSet.getInt("slot_number");

                model.addRow(new Object[]{fullname, age, address, email, patientCondition, doctorId, status, examinationDate, certificateDate, slotNumber});
            }
        } catch (SQLException e) {
            showErrorMessage("Error searching records: " + e.getMessage());
        }
    }

    private void searchRecordsByDate(java.util.Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(date);

        try (Connection connection = getConnection()) {
            String sql = "SELECT fullname, age, address, email, patient_condition, doctor_id, status, examination_date, certificate_date, slot_number FROM patients WHERE examination_date = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, formattedDate);
            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) showPatients.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                String fullname = resultSet.getString("fullname");
                int age = resultSet.getInt("age");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                String patientCondition = resultSet.getString("patient_condition");
                int doctorId = resultSet.getInt("doctor_id");
                String status = resultSet.getString("status");
                String certificateDate = resultSet.getString("certificate_date");
                int slotNumber = resultSet.getInt("slot_number");

                model.addRow(new Object[]{fullname, age, address, email, patientCondition, doctorId, status, formattedDate, certificateDate, slotNumber});
            }
        } catch (SQLException e) {
            showErrorMessage("Error searching records: " + e.getMessage());
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void printDocument(javax.swing.JTable table) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                Font font = new Font("Serif", Font.PLAIN, 12);
                g2d.setFont(font);

                int y = 20;
                for (int row = 0; row < table.getRowCount(); row++) {
                    for (int col = 0; col < table.getColumnCount(); col++) {
                        String value = table.getValueAt(row, col).toString();
                        g2d.drawString(value, 20 + (col * 150), y);
                    }
                    y += g2d.getFontMetrics().getHeight();
                }

                return PAGE_EXISTS;
            }
        });

        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                showErrorMessage("Error printing: " + ex.getMessage());
            }
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            String url = "jdbc:mysql://localhost:3306/patientdb";
            String user = "root"; 
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            showErrorMessage("Database connection error: " + e.getMessage());
        }
        return connection;
    }
   
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        searchYEARS = new javax.swing.JTextField();
        btnSearchYEAR = new javax.swing.JButton();
        JDateChooser = new com.toedter.calendar.JDateChooser();
        btnsearc = new javax.swing.JButton();
        searchField1 = new javax.swing.JTextField();
        btnSearchdate1 = new javax.swing.JButton();
        btnSearchMONTH = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        REFRESHPAGE = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        month = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        showPatients = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 255, 204), new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Serif", 3, 36)); // NOI18N
        jLabel1.setText("Records");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(230, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 6, 710, 50));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/SanpedroLogo.png"))); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/AlbueraLogo.png"))); // NOI18N
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 0, -1, -1));

        searchYEARS.setBackground(new java.awt.Color(204, 255, 255));
        searchYEARS.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        searchYEARS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchYEARSActionPerformed(evt);
            }
        });
        getContentPane().add(searchYEARS, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 130, 150, 30));

        btnSearchYEAR.setBackground(new java.awt.Color(204, 255, 255));
        btnSearchYEAR.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearchYEAR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        btnSearchYEAR.setText("SEARCH YEAR");
        btnSearchYEAR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchYEAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchYEARActionPerformed(evt);
            }
        });
        getContentPane().add(btnSearchYEAR, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 170, 150, -1));
        getContentPane().add(JDateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 130, 150, 30));

        btnsearc.setBackground(new java.awt.Color(204, 255, 255));
        btnsearc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnsearc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        btnsearc.setText("SEARCH NAME");
        btnsearc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnsearc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsearcActionPerformed(evt);
            }
        });
        getContentPane().add(btnsearc, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 140, -1));

        searchField1.setBackground(new java.awt.Color(204, 255, 255));
        searchField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        searchField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchField1ActionPerformed(evt);
            }
        });
        getContentPane().add(searchField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 150, 30));

        btnSearchdate1.setBackground(new java.awt.Color(204, 255, 255));
        btnSearchdate1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearchdate1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        btnSearchdate1.setText("SEARCH DATE");
        btnSearchdate1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchdate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchdate1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnSearchdate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 170, 150, -1));

        btnSearchMONTH.setBackground(new java.awt.Color(204, 255, 255));
        btnSearchMONTH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearchMONTH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        btnSearchMONTH.setText("SEARCH MONTH");
        btnSearchMONTH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchMONTH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchMONTHActionPerformed(evt);
            }
        });
        getContentPane().add(btnSearchMONTH, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 170, 160, -1));

        btnPrint.setBackground(new java.awt.Color(204, 255, 255));
        btnPrint.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print.png"))); // NOI18N
        btnPrint.setText("PRINT  RECORDS");
        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        getContentPane().add(btnPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 220, -1, -1));

        REFRESHPAGE.setBackground(new java.awt.Color(204, 255, 255));
        REFRESHPAGE.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        REFRESHPAGE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update.png"))); // NOI18N
        REFRESHPAGE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        REFRESHPAGE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                REFRESHPAGEActionPerformed(evt);
            }
        });
        getContentPane().add(REFRESHPAGE, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 130, -1, -1));

        jButton1.setBackground(new java.awt.Color(204, 255, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/back.png"))); // NOI18N
        jButton1.setText("BACK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, -1, -1));

        month.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER", " ", " " }));
        month.setSelectedItem(MONTH);
        getContentPane().add(month, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 130, 160, 30));

        showPatients.setBackground(new java.awt.Color(204, 204, 204));
        showPatients.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        showPatients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Patient Fullname", "Patient Address", "Patient Contact Number", "Doctor Fullname", "Doctor Address", "Doctor Contact Number", "Date of Examination", "Patient Condition", "Purpose of Certificate"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        showPatients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showPatientsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(showPatients);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 1010, 380));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbstatusActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cmbstatusActionPerformed

    private void searchYEARSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchYEARSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchYEARSActionPerformed

    private void btnSearchYEARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchYEARActionPerformed
         String year = searchYEARS.getText();
        searchRecordsByYear(year);

    }//GEN-LAST:event_btnSearchYEARActionPerformed

    private void btnsearcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsearcActionPerformed
        // TODO add your handling code here:
        String patientName = searchField1.getText();
        searchRecordsByName(patientName);
    }//GEN-LAST:event_btnsearcActionPerformed

    private void searchField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchField1ActionPerformed

    private void btnSearchdate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchdate1ActionPerformed
        // TODO add your handling code here:
        java.util.Date date = JDateChooser.getDate();
        if (date != null) {
            searchRecordsByDate(date);
        }
    }//GEN-LAST:event_btnSearchdate1ActionPerformed

    private void btnSearchMONTHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchMONTHActionPerformed
        // TODO add your handling code here:
         String month = (String) this.month.getSelectedItem();
        searchRecordsByMonth(month);
    }//GEN-LAST:event_btnSearchMONTHActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed

         try {
        printDocument(showPatients); 
        JOptionPane.showMessageDialog(this, "Printing initiated successfully.", "Print Status", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception e) {
        showErrorMessage("Error initiating print: " + e.getMessage());
    }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void REFRESHPAGEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_REFRESHPAGEActionPerformed
     loadRecords();

      
    }//GEN-LAST:event_REFRESHPAGEActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new AdminDashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void showPatientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showPatientsMouseClicked
       
    }//GEN-LAST:event_showPatientsMouseClicked


    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Records.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Records.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Records.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Records.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Records().setVisible(true);
            }
        });
    }


    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser JDateChooser;
    private javax.swing.JButton REFRESHPAGE;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSearchMONTH;
    private javax.swing.JButton btnSearchYEAR;
    private javax.swing.JButton btnSearchdate1;
    private javax.swing.JButton btnsearc;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> month;
    private javax.swing.JTextField searchField1;
    private javax.swing.JTextField searchYEARS;
    private javax.swing.JTable showPatients;
    // End of variables declaration//GEN-END:variables
}
   
