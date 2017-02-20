package com.example.sailik.progressbar_17_feb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mStartButton;
    private Button mNextButton;
    private ProgressBar mProgress;
    boolean shouldContinue=true;


    int progress=0;
    public final String state="state";
    int receivedProgress;
    private static final String TAG = MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartButton = (Button) findViewById(R.id.button);
        mNextButton = (Button) findViewById(R.id.button_next);
        mProgress = (ProgressBar)findViewById(R.id.progressBar);
        mProgress.setMax(100);
        if (savedInstanceState != null) {
            Log.d(TAG,"savedinstance not null oncreate");
            receivedProgress = savedInstanceState.getInt(state, 0);

            if(receivedProgress<100&&receivedProgress!=0){
                mStartButton.setEnabled(false);
            }
            else{
                mStartButton.setEnabled(true);
            }
            if(receivedProgress!=0) {
                mProgress.setProgress(receivedProgress);
                setProgressValue(receivedProgress);
            }

            mStartButton.setOnClickListener(this);
            mNextButton.setOnClickListener(this);

        }
        else{
            Log.d(TAG,"else---oncreate");
            mProgress.setProgress(0);
            mStartButton.setOnClickListener(this);
            mNextButton.setOnClickListener(this);
        }



    }

    @Override
    public void onClick(View v){
        Log.d(TAG,"onclick");
        switch(v.getId()){
            case R.id.button:
                mStartButton.setEnabled(false);
                mProgress.setProgress(0);
                shouldContinue=true;
                setProgressValue(0);

                break;
            case R.id.button_next:
                Intent i = new Intent(MainActivity.this,SecondScreen.class);
                startActivity(i);
                break;
        }
    }
    @Override
    protected void onResume(){
        Log.d(TAG,"onresume");
        super.onResume();
        if(mProgress.getProgress()==100){
            mStartButton.setEnabled(true);
        }

    }

    public void setProgressValue(final int progress){


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                int prog = progress;
                try {
                    //Thread.sleep(1000);
                    while(shouldContinue) {
                        Thread.sleep(1000);
                        prog += 10;
                        mProgress.setProgress(prog);
                        if (mProgress.getProgress() == 100){
                            shouldContinue=false;
                            mStartButton.post(new Runnable() {
                                @Override
                                public void run() {
                                    mStartButton.setEnabled(true);
                                    shouldContinue=false;
                                }
                            });

                        }
                    }
                    //mProgress.setProgress(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                Log.d(TAG, "setprogressvalue:" + progress);
                }

        });
        thread.start();


    }



    @Override
    protected void onSaveInstanceState(Bundle outState){
        Log.d(TAG,"to send bundle");

            int currentProgress = mProgress.getProgress();

            super.onSaveInstanceState(outState);

            outState.putInt(state, currentProgress);

    }

    @Override
    protected void onDestroy() {
        shouldContinue = false;
        super.onDestroy();
    }
}
