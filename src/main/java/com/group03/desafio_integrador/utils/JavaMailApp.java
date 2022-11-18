package com.group03.desafio_integrador.utils;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

// FONTE: https://www.devmedia.com.br/enviando-email-com-javamail-utilizando-gmail/18034
public class JavaMailApp
{
    public static void sendMail() {
        System.out.println("Começando!!!");

        Properties props = new Properties();
        /** Parâmetros de conexão com servidor Gmail */
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication("xxxxxxx@gmail.com", "xxxxxxxxxxx");
                    }
                });

        /** Ativa Debug para sessão */
        session.setDebug(true);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("xxxxxxx@gmail.com")); //Remetente

            Address[] toUser = InternetAddress
                    .parse("cliente@gmail.com"); //Destinatário(s)

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Sua compra chegou, aproveite!");//Assunto
            message.setText("Olá fulana Sua compra chegou, aproveite!" +
                           "Fizemos a entrega na Rua José Ribeiro Filho 35, Belo Horizonte CEP 31330500." +
                    "Esperamos que você esteja contente com os produtos. Caso contrário, você pode devolvê-los até sábado 5 de novembro.\n" +
                    "\n"
            );

            /**Método para enviar a mensagem criada*/
            Transport.send(message);
            System.out.println("Feito!!!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}