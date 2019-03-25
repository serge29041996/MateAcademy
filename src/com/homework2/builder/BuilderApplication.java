package com.homework2.builder;

/**
 * Interface of builder.
 */
public interface BuilderApplication {

  void setRequireInternet(boolean requireInternet);

  void setUserInterface(UserInterface userInterface);

  void setLanguageBackEnd(String languageBackEnd);

  void reset();
}
