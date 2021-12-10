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
import edu.wm.cs.cs301.JustinPark.generation.Singleton;

public class PlayManuallyActivity extends AppCompatActivity {
    private static final String TAG = "PlayManuallyActivity";
    private Button up, left, right, jump, shortcut;
    private Switch map, solution, walls;
    MazePanel panel;
    Robot robot = new ReliableRobot();
    RobotDriver manualDriver = new ManualDriver();
    ReliableSensor leftSensor = new ReliableSensor();
    ReliableSensor rightSensor = new ReliableSensor();
    ReliableSensor frontSensor = new ReliableSensor();
    ReliableSensor backSensor = new ReliableSensor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_manually);

        panel = findViewById(R.id.mazePanel);
        robot.setBatteryLevel(3500);
        Singleton.state.setMazeConfiguration(Singleton.mazeConfig);
        leftSensor.setMaze(Singleton.state.getMazeConfiguration());
        rightSensor.setMaze(Singleton.state.getMazeConfiguration());
        frontSensor.setMaze(Singleton.state.getMazeConfiguration());
        robot.addDistanceSensor(leftSensor, Robot.Direction.LEFT);
        robot.addDistanceSensor(rightSensor, Robot.Direction.RIGHT);
        robot.addDistanceSensor(frontSensor, Robot.Direction.FORWARD);
        robot.addDistanceSensor(backSensor, Robot.Direction.BACKWARD);
        manualDriver.setRobot(robot);
        Singleton.state.start(panel);
        up = (Button) findViewById(R.id.up);
        left = (Button) findViewById(R.id.left);
        right = (Button) findViewById(R.id.right);
        jump = (Button) findViewById(R.id.jump);



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
                robot.move(1);
                if(Singleton.isOutside){
                    playWinningActivity();
                }
                Log.v(TAG, "Up Button Clicked");
                Toast.makeText(getBaseContext(), "Up", Toast.LENGTH_SHORT).show();
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                robot.rotate(Robot.Turn.LEFT);
                Log.v(TAG, "Left Button Clicked");
                Toast.makeText(getBaseContext(), "Left", Toast.LENGTH_SHORT).show();
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                robot.rotate(Robot.Turn.RIGHT);
                Log.v(TAG, "Right Button Clicked");
                Toast.makeText(getBaseContext(), "Right", Toast.LENGTH_SHORT).show();
            }
        });

        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                robot.jump();
                Log.v(TAG, "Jump Button Clicked");
                Toast.makeText(getBaseContext(), "Jump", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void playWinningActivity(){
        Intent intent = new Intent(this, WinningActivity.class);
        startActivity(intent);
        finish();
    }
}