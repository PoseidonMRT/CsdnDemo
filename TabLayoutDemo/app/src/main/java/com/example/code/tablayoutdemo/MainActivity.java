package com.example.code.tablayoutdemo;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView(){
        mTabLayout = findViewById(R.id.tabs);
    }

    private void initData(){
        initTabs();
    }

    private void initTabs(){
        mTabLayout.addTab(mTabLayout.newTab().setText("first Tab").setIcon(R.mipmap.ic_launcher_round));
        mTabLayout.addTab(mTabLayout.newTab().setText("second Tab").setIcon(R.mipmap.ic_launcher_round));
        mTabLayout.addTab(mTabLayout.newTab().setText("third Tab").setIcon(R.mipmap.ic_launcher_round));
        mTabLayout.addTab(mTabLayout.newTab().setText("four Tab").setIcon(R.mipmap.ic_launcher_round));
    }
}
