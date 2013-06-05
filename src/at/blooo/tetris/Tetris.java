package at.blooo.tetris;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;

import at.blooo.MainActivity;

public class Tetris extends Entity{

  final int COLUMNS = 10;
  final int ROWS = 20;

  private Entity[][] field = new Entity[COLUMNS][ROWS];
  private MainActivity mMainActivity;
  private Stone mStone;
  
  public Tetris(MainActivity mainActivity) {
    // TODO Auto-generated constructor stub
    mMainActivity =  mainActivity;
  }

  public void initField() {
    /*
    for (int r = 0; r < ROWS; r++)
    {
      for (int c = 0; c < COLUMNS; c++)
      {
        if (field[c][r] != null)
        {
          final Entity e = field[c][r];
          mMainActivity.removeFromScene(e);
          field[c][r] = null;
        }
      }
    }
    */
    mStone = new Stone(mMainActivity);
    mStone.setStone();

    this.attachChild(mStone);
  }
  
  void moveLeft(){
    
  }

  void moveRight(){
    
  }
  
  void moveDown(){
    
  }
  
  void moveToBottom(){
    
  }
  
  void setStone(Stone stone){
    if (mStone != null)
    {
      mStone.clear();
      mMainActivity.removeFromScene(this.mStone);
    }
    this.mStone = stone;
    
    mStone.setPosition(this.getWidth()/2, this.getHeight()/2); //todo: fix initial position
  }
  
  void draw() {

  }

}
