package at.blooo.tetris;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;

import at.blooo.MainActivity;

public class Stone extends Entity{
  
  final int COLUMNS = 4;
  final int ROWS = 4;
  
  int mCol;
  int mRow;

  private Entity[][] mParts = new Entity[COLUMNS][ROWS];
  private MainActivity mMainActivity;
  
  Stone(MainActivity ma){
    mMainActivity = ma;
  }
  
  void clear(){
    for (int c = 0; c < COLUMNS; c++)
    {
      for (int r = 0; r < ROWS; r++)
      {
        if (mParts[c][r] != null){
          mMainActivity.removeFromScene(mParts[c][r]);
          mParts[c][r] = null;
        }

      }
    }
  }
  
  public void setStone(){
    final int bsize = 64;
    int offsetX = mMainActivity.WIDTH / 2;
    int offsetY = 0;
    clear();
    
    mParts[0][0] = mMainActivity.createBlock(bsize*0 + offsetX, bsize*0 + offsetY);
    mParts[1][0] = mMainActivity.createBlock(bsize*1 + offsetX, bsize*0 + offsetY);
    mParts[2][0] = mMainActivity.createBlock(bsize*2 + offsetX, bsize*0 + offsetY);
    mParts[2][1] = mMainActivity.createBlock(bsize*2 + offsetX, bsize*1 + offsetY);
    
    
    for (int c = 0; c < COLUMNS; c++)
    {
      for (int r = 0; r < ROWS; r++)
      {
        if (mParts[c][r] != null)
          this.attachChild(mParts[c][r]);
      }
    }
  }
      
}
