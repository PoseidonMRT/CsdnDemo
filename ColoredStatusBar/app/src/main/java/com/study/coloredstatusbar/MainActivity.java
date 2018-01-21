package com.study.coloredstatusbar;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

  private TabLayout mTabLayout;
  private int mLastTimePosition = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView(savedInstanceState);
    initData();
  }

  private void initView(Bundle savedInstanceState){
    mTabLayout = findViewById(R.id.tabs);
    mTabLayout.setBackgroundColor(getResources().getColor(R.color.red));
  }

  private void initData(){
    mTabLayout.addOnTabSelectedListener(new OnTabSelectedListener() {
      @Override
      public void onTabSelected(Tab tab) {
        int colorFrom = getColorForTab(mLastTimePosition);
        int colorTo = getColorForTab(tab.getPosition());

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(1000);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          @Override
          public void onAnimationUpdate(ValueAnimator animator) {
            int color = (int) animator.getAnimatedValue();

            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
            mTabLayout.setBackgroundColor(color);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
              getWindow().setStatusBarColor(color);
            }
          }

        });
        colorAnimation.start();
      }

      @Override
      public void onTabUnselected(Tab tab) {
        mLastTimePosition = tab.getPosition();
      }

      @Override
      public void onTabReselected(Tab tab) {

      }
    });
  }

  public int getColorForTab(int position) {
    if (position == 0) return ContextCompat.getColor(this, R.color.red);
    else if (position == 1) return ContextCompat.getColor(this, R.color.orange);
    else if (position == 2) return ContextCompat.getColor(this, R.color.yellow);
    else if (position == 3) return ContextCompat.getColor(this, R.color.green);
    else if (position == 4) return ContextCompat.getColor(this, R.color.verdant);
    else if (position == 5) return ContextCompat.getColor(this, R.color.blue);
    else if (position == 6) return ContextCompat.getColor(this, R.color.purple);
    else return ContextCompat.getColor(this, R.color.red);
  }
}
