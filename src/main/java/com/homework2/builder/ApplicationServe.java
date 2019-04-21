package com.homework2.builder;

/**
 * Solution for task 2.1.
 */
public class ApplicationServe {

  public static void main(String[] args) {
    Director director = new Director();
    ConsoleApplicationBuilderApplication
        consoleApplicationBuilderApplication = new ConsoleApplicationBuilderApplication();
    director.makeConsoleApplication(consoleApplicationBuilderApplication);
    Application application = consoleApplicationBuilderApplication.getApplication();
    System.out.println("Build application with console builder:");
    System.out.println(application);

    DesktopApplicationBuilderApplication
        desktopApplicationBuilderApplication = new DesktopApplicationBuilderApplication();
    director.makeDesktopApplication(desktopApplicationBuilderApplication);
    application = desktopApplicationBuilderApplication.getApplication();
    System.out.println("Build application with desktop builder:");
    System.out.println(application);

    WebApplicationBuilderApplication
        webApplicationBuilderApplication = new WebApplicationBuilderApplication();
    director.makeWebApplication(webApplicationBuilderApplication);
    application = webApplicationBuilderApplication.getApplication();
    System.out.println("Build application with web builder:");
    System.out.println(application);
  }
}
