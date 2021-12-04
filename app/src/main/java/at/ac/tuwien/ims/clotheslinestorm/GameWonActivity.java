package at.ac.tuwien.ims.clotheslinestorm;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This Activity shows the game won-class, showing points and enter username
 * @author Stefan Tobisch
 * **/


public class GameWonActivity extends AppCompatActivity {

    //EditText for writing the username
    private EditText nameEditText;
    private int scoreToSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        int points = -1; // or other values
        if(b != null)
            points = b.getInt("points");

        setContentView(R.layout.activity_game_won);

        nameEditText = findViewById(R.id.editText);
        /*
        * Animation for the point result
        * */
        TextView textView = findViewById(R.id.score);
        if(textView != null)
            textView.setText("Score: " + points);
            textView.startAnimation(AnimationUtils.loadAnimation(GameWonActivity.this, R.anim.pulse));

        scoreToSave = points;

        ImageView imageThreeView = (ImageView)findViewById(R.id.imageView5);
        /*
        * Animation for the "gameWon".png
        * */
        imageThreeView.startAnimation(AnimationUtils.loadAnimation(GameWonActivity.this, R.anim.rotate));
    }
    /**
     * save the score with points and username
     * @author Stefan Tobisch
     * */
    public void saveScore(View view) {
        /*
        * enter username
        * */
        String username = nameEditText.getText().toString();

        final Score scoreRoom = new Score();        //new score-entity

        //no save for scores without a username
        if(username.isEmpty()){
            return;
        }
        scoreRoom.setUsername(username);
        scoreRoom.setScore(scoreToSave);

        // run database action in background. don't block UI thread
        new AsyncTask<Void, Void, Long>() {     //new thread
            @Override
            protected Long doInBackground(Void... voids) { //what happens in the new thread
                return AppRoomDatabase.getDatabase(getApplicationContext()).
                        scoreDao().insert(scoreRoom); //get database from class which needs ApplicationContext and uses the insert-dunction on the scoreDao
            }

            @Override
            protected void onPostExecute(Long resultId) { //resultId = parameter from doInBackround
                // you can update the UI-Thread with your results here
                Toast.makeText(getApplicationContext(), "Created entry with id: " + resultId, Toast.LENGTH_LONG).show(); //shows notification with the id of the entry
            }
        }.execute();
    }
    /**
    * clicking the button saves the score only when username had been entered, finish the activity and opens HighscoreActivity
    * @author Stefan Tobisch
    * */
    public void highscoreButtonClick(View view) {
        saveScore(view);
        finish();
        Intent highscoreIntent = new Intent ( this, HighscoreActivity.class);
        startActivity(highscoreIntent);
    }
}


