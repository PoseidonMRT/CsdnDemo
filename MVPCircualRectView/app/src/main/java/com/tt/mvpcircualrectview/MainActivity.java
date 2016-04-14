package com.tt.mvpcircualrectview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tt.mvpcircualrectview.presenter.CircualRectPresenterImpl;
import com.tt.mvpcircualrectview.utils.MeasureUtils;
import com.tt.mvpcircualrectview.view.CircualRectView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CircualRectView circualRectView = new CircualRectView(this);
        CircualRectPresenterImpl circualRectPresenter = new CircualRectPresenterImpl(this, MeasureUtils.getScreenWidth(this),MeasureUtils.getScreenHeight(this));
        circualRectView.setCircualRectPresenter(circualRectPresenter);
        setContentView(circualRectView);
    }
}
