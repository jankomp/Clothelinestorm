package at.ac.tuwien.ims.clotheslinestorm.Clothes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

import at.ac.tuwien.ims.clotheslinestorm.ClotheBasket;
import at.ac.tuwien.ims.clotheslinestorm.GameObject;
import at.ac.tuwien.ims.clotheslinestorm.Pin;
import at.ac.tuwien.ims.clotheslinestorm.R;

/**
 * a single piece of laundry
 * @author Jan Kompatscher
 * */

public class ClothingPiece implements GameObject {
    private Bitmap clothes;
    private Rect rectangle;
    private Point position;
    private boolean falling;
    private int numberOfPins;
    private Pin pin1;
    private Pin pin2;
    public boolean wet = false;
    public boolean caught = false;
    private int worth;
    private int drawCounter;
    private GameSounds gameSounds;

    private float widthfactor;
    private float heightfactor;

    private int screenHeight;

    private ClotheBasket basket;

    private Context context;

    public static final int SOUND_FALL = 1;
    public static final int SOUND_MUD = 2;
    public static final int SOUND_CAUGHT = 3;

    // hr, hl hanging left and hanging right ~ where does the image of the hanging clothepiece start, where does it end
    //same for top and bottom
    private int hr;
    private int hl;
    private int ht = 0;
    private int hb = 139;

    // fr, fl falling left and falling right ~ where does the image of the falling clothepiece start, where does it end
    //same for top and bottom
    private int fr;
    private int fl;
    private int ft = 0;
    private int fb = 139;

    private int hheight = 200;
    private int hwidth = 160;

    private int fheight = 200;
    private int fwidth = 160;

    Point pin1point;
    Point pin2point;

    /**
     * create the desired kind of clothing and create also the pins
     * @author Jan Kompatscher
     * */
    public ClothingPiece(Context context,  int screenheight, ClotheBasket basket,Point position, int pin1expiryTime, int pin2expiryTime, ClothingKind kind, int worth, GameSounds gameSounds){
        this.gameSounds = gameSounds;
        this.position = position;
        this.screenHeight = screenheight;
        this.basket = basket;
        this.worth = worth;
        this.context = context;
        pin1point = new Point(position.x - (hwidth/2 - hwidth/8), position.y - hheight/2);
        pin2point = new Point(position.x + (hwidth/2 - hwidth/8), position.y - hheight/2);

        clothes = BitmapFactory.decodeResource(context.getResources(), R.drawable.debora);
        //Source: Originalbild von http://darlenefranklinwrites.com/clothes-cartoon/  22.01.2019

        widthfactor = clothes.getWidth() / 2400.0f;
        heightfactor = clothes.getHeight() / 140.0f;
        numberOfPins = 2;
        falling = false;

        /*
        switch (kind){
            case    BLUEHAT: hl = fl =  2160; ht = ft = 34; hr = fr = 2204; hb = fb = 94; fheight = hheight = 120; fwidth = hwidth = 100; numberOfPins = 1; position.y -= hheight/3; pin1point = new Point(position.x, position.y - hheight/2);break;
            case    BLUEPANTS: hl = fl =  1694; hr = fr = 1781; this.position.y -= hheight/12; pin1point = new Point(position.x - hwidth/4, position.y - hheight/2); pin2point = new Point(position.x + hwidth/4, position.y - hheight/2);                         break;
            case    GREENSHIRT: hl = 700; hr = 825; fl = 838; fr = 956; this.position.y -= hheight/5;   pin1point = new Point(position.x + hwidth/4, position.y - hheight/5); pin2point = new Point(position.x - hwidth/4, position.y - hheight/5);                break;
            case    lEFTSHOE: hl = 1012; ht = 35; hr = 1055; hb = 122; fl = 1096; ft = 78; fr = 1155; fb = 112; hheight = fheight = 100; hwidth = fwidth = 80;  numberOfPins = 1; this.position.y -= hheight/3; pin1point = new Point(position.x - hwidth/8, position.y - hheight/2);break;
            case    RIGHTSHOE: hl = 1282; ht = 35; hr = 1324; hb = 122; fl = 1139; ft = 78; fr = 1248; fb = 112; hheight = fheight = 100; hwidth = fwidth = 80;  numberOfPins = 1; this.position.y -= hheight/3; pin1point = new Point(position.x + hwidth/6, position.y - hheight/2);break;
            case    PURPLESKIRT: hl = fl =  2285; hr = fr = 2379; this.position.y -= hheight / 10; pin1point.x += hwidth/3; pin2point.x -= hwidth/5;                          break;
            case    REDHOODIE: hl = 397; hr = 520; fl = 540; fr = 682; this.position.y -= hheight/3; pin1point = new Point(position.x + hwidth/4, position.y - hheight/5); pin2point = new Point(position.x - hwidth/4, position.y - hheight/5);                   break;
            case    REDSHIRT: hl = 0; hr = 122; fl = 122; fr = 268; this.position.y -= hheight/4; pin1point = new Point(position.x + hwidth/5, position.y - hheight/3); pin2point = new Point(position.x - hwidth/5, position.y - hheight/3);                 break;
            case    VIOLETSHIRT: hl = fl = 268; hr = fr = 388; this.position.y -= hheight/5; pin1point = new Point(position.x + hwidth/5, position.y - hheight/3); pin2point = new Point(position.x - hwidth/5, position.y - hheight/3);                           break;
            case    WHITESHIRT: hl = 1834; hr = 1942; fl = 1980; fr = 2095; this.position.y -= hheight/6;   pin1point = new Point(position.x + hwidth/4, position.y - hheight/4); pin2point = new Point(position.x - hwidth/4, position.y - hheight/4);                  break;
            case    YELLOWJACKET: hl = 1391; hr = 1502; fl = 1544; fr = 1656; this.position.y -= hheight/3; pin1point = new Point(position.x + hwidth/4, position.y - hheight/4); pin2point = new Point(position.x - hwidth/4, position.y - hheight/4); break;
        }
        */
        hl = 0; fl =  394; ht = ft = 0; hr = 394; fr = 588; hb = fb = 394; fheight = hheight = 200; fwidth = hwidth = 200; numberOfPins = 1; pin1point = new Point(position.x, position.y - hheight/2);

        hl *= widthfactor; fl *= widthfactor;
        hr *= widthfactor; fr *= widthfactor;
        ht *= heightfactor; ft *= heightfactor;
        hb *= heightfactor; fb *= heightfactor;

        rectangle = new Rect(position.x - hwidth / 2,
                position.y - hheight / 2,
                position.x + hwidth / 2,
                position.y + hheight / 2);

        if(numberOfPins == 1){
            pin1 = new Pin(context, pin1point, pin1expiryTime, true);
        } else {
            pin1 = new Pin(context, pin1point,pin1expiryTime, false);
            pin2 = new Pin(context, pin2point,pin2expiryTime, true);
        }

    }

