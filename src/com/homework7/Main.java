package com.homework7;

import com.homework7.di.Injector;
import com.homework7.handler.ConsoleHandler;
import java.io.IOException;

public class Main {
  public static void main(String[] args)
      throws IOException, IllegalAccessException, ClassNotFoundException {
    Injector.injectDependency();
    ConsoleHandler.handle();
  }
}
