package at.blooo.minigame;

import java.util.Random;

import org.andengine.entity.Entity;

public class DummyMiniGame extends Entity implements MiniGame {

  public DummyMiniGame(){
    
  }
  
  @Override
  public boolean[][] getField() {
    Random r = new Random();
    boolean field[][] = new boolean[4][4];
    boolean found = false;
    for (int i = 0; i < 4; i++){
      for (int j = 0; j < 4; j++){
        boolean random = r.nextBoolean(); 
        field[i][j] = random;
        
        if (random != false)
          found = true;
        
      }
    }
    
    if (!found){
      field[2][2] = true;
    }
    return field;
  }

  @Override
  public void quit() {

  }

  @Override
  public void start() {
    
  }

}
