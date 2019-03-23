package com.homework1;

/**
 * Solution for task 1.1
 */
public class MatchOutcome {
  public static void main(String[] args) {
    System.out.println(getMatchOutcome(1, 1, 1, 1));
    System.out.println(getMatchOutcome(0, 2, 1, 1));
    System.out.println(getMatchOutcome(1, 2, 0, 3));
    System.out.println(getMatchOutcome(2, 1, 3, 0));
    System.out.println(getMatchOutcome(3, 3, 3, 2));
    System.out.println(getMatchOutcome(3, 2, 3, 3));
    System.out.println(getMatchOutcome(2, 3, 3, 3));
    System.out.println(getMatchOutcome(1, 2, 0, 1));
  }

  private static int getMatchOutcome(int playedScoreTeam1, int playedScoreTeam2,
                                     int rateScoreTeam1, int rateScoreTeam2) {
    return (playedScoreTeam1 == rateScoreTeam1 && playedScoreTeam2 == rateScoreTeam2) ?
        2 : playedScoreTeam1 > playedScoreTeam2 ? rateScoreTeam1 > playedScoreTeam1 ? 1 : 0 :
        rateScoreTeam2 > playedScoreTeam2 ? 1 : 0;
  }
}
