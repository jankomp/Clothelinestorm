package at.ac.tuwien.ims.clotheslinestorm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * the activity where the user chooses which level to play
 * @author Jan Kompatscher
 * */

public class Levels extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
    }

    public void level1ButtonClick(View view) {
        finish();
        Intent gameIntent = new Intent ( this, GameActivity.class);
        Bundle b = new Bundle();
        b.putInt("level", 1); //Your id
        gameIntent.putExtras(b);
        startActivity(gameIntent);
    }

    public void level2ButtonClick(View view) {
        finish();
        Intent gameIntent = new Intent ( this, GameActivity.class);
        Bundle b = new Bundle();
        b.putInt("level", 2); //Your id
        gameIntent.putExtras(b);
        startActivity(gameIntent);
    }

    public void level3ButtonClick(View view) {
        finish();
        Intent gameIntent = new Intent ( this, GameActivity.class);
        Bundle b = new Bundle();
        b.putInt("level", 3); //Your id
        gameIntent.putExtras(b);
        startActivity(gameIntent);
    }
}
