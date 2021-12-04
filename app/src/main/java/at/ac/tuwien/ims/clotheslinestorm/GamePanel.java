package at.ac.tuwien.ims.clotheslinestorm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import java.util.HashMap;

import at.ac.tuwien.ims.clotheslinestorm.Clothes.Clothefactory;
import at.ac.tuwien.ims.clotheslinestorm.Clothes.GameSounds;

/**
 * @author Jan Kompatscher
 * @author Stefan Tobisch

 * */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
	private Clothefactory clothefactory;

    private ClotheBasket clotheBasket;
    private Point playerPoint;
    private Bitmap dirtyTee;
    private int difficulty;

    private Clotheline clotheline;
    private Bitmap background;

    private int screenHeight;
    private int screenWidth;

    public boolean game;
    public boolean pause = false;
    private boolean sound = true;

    private Bitmap pauseButton;
    private Bitmap resumeButton;
    private Rect pauseButtonrect;
    private Bitmap soundOnButton;
    private Bitmap soundOffButton;
    private Rect soundButtonrect;

    private Bitmap[] leaves = new Bitmap[14];
    private int frameByFrameAnimationCounter = 0;
    private Rect leaveBitmapRect;
    private Rect leaveDrawRect;

    private GameActivity gameActivity;

    private long lastUpdate = System.nanoTime();

    /**
     * Constructor: create new Gamepanel, set all the needed params
     * @author Jan Kompatscher
     * */
    public GamePanel(Context context, GameActivity gameActivity, Display screen, int level){
        super(context);
        this.gameActivity = gameActivity;

        getHolder().addCallback(this);

        GameSounds gameSounds = new GameSounds(context);
        difficulty = 2* (level-1);

        Point screenSize = new Point();
        screen.getSize(screenSize);
        screenHeight = screenSize.y;
        screenWidth = screenSize.x;

        thread = new MainThread(getHolder(),this);
        dirtyTee = BitmapFactory.decodeResource(context.getResources(), R.drawable.dirytee);
        clotheBasket = new ClotheBasket(context, new Rect(0,0,0,0));

        switch(level) {
            case 1:
                clotheline = new Clotheline(context, 1);
                //note that there cant be a higher number of clothes than numberOfLines * 7
                clothefactory = new Clothefactory(this, clotheBasket, 7, 1, screenWidth, screenHeight, gameSounds);
                background = BitmapFactory.decodeResource(context.getResources(), R.drawable.jungle); break;
                //Source: https://www.pexels.com/photo/green-leaf-trees-904807/ 22.01.19
            case 2:
                clotheline = new Clotheline(context, 2);
                //note that there cant be a higher number of clothes than numberOfLines * 7
                clothefactory = new Clothefactory(this, clotheBasket, 14, 2, screenWidth, screenHeight, gameSounds);
                background = BitmapFactory.decodeResource(context.getResources(), R.drawable.snow); break;
                //Source: https://pixabay.com/de/schnee-wald-winter-natur-b%C3%A4ume-1902052/ 22.01.19
            case 3:
                clotheline = new Clotheline(context, 3);
                //note that there cant be a higher number of clothes than numberOfLines * 7
                clothefactory = new Clothefactory(this, clotheBasket, 21, 3, screenWidth, screenHeight, gameSounds);
                background = BitmapFactory.decodeResource(context.getResources(), R.drawable.city); break;
                //Source: https://pixabay.com/de/gasse-stra%C3%9Fe-stadt-stadtbild-863687/ 22.01.19
        }
        pauseButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause);
        resumeButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.resume);
        pauseButtonrect = new Rect(screenWidth*17/20, screenWidth/20, screenWidth*19/20, screenWidth*3/20);
        soundOnButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.sound_on_button);
        soundOffButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.sound_off_button);
        soundButtonrect = new Rect(screenWidth*14/20, screenWidth/20, screenWidth*16/20, screenWidth*3/20);
        playerPoint = new Point(200, screenHeight - 300);

        loadLeaves(context);
        leaveDrawRect = new Rect(0,screenHeight*2/5,screenWidth,screenHeight*9/10);

        setFocusable(true);
        game = true;
    }


    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height){

    }

    /**
     * starts the thread
     * @author Jan Kompatscher
     * */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder){
            thread = new MainThread(getHolder(), this);
            thread.setRunning(true);
            thread.start();
    }

    /**
     * if the surface is destroyed, the thread is stopped
     * @author Jan Kompatscher
     * */
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder){
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(Exception e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    /**
     * detects if clothebasket is toched (and updates it) and if one of the buttons is toched
     * @author Jan Kompatscher
     * */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN :
                if(pauseButtonrect.contains((int)event.getX(), (int)event.getY())){
                    pauseGame();
                    return false;
                }
                if(soundButtonrect.contains((int)event.getX(), (int)event.getY())){
                    toggleSound();
                    return false;
                }
                if(!clotheBasket.inside(new Point((int)event.getX(), (int)event.getY()))){
                    return false;
                }
            case MotionEvent.ACTION_MOVE :
                    playerPoint.set((int) event.getX(), playerPoint.y);
        }
        return true;
    }

    /**
     * Updates all the updatable ingame objects
     * @author Jan Kompatscher
     * */
    public void update(){
        if(!pause) {
            float timeDiff = System.nanoTime() - lastUpdate;
            long framesToJump = (long)(30 * timeDiff/1000000000.0f);
            clotheBasket.update(playerPoint);
            clothefactory.updateAllClothes(framesToJump, sound);
            lastUpdate = System.nanoTime();
            if(clothefactory.getWetClothes() > difficulty){
                gameActivity.lostGame();
            }else if(clothefactory.getHangingClothes() == 0){
                gameActivity.wonGame(clothefactory.getPoints());
            }
        }
    }

    /**
     * draw method, here all the things that will be visible in the game are drawn on the screen, gets called every frame
     * @author Jan Kompatscher
     * */
    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        super.draw(canvas);

        canvas.drawBitmap(background,
                          new Rect(0, 0, background.getWidth(), background.getHeight()),
                          new Rect(0, 0, canvas.getWidth(), canvas.getHeight()),
                          paint);
        if(!pause) {
            if (frameByFrameAnimationCounter < 26) {
                frameByFrameAnimationCounter++;
            } else {
                frameByFrameAnimationCounter = 0;
            }
        }
        Paint leavePaint = new Paint();
        //draw the leaves being blown around
        drawLeaves(frameByFrameAnimationCounter/2, canvas, leavePaint);

        //draw the number of dirty pieces
        Paint writingPaint = new Paint();
        writingPaint.setColor(Color.WHITE);
        writingPaint.setTextSize(100);
        canvas.drawText(clothefactory.getWetClothes() + "/" + difficulty, 180, 95, writingPaint);
        Paint dirtyTeePaint = new Paint();
        canvas.drawBitmap(dirtyTee,
                new Rect(0, 0, dirtyTee.getWidth(), dirtyTee.getHeight()),
                new Rect(60, 10, 150, 110),
                dirtyTeePaint);

        //clotheline.draw(canvas);
        clothefactory.drawAllClothes(canvas, pause);
        clotheBasket.draw(canvas);
        Paint pauseButtonPaint = new Paint();
        if(!pause) {
            canvas.drawBitmap(pauseButton, new Rect(0, 0, pauseButton.getWidth(), pauseButton.getHeight()), pauseButtonrect, pauseButtonPaint);
        }else{
            canvas.drawBitmap(resumeButton, new Rect(0, 0, pauseButton.getWidth(), pauseButton.getHeight()), pauseButtonrect, pauseButtonPaint);
        }
        Paint soundButtonPaint = new Paint();
        if(sound) {
            canvas.drawBitmap(soundOnButton, new Rect(0, 0, soundOnButton.getWidth(), soundOnButton.getHeight()), soundButtonrect, soundButtonPaint);
        }else{
            canvas.drawBitmap(soundOffButton, new Rect(0, 0, soundOffButton.getWidth(), soundOffButton.getHeight()), soundButtonrect, soundButtonPaint);
        }
    }

    /**
     * Draws the leaves on the canvas
     * @author Jan Kompatscher
     * */
    private void drawLeaves(int whichFrame, Canvas canvas, Paint paint){
        canvas.drawBitmap(leaves[whichFrame], leaveBitmapRect, leaveDrawRect, paint);
    }
    /**
     * Loads all the Bitmaps for the frame-by-frame animation of the leaves, gets called once
     * @author Jan Kompatscher
     * */
    private void loadLeaves(Context context){
            //Source: http://animal-jam-clans.wikia.com/wiki/File:Leaves.gif 22.01.19
            leaves[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.leaves00);
            leaves[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.leaves01);
            leaves[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.leaves02);
            leaves[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.leaves03);
            leaves[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.leaves04);
            leaves[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.leaves05);
            leaves[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.leaves06);
            leaves[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.leaves07);
            leaves[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.leaves08);
            leaves[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.leaves09);
            leaves[10] = BitmapFactory.decodeResource(context.getResources(), R.drawable.leaves10);
            leaves[11] = BitmapFactory.decodeResource(context.getResources(), R.drawable.leaves11);
            leaves[12] = BitmapFactory.decodeResource(context.getResources(), R.drawable.leaves12);
            leaves[13] = BitmapFactory.decodeResource(context.getResources(), R.drawable.leaves13);

            leaveBitmapRect = new Rect(0,0,leaves[0].getWidth(),leaves[0].getHeight());
    }
    /**
     * Sound stops when game on pause
     * @author Jan Kompatscher
     * @author Stefan Tobisch
     * */
    public void pauseGame(){
        pause = !pause;
        gameActivity.soundOff();
        if(!pause){
            lastUpdate = System.nanoTime();
            if(!sound){
                gameActivity.soundOff();
            }else{
                gameActivity.soundOn();
            }
        }
    }
    /**
     * controls sound when game is running or on pause
     * @author Jan Kompatscher
     * @author Stefan Tobisch
     * */
    public void toggleSound(){
        sound = !sound;
        if(!sound){
            gameActivity.soundOff();
        }else{
            if(!pause){
                gameActivity.soundOn();
            }
        }
    }
    /**
     * end the thread when game is over, to free ram
     * @author Jan Kompatscher
     * */
    public void gameOver(){
        thread.setRunning(false);
    }
}
