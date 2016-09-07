package com.example.tuozhaobing.callbackdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnLoginInforCompleted{

    private TextView mContentTextView;
    private CustomDialogFragment mCustomDialogFragment;
    public static final String COMMAND = "USER_INFOR_DIALOG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContentTextView = (TextView) findViewById(R.id.user_input_content);
        mCustomDialogFragment = new CustomDialogFragment();
        mCustomDialogFragment.setOnLoginInforCompleted(this);
        mContentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomDialogFragment.show(getFragmentManager(),COMMAND);
            }
        });
    }

    @Override
    public void inputLoginInforCompleted(String userName, String passWord) {
        mContentTextView.setText(userName+"::::"+passWord);
    }
}
