package edu.icet.pos.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailUtil {
    Properties prop;
    Session session;
    private static final String USERNAME = "usha.ranasinghe@gmail.com";
    private static final String PASSWORD = "dtog qvxj ugvp puvq";

    public EmailUtil(){
        prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        session = Session.getInstance(prop,
                new jakarta.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });
    }

    public void composeEmail(String msg, String receiverEmail, String emailSubject) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USERNAME));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(receiverEmail)
        );
        message.setSubject(emailSubject);
        message.setText("OTP "+ msg);

        Transport.send(message);
    }
}
