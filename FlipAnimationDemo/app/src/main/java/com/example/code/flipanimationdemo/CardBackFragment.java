package com.example.code.flipanimationdemo;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CardBackFragment extends Fragment {

    private MainPageViewModel mMainPageViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainPageViewModel = ViewModelProviders.of(getActivity()).get(MainPageViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_back_layout,container,false);
        view.findViewById(R.id.back_imageview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPageViewModel.isShowingBack().setValue(false);
            }
        });
        return view;
    }
}