    /**
     * draw the piece
     * @author Jan Kompatscher
     * */
    public void draw(Canvas canvas, int drawCounter) {
        if(!caught) {
            Paint paint = new Paint();
            if (!falling) {
                canvas.drawBitmap(clothes,
                        new Rect(hl, ht, hr, hb),
                        rectangle,
                        paint);

                if (pin1 != null) {
                    pin1.draw(canvas);
                }
                if (pin2 != null) {
                    pin2.draw(canvas);
                }
            } else {
                canvas.drawBitmap(clothes,
                        new Rect(fl, ft, fr, fb),
                        rectangle,
                        paint);
            }
        }
        this.drawCounter = drawCounter;
    }


    /**
     * update the piece
     * @author Jan Kompatscher
     * */
    public void update(long framesToJump, boolean sound) {
        if (position.y <= basket.height) {
            if (!falling) {

                //shoes and hat are only hung up on one pin the others all on two
                if (numberOfPins == 1) {
                    if (pin1.here) {
                        pin1.update(drawCounter);
                    } else //if the pin is not here anymore the clothingpiece starts falling
                    {
                        falling = true;
                        if(sound) {
                            gameSounds.playSound(SOUND_FALL);
                        }
                    }
                }
                if (numberOfPins == 2) {
                    if (pin1.here || pin2.here) {
                        pin1.update(drawCounter);
                        pin2.update(drawCounter);
                    }
                    if (!pin1.here && !pin2.here) {
                        falling = true;
                        if(sound) {
                            gameSounds.playSound(SOUND_FALL);
                        }
                    }
                }
            }
        } else {
            if (!wet && !caught) {
                if (basket.inside(new Point(position.x, basket.height))) {
                    caught = true;
                    falling = false;
                    if(sound) {
                        gameSounds.playSound(SOUND_CAUGHT);
                    }
                } else {
                    wet = true;
                    if(sound) {
                        gameSounds.playSound(SOUND_MUD);
                    }
                }

            } else if (wet) {
                if (position.y > screenHeight + fheight / 2) {
                    falling = false;
                }
            }
        }
        if (falling) {
            position.y += 15 * heightfactor * framesToJump;
            rectangle.set(position.x - fwidth / 2,
                    position.y - fheight / 2,
                    position.x + fwidth / 2,
                    position.y + fheight / 2);
        }

    }

    /**
     * return the points for the piece (only if it's caught)
     * @author Jan Kompatscher
     * */
    public int getPoints(){
        if(caught){
            return worth;
        }else {
            return 0;
        }
    }

    public void update(){}

    public void draw(Canvas canvas){}
}
