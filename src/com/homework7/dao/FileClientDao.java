package com.homework7.dao;

import com.homework7.di.Component;
import com.homework7.model.Client;
import com.homework7.service.ObjectOutputStreamInitialization;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileClientDao implements ClientDao {
  /**
   * Save information about client into file.
   * @param client client for saving
   */
  public void save(Client client) {
    try (ObjectOutputStream objectOutputStream =
        ObjectOutputStreamInitialization.initializeObjectOutputStream("client_storage.dat")) {
      objectOutputStream.writeObject(client);
    } catch (IOException e) {
      System.out.println("Не удалось записать клиента в базу");
    }
  }

  /**
   * Get first client, which was saved.
   * @return first client
   * @throws NoSuchClientException when client has not found
   */
  public List<Client> get() {
    List<Client> clients = new ArrayList<>();
    Client readClient;
    try (ObjectInputStream inputObjectStream = new ObjectInputStream(
        new FileInputStream("client_storage.dat"))) {
      while ((readClient = (Client)inputObjectStream.readObject()) != null) {
        clients.add(readClient);
      }
      return clients;
    } catch (EOFException e) {
      return clients;
    } catch (Exception e) {
      System.out.println("Клиенты не найдены в системе");
      throw new NoSuchClientException();
    }
  }
}
