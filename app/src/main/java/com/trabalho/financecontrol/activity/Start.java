package com.trabalho.financecontrol.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.trabalho.financecontrol.R;

import java.util.Timer;
import java.util.TimerTask;

public class Start extends AppCompatActivity {

    private Timer timer = new Timer();
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toMainActivity();
                    }
                });
            }
        };
        timer.schedule(timerTask,3000);
    }

    private void toMainActivity() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}