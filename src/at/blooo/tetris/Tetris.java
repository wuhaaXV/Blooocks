package at.blooo.tetris;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.util.Log;
import at.blooo.MainActivity;
import at.blooo.minigame.MiniGameManager;

public class Tetris extends Entity {

  final int COLUMNS = 10;
  final int ROWS = 25;
  final int VISIBLE_ROWS = 25;
  final int mPartSize = 64;
  final int mStoneSize = 5;

  Entity[][] mField = new Entity[COLUMNS][ROWS];
  MainActivity mMainActivity;
  Stone mStone;
  MiniGameManager mMiniGameManager;

  public Tetris(MainActivity mainActivity, MiniGameManager mgm) {
    mMainActivity = mainActivity;
    mMiniGameManager = mgm;
    mStone = new Stone(this, mPartSize, mStoneSize);
    
    Entity bg = mMainActivity.createTetrisBG(COLUMNS * mPartSize, VISIBLE_ROWS * mPartSize);
    bg.setPosition((COLUMNS * mPartSize) / 2, (ROWS * mPartSize) /2);
    this.attachChild(bg);
    
  }

  public synchronized void initField() {

    for (int r = 0; r < ROWS; r++) {
      for (int c = 0; c < COLUMNS; c++) {
        if (mField[c][r] != null) {
          final Entity e = mField[c][r];
          mMainActivity.detachFromScene(e);
          mField[c][r] = null;
        }
      }
    }

    mStone.setStone(mMiniGameManager.getNext());
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
      cropFullLines();
      mStone.setStone(mMiniGameManager.getNext());
      if (mStone.collides()){
        Log.d("Tetris", "You Lost!");
        // todo: do something useful. Just restart for now
        initField();
      }
    } else {
      mStone.moveDown();
    }
  }

  private void cropFullLines() {
    int r, c;
    
    for (r = 0; r < ROWS; r++){
      boolean line_full = true;
      for (c = 0; c < COLUMNS; c++){       // Identify a full line
        if (mField[c][r] == null){
          line_full = false;
          break;
        }
      }
      if (line_full){                      // drop every row above by one
        for (int r2 = r; r2 < ROWS-1; r2++){
          for (int c2 = 0; c2 < COLUMNS; c2++){
            
            if (mField[c2][r2] != null){
              mMainActivity.detachFromScene(mField[c2][r2]);
            }
            
            mField[c2][r2] = mField[c2][r2+1];
            if (mField[c2][r2] != null){
              updatePartPos(c2, r2, mField[c2][r2]);
            }
            
            mField[c2][r2+1] = null;
          }
        }
      }
    }
  }
  
  void updatePartPos(int c, int r, Entity e){
    e.setPosition(c * mPartSize +mPartSize/2, r * mPartSize +mPartSize/2); 
  }

  public synchronized void moveToBottom() {

  }

}
