package com.example.studytimerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;


public class MainActivity extends AppCompatActivity{

    TextView lastTask;
    EditText task;
    ImageButton start;
    ImageButton pause;
    ImageButton stop;
    Chronometer timer;
    long time;
    long recording = 0;
    String Time;
    String showText;
    String str;

    SharedPreferences share;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lastTask = findViewById(R.id.showtask);
        start = findViewById(R.id.start);
        pause = findViewById(R.id.pause);
        stop = findViewById(R.id.stop);
        task = findViewById(R.id.task);
        timer = findViewById(R.id.timer);
        lastTask.setText("You spent" + timer.getText().toString() + " on "+task.getText().toString() + " last time");
        share = getSharedPreferences("com.example.studytimerapp", MODE_PRIVATE);
        share();

    pause.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View view){

            timer.stop();
            recording = SystemClock.elapsedRealtime() - timer.getBase();
        }
    });

    stop.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View view){

            showText = "You spent " + timer.getText().toString() + " on " + task.getText().toString() + " last time";
            SharedPreferences.Editor editor = share.edit();
            editor.putString(str, showText);
            editor.apply();
            lastTask.setText(showText);
            recording = 0;
            timer.setBase(SystemClock.elapsedRealtime());
            timer.stop();
        }
    });


    if (savedInstanceState != null){

            timer.setBase(savedInstanceState.getLong(Time));
            timer.start();
            time = timer.getBase();

        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){

        super.onSaveInstanceState(outState);
        outState.putLong(Time, time);

    }

    public void share(){

      String show = share.getString(str, "");
      lastTask.setText(show);

    }

    public void start(View view){

        timer.setBase(SystemClock.elapsedRealtime() - recording);
        timer.start();
        time = timer.getBase();

    }
}