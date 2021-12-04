package at.ac.tuwien.ims.clotheslinestorm.Clothes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.LinkedList;
import java.util.List;

import at.ac.tuwien.ims.clotheslinestorm.ClotheBasket;
import at.ac.tuwien.ims.clotheslinestorm.GamePanel;

/**
 * to make and update all the clothes
 * @author Jan Kompatscher
 * */

public class Clothefactory {

    private List<ClothingPiece> allClothes = new LinkedList<ClothingPiece>();
    private int wetClothes = 0;
    int hangingClothes;
    private int totalClothes;
    private int points = 0;
    private int drawCounter = 0;

    /**
     * randomly decide which kinds of clothes in which order are hung up
     * @author Jan Kompatscher
     * */
    public Clothefactory(GamePanel gamePanel, ClotheBasket basket, int numberofClothes, int numberOfLines, int screenwidth, int screenheight, GameSounds gameSounds){
        int height = 0;
        int xPos = 0;
        totalClothes = hangingClothes = numberofClothes;
        boolean[][] takenPlaces = new boolean[numberOfLines][7];
        int pin1expiryTime = 0;
        int pin2expiryTime = 0;
        boolean[] takenpinExpiryTimes = new boolean[2*(numberofClothes+1)];
        Context context = gamePanel.getContext();

        for (int i = 0; i < numberofClothes; i++) {
            int typeRandom = (int)(Math.random()*11);
            ClothingPiece clothingPiece = null;


            //randomly decide on which clotheline to hang
            height = (int)(Math.random()*numberOfLines);

            //randomly decide where on the line to hang it
            xPos = (int)(Math.random()*7);

            while(takenPlaces[height][xPos]){
                height = (int)(Math.random()*numberOfLines);
                xPos = (int)(Math.random()*7);
            }

            takenPlaces[height][xPos] = true;

            //randomly decide when the pins will dissappear
            do{
                pin1expiryTime = (int)(Math.random()*2*(numberofClothes + 1));
            }
            while(takenpinExpiryTimes[pin1expiryTime]);
            takenpinExpiryTimes[pin1expiryTime] = true;
            do{
                pin2expiryTime = (int)(Math.random()*2*(numberofClothes + 1));
            }
            while(takenpinExpiryTimes[pin2expiryTime]);
            takenpinExpiryTimes[pin2expiryTime] = true;


            int offset = screenwidth / 8;

            int x = (xPos+1)* offset;

            //to hang the clothes on the curve of the clotheline:
            int yHangHeight = (x - screenwidth*11/20)*(x - screenwidth*11/20) / (screenheight*4/3);

            int y = screenheight /6 + (height) * screenheight/4 - yHangHeight;
            Point clothePoint = new Point(x , y);

            int worth = (height + 1) * 500;


            //randomly decide what kind of clothing to hang up
            ClothingKind kind = ClothingKind.RIGHTSHOE;

            switch (typeRandom) {
                case 0 : kind = ClothingKind.BLUEHAT; break;
                case 1 : kind = ClothingKind.BLUEPANTS; break;
                case 2 : kind = ClothingKind.GREENSHIRT; break;
                case 3 : kind = ClothingKind.lEFTSHOE; break;
                case 4 : kind = ClothingKind.RIGHTSHOE; break;
                case 5 : kind = ClothingKind.PURPLESKIRT; break;
                case 6 : kind = ClothingKind.REDHOODIE; break;
                case 7 : kind = ClothingKind.REDSHIRT; break;
                case 8 : kind = ClothingKind.VIOLETSHIRT; break;
                case 9 : kind = ClothingKind.WHITESHIRT; break;
                case 10 :kind = ClothingKind.YELLOWJACKET; break;
            }

            clothingPiece = new ClothingPiece(context, screenheight, basket, clothePoint, pin1expiryTime, pin2expiryTime, kind, worth, gameSounds);

            allClothes.add(clothingPiece);
        }

    }

    /**
     * draw all the clothes
     * @author Jan Kompatscher
     * */
    public void drawAllClothes(Canvas canvas, boolean pause){
        for(ClothingPiece c : allClothes){
            c.draw(canvas, drawCounter);
        }
        if(!pause) {
            drawCounter++;
        }
    }

    /**
     * update all the clothes
     * @author Jan Kompatscher
     * */
    public void updateAllClothes(long framesToJump, boolean sound){
        wetClothes = 0;
        points = 0;
        hangingClothes = totalClothes;

        for(ClothingPiece c : allClothes) {
            c.update(framesToJump, sound);
            if (c.wet) {
                wetClothes++;
            }
            if (c.wet || c.caught){
                hangingClothes--;
            }
            points += c.getPoints();
        }
    }

    public int getWetClothes(){
        return wetClothes;
    }

    public int getHangingClothes(){
        return hangingClothes;
    }

    public int getPoints(){
        return points;
    }
}
