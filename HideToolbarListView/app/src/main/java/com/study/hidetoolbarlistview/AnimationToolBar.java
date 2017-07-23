package com.study.hidetoolbarlistview;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zbtuo on 17-7-20.
 *
 * Function Declaration 在可见性变化时，显示属性动画:
 */

public class AnimationToolBar extends Toolbar {

  public AnimationToolBar(Context context) {
    super(context);
  }

  public AnimationToolBar(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public AnimationToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  public void setVisibility(int visibility) {
    if (getVisibility() != visibility){
      if (visibility == VISIBLE){
        this.animate().translationY(0).start();
      }else if (visibility == INVISIBLE || visibility == GONE){
        this.animate().translationY(-getHeight()).start();
      }
    }
    super.setVisibility(visibility);
  }
}
