package com.homework7.dao;

import com.homework7.di.Component;
import com.homework7.model.Human;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Realization work with human using file.
 */
@Component
public class FileHumanDao implements HumanDao {
  @Override
  public void save(Human human) {
    try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
        new FileOutputStream("human_storage.dat"))) {
      objectOutputStream.writeObject(human);
    } catch (IOException e) {
      System.out.println("Не удалось записать человека в базу");
    }
  }

  @Override
  public Human get() {
    try (ObjectInputStream inputObjectStream = new ObjectInputStream(
        new FileInputStream("human_storage.dat"))) {
      return (Human) inputObjectStream.readObject();
    } catch (Exception e) {
      System.out.println("Человек не найден в системе");
      return null;
    }
  }
}
