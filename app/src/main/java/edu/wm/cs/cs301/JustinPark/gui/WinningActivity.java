package edu.wm.cs.cs301.JustinPark.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import edu.wm.cs.cs301.JustinPark.R;

public class WinningActivity extends AppCompatActivity {
    int pathLength = 0;
    TextView win, energyUsed, path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning);
        Intent intent = getIntent();
        float energy = intent.getFloatExtra(PlayManuallyActivity.ENERGY_USED, 0);
        pathLength = intent.getIntExtra(PlayManuallyActivity.PATH_LENGTH, 0);
        win = (TextView) findViewById(R.id.textView4);
        energyUsed = (TextView) findViewById(R.id.textView9);
        path = (TextView) findViewById(R.id.textView8);
        energyUsed.setText("Energy Comsumption: " + energy);
        path.setText("Path Length: " + pathLength);
    }
}