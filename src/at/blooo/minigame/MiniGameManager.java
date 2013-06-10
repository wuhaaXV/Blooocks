package at.blooo.minigame;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;

import android.util.Log;
import at.blooo.MainActivity;
import at.blooo.tetris.Tetris;

public class MiniGameManager {

  Tetris mTetris;
  MainActivity mMainActivity;
  MiniGame mCurrentMiniGame;
  Entity mFrame;
  
  public MiniGameManager(MainActivity mainActivity, Tetris tetris, Entity frame){
    mMainActivity = mainActivity;
    mTetris = tetris;
    mFrame = frame;
    mCurrentMiniGame = new InitMiniGame();
    mFrame.attachChild((Entity)mCurrentMiniGame);
  }
  
  
  public boolean[][] getNext(){
   
   boolean field[][] = null; 
   field = mCurrentMiniGame.getField();
   mMainActivity.detachFromScene((Entity)mCurrentMiniGame);
   mCurrentMiniGame.quit();
   
   mCurrentMiniGame = getRandomMinigame();
   mCurrentMiniGame.start();
   mFrame.attachChild((Entity)mCurrentMiniGame);
    
    return field;
  }

  private MiniGame getRandomMinigame() {
    
    StandardTetrisMiniGame mini = new StandardTetrisMiniGame(mMainActivity);
    mini.setSize(mFrame.getWidth(), mFrame.getHeight());
    mini.setPosition(mFrame.getWidth()/2, mFrame.getHeight()/2);
    
    return mini;
  }
  
  
  
}
