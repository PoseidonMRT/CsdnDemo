package com.example.code.customview;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;


/**
 * A textview that can be used to implements type writer animation
 *
 * @author tuozhaobing
 * @date 2018/2/8
 */
public class TypeWriterTextView extends AppCompatTextView {

    private CharSequence mCharSequence;
    private int mIndex;
    private long mDelayTime = 150;
    private long mDuration = 0;

    private Handler mHandler = new Handler();

    private Runnable mDisplayRunnable = new Runnable() {
        @Override
        public void run() {
            setText(mCharSequence.subSequence(0, mIndex++));
            if (mIndex <= mCharSequence.length()) {
                mHandler.postDelayed(mDisplayRunnable, mDelayTime);
            }
        }
    };

    public TypeWriterTextView(Context context) {
        super(context);
    }

    public TypeWriterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TypeWriterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TypeWriterTextView setDuration(long duration){
        mDuration = duration;
        return this;
    }

    public void startAnimation() {
        if (TextUtils.isEmpty(getText().toString())) {
            return;
        }

        mCharSequence = getText();
        mIndex = 0;

        if (mDuration != 0){
            mDelayTime = mDuration / mCharSequence.length();
        }

        mHandler.postDelayed(mDisplayRunnable,mDelayTime);

    }
}
