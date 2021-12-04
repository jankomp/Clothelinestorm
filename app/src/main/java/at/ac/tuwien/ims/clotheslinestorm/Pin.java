package at.ac.tuwien.ims.clotheslinestorm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * the pins
 * @author Jan Kompatscher
 * */
public class Pin implements GameObject {
    private Bitmap pinPic;
    public Rect rectangle;
    public Point position;
    public double fallingTime;
    public boolean here = true;

    public Pin(Context context, Point position, int expiryTime, boolean bluepin){
        if(bluepin) {
            pinPic = BitmapFactory.decodeResource(context.getResources(), R.drawable.pin_2);
        } else {
            pinPic = BitmapFactory.decodeResource(context.getResources(), R.drawable.pin_1);
        }
        //Source: https://www.vecteezy.com/vector-art/90753-clothespin-vector-set 22.01.19

        this.position = position;
        rectangle = new Rect(position.x - 16, position.y - 32, position.x + 16, position.y + 32);
        fallingTime = expiryTime * 800.0;
    }

    /**
     * draw the pins
     * @author Jan Kompatscher
     * */
    public void draw(Canvas canvas){
        if(here) {
            Paint paint = new Paint();
            canvas.drawBitmap(pinPic, new Rect(0, 0, pinPic.getWidth(), pinPic.getHeight()), rectangle, paint);
        }
    }

    /**
     * update the pins
     * @author Jan Kompatscher
     * */
    public void update(int drawCounter){
        if(drawCounter * 1000/30 > fallingTime){
            here = false;
        }
    }

    public void update(){}
}
