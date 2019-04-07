package com.homework7.dao;

import com.homework7.di.Component;
import com.homework7.model.Client;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryClientDao implements ClientDao {
  private static final List<Client> inMemoryStorage = new ArrayList<>();

  @Override
  public void save(Client client) {
    inMemoryStorage.add(client);
  }

  @Override
  public Client get() {
    return inMemoryStorage.get(0);
  }
}
