package com.homework6;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Class for working with elements of stream.
 */
public class MailService<T> implements Consumer<Sendable<T>> {
  private Map<String, List<T>> result = new MyHashMap<>();

  @Override
  public void accept(Sendable<T> sendableT) {
    List<T> elements =
        result.get(sendableT.getKey()) == Collections.EMPTY_LIST ? new LinkedList<T>() :
            result.get(sendableT.getKey());
    elements.add(sendableT.getValue());
    result.put(sendableT.getKey(), elements);
  }

  public Map<String, List<T>> getMailBox() {
    return result;
  }
}
