package com.example.code.archicomponentssmaples;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Lifecycling;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    private LifecycleRegistry mLifecycleRegistry;
    private ClickCounterViewModal mClickCounterViewModal;

    private TextView mTextView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLifeCycle();
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED);
    }

    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    private void initView(){
        mTextView = (TextView)findViewById(R.id.textView);
        mButton = findViewById(R.id.button);
    }

    private void initLifeCycle(){
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);

        mClickCounterViewModal = ViewModelProviders.of(this).get(ClickCounterViewModal.class);
    }

    private void initData(){

        displayClickCount();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickCounterViewModal.setCount(mClickCounterViewModal.getCount()+1);
                displayClickCount();
            }
        });
    }

    private void displayClickCount(){
        mTextView.setText(mClickCounterViewModal.getCount()+"");
    }
}
