package com.ahn.tapit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private View.OnClickListener listener;
    private Button bPlay, bInstructions, bHighScores;
    private ArrayList<Integer> scores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bPlay = (Button) findViewById(R.id.play);
        bInstructions = (Button) findViewById(R.id.instructions);
        bHighScores = (Button) findViewById(R.id.button_high_scores);
        final DatabaseHandler db = new DatabaseHandler(this);

        listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(v.getId()==R.id.play) {
                    Intent g = new Intent(getApplicationContext(), Game.class);
                    startActivity(g);
                    finish();
                }
                if(v.getId()==R.id.instructions){
                    Intent g = new Intent(getApplicationContext(), Instructions.class);
                    startActivity(g);
                    finish();
                }
                if(v.getId()==R.id.button_high_scores){
                    Intent g = new Intent(getApplicationContext(), Highscores.class);
                    scores = db.getAllScores();
                    int x = 0;
                    if(scores.size()<10) {
                        while(x <10-scores.size()){
                            scores.add(null);
                        }
                    }
                    g.putIntegerArrayListExtra("data", scores);
                    startActivity(g);
                    finish();
                }
            }
        };
        bPlay.setOnClickListener(listener);
        bInstructions.setOnClickListener(listener);
        bHighScores.setOnClickListener(listener);

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        finish();
    }
}
