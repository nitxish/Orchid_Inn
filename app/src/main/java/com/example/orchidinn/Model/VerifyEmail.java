package com.example.orchidinn.Model;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class VerifyEmail {
    private String senderEmail;
    private String receiverEmail;
    private String passwordSenderEmail;
    private String receiverName;

    private int emailOtpNumber;

    public int getEmailOtpNumber() {
        return emailOtpNumber;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public void setPasswordSenderEmail(String passwordSenderEmail) {
        this.passwordSenderEmail = passwordSenderEmail;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void sendOtpToEmail(String s, String subject, String content){

        try {
            Random random = new Random();
            emailOtpNumber = random.nextInt(999999);
            Properties properties = System.getProperties();

            String stringHost = "smtp.gmail.com";
            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, passwordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));


            if(s.equals("otp")) {
                mimeMessage.setSubject("OTP for booking your room");
                mimeMessage.setText("Dear " + receiverName + ",\n\n" + emailOtpNumber + " is your OTP(One Time Password) for booking your room at Orchid Inn.\n\nOTP is valid for 5 minutes\n\nThank you\nTeam Orchid Inn");
            }else if(s.equals("book")){
                mimeMessage.setSubject(subject);
                mimeMessage.setText(content);
            }else if(s.equals("cancel")){
                mimeMessage.setSubject(subject);
                mimeMessage.setText(content);
            }
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);

                    } catch (MessagingException e) {
                        e.printStackTrace();

                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
