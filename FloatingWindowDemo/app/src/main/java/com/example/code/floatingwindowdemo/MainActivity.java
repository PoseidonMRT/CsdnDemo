package com.example.code.floatingwindowdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingWindow floatingWindow = new FloatingWindow.Builder(this,Gravity.CENTER).setClickable(true).setDragEnabled(true).setContentView(R.layout.default_text_floting_window_layout).buildCustom();
        floatingWindow.show();
    }
}
