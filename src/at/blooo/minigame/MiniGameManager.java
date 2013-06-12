package at.blooo.minigame;

import java.util.Random;

import org.andengine.entity.Entity;

import at.blooo.MainActivity;
import at.blooo.tetris.Tetris;

public class MiniGameManager {

  @SuppressWarnings("rawtypes")
  Class mGameClasses[] = {
      SelectorMiniGame.class,
      SelectorMiniGame.class
      };
  
  Tetris mTetris;
  MainActivity mMainActivity;
  MiniGame mCurrentMiniGame;
  Entity mFrame;
  
  public MiniGameManager(MainActivity mainActivity, Tetris tetris, Entity frame){
    mMainActivity = mainActivity;
    mTetris = tetris;
    mCurrentMiniGame = new InitMiniGame();
    mMainActivity = mainActivity;
    mFrame = frame;
  }
  
  
  public boolean[][] getNext(){
   
   boolean field[][] = null; 
   field = mCurrentMiniGame.getField();
   mMainActivity.detachFromScene((Entity)mCurrentMiniGame);
   mCurrentMiniGame.quit();
   
   mCurrentMiniGame = getRandomMinigame();
   mCurrentMiniGame.start();
    
    return field;
  }

  @SuppressWarnings("unchecked")
  private MiniGame getRandomMinigame() {
    
    Random rng = new Random();
    MiniGame game = null;
    int index = rng.nextInt(mGameClasses.length);
    
    try {
      game = (MiniGame)mGameClasses[index].getConstructor(MainActivity.class).newInstance(mMainActivity);
    } catch (Exception e){
      e.printStackTrace();
      assert(false);
    }
    
    mFrame.detachChildren();
    
    game.setSize(mFrame.getWidth(), mFrame.getHeight());
    game.setPosition(mFrame.getWidth()/2, mFrame.getHeight()/2);
    mFrame.attachChild(game);
    
    return game;
  }
  
  
  
}
