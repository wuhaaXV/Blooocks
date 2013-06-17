package at.blooo.minigame;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
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

import at.blooo.MainActivity;

//TODO way too much hardcoding

public class TouchRunnerMinigame extends MiniGame {

  ITiledTextureRegion mButtonARegion;
  ITiledTextureRegion mButtonBRegion;
  ITiledTextureRegion mCharacterRegion;
  ITextureRegion mBGRegion;

  ButtonSprite aSprite;
  ButtonSprite bSprite;
  TiledSprite mCharacterSprite;
  Sprite mBGSprite;

  MainActivity mMainActivity;
  boolean won;

  public TouchRunnerMinigame() {
    mMainActivity = MainActivity.instance();
    loadResources();
  }

  public void loadResources() {
    this.won = false;

    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

    BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(
        mMainActivity.getTextureManager(), 1024, 1024,
        BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR);

    this.mButtonARegion = BitmapTextureAtlasTextureRegionFactory
        .createTiledFromAsset(mBuildableBitmapTextureAtlas, mMainActivity,
            "button_a.png", 2, 1);

    this.mButtonBRegion = BitmapTextureAtlasTextureRegionFactory
        .createTiledFromAsset(mBuildableBitmapTextureAtlas, mMainActivity,
            "button_b.png", 2, 1);

    this.mCharacterRegion = BitmapTextureAtlasTextureRegionFactory
        .createTiledFromAsset(mBuildableBitmapTextureAtlas, mMainActivity,
            "charas.png", 12, 8);

    this.mBGRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
        mBuildableBitmapTextureAtlas, mMainActivity, "track.png");

    try {
      mBuildableBitmapTextureAtlas
          .build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
              0, 1, 1));
    } catch (TextureAtlasBuilderException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    mBuildableBitmapTextureAtlas.load();

    this.aSprite = new ButtonSprite(60 + 2 * 110, 60 + 110,
        this.mButtonARegion, mMainActivity.getVertexBufferObjectManager()) {
      @Override
      public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
          float pTouchAreaLocalX, float pTouchAreaLocalY) {

        if (pSceneTouchEvent.isActionDown()) {
          if (getCurrentTileIndex() == 0) {
            setCurrentTileIndex(1);
            TouchRunnerMinigame.this.bSprite.setCurrentTileIndex(0);
          }
        }

        return true;
      }
    };

    this.bSprite = new ButtonSprite(60 + 4 * 110, 60 + 110,
        this.mButtonBRegion, mMainActivity.getVertexBufferObjectManager()) {
      @Override
      public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
          float pTouchAreaLocalX, float pTouchAreaLocalY) {

        if (pSceneTouchEvent.isActionDown()) {
          if (getCurrentTileIndex() == 0) {
            setCurrentTileIndex(1);
            TouchRunnerMinigame.this.aSprite.setCurrentTileIndex(0);
            moveRunner();
          }
        }

        return true;
      }
    };

    this.bSprite.setCurrentTileIndex(1);

    this.mCharacterSprite = new TiledSprite(0, 345, this.mCharacterRegion,
        mMainActivity.getVertexBufferObjectManager());

    this.mBGSprite = new Sprite(200, 440, this.mBGRegion,
        mMainActivity.getVertexBufferObjectManager());

    int animationSize = 3;
    int numCharacters = 4;

    this.mCharacterSprite
        .setCurrentTileIndex(1 * animationSize * numCharacters);
    this.mCharacterSprite.setScale(2);

    mMainActivity.registerTouchArea(aSprite);
    mMainActivity.registerTouchArea(bSprite);
    this.attachChild(aSprite);
    this.attachChild(bSprite);
    this.attachChild(mBGSprite);
    this.attachChild(mCharacterSprite);

  }

  void moveRunner() {

    int animationSize = 3;
    int numCharacters = 4;

    if (!this.won) {
      this.mCharacterSprite.setX(this.mCharacterSprite.getX() + 30);
      this.mCharacterSprite.setCurrentTileIndex(1 * animationSize
          * numCharacters + ((mCharacterSprite.getCurrentTileIndex() + 1) % 3));
      if (this.mCharacterSprite.getX() > 400) {
        this.won = true;
        this.aSprite.setCurrentTileIndex(1);
        this.bSprite.setCurrentTileIndex(1);
      }
    }
  }

  @Override
  boolean[][] getField() {
    if (this.won)
      return FigureFactory.createBest(mMainActivity.mTetris.mField);
    else
      return FigureFactory.createWorst(mMainActivity.mTetris.mField);
  }

  @Override
  void quit() {
    // TODO Auto-generated method stub

  }

  @Override
  void start() {
    // TODO Auto-generated method stub

  }

}
