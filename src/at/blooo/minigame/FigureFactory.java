package at.blooo.minigame;

import org.andengine.entity.Entity;
import org.andengine.util.adt.color.Color;

import android.util.Log;
import at.blooo.MainActivity;

public class FigureFactory {

  static boolean[][] createWorst(Entity[][] field) {

    int[] ch = getColumnHeight(field);

    int[] min = new int[5 + 1];
    for (int i = 0; i < 6; i++)
      min[i] = 20;

    int[] max = new int[5 + 1];
    for (int i = 0; i < 10; i++) {
      for (int j = Math.max(i - 5 + 1, 0); j <= Math.min(5, i); j++) {

        if (ch[i] > max[j])
          max[j] = ch[i];
        if (ch[i] < min[j])
          min[j] = ch[i];
      }
    }

    int index = 0;
    int diff = 0;
    int d = 0;

    for (int i = 0; i < (5 + 1); i++) {
      d = max[i] - min[i];
      if (d <= 5 && d > diff) {
        index = i;
        diff = d;
      }
    }
    

    boolean[][] bField = new boolean[5][5];

    for (int x = 0; x < 5; x++)
      for (int y = 0; y < 5; y++) {
        if (!(min[index]+y >= ch[index+x]))
          bField[x][y] = true;
      }

    return bField;
  }

  public static boolean[][] createBest(Entity[][] field) {
    int[] ch = getColumnHeight(field);

    int[] min = new int[5 + 1];
    for (int i = 0; i < 6; i++)
      min[i] = 20;

    int[] max = new int[5 + 1];
    for (int i = 0; i < 10; i++) {
      for (int j = Math.max(i - 5 + 1, 0); j <= Math.min(5, i); j++) {

        if (ch[i] > max[j])
          max[j] = ch[i];
        if (ch[i] < min[j])
          min[j] = ch[i];
      }
    }

    int index = 0;
    int diff = 0;
    int d = 0;

    for (int i = 0; i < (5 + 1); i++) {
      d = max[i] - min[i];
      if (d <= 5 && d > diff) {
        index = i;
        diff = d;
      }
    }
    

    boolean[][] bField = new boolean[5][5];

    for (int x = 0; x < 5; x++)
      for (int y = 0; y < 5; y++) {
        if (min[index]+y >= ch[index+x])
          bField[x][y] = true;
      }

    return bField;
  }

  private static int[] getColumnHeight(Entity[][] field) {
    int[] ch = new int[10];
    for (int x = 0; x < 10; x++) {
      for (int y = 20; y >= 0; y--) {
        if (field[x][y] != null) {
          ch[x] = y+1;
          break;
        }
      }
    }

    /*
     * int last = 0;
     * 
     * for (int y= 20; y >=0; y--){ if (field[0][y] != null) { last = y; break;
     * } }
     * 
     * for (int x = 1; x < 10; x++){ for (int y= 20; y >=0; y--){ if
     * (field[x][y] != null) { ch[x-1] = y - last; last = y; break; } } }
     */

    return ch;
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

  static boolean[][] createClassicTetrisById(int id) {
    boolean[][] fig = null;

    switch (id) {
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

  public static Entity getSprite(boolean[][] fig, Color color) {
    // todo: wanna center that thing?
    Entity e = new Entity();

    for (int r = 0; r < MainActivity.FIGURE_SIZE; r++) {
      for (int c = 0; c < MainActivity.FIGURE_SIZE; c++) {
        if (fig[c][r] == true) {
          int x = MainActivity.BRICK_SIZE * (c - MainActivity.FIGURE_SIZE / 2);
          int y = MainActivity.BRICK_SIZE * (r - MainActivity.FIGURE_SIZE / 2);
          Entity block = MainActivity.instance().createBlock(x, y);
          block.setColor(color);
          e.attachChild(block);
        }
      }
    }

    return e;
  }

}
