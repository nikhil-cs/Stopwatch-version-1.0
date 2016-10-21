package com.example.stopwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;

public class StopwatchActivity extends AppCompatActivity {

    private int seconds=0; //record number of seconds
    private boolean running; //whether stopwatch is running or not
    private boolean wasRunning; //whether the stopwatch was running b4 onStop got called

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        //retrive the value of seconds and running from bundle
        if(savedInstanceState != null)
        {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();
    }

    //for start button
    public void onClickStart(View view)
    {
        running = true; //start the stopwatch running
    }

    //for stop button
    public void onClickStop(View view)
    {
        running = false; //stop the stopwatch running
    }

    //for reset button
    public void onClickReset(View view)
    {
        running = false; //stop the stopwatch running
        seconds = 0; //set seconds to zero
    }

    private void runTimer()
    {
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {   //use a handler to post code
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format("%d:%02d:%02d",hours, minutes,secs);
                timeView.setText(time); //set the text view text
                if(running)
                {
                    seconds++;
                }
                handler.postDelayed(this, 1000);    //post the code again with a default of 1 seconds

            }
        });
    }

    //to save instances before distroying
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

  /*  //to stop stopwatch if it is not visible to user
    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = running;
        running = false;
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(wasRunning) {
            running = true;
        }
    }*/

    //to stop stopwatch if it is partialy visible
    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }
}
