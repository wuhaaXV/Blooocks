package at.blooo.minigame;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.ui.activity.BaseGameActivity;

import at.blooo.MainActivity;

public class MyMinigame extends Entity implements MiniGame{
  
  BaseGameActivity baseGameActivity;
  
  public MyMinigame(BaseGameActivity basegameActivty){
    this.baseGameActivity = basegameActivty;
  }

  @Override
  public boolean[][] getField() {
    
    boolean field[][] =  new boolean[MainActivity.FIGURE_SIZE][MainActivity.FIGURE_SIZE];
    
    field[1][2] = true;
    field[2][2] = true;
    field[2][3] = true;
    field[3][2] = true;
    
    return field;
  }

  @Override
  public void quit() {
    //nothing to do here
    
  }

  @Override
  public void start() {
    Rectangle rec1 = new Rectangle(this.mWidth/4, 3*this.mHeight/4, 100, 100, baseGameActivity.getVertexBufferObjectManager());
    rec1.setColor(1.0f, 0.0f, 0.0f);
    this.attachChild(rec1);
    
    Rectangle rec2 = new Rectangle(3*this.mWidth/4, 3*this.mHeight/4, 100, 100, baseGameActivity.getVertexBufferObjectManager());
    rec2.setColor(0.0f, 1.0f, 0.0f);
    this.attachChild(rec2);
    
    Rectangle rec3 = new Rectangle(this.mWidth/4, this.mHeight/4, 100, 100, baseGameActivity.getVertexBufferObjectManager());
    rec3.setColor(0.0f, 0.0f, 1.0f);
    this.attachChild(rec3);
    
    Rectangle rec4 = new Rectangle(3*this.mWidth/4, this.mHeight/4, 100, 100, baseGameActivity.getVertexBufferObjectManager());
    rec4.setColor(1.0f, 1.0f, 1.0f);
    this.attachChild(rec4);
  }
  
}
