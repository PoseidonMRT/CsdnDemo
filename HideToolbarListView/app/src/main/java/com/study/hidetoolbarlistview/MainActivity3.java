package com.study.hidetoolbarlistview;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.widget.AbsListView.LayoutParams;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.study.hidetoolbarlistview.MainActivity2.ListViewOnTouchListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity3 extends AppCompatActivity {

  private ListView mListView;
  private float mFirstY,mCurrentY;
  private int direction;
  private int mTouchSlop;
  private boolean mShow = true;
  private ObjectAnimator mObjectAnimator;
  private Toolbar mToolBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main3);
    initView();
    initData();
  }

  private void initView(){
    mToolBar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(mToolBar);
    mListView = (ListView) findViewById(R.id.listview);
    mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
  }

  private void initData(){
    List<Map<String,Object>> lists = new ArrayList<>();
    for (int i=0 ;i<40;i++){
      Map<String,Object> item = new HashMap<String, Object>();
      item.put("text","item"+i);
      lists.add(item);
    }
    SimpleAdapter simpleAdapter = new SimpleAdapter(this,lists,R.layout.item_layout,new String[]{"text"},new int[]{R.id.text});
    mListView.setAdapter(simpleAdapter);
    mListView.setOnTouchListener(new MainActivity3.ListViewOnTouchListener());
  }

  public class ListViewOnTouchListener implements OnTouchListener {

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
      switch (motionEvent.getAction()){
        case MotionEvent.ACTION_DOWN:
          mFirstY = motionEvent.getY();
          break;
        case MotionEvent.ACTION_MOVE:
          mCurrentY = motionEvent.getY();
          if (mCurrentY - mFirstY > mTouchSlop){
            direction = 0;//down
          }else if (mFirstY - mCurrentY >mTouchSlop){
            direction = 1;//up
          }
          if (direction == 1){
            if (mShow){
              getSupportActionBar().show();
//              toolbarAnim(true);//show
              mShow = !mShow;
            }
          }else if (direction == 0){
            if (!mShow){
              getSupportActionBar().hide();
//              toolbarAnim(false);//hide
              mShow = !mShow;
            }
          }
          break;
        case MotionEvent.ACTION_UP:
          break;
      }
      return false;
    }
  }

  /**
   * 可不使用动画，添加动画只是为了让界面效果看起来更流畅
   * @param state
   */
  private void toolbarAnim(final boolean state){
    if (mObjectAnimator != null && mObjectAnimator.isRunning()){
      mObjectAnimator.cancel();
    }
    if (state){
      getSupportActionBar().hide();
      mObjectAnimator = ObjectAnimator.ofFloat(mToolBar,"translationY",mToolBar.getTranslationY(),-mToolBar.getHeight());
    }else{
      getSupportActionBar().show();
      mObjectAnimator = ObjectAnimator.ofFloat(mToolBar,"translationY",mToolBar.getTranslationY(),0);
    }
    mObjectAnimator.setDuration(300);
    mObjectAnimator.start();
  }
}
