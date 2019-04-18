package com.homework7.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Service for initialization object output stream for reading.
 */
public class ObjectOutputStreamInitialization {
  private ObjectOutputStreamInitialization() {}

  /**
   * Initialize object output stream checking existence of file.
   * @param nameFile name checking file
   * @return initialized object output stream
   * @throws IOException when I/O errors occur
   */
  public static ObjectOutputStream initializeObjectOutputStream(String nameFile)
      throws IOException {
    Path fileLocation = Paths.get(nameFile);
    FileOutputStream fileOutputStream;
    ObjectOutputStream objectOutputStream;
    if (Files.exists(fileLocation)) {
      fileOutputStream = new FileOutputStream(nameFile, true);
      objectOutputStream = new AppendingObjectOutputStream(fileOutputStream);
    } else {
      fileOutputStream = new FileOutputStream(nameFile);
      objectOutputStream = new ObjectOutputStream(fileOutputStream);
    }
    return objectOutputStream;
  }
}
