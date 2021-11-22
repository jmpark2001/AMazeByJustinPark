package edu.wm.cs.cs301.JustinPark.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.wm.cs.cs301.JustinPark.R;

public class AMazeActivity extends AppCompatActivity {
    public int skillLevel = 0;
    private Button revisit;
    private Button explore;
    private TextView textView;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setMazeSize();

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
}