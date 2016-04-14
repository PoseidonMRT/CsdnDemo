package com.tt.mvpcircualrectview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.tt.mvpcircualrectview.presenter.CircualRectPresenter;

/**
 * Created by tuozhaobing on 16-4-14.
 * Add Some Description There
 */
public class CircualRectView extends View {
    public void setCircualRectPresenter(CircualRectPresenter circualRectPresenter) {
        this.circualRectPresenter = circualRectPresenter;
    }

    private CircualRectPresenter circualRectPresenter;
    public CircualRectView(Context context) {
        super(context);
    }

    public CircualRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircualRectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        circualRectPresenter.draw(canvas);
        super.onDraw(canvas);
    }
}
