package edu.wm.cs.cs301.JustinPark.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import edu.wm.cs.cs301.JustinPark.R;

public class LosingActivity extends AppCompatActivity {
    int pathLength = 0;
    TextView lose, energyUsed, path;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_losing);

        mediaPlayer = MediaPlayer.create(LosingActivity.this, R.raw.silent);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        Intent intent = getIntent();
        pathLength = intent.getIntExtra(PlayManuallyActivity.PATH_LENGTH, 0);
        lose = (TextView) findViewById(R.id.textView7);
        energyUsed = (TextView) findViewById(R.id.textView11);
        path = (TextView) findViewById(R.id.textView12);
        energyUsed.setText("Energy Comsumption: 3500");
        path.setText("Path Length: " + pathLength);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}