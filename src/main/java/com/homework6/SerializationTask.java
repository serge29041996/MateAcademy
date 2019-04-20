package com.homework6;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Task with serialization and deserialization of class.
 */
public class SerializationTask {
  /**
   * Method for serialization and deserialization Animal objects.
   * @param args arguments for the application
   * @throws IOException if file cannot open or create
   */
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("data.bin"));
    System.out.println("Write to file data.bin serialized animal objects: ");
    int numberAnimal = 11;
    System.out.println("Number of animal objects: " + numberAnimal);
    outputStream.writeInt(numberAnimal);
    for (int i = 0; i < numberAnimal; i++) {
      Animal currentAnimal = new Animal("" + i);
      System.out.println("Write new animal object: " + currentAnimal);
      outputStream.writeObject(currentAnimal);
    }
    System.out.println("Write all animal objects");
    outputStream.close();
    byte[] resultArray = Files.readAllBytes(Paths.get("data.bin"));
    System.out.println("Read all deserialize animal objects: ");
    Animal[] animals = deserializeAnimalArray(resultArray);
    System.out.println("All read animal objects:");
    for (int i = 0; i < animals.length; i++) {
      if (i > 0) {
        System.out.print(",");
      }
      System.out.print(animals[i]);
    }
    System.out.println();
  }

  private static Animal[] deserializeAnimalArray(byte[] data) {
    try (ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(data))) {
      int size = inputStream.readInt();
      if (size < 0) {
        throw new IllegalArgumentException("Size for array cannot be less than 0");
      }
      Animal[] animals = new Animal[size];
      for (int i = 0; i < size; i++) {
        animals[i] = (Animal)inputStream.readObject();
      }
      return animals;
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read data.");
    } catch (ClassNotFoundException e) {
      throw new IllegalArgumentException("Class did not find.");
    } catch (ClassCastException e) {
      throw new IllegalArgumentException("Cannot cast to class Animal.");
    }
  }
}
