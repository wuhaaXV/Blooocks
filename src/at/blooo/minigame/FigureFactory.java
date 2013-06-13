package at.blooo.minigame;

import org.andengine.entity.Entity;
import org.andengine.util.adt.color.Color;

import at.blooo.MainActivity;

public class FigureFactory {

  static boolean[][] createWorst(){
    boolean field[][] = new boolean[MainActivity.FIGURE_SIZE][MainActivity.FIGURE_SIZE];
    field[0][0] = true;
    field[0][1] = true;
    field[0][2] = true;
    field[0][3] = true;
    field[0][4] = true;
    field[1][0] = true;
    field[1][4] = true;
    field[2][0] = true;
    field[2][4] = true;
    field[3][0] = true;
    field[3][4] = true;
    field[4][0] = true;
    field[4][1] = true;
    field[4][2] = true;
    field[4][3] = true;
    return field;
  }
  
  static boolean[][] createT() {

    boolean field[][] = new boolean[MainActivity.FIGURE_SIZE][MainActivity.FIGURE_SIZE];
    field[1][2] = true;
    field[2][2] = true;
    field[2][3] = true;
    field[3][2] = true;
    return field;
  }

  static boolean[][] createL1() {

    boolean field[][] = new boolean[MainActivity.FIGURE_SIZE][MainActivity.FIGURE_SIZE];
    field[1][2] = true;
    field[2][2] = true;
    field[3][2] = true;
    field[3][3] = true;
    return field;
  }

  static boolean[][] createL2() {

    boolean field[][] = new boolean[MainActivity.FIGURE_SIZE][MainActivity.FIGURE_SIZE];
    field[1][2] = true;
    field[1][3] = true;
    field[2][2] = true;
    field[3][2] = true;
    return field;
  }

  static boolean[][] createZ1() {

    boolean field[][] = new boolean[MainActivity.FIGURE_SIZE][MainActivity.FIGURE_SIZE];
    field[1][2] = true;
    field[2][2] = true;
    field[2][1] = true;
    field[3][1] = true;
    return field;
  }

  static boolean[][] createZ2() {

    boolean field[][] = new boolean[MainActivity.FIGURE_SIZE][MainActivity.FIGURE_SIZE];
    field[1][1] = true;
    field[2][1] = true;
    field[2][2] = true;
    field[3][2] = true;
    return field;
  }

  static boolean[][] createI() {

    boolean field[][] = new boolean[MainActivity.FIGURE_SIZE][MainActivity.FIGURE_SIZE];
    field[1][2] = true;
    field[2][2] = true;
    field[3][2] = true;
    field[4][2] = true;
    return field;
  }

  static boolean[][] createSquare() {

    boolean field[][] = new boolean[MainActivity.FIGURE_SIZE][MainActivity.FIGURE_SIZE];
    field[1][1] = true;
    field[1][2] = true;
    field[2][1] = true;
    field[2][2] = true;
    return field;
  }
  
  static boolean[][] createClassicTetrisById(int id){
    boolean[][] fig = null;
    
    switch(id){
    case 0:
      fig = createT();
      break;
    case 1:
      fig = createL1();
      break;
    case 2:
      fig = createL2();
      break;
    case 3:
      fig = createZ1();
      break;
    case 4:
      fig = createZ2();
      break;
    case 5:
      fig = createI();
      break;
    case 6:
      fig = createSquare();
      break;
    }

    return fig;
  }
  
  public static Entity getSprite(boolean[][] fig, Color color){
    // todo: wanna center that thing?
    Entity e = new Entity();
    
    for (int r = 0; r < MainActivity.FIGURE_SIZE; r++){
      for (int c = 0; c < MainActivity.FIGURE_SIZE; c++){
        if (fig[c][r] == true){
          int x = MainActivity.BRICK_SIZE * (c- MainActivity.FIGURE_SIZE/2);
          int y = MainActivity.BRICK_SIZE * (r- MainActivity.FIGURE_SIZE/2);
          Entity block = MainActivity.instance().createBlock(x, y);
          block.setColor(color);
          e.attachChild(block);
        }
      }
    }
    
    return e;
  }
  
}
