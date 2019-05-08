package com.homework17.service;

import com.homework17.util.RandomCodeGenerator;
import java.util.Optional;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;

/**
 * Service for sending mail with confirmation code.
 */
public class MailConfirmation {
  private static final Logger LOGGER = Logger.getLogger(MailConfirmation.class);
  private static final String USERNAME = "testmail2904@gmail.com";
  private static String PASSWORD = "8765t4321";

  /**
   * Generate or set code and send to mail.
   * @param mail mail of destination
   * @param code code for confirmation of purchase
   * @return code for confirmation
   */
  public Optional<String> generateCodeAndSendToUserMail(String mail, String code) {
    LOGGER.debug("Try send mail with confirmation code to mail " + mail);
    Properties prop = new Properties();
    prop.put("mail.smtp.host", "smtp.gmail.com");
    prop.put("mail.smtp.port", "587");
    prop.put("mail.smtp.auth", "true");
    prop.put("mail.smtp.starttls.enable", "true");

    Session session = Session.getInstance(prop,
        new Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(USERNAME, PASSWORD);
          }
        });

    try {

      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(USERNAME));
      message.setRecipients(
          Message.RecipientType.TO,
          InternetAddress.parse(mail)
      );
      message.setSubject("Код для подтверждения покупки");
      String generatedCode;
      if (code.equals("")) {
        generatedCode = RandomCodeGenerator.generateFourSignCode();
      } else {
        generatedCode = code;
      }
      LOGGER.debug("Generated code for mail " + mail + ": " + generatedCode);
      message.setText("Уважаемый покупатель. Код подтверждения покупки " + generatedCode + ".");

      Transport.send(message);

      LOGGER.debug("Successfully send mail with confirmation code to address: " + mail);
      return Optional.of(generatedCode);
    } catch (MessagingException e) {
      LOGGER.error("Unable to send mail with confirmation code", e);
      return Optional.empty();
    }
  }

  /**
   * Generate code and send by mail.
   * @param mail mail of destination
   * @return generated code
   */
  public Optional<String> generateCodeAndSendToUserMail(String mail) {
    return generateCodeAndSendToUserMail(mail, "");
  }
}
