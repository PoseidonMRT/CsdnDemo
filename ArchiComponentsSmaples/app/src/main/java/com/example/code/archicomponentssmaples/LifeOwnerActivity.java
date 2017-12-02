package com.example.code.archicomponentssmaples;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LifeOwnerActivity extends AppCompatActivity {

  private TextView mTextView;
  private Button mButton;
  private ClickCounterViewModal mClickCounterViewModal;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_life_owner);
    mClickCounterViewModal = ViewModelProviders.of(this).get(ClickCounterViewModal.class);
    initView();
    initData();
  }

  private void initView(){
    mTextView = (TextView)findViewById(R.id.owner_textView);
    mButton = findViewById(R.id.owner_Button);
  }

  private void initData(){

    displayClickCount();

    mButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mClickCounterViewModal.setCount(mClickCounterViewModal.getCount()+1);
        displayClickCount();
      }
    });
  }

  private void displayClickCount(){
    mTextView.setText(mClickCounterViewModal.getCount()+"");
  }
}
