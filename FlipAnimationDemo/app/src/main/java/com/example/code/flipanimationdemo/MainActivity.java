package com.example.code.flipanimationdemo;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mContainer;
    private MainPageViewModel mMainPageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mContainer = findViewById(R.id.container);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new CardFrontFragment())
                .commit();
    }

    private void initData(){
        mMainPageViewModel = ViewModelProviders.of(this).get(MainPageViewModel.class);
        mMainPageViewModel.isShowingBack().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                flipCard(aBoolean);
            }
        });
    }

    @SuppressLint("ResourceType")
    private void flipCard(boolean showingBack){
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.anim.right_in, R.anim.right_out,
                            R.anim.left_in, R.anim.left_out)
                    .replace(R.id.container, showingBack?new CardBackFragment():new CardFrontFragment())
                    // Add this transaction to the back stack, allowing users to press Back
                    // to get to the front of the card.
                    .addToBackStack(null)
                    // Commit the transaction.
                    .commit();

    }
}
