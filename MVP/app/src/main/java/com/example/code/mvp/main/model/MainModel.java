package com.example.code.mvp.main.model;

import com.example.code.mvp.main.model.interfaces.IMainModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/5/11
 */
public class MainModel implements IMainModel {

    private List<String> mSpinnerDatas;

    public MainModel() {

    }

    @Override
    public List<String> loadSpinnerData() {
        loadSpinnerDatas();
        return mSpinnerDatas;
    }

    private void loadSpinnerDatas(){
        mSpinnerDatas = new ArrayList<>();
        mSpinnerDatas.add("Java");
        mSpinnerDatas.add("C++");
        mSpinnerDatas.add("C");
        mSpinnerDatas.add("C#");
        mSpinnerDatas.add("Python");
        mSpinnerDatas.add("Dart");
    }

    @Override
    public List<String> loadListData(String type) {
        List<String> listData = new ArrayList<>();
        for (int i=0;i<50;i++){
            listData.add("Hello "+ type+">>>>"+i);
        }
        return listData;
    }
}
