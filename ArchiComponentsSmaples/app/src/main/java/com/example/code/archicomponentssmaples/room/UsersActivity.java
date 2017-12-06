package com.example.code.archicomponentssmaples.room;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.example.code.archicomponentssmaples.R;

public class UsersActivity extends AppCompatActivity {

  private TextView mUserCountTextView;
  private Button mAddUserButton;
  private UserViewModel mUserViewModel;
  private int clickTimes ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_users);
    initView();
    initData();
  }


  private void initView(){
    mAddUserButton = findViewById(R.id.add_user);
    mUserCountTextView = findViewById(R.id.user_count);
  }

  private void initData(){
    mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    final Observer<Integer> userObserver = new Observer<Integer>() {
      @Override
      public void onChanged(@Nullable Integer integer) {
        mUserCountTextView.setText(integer+"");
      }
    };
    mUserViewModel.getUserCounts().observe(this,userObserver);

    mAddUserButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        clickTimes++;
        User _user = new User();
        _user.id = clickTimes;
        _user.name = "John"+clickTimes;
        mUserViewModel.putUsers(_user,UsersActivity.this);
      }
    });
  }
}
