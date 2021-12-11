package edu.wm.cs.cs301.JustinPark.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import edu.wm.cs.cs301.JustinPark.R;

public class LosingActivity extends AppCompatActivity {
    int pathLength = 0;
    TextView lose, energyUsed, path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_losing);
        Intent intent = getIntent();
        pathLength = intent.getIntExtra(PlayManuallyActivity.PATH_LENGTH, 0);
        lose = (TextView) findViewById(R.id.textView7);
        energyUsed = (TextView) findViewById(R.id.textView11);
        path = (TextView) findViewById(R.id.textView12);
        energyUsed.setText("Energy Comsumption: 3500");
        path.setText("Path Length: " + pathLength);
    }
}