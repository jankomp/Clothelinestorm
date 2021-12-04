package at.ac.tuwien.ims.clotheslinestorm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * our player
 * @author Jan Kompatscher
 * */

public class ClotheBasket implements GameObject {
    private Rect rect;
    private Bitmap basket;

    public int height;

    public ClotheBasket(Context context, Rect rect){
        this.rect = rect;
        basket = BitmapFactory.decodeResource(context.getResources(), R.drawable.clothesbasket);
        //Source: https://www.google.com/imgres?imgurl=https://www.beautyofwater.org/media/2018/09/07/clip-art-laundry-bin-free-s-vectors-and-stock-heavy-photos-image-heavy-laundry_15f4f10b26135e4b.jpg&imgrefurl=https://www.tollebild.com/bilden/dirty-clothes-hamper-clipart-ef.html&h=1264&w=1185&tbnid=vUHUENXGHPyhaM&tbnh=232&tbnw=217&usg=K_VTuTpbpOdYYzWUTxL4jOtwuwqZI=&hl=de-AT&docid=OGj0S_-uAdgzVM&itg=1 22.01.19
    }

    /**
     * draw the player
     * @author Jan Kompatscher
     * */
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();

        canvas.drawBitmap(basket,
                          new Rect(0, 0, basket.getWidth(), basket.getHeight()),
                          rect,
                          paint);
    }

    @Override
    public void update() {

    }

    /**
     * update the player-point
     * @author Jan Kompatscher
     * */
    public void update(Point point){
        //l,t,r,b
        rect.set(point.x - 120,
                point.y - 110,
                point.x + 120,
                point.y + 110);

        height = point.y - 110;
    }

    /**
     * is a point inside our clothebasket?
     * @author Jan Kompatscher
     * */
    public boolean inside(Point point){
        if(point.x >= rect.left && point.x <= rect.right && point.y >= rect.top && point.y <= rect.bottom){
            return true;
        }else{
            return false;
        }
    }

}
