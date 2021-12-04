package at.ac.tuwien.ims.clotheslinestorm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * this activity shows the MainMenu
 * @author Stefan Tobisch
 * **/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    /**
     * @author Stefan Tobisch
    * pushing the exitButton ends the game
    * */
    public void exitButtonclick(View view) {
        this.finish();
    }
    /**
     * pushing the StartButton finishs the current intent and starts the next screen with the levels to choose
     * @author Stefan Tobisch
     * */
    public void nextButtonClick(View view) {
        finish();
        Intent levelsIntent = new Intent ( this, Levels.class);
        startActivity(levelsIntent);
    }
    /**
     * pushing the highscoreButton finishs MainActivity and starts HighscoreActivity
     * @author Stefan Tobisch
     * */
    public void highscoreButtonClick(View view) {
        finish();
        Intent highscoreIntent = new Intent (this, HighscoreActivity.class);
        startActivity(highscoreIntent);
    }
    /**
     * pushing the HelpButton finishs MainActivity and starts HelpActivity
     * @author Stefan Tobisch
     * */
    public void helpButtonClick(View view) {
        finish();
        Intent helpIntent = new Intent (this, HelpActivity.class);
        startActivity(helpIntent);
    }
}
