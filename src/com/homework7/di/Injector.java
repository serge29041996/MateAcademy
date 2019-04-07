package com.homework7.di;

import com.homework7.handler.ConsoleHandler;
import com.homework7.service.ClassFinder;
import com.homework7.service.FilterClass;
import com.homework7.service.PropertyLoader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class Injector {

  /**
   * Inject fields with need value.
   * @throws IllegalAccessException when we try access to private field.
   * @throws IOException when I/O errors occur
   * @throws ClassNotFoundException when class was not found
   */
  public static void injectDependency()
      throws IllegalAccessException, ClassNotFoundException, IOException {
    Class consoleHandlerClass = ConsoleHandler.class;
    /*
    Objects, which was created by teacher in class work for simplify
    Class fileClientDaoClass = FileClientDao.class;
    Class inMemoryClientDao = InMemoryClientDao.class;
    */

    String property = PropertyLoader.getProperty("db.name");

    Field[] fields = consoleHandlerClass.getDeclaredFields();
    for (Field field : fields) {
      if (field.isAnnotationPresent(Inject.class)) {
        field.setAccessible(true);
        /*
        Realization, which was created by teacher in class work
        boolean fileDao = fileClientDaoClass.isAnnotationPresent(Component.class);
        if (fileDao) {
          System.out.println("Работа с файлами разрешена");
        }
        boolean inMemoryDao = inMemoryClientDao.isAnnotationPresent(Component.class);
        if (inMemoryDao) {
          System.out.println("Работа с ОЗУ разрешена");
        }
        ClientDao clientDao = ClientDaoFactory.getClientDao(fileDao, inMemoryDao);
        field.set(null, clientDao);
        */

        String[] information = ClassFinder.getInformationAboutField(field);
        String nameClass = information[0];
        String fullNameClass = information[1];
        String locationAppropriateClasses = information[2];

        List<String> foundClasses = ClassFinder.getAppropriateClassesNameByNeedName(nameClass,
            locationAppropriateClasses);

        String findClass = FilterClass.findClassByProperty(property, foundClasses, nameClass);
        Class clazz = Class.forName(fullNameClass.replace(nameClass, "") + findClass);
        FilterClass.checkClassByImplementationExtensionAndAnnotation(clazz, nameClass);

        String locationFactory = System.getProperty("user.dir") + "/src/com/homework7/factory";
        String locationFactoryForClass = "com.homework7.factory";
        String findFactory = ClassFinder.findFactory(locationFactory, nameClass);
        Class clazzFactory = Class.forName(locationFactoryForClass + "." + findFactory);

        Field[] fieldsNeedFactory = clazzFactory.getDeclaredFields();
        Field findFactoryField = FilterClass.findNeedFieldByPartOfName(fieldsNeedFactory,
            findClass);

        findFactoryField.setAccessible(true);
        field.set(null, findFactoryField.get(null));
        System.out.println(field.get(null));
      }
    }
  }
}
