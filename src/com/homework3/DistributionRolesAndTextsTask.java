package com.homework3;

/**
 * Output distributed texts to every role.
 */
public class DistributionRolesAndTextsTask {
  private static final char END_ROLE_DECLARATION = ':';
  private static final String INDEX_SEPARATOR = ",";

  public static void main(String[] args) {
    String[] roles = formRoles();
    String[] textLines = formTextLines();
    String distributedTextLinesToRole = distributeRolesAndTextLines(roles, textLines);
    System.out.println(distributedTextLinesToRole);
  }

  private static String[] formRoles() {
    String[] roles = new String[4];
    roles[0] = "Городничий";
    roles[1] = "Аммос Федорович";
    roles[2] = "Артемий Филиппович";
    roles[3] = "Лука Лукич";
    return roles;
  }

  private static String[] formTextLines() {
    String[] textLines = new String[7];
    textLines[0] = "Городничий: Я пригласил вас, господа, с тем, чтобы сообщить"
        + " вам пренеприятное известие: к нам едет ревизор.";
    textLines[1] = "Аммос Федорович: Как ревизор?";
    textLines[2] = "Артемий Филиппович: Как ревизор?";
    textLines[3] = "Городничий: Ревизор из Петербурга, инкогнито. И еще с секретным предписаньем.";
    textLines[4] = "Аммос Федорович: Вот те на!";
    textLines[5] = "Артемий Филиппович: Вот не было заботы, так подай!";
    textLines[6] = "Лука Лукич: Господи боже! еще и с секретным предписаньем!";
    return textLines;
  }

  private static String distributeRolesAndTextLines(String[] roles, String[] textLines) {
    StringBuilder[] indexesTextLinesForRole = new StringBuilder[roles.length];
    for (int i = 0; i < indexesTextLinesForRole.length; i++) {
      indexesTextLinesForRole[i] = new StringBuilder();
    }

    for(int i = 0; i < textLines.length; i++) {
      for (int j = 0; j < roles.length; j++) {
        if (textLines[i].startsWith(roles[j] + END_ROLE_DECLARATION)) {
          indexesTextLinesForRole[j].append(i);
          indexesTextLinesForRole[j].append(INDEX_SEPARATOR);
          textLines[i] = textLines[i].replaceFirst(roles[j] + END_ROLE_DECLARATION, "");
          break;
        }
      }
    }

    return formResultStringWithRolesAndTextLines(roles, indexesTextLinesForRole, textLines);
  }

  private static String formResultStringWithRolesAndTextLines(String[] roles,
      StringBuilder[] indexesTextLinesForRole, String[] textLines) {
    StringBuilder distributedText = new StringBuilder();
    final String nextLineSymbol = "\n";
    final char closeRoundBracket = ')';
    String[] indexesTextLineCurrentRole;
    int currentNumberTextLineForRole;
    String listTextLinesCurrentRole;
    for (int i = 0; i < roles.length; i++) {
      distributedText.append(roles[i]);
      distributedText.append(END_ROLE_DECLARATION);
      distributedText.append(nextLineSymbol);
      listTextLinesCurrentRole = indexesTextLinesForRole[i].toString();
      if ("".equals(listTextLinesCurrentRole)) {
        break;
      }
      indexesTextLineCurrentRole = indexesTextLinesForRole[i].toString().split(INDEX_SEPARATOR);
      for (int j = 0; j < indexesTextLineCurrentRole.length; j++) {
        currentNumberTextLineForRole = Integer.parseInt(indexesTextLineCurrentRole[j]);
        distributedText.append(currentNumberTextLineForRole + 1);
        distributedText.append(closeRoundBracket);
        distributedText.append(textLines[currentNumberTextLineForRole]);
        distributedText.append(nextLineSymbol);
      }
      if (i < roles.length - 1) {
        distributedText.append(nextLineSymbol);
      }
    }
    return distributedText.toString();
  }
}
