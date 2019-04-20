package com.homework7.dao;

import com.homework7.di.Component;
import com.homework7.model.Human;
import com.homework7.service.ObjectOutputStreamInitialization;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Realization work with human using file.
 */
@Component
public class FileHumanDao implements HumanDao {
  /**
   * Save information about human.
   * @param human object, which have information about human
   */
  @Override
  public void save(Human human) {
    try (ObjectOutputStream objectOutputStream =
        ObjectOutputStreamInitialization.initializeObjectOutputStream("human_storage.dat")) {
      objectOutputStream.writeObject(human);
    } catch (IOException e) {
      System.out.println("Не удалось записать человека в базу");
    }
  }

  /**
   * Get first human.
   * @return first human
   * @throws NoSuchHumanException when human has not found
   */
  @Override
  public List<Human> get() {
    List<Human> humans = new ArrayList<>();
    Human readHuman;
    try (ObjectInputStream inputObjectStream = new ObjectInputStream(
        new FileInputStream("human_storage.dat"))) {
      while ((readHuman = (Human)inputObjectStream.readObject()) != null) {
        humans.add(readHuman);
      }
      return humans;
    } catch (EOFException e) {
      return humans;
    } catch (Exception e) {
      System.out.println("Человек не найден в системе");
      throw new NoSuchHumanException();
    }
  }
}
