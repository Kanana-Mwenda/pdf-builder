import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.DataSource;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Transport;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;


public class SendEmail {
    public static void main(String[] args) {

        // Recipient and sender emails
        String recipient = "kananamwenda20@gmail.com";
        String sender = "lizamwenda95@gmail.com";

        // Gmail SMTP host
        String host = "smtp.gmail.com";

        // Gmail authentication credentials
        final String username = "kananamwenda20@gmail.com"; 
        final String password = "sfoq hwop njvp qsqt";

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
            message.setSubject("Statements PDF"); //subject
            
            // Load HTML email template from file path
            String htmlFilePath = "target/classes/emailtemplate.html"; 
            String htmlContent = Files.readString(Paths.get(htmlFilePath));

            // HTML Body Part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlContent, "text/html; charset=utf-8");

            //pdf body part
            MimeBodyPart attachmentPart = new MimeBodyPart();
            String fileName = "src/out/statements.pdf";
            DataSource source = new FileDataSource(fileName);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(new File(fileName).getName());
            
            //message and pdf
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            // Send the message
            Transport.send(message);
            System.out.println(" Mail successfully sent!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

