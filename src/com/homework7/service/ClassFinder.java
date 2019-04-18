package com.homework7.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for searching of classes.
 */
public class ClassFinder {
  private ClassFinder() {
  }

  /**
   * Get need information for injection.
   *
   * @param needField field, which would inject
   * @return array of need information: simple name of type, full name of type and location of type
   */
  public static String[] getInformationAboutField(Field needField) {
    String[] information = new String[3];
    Class<?> classField = needField.getType();
    information[0] = classField.getSimpleName();
    information[1] = classField.getCanonicalName();
    Package packageLocationClass = classField.getPackage();
    String packageName = packageLocationClass.getName();
    information[2] = System.getProperty("user.dir") + "/src/"
        + packageName.replace(".", "/");
    return information;
  }

  /**
   * Get appropriate classes by name.
   * @param needName name of need class
   * @param locationAppropriateClasses location, when appropriate classes located
   * @return list of found classes
   * @throws IOException if was occur I/O errors
   */
  public static List<String> getAppropriateClassesNameByNeedName(String needName,
      String locationAppropriateClasses) throws IOException {
    Path rootLocationPath = Paths.get(locationAppropriateClasses);
    String needClass;
    List<String> appropriateClasses = new ArrayList<>();
    DirectoryStream<Path> stream = Files.newDirectoryStream(rootLocationPath);
    for (Path file : stream) {
      needClass = file.getFileName().toString();
      if (needClass.contains(needName)) {
        appropriateClasses.add(needClass.split("\\.")[0]);
      }
    }
    appropriateClasses.remove(needName);
    return appropriateClasses;
  }

  /**
   * Find name of need class factory.
   * @param locationFactories location of all factories
   * @param partNameNeedFactory part of name for need factory
   * @return found name of class factory
   * @throws IOException when I/O exception occur
   */
  public static String findFactory(String locationFactories, String partNameNeedFactory)
      throws IOException {
    String findFactory;
    Path pathFactory = Paths.get(locationFactories);
    DirectoryStream<Path> factoryStream = Files.newDirectoryStream(pathFactory);
    for (Path factory : factoryStream) {
      findFactory = factory.getFileName().toString();
      if (findFactory.contains(partNameNeedFactory)) {
        findFactory = findFactory.split("\\.")[0];
        return findFactory;
      }
    }
    throw new FactoryNotFoundException();
  }
}
