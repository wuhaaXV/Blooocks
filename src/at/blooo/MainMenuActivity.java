package at.blooo;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.IBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.content.Intent;

public class MainMenuActivity extends BaseGameActivity {

  public static final int WIDTH = 720;
  public static final int HEIGHT = 1280;

  float mTime = 0.0f;

  Camera mCamera;
  ITiledTextureRegion mStartButtonTextureRegion;
  ITiledTextureRegion mOptionsButtonTextureRegion;
  ITiledTextureRegion mAboutButtonTextureRegion;
  ITiledTextureRegion mExitButtonTextureRegion;
  Scene mScene;
  Sprite mSprite;

  @Override
  public EngineOptions onCreateEngineOptions() {
    mCamera = new Camera(0, 0, WIDTH, HEIGHT);

    EngineOptions engineOptions = new EngineOptions(true,
        ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), mCamera);

    return engineOptions;
  }

  @Override
  public void onCreateResources(
      OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {

    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

    BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(
        mEngine.getTextureManager(), 1024, 1024, BitmapTextureFormat.RGBA_8888,
        TextureOptions.BILINEAR);

    this.mStartButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory
        .createTiledFromAsset(mBuildableBitmapTextureAtlas, this,
            "start_button.png", 2, 1);

    this.mOptionsButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory
        .createTiledFromAsset(mBuildableBitmapTextureAtlas, this,
            "options_button.png", 2, 1);

    this.mAboutButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory
        .createTiledFromAsset(mBuildableBitmapTextureAtlas, this,
            "about_button.png", 2, 1);

    this.mExitButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory
        .createTiledFromAsset(mBuildableBitmapTextureAtlas, this,
            "exit_button.png", 2, 1);

    try {
      mBuildableBitmapTextureAtlas
          .build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
              0, 1, 1));
    } catch (TextureAtlasBuilderException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    mBuildableBitmapTextureAtlas.load();

    pOnCreateResourcesCallback.onCreateResourcesFinished();

  }

  @Override
  public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
      throws IOException {

    this.mScene = new Scene();

    pOnCreateSceneCallback.onCreateSceneFinished(mScene);
  }

  @Override
  public void onPopulateScene(Scene pScene,
      OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
    // Background background = new Background(0.1f, 0.2f, 1.0f, 1.0f);

    // this.mScene.setBackground(background);

    ButtonSprite startButtonSprite = new ButtonSprite(WIDTH * 0.5f,
        HEIGHT * 0.7f, this.mStartButtonTextureRegion,
        mEngine.getVertexBufferObjectManager()) {
      @Override
      public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
          float pTouchAreaLocalX, float pTouchAreaLocalY) {

        if (pSceneTouchEvent.isActionDown()) {

          Intent intent = new Intent(MainMenuActivity.this, MainActivity.class);
          startActivity(intent);
        }

        return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
            pTouchAreaLocalY);
      }
    };

    ButtonSprite optionsButtonSprite = new ButtonSprite(WIDTH * 0.5f,
        HEIGHT * 0.575f, this.mOptionsButtonTextureRegion,
        mEngine.getVertexBufferObjectManager()) {
      @Override
      public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
          float pTouchAreaLocalX, float pTouchAreaLocalY) {

        if (pSceneTouchEvent.isActionDown()) {

          // TODO Options
        }

        return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
            pTouchAreaLocalY);
      }
    };

    ButtonSprite aboutButtonSprite = new ButtonSprite(WIDTH * 0.5f,
        HEIGHT * 0.425f, this.mAboutButtonTextureRegion,
        mEngine.getVertexBufferObjectManager()) {
      @Override
      public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
          float pTouchAreaLocalX, float pTouchAreaLocalY) {

        if (pSceneTouchEvent.isActionDown()) {
          // About
        }

        return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
            pTouchAreaLocalY);
      }
    };

    ButtonSprite exitButtonSprite = new ButtonSprite(WIDTH * 0.5f,
        HEIGHT * 0.3f, this.mExitButtonTextureRegion,
        mEngine.getVertexBufferObjectManager()) {
      @Override
      public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
          float pTouchAreaLocalX, float pTouchAreaLocalY) {

        if (pSceneTouchEvent.isActionDown()) {
          Intent intent = new Intent(Intent.ACTION_MAIN);
          intent.addCategory(Intent.CATEGORY_HOME);
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
        }

        return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
            pTouchAreaLocalY);
      }
    };

    mScene.registerTouchArea(startButtonSprite);
    mScene.registerTouchArea(optionsButtonSprite);
    mScene.registerTouchArea(aboutButtonSprite);
    mScene.registerTouchArea(exitButtonSprite);
    mScene.setTouchAreaBindingOnActionDownEnabled(true);
    mScene.attachChild(startButtonSprite);
    mScene.attachChild(optionsButtonSprite);
    mScene.attachChild(aboutButtonSprite);
    mScene.attachChild(exitButtonSprite);

    pOnPopulateSceneCallback.onPopulateSceneFinished();

  }

}
