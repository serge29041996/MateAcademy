package com.homework7.service;

import com.homework7.di.Component;
import com.homework7.factory.ComponentNotFoundException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Service for filtering classes with need category.
 */
public class FilterClass {
  private FilterClass() {}

  /**
   * Find class name by property among list of classes.
   * @param property property for finding class
   * @param classesForSearch list of classes name, where need class located
   * @param sparePartName spare part in name of all classes
   * @return find class name
   * @throws ComponentNotFoundException if has not found class name by property
   */
  public static String findClassByProperty(String property, List<String> classesForSearch,
      String sparePartName) {
    String clearedName;
    String findClass;
    for (String name : classesForSearch) {
      clearedName = name.replaceAll(sparePartName, "").toLowerCase();
      if (clearedName.equals(property)) {
        findClass = name;
        return findClass;
      }
    }
    throw new ComponentNotFoundException();
  }

  /**
   * Check clazz by implementation of interface, extension of class and existence of annotation.
   * @param clazz class for checking
   * @param nameNeedClass name of class which is parent or interface, which implemented
   */
  public static void checkClassByImplementationExtensionAndAnnotation(Class clazz,
      String nameNeedClass) {
    boolean isClassImplement = FilterClass.isClassImplementNeedInterface(clazz, nameNeedClass);
    boolean isHasComponentAnnotation = FilterClass.isClassAnnotated(clazz);
    boolean isClassExtend = FilterClass.isClassExtendNeedClass(clazz, nameNeedClass);
    boolean isValidClass = (isClassImplement || isClassExtend) && isHasComponentAnnotation;
    if (!isValidClass) {
      throw new ComponentNotFoundException();
    }
  }

  /**
   * Find need field by part of name.
   * @param fields array of fields
   * @param partName part of name for field
   * @return found field
   * @throws FieldNotFoundException when field with need part of name was not found
   */
  public static Field findNeedFieldByPartOfName(Field[] fields, String partName) {
    Field findField;
    for (Field field : fields) {
      if (field.getName().toLowerCase().contains(partName.toLowerCase())) {
        findField = field;
        return findField;
      }
    }
    throw new FieldNotFoundException();
  }

  /**
   * Check if class implement need interface.
   * @param clazz class for checking of implementation
   * @param nameNeedInterface name of need interface
   * @return true, if implement need interface, otherwise false
   */
  private static boolean isClassImplementNeedInterface(Class clazz, String nameNeedInterface) {
    Class<?>[] interfaces = clazz.getInterfaces();
    for (int i = 0; i < interfaces.length; i++) {
      if (interfaces[i].getName().contains(nameNeedInterface)) {
        return true;
      }
    }
    return false;
  }

  private static boolean isClassAnnotated(Class clazz) {
    return clazz.isAnnotationPresent(Component.class);
  }

  private static boolean isClassExtendNeedClass(Class clazz, String nameNeedClass) {
    Class<?> superClass = clazz.getSuperclass();
    return superClass.getName().contains(nameNeedClass);
  }
}
