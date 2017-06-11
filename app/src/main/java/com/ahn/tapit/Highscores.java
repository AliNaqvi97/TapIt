package com.ahn.tapit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Highscores extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Integer> data;
    private ListViewAdapter adapter;
    private View.OnClickListener listener;
    private Button mButton, pButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        Intent i = getIntent();
        listView = (ListView) findViewById(R.id.list_score);
        data=i.getIntegerArrayListExtra("data");
        adapter = new ListViewAdapter(getApplicationContext(), R.layout.score_list, data);
        listView.setAdapter(adapter);
        mButton=(Button) findViewById(R.id.main_menu);
        pButton=(Button) findViewById(R.id.play_game);

        listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(v.getId() == R.id.main_menu) {
                    Intent g = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(g);
                    finish();
                }
                if(v.getId() == R.id.play_game){
                    Intent g = new Intent(getApplicationContext(), Game.class);
                    startActivity(g);
                    finish();
                }
            }
        };
        mButton.setOnClickListener(listener);
        pButton.setOnClickListener(listener);
    }

   @Override
    public void onBackPressed(){
        Intent g = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(g);
        finish();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        finish();
    }
}
