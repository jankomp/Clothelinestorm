package at.ac.tuwien.ims.clotheslinestorm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * the horizontal lines seen in our game
 * @author Jan Kompatscher
 * */

public class Clotheline implements GameObject {
    int height1;
    int height2;
    int height3;
    int numberOfLines;
    Bitmap clotheline;

    public Clotheline(Context context, int number) {
        clotheline = BitmapFactory.decodeResource(context.getResources(), R.drawable.clotheline);
        numberOfLines = number;
    }

    /**
     * draw the desired number of lines
     * @author Jan Kompatscher
     * */
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();

        height1 = canvas.getHeight()/8;
        height2 = canvas.getHeight()*3/8;
        height3 = canvas.getHeight()*5/8;

        int bitmapHeight = canvas.getHeight()/10;


        canvas.drawBitmap(clotheline,
                          new Rect(0,0, clotheline.getWidth(), clotheline.getHeight()),
                          new Rect(0, height1 - bitmapHeight*3/2, canvas.getWidth(),height1 + bitmapHeight/2),
                          paint);
        if(numberOfLines > 1) {
            canvas.drawBitmap(clotheline,
                    new Rect(0, 0, clotheline.getWidth(), clotheline.getHeight()),
                    new Rect(0, height2 - bitmapHeight * 3 / 2, canvas.getWidth(), height2 + bitmapHeight / 2),
                    paint);
        }
        if(numberOfLines > 2) {
            canvas.drawBitmap(clotheline,
                    new Rect(0, 0, clotheline.getWidth(), clotheline.getHeight()),
                    new Rect(0, height3 - bitmapHeight * 3 / 2, canvas.getWidth(), height3 + bitmapHeight / 2),
                    paint);
        }
    }

    @Override
    public void update() {
        //once hung up the clotheline never changes position
    }
}
