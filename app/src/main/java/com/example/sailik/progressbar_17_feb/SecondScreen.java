package com.example.sailik.progressbar_17_feb;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class SecondScreen extends AppCompatActivity implements View.OnClickListener {
    private Button mStartButton;
    private ProgressBar mProgress;
    int progress=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_second_screen);

        mStartButton = (Button) findViewById(R.id.button);
        mProgress = (ProgressBar)findViewById(R.id.progressBar);

        mProgress.setMax(100);
        mProgress.setProgress(0);

        mStartButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.button:
                mStartButton.setEnabled(false);
                setProgressValue(progress);
                break;
        }
    }

    public void setProgressValue(final int progress){

        if (progress==110) {

            mStartButton.post(new Runnable() {
                @Override
                public void run() {
                    mStartButton.setEnabled(true);
                }
            });
            return;
        }

        mProgress.setProgress(progress);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(progress + 10);
            }

        });
        thread.start();
    }
}
