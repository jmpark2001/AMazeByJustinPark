package edu.wm.cs.cs301.JustinPark.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import edu.wm.cs.cs301.JustinPark.R;

public class AMazeActivity extends AppCompatActivity {
    public int skillLevel = 0;
    private Button revisit;
    private Button explore;
    private TextView textView;
    private SeekBar seekBar;
    private String mazeAlgorithm = "DFS";
    private String room = "Yes";
    private boolean hasRoom = false;

    public static final String ALGORITHM = "edu.wm.cs.cs301.JustinPark.ALGORITHM";
    public static final String MAZE_SIZE = "edu.wm.cs.cs301.JustinPark.MAZE_SIZE";
    public static final String ROOMS = "edu.wm.cs.cs301.JustinPark.ROOMS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setMazeSize();
        setRooms();
        setAlgorithm();

        revisit = (Button) findViewById(R.id.revisit);
        explore = (Button) findViewById(R.id.explore);
        revisit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                moveToGeneratingActivity();
            }
        });

        explore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                moveToGeneratingActivity();
            }
        });
    }

    private void moveToGeneratingActivity(){
        Intent intent = new Intent(this, GeneratingActivity.class);
        intent.putExtra(MAZE_SIZE, skillLevel);
        intent.putExtra(ROOMS, hasRoom);
        intent.putExtra(ALGORITHM, mazeAlgorithm);
        startActivity(intent);
    }

    public void setMazeSize(){
        textView = (TextView) findViewById(R.id.size);
        seekBar = (SeekBar) findViewById(R.id.sizeBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText("Size: " + progress);
                skillLevel = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setRooms(){
        Switch room_switch = findViewById(R.id.switch1);
        room_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getBaseContext(), "Rooms on", Toast.LENGTH_SHORT).show();
                    hasRoom = true;
                }
                else{
                    Toast.makeText(getBaseContext(), "Rooms off", Toast.LENGTH_SHORT).show();
                    hasRoom = false;
                }
            }
        });
    }

    public void setAlgorithm(){
        Spinner spinner = findViewById(R.id.algorithm_spin);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.algorithm_spinner, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mazeAlgorithm = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}