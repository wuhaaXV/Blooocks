package at.blooo.tetris;

import org.andengine.entity.Entity;

import android.util.Log;
import at.blooo.MainActivity;

public class Tetris extends Entity {

  final int COLUMNS = 10;
  final int ROWS = 20;
  final int mPartSize = 64;
  final int mStoneSize = 4;

  Entity[][] mField = new Entity[COLUMNS][ROWS];
  MainActivity mMainActivity;
  Stone mStone;

  public Tetris(MainActivity mainActivity) {
    mMainActivity = mainActivity;
    mStone = new Stone(this, mPartSize, mStoneSize);
  }

  public synchronized void initField() {

    for (int r = 0; r < ROWS; r++) {
      for (int c = 0; c < COLUMNS; c++) {
        if (mField[c][r] != null) {
          final Entity e = mField[c][r];
          mMainActivity.removeFromScene(e);
          mField[c][r] = null;
        }
      }
    }
    
    mStone.setStone();
  }

  public synchronized void moveLeft() {
    mStone.moveLeft();
  }

  public synchronized void moveRight() {
    mStone.moveRight();
  }
  
  public synchronized void rotateRight(){
    mStone.rotateRight();
  }

  public synchronized void moveDown() {
    if (mStone.collidesOnNextDrop()) {
      mStone.freeze();
      mStone.setStone();
      if (mStone.collides()){
        Log.d("Tetris", "You Lost!"); // todo: do something useful
      }
    } else {
      mStone.moveDown();
    }
  }

  synchronized void moveToBottom() {

  }

}
