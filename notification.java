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
            String repoOwner = "owner"; // Replace with the repository owner
            String repoName = "repo"; // Replace with the repository name
            String apiKey = "your_api_key"; // Replace with your GitHub API key
            JSONArray issues = GitHubIssueFetcher.fetchIssues(repoOwner, repoName, apiKey);

            DefaultTableModel model = (DefaultTableModel) showPatients.getModel();
            model.setRowCount(0); // Clear existing rows

            for (int i = 0; i < issues.length(); i++) {
                JSONObject issue = issues.getJSONObject(i);
                String title = issue.getString("title");
                String userLogin = issue.getJSONObject("user").getString("login");
                String htmlUrl = issue.getString("html_url");
                String state = issue.getString("state");

                model.addRow(new Object[]{title, userLogin, htmlUrl, state});
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
        decline = new javax.swing.JButton();
        search = new javax.swing.JButton();
        searchjTextField = new javax.swing.JTextField();
        back = new javax.swing.JButton();

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
        approved.setText("Approved");

        showPatients.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Title", "User", "URL", "Status"}
        ) {
            boolean[] canEdit = new boolean[]{false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane2.setViewportView(showPatients);

        decline.setFont(new java.awt.Font("Segoe UI Historic", 1, 12));
        decline.setText("Decline");

        search.setFont(new java.awt.Font("Segoe UI Historic", 1, 12));
        search.setText("Search");

        back.setBackground(new java.awt.Color(204, 255, 255));
        back.setFont(new java.awt.Font("Segoe UI Historic", 1, 12));
        back.setText("BACK");
        back.addActionListener(this::backActionPerformed);

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
                            .addGap(18, 18, 18)))
                    .addComponent(decline)
                    .addGap(56, 56, 56))
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2)
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
                        .addComponent(decline)
                        .addComponent(search)
                        .addComponent(searchjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(back))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addContainerGap())
        );

        pack();
    }

    private void backActionPerformed(ActionEvent evt) {
        // Handle back button action
        new AdminDashboard().setVisible(true);
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
    private javax.swing.JButton decline;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton search;
    private javax.swing.JTextField searchjTextField;
    private javax.swing.JTable showPatients;
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
