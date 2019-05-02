package com.homework16.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for enum role.
 */
public class RoleTest {
  @Test
  public void testComparingEqualRole() {
    Assert.assertEquals(Role.USER.getValue(), "user");
  }

  @Test
  public void testComparingNonEqualRole() {
    Assert.assertNotEquals(Role.ADMIN.getValue(), "user");
  }

  @Test
  public void testInitializationEnumFromString() {
    Assert.assertEquals(Role.fromString("user"), Role.USER);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInitializationEnumFromStringWithUnExistRole() {
    Assert.assertEquals(Role.fromString("test"), Role.USER);
  }
}
