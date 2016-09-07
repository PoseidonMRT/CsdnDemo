package com.example.tuozhaobing.callbackdemo;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by tuozhaobing on 16/9/7.
 */
public class CustomDialogFragment extends DialogFragment {
    private View mRootView;
    private EditText mUserNameEditText,mPassWordEditText;
    private TextView mOkTextView,mCancelTextView;
    private OnLoginInforCompleted mOnLoginInforCompleted;

    public void setOnLoginInforCompleted(OnLoginInforCompleted onLoginInforCompleted) {
        mOnLoginInforCompleted = onLoginInforCompleted;
    }

    public CustomDialogFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.custom_fragment_layout,null);
        initView();
        initData();
        return mRootView;
    }

    public void initView(){
        mUserNameEditText = (EditText)mRootView.findViewById(R.id.id_txt_username);
        mPassWordEditText = (EditText)mRootView.findViewById(R.id.id_txt_password);
        mOkTextView = (TextView)mRootView.findViewById(R.id.id_ok);
        mCancelTextView = (TextView)mRootView.findViewById(R.id.id_cancel);
    }

    public void initData(){
        mOkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnLoginInforCompleted.inputLoginInforCompleted(mUserNameEditText.getText().toString(),mPassWordEditText.getText().toString());
                dismiss();
            }
        });
        mCancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
