package at.blooo.minigame;

import org.andengine.entity.Entity;

public abstract class MiniGame extends Entity {
  
  
  
  abstract boolean[][] getField();

  abstract void quit();

  abstract void start();
}
