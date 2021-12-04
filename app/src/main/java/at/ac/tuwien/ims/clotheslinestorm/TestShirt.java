package at.ac.tuwien.ims.clotheslinestorm;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * @author Jan Kompatscher
 **/

public class TestShirt implements GameObject {
    private Rect rect;
    private Bitmap shirt;

    public TestShirt(Context context, Rect rect){
        this.rect = rect;
        shirt = BitmapFactory.decodeResource(context.getResources(), R.drawable.violetshirt);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();

        canvas.drawBitmap(shirt,
                new Rect(0, 0, 88, 95),
                rect,
                paint);
    }

    @Override
    public void update() {

    }

    //point is the centre of the rectangle of our clothebaket
    public void update(Point point){
        //l,t,r,b
        rect.set(point.x - 120,
                point.y - 110,
                point.x + 120,
                point.y + 110);
    }

}
