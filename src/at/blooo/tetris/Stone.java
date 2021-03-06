package at.blooo.tetris;

import java.util.Random;

import org.andengine.entity.Entity;
import org.andengine.util.adt.color.Color;

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
  
  private void centerField() {

    int minX = 4;
    int maxX = 0; // TODO sizeof field
    int minY = 4;
    int maxY = 0;

    int size = 0;

    for (int y = 0; y < 5; y++)
      for (int x = 0; x < 5; x++)
        if (mParts[x][y] != null) {

          if (x < minX)
            minX = x;

          if (x > maxX)
            maxX = x;

          if (y < minY)
            minY = y;

          if (y > maxY)
            maxY = y;

        }

    size = 4;// maxX >= maxY ? maxX : maxY;

    Entity[][] tmpField = new Entity[5][5];

    for (int y = 0; y < 5; y++)
      for (int x = 0; x < 5; x++)
        tmpField[x][y] = null;

    for (int y = ((size / 2) - ((maxY - minY) / 2)); y <= (size / 2)
        + ((maxY - minY + 1) / 2); y++)
      for (int x = ((size / 2) - ((maxX - minX) / 2)); x <= (size / 2)
          + ((maxX - minX + 1) / 2); x++)
        tmpField[x][y] = mParts[x + ((maxX + minX) / 2) - (size / 2)][y
            + ((maxY + minY) / 2) - (size / 2)];

    for (int y = 0; y < 5; y++)
      for (int x = 0; x < 5; x++)
        mParts[x][y] = tmpField[x][y];

  }
  
  public void setStone(boolean[][] field){
    clear();
    
    mCol = mTetris.COLUMNS / 2 - MainActivity.FIGURE_SIZE / 2;
    mRow = mTetris.ROWS - MainActivity.FIGURE_SIZE;
    
    Color color = getRandomColor();
    
    for (int i = 0; i < field.length; i++){
      for (int j = 0; j < field[0].length; j++){
        if (field[i][j] == true){
          mParts[i][j] = mTetris.mMainActivity.createBlock(0,0);
          mParts[i][j].setColor(color);
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
    
    centerField();
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
          brick.setVisible(r + mRow < mTetris.VISIBLE_ROWS);
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
    
    centerField();
    
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