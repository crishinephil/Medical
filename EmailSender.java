import javax.mail.*;
import javax.mail.internet.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EmailSender {

    private static final String CONFIG_FILE = "C:\\Users\\user\\Documents\\NetBeansProjects\\Mejia\\src\\email.properties";

    private Properties loadEmailProperties() throws IOException {
        Properties properties = new Properties();
        FileInputStream input = new FileInputStream(CONFIG_FILE);
        properties.load(input);
        input.close();
        return properties;
    }

    public void sendEmail(String toEmail, String subject, String body) {
        try {
            Properties properties = loadEmailProperties();

            String user = properties.getProperty("mail.smtp.user");
            String password = properties.getProperty("mail.smtp.password");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EmailSender emailSender = new EmailSender();
        emailSender.sendEmail("recipient@example.com", "Test Email", "This is a test email.");
    }
}
