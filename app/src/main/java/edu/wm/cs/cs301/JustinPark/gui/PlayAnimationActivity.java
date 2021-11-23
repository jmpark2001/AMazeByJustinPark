package edu.wm.cs.cs301.JustinPark.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import edu.wm.cs.cs301.JustinPark.R;

public class PlayAnimationActivity extends AppCompatActivity {
    private final String TAG = "PlayAnimationActivity";
    private Switch map, solution, walls;
    private Button play;
    private ProgressBar energy;
    private Button win;
    private Button lose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_animation);

        energy = (ProgressBar) findViewById(R.id.progressBar);
//        energy.setProgress(3500);

        map = (Switch) findViewById(R.id.switch2);
        map.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getBaseContext(), "Map On", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Map Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        win = (Button) findViewById(R.id.button);
        win.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Go to win");
                openWinningActivity();
            }
        });
        lose = (Button) findViewById(R.id.button4);
        lose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Go to win");
                openLosingActivity();
            }
        });
    }

    public void openWinningActivity() {
        Intent intent = new Intent(this, WinningActivity.class);
        startActivity(intent);
        finish();
    }

    public void openLosingActivity() {
        Intent intent = new Intent(this, LosingActivity.class);
        startActivity(intent);
        finish();
    }
}