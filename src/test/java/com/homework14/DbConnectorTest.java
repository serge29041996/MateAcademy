package com.homework14;

import com.homework14.dao.DbConnector;
import java.sql.Connection;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for database connector.
 */
public class DbConnectorTest {
  @Test
  public void testDbConnector()
      throws ClassNotFoundException {
    Connection connection = DbConnector.getConnection();
    Assert.assertNotNull(connection);
  }

}
