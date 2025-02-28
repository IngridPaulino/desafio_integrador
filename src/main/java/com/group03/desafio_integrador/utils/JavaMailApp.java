package com.group03.desafio_integrador.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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
   // @Autowired
   // @Value("${string.datasource.email}")
   // private static String email;
    //@Autowired
    //@Value("${string.datasource.passwordemail}")
    private static String password;
    public static void sendMail(String name, String emailCliente, String address) {
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
                        // TODO: 19/11/22 Configurar variaveis de ambiente
                        return new PasswordAuthentication("xxxxxx.com", "xxxxxxx");
                    }
                });

        /** Ativa Debug para sessão */
        session.setDebug(true);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("xxxxxx@gmail.com")); //Remetente

            Address[] toUser = InternetAddress
                    .parse(emailCliente); //Destinatário(s)

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Sua compra chegou, aproveite!");//Assunto
            message.setText("Olá " + name + ", sua compra chegou, aproveite! " +
                           "Fizemos a entrega na " + address + "." +
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