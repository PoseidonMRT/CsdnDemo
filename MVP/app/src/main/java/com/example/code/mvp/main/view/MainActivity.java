package com.example.code.mvp.main.view;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.code.mvp.R;
import com.example.code.mvp.common.base.activity.BaseActivity;
import com.example.code.mvp.main.presenter.MainPresenter;
import com.example.code.mvp.main.presenter.interfaces.IMainPresenter;
import com.example.code.mvp.main.view.interfaces.IMainView;

import java.util.List;

/**
 * @author ZhaoBingTuo
 * main page for demo app
 */
public class MainActivity extends BaseActivity implements IMainView , AdapterView.OnItemSelectedListener{

    private Spinner mListSourceSpinner;
    private ArrayAdapter mListSourceSpinnerAdapter;
    private ListView mListView;
    private ArrayAdapter mListViewAdapter;
    private IMainPresenter mMainPresenter;

    @Override
    public void initContent() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initPresenter() {
        mMainPresenter = new MainPresenter(this);
    }

    @Override
    public void initView() {
        mListSourceSpinner = findViewById(R.id.list_source_spinner);
        mListView = findViewById(R.id.list);
    }

    @Override
    public void initData() {
        mMainPresenter.loadSpinnerData();
    }

    @Override
    public void showErrorMsg() {
        Toast.makeText(this, R.string.load_data_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateSpinnerData(List<String> data) {
        mListSourceSpinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,data);
        mListSourceSpinner.setAdapter(mListSourceSpinnerAdapter);
        mListSourceSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mMainPresenter.loadListData(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void updateListData(List<String> data) {
        mListViewAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,data);
        mListView.setAdapter(mListViewAdapter);
    }
}
