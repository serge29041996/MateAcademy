package com.homework18.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 * Utils for encryption and generating salt.
 */
public class HashUtils {
  private static final Logger LOGGER = Logger.getLogger(HashUtils.class);

  private HashUtils() {}

  /**
   * Encrypt password with SHA-512 algorithm.
   * @param password password for encryption
   * @param salt salt for boosting encryption
   * @return encrypted password
   */
  public static String getSha512SecurePassword(String password, String salt) {
    String generatedPassword = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      md.update(salt.getBytes(StandardCharsets.UTF_8));
      byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < bytes.length; i++) {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      generatedPassword = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      LOGGER.error("Cannot encrypt password", e);
    }
    return generatedPassword;
  }

  /**
   * Generate salt which consist from 7 characters.
   * @return generated salt
   */
  public static String generateSalt() {
    byte[] array = new byte[7];
    new Random().nextBytes(array);
    return new String(array, Charset.forName("UTF-8"));
  }
}
