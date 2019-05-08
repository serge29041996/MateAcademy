package com.homework17.service;

import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for mail confirmation.
 */
public class MailConfirmationTest {
  private final MailConfirmation mailConfirmation = new MailConfirmation();

  @Test
  public void testGenerateCodeAndSendToUserMailToExistMail() {
    Optional<String> generatedCode = mailConfirmation
        .generateCodeAndSendToUserMail("sergei29041996@gmail.com");
    Assert.assertNotEquals(Optional.empty(), generatedCode);
  }

  @Test
  public void testGenerateCodeAndSendToUserMailToNonExistMail() {
    Optional<String> generatedCode = mailConfirmation
        .generateCodeAndSendToUserMail("mail@test.com");
    Assert.assertNotEquals(Optional.empty(), generatedCode);
  }
}
