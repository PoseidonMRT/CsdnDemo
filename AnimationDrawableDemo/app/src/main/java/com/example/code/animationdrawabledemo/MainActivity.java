package com.example.code.animationdrawabledemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TwoSubRingRotateDrawable drawable = new TwoSubRingRotateDrawable(Color.parseColor("#f08d43"));
        findViewById(R.id.text_view).setBackground(drawable);
        drawable.start();
    }
}
