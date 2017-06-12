package com.ahn.tapit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.button_play) Button buttonPlay;
    @BindView(R.id.button_instructions) Button buttonInstructions;
    @BindView(R.id.button_high_scores) Button buttonHighScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Game.class));
            }
        });
        buttonInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Instructions.class));
            }
        });
        buttonHighScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Highscores.class);
                ArrayList<Integer> scores = new DatabaseHandler(MainActivity.this).getAllScores();
                int x = 0;
                if (scores.size() < 10) {
                    while (x < 10 - scores.size()) {
                        scores.add(null);
                    }
                }
                intent.putIntegerArrayListExtra("data", scores);
                startActivity(intent);
            }
        });
    }
}
