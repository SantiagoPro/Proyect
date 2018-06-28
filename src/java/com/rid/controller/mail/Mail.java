/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.controller.mail;

/**
 *
 * @author Santiago
 */

import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail {

    private final static String KEY_SMTP_SERVER = "mail.smtp.host";
    private final static String KEY_FROM = "mail.smtp.user";
    private final static String KEY_PASSWORD = "mail.smtp.password";

    private static Properties props;

    private static void loadConfig() {
        if (props == null) {
            props = new Properties();
            props.put(KEY_SMTP_SERVER, "smtp.gmail.com");
            props.put(KEY_FROM, "sistemar.i.d@gmail.com");
            props.put(KEY_PASSWORD, "SISTEMAR.I.D1365297");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", "587");
        }

    }

    public static void sendMail(String destinatario, String asunto, String cuerpo) {
        loadConfig();
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(props.getProperty(KEY_FROM)));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport transport = session.getTransport("smtp");
            transport.connect(props.getProperty(KEY_SMTP_SERVER),
                    props.getProperty(KEY_FROM),
                    props.getProperty(KEY_PASSWORD));
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
            me.printStackTrace();
        }

    }

    public static void sendMailHTML(String destinatarios, String asunto, String cuerpoHTML) {
        loadConfig();
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(props.getProperty(KEY_FROM)));
            message.addRecipients(Message.RecipientType.TO, destinatarios);
            message.setSubject(asunto);
            Multipart parts = new MimeMultipart();
            BodyPart bodymail = new MimeBodyPart();
            bodymail.setContent(cuerpoHTML, "text/HTML");
            parts.addBodyPart(bodymail);
            message.setContent(parts);
            Transport transport = session.getTransport("smtp");
            transport.connect(props.getProperty(KEY_SMTP_SERVER),
                    props.getProperty(KEY_FROM),
                    props.getProperty(KEY_PASSWORD));
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }

//    public static void sendMailHTM(String destinatarios, String asunto, String cuerpoHTML, List<File> files) throws IOException {
//        loadConfig();
//        Session session = Session.getDefaultInstance(props);
//        MimeMessage message = new MimeMessage(session);
//        try {
//            message.setFrom(new InternetAddress(props.getProperty(KEY_FROM)));
//            message.addRecipients(Message.RecipientType.TO, destinatarios);
//            message.setSubject(asunto);
//            Multipart parts = new MimeMultipart();
//            BodyPart bodymail = new MimeBodyPart();
//            bodymail.setContent(cuerpoHTML, "text/HTML");
//            parts.addBodyPart(bodymail);
//            for (File file : files) {
//                MimeBodyPart attached = new MimeBodyPart();
//                attached.attachFile(file);
//                parts.addBodyPart(attached);
//            }
//            message.setContent(parts);
//            Transport transport = session.getTransport("smtp");
//            transport.connect(props.getProperty(KEY_SMTP_SERVER),
//                    props.getProperty(KEY_FROM),
//                    props.getProperty(KEY_PASSWORD));
//            transport.sendMessage(message, message.getAllRecipients());
//            transport.close();
//        } catch (MessagingException me) {
//            me.printStackTrace();
//        }
//    }
    
}
