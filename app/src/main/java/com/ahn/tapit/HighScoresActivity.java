package com.ahn.tapit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HighScoresActivity extends AppCompatActivity {
    @BindView(R.id.button_main_menu)
    private Button buttonMainMenu;
    @BindView(R.id.button_play_again)
    private Button buttonPlayAgain;
    @BindView(R.id.score_listview)
    private ListView scoreListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);
        ButterKnife.bind(this);

        ArrayList<Integer> scores = new DatabaseHandler(HighScoresActivity.this).getAllScores();

        if (scores.size() < 10) {
            while (0 < 10 - scores.size()) {
                scores.add(null);
            }
        }

        ListViewAdapter adapter = new ListViewAdapter(HighScoresActivity.this,
                R.layout.score_list, scores);
        scoreListView.setAdapter(adapter);

        buttonMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        buttonPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HighScoresActivity.this, Game.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
