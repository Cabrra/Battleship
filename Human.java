package Agents;

import GameLogic.Board;
import GameLogic.Tile;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import javafx.util.Pair;

public class Human extends Player {

  private VerifyBoard trackBoard;

  public Human() {
    this.ships = new ArrayList<>();
    this.board = new Board();
    this.trackBoard = new VerifyBoard();
    this.isAI = false;
  }

  @Override
  public void reset() {
    this.ships.clear();
    this.board.resetBoard();
    this.trackBoard.resetTrack();
  }

  @Override
  public Pair<Integer, Integer> makeAttack() {
    boolean valid = false;
    int x = 0;
    int y = 0;

    Scanner userInput = new Scanner(System.in);

    while (!valid) {
      try {
        System.out.println("Please, select a valid X axis value to attack");
        x = userInput.nextInt();

        if (x >= 0 && x <= 9) {
          System.out.println("Please, select a valid Y axis value to attack");
          y = userInput.nextInt();

          if (y >= 0 && y <= 9) {
            // success!!
            valid = trackBoard.evaluateAttack(x, y);

            if (!valid) {
              System.out.println("REPEATED entry, please select another position");
            }
          }
        }
      } catch (InputMismatchException ime) {
        System.out.println("ERROR, please select values between 0 and 9 for both coordinates");
        valid = false;
        userInput.nextLine();
        x = 0;
        y = 0;
      }

    }

    trackBoard.addAttack(x, y);
    return new Pair<>(x, y);
  }

  @Override
  public void evaluateAttack(int x, int y, Tile outcome) {
    // Add some UI messages to let the player know
    switch (outcome) {
      case HIT:
      {
        System.out.println("HIT! Ship damaged!");
        break;
      }
      case SINK:
      {
        System.out.println("SUNK! Ship destroyed!");
        break;
      }
      case WATER:
      {
        System.out.println("Water! nothing to see here");
        break;
      }
    }

  }
}
