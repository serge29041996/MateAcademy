package com.homework7.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PropertyLoader {

  /**
   * Read property from application.properties
   * @param name need property
   * @return value for need property
   * @throws IOException when problem work with file is occur
   */
  public static String getProperty(String name) throws IOException {
    Path property = Paths.get("application.properties");
    String propertyFileString = new String(Files.readAllBytes(property));
    return propertyFileString.split("=")[1];
  }
}
