package edu.wm.cs.cs301.JustinPark.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import edu.wm.cs.cs301.JustinPark.R;

public class WinningActivity extends AppCompatActivity {
    int pathLength = 0;
    TextView win, energyUsed, path;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning);

        mediaPlayer = MediaPlayer.create(WinningActivity.this, R.raw.wonderful);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        Intent intent = getIntent();
        float energy = intent.getFloatExtra(PlayManuallyActivity.ENERGY_USED, 0);
        pathLength = intent.getIntExtra(PlayManuallyActivity.PATH_LENGTH, 0);
        win = (TextView) findViewById(R.id.textView4);
        energyUsed = (TextView) findViewById(R.id.textView9);
        path = (TextView) findViewById(R.id.textView8);
        energyUsed.setText("Energy Comsumption: " + energy);
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