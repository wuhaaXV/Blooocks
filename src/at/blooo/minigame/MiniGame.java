package at.blooo.minigame;

import org.andengine.entity.Entity;

import at.blooo.MainActivity;

public abstract class MiniGame extends Entity {
  
  
  abstract boolean[][] getField();

  abstract void quit();

  abstract void start();
}
