package com.homework2.builder;

/**
 * Realization of builder for web application.
 */
public class WebApplicationBuilderApplication implements BuilderApplication {

  private Application application;

  @Override
  public void setRequireInternet(boolean requireInternet) {
    application.setRequireInternet(requireInternet);
  }

  @Override
  public void setUserInterface(UserInterface userInterface) {
    application.setUserInterface(userInterface);
  }

  @Override
  public void setLanguageBackEnd(String languageBackEnd) {
    if (application.getBackEnd() == null) {
      application.setBackEnd(new BackEnd(languageBackEnd));
    } else {
      application.getBackEnd().setLanguage(languageBackEnd);
    }
  }

  public void setDatabase(String database) {
    application.setDatabase(database);
  }

  public void setBackEnd(BackEnd backEnd) {
    if (application.getBackEnd() == null) {
      application.setBackEnd(backEnd);
    } else {
      BackEnd currentBackEnd = application.getBackEnd();
      currentBackEnd.setLanguage(backEnd.getLanguage());
      currentBackEnd.setDependencies(backEnd.getDependencies());
      currentBackEnd.setMainFramework(backEnd.getMainFramework());
    }
  }

  @Override
  public void reset() {
    application = new Application();
  }

  public Application getApplication() {
    return this.application;
  }
}
