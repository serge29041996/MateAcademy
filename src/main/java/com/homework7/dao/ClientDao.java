package com.homework7.dao;

import com.homework7.model.Client;
import java.util.List;

public interface ClientDao {

  void save(Client client);

  List<Client> get();
}
