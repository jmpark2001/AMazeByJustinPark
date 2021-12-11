package edu.wm.cs.cs301.JustinPark.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import edu.wm.cs.cs301.JustinPark.R;
import edu.wm.cs.cs301.JustinPark.generation.Singleton;

public class PlayAnimationActivity extends AppCompatActivity {
    private final String TAG = "PlayAnimationActivity";
    public static final String PATH_LENGTH = "edu.wm.cs.cs301.JustinPark.PATH_LENGTH";
    public static final String ENERGY_USED = "edu.wm.cs.cs301.JustinPark.ENERGY_USED";
    private Switch map, solution, walls;
    private Button play, pause;
    private ProgressBar energy;
    private Handler handler = new Handler();
    private String driver;
    MazePanel panel;
    Robot robot = new ReliableRobot();
    RobotDriver robotDriver;
    ReliableSensor leftSensor = new ReliableSensor();
    ReliableSensor rightSensor = new ReliableSensor();
    ReliableSensor frontSensor = new ReliableSensor();
    ReliableSensor backSensor = new ReliableSensor();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            energy.setProgress((int)robot.getBatteryLevel());
            try{
                robotDriver.drive1Step2Exit();
            }
            catch(Exception e){
                Singleton.run = false;
                handler.removeCallbacks(this);
                if(Singleton.isOutside){
                    openWinningActivity();
                }
                else{
                    openLosingActivity();
                }
            }
            if(Singleton.run){
                handler.postDelayed(this, 100);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_animation);

        panel = findViewById(R.id.mazePanel);
        Intent intent = getIntent();
        driver = intent.getStringExtra(GeneratingActivity.DRIVER);
        Singleton.state.setMazeConfiguration(Singleton.mazeConfig);
        Singleton.state.start(panel);
        Singleton.run = false;
        leftSensor.setMaze(Singleton.state.getMazeConfiguration());
        rightSensor.setMaze(Singleton.state.getMazeConfiguration());
        frontSensor.setMaze(Singleton.state.getMazeConfiguration());
        backSensor.setMaze(Singleton.state.getMazeConfiguration());
        robot.addDistanceSensor(leftSensor, Robot.Direction.LEFT);
        robot.addDistanceSensor(rightSensor, Robot.Direction.RIGHT);
        robot.addDistanceSensor(frontSensor, Robot.Direction.FORWARD);
        robot.addDistanceSensor(backSensor, Robot.Direction.BACKWARD);
        energy = (ProgressBar) findViewById(R.id.energy);
        energy.setProgress(3500);

        if(driver.equals("WallFollower")){
            robotDriver = new WallFollower();
        }
        else{
            robotDriver = new Wizard();
        }
        robotDriver.setMaze(Singleton.state.mazeConfig);
        robotDriver.setRobot(robot);

        energy = findViewById(R.id.energy);
        energy.setProgress(3500);

        play = findViewById(R.id.button7);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.run = true;
                handler.postDelayed(runnable, 20);
            }
        });

        pause = findViewById(R.id.button8);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton.run = false;
                handler.removeCallbacks(runnable);
            }
        });

        map = (Switch) findViewById(R.id.switch2);
        map.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getBaseContext(), "Map On", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Map Off", Toast.LENGTH_SHORT).show();
                }
                Singleton.state.keyDown(Constants.UserInput.TOGGLEFULLMAP, 0);
            }
        });
    }

    public void openWinningActivity() {
        Intent intent = new Intent(this, WinningActivity.class);
        intent.putExtra(PATH_LENGTH, robot.getOdometerReading());
        intent.putExtra(ENERGY_USED, robotDriver.getEnergyConsumption());
        startActivity(intent);
        finish();
    }

    public void openLosingActivity() {
        Intent intent = new Intent(this, LosingActivity.class);
        intent.putExtra(PATH_LENGTH, robot.getOdometerReading());
        intent.putExtra(ENERGY_USED, robotDriver.getEnergyConsumption());
        startActivity(intent);
        finish();
    }
}