package at.ac.tuwien.ims.clotheslinestorm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * This Activity shows the help dialog
 * @author Stefan Tobisch
 * **/

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }
    /**
     * pushing the button finishs the intent and start the MainActivity
     * @author Stefan Tobisch
     * */
    public void backButtonclick(View view) {
        finish();
        Intent mainIntent = new Intent ( this, MainActivity.class);
        startActivity(mainIntent);
    }
}
