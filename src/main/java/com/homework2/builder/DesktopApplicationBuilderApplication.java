package com.homework2.builder;

/**
 * Realization of builder for desktop application.
 */
public class DesktopApplicationBuilderApplication implements BuilderApplication {

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
    application.setBackEnd(new BackEnd(languageBackEnd));
  }

  @Override
  public void reset() {
    application = new Application();
  }

  public Application getApplication() {
    return this.application;
  }
}
