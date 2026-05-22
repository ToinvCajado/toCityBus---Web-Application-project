/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reports;

/**
 *
 * @author Micro
 */

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.*;

public class Email {
    public static void main(String[] args) {

        final String email = "gust92337@gmail.com";
        final String senha = "#Essej01";
        String destino = request.getParameter("email");
        
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(destino)
                
        );

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(
            props,
            new Authenticator() {
                protected PasswordAuthentication
                getPasswordAuthentication() {

                    return new PasswordAuthentication(
                        email,
                        senha
                    );
                }
            }
        );

        try {

            Message message =
                new MimeMessage(session);

            message.setFrom(
                new InternetAddress(email)
            );

            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(destino)
            );

            message.setSubject("Relatório PDF");

            BodyPart texto = new MimeBodyPart();

            texto.setText(
                "Segue o relatório em PDF."
            );

            MimeBodyPart anexo =
                new MimeBodyPart();

            anexo.attachFile("relatorio.pdf");

            Multipart multipart =
                new MimeMultipart();

            multipart.addBodyPart(texto);
            multipart.addBodyPart(anexo);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println(
                "Email foi enviado!"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
