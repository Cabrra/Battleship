package GameLogic;

import Agents.CPU;
import Agents.Human;
import java.util.InputMismatchException;
import java.util.Scanner;
import javafx.util.Pair;

public class GameController {

  private Human human;
  private CPU AI;
  private Scanner userInput;

  public GameController() {
    human = new Human();
    AI = new CPU();
    userInput = new Scanner(System.in);
  }

  public void play() {

    human.reset();
    AI.reset();

    System.out.println("Generating both player boards");
    System.out.println("Player Ships:");
    human.placeShips();
    System.out.println("CPU Ships:");
    AI.placeShips();

    Pair<Integer, Integer> currentPlay;
    while (!human.lostGame() && !AI.lostGame()) {
      // game logic
      human.drawBoard();
      AI.drawBoard();

      // Player's turn
      currentPlay = human.makeAttack();
      AI.takeDamage(currentPlay);

      if (AI.lostGame()) {
        // early out
        break;
      }

      currentPlay = AI.makeAttack();
      human.takeDamage(currentPlay);
    }

    if (human.lostGame()) {
      System.out.println("You lost :( ");
    } else {
      System.out.println("You WON! :D");
    }

  }

  public void MainMenu() {

    System.out.println("888888b.            888    888    888                   888      d8b 8888888b.  \n"
                     + "888  \"88b           888    888    888                   888      Y8P 888   Y88b \n"
                     + "888  .88P           888    888    888                   888          888    888 \n"
                     + "8888888K.   8888b.  888888 888888 888  .d88b.  .d8888b  88888b.  888 888   d88P \n"
                     + "888  \"Y88b     \"88b 888    888    888 d8P  Y8b 88K      888 \"88b 888 8888888P\"  \n"
                     + "888    888 .d888888 888    888    888 88888888 \"Y8888b. 888  888 888 888        \n"
                     + "888   d88P 888  888 Y88b.  Y88b.  888 Y8b.          X88 888  888 888 888        \n"
                     + "8888888P\"  \"Y888888  \"Y888  \"Y888 888  \"Y8888   88888P' 888  888 888 888        \n"
                     + "                                                                                \n");

    System.out.println();
    System.out.println();
    System.out.println("Welcome to battleship - the cmd game");
    System.out.println("Please, press enter to start...");

    boolean keepPlaying = true;
    while (keepPlaying) {
      selectAILevel();
      play();
      keepPlaying = interLobby();
    }

    userInput.close();
  }

  public void selectAILevel() {
    int wait = 0;
    boolean selected = false;
    while (!selected) {
      try {
        userInput.nextLine(); //cleanup
        System.out.println("Please, select AI level");
        System.out.println("1 - Easy");
        System.out.println("2 - Challenging");
        wait = userInput.nextInt();

        if (wait == 1 || wait == 2) {
          selected = true;
        }
      } catch (InputMismatchException ime) {
        System.out.println("Please, input a valid selection");
        selected = false;
      }

    }

    AI.selectDifficulty(wait == 1? Difficulty.EASY : Difficulty.MEDIUM);
  }

  public boolean interLobby() {
    String wait = "";
    boolean selected = false;
    while (!selected) {
      try {
        userInput.nextLine();
        System.out.println("Do you want to play again? y/n");
        wait = userInput.nextLine();

        if (wait.charAt(0) == 'y' || wait.charAt(0) == 'n') {
          selected = true;
        }
      } catch (InputMismatchException ime) {
        selected = false;
      }
    }

    return wait.charAt(0) == 'y' ? true : false;
  }

}
