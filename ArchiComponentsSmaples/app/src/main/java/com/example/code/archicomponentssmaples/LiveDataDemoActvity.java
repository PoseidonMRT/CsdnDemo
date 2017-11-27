package com.example.code.archicomponentssmaples;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2017/11/27
 */
public class LiveDataDemoActvity extends AppCompatActivity {

    private Button mButton;
    private TextView mTextView;
    private UserNameViewModal mUserNameViewModal;
    private int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_data_demo_layout);
        mUserNameViewModal = ViewModelProviders.of(this).get(UserNameViewModal.class);
        mUserNameViewModal.getCurrentUserName().setValue("John");
        initView();
        initData();
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
                index++;
                String otherName = "John"+ index;
                mUserNameViewModal.getCurrentUserName().setValue(otherName);
            }
        });
    }
}
