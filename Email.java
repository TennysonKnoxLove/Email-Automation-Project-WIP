import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.net.URISyntaxException;



public class Email {
Session newSession = null;
MimeMessage mimeMessage = null;

public static void sendUser(String[] emailRecipients, String emailSubject, String emailContent) throws AddressException, MessagingException, IOException, InterruptedException, URISyntaxException {
Email mail = new Email();
mail.setupServerProperties();
mail.draftEmail(emailRecipients, emailSubject, ChatGPT.chatGptEmailOptimization(emailContent));
mail.sendEmail();

}



    private void sendEmail() throws NoSuchProviderException, MessagingException  {
        String fromUser = "YourEmailHere@something.com";
        String fromUserPassword = "Enter API Key";
        String emailHost = "smtp.gmail.com";
        
        Transport transport = newSession.getTransport(
            "smtp"); 
        transport.connect(emailHost, fromUser, fromUserPassword); 
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
    }

    private MimeMessage draftEmail(String[] emailRec, String emailSub, String emailCont) throws MessagingException, AddressException, IOException  {
       
        
        mimeMessage = new MimeMessage(newSession);
        for(int i = 0; i< emailRec.length; i++) {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRec[i]));
        }

        mimeMessage.setSubject(emailSub);
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(emailCont, "text/html");
        MimeMultipart multiPart = new MimeMultipart();
        multiPart.addBodyPart(bodyPart);
        mimeMessage.setContent(multiPart);
        System.out.println("Email sent.");
        return mimeMessage;

    }

    private void setupServerProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        newSession = Session.getDefaultInstance(properties, null);
    }
}