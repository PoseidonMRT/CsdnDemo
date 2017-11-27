package com.example.code.archicomponentssmaples;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LiveDataLifeCycleActivity extends AppCompatActivity implements LifecycleOwner{

    private Button mButton;
    private TextView mTextView;
    private UserNameViewModal mUserNameViewModal;
    private int index = 0;

    private LifecycleRegistry mLifeCycleRegistry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_data_demo_layout);
        mLifeCycleRegistry = new LifecycleRegistry(this);
        mLifeCycleRegistry.markState(Lifecycle.State.CREATED);
        mUserNameViewModal = ViewModelProviders.of(this).get(UserNameViewModal.class);
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLifeCycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLifeCycleRegistry.markState(Lifecycle.State.DESTROYED);
    }

    public void initView(){
        mButton = findViewById(R.id.live_data_button);
        mTextView = findViewById(R.id.live_data_textView);
    }

    public void initData(){

        final Observer<String> userNameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mTextView.setText(s);
            }
        };

        mUserNameViewModal.getCurrentUserName().observe(this,userNameObserver);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otherName = "John"+ index;
                mUserNameViewModal.getCurrentUserName().setValue(otherName);
                index ++;
            }
        });
    }

    @Override
    public Lifecycle getLifecycle() {
        return mLifeCycleRegistry;
    }
}
