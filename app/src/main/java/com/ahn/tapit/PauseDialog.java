package com.ahn.tapit;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PauseDialog extends DialogFragment {
    private Game gameObject;

    @BindView(R.id.resume_button)
    Button resumeButton;
    @BindView(R.id.restart_button)
    Button restartButton;
    @BindView(R.id.highscore_button)
    Button highScoreButton;
    @BindView(R.id.quit_button)
    Button quitButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.pause_dialogue, null);
        ButterKnife.bind(this, view);

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                gameObject.resumeGame(false);
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                gameObject.resumeGame(true);
            }
        });

        highScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                startActivity(new Intent(getActivity(), HighScoresActivity.class));
                getActivity().finish();
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                getActivity().finish();
            }
        });

        return view;
    }

    public void setObject(Game object) {
        gameObject = object;
    }
}