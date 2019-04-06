package Agents;

  /*
   * Board to keep track of attacked coordinates
   */
public class VerifyBoard {

  private boolean[][] track;

  public VerifyBoard() {

    track = new boolean[10][10];
    resetTrack();
  }

  public void resetTrack() {
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j< 10; j++) {
        track[i][j] = true; // valid position
      }
    }
  }

  public boolean evaluateAttack(int x, int y) {
    return track[x][y];
  }

  public void addAttack(int x, int y) {
    track[x][y] = false; //mark as invalid position
  }
}
