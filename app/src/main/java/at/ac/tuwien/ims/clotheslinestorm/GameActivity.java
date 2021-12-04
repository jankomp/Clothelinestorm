package at.ac.tuwien.ims.clotheslinestorm;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import android.media.MediaPlayer;
import android.view.View;
import android.content.Intent;

/**
* Activity during the game
*
* @author Stefan Tobisch
* @author Jan Kompatscher
* */


public class GameActivity extends Activity {

    double startTime;

    MediaPlayer mediaPlayer;

    GamePanel gamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide the title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Bundle b = getIntent().getExtras();
        int level = -1; // or other values
        if(b != null)
            level = b.getInt("level");

        Display display = getWindowManager().getDefaultDisplay();
        gamePanel = new GamePanel(this, this, display, level);
        setContentView(gamePanel);

        //Hintergrundsound
        mediaPlayer = MediaPlayer.create(this, R.raw.wind);     //Hintergrundsound Wind
        mediaPlayer.start();        //startet Hintergrundsound
        mediaPlayer.setLooping(true);       //loop für Hintergrundsound aktiviert
    }

    /**
    * release ressource when game is paused
    * @author Stefan Tobisch
    * */
    public void pausePushed(View view){
        gamePanel.pause = true;
        mediaPlayer.release();
    }

    /**
    * When game is lost the mediaplayer stops, release ressource and switch to gameLostActivity
    * @author Stefan Tobisch
    * */
    public void lostGame(){
        gamePanel.gameOver();
        mediaPlayer.stop();
        mediaPlayer.release();
        finish();
        Intent gamelostIntent = new Intent (this, GameOverActivity.class);
        startActivity(gamelostIntent);
    }
    /**
    * if game is won, calls the methods to end the game, release ressource and opens the gamewonintent and displays the points
    *@author Stefan Tobisch
    * @author Jan Kompatscher
    * */
    public void wonGame(int points){
        gamePanel.gameOver();
        mediaPlayer.stop();
        mediaPlayer.release();
        finish();
        Intent gamewonIntent = new Intent (this, GameWonActivity.class);

        Bundle b = new Bundle();
        b.putInt("points", points); //Your id
        gamewonIntent.putExtras(b);
        startActivity(gamewonIntent);
    }
    /**
    *when pushing soundOn-Button
    *@author Stefan Tobisch
    * */
    public void soundOn(){
        mediaPlayer = MediaPlayer.create(this, R.raw.wind);     //Hintergrundsound Wind
        mediaPlayer.start();        //startet Hintergrundsound
        mediaPlayer.setLooping(true);       //loop für Hintergrundsound aktiviert
    }

    /**
    *when pushing soundOff-Button
    * @author Stefan Tobisch
    * */
    public void soundOff(){
        mediaPlayer.stop();
    }
}
