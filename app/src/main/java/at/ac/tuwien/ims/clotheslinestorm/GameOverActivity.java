package at.ac.tuwien.ims.clotheslinestorm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
* this class represents the GameOver
* @author Stefan Tobisch
* */

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        /*
        * the .png-files "game" and "over"
        * */
        ImageView imageOneView = (ImageView)findViewById(R.id.imageView3);
        ImageView imageTwoView = (ImageView)findViewById(R.id.imageView4);
        /*
        * animation on the Imagefiles
        * */
        imageOneView.startAnimation(AnimationUtils.loadAnimation(GameOverActivity.this, R.anim.translate));
        imageTwoView.startAnimation(AnimationUtils.loadAnimation(GameOverActivity.this, R.anim.translate_two));
    }

    /**
    * pushing the button finishs the intent and start the MainActivity
    * @author Stefan Tobisch
    * */
    public void menuButtonClick(View view) {
        finish();
        Intent gameIntent = new Intent ( this, MainActivity.class);
        startActivity(gameIntent);
    }
}


