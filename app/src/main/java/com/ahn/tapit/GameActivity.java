package com.ahn.tapit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameActivity extends AppCompatActivity {
    @BindView(R.id.score_change)
    TextView scoreChange;
    @BindView(R.id.score_textview)
    TextView scoreTextView;
    @BindView(R.id.time_textview)
    TextView timeTextView;
    @BindView(R.id.add_score)
    Button powerUpButton;
    @BindView(R.id.time_change)
    TextView timeChange;

    private int score;
    private int choice;
    private long timeLeft;
    private long timeRandom;
    private boolean isPaused;
    private PauseDialog dialog;
    private AnimationSet as = new AnimationSet(true);
    private CountDownTimer time, buttonTime, buttonVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        score = 0;
        timeLeft = 6100;
        timeRandom = (long) (((Math.random() * 2) + 5) * 1000);
        scoreTextView.setText(getString(R.string.score, score));

        Animation in = new AlphaAnimation(0.0f, 1.0f);
        Animation out = new AlphaAnimation(1.0f, 0.0f);
        in.setDuration(1000);
        out.setDuration(1000);
        as.addAnimation(in);
        out.setStartOffset(1500);
        as.addAnimation(out);

        dialog = new PauseDialog();
        dialog.setObject(this);
        dialog.setCancelable(false);
    }

    public void startCountDownTimer() {
        time = new CountDownTimer(timeLeft, 1000) {
            public void onTick(long millisUntilFinished) {
                long min = millisUntilFinished / 60000;
                timeLeft = millisUntilFinished;
                long sec = (millisUntilFinished % 60000) / 1000;
                if (sec > 9) {
                    timeTextView.setText(min + ":" + sec);
                } else if (sec <= 9) {
                    timeTextView.setText(min + ":0" + sec);
                }
            }

            public void onFinish() {
                DatabaseHandler db = new DatabaseHandler(GameActivity.this);
                timeTextView.setText(getString(R.string.button_text, "0:00"));
                db.addScore(score);
                startActivity(new Intent(GameActivity.this, HighScoresActivity.class));
                finish();
            }
        }.start();
    }

    public void buttonTimer() {
        buttonTime = new CountDownTimer(timeRandom, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Random random = new Random();
                choice = random.nextInt(4);
                boolean visible = random.nextBoolean();
                switch (choice) {
                    case 0:
                        if (visible)
                            powerUpButton.setText(getString(R.string.button_text, "+5 Score!"));
                        else
                            powerUpButton.setText("????");

                        if (!isPaused) {
                            powerUpButton.setVisibility(View.VISIBLE);
                            buttonVisibility(powerUpButton);
                        }
                        break;
                    case 1:
                        if (visible)
                            powerUpButton.setText(getString(R.string.button_text, "-5 Score!"));
                        else
                            powerUpButton.setText("????");

                        if (!isPaused) {
                            powerUpButton.setVisibility(View.VISIBLE);
                            buttonVisibility(powerUpButton);
                        }
                        break;
                    case 2:
                        if (visible)
                            powerUpButton.setText(getString(R.string.button_text, "+5 Seconds!"));
                        else
                            powerUpButton.setText("????");

                        if (!isPaused) {
                            powerUpButton.setVisibility(View.VISIBLE);
                            buttonVisibility(powerUpButton);
                        }
                        break;
                    case 3:
                        if (visible)
                            powerUpButton.setText(getString(R.string.button_text, "-5 Seconds!"));
                        else
                            powerUpButton.setText("????");

                        if (!isPaused) {
                            powerUpButton.setVisibility(View.VISIBLE);
                            buttonVisibility(powerUpButton);
                        }
                        break;
                }
            }
        }.start();
    }

    public void scoreOnClick(View view) {
        score++;
        scoreTextView.setText(getString(R.string.score, score));
    }

    public void addScoreOnClick(View view) {
        switch (choice) {
            case 0:
                score += 5;
                scoreTextView.setText(getString(R.string.score, score));
                startTextAnimation(true, true);
                break;
            case 1:
                score = score - 5 < 0 ? 0 : score - 5;
                scoreTextView.setText(getString(R.string.score, score));
                startTextAnimation(true, false);
                break;
            case 2:
                time.cancel();
                timeLeft += 5000;
                startCountDownTimer();
                startTextAnimation(false, true);
                break;
            case 3:
                time.cancel();
                timeLeft = timeLeft - 5000 < 0 ? 0 : timeLeft - 5000;
                startCountDownTimer();
                startTextAnimation(false, false);
                break;
        }
        buttonTime.cancel();
        timeRandom = (long) ((Math.random() * 2) + 5) * 1000;
        buttonTimer();
        powerUpButton.setVisibility(View.INVISIBLE);
    }

    public void startTextAnimation(boolean isScore, boolean isPositive) {
        if (isScore) {
            if (isPositive) {
                scoreChange.setTextColor(Color.GREEN);
                scoreChange.setText("+5");
            } else {
                scoreChange.setTextColor(Color.RED);
                scoreChange.setText("-5");
            }
            scoreChange.startAnimation(as);
            visibleTimer(scoreChange);
        } else {
            if (isPositive) {
                timeChange.setTextColor(Color.GREEN);
                timeChange.setText("+5");
            } else {
                timeChange.setTextColor(Color.RED);
                timeChange.setText("-5");
            }
            timeChange.startAnimation(as);
            visibleTimer(timeChange);
        }
    }

    public void pauseOnClick(View v) {
        this.onPause();
        dialog.show(getFragmentManager(), "pause_dialog");
    }

    private void visibleTimer(final TextView textView) {
        new CountDownTimer(2500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                textView.setText("");
            }
        }.start();
    }

    private void buttonVisibility(final Button buttonIn) {
        buttonVisible = new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (isPaused) {
                    buttonIn.setVisibility(View.INVISIBLE);
                    buttonVisible.cancel();

                }
            }

            @Override
            public void onFinish() {
                buttonIn.setVisibility(View.INVISIBLE);
                buttonTime.cancel();
                timeRandom = (long) ((Math.random() * 2) + 5) * 1000;
                buttonTimer();
            }
        }.start();
    }

    public void resumeGame(boolean isReset) {
        if (!dialog.isVisible()) {
            if (isReset) {
                score = 0;
                scoreTextView.setText(getString(R.string.score, score));
                timeLeft = 61000;
            }
            this.startCountDownTimer();
            buttonTimer();
            isPaused = false;
        }
    }

    public void pauseGame() {
        time.cancel();
        buttonTime.cancel();
        isPaused = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseGame();
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeGame(false);
    }
}