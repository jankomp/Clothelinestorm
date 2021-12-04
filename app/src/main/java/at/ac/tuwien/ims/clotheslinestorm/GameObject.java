package at.ac.tuwien.ims.clotheslinestorm;

import android.graphics.Canvas;

/**
 * the class which all our gameobjects extend
 * @author Jan Kompatscher
 * */

public interface GameObject {
    public void draw(Canvas canvas);
    public void update();
}
