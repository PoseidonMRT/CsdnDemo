package com.tt.coordinatorlayoutdemo;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String[] str  = new String[]{"Hello","text","Hello","text","text","Hello","text","text","Hello","text","text","text","Hello","text","text","Hello","text","text","Hello","text","text","Hello","text","text","Hello","text","text","text","Hello","text","text","Hello","text","text","Hello","text","text","Hello","text","text","Hello","text","text","text","Hello","text","text","Hello","text","text","Hello","text","text","Hello","text","text","Hello","text","text","text","Hello","text","text","Hello","text","text","Hello","text","text","Hello","text","text","Hello","text","text","Hello","text","text","Hello","text","text","Hello","text"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        CustomListView customListView = (CustomListView)findViewById(R.id.custom_listview);
//        List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
//        for (int i = 0; i < str.length; i++) {
//            Map<String, Object> listem = new HashMap<String, Object>();
//            listem.put("textview", str[i]);
//            listems.add(listem);
//        }
//        SimpleAdapter simpleAdapter = new SimpleAdapter(this,listems,R.layout.item,new String[]{"textview"},new int[]{R.id.tv});
//        customListView.setAdapter(simpleAdapter);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("搜索");
    }
}
