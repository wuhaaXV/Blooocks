package at.blooo.tetris;

import org.andengine.entity.Entity;

import android.util.Log;
import at.blooo.MainActivity;

public class Stone {

  Tetris mTetris;
  public int mCol;
  public int mRow;

  Entity[][] mParts;
  Entity[][] mTempParts;
  int mPartSize; /* size of one part in pixels */

  Stone(Tetris tetris, int partSize) {
    mTetris = tetris;
    mPartSize = partSize; 
    mParts = new Entity[MainActivity.FIGURE_SIZE][MainActivity.FIGURE_SIZE];
    mTempParts = new Entity[MainActivity.FIGURE_SIZE][MainActivity.FIGURE_SIZE];
  }

  void clear() {
    for (int c = 0; c < MainActivity.FIGURE_SIZE; c++) {
      for (int r = 0; r < MainActivity.FIGURE_SIZE; r++) {
        if (mParts[c][r] != null) {
          mTetris.mMainActivity.detachFromScene(mParts[c][r]);
          mParts[c][r] = null;
        }
      }
    }
  }
/*
  public void setStone() {

    clear();

    mCol = mTetris.COLUMNS / 2 - mPartsPerSide / 2;
    mRow = mTetris.ROWS - mPartsPerSide;
    
    mParts[0][2] = mTetris.mMainActivity.createBlock(0,0);
    mParts[1][2] = mTetris.mMainActivity.createBlock(0,0);
    mParts[2][2] = mTetris.mMainActivity.createBlock(0,0);
    mParts[2][3] = mTetris.mMainActivity.createBlock(0,0);

    updatePartsPositions();
    
    for (int c = 0; c < mPartsPerSide; c++) {
      for (int r = 0; r < mPartsPerSide; r++) {
        if (mParts[c][r] != null)
          //mParts[c][r].setAnchorCenter(-mPartSize/2.f,-mPartSize/2.f);
          mTetris.attachChild(mParts[c][r]);
      }
    }
  }
  */
  public void setStone(boolean[][] field){
    clear();
    
    mCol = mTetris.COLUMNS / 2 - MainActivity.FIGURE_SIZE / 2;
    mRow = mTetris.ROWS - MainActivity.FIGURE_SIZE;
    
    for (int i = 0; i < field.length; i++){
      for (int j = 0; j < field[0].length; j++){
        if (field[i][j] == true){
          mParts[i][j] = mTetris.mMainActivity.createBlock(0,0);
        }
      }
    }
    
    updatePartsPositions();
    for (int c = 0; c < MainActivity.FIGURE_SIZE; c++) {
      for (int r = 0; r < MainActivity.FIGURE_SIZE; r++) {
        if (mParts[c][r] != null)
          mTetris.attachChild(mParts[c][r]);
      }
    }
  }

  boolean collides(int x, int y) {
    for (int c = 0; c < MainActivity.FIGURE_SIZE; c++) {
      for (int r = 0; r < MainActivity.FIGURE_SIZE; r++) {
        if (mParts[c][r] != null) {
          if (c + x >= mTetris.COLUMNS || c + x < 0 || 
              r + y >= mTetris.ROWS ||r + y < 0)
            return true;

          if (mTetris.mField[c + x][r + y] != null)
            return true;
        }
      }
    }
    return false;
  }
  
  boolean collides(){
    return collides(mCol, mRow);
  }

  boolean moveLeft() {

    if (collides(mCol-1, mRow))
      return false;

    mCol--;
    updatePartsPositions();
    return true;
  }

  boolean moveRight() {

    if (collides(mCol + 1, mRow))
      return false;

    mCol++;
    updatePartsPositions();
    return true;
  }
  
  boolean moveDown() {

    if (collides(mCol, mRow -1))
      return false;
    
    mRow--;
    updatePartsPositions();
    return true;
  }

  boolean collidesOnNextDrop() {
    int col = mCol;
    int row = mRow - 1;

    return collides(col, row);
  }
  

  void updatePartsPositions(){
    for (int c = 0; c < MainActivity.FIGURE_SIZE; c++) {
      for (int r = 0; r < MainActivity.FIGURE_SIZE; r++) {
        if (mParts[c][r] != null) {
          Entity brick = mParts[c][r];
          brick.setPosition((c + mCol) * mPartSize + mPartSize/2, (r + mRow) * mPartSize + mPartSize/2);
        }
      }
    }
  }
  
  boolean rotateRight(){
    for (int i = 0; i < MainActivity.FIGURE_SIZE; i++){
      for (int j = 0; j < MainActivity.FIGURE_SIZE; j++){
        mTempParts[i][j] = mParts[MainActivity.FIGURE_SIZE - j - 1][i];
      }
    }
    
    Entity[][] swap = mParts;
    mParts = mTempParts;
    mTempParts = swap;
    
    if (collides()){
      mTempParts = mParts;
      mParts = swap;
      return false;
    }
    
    return true;
  }
  
  void freeze() {
    for (int c = 0; c < MainActivity.FIGURE_SIZE; c++) {
      for (int r = 0; r < MainActivity.FIGURE_SIZE; r++) {
        if (mParts[c][r] != null) {
          if (mTetris.mField[c + mCol][r + mRow] != null)
            Log.e("Tetris", "Overwriting stone on freezing");

          mTetris.mField[c + mCol][r + mRow] = mParts[c][r];
          mParts[c][r] = null;
        }
      }
    }

  }
}
