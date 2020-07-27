package com.e.eggtimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final long COUNT_DOWN_TIMER_INTERVAL = 1000;
    final int TIME_SEEK_BAR_MAX_LENGTH = 600;
    SeekBar timeSeekBar;
    TextView timerTextView;
    Button startButton;
    MediaPlayer mediaPlayer;
    Boolean isCounterActive = false;
    CountDownTimer countDownTimer;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeSeekBar = findViewById(R.id.timeSeekBar);
        timerTextView = findViewById(R.id.timerTextView);

        timeSeekBar.setMax(TIME_SEEK_BAR_MAX_LENGTH);
        timeSeekBar.setMin(1);
        timeSeekBar.setProgress(30);

        updateSeconds(timeSeekBar.getProgress());

        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateSeconds(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void updateSeconds(int i) {
        int minutes = i / 60;
        int seconds = i - (minutes * 60);

        String secondString = Integer.toString(seconds);

        if (seconds <= 9)
            secondString = "0" + seconds;

        timerTextView.setText(minutes + ":" + secondString);
    }

    @SuppressLint("SetTextI18n")
    public void resetTimer() {
        timerTextView.setText(String.valueOf(timeSeekBar.getProgress()));
        timeSeekBar.setProgress(30);
        timeSeekBar.setEnabled(true);
        countDownTimer.cancel();
        startButton.setText("START");
        isCounterActive = false;
    }

    @SuppressLint("SetTextI18n")
    public void onClick(View view) {
        startButton = findViewById(R.id.startButton);




        if (isCounterActive) {
            resetTimer();
        } else {

            isCounterActive = true;
            timeSeekBar.setEnabled(false);
            startButton.setText("STOP");

            countDownTimer = new CountDownTimer(timeSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long l) {
                    updateSeconds((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }


}