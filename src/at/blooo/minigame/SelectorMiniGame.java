package at.blooo.minigame;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.util.adt.color.Color;

import android.util.Log;
import at.blooo.MainActivity;

public class SelectorMiniGame extends MiniGame {

  
  
  MainActivity mMainActivity;
  
  Entity[] mButtons = new Entity[7];
  Color[]  mColors = {new Color(0,0,1),
                      new Color(0,1,0),
                      new Color(0,1,1),
                      new Color(1,0,0),
                      new Color(1,0,1),
                      new Color(1,1,0),
                      new Color(1,1,1),    
  };

  int selected = -1;

  public SelectorMiniGame(MainActivity mainActivity) {
    mMainActivity = mainActivity;
  }

  @Override
  public boolean[][] getField() {
    if (selected >= 0)
      return FigureFactory.createClassicTetrisById(selected);
    
    return FigureFactory.createWorst();
  }

  @Override
  public void quit() {
    // nothing to do here

  }

  private void resetButtons() {
    for (int i = 0; i < mButtons.length; i++)
      mButtons[i].setScale(1.f);
  }

  @Override
  public void start() {
    int col;
    int row;
    
    for (int i = 0; i < mButtons.length; i++){
      final int finalI = i;
      if (i < 4){
        col = 1;
        row = i*2+1;
      } 
      else{
        col = 3;
        row = (i-4)*2+2;
      }
      
      mButtons[i] = new Rectangle(col * this.mWidth / 4, row * this.mHeight / 8, 100, 100,
          mMainActivity.getVertexBufferObjectManager()) {
        public boolean onAreaTouched(
            org.andengine.input.touch.TouchEvent pSceneTouchEvent,
            float pTouchAreaLocalX, float pTouchAreaLocalY) {
          if (pSceneTouchEvent.isActionDown()) {
            resetButtons();
            mButtons[finalI].setScale(1.4f);
            selected = finalI;
          }
          return true;
        };
      };
      mButtons[i].setColor(mColors[i]);
      mMainActivity.registerTouchArea(mButtons[i]);
      this.attachChild(mButtons[i]); 
    }
  }
}