package com.example.tuozhaobing.recyclerdatabinding;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import com.example.tuozhaobing.recyclerdatabinding.bean.User;
import com.example.tuozhaobing.recyclerdatabinding.databinding.RecyclerItemBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<User> mUserList = new ArrayList();
    private UserAdapter mRecyclerViewAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_layout);
//        fillData();
//        mRecyclerView = (RecyclerView)findViewById(R.id.recycler);
//        mRecyclerViewAdapter = new UserAdapter(mUserList);
//        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        RecyclerItemBinding binding = DataBindingUtil.setContentView(this, R.layout.recycler_item);
        User user = new User(R.mipmap.ic_launcher,"Test", "User");
        binding.setUser(user);

    }

    private void fillData() {
        mUserList.add(new User(R.mipmap.ic_launcher, "Tom","加拿大"));
        mUserList.add(new User(R.mipmap.ic_launcher, "Jerry","加拿大"));
        mUserList.add(new User(R.mipmap.ic_launcher, "NiJ","加拿大"));
    }
}
