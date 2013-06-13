package at.blooo.minigame;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
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
  ITextureRegion[] mTextures = new ITextureRegion[7];

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
  
  void loadResources(){
    BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(
        mMainActivity.getEngine().getTextureManager(), 512, 512, BitmapTextureFormat.RGBA_8888,
        TextureOptions.BILINEAR);

    
    // todo: draw and load real textures
   mTextures[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
        mBuildableBitmapTextureAtlas, mMainActivity, "tetris_t.png");
   mTextures[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
       mBuildableBitmapTextureAtlas, mMainActivity, "tetris_t.png");
   mTextures[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
       mBuildableBitmapTextureAtlas, mMainActivity, "tetris_t.png");
   mTextures[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
       mBuildableBitmapTextureAtlas, mMainActivity, "tetris_t.png");
   mTextures[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
       mBuildableBitmapTextureAtlas, mMainActivity, "tetris_t.png");
   mTextures[5] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
       mBuildableBitmapTextureAtlas, mMainActivity, "tetris_t.png");
   mTextures[6] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
       mBuildableBitmapTextureAtlas, mMainActivity, "tetris_t.png");
   
   try {
    mBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
         0, 1, 1));
  } catch (TextureAtlasBuilderException e) {
    e.printStackTrace();
  }
   mBuildableBitmapTextureAtlas.load();
  }
  
  @Override
  public void start() {
    loadResources();
    
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
      
      mButtons[i] = new Sprite(col * this.mWidth / 4,row * this.mHeight / 8,mTextures[i], mMainActivity.getVertexBufferObjectManager()) {
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