package com.homework2.typeclassestask;

/**
 * Task with using anonymous class.
 */
public class Game {

  public static void main(String[] args) {
    String game = "Starcraft 1";
    Playable newPlayer = new Playable() {
      @Override
      public void play(String game) {
        System.out.println("I try to play in this game but it so boring. I will left this game.");
      }
    };
    newPlayer.play(game);
  }
}
