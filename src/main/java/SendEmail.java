import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import javax.mail.Transport;
import java.nio.file.Files;
import java.nio.file.Paths;

import EmailSenderApp.src.DecryptPassword;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;


public class SendEmail {
    public static void main(String[] args) {

        // Recipient and sender emails
        String recipient = "lizamwenda95@gmail.com";
        String sender = "kananamwenda20@gmail.com";

        // Gmail SMTP host
        String host = "smtp.gmail.com";

        // Gmail authentication credentials
        final String username = "kananamwenda20@gmail.com"; 
        final String password = DecryptPassword.getDecryptedPassword();

        // Mail properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true"); //authentication
        properties.put("mail.smtp.starttls.enable", "true");//security protocol
        properties.put("mail.smtp.host", host);//gmail server
        properties.put("mail.smtp.port", "587");//port number

        // Session with authentication
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Mime message object
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(sender)); //senders email
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient)); //recipients email
            message.setSubject("Account Statements PDF"); //subject

            // Create multipart
            Multipart multipart = new MimeMultipart();

            // HTML body part
            MimeBodyPart htmlPart = new MimeBodyPart();
            String htmlTemplate = new String(Files.readAllBytes(Paths.get("target/classes/emailtemplate.html")));
            htmlPart.setContent(htmlTemplate, "text/html");
            multipart.addBodyPart(htmlPart);

            // Attachment body part
            MimeBodyPart attachmentPart = new MimeBodyPart();
            String attachmentPath = "src/out/statements.pdf";
            FileDataSource source = new FileDataSource(attachmentPath);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName("statements.pdf");
            multipart.addBodyPart(attachmentPart);

            // Set multipart as content
            message.setContent(multipart);

            // Send the message
            Transport.send(message);
            System.out.println(" Mail successfully sent!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
