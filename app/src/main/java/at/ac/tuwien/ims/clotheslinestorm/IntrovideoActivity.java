package at.ac.tuwien.ims.clotheslinestorm;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.VideoView;

/**
* This class represents the IntrovideoActivity
* @author Stefan Tobisch
* */

public class IntrovideoActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, View.OnClickListener {

    VideoView view;
    Button skipButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //Video on fullscreen
        setContentView(R.layout.activity_introvideo);

        view = (VideoView) findViewById(R.id.videoView);    // initialise VideoView

        view.setOnCompletionListener(this);                 //when video has finished

        skipButton = (Button) findViewById(R.id.skipVideoButton);   //initialise SkipButton

        skipButton.setOnClickListener(this);                //Video ends with pushing skipButton

        //getting videofile
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.introvideo;
        Uri uri = Uri.parse(videoPath);
        view.setVideoURI(uri);
        view.start();       //starts video
    }
    /**
    * when video has ended skipVideo-function starts
    * @author Stefan Tobisch
    */
    public void onCompletion(MediaPlayer mp) {
        skipVideo();
    }
    /**
    * pushing SkipButton
    * @author Stefan Tobisch
    */
    public void onClick(View v) {
        if (v.getId() == R.id.skipVideoButton) {
            skipVideo();
        }
    }
    /**
    * ending IntrovideoActivity and starts MainActivity
    * @author Stefan Tobisch
    * */
    private void skipVideo(){
        finish();
        Intent mainIntent = new Intent (this, MainActivity.class);
        startActivity(mainIntent);
    }
}

