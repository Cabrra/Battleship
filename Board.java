package GameLogic;

public class Board {

  // I could've used ArrayLists or Vectors however since this is a constant size array
  // I decided using plain arrays.
  private char[][] playerBoard;

  public Board() {
    playerBoard = new char[10][10];
  }

  public void resetBoard() {
    //Reset board
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        playerBoard[i][j] = Tile.HIDDEN.getValue();
      }
    }
  }

  public boolean checkAvailable(int x, int y, boolean horizontal, int length) {

    boolean isAvailable = true;
    if (horizontal) {
      // early check-out
      if (y + length > 9) {
        return false;
      }

      for (int pos = 0; pos < length; pos++) {
        if (playerBoard[x][y+pos] != Tile.HIDDEN.getValue())
        {
          isAvailable = false;
          break;
        }
      }

    } else {
      // early check-out
      if (x + length > 9) {
        return false;
      }

      for (int pos = 0; pos < length; pos++) {
        if (playerBoard[x+pos][y] != Tile.HIDDEN.getValue())
        {
          isAvailable = false;
          break;
        }
      }

    }

    return isAvailable;
  }

  /*
   * Place the ship on selected position
   */
  public void placeShip( int x, int y, boolean horizontal, int length, char type) {
    if (horizontal) {
      for (int pos = 0; pos < length; pos++) {
        playerBoard[x][y + pos] = type;
      }
    } else {
      for (int pos = 0; pos < length; pos++) {
        playerBoard[x+pos][y] = type;
      }
    }
  }

  /*
   * Function that randomizes the ships on both HUDs
   * This reuses the function and resources.
   */
  public void drawBoard(boolean isAI) {
    if (isAI) {
      // clear console
      System.out.flush();

      System.out.println("Enemy board");
      System.out.println("   0 1 2 3 4 5 6 7 8 9");
      System.out.println("   - - - - - - - - - -");
      for (int i = 0; i < 10; i++) {
        System.out.print(i + " |");
        for (int j = 0; j < 10; j++) {
          if (playerBoard[i][j] != Tile.HIT.getValue() &&
              playerBoard[i][j] != Tile.WATER.getValue()) {
            System.out.print(Tile.HIDDEN.getValue() + "|");
          } else {
            evaluateColor(Tile.findByValue(playerBoard[i][j]));
          }

        }
        System.out.println();
        System.out.println("   - - - - - - - - - -");
      }
    } else{
      // is player

      System.out.println();
      System.out.println("Player board");
      System.out.println("   0 1 2 3 4 5 6 7 8 9");
      System.out.println("   - - - - - - - - - -");

      for (int i = 0; i < 10; i++) {
        System.out.print(i + " |");
          for (int j = 0; j < 10; j++) {
            evaluateColor(Tile.findByValue(playerBoard[i][j]));
          }
        System.out.println();
        System.out.println("   - - - - - - - - - -");
      }
      System.out.println();
    }
  }

  public void evaluateColor(Tile value) {
    switch (value) {
      case HIT: {
        System.out.print(ConsoleColor.ANSI_RED + value.getValue() + ConsoleColor.ANSI_RED +  ConsoleColor.ANSI_WHITE + "|" +  ConsoleColor.ANSI_WHITE);
        break;
      }
      case WATER: {
        System.out.print(ConsoleColor.ANSI_BLUE + value.getValue() + ConsoleColor.ANSI_BLUE +  ConsoleColor.ANSI_WHITE + "|" +  ConsoleColor.ANSI_WHITE);
        break;
      }
      case HIDDEN: {
        System.out.print(ConsoleColor.ANSI_CYAN + value.getValue() + ConsoleColor.ANSI_CYAN +  ConsoleColor.ANSI_WHITE + "|" +  ConsoleColor.ANSI_WHITE);
        break;
      }
      default: {
        System.out.print(value.getValue() + "|");
        break;
      }
    }
  }

  public void drawDebugBoard() {

    System.out.println();
    System.out.println("Agents Debug Player board");
    System.out.println("  0 1 2 3 4 5 6 7 8 9");
    System.out.println("  - - - - - - - - - -");

    for (int i = 0; i < 10; i++) {
      System.out.print(i + "|");
      for (int j = 0; j < 10; j++) {
        System.out.print(playerBoard[i][j] + "|");
      }
      System.out.println("");
      System.out.println("   - - - - - - - - - -");
    }
    System.out.println();
  }


  public char applyAttack(int x, int y) {

    char result = Tile.WATER.getValue();
    char type = playerBoard[x][y];
    if (type == Tile.DESTROYER.getValue() ||
        type == Tile.SUBMARINE.getValue() ||
        type == Tile.CRUISER.getValue() ||
        type == Tile.BATTLESHIP.getValue() ||
        type == Tile.CARRIER.getValue()) {
          result = type;
          playerBoard[x][y] = Tile.HIT.getValue();
    }
    else {
      playerBoard[x][y] = Tile.WATER.getValue();
    }

    return result;
  }

}
