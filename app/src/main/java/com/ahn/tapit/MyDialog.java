package com.ahn.tapit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Ali on 11/12/2015.
 */
public class MyDialog extends DialogFragment {

    LayoutInflater inflater;
    View v;
    private Button resume, quit, highScore, restart;
    private long timeLeft, buttonTime;
    int count;
    private ArrayList<Integer> scores;

    public void values(long gameTimer, long buttonTimer, int countIn){
        timeLeft=gameTimer;
        count = countIn;
        buttonTime = buttonTimer;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.pause_dialogue, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        resume = (Button) v.findViewById(R.id.resume);
        restart = (Button) v.findViewById(R.id.restart);
        quit = (Button) v.findViewById(R.id.quit);
        highScore = (Button) v.findViewById(R.id.highscore);
        final DatabaseHandler db = new DatabaseHandler(getActivity());


        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent(getActivity(), Game.class);
                g.putExtra("score", count);
                g.putExtra("gameTime", timeLeft);
                g.putExtra("buttonTime", buttonTime);
                startActivity(g);
                getActivity().finish();

            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent(getActivity(), Game.class);
                startActivity(g);
                getActivity().finish();
            }
        });
        highScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent(getActivity(), Highscores.class);
                scores = db.getAllScores();
                int x = 0;
                if(scores.size()<10) {
                    while(x <10-scores.size()){
                        scores.add(null);
                    }
                }
                g.putIntegerArrayListExtra("data", scores);
                startActivity(g);
                getActivity().finish();
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent(getActivity(), MainActivity.class);
                startActivity(g);
                getActivity().finish();
            }
        });
        return builder.create();
    }
}
