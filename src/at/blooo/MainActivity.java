package at.blooo;

import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.KeyEvent;
import at.blooo.tetris.Tetris;

public class MainActivity extends BaseGameActivity implements
    IAccelerationListener {

  public static final int WIDTH = 1280;
  public static final int HEIGHT = 720;

  float mTime = 0.0f;

  Camera mCamera;
  ITextureRegion mBlock;
  ITextureRegion mTetrisBackground;
  Scene mScene;
  int mBlocksize = 64;

  float xVal = 0;
  float yVal = 0;
  float mSpeed = 0.5f;

  Tetris mTetris;

  @Override
  public void onResumeGame() {
    super.onResumeGame();
    this.enableAccelerationSensor(this);
  }

  @Override
  public void onPauseGame() {
    super.onPauseGame();
    this.disableAccelerationSensor();
  }

  @Override
  public EngineOptions onCreateEngineOptions() {
    mCamera = new Camera(0, 0, WIDTH, HEIGHT);

    EngineOptions engineOptions = new EngineOptions(true,
        ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), mCamera);

    return engineOptions;

  }

  @Override
  public Engine onCreateEngine(EngineOptions eo) {

    return new FixedStepEngine(eo, 60);
  }

  @Override
  public void onCreateResources(
      OnCreateResourcesCallback pOnCreateResourcesCallback) {
    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

    BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(
        mEngine.getTextureManager(), 1024, 1024, BitmapTextureFormat.RGB_565,
        TextureOptions.BILINEAR);

    this.mBlock = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
        mBuildableBitmapTextureAtlas, this, "blocks.png");

    BuildableBitmapTextureAtlas tetrisBgTextureAtlas = new BuildableBitmapTextureAtlas(
        mEngine.getTextureManager(), 64, 64, TextureOptions.REPEATING_BILINEAR);

    // Create our texture region - nothing new here
    mTetrisBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
        tetrisBgTextureAtlas, this, "tetrisBgTile.png");

    try {
      mBuildableBitmapTextureAtlas
          .build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
              0, 1, 1));
      tetrisBgTextureAtlas
          .build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
              0, 0, 0));
    } catch (TextureAtlasBuilderException e) {
      e.printStackTrace();
    }

    mBuildableBitmapTextureAtlas.load();
    tetrisBgTextureAtlas.load();

    pOnCreateResourcesCallback.onCreateResourcesFinished();

  }

  @Override
  public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {

    mScene = new Scene();
    mTetris = new Tetris(this);

    mTetris.setPosition(0, 0);
    mTetris.initField();
    mScene.attachChild(mTetris);
    mTetris.setScale(0.55f);

    pOnCreateSceneCallback.onCreateSceneFinished(mScene);

  }

  @Override
  public void onPopulateScene(Scene pScene,
      OnPopulateSceneCallback pOnPopulateSceneCallback) {

    mScene.registerUpdateHandler(new IUpdateHandler() {

      @Override
      public void onUpdate(float pSecondsElapsed) {
        MainActivity.this.mTime += pSecondsElapsed;

        if (MainActivity.this.mTime < mSpeed) {
          return;
        }

        MainActivity.this.mTime = 0;

        int val = (int) (MainActivity.this.xVal);

        if (val < 0) {
          for (int i = val; i < 0; i++)
            mTetris.moveLeft();
        }

        if (val > 0) {
          for (int i = val; i > 0; i--)
            mTetris.moveRight();
        }

        mTetris.moveDown();
      }

      @Override
      public void reset() {
        // TODO Auto-generated method stub

      }

    });

    pOnPopulateSceneCallback.onPopulateSceneFinished();
  }

  @Override
  public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
    // TODO Auto-generated method stub
  }

  @Override
  public void onAccelerationChanged(AccelerationData pAccelerationData) {
    this.yVal = pAccelerationData.getY();
    this.xVal = pAccelerationData.getX();
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {

    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
      mTetris.moveLeft();
      return false;
    }
    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
      mTetris.moveRight();
      return false;
    }
    if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
      mTetris.rotateRight();
      return false;
    }

    return super.onKeyDown(keyCode, event);
  }

  public void removeFromScene(final Entity e) {
    runOnUpdateThread(new Runnable() {
      
      @Override
      public void run() {
        e.detachSelf();
      }
    });
  }

  public Sprite createBlock(int x, int y) {
    return new Sprite(x, y, mBlock, mEngine.getVertexBufferObjectManager());
  }

  public Sprite createTetrisBG(int width, int height) {
    mTetrisBackground.setTextureSize(width, height);
    return new Sprite(0, 0, mTetrisBackground,
        mEngine.getVertexBufferObjectManager());
  }
}