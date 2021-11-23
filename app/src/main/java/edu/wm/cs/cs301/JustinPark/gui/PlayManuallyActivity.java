package edu.wm.cs.cs301.JustinPark.gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TintableCompoundDrawablesView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import edu.wm.cs.cs301.JustinPark.R;

public class PlayManuallyActivity extends AppCompatActivity {
    private static final String TAG = "PlayManuallyActivity";
    private Button up, left, right, jump, shortcut;
    private Switch map, solution, walls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_manually);

        up = (Button) findViewById(R.id.up);
        left = (Button) findViewById(R.id.left);
        right = (Button) findViewById(R.id.right);
        jump = (Button) findViewById(R.id.jump);
        shortcut = (Button) findViewById(R.id.ShortCut);

        map = findViewById(R.id.mapSwitch);
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

        solution = findViewById(R.id.solutionSwitch);
        solution.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getBaseContext(), "Solution On", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Solution Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        walls = findViewById(R.id.wallSwitch);
        walls.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getBaseContext(), "Walls On", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Walls Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Up Button Clicked");
                Toast.makeText(getBaseContext(), "Up", Toast.LENGTH_SHORT).show();
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Left Button Clicked");
                Toast.makeText(getBaseContext(), "Left", Toast.LENGTH_SHORT).show();
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Right Button Clicked");
                Toast.makeText(getBaseContext(), "Right", Toast.LENGTH_SHORT).show();
            }
        });

        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Jump Button Clicked");
                Toast.makeText(getBaseContext(), "Jump", Toast.LENGTH_SHORT).show();
            }
        });

        shortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "ShortCut Button Clicked");
                Toast.makeText(getBaseContext(), "ShortCut", Toast.LENGTH_SHORT).show();
                playWinningActivity();
            }
        });
    }

    private void playWinningActivity(){
        Intent intent = new Intent(this, WinningActivity.class);
        startActivity(intent);
        finish();
    }
}