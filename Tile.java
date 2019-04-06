package GameLogic;/*
 * Enum to handle the board tile values
 * Note that if a tile is 'Hidden' means that it wasn't attacked yet.
 * 'A-B-C-D-E' - is used only for display to the player and keeping he ship type
 * 'H' - is when either player damages a ship
 * 'W' - is to display an attacked Water tile
 */

import java.util.HashMap;
import java.util.Map;

public enum Tile {
  HIDDEN('~'),
  WATER('W'),
  DESTROYER('A'), // baseline ship
  SUBMARINE('B'), // SHIP+1
  CRUISER ('C'), // SHIP+2
  BATTLESHIP('D'), //SHIP+3
  CARRIER('E'), //SHIP+4
  HIT('H'),// any hit type for both AI and Agents.Player
  SINK('S'); // ONLY FOR THE AI

  private final char value;
  // Note that in this case doesn't make much sense using a map
  private static final Map<Character, Tile> lookup = new HashMap<>();

  private Tile(char value) {
    this.value = value;
  }

  public char getValue() {
    return this.value;
  }

  static {
    for (Tile t : Tile.values()) {
      lookup.put(t.getValue(), t);
    }
  }

  public static Tile get(char value) {
    return lookup.get(value);
  }

  public static Tile findByValue(char find){
    for(Tile t : values()){
      if( t.getValue() == find){
        return t;
      }
    }
    return null;
  }

}