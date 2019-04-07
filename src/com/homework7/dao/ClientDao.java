package com.homework7.dao;

import com.homework7.model.Client;

public interface ClientDao {

  void save(Client client);

  Client get();
}
