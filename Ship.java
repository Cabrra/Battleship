package GameLogic;

public class Ship {
  private int length;
  private int damage;

  public Ship(int length) {
    this.length = length;
    this.damage = 0;
  }

  public void applyDamage() {this.damage++; }

  public boolean isDestroyed() { return this.damage == this.length; }

}
