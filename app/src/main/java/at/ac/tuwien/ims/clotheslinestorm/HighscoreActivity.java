package at.ac.tuwien.ims.clotheslinestorm;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 *This class shows the saved highscores with points and username
 * @author Stefan Tobisch
 * **/

public class HighscoreActivity extends AppCompatActivity {

    private ListView scoreListView;
    private View progressView;
    private ScoreAdapter scoreAdapter;
    private ArrayList<Score> scoresList;

    private static final int DELETE_ID = Menu.FIRST + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        scoreListView = findViewById(R.id.scoreListView);
        progressView = findViewById(R.id.progressView);

        scoresList = new ArrayList<>();
        scoreAdapter = new ScoreAdapter(this, scoresList);
        scoreListView.setAdapter(scoreAdapter);
        fillData();
        registerForContextMenu(scoreListView);
    }

    private void showProgress(boolean progress) {
        scoreListView.setVisibility(progress ? View.GONE : View.VISIBLE);
        progressView.setVisibility(progress ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    /**
    * fills data from the database in list
    * @author Stefan Tobisch
    * */
    private void fillData() {
        showProgress(true);

        // run database action in background. don't block UI thread
        new AsyncTask<Void, Void, List<Score>>() {
            @Override
            protected List<Score> doInBackground(Void... voids) {
                return AppRoomDatabase.getDatabase(getApplicationContext()).
                        scoreDao().findAllScoreDesc();
            }

            @Override
            protected void onPostExecute(List<Score> scores) {
                // you can update the UI-Thread with your results here
                scoresList.clear();
                scoresList.addAll(scores);
                showProgress(false);
            }
        }.execute();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                        .getMenuInfo();
                // get score id from index
                final Score score = new Score(scoresList.get((int) info.id).getId());
                new AsyncTask<Void, Void, Score>() {
                    @Override
                    protected Score doInBackground(Void... voids) {
                        AppRoomDatabase.getDatabase(getApplicationContext()).
                                scoreDao().deleteScore(score);
                        return score;
                    }

                    @Override
                    protected void onPostExecute(Score scoreRoom) {
                        scoreAdapter.notifyDataSetChanged();
                    }
                }.execute();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    // Source: https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView
    class ScoreAdapter extends ArrayAdapter<Score> {
        public ScoreAdapter(Context context, List<Score> scores) {
            super(context, 0, scores);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Score score = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_score, parent, false);
            }
            // Lookup view for data population
            TextView userNameTxtField = convertView.findViewById(R.id.textViewName);
            TextView scoreTxtField = convertView.findViewById(R.id.textViewScore);

            // Populate the data into the template view using the data object
            userNameTxtField.setText(score.getUsername());
            scoreTxtField.setText(String.valueOf(score.getScore()));
            // Return the completed view to render on screen
            return convertView;
        }
    }

    /**
     * pushing the button finishs the highscoreActivity and start the MainActivity
     * @author Stefan Tobisch
     * */
    public void backButtonClick(View view) {
        finish();
        Intent mainIntent = new Intent ( this, MainActivity.class);
        startActivity(mainIntent);
    }
}
