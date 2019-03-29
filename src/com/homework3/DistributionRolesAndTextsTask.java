package com.homework3;

/**
 * Output distributed texts to every role.
 */
public class DistributionRolesAndTextsTask {
  private static final char END_ROLE_DECLARATION = ':';
  private static final String INDEX_SEPARATOR = ",";
  private static final String NEXT_LINE_SYMBOL = "\n";

  public static void main(String[] args) {
    System.out.println("Distribution roles and text lines");
    String[] roles = formRoles();
    String[] textLines = formTextLines();
    String distributedTextLinesToRole = distributeRolesAndTextLines(roles, textLines);
    System.out.println(distributedTextLinesToRole);
  }

  private static String[] formRoles() {
    return new String[]{"Городничий", "Аммос Федорович", "Артемий Филиппович", "Лука Лукич"};
  }

  private static String[] formTextLines() {
    return new String[] {
        "Городничий: Я пригласил вас, господа, с тем, чтобы сообщить"
            + " вам пренеприятное известие: к нам едет ревизор.",
        "Аммос Федорович: Как ревизор?",
        "Артемий Филиппович: Как ревизор?",
        "Городничий: Ревизор из Петербурга, инкогнито. И еще с секретным предписаньем.",
        "Аммос Федорович: Вот те на!",
        "Артемий Филиппович: Вот не было заботы, так подай!",
        "Лука Лукич: Господи боже! еще и с секретным предписаньем!"};
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
    for (int i = 0; i < roles.length; i++) {
      distributedText.append(roles[i]);
      distributedText.append(END_ROLE_DECLARATION);
      distributedText.append(NEXT_LINE_SYMBOL);
      addTextLinesToCurrentRole(indexesTextLinesForRole[i], distributedText, textLines);
      if (i < roles.length - 1) {
        distributedText.append(NEXT_LINE_SYMBOL);
      }
    }
    return distributedText.toString();
  }

  private static void addTextLinesToCurrentRole(StringBuilder indexesTextLinesCurrentRole,
      StringBuilder distributedText, String[] textLines) {
    final char closeRoundBracket = ')';
    String listTextLinesCurrentRole = indexesTextLinesCurrentRole.toString();
    if (!"".equals(listTextLinesCurrentRole)) {
      String[] indexesTextLineCurrentRole = listTextLinesCurrentRole.split(INDEX_SEPARATOR);
      for (int j = 0; j < indexesTextLineCurrentRole.length; j++) {
        int currentNumberTextLineForRole = Integer.parseInt(indexesTextLineCurrentRole[j]);
        distributedText.append(currentNumberTextLineForRole + 1);
        distributedText.append(closeRoundBracket);
        distributedText.append(textLines[currentNumberTextLineForRole]);
        distributedText.append(NEXT_LINE_SYMBOL);
      }
    }
  }
}
