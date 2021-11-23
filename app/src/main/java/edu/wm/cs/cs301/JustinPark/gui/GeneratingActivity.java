package edu.wm.cs.cs301.JustinPark.gui;

import edu.wm.cs.cs301.JustinPark.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GeneratingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Instantiate variables
    public Button manualButton;
    public Button wizardButton;
    public Button wallFollowerButton;
    public Spinner driverSpinner;
    public ProgressBar progressBar;
    public TextView textView;

    public String driverSelected;
    public String sensorTypeChosen = "";
    public Boolean threadIsDone = false;
    public Boolean startButton = false;
    public boolean driverIsSelected = false;
    private Handler mazeHandler = new Handler();
    private int progress = 0;

    // TAG for logcat messages
    private static final String TAG = "GeneratingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating);

        // create variables for widgets
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView3);
        manualButton = (Button) findViewById(R.id.manual_ready);
        wizardButton = (Button) findViewById(R.id.wizard);
        wallFollowerButton = (Button) findViewById(R.id.WallFollowerButton);
        driverSpinner = (Spinner) findViewById(R.id.robot_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.robot_spinner_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driverSpinner.setAdapter(adapter);
        driverSpinner.setOnItemSelectedListener(this);

        // Start thread
        runBackgroundThread();

        // Manual button pressed
        manualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverSelected = "Manual";
                driverIsSelected = true;
                if(startButton){
                    playManuallyActivity();
                }
            }
        });

        // Wizard button pressed
        wizardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                driverSpinner.setVisibility(View.VISIBLE);
                driverSelected = "Wizard";
            }
        });

        // WallFollower button pressed
        wallFollowerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                driverSpinner.setVisibility(View.VISIBLE);
                driverSelected = "WallFollower";
            }
        });

    }

    public void retrieveChoices() {
        if(!driverIsSelected){
            Log.v(TAG, "Default");
            Toast.makeText(getApplicationContext(), "Choose a Driver", Toast.LENGTH_SHORT).show();
            startButton = true;
        }
        else{
            Log.v(TAG, "User Input");
            Toast.makeText(getApplicationContext(), "Waiting for maze to generate", Toast.LENGTH_SHORT).show();
            if(driverSelected.equals("Manual")){
                playManuallyActivity();
            }
            else if(!sensorTypeChosen.equals("Select")){
                playAutomaticActivity();
            }
        }
    }

    public void playManuallyActivity() {
        Intent intent = new Intent(this, PlayManuallyActivity.class);
        driverSpinner.setVisibility(View.GONE);
        intent.putExtra("chosenDriver", driverSelected);
        intent.putExtra("chosenSensor", sensorTypeChosen);
        startActivity(intent);
    }

    public void playAutomaticActivity() {
        Intent intent = new Intent(this, PlayAnimationActivity.class);
        driverSpinner.setVisibility(View.GONE);
        intent.putExtra("USER_CHOICE_DRIVER", driverSelected);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        sensorTypeChosen = parent.getItemAtPosition(pos).toString();
        driverIsSelected = true;
        Toast.makeText(getApplicationContext(), sensorTypeChosen, Toast.LENGTH_SHORT).show();
        if(!sensorTypeChosen.equals("Select") && threadIsDone){
            playAutomaticActivity();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void runBackgroundThread() {
        mazeRunnable runnable = new mazeRunnable();
        new Thread(runnable).start();
    }

    class mazeRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                Log.v(TAG, "Thread began: " + i);
                try {
                    Thread.sleep(50);
                    progress += 1;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mazeHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(progress);
                        if (progress == 100) {
                            textView.setText("Generation Finished");
                        }
                    }
                });
            }
            mazeHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.v(TAG, "hello there");
                    retrieveChoices();
                    threadIsDone = true;
                }
            });
        }
    }
}