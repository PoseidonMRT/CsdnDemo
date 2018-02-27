package com.example.code.animationdrawabledemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SubRingRotateDrawable drawable = new SubRingRotateDrawable(Color.parseColor("#f08d43"));
        findViewById(R.id.text).setBackground(drawable);
        drawable.start();
    }
}
