package Agents;

import GameLogic.Board;
import GameLogic.Difficulty;

import GameLogic.Tile;
import java.util.ArrayList;
import java.util.Random;
import javafx.util.Pair;

public class CPU extends Player {

  private Pair<Integer, Integer> lastSuccess;
  private int direction;

  private Difficulty level;
  private VerifyBoard trackBoard;

  public CPU() {
    this.board = new Board();
    this.ships = new ArrayList<>();
    this.trackBoard = new VerifyBoard();
    this.lastSuccess = null;
    this.direction = -1;
    this.isAI = true;
  }

  public void selectDifficulty(Difficulty level) {
    this.level = level;
  }

  @Override
  public void reset() {
    this.ships.clear();
    this.board.resetBoard();
    this.trackBoard.resetTrack();
    this.lastSuccess = null;
    this.direction = -1;
  }

  @Override
  public Pair<Integer, Integer> makeAttack() {
    int x = 0;
    int y = 0;
    Pair<Integer, Integer> values = null;

    switch (this.level){
      case EASY:
      {
        // total random

        values = pickRandom();
      }
      case MEDIUM:
      {
        // keep track of last successful hit
        if (lastSuccess == null) {
         values = pickRandom();
        } else {
          // evaluate what we already know around this point
          if (direction != -1) {
            // we know the direction
            values = direction == 0 ? pickNearbyHorizontal(lastSuccess.getKey(), lastSuccess.getValue()) :
                                      pickNearbyVertical(lastSuccess.getKey(), lastSuccess.getValue());
          } else {
            // pick any point around this one
            Random rand = new Random();
            int localDir = rand.nextInt(2);
            values = pickNearbyHorizontal(lastSuccess.getKey(), lastSuccess.getValue());
          }

        }
      }
    }
    trackBoard.addAttack(values.getKey(), values.getValue());
    return values;

  }

  private Pair<Integer, Integer> pickNearbyHorizontal(int x, int y) {

    boolean valid = false;
    int xRet = x;
    int yRet = 0;

    while(!valid) {
        for(int bottom = y; bottom< 10; bottom++) {
          yRet = bottom;
          valid = trackBoard.evaluateAttack(xRet, yRet);
          if (valid) {
            break;
          }
        }

        if (!valid) {
          // we hit the wall
          for(int bottom = y; bottom > 0; bottom--) {
            yRet = bottom;
            valid = trackBoard.evaluateAttack(xRet, yRet);
            if (valid) {
              break;
            }
          }

          // safety net for edge cases
          if (!valid) {
            Pair<Integer, Integer> finalPick = pickRandom();
            xRet = finalPick.getKey();
            yRet = finalPick.getValue();
          }
      }
    }
    return new Pair<>(xRet, yRet);
  }

  private Pair<Integer, Integer> pickNearbyVertical(int x, int y) {

    boolean valid = false;
    int xRet = 0;
    int yRet = y;

    while(!valid) {
      for(int bottom = x; bottom< 10; bottom++) {
        xRet = bottom;
        valid = trackBoard.evaluateAttack(xRet, yRet);
        if (valid) {
          break;
        }
      }

      if (!valid) {
        // we hit the wall
        for(int bottom = x; bottom > 0; bottom--) {
          xRet = bottom;
          valid = trackBoard.evaluateAttack(xRet, yRet);
          if (valid) {
            break;
          }
        }

        // safety net for edge cases
        if (!valid) {
          Pair<Integer, Integer> finalPick = pickRandom();
          xRet = finalPick.getKey();
          yRet = finalPick.getValue();
        }
      }
    }

    return new Pair<>(xRet, yRet);
  }

    private Pair<Integer, Integer> pickRandom() {
    Random rand = new Random();
    int x= 0;
    int y = 0;

    boolean valid = false;
    while(!valid) {
      x = rand.nextInt(10);
      y = rand.nextInt(10);

      valid = trackBoard.evaluateAttack(x, y);
    }

    return new Pair<>(x, y);
  }

  @Override
  public void evaluateAttack(int x, int y, Tile outcome) {
    switch (outcome) {
      case HIT:
      {
        // if we already had one success hit we should explore it's direction
        if (lastSuccess != null) {

        } else {
          // we can interpolate the direction
          if (lastSuccess.getKey() == x) {
            // if the X was the same = horizontal
            this.direction = 0;
          } else if (lastSuccess.getValue() == y){
            // if the Y was the same Vertical
            this.direction =1;
          } else {
            // we didn't get nearby point
            direction = -1;
          }

        }

        lastSuccess = new Pair<>(x,y);
        break;
      }
      case SINK:
      {
        // reset the last success, since the ship was destroyed
        this.lastSuccess = null;
        this.direction = -1;
        break;
      }
      case WATER:
      {
        // do nothing
        break;
      }
    }

  }
}
