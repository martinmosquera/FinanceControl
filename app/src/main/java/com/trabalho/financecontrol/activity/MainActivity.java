package com.trabalho.financecontrol.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.trabalho.financecontrol.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void operation(View view) {
        Intent intent = new Intent(this, OperationActivity.class);
        startActivity(intent);
        finish();
    }

    public void search(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
        finish();
    }

    public void list(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
        finish();
    }

    public void extract(View view) {
        Intent intent = new Intent(this, ExtractActivity.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view) {
        finish();
    }
}