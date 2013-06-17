package at.blooo.minigame;

import java.util.Random;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import android.util.Log;
import at.blooo.MainActivity;

public class LightsOutMiniGame extends MiniGame {

  ITiledTextureRegion mButtonRegion;

  MainActivity mMainActivity;
  boolean[][] mField;

  public LightsOutMiniGame() {
    mMainActivity = MainActivity.instance();
    loadResources();
  }

  public void loadResources() {
    mField = new boolean[5][5];
    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

    BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(
        mMainActivity.getTextureManager(), 1024, 1024,
        BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR);

    this.mButtonRegion = BitmapTextureAtlasTextureRegionFactory
        .createTiledFromAsset(mBuildableBitmapTextureAtlas, mMainActivity,
            "lights_out_button.png", 2, 1);

    try {
      mBuildableBitmapTextureAtlas
          .build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
              0, 1, 1));
    } catch (TextureAtlasBuilderException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    mBuildableBitmapTextureAtlas.load();

    Random r = new Random();

    LightsOutButtonSprite buttonSprite;

    for (int y = 0; y < 5; y++) {
      for (int x = 0; x < 5; x++) {
        mField[x][y] = r.nextBoolean();

        buttonSprite = new LightsOutButtonSprite(60 + 110 * x, 60 + 110 * y,
            this.mButtonRegion, x, y,
            mMainActivity.getVertexBufferObjectManager()) {
          @Override
          public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
              float pTouchAreaLocalX, float pTouchAreaLocalY) {

            if (pSceneTouchEvent.isActionDown()) {
              if (mField[this.x][this.y]) {
                mField[this.x][this.y] = false;
                setCurrentTileIndex(0);
              } else {
                mField[this.x][this.y] = true;
                setCurrentTileIndex(1);
              }
            }

            return true;
          }
        };

        if (mField[x][y])
          buttonSprite.setCurrentTileIndex(1);

        mMainActivity.registerTouchArea(buttonSprite);
        this.attachChild(buttonSprite);
      }
    }
  }

  @Override
  public boolean[][] getField() {

    for (int y=0; y <5; y++)
      for (int x =0; x< 5; x++)
        if (mField[x][y])
          return mField;
    
    return FigureFactory.createWorst(mMainActivity.mTetris.mField);
  }

  @Override
  public void quit() {
    // todo: unload resources (textures)
  }

  @Override
  public void start() {

  }
}
