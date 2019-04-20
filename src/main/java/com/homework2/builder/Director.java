package com.homework2.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Realization of director for management of builders.
 */
public class Director {
  public void makeConsoleApplication(ConsoleApplicationBuilderApplication builderApplication) {
    builderApplication.reset();
    builderApplication.setRequireInternet(false);
    builderApplication.setLanguageBackEnd("Java");
    builderApplication.setUserInterface(
        new UserInterface(800, 800, "black", "console"));
  }

  public void makeDesktopApplication(DesktopApplicationBuilderApplication builderApplication) {
    builderApplication.reset();
    builderApplication.setRequireInternet(false);
    builderApplication.setLanguageBackEnd("Java");
    builderApplication.setUserInterface(
        new UserInterface(800, 800, "grey", "form"));
  }

  public void makeWebApplication(WebApplicationBuilderApplication builderApplication) {
    builderApplication.reset();
    builderApplication.setRequireInternet(true);
    builderApplication.setDatabase("MySQL");
    builderApplication.setUserInterface(
        new UserInterface(800, 800, "blue", "webpage"));
    List<String> dependencies = new ArrayList<>();
    dependencies.add("JPA");
    dependencies.add("Hibernate");
    dependencies.add("Liquibase");
    dependencies.add("Spring MVC");
    dependencies.add("Log4j");
    BackEnd backEnd = new BackEnd("Java", "Spring");
    backEnd.setDependencies(dependencies);
    builderApplication.setBackEnd(backEnd);
  }
}
