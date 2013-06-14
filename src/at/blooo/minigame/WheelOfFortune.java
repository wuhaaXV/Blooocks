package at.blooo.minigame;

import org.andengine.entity.Entity;
import org.andengine.util.adt.color.Color;

import at.blooo.MainActivity;

public class WheelOfFortune extends MiniGame {

  Entity mWheel;
  
  @Override
  boolean[][] getField() {
    // TODO Auto-generated method stub
    return FigureFactory.createL2();
  }

  @Override
  void quit() {
    // TODO Auto-generated method stub
    
  }

  @Override
  void start() {
    
    boolean[][] fig = FigureFactory.createL2();
    Entity sprite = FigureFactory.getSprite(fig, Color.WHITE);
    sprite.setPosition(this.getWidth()/2, this.getHeight()/2);
    this.attachChild(sprite);
  }

}
