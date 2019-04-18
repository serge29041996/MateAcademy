package com.homework2.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Realization saving information about back end.
 */
public class BackEnd {

  private String language;
  private List<String> dependencies;
  private String mainFramework;

  public BackEnd() {
    dependencies = new ArrayList<>();
  }

  public BackEnd(String language) {
    this();
    this.language = language;
  }

  public BackEnd(String language, String mainFramework) {
    this(language);
    this.mainFramework = mainFramework;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public List<String> getDependencies() {
    return dependencies;
  }

  public void setDependencies(List<String> dependencies) {
    this.dependencies = new ArrayList<>(dependencies);
  }

  public void addDependency(String dependency) {
    this.dependencies.add(dependency);
  }

  public String getMainFramework() {
    return mainFramework;
  }

  public void setMainFramework(String mainFramework) {
    this.mainFramework = mainFramework;
  }

  @Override
  public String toString() {
    return "BackEnd{"
        + "language='" + language + '\''
        + ", dependencies=" + dependencies
        + ", mainFramework='" + mainFramework + '\''
        + '}';
  }
}
