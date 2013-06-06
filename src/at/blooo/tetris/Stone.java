package at.blooo.tetris;

import org.andengine.entity.Entity;

import android.util.Log;

public class Stone {

  Tetris mTetris;
  public int mCol;
  public int mRow;

  Entity[][] mParts;
  Entity[][] mTempParts;
  int mPartsPerSide; /* number of parts along  x and y (array size) */
  int mPartSize; /* size of one part in pixels */

  Stone(Tetris tetris, int partSize, int partsDim) {
    mTetris = tetris;
    mPartSize = partSize;
    mPartsPerSide = partsDim;
    mParts = new Entity[mPartsPerSide][mPartsPerSide];
    mTempParts = new Entity[mPartsPerSide][mPartsPerSide];
  }

  void clear() {
    for (int c = 0; c < mPartsPerSide; c++) {
      for (int r = 0; r < mPartsPerSide; r++) {
        if (mParts[c][r] != null) {
          mTetris.mMainActivity.removeFromScene(mParts[c][r]);
          mParts[c][r] = null;
        }
      }
    }
  }

  public void setStone() {

    clear();

    mCol = mTetris.COLUMNS / 2 - mPartsPerSide / 2;
    mRow = mTetris.ROWS - mPartsPerSide / 2;
    
    mParts[0][0] = mTetris.mMainActivity.createBlock(mPartSize * (0 + mCol),
        mPartSize * (0 + mRow));
    mParts[1][0] = mTetris.mMainActivity.createBlock(mPartSize * (1 + mCol),
        mPartSize * (0 + mRow));
    mParts[2][0] = mTetris.mMainActivity.createBlock(mPartSize * (2 + mCol),
        mPartSize * (0 + mRow));
    mParts[2][1] = mTetris.mMainActivity.createBlock(mPartSize * (2 + mCol),
        mPartSize * (1 + mRow));

    for (int c = 0; c < mPartsPerSide; c++) {
      for (int r = 0; r < mPartsPerSide; r++) {
        if (mParts[c][r] != null)
          //mParts[c][r].setAnchorCenter(-mPartSize/2.f,-mPartSize/2.f);
          mTetris.attachChild(mParts[c][r]);
      }
    }
  }

  boolean collides(int x, int y) {
    for (int c = 0; c < mPartsPerSide; c++) {
      for (int r = 0; r < mPartsPerSide; r++) {
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
    for (int c = 0; c < mPartsPerSide; c++) {
      for (int r = 0; r < mPartsPerSide; r++) {
        if (mParts[c][r] != null) {
          Entity brick = mParts[c][r];
          brick.setPosition((c + mCol) * mPartSize, (r + mRow) * mPartSize);
        }
      }
    }
  }
  
  boolean rotateRight(){
    for (int i = 0; i < mPartsPerSide; i++){
      for (int j = 0; j < mPartsPerSide; j++){
        mTempParts[i][j] = mParts[mPartsPerSide - j - 1][i];
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
    for (int c = 0; c < mPartsPerSide; c++) {
      for (int r = 0; r < mPartsPerSide; r++) {
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
