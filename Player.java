package Agents;

import java.util.ArrayList;
import GameLogic.*;
import java.util.Random;
import javafx.util.Pair;

public abstract class Player {

  protected Board board;
  // Better structure can be created serializing the ship types
  protected ArrayList<Ship> ships;
  protected boolean isAI;

  public abstract Pair<Integer, Integer> makeAttack();
  public abstract void evaluateAttack(int x, int y, Tile outcome);


  public void drawBoard() {
    this.board.drawBoard(isAI);
  }

  public void drawDebugBoard() {
    this.board.drawDebugBoard();
  }

  public boolean lostGame() {
    for(Ship current: ships){
      if (current.isDestroyed() == false) {
        return false;
      }
    }

    return true;
  }

  public void takeDamage(Pair<Integer, Integer> position) {
    System.out.print("Selected position was: " + position.getKey() +" " + position.getValue());
    char type = this.board.applyAttack(position.getKey(), position.getValue());
    if (type != Tile.WATER.getValue()) {
      System.out.println(" Ship damaged!"); // Intended not telling the ship type
      int value = Character.valueOf(type) - Character.valueOf(Tile.DESTROYER.getValue());
      this.ships.get(4 - value).applyDamage();

      if (this.ships.get(4 - value).isDestroyed()) {
        Tile destroyed = Tile.findByValue(type);
        System.out.println(destroyed + " was Destroyed!");
      }
    } else {
      System.out.println(" Water!");
    }
  }

  /*
   * Function that randomizes the ships on both players
   * Note: is intended that the player can't pick the location
   */
  public void placeShips() {
    //clean-up board
    this.board.resetBoard();
    this.ships.clear();

    /*
     * start with size 5 ship and go down to size 2
     * since it will be easier placing the bigger ones first
     */

    Random rand = new Random();
    // Place the biggest one first
    for (int i = 4; i >= 0; i--) {

      char type = Tile.DESTROYER.getValue();
      type += i;
      System.out.println("placing " + Tile.get(type) + " ship");

      int length = getLength(Tile.get(type));

      boolean placed = false;
      while (!placed) {
        // get random point on the table
        int x = rand.nextInt(10);
        int y = rand.nextInt(10);


        boolean dir = (rand.nextInt(2) == 1);

        if (this.board.checkAvailable(x, y, dir, length)) {
          this.board.placeShip(x, y, dir, length, type);
          Ship current = new Ship(length);
          ships.add(current);
          placed = true;
        }

      }
    }
  }


  private int getLength(Tile type) {

    switch(type) {
      case SUBMARINE: {
        return 3;
      }
      case CRUISER: {
        return 3;
      }
      case BATTLESHIP: {
        return 4;
      }
      case CARRIER: {
        return 5;
      }
      default: //DESTROYER
        return 2;
    }
  }

  public abstract void reset();

}
