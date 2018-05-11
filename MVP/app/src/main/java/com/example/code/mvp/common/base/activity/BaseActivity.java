package com.example.code.mvp.common.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/5/11
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContent();
        initPresenter();
        initView();
        initData();
    }

    /**
     * init activity layout method
     */
    public abstract void initContent();

    /**
     * init presenter that can be used in activity
     */
    public abstract void initPresenter();

    /**
     * init views method
     */
    public abstract void initView();

    /**
     * init data method
     */
    public abstract void initData();
}
