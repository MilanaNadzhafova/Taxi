package com.example.projecttaxi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import static android.view.View.VISIBLE;

public class WaitTimer extends AppCompatActivity {
    TextView txtTimer;
    TextView txtExpectation;
    Button btnCancel;
    Button btnStop;
    CardView Card;
    int hours =0,minutes = 0, seconds =0, mlseconds =0 ;
    int milliseconds = 900000;
    private boolean running = true;
    int touch =1;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_timer);
        Card = (CardView) findViewById(R.id.cardBlock);
        Card.setVisibility(View.GONE);
        Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card.setVisibility(View.GONE);
                Intent intent = new Intent(WaitTimer.this,GPSForm.class);
                startActivity(intent);
            }
        });
         txtTimer = (TextView)findViewById(R.id.textForTimer);
        txtExpectation = (TextView)findViewById(R.id.expectation);
         btnCancel = (Button)findViewById(R.id.btnCancel);
         btnStop = (Button)findViewById(R.id.btnStop);

         thread.start();
         btnStop.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(touch==2)
                 {
                     Card.setVisibility(View.VISIBLE);
                 }
                 else
                 {
                     AnimationStart();
                     touch=2;
                 }

             }
         });

    }

      Thread thread = new Thread() {

         @Override
         public void run() {

             if (milliseconds >= 0) {

                 minutes = (int) ((milliseconds / (1000 * 60)) % 60);
                 seconds = (int) (milliseconds / 1000) % 60;
                 hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
                 txtTimer.setText(String.valueOf(hours) + ":" + String.valueOf(minutes) + ":" + String.valueOf(seconds));
             }
             else
             {
                 running = false;
             }

             if (running)
             {
                 milliseconds -= 1000;
             }
             else
             {
                 txtTimer.setText("00:00:00");
                 txtExpectation.setText("Drive timer");
             }
             handler.postDelayed(this, 1000);
         }
     };
    float translationX,translationY;
    private void AnimationStart()
    {
        btnCancel.setVisibility(View.GONE);
         translationX = btnStop.getTranslationX();
         translationY = btnStop.getTranslationY();
        ObjectAnimator objectAnimator= ObjectAnimator.ofFloat(btnStop, "translationY", translationY, translationY+300);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
        running = false;
    }

 }
