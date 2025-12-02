package es.israeldelamo.transportes.utilidades;

import es.israeldelamo.transportes.bbdd.Propiedades;
import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class EnvioCorreos {

    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(EnvioCorreos.class);


    public static void lanzaUnCorreoDeCargas(String codConductor, String descripcion) {
        String to = Propiedades.getValor("destinatarioCorreo");
        String from = "transportes@israeldelamo.es";
        final String username = "api";
        final String password = Propiedades.getValor("passwordCorreo");
        String host = "live.smtp.mailtrap.io";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        //create the Session object
        Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            //create a MimeMessage object
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(from));
            //set From email field
            // Establecer los correos de los destinatarios (TO)
            String[] destinatarios = {
                    Propiedades.getValor("destinatarioCorreo1"),
                    Propiedades.getValor("destinatarioCorreo2"),
                    Propiedades.getValor("destinatarioCorreo3"),
                    Propiedades.getValor("destinatarioCorreo4")};

            InternetAddress[] toAddresses = new InternetAddress[destinatarios.length];
            for (int i = 0; i < destinatarios.length; i++) {
                toAddresses[i] = new InternetAddress(destinatarios[i]);
            }

            // Establecer todos los destinatarios a la vez
            mensaje.setRecipients(Message.RecipientType.TO, toAddresses);
            //set email subject field
            mensaje.setSubject("Notificación de la aplicación de transportes");


            //set the content of the email message
            mensaje.setText("El conductor con DNI: " + codConductor + " " + descripcion);
            //send the email message
            Transport.send(mensaje);
            logger.info("Email Message Sent Successfully");
        } catch (AddressException ex) {
            throw new RuntimeException(ex);
        } catch (MessagingException ex) {
            logger.error("Fallo en el envio");
            logger.error(ex.getMessage());
        }


    }



/*

    public static void main(String[] args) {
        EnvioCorreos.lanzaUnCorreoDeNoCargas(" el 2", "nada");
    }
*/

}