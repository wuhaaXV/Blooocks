package at.blooo.tetris;

import java.util.Random;

import org.andengine.entity.Entity;
import org.andengine.util.adt.color.Color;

import at.blooo.MainActivity;

public class Stone extends Entity{

  public int mCol;
  public int mRow;

  Entity[][] mParts;
  Entity[][] mTempParts;
  int mPartSize; /* size of one part in pixels */

  Stone(Tetris tetris, int partSize) {
    mPartSize = partSize; 
    mParts = new Entity[MainActivity.FIGURE_SIZE][MainActivity.FIGURE_SIZE];
    mTempParts = new Entity[MainActivity.FIGURE_SIZE][MainActivity.FIGURE_SIZE];
  }

  void clear() {
    for (int c = 0; c < MainActivity.FIGURE_SIZE; c++) {
      for (int r = 0; r < MainActivity.FIGURE_SIZE; r++) {
        if (mParts[c][r] != null) {
          MainActivity.instance().detachFromScene(mParts[c][r]);
          mParts[c][r] = null;
        }
      }
    }
  }

  
  Color getRandomColor(){

    Random rng = new Random();
    Color c;
    
    switch (rng.nextInt(5)){
    case 0:
      c = Color.YELLOW;
    break;
    case 1:
      c = Color.GREEN;
    break;
    case 2:
      c = Color.BLUE;
    break;
    case 3:
      c = Color.RED;
    break;
    case 4:
      c = Color.CYAN;
    break;
    default:
      c = Color.WHITE; // white on white BG sucks!
    break;
    }
    
    return c;
  }
  
  public void setStone(boolean[][] field){
    clear();

    Color color = getRandomColor();
    
    for (int c = 0; c < field.length; c++){
      for (int r = 0; r < field[0].length; r++){
        if (field[c][r] == true){
          mParts[c][r] = MainActivity.instance().createBlock(0,0);
          mParts[c][r].setColor(color);
          this.attachChild(mParts[c][r]);
        }
      }
    }
  }


}
