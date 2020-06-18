package ru.newmcpe.bhop.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.newmcpe.bhop.event.impl.OverlayEvent;

/**
 * Created by Babbaj on 12/17/2017.
 */
public class RenderUtils {
    private RenderUtils() {}

    public static void setScale(Batch batch, float scale) {
        batch.getTransformMatrix().setToScaling(scale, scale, 1);
    }



    public static void drawRect(OverlayEvent event, float x, float y, float width, float height) {

    }

   /* public static void drawTexture(OverlayEvent event, TextureRegion texture, float x, float y, float width, float height) {
        event.getBatch().begin();
        {
            event.getBatch().draw(texture, x, y, width, height);
        }
        event.getBatch().end();
    }

    public static void drawText(OverlayEvent event, CharSequence charSequence, float x, float y, float width, float height) {
        event.getBatch().begin();
        {
          // event.getFontRenderer().draw(event.getBatch(), charSequence, x, y, width, 0, true);
        }
        event.getBatch().end();
    }

    public static void drawTexture(OverlayEvent event, TextureRegion texture, float x, float y, float scale) {
        event.getBatch().begin();
        {
            setScale(event.getBatch(), scale);
            event.getBatch().draw(texture, x * (1 / scale), y * (1 / scale));
        }
        event.getBatch().end();
    }
*/
}
