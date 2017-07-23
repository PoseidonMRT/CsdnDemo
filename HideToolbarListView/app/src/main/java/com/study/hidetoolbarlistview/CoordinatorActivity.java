package com.study.hidetoolbarlistview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoordinatorActivity extends AppCompatActivity {

  private RecyclerView mRecyclerView;
  private Toolbar mToolBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_coordinator);
    initView();
    initData();
  }

  private void initView(){
    mToolBar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(mToolBar);
    mRecyclerView = (RecyclerView) findViewById(R.id.listview);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  private void initData(){
    List<String> lists = new ArrayList<>();
    for (int i=0 ;i<40;i++){
      lists.add("item "+i);
    }
    mRecyclerView.setAdapter(new HomeAdapter(this,lists));
  }

  class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
  {

    private Context mContext;
    private List<String> mDatas;
    public HomeAdapter(Context context,List<String> datas) {
      super();
      mContext = context;
      mDatas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
      MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
          CoordinatorActivity.this).inflate(R.layout.item_layout, parent,
          false));
      return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
      holder.tv.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount()
    {
      return mDatas.size();
    }

    class MyViewHolder extends ViewHolder
    {

      TextView tv;

      public MyViewHolder(View view)
      {
        super(view);
        tv = (TextView) view.findViewById(R.id.text);
      }
    }
  }


}
