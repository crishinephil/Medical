import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class Notification extends javax.swing.JFrame {

    public Notification() {
        initComponents();
        fetchAndDisplayIssues();
    }

    private void fetchAndDisplayIssues() {
        try {
            String repoOwner = "crishinephil"; 
            String repoName = "Medical";
            String apiKey = "put here the link of the api key"; // GitHub API key
            JSONArray issues = GitHubIssueFetcher.fetchIssues(repoOwner, repoName, apiKey);

            DefaultTableModel model = (DefaultTableModel) showPatients.getModel();
            // Keep the first row and clear the rest
            while (model.getRowCount() > 1) {
                model.removeRow(1);
            }

            for (int i = 0; i < issues.length(); i++) {
                JSONObject issue = issues.getJSONObject(i);
                String title = issue.getString("title");
                String userLogin = issue.getJSONObject("user").getString("login");
                String htmlUrl = issue.getString("html_url");
                String state = issue.getString("state");
                String body = issue.optString("body", "No content");

                model.addRow(new Object[]{body, title, userLogin, htmlUrl, state});
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching issues: " + e.getMessage());
        }
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        approved = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        showPatients = new javax.swing.JTable();
        search = new javax.swing.JButton();
        searchjTextField = new javax.swing.JTextField();
        back = new javax.swing.JButton();
        contentTextArea = new javax.swing.JTextArea();
        javax.swing.JScrollPane contentScrollPane = new javax.swing.JScrollPane(contentTextArea);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36));
        jLabel1.setText("TO BE APPROVED");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(76, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(70, 70, 70))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        approved.setFont(new java.awt.Font("Segoe UI Historic", 1, 12));
        approved.setText("Schedule Appointment");
        approved.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                approvedActionPerformed(evt);
            }
        });

        showPatients.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Content", "Title", "User", "URL", "Status"}
        ) {
            boolean[] canEdit = new boolean[]{false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        // Set column widths
        showPatients.getColumnModel().getColumn(0).setPreferredWidth(750); // Content
        showPatients.getColumnModel().getColumn(1).setPreferredWidth(10); // Title
        showPatients.getColumnModel().getColumn(2).setPreferredWidth(10); // User
        showPatients.getColumnModel().getColumn(3).setPreferredWidth(10); // URL
        showPatients.getColumnModel().getColumn(4).setPreferredWidth(10);  // Status
        
        jScrollPane2.setViewportView(showPatients);

        search.setFont(new java.awt.Font("Segoe UI Historic", 1, 12));
        search.setText("Search");

        back.setBackground(new java.awt.Color(204, 255, 255));
        back.setFont(new java.awt.Font("Segoe UI Historic", 1, 12));
        back.setText("BACK");
        back.addActionListener(this::backActionPerformed);

        showPatients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showPatientsMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(286, 286, 286)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 179, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(28, 28, 28)
                            .addComponent(back)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(searchjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(search)
                            .addGap(51, 51, 51)
                            .addComponent(approved)
                            .addGap(56, 56, 56))))
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2)
                        .addComponent(contentScrollPane))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(approved)
                        .addComponent(search)
                        .addComponent(searchjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(back))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(contentScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );

        // Configure text area
        contentTextArea.setEditable(false);
        contentTextArea.setLineWrap(true);
        contentTextArea.setWrapStyleWord(true);
        contentTextArea.setFont(new java.awt.Font("Segoe UI", 0, 14));

        pack();
    }

    private void backActionPerformed(ActionEvent evt) {
        // Handle back button action
        new AdminDashboard().setVisible(true);
        this.dispose();
    }

    private void showPatientsMouseClicked(java.awt.event.MouseEvent evt) {
        int row = showPatients.getSelectedRow();
        if (row != -1) {
            String content = (String) showPatients.getValueAt(row, 0); // Get content from the 1st column
            contentTextArea.setText(content);
        }
    }

    private void approvedActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = showPatients.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row first!", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get content from the selected row
        String content = (String) showPatients.getValueAt(selectedRow, 0);
        String user = (String) showPatients.getValueAt(selectedRow, 2);

        // Create and show PatientForm
        PatientForm patientForm = new PatientForm();
        
        // Set the message content in text area
        patientForm.setMessageContent(content);
        
        // Parse content to extract patient information
        String[] lines = content.split("\n");
        for (String line : lines) {
            String[] parts = line.split(":", 2);
            if (parts.length == 2) {
                String key = parts[0].trim();
                String value = parts[1].trim();
                
                switch (key.toLowerCase()) {
                    case "name":
                        patientForm.setPatientName(value);
                        break;
                    case "age":
                        patientForm.setAge(value);
                        break;
                    case "address":
                        patientForm.setAddress(value);
                        break;
                    case "email":
                        patientForm.setEmail(value);
                        break;
                    case "condition":
                        patientForm.setCondition(value);
                        break;
                    case "purpose":
                        patientForm.setPurpose(value);
                        break;
                }
            }
        }

        // Set the doctor name
        if (user != null && !user.isEmpty()) {
            patientForm.setDoctorName(user);
        }

        patientForm.setVisible(true);
        this.dispose();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Notification.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new Notification().setVisible(true));
    }

    private javax.swing.JButton approved;
    private javax.swing.JButton back;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton search;
    private javax.swing.JTextField searchjTextField;
    private javax.swing.JTable showPatients;
    private javax.swing.JTextArea contentTextArea;
}

class GitHubIssueFetcher {
    public static JSONArray fetchIssues(String repoOwner, String repoName, String apiKey) throws Exception {
        String url = "https://api.github.com/repos/" + repoOwner + "/" + repoName + "/issues";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
        if (apiKey != null && !apiKey.isEmpty()) {
            connection.setRequestProperty("Authorization", "token " + apiKey);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return new JSONArray(response.toString());
        }
    }
}
