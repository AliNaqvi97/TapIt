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
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class Game extends AppCompatActivity {
    private TextView tvScore, scoreChange;
    private TextView tvTime, timeChange;
    private ImageButton pause;
    private Button tButton, addTime, subTime, addScore, subScore;
    private ArrayList<Integer> scores;
    private AnimationSet as = new AnimationSet(true);
    public CountDownTimer time, buttonTime, visibleTime, buttonVisible;
    public int count, choice = 0, visible = 0;
    public long min, sec, timeLeft, time2, timeRandom, defaultButtonTime = (long) (((Math.random() * 2) + 5) * 1000);
    private boolean isPaused = false;
    public MyDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvScore = (TextView) findViewById(R.id.score);
        tvTime = (TextView) findViewById(R.id.time);
        scoreChange = (TextView) findViewById(R.id.score_change);
        timeChange = (TextView) findViewById(R.id.time_change);
        tButton = (Button) findViewById(R.id.tap_button);
        addTime = (Button) findViewById(R.id.add_time);
        pause = (ImageButton) findViewById(R.id.pause);
        addScore = (Button) findViewById(R.id.add_score);
        subScore = (Button) findViewById(R.id.sub_score);
        subTime = (Button) findViewById(R.id.sub_time);
        Intent i = getIntent();
        count = i.getIntExtra("score", 0);
        timeLeft = i.getLongExtra("gameTime", 61000);
        timeRandom = i.getLongExtra("buttonTime", defaultButtonTime);
        tvScore.setText(count + "");
        Animation in = new AlphaAnimation(0.0f, 1.0f);
        Animation out = new AlphaAnimation(1.0f, 0.0f);
        in.setDuration(1000);
        out.setDuration(1000);
        as.addAnimation(in);
        out.setStartOffset(1500);
        as.addAnimation(out);

    }

    DatabaseHandler db = new DatabaseHandler(this);

    public void startCountDownTimer(long milliseconds) {
        time = new CountDownTimer(timeLeft, 1000) {
            public void onTick(long millisUntilFinished) {
                min = millisUntilFinished / 60000;
                timeLeft = millisUntilFinished;
                sec = (millisUntilFinished % 60000) / 1000;
                if (sec > 9) {
                    tvTime.setText(min + ":" + sec);
                } else if (sec <= 9) {
                    tvTime.setText(min + ":0" + sec);
                }
            }

            public void onFinish() {
                tvTime.setText("0:00");
                db.addScore(count);
                scores = db.getAllScores();
                int x = 0;
                if (scores.size() < 10) {
                    while (x < 10 - scores.size()) {
                        scores.add(null);
                    }
                }
                Intent g = new Intent(getApplicationContext(), Highscores.class);
                g.putIntegerArrayListExtra("data", scores);
                startActivity(g);
                finish();
            }
        }.start();
    }

    public void buttonTimer() {
        buttonTime = new CountDownTimer(timeRandom, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time2 = millisUntilFinished;
                choice = (int) ((Math.random() * 4) + 1);
                visible = (int) (Math.random() * 2);
            }

            @Override
            public void onFinish() {
                switch (choice) {
                    case 1:
                        switch (visible) {
                            case 0:
                                addScore.setText("????");
                                break;
                            case 1:
                                addScore.setText("+5 Score!");
                                break;
                        }
                        if (!isPaused) {
                            addScore.setVisibility(View.VISIBLE);
                            buttonVisibility(addScore);
                        }
                        break;
                    case 2:
                        switch (visible) {
                            case 0:
                                subScore.setText("????");
                                break;
                            case 1:
                                subScore.setText("-5 Score!");
                                break;
                        }
                        if (!isPaused) {
                            subScore.setVisibility(View.VISIBLE);
                            buttonVisibility(subScore);
                        }
                        break;
                    case 3:
                        switch (visible) {
                            case 0:
                                addTime.setText("????");
                                break;
                            case 1:
                                addTime.setText("+5 Seconds!");
                                break;
                        }
                        if (!isPaused) {
                            addTime.setVisibility(View.VISIBLE);
                            buttonVisibility(addTime);
                        }
                        break;
                    case 4:
                        switch (visible) {
                            case 0:
                                subTime.setText("????");
                                break;
                            case 1:
                                subTime.setText("-5 Seconds!");
                                break;
                        }
                        if (!isPaused) {
                            subTime.setVisibility(View.VISIBLE);
                            buttonVisibility(subTime);
                        }
                        break;
                }
            }
        }.start();
    }

    public void scoreOnClick(View view) {
        count++;
        tvScore.setText(count + "");
    }

    public void addScoreOnClick(View view) {
        count += 5;
        buttonTime.cancel();
        tvScore.setText(count + "");
        timeRandom = (long) ((Math.random() * 2) + 5) * 1000;
        buttonTimer();
        addScore.setVisibility(View.INVISIBLE);
        scoreChange.setTextColor(Color.GREEN);
        scoreChange.startAnimation(as);
        scoreChange.setText("+5");
        visibleTimer(scoreChange);
    }

    public void subScoreOnClick(View view) {
        count -= 5;
        buttonTime.cancel();
        tvScore.setText(count + "");
        timeRandom = (long) ((Math.random() * 2) + 5) * 1000;
        buttonTimer();
        subScore.setVisibility(View.INVISIBLE);
        scoreChange.setTextColor(Color.RED);
        scoreChange.startAnimation(as);
        scoreChange.setText("-5");
        visibleTimer(scoreChange);
    }

    public void addTimeOnClick(View view) {
        time.cancel();
        buttonTime.cancel();
        startCountDownTimer(timeLeft += 5000);
        timeRandom = (long) ((Math.random() * 2) + 5) * 1000;
        buttonTimer();
        addTime.setVisibility(View.INVISIBLE);
        timeChange.setTextColor(Color.GREEN);
        timeChange.startAnimation(as);
        timeChange.setText("+5");
        visibleTimer(timeChange);
    }

    public void subTimeOnClick(View view) {
        time.cancel();
        buttonTime.cancel();
        startCountDownTimer(timeLeft -= 5000);
        timeRandom = (long) ((Math.random() * 2) + 5) * 1000;
        buttonTimer();
        subTime.setVisibility(View.INVISIBLE);
        timeChange.setTextColor(Color.RED);
        timeChange.startAnimation(as);
        timeChange.setText("-5");
        visibleTimer(timeChange);
    }

    public void pauseOnClick(View v) {
        dialog = new MyDialog();
        onPause();
        dialog.show(getFragmentManager(), "my_dialog");
        dialog.setCancelable(false);
        dialog.values(timeLeft, time2, count);
    }

    private void visibleTimer(final TextView textView) {
        visibleTime = new CountDownTimer(2500, 1000) {
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

    @Override
    public void onBackPressed() {
        Intent g = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(g);
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        time.cancel();
        buttonTime.cancel();
        isPaused = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        startCountDownTimer(timeLeft);
        buttonTimer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        time.cancel();
        buttonTime.cancel();
        finish();
    }
}