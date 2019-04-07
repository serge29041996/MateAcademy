package com.homework7.dao;

import com.homework7.di.Component;
import com.homework7.model.Client;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@Component
public class FileClientDao implements ClientDao {
  /**
   * Save information about client into file.
   * @param client client for saving
   */
  public void save(Client client) {
    try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
        new FileOutputStream("client_storage.dat"))) {
      objectOutputStream.writeObject(client);
    } catch (IOException e) {
      System.out.println("Не удалось записать клиента в базу");
    }
  }

  /**
   * Get first client, which was saved.
   * @return first client
   */
  public Client get() {
    try (ObjectInputStream inputObjectStream = new ObjectInputStream(
        new FileInputStream("client_storage.dat"))) {
      return (Client) inputObjectStream.readObject();
    } catch (Exception e) {
      System.out.println("Клиент не найден в системе");
      return null;
    }
  }
}
