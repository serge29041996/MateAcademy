package com.homework2;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Solution for task about delete all files and directories recursively.
 */
public class DirectoriesClearing {

  public static void main(String[] args) throws IOException {
    generateDirectoriesAndFiles(5);
    clearAllElementsFromDirectory(System.getProperty("user.dir") + "/example");
  }

  private static void generateDirectoriesAndFiles(int numberGeneratedElements) throws IOException {
    String newParentPath = System.getProperty("user.dir") + "/example";
    File createdDirectory = new File(newParentPath);
    createdDirectory.mkdir();
    Random random = new Random();
    generateElements(newParentPath, numberGeneratedElements, random);
  }

  private static void generateElements(String parentPath, int numberGeneratedElement,
      Random random) throws IOException {
    if (numberGeneratedElement > 0) {
      if (random.nextDouble() < 0.5) {
        File createdFile = new File(parentPath + "/" + numberGeneratedElement + ".txt");
        numberGeneratedElement--;
        createdFile.createNewFile();
        generateElements(parentPath, numberGeneratedElement, random);
      } else {
        parentPath = parentPath + "/" + numberGeneratedElement;
        numberGeneratedElement--;
        File createdDirectory = new File(parentPath);
        createdDirectory.mkdir();
        generateElements(parentPath, numberGeneratedElement, random);
      }
    }
  }

  private static void clearAllElementsFromDirectory(String parentPath) {
    File directory = new File(parentPath);
    if (directory.isDirectory()) {
      File[] files = directory.listFiles();
      for (File f : files) {
        if (f.isDirectory()) {
          parentPath = parentPath + "/" + f.getName();
          clearAllElementsFromDirectory(parentPath);
        } else {
          f.delete();
        }
      }
      directory.delete();
    }
  }
}
