package modulos.Alertas;

import modulos.Usuario.Administrador;
import modulos.Usuario.GestionUsuarios;
import modulos.Usuario.PersonalCGA;
import modulos.Usuario.Usuario;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;


public class Notificacion {

    private Session session;
    private MimeMessage correo;
    private Properties properties;
    private ArrayList<DataSource> attachedFiles;
    private static String passwordFrom = "jgjlvdwyrjvisxym";
    private static String emailFrom = "gestionestablecimientomascota@gmail.com";

    public Notificacion(){
        properties = new Properties();
        attachedFiles = new ArrayList<>();
    }

    public void attachFile(String filePath) {
        FileDataSource fileDataSource = new FileDataSource(filePath);
        attachedFiles.add(fileDataSource);
    }

    public void createEmail(Address[] addresses, String subject, String content) {

        //Protocolo de transferencia
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.user", emailFrom);
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.setProperty("mail.smtp.auth", "true");

        session = Session.getDefaultInstance(properties);
        correo = new MimeMessage(session);

        try {
            // Crear un contenedor MimeMultipart
            MimeMultipart multipart = new MimeMultipart();

            // Parte de texto del correo
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(content, "ISO-8859-1", "html");
            multipart.addBodyPart(textPart);

            // Adjuntar archivos al correo, si hay alguno
            for (DataSource attachedFile : attachedFiles) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.setDataHandler(new DataHandler(attachedFile));
                attachmentPart.setFileName(attachedFile.getName());
                multipart.addBodyPart(attachmentPart);
            }

            // Configurar el contenido del correo
            correo.setContent(multipart);

            // Configurar el remitente, destinatarios y otros detalles del correo
            correo.setFrom(new InternetAddress(emailFrom));
            correo.setRecipients(Message.RecipientType.TO, addresses);
            correo.setSubject(subject);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEmail(){
        try {
            Transport transport = session.getTransport("smtp");
            transport.connect(emailFrom, passwordFrom);
            transport.sendMessage(correo, correo.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public Address[] getDestinatariosNotificacion(String emailTo){

        ArrayList<InternetAddress> emails = new ArrayList<>();

        //Notificar a administradores y personal CGA
        for(Map.Entry<String, Usuario> entryUsuario : GestionUsuarios.getInstance().getUsuarios().entrySet()){
            if(entryUsuario.getValue() instanceof Administrador || entryUsuario.getValue() instanceof PersonalCGA) {
                try {
                    emails.add(new InternetAddress(entryUsuario.getValue().getCorreo()));
                } catch (AddressException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        //Notificar a propietario
        try {
            emails.add(new InternetAddress(emailTo));
        } catch (AddressException e) {
            throw new RuntimeException(e);
        }

        return emails.toArray(new InternetAddress[0]);
    }


}
