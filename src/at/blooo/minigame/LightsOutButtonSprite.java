package at.blooo.minigame;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

//TODO maybe there's a better solution
public class LightsOutButtonSprite extends ButtonSprite {

  public int x;
  public int y;
  
  
  public LightsOutButtonSprite(float pX, float pY,
      ITiledTextureRegion pTiledTextureRegion, int x, int y,
      VertexBufferObjectManager pVertexBufferObjectManager) {

    super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);

    this.x = x;
    this.y = y;
  }

}
